package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import BusinessObjects.Client;
import BusinessObjects.Reservation;
import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class GestionReservations extends JFrame {

	/**
	 * 
	 */
	Reservation reservation=new Reservation();
	Client client=new Client();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCodeReservation;
	private JTable table;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JTextField textCodeClient;
	private JDateChooser textDateReservation;
	private JDateChooser textDateDepart;
	private JLabel btnPrevious;
	private JButton btnAnnuler;
	private JFrame frame;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionReservations frame = new GestionReservations();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void autoId() {
		long id=reservation.AutoId();
		if(id==0) {
			textCodeReservation.setText("R0001");
		}
		else {
			textCodeReservation.setText("R0" + String.format("%03d", id));
		}
	}
	public void autoDate() {
		java.util.Date uDate = new java.util.Date();
		java.sql.Date dateValidation = new java.sql.Date(uDate.getTime());
		textDateReservation.setDate(dateValidation);
	}

	/**
	 * Create the frame.
	 */
	public GestionReservations() {
		frame=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		reservation.VerifierUneReservation();
		
		JDateChooser textDateRetour = new JDateChooser();
		((JTextField) textDateRetour.getDateEditor().getUiComponent()).setBackground(Color.decode("#e6ebee"));
		((JTextField) textDateRetour.getDateEditor().getUiComponent()).setBorder(null);
		((JTextField) textDateRetour.getDateEditor().getUiComponent()).setFont(new Font("Verdana", Font.PLAIN, 16));
		
		JButton btnSupprimer = new JButton("");
		btnSupprimer.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSupprimer.png")).getImage()));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codeReservation=textCodeReservation.getText();
				if(codeReservation.length()==0) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le code de réservation", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
				else {
					int choixSupprim=JOptionPane.showConfirmDialog(null, "<html>Êtes-vous sûr de supprimer cette réservation?<br><br></html>", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(choixSupprim==0) {
						int d=reservation.Supprimer(codeReservation);
						if(d==1) {
							JOptionPane.showMessageDialog(null, "La réservation a été supprimée");
						}
						else {
							JOptionPane.showMessageDialog(null, "La réservation n'a pas été supprimée! Veuillez reessayez");
						}
					}
					else {
						
					}
				}
				((JTextField) textDateReservation.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateDepart.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateRetour.getDateEditor().getUiComponent()).setText("");
				autoId();
				button.setEnabled(true);
				button_1.doClick();
				autoDate();
			}
		});
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficherListe.png")).getImage()));
		button_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] affiche = {"liste de réservations validées", "liste de réservations non validées","liste de réservation annulées"};
			    String validation = (String)JOptionPane.showInputDialog(null, "Veuillez choisir votre tache !","editer réservation!", JOptionPane.QUESTION_MESSAGE,null,affiche,affiche[2]);
			    ResultSet rs = null;
			    if(validation==affiche[0]) {
			    	 rs=reservation.AfficherParListe("validée");
			    }
			    else if(validation==affiche[1]) {
			    	rs=reservation.AfficherParListe("non validée");
			    }
			    else if(validation==affiche[2]) {
			    	rs=reservation.AfficherParListe("annulée");
			    }
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			    ((JTextField) textDateReservation.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateDepart.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateRetour.getDateEditor().getUiComponent()).setText("");
				autoId();
				button.setEnabled(true);
				autoDate();
			}
		});
		
		btnAnnuler = new JButton("");
		btnAnnuler.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAnnuler.png")).getImage()));
		btnAnnuler.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					String validation="annulée";
					String codeReservation=textCodeReservation.getText();
					int d=reservation.Valider(codeReservation, validation);
					if(d==1) {
						JOptionPane.showMessageDialog(null, "votre réservation a été : "+validation);
					}else {
						JOptionPane.showMessageDialog(null, "Erreur lors de validation: ");
					}
					((JTextField) textDateReservation.getDateEditor().getUiComponent()).setText("");
					((JTextField) textDateDepart.getDateEditor().getUiComponent()).setText("");
					((JTextField) textDateRetour.getDateEditor().getUiComponent()).setText("");
					autoId();
					button.setEnabled(true);
					button_1.doClick();
					autoDate();
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(412, 106, 807, 291);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionForeground(new Color(255, 255, 255));
		table.setShowVerticalLines(false);
		table.setRowHeight(25);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFocusable(false);
		table.setBorder(null);
		table.setSelectionBackground(Color.decode("#33ae5d"));
		table.setSelectionForeground(Color.decode("#ffffff"));
		table.setFont(new Font("Franklin Gothic Demi C...", Font.PLAIN, 14));
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(Color.decode("#e6ebee"));
		table.getTableHeader().setForeground(Color.decode("#000"));
		table.getTableHeader().setFont(new Font("Franklin Gothic Demi C...", Font.PLAIN, 18));
		table.setRowHeight(30);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int ligne=table.getSelectedRow();
				textCodeClient.setText(table.getModel().getValueAt(ligne, 5).toString());
				textCodeReservation.setText(table.getModel().getValueAt(ligne, 0).toString());
				textDateReservation.setDate(((java.sql.Date) table.getModel().getValueAt(ligne, 1)));
				textDateDepart.setDate(((java.sql.Date) table.getModel().getValueAt(ligne, 2)));
				textDateRetour.setDate(((java.sql.Date) table.getModel().getValueAt(ligne, 3)));
				button.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		
		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(frame.getTitle()== "Gestion des réservations ") {
					Menu previous = new Menu();
					previous.setTitle("Menu - Utilisateur");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}else if(frame.getTitle()== "Gestion des réservations  ") {
					MenuAdmin previous = new MenuAdmin();
					previous.setTitle("Menu - Admin");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}else {
					Menu previous = new Menu();
					previous.setTitle("Menu - Utilisateur");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}
			}
		});
		btnPrevious.setBounds(43, 3, 46, 49);
		btnPrevious.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnPrevious);
		
		
		btnAnnuler.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnAnnuler.setBounds(235, 430, 152, 46);
		contentPane.add(btnAnnuler);
		
		
		button_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_3.setBounds(423, 430, 218, 46);
		contentPane.add(button_3);
		btnSupprimer.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnSupprimer.setBounds(835, 490, 171, 46);
		btnSupprimer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnSupprimer);
		
		button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnModifier.png")).getImage()));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				java.sql.Date dateReservation = new java.sql.Date(textDateReservation.getDate().getTime());
				java.sql.Date dateDepart = new java.sql.Date(textDateDepart.getDate().getTime());
				java.sql.Date dateRetour = new java.sql.Date(textDateRetour.getDate().getTime());
				String codeReservation=textCodeReservation.getText();
				String codeClient=textCodeClient.getText();
				int m=client.RechercherParCodeClient(codeClient, "");
				if(codeReservation.length()==0 || dateReservation.equals("") || dateDepart.equals("") || dateRetour.equals("")) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur de modification", JOptionPane.OK_OPTION);
				}
				else {
					if(m==0) {
						JOptionPane.showMessageDialog(null, "Ce client n'existe pas dans notre base de données", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
					else {
						int d=reservation.Modifier(codeReservation, dateReservation, dateDepart, dateRetour,codeClient,"");
						if(d==1) {
							JOptionPane.showMessageDialog(null, "La réservation a été modifiée");
						}
						else {
							JOptionPane.showMessageDialog(null, "La reservation n'a pas été modifié! Veuillez reessayez");
						}
					}
				}
				((JTextField) textDateReservation.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateDepart.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateRetour.getDateEditor().getUiComponent()).setText("");
				autoId();
				button.setEnabled(true);
				button_1.doClick();
				autoDate();
			}
		});
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(235, 490, 152, 46);
		button_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_2);
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficher.png")).getImage()));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultSet rs;
				try {
					rs = reservation.Lister();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_1.setBounds(423, 490, 157, 46);
		button_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_1);
		
		button_4 = new JButton("");
		button_4.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnRechercher.png")).getImage()));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codeReservation=JOptionPane.showInputDialog(null, "Veuillez saisir le code de réservation", "recherche d'informations sur la réservation", JOptionPane.QUESTION_MESSAGE);
				if(codeReservation!=null) {
					ResultSet rs=reservation.Rechercher(codeReservation);
					if(rs!=null) {
						table.setModel(DbUtils.resultSetToTableModel(rs));
					}
					else {
						JOptionPane.showMessageDialog(null, "Aucune réservation n'existe avec ce code : "+codeReservation);
					}
				}
				else {
					System.out.println("tache annulée");
				}
				((JTextField) textDateReservation.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateDepart.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateRetour.getDateEditor().getUiComponent()).setText("");
				autoId();
				button.setEnabled(true);
				autoDate();
			}
		});
		button_4.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_4.setBounds(616, 490, 183, 46);
		button_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_4);
		
		button = new JButton("");
		button.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAjouter.png")).getImage()));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					if(textCodeReservation.getText().length()==0 || textCodeClient.getText().length()==0 || textDateReservation.equals("") || textDateDepart.equals("") 
							|| textDateRetour.equals("")) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}else {
						java.sql.Date dateReservation = new java.sql.Date(textDateReservation.getDate().getTime());
						java.sql.Date dateDepart = new java.sql.Date(textDateDepart.getDate().getTime());
						java.sql.Date dateRetour = new java.sql.Date(textDateRetour.getDate().getTime());
						String codeReservation=textCodeReservation.getText();
						String codeClient=textCodeClient.getText();
					int m=client.RechercherParCodeClient(codeClient, "");
					if(codeReservation.length()==0 || codeClient.length()==0 || dateReservation.equals("") || dateDepart.equals("") || dateRetour.equals("")) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
					else {
						if(m==0) {
							JOptionPane.showMessageDialog(null, "Ce client n'existe pas dans notre base de données", "Erreur d'insertion", JOptionPane.OK_OPTION);
						}
						else {
							int d=reservation.Ajouter(codeReservation, dateReservation, dateDepart, dateRetour,codeClient,"");
							if(d==1) {
								JOptionPane.showMessageDialog(null, "La réservation a été ajoutée");
							}
							else {
								JOptionPane.showMessageDialog(null, "La reservation n'a pas été ajoutée! Veuillez reessayez");
							}
						}
					}
				}
				autoDate();
				((JTextField) textDateDepart.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateRetour.getDateEditor().getUiComponent()).setText("");
				autoId();
				button_1.doClick();
			}
		});
		button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button.setBounds(62, 490, 137, 46);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button);
		
		button_5 = new JButton("");
		button_5.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnValider.png")).getImage()));
		button_5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				String validation="validée";
				String codeReservation=textCodeReservation.getText();
				int d=reservation.Valider(codeReservation, validation);
				if(d==1) {
					JOptionPane.showMessageDialog(null, "Votre réservation a été : "+validation);
				}else {
					JOptionPane.showMessageDialog(null, "Erreur lors de validation: ");
				}
				((JTextField) textDateReservation.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateDepart.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateRetour.getDateEditor().getUiComponent()).setText("");
				autoId();
				button.setEnabled(true);
				button_1.doClick();
				autoDate();
			}
		});
		button_5.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_5.setBounds(62, 430, 138, 46);
		contentPane.add(button_5);
		textDateRetour.setDateFormatString("yyyy/MM/dd");
		textDateRetour.setBounds(68, 376, 234, 32);
		contentPane.add(textDateRetour);
		
		textCodeReservation = new JTextField();
		textCodeReservation.setBorder(null);
		textCodeReservation.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCodeReservation.setBackground(Color.decode("#e6ebee"));
		textCodeReservation.setColumns(10);
		textCodeReservation.setBounds(68, 167, 234, 32);
		contentPane.add(textCodeReservation);
		
		textCodeClient = new JTextField();
		textCodeClient.setBorder(null);
		textCodeClient.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCodeClient.setBackground(Color.decode("#e6ebee"));
		textCodeClient.setColumns(10);
		textCodeClient.setBounds(68, 98, 234, 32);
		contentPane.add(textCodeClient);
		
		textDateDepart = new JDateChooser();
		((JTextField) textDateDepart.getDateEditor().getUiComponent()).setBackground(Color.decode("#e6ebee"));
		((JTextField) textDateDepart.getDateEditor().getUiComponent()).setBorder(null);
		((JTextField) textDateDepart.getDateEditor().getUiComponent()).setFont(new Font("Verdana", Font.PLAIN, 16));
		textDateDepart.setDateFormatString("yyyy/MM/dd");
		textDateDepart.setBounds(68, 306, 234, 32);
		contentPane.add(textDateDepart);
		
		textDateReservation = new JDateChooser();
		((JTextField) textDateReservation.getDateEditor().getUiComponent()).setBackground(Color.decode("#e6ebee"));
		((JTextField) textDateReservation.getDateEditor().getUiComponent()).setBorder(null);
		((JTextField) textDateReservation.getDateEditor().getUiComponent()).setFont(new Font("Verdana", Font.PLAIN, 16));
		textDateReservation.setDateFormatString("yyyy/MM/dd");
		textDateReservation.setBounds(68, 236, 234, 32);
		contentPane.add(textDateReservation);
		
		JLabel gestionReservation = new JLabel("New label");
		gestionReservation.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/MenuGestionReservations.png")).getImage()));
		gestionReservation.setBounds(0, 0, 1234, 549);
		contentPane.add(gestionReservation);
		autoId();
		autoDate();
	}
}
