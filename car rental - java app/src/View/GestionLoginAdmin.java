package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BusinessObjects.Utilisateur;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GestionLoginAdmin extends JFrame {

	/**
	 * 
	 */
	Utilisateur utilisateur=new Utilisateur();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUtilisateurAdmin;
	private JPasswordField passwordAdmin;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionLoginAdmin frame = new GestionLoginAdmin();
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
	public GestionLoginAdmin() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel btnLoginAdmin = new JLabel("");
		btnLoginAdmin.setBounds(581, 369, 318, 52);
		contentPane.add(btnLoginAdmin);
		btnLoginAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLoginAdmin.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textUtilisateurAdmin.getText().length()!=0 && passwordAdmin.getText().length()!=0) {
					String nomComplet=textUtilisateurAdmin.getText();
					String motDePasse=passwordAdmin.getText();
					int d=utilisateur.LoginAdmin(nomComplet, motDePasse);
					if(d==1) {
						String codeUtilisateur=utilisateur.getCodeUtilisateur(nomComplet, motDePasse);
						int m=utilisateur.getStatut(codeUtilisateur);
						if(m==2) {
							JOptionPane.showMessageDialog(null, "Cet utilisateur est en congé ! vous n'avez pas l'accès à ce moment.");
						}else {
							MenuAdmin menu = new MenuAdmin();
							menu.setTitle("Menu - Admin");
							menu.setVisible(true);
							dispose();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "username ou mot de passe erroné");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Veuillez Remplir toutes les cases");
				}
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLoginAdmin.setIcon(new ImageIcon(""));
			}
			public void mouseExited(MouseEvent e) {
				btnLoginAdmin.setIcon(new ImageIcon(""));
			}
		});
		
		passwordAdmin = new JPasswordField();
		passwordAdmin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordAdmin.setBackground(Color.decode("#e1e3e4"));
		passwordAdmin.setBorder(null);
		passwordAdmin.setBounds(630, 293, 251, 47);
		contentPane.add(passwordAdmin);
		
		textUtilisateurAdmin = new JTextField();
		textUtilisateurAdmin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textUtilisateurAdmin.setBorder(null);
		textUtilisateurAdmin.setBackground(Color.decode("#e1e3e4"));
		textUtilisateurAdmin.setForeground(new Color(0, 0, 0));
		textUtilisateurAdmin.setColumns(10);
		textUtilisateurAdmin.setBounds(630, 186, 251, 47);
		contentPane.add(textUtilisateurAdmin);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/login-Menu-Admin.png")).getImage()));
		lblNewLabel.setBounds(0, 0, 984, 461);
		contentPane.add(lblNewLabel);
		
		JLabel loginMenu = new JLabel("New label");
		loginMenu.setBounds(0, 0, 984, 461);
		contentPane.add(loginMenu);
	}
}
