package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FactureDao extends ConnectionStatement{

	
	private Statement statement=null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private int numeroFacture;
	private java.sql.Date dateFacture;
	private double montant;
	private ClientDao client;
	private ContratDao contrat;
	
	public FactureDao(int numeroFacture, Date dateFacture, double montant, ClientDao client, ContratDao contrat) {
		this.numeroFacture = numeroFacture;
		this.dateFacture = dateFacture;
		this.montant = montant;
		this.client = client;
		this.contrat = contrat;
	}
	
	public FactureDao(int numeroFacture) {
		this.numeroFacture = numeroFacture;
	}
	
	public FactureDao() {
		
	}
	
	public ResultSet Lister(){
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `facture` ORDER BY `date_facture`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int Ajouter(FactureDao facture) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("INSERT INTO `facture`(`num_facture`, `date_facture`, `montant`, `code_client`, `num_contrat`) VALUES (?,?,?,?,?) ");
			preparedStatement.setInt(1, facture.getNumeroFacture());
			preparedStatement.setDate(2, facture.getDateFacture());
			preparedStatement.setDouble(3, facture.getMontant());
			preparedStatement.setString(4, facture.getClient().getCodeClient());
			preparedStatement.setInt(5, facture.getContrat().getNumeroContrat());
			preparedStatement.executeUpdate();
			//client.getReservations().add(reservation);
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public ResultSet Rechercher(FactureDao facture) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `facture` WHERE `num_facture`='"+facture.getNumeroFacture()+"' ORDER BY `date_facture`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int Modifier(FactureDao facture) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `facture` SET `date_facture`= ? ,`montant`= ? ,"
					+ "`code_client`= ? , `num_contrat`= ?  WHERE `num_facture`= ? ");
			preparedStatement.setDate(1, facture.getDateFacture());
			preparedStatement.setDouble(2, facture.getMontant());
			preparedStatement.setString(3, facture.getClient().getCodeClient());
			preparedStatement.setInt(4, facture.getContrat().getNumeroContrat());
			preparedStatement.setInt(5, facture.getNumeroFacture());
			preparedStatement.executeUpdate();
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Supprimer(FactureDao facture) {
		this.statement=this.getStatement();
		String sql="DELETE FROM `facture` WHERE `num_facture`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setInt(1, facture.getNumeroFacture());
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
		String sql="select Max(num_facture) from facture";
		 try {
			this.resultSet=statement.executeQuery(sql);
			 resultSet.next();
			 resultSet.getInt("Max(num_facture)");
			if(resultSet.getInt("Max(num_facture)")==0) {
				return 0;
			}
			else {
				long id=resultSet.getInt("Max(num_facture)");
				id++;
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getNumeroFacture() {
		return numeroFacture;
	}
	public void setNumeroFacture(int numeroFacture) {
		this.numeroFacture = numeroFacture;
	}
	public java.sql.Date getDateFacture() {
		return dateFacture;
	}
	public void setDateFacture(java.sql.Date dateFacture) {
		this.dateFacture = dateFacture;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public ClientDao getClient() {
		return client;
	}
	public void setClient(ClientDao client) {
		this.client = client;
	}
	public ContratDao getContrat() {
		return contrat;
	}
	public void setContrat(ContratDao contrat) {
		this.contrat = contrat;
	}
}
