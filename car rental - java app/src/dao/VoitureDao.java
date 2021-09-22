package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VoitureDao extends ConnectionStatement{

	private Statement statement=null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private int numImmatriculation;
	private String marque;
	private String type;
	private String carburant;
	private int compteurKm;
	private java.sql.Date dateMisEnCirculation;
	//private ParkingDao parking;
	private ClientDao client;
	private ParkingDao parking;
	
	public VoitureDao (int numImmatriculation, String marque, String type, String carburant, int compteurKm,java.sql.Date dateMisEnCirculation) {
		this.setNumImmatriculation(numImmatriculation);
		this.setMarque(marque);
		this.setType(type);
		this.setCarburant(carburant);
		this.setCompteurKm(compteurKm);
		this.setDateMisEnCirculation(dateMisEnCirculation);
		
	}
	public VoitureDao(int numImmatriculation,ClientDao client,ParkingDao parking) {
		this.setClient(client);
		this.setNumImmatriculation(numImmatriculation);
		this.setParking(parking);
	}
	public VoitureDao(int numImmatriculation,ClientDao client) {
		this.setClient(client);
		this.setNumImmatriculation(numImmatriculation);
	}
	public VoitureDao(int numImmatriculation) {
		this.setNumImmatriculation(numImmatriculation);
	}
	public VoitureDao() {
		
	}

	public ResultSet Lister(){
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `voiture` ORDER BY `num_immatriculation`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int Ajouter(VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("INSERT INTO `voiture`(`num_immatriculation`, `marque`, `type`, `carburant`, `compteur_km`,`date_mis_en_circulation`) VALUES (?,?,?,?,?,?) ");
			preparedStatement.setInt(1, voiture.getNumImmatriculation());
			preparedStatement.setString(2, voiture.getMarque());
			preparedStatement.setString(3, voiture.getType());
			preparedStatement.setString(4, voiture.getCarburant());
			preparedStatement.setInt(5, voiture.getCompteurKm());
			preparedStatement.setDate(6, voiture.getDateMisEnCirculation());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public ResultSet Rechercher(VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `voiture` WHERE `num_immatriculation`='"+numImmatriculation+"' ORDER BY `num_immatriculation`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int Modifier(VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `voiture` SET `marque`= ? ,`type`= ? ,"
					+ "`carburant`= ? ,`compteur_km`= ?,`date_mis_en_circulation`= ? WHERE `num_immatriculation`= ? ");
			preparedStatement.setString(1, marque);
			preparedStatement.setString(2, type);
			preparedStatement.setString(3, carburant);
			preparedStatement.setInt(4, compteurKm);
			preparedStatement.setDate(5, dateMisEnCirculation);
			preparedStatement.setInt(6, numImmatriculation);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Supprimer(VoitureDao voiture) {
		this.statement=this.getStatement();
		String sql="DELETE FROM `voiture` WHERE `num_immatriculation`= ?";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setInt(1,numImmatriculation);
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int louerVoiture(VoitureDao voiture) {
		this.statement=this.getStatement();
		String sql="UPDATE `voiture` SET `code_client` = ? WHERE `num_immatriculation`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setString(1,voiture.getClient().getCodeClient());
			preparedStatement.setInt(2,voiture.getNumImmatriculation());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int RechercherParCode(VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `voiture` WHERE `num_immatriculation`= '"+voiture.getNumImmatriculation()+"'");
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
	
	public int compterLeNombreVoitureEnParking(VoitureDao voiture) {
		this.statement=this.getStatement();
		String sql="SELECT COUNT(`num_immatriculation`) FROM `voiture` WHERE `num_parking`='"+voiture.getParking().getNumParking()+"' ";
		try {
			this.resultSet=statement.executeQuery(sql);
			resultSet.next();
			return resultSet.getInt("COUNT(`num_immatriculation`)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}
	public boolean LouerOuNon(VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT 'code_client' FROM `voiture` WHERE `num_immatriculation`= '"+voiture.getNumImmatriculation()+"'");
			resultSet.next();
			if(resultSet.getString("code_client").length()==0) {
				return false;
			}
			else {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public int restituer(VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `voiture` SET `code_client` = ? WHERE `num_immatriculation`= ? ");
			preparedStatement.setString(1, null);
			preparedStatement.setInt(2,voiture.getNumImmatriculation());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public boolean RestituerOuNon(VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT 'code_client' FROM `voiture` WHERE `num_immatriculation`= '"+voiture.getNumImmatriculation()+"'");
			resultSet.next();
			if(resultSet.getString("code_client").equalsIgnoreCase(null)) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public int getNumImmatriculation() {
		return numImmatriculation;
	}
	public void setNumImmatriculation(int numImmatriculation) {
		this.numImmatriculation = numImmatriculation;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCarburant() {
		return carburant;
	}
	public void setCarburant(String carburant) {
		this.carburant = carburant;
	}
	public int getCompteurKm() {
		return compteurKm;
	}
	public void setCompteurKm(int compteurKm) {
		this.compteurKm = compteurKm;
	}
	public java.sql.Date getDateMisEnCirculation() {
		return dateMisEnCirculation;
	}
	public void setDateMisEnCirculation(java.sql.Date dateMisEnCirculation) {
		this.dateMisEnCirculation = dateMisEnCirculation;
	}
	public ClientDao getClient() {
		return client;
	}
	public void setClient(ClientDao client) {
		this.client = client;
	}
	public ParkingDao getParking() {
		return parking;
	}
	public void setParking(ParkingDao parking) {
		this.parking = parking;
	}
}
