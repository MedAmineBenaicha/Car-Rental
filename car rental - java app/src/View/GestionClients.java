package View;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import BusinessObjects.Client;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Dimension;

public class GestionClients extends JFrame {

	/**
	 * 
	 */
	Client client=new Client();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCodeClient;
	private JTextField textNomComplet;
	private JTextField textAdresse;
	private JTextField textCin;
	private JTextField textNumTel;
	private JTable table;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton btnModifier;
	private JLabel btnPrevious;
	private JButton btnBrowse;
	private JLabel imagePermis;
	private String s;
	private JFrame frame;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionClients frame = new GestionClients();
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
		long id=client.AutoId();
		if(id==0) {
			textCodeClient.setText("C0001");
		}
		else {
			textCodeClient.setText("C0" + String.format("%03d", id));
		}
	}
  public ImageIcon ResizeImage(String imgPath){
        ImageIcon MyImage = new ImageIcon(imgPath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(imagePermis.getWidth(), imagePermis.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
	public GestionClients() {
		frame=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1250, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		button = new JButton("");
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAjouter.png")).getImage()));
		button.setBorder(null);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(textNomComplet.getText().length()!=0 && textCodeClient.getText().length()!=0 && 
							textAdresse.getText().length()!=0 && textCin.getText().length()!=0 
							&& textNumTel.getText().length()!=0) {
						String nomComplet=textNomComplet.getText();
						String codeClient=textCodeClient.getText();
						String adresse=textAdresse.getText();
						String cin=textCin.getText();
						InputStream imagePermis = new FileInputStream(new File(s));
						int numTelephon=Integer.parseInt(textNumTel.getText());
						try {
							int d=client.AjouterClient(codeClient, nomComplet, adresse, cin, numTelephon,imagePermis);
							if(d==1) {
								JOptionPane.showMessageDialog(null, "le client a été ajouté");
							}
							else {
								JOptionPane.showMessageDialog(null, "Client n'a pas été ajouté! Veuillez reessayez");
							}
							autoId();
							textNomComplet.setText("");
							textAdresse.setText("");
							textCin.setText("");
							textNumTel.setText("");
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
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		button_2 = new JButton("");
		button_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnRechercher.png")).getImage()));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomComplet=JOptionPane.showInputDialog(null, "Veuillez saisir le nom complet du client ", "recherche d'informations sur le client", JOptionPane.QUESTION_MESSAGE);
				if(nomComplet!=null) {
					ResultSet rs=client.RechercherClient(nomComplet);
					if(rs!=null) {
						table.setModel(DbUtils.resultSetToTableModel(rs));
					}
					else {
						JOptionPane.showMessageDialog(null, "Aucun client n'existe avec ce nom : "+nomComplet);
					}
				}
				else {
					System.out.println("tache annulée");
				}
			}
		});
		
		button_1 = new JButton("");
		button_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficher.png")).getImage()));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs;
				try {
					rs = client.ListerClient();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				autoId();
				textNomComplet.setText("");
				textAdresse.setText("");
				textCin.setText("");
				textNumTel.setText("");
				button.setEnabled(true);
			}
		});
		
		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(frame.getTitle()== "Gestion des clients ") {
					Menu previous = new Menu();
					previous.setTitle("Menu - Utilisateur");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}else if(frame.getTitle()== "Gestion des clients  ") {
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
		
		btnBrowse = new JButton("");
		btnBrowse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBrowse.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnUpload.png")).getImage()));
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
		         fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		         FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
		         fileChooser.addChoosableFileFilter(filter);
		         int result = fileChooser.showSaveDialog(null);
		         if(result == JFileChooser.APPROVE_OPTION){
		             File selectedFile = fileChooser.getSelectedFile();
		             String path = selectedFile.getAbsolutePath();
		             imagePermis.setIcon(ResizeImage(path));
		             s = path;
		              }
		         else if(result == JFileChooser.CANCEL_OPTION){
		             System.out.println("Pas de données");
		         }
			}
		});
		
		
		btnBrowse.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnBrowse.setBorder(null);
		btnBrowse.setBounds(181, 475, 140, 46);
		contentPane.add(btnBrowse);
		
		imagePermis = new JLabel("");
		imagePermis.setBounds(47, 447, 105, 92);
		contentPane.add(imagePermis);
		btnPrevious.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPrevious.setBounds(42, 3, 46, 49);
		contentPane.add(btnPrevious);
		
		button_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_1.setBounds(789, 413, 157, 46);
		
		contentPane.add(button_1);
		
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(997, 413, 183, 46);
		contentPane.add(button_2);
		
		btnModifier = new JButton("");
		btnModifier.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModifier.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnSupprimer.png")).getImage()));
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNomComplet.getText().length()!=0 && textCodeClient.getText().length()!=0) {
					String nomComplet=textNomComplet.getText();
					String codeClient=textCodeClient.getText();
					int choixSupprim=JOptionPane.showConfirmDialog(null, "<html>Êtes-vous sûr de supprimer ce client ?<br><br>La suppression du client va supprimer tous ces <b>réservations</b><br><br>et tous ces contrats et factures ! </html>", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(choixSupprim==0) {
						try {
							int d=client.SupprimerClient(codeClient, nomComplet);
							if(d!=0) {
								JOptionPane.showMessageDialog(null, "Suppression effectuée","Suppression de client", JOptionPane.INFORMATION_MESSAGE);
							}
							else {
				    			JOptionPane.showMessageDialog(null, "Erreur dans la Base de données","Erreur de suppression", JOptionPane.INFORMATION_MESSAGE);
							}
							autoId();
							textNomComplet.setText("");
							textAdresse.setText("");
							textCin.setText("");
							textNumTel.setText("");
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
					JOptionPane.showMessageDialog(null, "Veuillez remplir les cases : nom Complet et code Client ", "Erreur de suppression", JOptionPane.OK_OPTION);
				}
			}
		});
		btnModifier.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		btnModifier.setBounds(412, 475, 171, 46);
		contentPane.add(btnModifier);
		
		button_3 = new JButton("");
		button_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnModifier.png")).getImage()));
		button_3.setBackground(new Color(240, 240, 240));
		button_3.setBorder(null);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textNomComplet.getText().length()!=0 && textCodeClient.getText().length()!=0 && 
							textAdresse.getText().length()!=0 && textCin.getText().length()!=0 
							&& textNumTel.getText().length()!=0) {
						String nomComplet=textNomComplet.getText();
						String codeClient=textCodeClient.getText();
						String adresse=textAdresse.getText();
						String cin=textCin.getText();
						int numTelephon=Integer.parseInt(textNumTel.getText());
						try {
							int d=client.ModifierClient(codeClient, nomComplet, adresse, cin, numTelephon);
							if(d==1) {
								JOptionPane.showMessageDialog(null, "Modification effectuée","Modification des champs", JOptionPane.INFORMATION_MESSAGE);
							}
							else if(d==0){
				    			JOptionPane.showMessageDialog(null, "Erreur dans la Base de données","Erreur de modification", JOptionPane.INFORMATION_MESSAGE);
							}
							autoId();
							textNomComplet.setText("");
							textAdresse.setText("");
							textCin.setText("");
							textNumTel.setText("");
							button.setEnabled(true);
							button_1.doClick();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases", "Erreur de modification", JOptionPane.OK_OPTION);
					}
				}
				catch(NumberFormatException e1) {
						JOptionPane.showMessageDialog(null,"Le numéro de telephone se compose des entiers seulement.");
				}
			}
		});
		button_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_3.setBounds(586, 413, 152, 46);
		contentPane.add(button_3);
		
		
		button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button.setBounds(412, 413, 137, 46);
		contentPane.add(button);
		
		textNomComplet = new JTextField();
		textNomComplet.setBorder(null);
		textNomComplet.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNomComplet.setBackground(Color.decode("#e6ebee"));
		textNomComplet.setColumns(10);
		textNomComplet.setBounds(68, 167, 234, 32);
		contentPane.add(textNomComplet);
		
		textAdresse = new JTextField();
		textAdresse.setBorder(null);
		textAdresse.setFont(new Font("Verdana", Font.PLAIN, 16));
		textAdresse.setBackground(Color.decode("#e6ebee"));
		textAdresse.setColumns(10);
		textAdresse.setBounds(68, 236, 234, 32);
		contentPane.add(textAdresse);
		
		textCin = new JTextField();
		textCin.setBorder(null);
		textCin.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCin.setBackground(Color.decode("#e6ebee"));
		textCin.setColumns(10);
		textCin.setBounds(68, 306, 234, 32);
		contentPane.add(textCin);
		
		textNumTel = new JTextField();
		textNumTel.setBorder(null);
		textNumTel.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNumTel.setBackground(Color.decode("#e6ebee"));
		textNumTel.setColumns(10);
		textNumTel.setBounds(68, 375, 234, 32);
		contentPane.add(textNumTel);
		
		textCodeClient = new JTextField();
		textCodeClient.setBorder(null);
		textCodeClient.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCodeClient.setBackground(Color.decode("#e6ebee"));
		textCodeClient.setColumns(10);
		textCodeClient.setBounds(68, 98, 234, 32);
		contentPane.add(textCodeClient);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(412, 106, 807, 291);
		contentPane.add(scrollPane);
		scrollPane.setBackground(new Color(247, 214, 183));
		scrollPane.getViewport().setBackground(Color.decode("#e6ebee"));
		
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
				textCodeClient.setText(table.getModel().getValueAt(ligne, 1).toString());
				textAdresse.setText(table.getModel().getValueAt(ligne, 2).toString());
				textCin.setText(table.getModel().getValueAt(ligne, 3).toString());
				textNumTel.setText(table.getModel().getValueAt(ligne, 4).toString());
				button.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel gestionClients = new JLabel("");
		gestionClients.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gestionClients.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/MenuGestion.png")).getImage()));
		gestionClients.setBounds(0, 0, 1234, 549);
		contentPane.add(gestionClients);
		autoId();
	}
}
