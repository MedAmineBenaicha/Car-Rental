package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BusinessObjects.Parking;
import BusinessObjects.Voiture;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class GestionParkings extends JFrame {

	/**
	 * 
	 */
	Parking parking=new Parking();
	Voiture voiture=new Voiture();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNumeroParking;
	private JTextField textCapacite;
	private JTextField textRue;
	private JTextField textArrondissement;
	private JTable table;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton btnSupprimer;
	private JButton btnDeposer;
	private JButton btnSortir;
	private JLabel gestionParkings;
	private JLabel btnPrevious;
	private JFrame frame;
	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionParkings frame = new GestionParkings();
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
		long id=parking.autoId();
		if(id==0) {
			textNumeroParking.setText("1");
		}
		else {
			textNumeroParking.setText(""+id);
		}
	}
	public GestionParkings() {
		frame=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSupprimer = new JButton("");
		btnSupprimer.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSupprimer.png")).getImage()));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNumeroParking.getText().length()==0 || textCapacite.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro de parking et sa capacité", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
				else {
					int choixSupprim=JOptionPane.showConfirmDialog(null, "<html>Êtes-vous sûr de supprimer ce parking ?<br><br></html>", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(choixSupprim==0) {
						try {
							int numeroParking=Integer.parseInt(textNumeroParking.getText());
							int capacite=Integer.parseInt(textCapacite.getText());
							int d=parking.SupprimerParking(numeroParking,capacite);
							if(d==1) {
								JOptionPane.showMessageDialog(null, "Le parking a été supprimé");
							}
							else {
								JOptionPane.showMessageDialog(null, "<html>Ce parking ne peut pas être supprimé<br><br>Veuillez vérifier si ce parking contient des voitures<br><br>si oui veuillez les supprimer ou les déplacer d'abord !</html>");
							}
						}
						catch(NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "le numéro d'immatriculation et la capacité se compose des entiers seulement !","Erreur lors du suppression",JOptionPane.ERROR_MESSAGE);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						
					}
				}
				autoId();
				textCapacite.setText("");
				textRue.setText("");
				textArrondissement.setText("");
				button_1.doClick();
				button.setEnabled(true);
			}
		});
		btnSupprimer.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnSupprimer.setBounds(835, 471, 171, 46);
		btnSupprimer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnSupprimer);
		
		
		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(frame.getTitle()== "Gestion des parkings ") {
					Menu previous = new Menu();
					previous.setTitle("Menu - Utilisateur");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}else if(frame.getTitle()== "Gestion des parkings  ") {
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
		
		button = new JButton("");
		button.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAjouter.png")).getImage()));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(textNumeroParking.getText().length()==0 || textRue.getText().length()==0 || textCapacite.getText().length()==0 || 
							textArrondissement.getText().length()==0) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
					else {
						int numeroParking=Integer.parseInt(textNumeroParking.getText());
						int numeroCapacite=Integer.parseInt(textCapacite.getText());
						String rue=textRue.getText();
						String arrondissement=textArrondissement.getText();
						int d=parking.AjouterParking(numeroParking, numeroCapacite, rue,arrondissement);
						if(d==1) {
							JOptionPane.showMessageDialog(null, "Le parking a été ajouté");
						}
						else {
							JOptionPane.showMessageDialog(null, "Le parking n'a pas été ajouté! Veuillez reessayez");
						}
					}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro de parking et la capcite se compose des entiers seulement !","Erreur lors du saisie",JOptionPane.ERROR_MESSAGE);
				}
				autoId();
				textCapacite.setText("");
				textRue.setText("");
				textArrondissement.setText("");
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
					if(textNumeroParking.getText().length()==0 || textRue.getText().length()==0 || textCapacite.getText().length()==0 || 
							textArrondissement.getText().length()==0) {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur de modification", JOptionPane.OK_OPTION);
					}
					else {
						int numeroParking=Integer.parseInt(textNumeroParking.getText());
						int numeroCapacite=Integer.parseInt(textCapacite.getText());
						String rue=textRue.getText();
						String arrondissement=textArrondissement.getText();
						int d=parking.ModifierParking(numeroParking, numeroCapacite, rue,arrondissement);
						if(d==1) {
							JOptionPane.showMessageDialog(null, "Le parking a été modifié");
						}
						else {
							JOptionPane.showMessageDialog(null, "Le parking n'a pas été modifié! Veuillez reessayez");
						}
					}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro de parking et la capcite se compose des entiers seulement !","Erreur lors du saisie",JOptionPane.ERROR_MESSAGE);
				}
				autoId();
				textCapacite.setText("");
				textRue.setText("");
				textArrondissement.setText("");
				button_1.doClick();
				button.setEnabled(true);
			}
		});
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(235, 471, 152, 46);
		button_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_2);
		
		btnSortir = new JButton("");
		btnSortir.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSortir.png")).getImage()));
		btnSortir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSortir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNumeroParking.getText().length()==0 ) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro de parking et sa capacité", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
				else {
					try {
						int numeroParking=Integer.parseInt(textNumeroParking.getText());
						int numeroImatriculation=Integer.parseInt(JOptionPane.showInputDialog(null, "Veuillez saisir le numéro d'immatriculation de la voiture pour la déposer", "Deposer voiture", JOptionPane.QUESTION_MESSAGE));
						if(numeroImatriculation!=0) {
							int m=voiture.RechercherVoitureParCode(numeroImatriculation);
							int e1=parking.RechercherParNumeroParking(numeroParking);
							if(m==1 && e1==1) {
								int d=parking.SortirVoiture(numeroParking,numeroImatriculation);
								if(d!=0 ) {
									JOptionPane.showMessageDialog(null, "La voiture a été sortie du parking : "+numeroParking);
								}
								else {
									JOptionPane.showMessageDialog(null, "Cette voiture n'appartient pas à ce parking : "+numeroParking);
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Cette voiture ou le parking n'existe pas dans notre base de données ! veuillez réessayer", "Erreur de déposition", JOptionPane.OK_OPTION);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Cette voiture n'existe pas dans notre base de données ! veuillez reessayer", "Erreur de déposition", JOptionPane.OK_OPTION);
						}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro d'immatriculatuion et le numéro de Parking se compose des entiers seulement !","Erreur lors du recherche",JOptionPane.ERROR_MESSAGE);
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				}
				autoId();
				textCapacite.setText("");
				textRue.setText("");
				textArrondissement.setText("");
				button_1.doClick();
				button.setEnabled(true);
			}
		});
		btnSortir.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnSortir.setBounds(62, 408, 138, 46);
		contentPane.add(btnSortir);
		
		btnDeposer = new JButton("");
		btnDeposer.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnDeposer.png")).getImage()));
		btnDeposer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeposer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNumeroParking.getText().length()==0 ) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro de parking et sa capacité", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
				else {
					try {
						int numeroParking=Integer.parseInt(textNumeroParking.getText());
						int numeroImatriculation=Integer.parseInt(JOptionPane.showInputDialog(null, "Veuillez saisir le numéro d'immatriculation de la voiture pour la déposer", "Déposer voiture", JOptionPane.QUESTION_MESSAGE));
						if(numeroImatriculation!=0) {
							int m=voiture.RechercherVoitureParCode(numeroImatriculation);
							int e1=parking.RechercherParNumeroParking(numeroParking);
							if(m==1 && e1==1) {
								int d=parking.DeposerVoiture(numeroParking,numeroImatriculation);
								if(d!=0 ) {
									JOptionPane.showMessageDialog(null, "La voiture a été deposée dans le parking :"+numeroParking);
								}
								else {
									JOptionPane.showMessageDialog(null, "<html>Ce parking a atteint sa taille maximale<br><br>Cette voiture ne peut être pas depose!<br><br>Veuillez la déposer dans un autre</html>");
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Cette voiture ou le parking n'existe pas dans notre base de données ! veuillez réessayer", "Erreur de déposition", JOptionPane.OK_OPTION);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Cette voiture n'existe pas dans notre base de données ! veuillez reessayer", "Erreur de déposition", JOptionPane.OK_OPTION);
						}
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "le numéro d'immatriculatuion et le numéro de Parking se compose des entiers seulement !","Erreur lors du recherche",JOptionPane.ERROR_MESSAGE);
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				autoId();
				textCapacite.setText("");
				textRue.setText("");
				textArrondissement.setText("");
				button_1.doClick();
				button.setEnabled(true);
			}
		});
		btnDeposer.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnDeposer.setBounds(235, 408, 140, 46);
		contentPane.add(btnDeposer);
		
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
				textNumeroParking.setText(table.getModel().getValueAt(ligne, 0).toString());
				textCapacite.setText(table.getModel().getValueAt(ligne, 1).toString());
				textRue.setText(table.getModel().getValueAt(ligne, 2).toString());
				textArrondissement.setText(table.getModel().getValueAt(ligne, 3).toString());
				button.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnRechercher.png")).getImage()));		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int numeroParking=Integer.parseInt(JOptionPane.showInputDialog(null, "Veuillez saisir le numéro de parking", "recherche d'informations sur le parking", JOptionPane.QUESTION_MESSAGE));
					if(numeroParking!=0) {
						ResultSet rs=parking.RechercherParking(numeroParking);
						if(rs!=null ) {
							table.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else {
							JOptionPane.showMessageDialog(null, "Aucun Parking n'existe avec ce numéro : "+numeroParking);
						}
					}
					else {
						System.out.println("tache annulée");
					}
			}catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "le numéro de parking se compose des entiers seulement !","Erreur lors du recherche",JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				autoId();
				textCapacite.setText("");
				textRue.setText("");
				textArrondissement.setText("");
				button.setEnabled(true);
			}
		});
		button_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_3.setBounds(616, 471, 183, 46);
		button_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_3);
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficher.png")).getImage()));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs;
				try {
					rs = parking.Lister();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				autoId();
				textCapacite.setText("");
				textRue.setText("");
				textArrondissement.setText("");
				button.setEnabled(true);
			}
		});
		button_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_1.setBounds(423, 471, 157, 46);
		button_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(button_1);
		
		textNumeroParking = new JTextField();
		textNumeroParking.setBorder(null);
		textNumeroParking.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNumeroParking.setBackground(Color.decode("#e6ebee"));
		textNumeroParking.setColumns(10);
		textNumeroParking.setBounds(68, 98, 234, 32);
		contentPane.add(textNumeroParking);
		
		textCapacite = new JTextField();
		textCapacite.setBorder(null);
		textCapacite.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCapacite.setBackground(Color.decode("#e6ebee"));
		textCapacite.setColumns(10);
		textCapacite.setBounds(68, 167, 234, 32);
		contentPane.add(textCapacite);
		
		textArrondissement = new JTextField();
		textArrondissement.setBorder(null);
		textArrondissement.setFont(new Font("Verdana", Font.PLAIN, 16));
		textArrondissement.setBackground(Color.decode("#e6ebee"));
		textArrondissement.setColumns(10);
		textArrondissement.setBounds(68, 306, 234, 32);
		contentPane.add(textArrondissement);
		
		textRue = new JTextField();
		textRue.setBorder(null);
		textRue.setFont(new Font("Verdana", Font.PLAIN, 16));
		textRue.setBackground(Color.decode("#e6ebee"));
		textRue.setColumns(10);
		textRue.setBounds(68, 236, 234, 32);
		contentPane.add(textRue);
		
		gestionParkings = new JLabel("New label");
		gestionParkings.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/MenuGestionParkings.png")).getImage()));
		gestionParkings.setBounds(0, 0, 1234, 549);
		contentPane.add(gestionParkings);
	}
}
