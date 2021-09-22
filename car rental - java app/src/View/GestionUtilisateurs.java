package View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import BusinessObjects.Utilisateur;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class GestionUtilisateurs extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	Utilisateur utilisateur=new Utilisateur();
	private JPanel contentPane;
	private JTextField textCodeUtilisateur;
	private JTextField textNomComplet;
	private JTextField textAdresse;
	private JTextField textCin;
	private JTextField textNumTel;
	private JTextField textMdp;
	private JTable table;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton btnSupprimer;
	private JLabel  btnPrevious;
	private JButton btnSuspendre;
	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionUtilisateurs frame = new GestionUtilisateurs();
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
	public void autoId() throws SQLException {
		long id=utilisateur.autoID();
		if(id==0) {
			textCodeUtilisateur.setText("A0001");
		}
		else {
			textCodeUtilisateur.setText("A0" + String.format("%03d", id));
		}
	}
	public GestionUtilisateurs() throws SQLException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 588);
		setTitle("Gestion des utilisateurs");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MenuAdmin previous = new MenuAdmin();
				previous.setTitle("Menu - Admin");
				previous.setVisible(true);
				previous.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnPrevious.setBounds(43, 3, 46, 49);
		btnPrevious.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnPrevious);
		
		btnSupprimer = new JButton("");
		btnSupprimer.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSupprimer.png")).getImage()));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textNomComplet.getText().length()!=0 && textCodeUtilisateur.getText().length()!=0 && textMdp.getText().length()!=0) {
					String nomComplet=textNomComplet.getText();
					String codeUtilisateur=textCodeUtilisateur.getText();
					String motDePasse=textMdp.getText();
					int choixSupprim=JOptionPane.showConfirmDialog(null, "Êtes-vous sur ?", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(choixSupprim==0) {
						try {
							int d=utilisateur.Supprimer(codeUtilisateur, nomComplet,motDePasse);
							if(d!=0) {
								JOptionPane.showMessageDialog(null, "Suppression effectuée","Suppression d'utilisateur", JOptionPane.INFORMATION_MESSAGE);
							}
							else {
				    			JOptionPane.showMessageDialog(null, "Erreur dans la base de données","Erreur de suppression", JOptionPane.INFORMATION_MESSAGE);
							}
							autoId();
							textNomComplet.setText("");
							textAdresse.setText("");
							textCin.setText("");
							textNumTel.setText("");
							textMdp.setText("");
							button.setEnabled(true);
							button_1.doClick();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						
					}	
				}
				else {
					JOptionPane.showMessageDialog(null, "Veuillez remplir les cases : nom Complet et code Utilisateur ", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
			}
		});
		btnSupprimer.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnSupprimer.setBounds(412, 475, 171, 46);
		
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
				textNomComplet.setText(table.getModel().getValueAt(ligne, 0).toString());
				textCodeUtilisateur.setText(table.getModel().getValueAt(ligne, 1).toString());
				textAdresse.setText(table.getModel().getValueAt(ligne, 2).toString());
				textCin.setText(table.getModel().getValueAt(ligne, 3).toString());
				textNumTel.setText(table.getModel().getValueAt(ligne, 4).toString());
				textMdp.setText(table.getModel().getValueAt(ligne, 5).toString());
				button.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		contentPane.add(btnSupprimer);
		
		button = new JButton("");
		button.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAjouter.png")).getImage()));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(textNomComplet.getText().length()!=0 && textCodeUtilisateur.getText().length()!=0 && 
							textAdresse.getText().length()!=0 && textCin.getText().length()!=0 
							&& textNumTel.getText().length()!=0 && textMdp.getText().length()!=0) {
						String nomComplet=textNomComplet.getText();
						String codeUtilisateur=textCodeUtilisateur.getText();
						String adresse=textAdresse.getText();
						String cin=textCin.getText();
						String mdp=textMdp.getText();
						int numTel=Integer.parseInt(textNumTel.getText());
						try {
							int d=utilisateur.Ajouter(nomComplet, codeUtilisateur, adresse, cin, numTel, mdp);
							if(d!=0) {
								JOptionPane.showMessageDialog(null, "L'utilisateur a été ajouté");
							}
							else {
								JOptionPane.showMessageDialog(null, "L'utilisateur n'a pas été ajouté! Veuillez reessayez");
							}
							textNomComplet.setText("");
							autoId();
							textAdresse.setText("");
							textCin.setText("");
							textNumTel.setText("");
							textMdp.setText("");
							button_1.doClick();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
				}
				catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null,"Le numéro de telephone se compose des entiers seulement.");
				}
			}
		});
		button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button.setBounds(412, 413, 132, 46);
		contentPane.add(button);
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficher.png")).getImage()));
		button_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_1.setBounds(789, 413, 157, 46);
		contentPane.add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultSet rs;
				try {
					rs = utilisateur.Lister();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textNomComplet.setText("");
				try {
					autoId();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textAdresse.setText("");
				textCin.setText("");
				textNumTel.setText("");
				textMdp.setText("");
				button.setEnabled(true);
			}
		});
		
		btnSuspendre = new JButton("");
		btnSuspendre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSuspendre.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSuspendre.png")).getImage()));
		btnSuspendre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(textCodeUtilisateur.getText().length()!=0) {
						String codeUtilisateur=textCodeUtilisateur.getText();
						int suspention = utilisateur.getStatut(codeUtilisateur);
						if(suspention == 0) {
							try {
								int d=utilisateur.Suspendre(codeUtilisateur);
								if(d==1) {
									JOptionPane.showMessageDialog(null, "Suspension effectuée","Suspension des champs", JOptionPane.INFORMATION_MESSAGE);
								}
								else {
					    			JOptionPane.showMessageDialog(null, "Erreur dans la base de données","Erreur de Suspension", JOptionPane.INFORMATION_MESSAGE);
								}
								autoId();
								textNomComplet.setText("");
								textAdresse.setText("");
								textCin.setText("");
								textNumTel.setText("");
								textMdp.setText("");
								button.setEnabled(true);
								button_1.doClick();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}else if(suspention == 2) {
								int choixSuspention=JOptionPane.showConfirmDialog(null, "<html>Ce client est déjà suspendu<br><br>voulez vous le restituer ?</html>", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
								if(choixSuspention == 0) {
									int m = utilisateur.Restituer(codeUtilisateur);
									if(m==1) {
										JOptionPane.showMessageDialog(null, "Restituation effectuée","Restituation des champs", JOptionPane.INFORMATION_MESSAGE);
										autoId();
										textNomComplet.setText("");
										textAdresse.setText("");
										textCin.setText("");
										textNumTel.setText("");
										textMdp.setText("");
										button.setEnabled(true);
										button_1.doClick();
									}
									else {
						    			JOptionPane.showMessageDialog(null, "Erreur dans la base de données","Erreur de Restituation", JOptionPane.INFORMATION_MESSAGE);
									}
								}else {
									//nothing to do
								}
							}
						}	
					else {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur de suspension", JOptionPane.OK_OPTION);
					}
				}
				catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null,"Le numéro de telephone se compose des entiers seulement.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textAdresse.setText("");
				textCin.setText("");
				textNumTel.setText("");
				textMdp.setText("");
				button.setEnabled(true);
			}
		});
		btnSuspendre.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnSuspendre.setBounds(618, 475, 169, 46);
		contentPane.add(btnSuspendre);
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnModifier.png")).getImage()));
		button_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_3.setBounds(586, 413, 152, 46);
		contentPane.add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(textNomComplet.getText().length()!=0 && textCodeUtilisateur.getText().length()!=0 && 
							textAdresse.getText().length()!=0 && textCin.getText().length()!=0 
							&& textNumTel.getText().length()!=0 && textMdp.getText().length()!=0) {
						String nomComplet=textNomComplet.getText();
						String codeUtilisateur=textCodeUtilisateur.getText();
						String adresse=textAdresse.getText();
						String cin=textCin.getText();
						String mdp=textMdp.getText();
						int numTel=Integer.parseInt(textNumTel.getText());
						try {
							int d=utilisateur.Modifier(codeUtilisateur, nomComplet, adresse, cin, numTel, mdp);
							if(d!=0) {
								JOptionPane.showMessageDialog(null, "Modification effectuée","Modification des champs", JOptionPane.INFORMATION_MESSAGE);
							}
							else {
				    			JOptionPane.showMessageDialog(null, "Erreur dans la base de donnes","Erreur de modification", JOptionPane.INFORMATION_MESSAGE);
							}
							autoId();
							textNomComplet.setText("");
							textAdresse.setText("");
							textCin.setText("");
							textNumTel.setText("");
							textMdp.setText("");
							button.setEnabled(true);
							button_1.doClick();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur d'insertion", JOptionPane.OK_OPTION);
					}
				}
				catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null,"Le numéro de telephone se compose des entiers seulement.");
				}
				textAdresse.setText("");
				textCin.setText("");
				textNumTel.setText("");
				textMdp.setText("");
				button.setEnabled(true);
			}
		});
		
		button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnRechercher.png")).getImage()));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomComplet=JOptionPane.showInputDialog(null, "Veuillez sasir le nom complet de l'utilisateur ", "recherche d'informations sur l'utilisateur", JOptionPane.QUESTION_MESSAGE);
				if(nomComplet!=null) {
					ResultSet rs=utilisateur.Rechercher(nomComplet);
					if(rs!=null) {
						table.setModel(DbUtils.resultSetToTableModel(rs));
					}
					else {
						JOptionPane.showMessageDialog(null, "Aucun utilisateur n'existe avec ce nom : "+nomComplet);
					}
				}
				else {
					System.out.println("tache annulée");
				}
			}
		});
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(997, 413, 183, 46);
		contentPane.add(button_2);
		
		textNomComplet = new JTextField();
		textNomComplet.setBorder(null);
		textNomComplet.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNomComplet.setBackground(Color.decode("#e6ebee"));
		textNomComplet.setColumns(10);
		textNomComplet.setBounds(68, 167, 234, 32);
		contentPane.add(textNomComplet);
		
		textCodeUtilisateur = new JTextField();
		textCodeUtilisateur.setBorder(null);
		textCodeUtilisateur.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCodeUtilisateur.setBackground(Color.decode("#e6ebee"));
		textCodeUtilisateur.setColumns(10);
		textCodeUtilisateur.setBounds(68, 98, 234, 32);
		contentPane.add(textCodeUtilisateur);
		
		textNumTel = new JTextField();
		textNumTel.setBorder(null);
		textNumTel.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNumTel.setBackground(Color.decode("#e6ebee"));
		textNumTel.setColumns(10);
		textNumTel.setBounds(68, 376, 234, 32);
		contentPane.add(textNumTel);
		
		textCin = new JTextField();
		textCin.setBorder(null);
		textCin.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCin.setBackground(Color.decode("#e6ebee"));
		textCin.setColumns(10);
		textCin.setBounds(68, 306, 234, 32);
		contentPane.add(textCin);
		
		textMdp = new JTextField();
		textMdp.setBorder(null);
		textMdp.setFont(new Font("Verdana", Font.PLAIN, 16));
		textMdp.setBackground(Color.decode("#e6ebee"));
		textMdp.setColumns(10);
		textMdp.setBounds(68, 445, 234, 32);
		contentPane.add(textMdp);
		
		textAdresse = new JTextField();
		textAdresse.setBorder(null);
		textAdresse.setFont(new Font("Verdana", Font.PLAIN, 16));
		textAdresse.setBackground(Color.decode("#e6ebee"));
		textAdresse.setColumns(10);
		textAdresse.setBounds(68, 236, 234, 32);
		contentPane.add(textAdresse);
		
		JLabel gestionUtilisateurs = new JLabel("New label");
		gestionUtilisateurs.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/MenuGestionUtilisateurs.png")).getImage()));
		gestionUtilisateurs.setBounds(0, 0, 1234, 549);
		contentPane.add(gestionUtilisateurs);
		autoId();
		setVisible(true);
	}
}
