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
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;

import BusinessObjects.Client;
import BusinessObjects.Contrat;
import BusinessObjects.Sanction;
import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class GestionContrats extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client client=new Client();
	Contrat contrat=new Contrat();
	Sanction sanction = new Sanction();
	private JPanel contentPane;
	private JTextField textCodeClient;
	private JTextField textNumeroContrat;
	private JTable table;
	private JDateChooser textDateContrat;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JLabel gestionDesContrats;
	private JLabel btnPrevious;
	private JFrame frame;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionContrats frame = new GestionContrats();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void autoId() {
		long id=contrat.AutoId();
		if(id==0) {
			textNumeroContrat.setText("1");
		}
		else {
			textNumeroContrat.setText(""+id);
		}
	}
	
	public void autoDate() {
		java.util.Date uDate = new java.util.Date();
		java.sql.Date dateContrat = new java.sql.Date(uDate.getTime());
		textDateContrat.setDate(dateContrat);
	}
	
	public GestionContrats() {
		frame=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textDateContrat = new JDateChooser();
		((JTextField) textDateContrat.getDateEditor().getUiComponent()).setBackground(Color.decode("#e6ebee"));
		((JTextField) textDateContrat.getDateEditor().getUiComponent()).setBorder(null);
		
		JDateChooser textDateEcheance = new JDateChooser();
		((JTextField) textDateEcheance.getDateEditor().getUiComponent()).setBackground(Color.decode("#e6ebee"));
		((JTextField) textDateEcheance.getDateEditor().getUiComponent()).setBorder(null);
		
		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(frame.getTitle()== "Gestion des contrats ") {
					Menu previous = new Menu();
					previous.setTitle("Menu - Utilisateur");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}else if(frame.getTitle()== "Gestion des contrats  ") {
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
		
		
		textDateEcheance.setFont(new Font("Verdana", Font.PLAIN, 16));
		textDateEcheance.setBounds(68, 306, 234, 32);
		textDateEcheance.setDateFormatString("yyyy/MM/dd");
		contentPane.add(textDateEcheance);
		
		button = new JButton("");
		button.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAjouter.png")).getImage()));
		button.setSize(137, 46);
		button.setLocation(62, 471);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textCodeClient.getText().length()==0 || textNumeroContrat.getText().length()==0 || textDateContrat.equals("") || textDateEcheance.equals("")) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
					else {
						java.sql.Date dateContrat = new java.sql.Date(textDateContrat.getDate().getTime());
						java.sql.Date dateEcheance = new java.sql.Date(textDateEcheance.getDate().getTime());
						String codeClient=textCodeClient.getText();
						int numeroContrat=Integer.parseInt(textNumeroContrat.getText());
						int m=client.RechercherParCodeClient(codeClient, "");
						if(m==0) {
							JOptionPane.showMessageDialog(null, "Ce client n'existe pas dans notre base de données", "Erreur d'insertion", JOptionPane.OK_OPTION);
						}
						else {
							int d=contrat.Ajouter(numeroContrat, dateContrat, dateEcheance,codeClient);
							if(d==1) {
								// lors de l'ajout d'un contrat une sanction se créera pour calculer l'amende!!
								sanction.Ajouter(numeroContrat, dateEcheance,0);
								JOptionPane.showMessageDialog(null, "le contrat a été ajouté");
							}
							else {
								JOptionPane.showMessageDialog(null, "Le contrat n'a pas été ajouté! Veuillez reessayez");
							}
						}
					}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro de contrat se compose des entiers seulement !","Erreur lors du saisie",JOptionPane.ERROR_MESSAGE);
				}
				autoDate();
				((JTextField) textDateContrat.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateEcheance.getDateEditor().getUiComponent()).setText("");
				autoId();
				button_1.doClick();
			}
		});
		button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		contentPane.add(button);
		textDateContrat.setFont(new Font("Verdana", Font.PLAIN, 16));
		textDateContrat.setBounds(68, 236, 234, 32);
		textDateContrat.setDateFormatString("yyyy/MM/dd");
		contentPane.add(textDateContrat);
		
		textCodeClient = new JTextField();
		textCodeClient.setBorder(null);
		textCodeClient.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCodeClient.setBackground(Color.decode("#e6ebee"));
		textCodeClient.setColumns(10);
		textCodeClient.setBounds(68, 98, 234, 32);
		contentPane.add(textCodeClient);
		
		textNumeroContrat = new JTextField();
		textNumeroContrat.setBorder(null);
		textNumeroContrat.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNumeroContrat.setBackground(Color.decode("#e6ebee"));
		textNumeroContrat.setColumns(10);
		textNumeroContrat.setBounds(68, 167, 234, 32);
		contentPane.add(textNumeroContrat);
		autoId();
		autoDate();
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficher.png")).getImage()));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs;
				try {
					rs = contrat.Lister();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				autoDate();
				((JTextField) textDateContrat.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateEcheance.getDateEditor().getUiComponent()).setText("");
				autoId();
				button.setEnabled(true);
			}
		});
		button_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_1.setBounds(423, 471, 157, 46);
		button_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_1);
		
		button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnModifier.png")).getImage()));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textCodeClient.getText().length()==0 || textNumeroContrat.getText().length()==0 || textDateContrat.equals("") || textDateEcheance.equals("")) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
					else {
						java.sql.Date dateContrat = new java.sql.Date(textDateContrat.getDate().getTime());
						java.sql.Date dateEcheance = new java.sql.Date(textDateEcheance.getDate().getTime());
						String codeClient=textCodeClient.getText();
						int numeroContrat=Integer.parseInt(textNumeroContrat.getText());
						int m=client.RechercherParCodeClient(codeClient, "");
						if(m==0) {
							JOptionPane.showMessageDialog(null, "Ce client n'existe pas dans notre base de données", "Erreur d'insertion", JOptionPane.OK_OPTION);
						}
						else {
							int d=contrat.Modifier(numeroContrat, dateContrat, dateEcheance,codeClient);
							if(d==1) {
								JOptionPane.showMessageDialog(null, "Le contrat a été modifié");
							}
							else {
								JOptionPane.showMessageDialog(null, "Le contrat n'a pas été modifié! Veuillez reessayez");
							}
						}
					}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro de contrat se compose des entiers seulement !","Erreur lors du modification",JOptionPane.ERROR_MESSAGE);
				}
				autoDate();
				((JTextField) textDateContrat.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateEcheance.getDateEditor().getUiComponent()).setText("");
				autoId();
				button_1.doClick();
				button.setEnabled(true);
			}
		});
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(235, 471, 152, 46);
		button_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_2);
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnRechercher.png")).getImage()));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int numeroContrat=Integer.parseInt(JOptionPane.showInputDialog(null, "Veuillez saisir le numéro de contrat", "recherche d'informations sur le contrat", JOptionPane.QUESTION_MESSAGE));
					if(numeroContrat!=0) {
						ResultSet rs=contrat.Rechercher(numeroContrat);
						if(rs!=null ) {
							table.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else {
							JOptionPane.showMessageDialog(null, "Aucune Contrat n'existe avec ce numéro : "+numeroContrat);
						}
					}
					else {
						System.out.println("tache annulée");
					}
			}catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "le numéro de contrat se compose des entiers seulement !","Erreur lors du modification",JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			autoDate();
			((JTextField) textDateContrat.getDateEditor().getUiComponent()).setText("");
			((JTextField) textDateEcheance.getDateEditor().getUiComponent()).setText("");
			autoId();
			button.setEnabled(true);
			}
		});
		button_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_3.setBounds(616, 471, 183, 46);
		button_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_3);
		
		button_4 = new JButton("");
		button_4.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSupprimer.png")).getImage()));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNumeroContrat.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro de contrat", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
				else {
					try {
						int numeroContrat=Integer.parseInt(textNumeroContrat.getText());
						int d=contrat.Supprimer(numeroContrat);
						if(d==1) {
							JOptionPane.showMessageDialog(null, "Le contrat a été supprimé");
						}
						else {
							JOptionPane.showMessageDialog(null, "Le contrat n'a pas été supprimé! Veuillez reessayez");
						}
					}
					catch(NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Le numéro de contrat se compose des entiers seulement !","Erreur lors du suppression",JOptionPane.ERROR_MESSAGE);
					}
				}
				autoDate();
				((JTextField) textDateContrat.getDateEditor().getUiComponent()).setText("");
				((JTextField) textDateEcheance.getDateEditor().getUiComponent()).setText("");
				autoId();
				button_1.doClick();
				button.setEnabled(true);
			}
		});
		button_4.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_4.setBounds(835, 471, 171, 46);
		button_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_4);
		
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
				textCodeClient.setText(table.getModel().getValueAt(ligne, 3).toString());
				textNumeroContrat.setText(table.getModel().getValueAt(ligne, 0).toString());
				textDateContrat.setDate(((java.sql.Date) table.getModel().getValueAt(ligne, 1)));
				textDateEcheance.setDate(((java.sql.Date) table.getModel().getValueAt(ligne, 2)));
				button.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		
		gestionDesContrats = new JLabel("");
		gestionDesContrats.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/MenuGestionContrats.png")).getImage()));
		gestionDesContrats.setBounds(0, 0, 1234, 549);
		contentPane.add(gestionDesContrats);
		
	}
}
