package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilisateurDao extends ConnectionStatement{
	
	private Statement statement=null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private String codeUtilisateur;
	private String nomComplet;
	private String adresse;
	private String cin;
	private int numTelephone;
	private String motDePasse;
	
	public UtilisateurDao(String codeUtilisateur, String nomComplet, String adresse, String cin, int numTelephone,
			String motDePasse) {
		this.codeUtilisateur = codeUtilisateur;
		this.nomComplet = nomComplet;
		this.adresse = adresse;
		this.cin = cin;
		this.numTelephone = numTelephone;
		this.motDePasse = motDePasse;
	}
	
	public UtilisateurDao(String codeUtilisateur, String nomComplet,String motDePasse) {
		this.codeUtilisateur = codeUtilisateur;
		this.nomComplet = nomComplet;
		this.motDePasse=motDePasse;
	}
	
	public UtilisateurDao(String nomComplet, String motDePasse) {
		this.nomComplet = nomComplet;
		this.motDePasse = motDePasse;
	}
	
	public UtilisateurDao(String nomComplet) {
		this.nomComplet = nomComplet;
	}
	
	public UtilisateurDao() {
		
	}

	public ResultSet Lister(UtilisateurDao utilisateur) throws Exception{
		this.statement=this.getStatement();
		this.resultSet=statement.executeQuery("SELECT * FROM `utilisateur` ORDER BY `nom_complet`");
		return resultSet;
	}
	public int Ajouter(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("INSERT INTO `utilisateur`(`nom_complet`, `code_utilisateur`, `adresse`, `cin`, `num_tel`, `mdp`) VALUES (?,?,?,?,?,?) ");
			preparedStatement.setString(1, utilisateur.getNomComplet());
			preparedStatement.setString(2, utilisateur.getCodeUtilisateur());
			preparedStatement.setString(3, utilisateur.getAdresse());
			preparedStatement.setString(4, utilisateur.getCin());
			preparedStatement.setInt(5, utilisateur.getNumTelephone());
			preparedStatement.setString(6, utilisateur.getMotDePasse());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	public long autoID() throws SQLException{
		this.statement=this.getStatement();
		String sql="select Max(code_utilisateur) from utilisateur";
		 this.resultSet=statement.executeQuery(sql);
		 resultSet.next();
		 resultSet.getString("Max(code_utilisateur)");
		if(resultSet.getString("Max(code_utilisateur)")==null) {
			return 0;
		}
		else {
			long id=Long.parseLong(resultSet.getString("Max(code_utilisateur)").substring(2, resultSet.getString("Max(code_utilisateur)").length()));
			id++;
			return id;
		}
	}
	
	public int Modifier(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		String sql="UPDATE `utilisateur` SET `nom_complet`= ? ,`adresse`= ? ,`cin`= ? ,`num_tel`= ? ,`mdp`= ? WHERE `code_utilisateur`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setString(1, utilisateur.getNomComplet());
			preparedStatement.setString(2, utilisateur.getAdresse());
			preparedStatement.setString(3, utilisateur.getCin());
			preparedStatement.setInt(4, utilisateur.getNumTelephone());
			preparedStatement.setString(5, utilisateur.getMotDePasse());
			preparedStatement.setString(6, utilisateur.getCodeUtilisateur());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Suspendre(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		String sql="UPDATE `utilisateur` SET `Statut`= ? WHERE `code_utilisateur`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setInt(1, 2);
			preparedStatement.setString(2, utilisateur.getCodeUtilisateur());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Restituer(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		String sql="UPDATE `utilisateur` SET `Statut`= ? WHERE `code_utilisateur`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, utilisateur.getCodeUtilisateur());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int getStatut(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT `Statut` FROM `utilisateur` WHERE `code_utilisateur` = '"+utilisateur.getCodeUtilisateur()+"' ");
			if(this.resultSet.next()) {
				return this.resultSet.getInt("Statut");
			}
			return 3;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3;
		}
	}
	public String getCodeUtilisateur(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT `code_utilisateur` FROM `utilisateur` WHERE `nom_complet` = '"+utilisateur.getNomComplet()+"' and"
					+ " mdp= '"+utilisateur.getMotDePasse()+"'  ");
			if(this.resultSet.next()) {
				return this.resultSet.getString("code_utilisateur");
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet Rechercher(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `utilisateur` WHERE `nom_complet` LIKE '"+nomComplet+"' ORDER BY `nom_complet`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int Supprimer(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		String sql="DELETE FROM `utilisateur` WHERE `code_utilisateur`= ? and `nom_complet`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setString(1, utilisateur.getCodeUtilisateur());
			preparedStatement.setString(2, utilisateur.getNomComplet());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Login(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		String sql="SELECT `nom_complet`,`mdp` FROM `utilisateur` WHERE nom_complet= '"+utilisateur.getNomComplet()+"' "
				+ "and mdp= '"+utilisateur.getMotDePasse()+"'";
		try {
			this.resultSet=statement.executeQuery(sql);
			if(resultSet.next()) {
				return 1;
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int LoginAdmin(UtilisateurDao utilisateur) {
		this.statement=this.getStatement();
		String sql="SELECT `nom_complet`,`mdp` FROM `utilisateur` WHERE nom_complet= '"+utilisateur.getNomComplet()+"' "
				+ "and mdp= '"+utilisateur.getMotDePasse()+"' and `Statut` = 1";
		try {
			this.resultSet=statement.executeQuery(sql);
			if(resultSet.next()) {
				return 1;
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public String getCodeUtilisateur() {
		return codeUtilisateur;
	}
	public void setCodeUtilisateur(String codeUtilisateur) {
		this.codeUtilisateur = codeUtilisateur;
	}
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public int getNumTelephone() {
		return numTelephone;
	}
	public void setNumTelephone(int numTelephone) {
		this.numTelephone = numTelephone;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
}
