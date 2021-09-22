package View;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuAdmin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuAdmin frame = new MenuAdmin();
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
	public MenuAdmin() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 588);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Menu");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		
		
		JLabel btnGestionClients = new JLabel("");
		btnGestionClients.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGestionClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionClients fenetre1 = new GestionClients();
				fenetre1.setTitle("Gestion des clients  ");
				fenetre1.setVisible(true);
				fenetre1.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGestionClients.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Gestion-Clients-Hover.png")).getImage()));
			}
			public void mouseExited(MouseEvent e) {
				btnGestionClients.setIcon(new ImageIcon(""));
			}
		});
		
		JLabel btnGestionUtilisateurs = new JLabel("");
		btnGestionUtilisateurs.setBounds(187, 164, 180, 151);
		getContentPane().add(btnGestionUtilisateurs);
		btnGestionUtilisateurs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGestionUtilisateurs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionUtilisateurs fenetre9;
				try {
					fenetre9 = new GestionUtilisateurs();
					fenetre9.setTitle("Gestion des utilisateurs  ");
					fenetre9.setVisible(true);
					fenetre9.setLocationRelativeTo(null);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGestionUtilisateurs.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Gestion-utilisateurs-hover.png")).getImage()));
			}
			public void mouseExited(MouseEvent e) {
				btnGestionUtilisateurs.setIcon(new ImageIcon(""));
			}
		});
		
		JLabel btnGestionVehicules = new JLabel("");
		btnGestionVehicules.setBounds(413, 347, 180, 151);
		getContentPane().add(btnGestionVehicules);
		btnGestionVehicules.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGestionVehicules.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionVoitures fenetre2 = new GestionVoitures();
				fenetre2.setTitle("Gestion des véhicules  ");
				fenetre2.setVisible(true);
				fenetre2.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGestionVehicules.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Gestion-vehicules-hover.png")).getImage()));
			}
			public void mouseExited(MouseEvent e) {
				btnGestionVehicules.setIcon(new ImageIcon(""));
			}
		});
		
		JLabel btnGestionParking = new JLabel("");
		btnGestionParking.setBounds(638, 347, 180, 151);
		getContentPane().add(btnGestionParking);
		btnGestionParking.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGestionParking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionParkings fenetre3 = new GestionParkings();
				fenetre3.setTitle("Gestion des parkings  ");
				fenetre3.setVisible(true);
				fenetre3.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGestionParking.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Gestion-Parking-hover.png")).getImage()));
			}
			public void mouseExited(MouseEvent e) {
				btnGestionParking.setIcon(new ImageIcon(""));
			}
		});
		
		JLabel btnGestionSanctions = new JLabel("");
		btnGestionSanctions.setBounds(863, 347, 180, 151);
		getContentPane().add(btnGestionSanctions);
		btnGestionSanctions.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGestionSanctions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionSanctions fenetre4= new GestionSanctions();
				fenetre4.setTitle("Gestion des sanctions  ");
				fenetre4.setVisible(true);
				fenetre4.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGestionSanctions.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Gestion-Sanctions-hover.png")).getImage()));
			}
			public void mouseExited(MouseEvent e) {
				btnGestionSanctions.setIcon(new ImageIcon(""));
			}
		});
		
		JLabel btnGestionFactures = new JLabel("");
		btnGestionFactures.setBounds(187, 347, 180, 151);
		getContentPane().add(btnGestionFactures);
		btnGestionFactures.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGestionFactures.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionFactures fenetre5 = new GestionFactures();
				fenetre5.setTitle("Gestion des factures  ");
				fenetre5.setVisible(true);
				fenetre5.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGestionFactures.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Gestion-factures-hover.png")).getImage()));
			}
			public void mouseExited(MouseEvent e) {
				btnGestionFactures.setIcon(new ImageIcon(""));
			}
		});
		
		JLabel btnGestionContrats = new JLabel("");
		btnGestionContrats.setBounds(863, 164, 180, 151);
		getContentPane().add(btnGestionContrats);
		btnGestionContrats.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGestionContrats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionContrats fenetre6 = new GestionContrats();
				fenetre6.setTitle("Gestion des contrats  ");
				fenetre6.setVisible(true);
				fenetre6.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGestionContrats.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Gestion-contrats-hover.png")).getImage()));
			}
			public void mouseExited(MouseEvent e) {
				btnGestionContrats.setIcon(new ImageIcon(""));
			}
		});
		
		JLabel btnGestionReservations = new JLabel("");
		btnGestionReservations.setBounds(638, 164, 180, 151);
		getContentPane().add(btnGestionReservations);
		btnGestionReservations.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGestionReservations.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionReservations fenetre7 = new GestionReservations();
				fenetre7.setTitle("Gestion des réservations  ");
				fenetre7.setVisible(true);
				fenetre7.setLocationRelativeTo(null);
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGestionReservations.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Gestion-Reservations-hover.png")).getImage()));
			}
			public void mouseExited(MouseEvent e) {
				btnGestionReservations.setIcon(new ImageIcon(""));
			}
		});
		
		btnGestionClients.setBounds(412, 164, 180, 151);
		getContentPane().add(btnGestionClients);
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Menu-Home-Admin.png")).getImage()));
		lblNewLabel.setBounds(0, 0, 1234, 549);
		getContentPane().add(lblNewLabel);
		
	}
}
