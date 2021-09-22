package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import com.toedter.calendar.JDateChooser;

import BusinessObjects.Contrat;
import BusinessObjects.Facture;
import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class GestionFactures extends JFrame {

	/**
	 * 
	 */
	Facture facture=new Facture();
	Contrat contrat=new Contrat();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCodeContrat;
	private JTextField textNumeroFacture;
	private JTable table;
	private JTextField textMontant;
	private JDateChooser textDateFacture;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JLabel btnPrevious;
	private JFrame frame;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionFactures frame = new GestionFactures();
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
		long id=facture.AutoId();
		if(id==0) {
			textNumeroFacture.setText("1");
		}
		else {
			textNumeroFacture.setText(""+id);
		}
	}
	
	public void autoDate() {
		java.util.Date uDate = new java.util.Date();
		java.sql.Date dateFacture = new java.sql.Date(uDate.getTime());
		textDateFacture.setDate(dateFacture);
	}
	
	public GestionFactures() {
		frame=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficher.png")).getImage()));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs;
				try {
					rs = facture.Lister();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				autoDate();
				textMontant.setText("");
				autoId();
				textCodeContrat.setText("");
				button.setEnabled(true);
			}
		});
		button_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_1.setBounds(423, 471, 157, 46);
		button_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_1);
		
		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(frame.getTitle()== "Gestion des factures ") {
					Menu previous = new Menu();
					previous.setTitle("Menu - Utilisateur");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}else if(frame.getTitle()== "Gestion des factures  ") {
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
				textCodeContrat.setText(table.getModel().getValueAt(ligne, 4).toString());
				textNumeroFacture.setText(table.getModel().getValueAt(ligne, 0).toString());
				textDateFacture.setDate(((java.sql.Date) table.getModel().getValueAt(ligne, 1)));
				textMontant.setText(table.getModel().getValueAt(ligne, 2).toString());
				button.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSupprimer.png")).getImage()));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNumeroFacture.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro de la facture", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
				else {
					int choixSupprim=JOptionPane.showConfirmDialog(null, "<html>Êtes-vous sûr de supprimer cette facture?<br><br></html>", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(choixSupprim==0) {
						try {
							int numeroFacture=Integer.parseInt(textNumeroFacture.getText());
							int d=facture.Supprimer(numeroFacture);
							if(d==1) {
								JOptionPane.showMessageDialog(null, "La facture a été supprimée");
							}
							else {
								JOptionPane.showMessageDialog(null, "La facture  n'a pas été supprimée! Veuillez reessayez");
							}
						}
						catch(NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Le numéro de la facture se compose des entiers seulement !","Erreur lors du suppression",JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						
					}
				}
				autoDate();
				autoId();
				textMontant.setText("");
				textCodeContrat.setText("");
				button.setEnabled(true);
				button_1.doClick();
			}
		});
		button_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_3.setBounds(835, 471, 171, 46);
		button_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_3);
		
		button_4 = new JButton("");
		button_4.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnRechercher.png")).getImage()));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int numeroFacture=Integer.parseInt(JOptionPane.showInputDialog(null, "Veuillez saisir le numéro de la facture", "recherche d'informations sur la facture", JOptionPane.QUESTION_MESSAGE));
					if(numeroFacture!=0) {
						ResultSet rs=facture.Rechercher(numeroFacture);
						if(rs!=null ) {
							table.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else {
							JOptionPane.showMessageDialog(null, "Aucune facture n'existe avec ce numéro : "+numeroFacture);
						}
					}
					else {
						System.out.println("tache annulée");
					}
			}catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "le numéro de la facture se compose des entiers seulement !","Erreur lors du recherche",JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
				autoDate();
				autoId();
				textMontant.setText("");
				textCodeContrat.setText("");
				button.setEnabled(true);
			}
		});
		button_4.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_4.setBounds(616, 471, 183, 46);
		button_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_4);
		
		button = new JButton("");
		button.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAjouter.png")).getImage()));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textCodeContrat.getText().length()==0 || textNumeroFacture.getText().length()==0 || textDateFacture.equals("") || textMontant.getText().length()==0) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
					else {
						java.sql.Date dateFacture = new java.sql.Date(textDateFacture.getDate().getTime());
						int numeroContrat=Integer.parseInt(textCodeContrat.getText());
						double montant=Double.parseDouble(textMontant.getText());
						int numeroFacture=Integer.parseInt(textNumeroFacture.getText());
						int m=contrat.RechercherParNumeroContrat(numeroContrat);
						if(m==0) {
							JOptionPane.showMessageDialog(null, "Cette contrat n'existe pas dans notre base de données", "Erreur d'insertion", JOptionPane.OK_OPTION);
						}
						else {
							String codeClient=contrat.RetournerCodeClient(numeroContrat);
							if(codeClient==null) {
								JOptionPane.showMessageDialog(null, "Ce contrat n'est associé à aucun client veuillez vérifier vos donnes", "Erreur d'insertion", JOptionPane.OK_OPTION);
							}
							else {
								int d=facture.Ajouter(numeroFacture, dateFacture, montant,numeroContrat,codeClient);
								if(d==1) {
									JOptionPane.showMessageDialog(null, "La facture a été ajoutée");
								}
								else {
									JOptionPane.showMessageDialog(null, "La facture n'a pas été ajoutée! Veuillez reessayez");
								}
							}
						}
					}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro du la facture et le numéro du contrat se compose des entiers seulement !","Erreur lors du saisie",JOptionPane.ERROR_MESSAGE);
				}
				autoDate();
				autoId();
				textMontant.setText("");
				textCodeContrat.setText("");
				button_1.doClick();
			}
		});
		button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button.setBounds(62, 471, 137, 46);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button);
		
		button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnModifier.png")).getImage()));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textCodeContrat.getText().length()==0 || textNumeroFacture.getText().length()==0 || textDateFacture.equals("") || textMontant.getText().length()==0) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur de modification", JOptionPane.OK_OPTION);
					}
					else {
						java.sql.Date dateFacture = new java.sql.Date(textDateFacture.getDate().getTime());
						int numeroContrat=Integer.parseInt(textCodeContrat.getText());
						double montant=Double.parseDouble(textMontant.getText());
						int numeroFacture=Integer.parseInt(textNumeroFacture.getText());
						int m=contrat.RechercherParNumeroContrat(numeroContrat);
						if(m==0) {
							JOptionPane.showMessageDialog(null, "Ce contrat n'existe pas dans notre base de données", "Erreur de modification", JOptionPane.OK_OPTION);
						}
						else {
							String codeClient=contrat.RetournerCodeClient(numeroContrat);
							if(codeClient==null) {
								JOptionPane.showMessageDialog(null, "Ce contrat n'est associé a aucun client, veuillez verifiez vos données", "Erreur d'insertion", JOptionPane.OK_OPTION);
							}
							else {
								int d=facture.Modifier(numeroFacture, dateFacture, montant,numeroContrat,codeClient);
								if(d==1) {
									JOptionPane.showMessageDialog(null, "La facture a ete modifiée");
								}
								else {
									JOptionPane.showMessageDialog(null, "La facture n'a pas été modifiée! Veuillez reessayez");
								}
							}
						}
					}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro du la facture et le numéro du contrat se compose des entiers seulement !","Erreur lors du saisie",JOptionPane.ERROR_MESSAGE);
				}
				autoDate();
				autoId();
				textCodeContrat.setText("");
				textMontant.setText("");
				button.setEnabled(true);
				button_1.doClick();
			}
		});
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(235, 471, 152, 46);
		button_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_2);
		
		textCodeContrat = new JTextField();
		textCodeContrat.setBorder(null);
		textCodeContrat.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCodeContrat.setBackground(Color.decode("#e6ebee"));
		textCodeContrat.setColumns(10);
		textCodeContrat.setBounds(68, 98, 234, 32);
		contentPane.add(textCodeContrat);
		
		textMontant = new JTextField();
		textMontant.setBorder(null);
		textMontant.setFont(new Font("Verdana", Font.PLAIN, 16));
		textMontant.setBackground(Color.decode("#e6ebee"));
		textMontant.setColumns(10);
		textMontant.setBounds(68, 306, 234, 32);
		contentPane.add(textMontant);
		
		textDateFacture = new JDateChooser();
		((JTextField) textDateFacture.getDateEditor().getUiComponent()).setBackground(Color.decode("#e6ebee"));
		((JTextField) textDateFacture.getDateEditor().getUiComponent()).setBorder(null);
		((JTextField) textDateFacture.getDateEditor().getUiComponent()).setFont(new Font("Verdana", Font.PLAIN, 16));
		textDateFacture.setDateFormatString("yyyy/MM/dd");
		textDateFacture.setBounds(68, 236, 234, 32);
		contentPane.add(textDateFacture);
		
		textNumeroFacture = new JTextField();
		textNumeroFacture.setBorder(null);
		textNumeroFacture.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNumeroFacture.setBackground(Color.decode("#e6ebee"));
		textNumeroFacture.setColumns(10);
		textNumeroFacture.setBounds(68, 167, 234, 32);
		contentPane.add(textNumeroFacture);
		
		JLabel gestionFactures = new JLabel("New label");
		gestionFactures.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/gestionDesFactures.png")).getImage()));
		gestionFactures.setBounds(0, 0, 1234, 549);
		contentPane.add(gestionFactures);
		autoId();
		autoDate();
	}
}
