package dao;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public final class ClientDao extends ConnectionStatement{
	
	private Statement statement=null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private String codeClient;
	private String nomComplet;
	private String adresse;
	private String cin;
	private int numTelephone;
	private InputStream imagePermis;
	//private ReservationDao reservation;
	private ArrayList<ReservationDao> reservations;
	private ArrayList<FactureDao> factures;
	private ArrayList<ContratDao> contrats;
	private ArrayList<VoitureDao> voitures;
	
	public ClientDao(String codeClient, String nomComplet, String adresse,String cin, int numTelephone,InputStream imagePermis) {
		this.codeClient = codeClient;
		this.nomComplet = nomComplet;
		this.adresse = adresse;
		this.cin=cin;
		this.numTelephone = numTelephone;
		this.imagePermis = imagePermis;
		reservations=new ArrayList<>();
	}
	
	public ClientDao(String codeClient, String nomComplet, String adresse,String cin, int numTelephone) {
		this.codeClient = codeClient;
		this.nomComplet = nomComplet;
		this.adresse = adresse;
		this.cin=cin;
		this.numTelephone = numTelephone;
		reservations=new ArrayList<>();
	}
	
	public ClientDao(String codeClient,String nomComplet) {
		this.nomComplet = nomComplet;
		this.codeClient=codeClient;
		reservations=new ArrayList<>();
	}

	public ClientDao(String nomComplet) {
		this.nomComplet = nomComplet;
		reservations=new ArrayList<>();
	}
	
	public ClientDao() {
		reservations=new ArrayList<>();
	}
	
	public String getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
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
	
	public ResultSet Lister(){
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `client` ORDER BY `nom_complet`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int Ajouter(ClientDao client) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("INSERT INTO `client`(`nom_complet`, `code_client`, `adresse`, `cin`, `num_tel`, `image_permis`) VALUES (?,?,?,?,?,?) ");
			preparedStatement.setString(1, client.getNomComplet());
			preparedStatement.setString(2, client.getCodeClient());
			preparedStatement.setString(3, client.getAdresse());
			preparedStatement.setString(4, client.getCin());
			preparedStatement.setInt(5, client.getNumTelephone());
			preparedStatement.setBlob(6, client.getImagePermis());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public String getClientName(ClientDao client) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("SELECT `nom_complet` FROM `client` WHERE `code_client` = ? ");
			preparedStatement.setString(1, client.getCodeClient());
			this.resultSet=preparedStatement.executeQuery();
			if(this.resultSet.next()) {
				return this.resultSet.getString("nom_complet");
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public ResultSet Rechercher(ClientDao client) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `client` WHERE `nom_complet` LIKE '"+nomComplet+"' ORDER BY `nom_complet`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int RechercherParCodeClient(ClientDao client) {
		String codeClient=client.getCodeClient();
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `client` WHERE `code_client`='"+codeClient+"' ");
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
	public int Modifier(ClientDao client) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `client` SET `nom_complet`= ? ,`adresse`= ? ,"
					+ "`cin`= ? ,`num_tel`= ? WHERE `code_client`= ? ");
			preparedStatement.setString(1, nomComplet);
			preparedStatement.setString(2, adresse);
			preparedStatement.setString(3, cin);
			preparedStatement.setInt(4, numTelephone);
			preparedStatement.setString(5, codeClient);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Supprimer(ClientDao client) {
		this.statement=this.getStatement();
		String sql="DELETE FROM `client` WHERE `code_client`= ? and `nom_complet`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setString(1, codeClient);
			preparedStatement.setString(2, nomComplet);
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public long autoID() {
		this.statement=this.getStatement();
		String sql="select Max(code_client) from client";
		 try {
			this.resultSet=statement.executeQuery(sql);
			 resultSet.next();
			 resultSet.getString("Max(code_client)");
			if(resultSet.getString("Max(code_client)")==null) {
				return 0;
			}
			else {
				long id=Long.parseLong(resultSet.getString("Max(code_client)").substring(2, resultSet.getString("Max(code_client)").length()));
				id++;
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int ChercherContratParClient(ClientDao client) {
		String codeClient=client.getCodeClient();
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `contrat` WHERE `code_client`='"+codeClient+"' ");
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

	public ArrayList<ReservationDao> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<ReservationDao> reservations) {
		this.reservations = reservations;
	}

	public ArrayList<FactureDao> getFactures() {
		return factures;
	}

	public void setFactures(ArrayList<FactureDao> factures) {
		this.factures = factures;
	}

	public ArrayList<ContratDao> getContrats() {
		return contrats;
	}

	public void setContrats(ArrayList<ContratDao> contrats) {
		this.contrats = contrats;
	}

	public ArrayList<VoitureDao> getVoitures() {
		return voitures;
	}

	public void setVoitures(ArrayList<VoitureDao> voitures) {
		this.voitures = voitures;
	}

	public InputStream getImagePermis() {
		return imagePermis;
	}

	public void setImagePermis(InputStream imagePermis) {
		this.imagePermis = imagePermis;
	}
}
