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

public class GestionLogin extends JFrame {

	/**
	 * 
	 */
	Utilisateur utilisateur=new Utilisateur();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUtilisateur;
	private JPasswordField password;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionLogin frame = new GestionLogin();
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
	public GestionLogin() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel btnLogin = new JLabel("");
		btnLogin.setBounds(581, 370, 318, 52);
		contentPane.add(btnLogin);
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textUtilisateur.getText().length()!=0 && password.getText().length()!=0) {
					String nomComplet=textUtilisateur.getText();
					String motDePasse=password.getText();
					int d=utilisateur.Login(nomComplet, motDePasse);
					if(d==1) {
						String codeUtilisateur=utilisateur.getCodeUtilisateur(nomComplet, motDePasse);
						int m=utilisateur.getStatut(codeUtilisateur);
						if(m==2) {
							JOptionPane.showMessageDialog(null, "Cet utilisateur est en congé ! vous n'avez pas l'accès à ce moment.");
						}else {
							Menu menu = new Menu();
							menu.setTitle("Menu - Utilisateur");
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
				btnLogin.setIcon(new ImageIcon(""));
			}
			public void mouseExited(MouseEvent e) {
				btnLogin.setIcon(new ImageIcon(""));
			}
		});
		
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 18));
		password.setBackground(Color.decode("#e1e3e4"));
		password.setBorder(null);
		password.setBounds(630, 293, 251, 47);
		contentPane.add(password);
		
		textUtilisateur = new JTextField();
		textUtilisateur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textUtilisateur.setBorder(null);
		textUtilisateur.setBackground(Color.decode("#e1e3e4"));
		textUtilisateur.setForeground(new Color(0, 0, 0));
		textUtilisateur.setColumns(10);
		textUtilisateur.setBounds(630, 186, 251, 47);
		contentPane.add(textUtilisateur);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/login-Menu2.png")).getImage()));
		lblNewLabel.setBounds(0, 0, 984, 461);
		contentPane.add(lblNewLabel);
	}
}
