package View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class Gestionde extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Create the panel.
	 */
	public Gestionde() {
		setBounds(0, 0, 986, 466);
		setLayout(null);
		
		JLabel label = new JLabel("Gestion de clients");
		label.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
		label.setBounds(10, 11, 188, 31);
		add(label);
		
		JLabel label_1 = new JLabel("Code client");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(10, 74, 116, 14);
		add(label_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(128, 73, 202, 20);
		add(textField);
		
		JLabel label_2 = new JLabel("Nom Complet");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_2.setBounds(10, 114, 116, 14);
		add(label_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(128, 113, 202, 20);
		add(textField_1);
		
		JLabel label_3 = new JLabel("Adresse");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_3.setBounds(10, 154, 116, 14);
		add(label_3);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(128, 153, 202, 20);
		add(textField_2);
		
		JLabel label_4 = new JLabel("Cin");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_4.setBounds(10, 195, 116, 14);
		add(label_4);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(128, 194, 202, 20);
		add(textField_3);
		
		JLabel label_5 = new JLabel("Num tel ");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_5.setBounds(10, 235, 116, 14);
		add(label_5);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(128, 234, 202, 20);
		add(textField_4);
		
		JButton button = new JButton("Ajouter");
		button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button.setBounds(10, 353, 108, 43);
		add(button);
		
		JButton button_1 = new JButton("Afficher");
		button_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_1.setBounds(128, 353, 108, 43);
		add(button_1);
		
		JButton button_2 = new JButton("Rechercher");
		button_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_2.setBounds(10, 407, 108, 43);
		add(button_2);
		
		JButton button_3 = new JButton("Modifier");
		button_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_3.setBounds(246, 353, 108, 43);
		add(button_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(375, 37, 599, 375);
		add(scrollPane);
		
		JLabel label_6 = new JLabel("Tableau d'affichage");
		label_6.setFont(new Font("Myanmar Text", Font.BOLD, 14));
		label_6.setBounds(375, 11, 142, 26);
		add(label_6);
		
		JButton button_4 = new JButton("Retour");
		button_4.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_4.setBounds(871, 423, 103, 27);
		add(button_4);
		
		JButton button_5 = new JButton("Supprimer");
		button_5.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		button_5.setBounds(128, 407, 108, 43);
		add(button_5);

	}

}
