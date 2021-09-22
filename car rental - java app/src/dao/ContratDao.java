package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContratDao extends ConnectionStatement{
	private Statement statement=null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private int numeroContrat;
	private Date dateContrat;
	private Date dateEcheance;
	private ClientDao client;
	
	public ContratDao(int numeroContrat, Date dateContrat, Date dateEcheance,
			ClientDao client) {
		this.numeroContrat = numeroContrat;
		this.dateContrat = dateContrat;
		this.dateEcheance = dateEcheance;
		this.client = client;
	}

	public ContratDao(int numeroContrat, Date dateContrat, Date dateEcheance) {
		this.numeroContrat = numeroContrat;
		this.dateContrat = dateContrat;
		this.dateEcheance = dateEcheance;
	}
	
	public ContratDao(int numeroContrat) {
		this.numeroContrat = numeroContrat;
	}
	
	public ContratDao() {
		
	}
	
	public ResultSet Lister() {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `contrat` ORDER BY `date_contrat`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int Ajouter(ContratDao contrat) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("INSERT INTO `contrat`(`num_contrat`, `date_contrat`, `date_echeance`, `code_client`) VALUES (?,?,?,?) ");
			preparedStatement.setInt(1, contrat.getNumeroContrat());
			preparedStatement.setDate(2, contrat.getDateContrat());
			preparedStatement.setDate(3, contrat.getDateEcheance());
			preparedStatement.setString(4, contrat.getClient().getCodeClient());
			preparedStatement.executeUpdate();
			//client.getReservations().add(reservation);
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public ResultSet Rechercher(ContratDao contrat) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT `num_contrat` FROM `contrat` WHERE `num_contrat`="+contrat.getNumeroContrat()+"");
			if(resultSet.next()) {
				this.resultSet=statement.executeQuery("SELECT * FROM `contrat` WHERE `num_contrat`="+contrat.getNumeroContrat()+" ORDER BY `date_contrat`");
				return resultSet;
			}
			else {
				return null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int RechercherParNumeroContrat(ContratDao contrat) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `contrat` WHERE `num_contrat`="+contrat.getNumeroContrat()+"");
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
	
	public int Modifier(ContratDao contrat) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `contrat` SET `date_contrat`= ? ,`date_echeance`= ? ,"
					+ " `code_client`= ?  WHERE `num_contrat`= ? ");
			preparedStatement.setDate(1, contrat.getDateContrat());
			preparedStatement.setDate(2, contrat.getDateEcheance());
			preparedStatement.setString(3, contrat.getClient().getCodeClient());
			preparedStatement.setInt(4, contrat.getNumeroContrat());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int Supprimer(ContratDao contrat) {
		this.statement=this.getStatement();
		String sql="DELETE FROM `contrat` WHERE `num_contrat`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setInt(1, contrat.getNumeroContrat());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public long autoID() {
		this.statement=this.getStatement();
		String sql="select Max(num_contrat) from contrat";
		 try {
			this.resultSet=statement.executeQuery(sql);
			 resultSet.next();
			 resultSet.getInt("Max(num_contrat)");
			if(resultSet.getInt("Max(num_contrat)")==0) {
				return 0;
			}
			else {
				long id=resultSet.getInt("Max(num_contrat)");
				id++;
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public String RetournerCodeClient(ContratDao contrat) {
		this.statement=this.getStatement();
		String sql="SELECT `code_client` FROM `contrat` WHERE `num_contrat`= '"+contrat.getNumeroContrat()+"'";
		try {
			this.resultSet=statement.executeQuery(sql);
			if(resultSet.next()) {
				return resultSet.getString("code_client");
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int getNumeroContrat() {
		return numeroContrat;
	}

	public void setNumeroContrat(int numerContrat) {
		this.numeroContrat = numerContrat;
	}

	public Date getDateContrat() {
		return dateContrat;
	}

	public void setDateContrat(Date dateContrat) {
		this.dateContrat = dateContrat;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public ClientDao getClient() {
		return client;
	}

	public void setClient(ClientDao client) {
		this.client = client;
	}
	
}
