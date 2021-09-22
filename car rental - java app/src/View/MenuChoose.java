package View;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class MenuChoose extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuChoose frame = new MenuChoose();
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
	public MenuChoose() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel btnUser = new JLabel("");
		btnUser.setBounds(775, 282, 139, 39);
		contentPane.add(btnUser);
		btnUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionLogin login = new GestionLogin();
				login.setTitle("Login - Utilisateur");
				login.setVisible(true);
				login.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUser.setIcon(new ImageIcon(""));
			}
			public void mouseExited(MouseEvent e) {
				btnUser.setIcon(new ImageIcon(""));
			}
		});

		JLabel btnAdmin = new JLabel("");
		btnAdmin.setBounds(595, 282, 139, 39);
		contentPane.add(btnAdmin);
		btnAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionLoginAdmin loginAdmin = new GestionLoginAdmin();
				loginAdmin.setTitle("Login - Admin");
				loginAdmin.setVisible(true);
				loginAdmin.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUser.setIcon(new ImageIcon(""));
			}
			public void mouseExited(MouseEvent e) {
				btnUser.setIcon(new ImageIcon(""));
			}
		});
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Menu-choose.png")).getImage()));
		lblNewLabel.setBounds(0, 0, 984, 461);
		contentPane.add(lblNewLabel);

	}
}
