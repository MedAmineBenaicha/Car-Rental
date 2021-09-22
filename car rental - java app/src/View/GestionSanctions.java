package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import BusinessObjects.Client;
import BusinessObjects.Sanction;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class GestionSanctions extends JFrame {

	/**
	 * 
	 */
	Sanction sanction = new Sanction();
	Client client = new Client();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textNumeroContrat;
	private JTextField textCodeClient;
	private JTextField textNomComplet;
	private JTextField textAmende;
	private JTextField textNumeroSanction;
	private JButton button_2;
	private JLabel btnPrevious;
	private JFrame frame;

	/**
	 * Lancez l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionSanctions frame = new GestionSanctions();
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
	public GestionSanctions() {
		frame=this;
		sanction.calculerAmmende();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textNumeroSanction = new JTextField();
		textNumeroSanction.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNumeroSanction.setColumns(10);
		textNumeroSanction.setBorder(null);
		textNumeroSanction.setBackground(new Color(230, 235, 238));
		textNumeroSanction.setBounds(68, 98, 234, 32);
		contentPane.add(textNumeroSanction);
		
		textAmende = new JTextField();
		textAmende.setFont(new Font("Verdana", Font.PLAIN, 16));
		textAmende.setColumns(10);
		textAmende.setBorder(null);
		textAmende.setBackground(new Color(230, 235, 238));
		textAmende.setBounds(68, 375, 234, 32);
		contentPane.add(textAmende);
		
		textCodeClient = new JTextField();
		textCodeClient.setFont(new Font("Verdana", Font.PLAIN, 16));
		textCodeClient.setColumns(10);
		textCodeClient.setBorder(null);
		textCodeClient.setBackground(new Color(230, 235, 238));
		textCodeClient.setBounds(68, 236, 234, 32);
		contentPane.add(textCodeClient);
		
		textNumeroContrat = new JTextField();
		textNumeroContrat.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNumeroContrat.setColumns(10);
		textNumeroContrat.setBorder(null);
		textNumeroContrat.setBackground(new Color(230, 235, 238));
		textNumeroContrat.setBounds(68, 167, 234, 32);
		contentPane.add(textNumeroContrat);
		
		textNomComplet = new JTextField();
		textNomComplet.setFont(new Font("Verdana", Font.PLAIN, 16));
		textNomComplet.setColumns(10);
		textNomComplet.setBorder(null);
		textNomComplet.setBackground(new Color(230, 235, 238));
		textNomComplet.setBounds(68, 305, 234, 32);
		contentPane.add(textNomComplet);
		
		JButton button = new JButton("");
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String amende = textAmende.getText();
				String nom = textNomComplet.getText();
				if(textNumeroSanction.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Veuillez remplir le numéro de sanction", "Erreur de payement", JOptionPane.OK_OPTION);
				}
				else {
					int done= JOptionPane.showConfirmDialog(null, "<html>Pour régler la facture du client : "+nom+" <br><br> vous devez payer : "+amende+" Dhs</html>", "Règlement de la sanction",JOptionPane.YES_NO_OPTION);
					if(done == 0) {
						try {
							int numeroSanction=Integer.parseInt(textNumeroSanction.getText());
							int d=sanction.Payer(numeroSanction,0);
							if(d==1) {
								JOptionPane.showMessageDialog(null, "La sanction a été payée");
							}
							else {
								JOptionPane.showMessageDialog(null, "La sanction n'a pas été payée! Veuillez reessayez");
							}
						}
						catch(NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Le numéro de sanction se compose des entiers seulement !","Erreur lors du payement",JOptionPane.ERROR_MESSAGE);
						}
					}else {
						
					}
				}
				textNumeroSanction.setText("");
				textCodeClient.setText("");
				textNumeroContrat.setText("");
				textNomComplet.setText("");
				textAmende.setText("");
				button_2.doClick();
			}
		});
		button.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnPayer.png")).getImage()));
		button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button.setBounds(62, 471, 140, 46);
		contentPane.add(button);
		
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
				textNumeroSanction.setText(table.getModel().getValueAt(ligne, 0).toString());
				textNumeroContrat.setText(table.getModel().getValueAt(ligne, 1).toString());
				int numeroContrat = Integer.parseInt(table.getModel().getValueAt(ligne, 1).toString());
				String codeClient=sanction.getNomClient(numeroContrat);
				textCodeClient.setText(codeClient);
				String nomClientComplet = client.getNomClient(codeClient);
				textNomComplet.setText(nomClientComplet);
				textAmende.setText(table.getModel().getValueAt(ligne, 3).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		
		btnPrevious = new JLabel("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(frame.getTitle()== "Gestion des sanctions ") {
					Menu previous = new Menu();
					previous.setTitle("Menu - Utilisateur");
					previous.setVisible(true);
					previous.setLocationRelativeTo(null);
					dispose();
				}else if(frame.getTitle()== "Gestion des sanctions  ") {
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
		
		button_2 = new JButton("");
		button_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultSet rs;
				try {
					rs = sanction.Afficher();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textNumeroSanction.setText("");
				textCodeClient.setText("");
				textNumeroContrat.setText("");
				textNomComplet.setText("");
				textAmende.setText("");
			}
		});
		button_2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/btnAfficher.png")).getImage()));
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(238, 471, 157, 46);
		contentPane.add(button_2);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/menuGestionDesSanctions.png")).getImage()));
		lblNewLabel.setBounds(0, 0, 1234, 549);
		contentPane.add(lblNewLabel);
	}
}
