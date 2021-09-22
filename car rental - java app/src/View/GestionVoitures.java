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
import com.toedter.calendar.JDateChooser;

import BusinessObjects.Client;
import BusinessObjects.Parking;
import BusinessObjects.Voiture;
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

public class GestionVoitures extends JFrame {

	/**
	 * 
	 */
	Voiture voiture=new Voiture();
	Client client=new Client();
	Parking parking=new Parking();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNumeroImmatriculation;
	private JTextField textMarque;
	private JTextField textType;
	private JTextField textCarburant;
	private JTextField textCompteurEnKm;
	private JDateChooser textDateMiseEnCirculation;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton btnSupprimer;
	private JTable table;
	private JButton btnLouer;
	private JButton btnRestituer;
	private JLabel btnPrevious;
	private JFrame frame;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionVoitures frame = new GestionVoitures();
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
	public GestionVoitures() {
		frame=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textDateMiseEnCirculation = new JDateChooser();
		((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setBackground(Color.decode("#e6ebee"));
		((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setBorder(null);
		((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setColumns(10);
		((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setFont(new Font("Verdana", Font.PLAIN, 16));
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnNewButton.setBounds(68, 498, 89, 23);
		contentPane.add(btnNewButton);
		
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
			public void mouseClicked(MouseEvent e) {
				int ligne=table.getSelectedRow();
				textNumeroImmatriculation.setText(table.getModel().getValueAt(ligne, 0).toString());
				textMarque.setText(table.getModel().getValueAt(ligne, 1).toString());
				textDateMiseEnCirculation.setDate(((java.sql.Date) table.getModel().getValueAt(ligne, 5)));
				textType.setText(table.getModel().getValueAt(ligne, 2).toString());
				textCarburant.setText(table.getModel().getValueAt(ligne, 3).toString());
				textCompteurEnKm.setText(table.getModel().getValueAt(ligne, 4).toString());
				button.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		
		btnLouer = new JButton("");
		btnLouer.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnLouer.png")).getImage()));
		btnLouer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNumeroImmatriculation.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro d'immatriculation", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
				else {
					String codeClient=JOptionPane.showInputDialog(null, "<html>Veuillez saisir le code du client qui va louer cette voiture :<br><br></html>", "Confirmation",JOptionPane.QUESTION_MESSAGE);
					if(codeClient!=null) {
						int d=client.RechercherParCodeClient(codeClient, "");
						if(d==1) {
							try {
								int f=client.ChercherContratParClient(codeClient, "");
								if(f==1) {
									int numeroImmatriculation=Integer.parseInt(textNumeroImmatriculation.getText());
									boolean voitureRestituer=voiture.RestituerOuNon(numeroImmatriculation);
									if(voitureRestituer==true) {
										int m=voiture.LouerVoiture(numeroImmatriculation,codeClient,"");
										if(m==1) {
											JOptionPane.showMessageDialog(null, "la voiture a été loueé par le client ayant le code :"+codeClient);
										}
										else {
											JOptionPane.showMessageDialog(null, "Voiture n'a pas été loué! Veuillez réessayer");
										}
									}
									else {
										JOptionPane.showMessageDialog(null, "Cette voiture est deja loué ! Veuillez la restituer d'abord");
									}
								}
								else {
									JOptionPane.showMessageDialog(null, "Ce client n'a pas effectué aucune contrat ! Veuillez effectuer une pour pouvoir louer la voiture");
								}
							}
							catch(NumberFormatException e1) {
								JOptionPane.showMessageDialog(null, "le numéro d'immatriculation  se compose des entiers seulement !","Erreur lors du location",JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Ce client n'existe pas dans notre base de données", "Erreur de location", JOptionPane.OK_OPTION);
						}
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Veuillez saisir un code de client Valide", "Erreur de location", JOptionPane.OK_OPTION);
					}
				}
				textType.setText("");
				textMarque.setText("");
				textNumeroImmatriculation.setText("");
				textCarburant.setText("");
				textCompteurEnKm.setText("");
				button_1.doClick();
				((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setText("");
				button.setEnabled(true);
			}
		});
		btnLouer.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnLouer.setBounds(623, 475, 92, 46);
		contentPane.add(btnLouer);
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnRechercher.png")).getImage()));
		button_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int numeroImmatriculation=Integer.parseInt(JOptionPane.showInputDialog(null, "Veuillez saisir le numéro d'immatriculation", "recherche d'informations sur la voiture", JOptionPane.QUESTION_MESSAGE));
					if(numeroImmatriculation!=0) {
						ResultSet rs=voiture.RechercherVoiture(numeroImmatriculation);
						if(rs!=null ) {
							table.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else {
							JOptionPane.showMessageDialog(null, "Aucune voiture n'existe avec cette immatriculation:"+numeroImmatriculation);
						}
					}
					else {
						System.out.println("tache annulée");
					}
			}catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "le numéro d'immatriculation se compose des entiers seulement !","Erreur lors de la recherche",JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
				textType.setText("");
				textMarque.setText("");
				textNumeroImmatriculation.setText("");
				textCarburant.setText("");
				textCompteurEnKm.setText("");
				((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setText("");
				button.setEnabled(true);
			}
		});
		button_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_3.setBounds(997, 413, 183, 46);
		contentPane.add(button_3);
		
		button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnModifier.png")).getImage()));
		button_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textNumeroImmatriculation.getText().length()==0 || textMarque.getText().length()==0 || textDateMiseEnCirculation.equals("") || 
							textType.getText().length()==0 || textCarburant.getText().length()==0 || textCompteurEnKm.getText().length()==0) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur de modification", JOptionPane.OK_OPTION);
					}
					else {
						java.sql.Date dateMiseEnCirculation = new java.sql.Date(textDateMiseEnCirculation.getDate().getTime());
						int numeroImmatriculation=Integer.parseInt(textNumeroImmatriculation.getText());
						String marque=textMarque.getText();
						String type=textType.getText();
						String carburant=textCarburant.getText();
						int compteurEnKm=Integer.parseInt(textCompteurEnKm.getText());
						int d=voiture.ModifierVoiture(numeroImmatriculation, marque, type,carburant,compteurEnKm,dateMiseEnCirculation);
						if(d==1) {
							JOptionPane.showMessageDialog(null, "la voiture a été modifié");
						}
						else {
							JOptionPane.showMessageDialog(null, "Voiture n'a pas été modifié! Veuillez reessayez");
						}
					}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro de facture et numéro de contrat se compose des entiers seulement !","Erreur lors du saisie",JOptionPane.ERROR_MESSAGE);
				}
				textType.setText("");
				textMarque.setText("");
				textNumeroImmatriculation.setText("");
				textCarburant.setText("");
				textCompteurEnKm.setText("");
				((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setText("");
				button_1.doClick();
				button.setEnabled(true);
			}
		});
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(586, 413, 152, 46);
		contentPane.add(button_2);
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficher.png")).getImage()));
		button_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs;
				try {
					rs = voiture.ListerVoiture();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textType.setText("");
				textMarque.setText("");
				textNumeroImmatriculation.setText("");
				textCarburant.setText("");
				textCompteurEnKm.setText("");
				((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setText("");
				button.setEnabled(true);
			}
		});
		button_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_1.setBounds(789, 413, 157, 46);
		contentPane.add(button_1);
		
		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Pour savoir exactement le menu précédent !!
				
				if(frame.getTitle()=="Gestion des véhicules ") {
					Menu previous = new Menu();
					previous.setTitle("Menu - Utilisateur");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}else if(frame.getTitle()=="Gestion des véhicules  ") {
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
		
		btnSupprimer = new JButton("");
		btnSupprimer.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSupprimer.png")).getImage()));
		btnSupprimer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNumeroImmatriculation.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro d'immatriculation", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
				else {
					int choixSupprim=JOptionPane.showConfirmDialog(null, "<html>Êtes-vous sûr de supprimer cette voiture?<br><br></html>", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(choixSupprim==0) {
						try {
							int numeroImmatriculation=Integer.parseInt(textNumeroImmatriculation.getText());
							int d=voiture.SupprimerVoiture(numeroImmatriculation);
							if(d==1) {
								JOptionPane.showMessageDialog(null, "la voiture a été supprimé");
							}
							else {
								JOptionPane.showMessageDialog(null, "Voiture n'a pas été supprimé! Veuillez reessayez");
							}
						}
						catch(NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "le numéro d'immatriculation se compose des entiers seulement !","Erreur lors du suppression",JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						
					}
				}
				textType.setText("");
				textMarque.setText("");
				textNumeroImmatriculation.setText("");
				textCarburant.setText("");
				textCompteurEnKm.setText("");
				button_1.doClick();
				((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setText("");
				button.setEnabled(true);
			}
		});
		btnSupprimer.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnSupprimer.setBounds(412, 475, 171, 46);
		contentPane.add(btnSupprimer);
		
		btnRestituer = new JButton("");
		btnRestituer.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnRestituer.png")).getImage()));
		btnRestituer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRestituer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNumeroImmatriculation.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro d'immatriculation", "Erreur de restitution", JOptionPane.OK_OPTION);
				}
				else {
					try {
						int numeroImmatriculation=Integer.parseInt(textNumeroImmatriculation.getText());
						if(numeroImmatriculation!=0) {
							//boolean m=voiture.LouerOuNon(numeroImmatriculation);
							//if(m==true) {
								int d=voiture.Restituer(numeroImmatriculation);
								if(d==1 ) {
									JOptionPane.showMessageDialog(null, "La voiture :"+numeroImmatriculation+"a été restitué");
								}
								else {
									JOptionPane.showMessageDialog(null, "<html>Erreur lors de restition</html>");
								}
							//}
							//else {
						//		JOptionPane.showMessageDialog(null, "Cette voiture est déjà restituée", "Erreur de restitution", JOptionPane.OK_OPTION);
							//}
						}
						else {
							JOptionPane.showMessageDialog(null, "Cette voiture n'existe pas dans notre base de données ! veuillez réessayer", "Erreur de deposition", JOptionPane.OK_OPTION);
						}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro d'immatriculation et numéro de Parking se compose des entiers seulement !","Erreur lors du recherche",JOptionPane.ERROR_MESSAGE);
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				}
				textType.setText("");
				textMarque.setText("");
				textNumeroImmatriculation.setText("");
				textCarburant.setText("");
				textCompteurEnKm.setText("");
				((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setText("");
				button.setEnabled(true);
				button_1.doClick();
			}

		});
		btnRestituer.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnRestituer.setBounds(755, 475, 146, 46);
		contentPane.add(btnRestituer);
		
		button = new JButton("");
		button.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAjouter.png")).getImage()));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(textNumeroImmatriculation.getText().length()==0 || textMarque.getText().length()==0 || textDateMiseEnCirculation.equals("") || 
							textType.getText().length()==0 || textCarburant.getText().length()==0 || textCompteurEnKm.getText().length()==0) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
					else {
						java.sql.Date dateMiseEnCirculation = new java.sql.Date(textDateMiseEnCirculation.getDate().getTime());
						int numeroImmatriculation=Integer.parseInt(textNumeroImmatriculation.getText());
						String marque=textMarque.getText();
						String type=textType.getText();
						String carburant=textCarburant.getText();
						int compteurEnKm=Integer.parseInt(textCompteurEnKm.getText());
						int d=voiture.AjouterVoiture(numeroImmatriculation, marque, type,carburant,compteurEnKm,dateMiseEnCirculation);
						if(d==1) {
							JOptionPane.showMessageDialog(null, "la voiture a été ajouté");
						}
						else {
							JOptionPane.showMessageDialog(null, "Voiture n'a pas été ajouté! Veuillez reessayez");
						}
					}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro de facture et numéro de contrat se compose des entiers seulement !","Erreur lors du saisie",JOptionPane.ERROR_MESSAGE);
				}
				textType.setText("");
				textMarque.setText("");
				textNumeroImmatriculation.setText("");
				textCarburant.setText("");
				textCompteurEnKm.setText("");
				((JTextField) textDateMiseEnCirculation.getDateEditor().getUiComponent()).setText("");
				button_1.doClick();
			}
		});
		button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button.setBounds(412, 413, 132, 46);
		contentPane.add(button);
		textDateMiseEnCirculation.setDateFormatString("yyyy/MM/dd");
		textDateMiseEnCirculation.setBounds(68, 445, 234, 32);
		contentPane.add(textDateMiseEnCirculation);
		
		textCompteurEnKm = new JTextField();
		textCompteurEnKm.setBorder(null);
		textCompteurEnKm.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCompteurEnKm.setBackground(Color.decode("#e6ebee"));
		textCompteurEnKm.setColumns(10);
		textCompteurEnKm.setBounds(68, 376, 234, 32);
		contentPane.add(textCompteurEnKm);
		
		textCarburant = new JTextField();
		textCarburant.setBorder(null);
		textCarburant.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCarburant.setBackground(Color.decode("#e6ebee"));
		textCarburant.setColumns(10);
		textCarburant.setBounds(68, 306, 234, 32);
		contentPane.add(textCarburant);
		
		textNumeroImmatriculation = new JTextField();
		textNumeroImmatriculation.setBorder(null);
		textNumeroImmatriculation.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNumeroImmatriculation.setBackground(Color.decode("#e6ebee"));
		textNumeroImmatriculation.setColumns(10);
		textNumeroImmatriculation.setBounds(68, 98, 234, 32);
		contentPane.add(textNumeroImmatriculation);
		
		textMarque = new JTextField();
		textMarque.setBorder(null);
		textMarque.setFont(new Font("Verdana", Font.PLAIN, 16));
		textMarque.setBackground(Color.decode("#e6ebee"));
		textMarque.setColumns(10);
		textMarque.setBounds(68, 167, 234, 32);
		contentPane.add(textMarque);
		
		textType = new JTextField();
		textType.setBorder(null);
		textType.setFont(new Font("Verdana", Font.PLAIN, 16));
		textType.setBackground(Color.decode("#e6ebee"));
		textType.setColumns(10);
		textType.setBounds(68, 236, 234, 32);
		contentPane.add(textType);
		
		JLabel gestionVoitures = new JLabel("New label");
		gestionVoitures.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/MenuGestionVoitures.png")).getImage()));
		gestionVoitures.setBounds(0, 0, 1234, 549);
		contentPane.add(gestionVoitures);
	}
}
