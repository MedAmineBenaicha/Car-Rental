package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ParkingDao extends ConnectionStatement {
	private Statement statement=null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private int numParking;
	private int capacite;
	private String rue;
	private String arrondissement;
	private ArrayList<VoitureDao> voitures=new ArrayList<VoitureDao>(capacite);
	
	public ParkingDao(int numParking,int capacite,String rue,String arrondissement) {
		this.setNumParking(numParking);
		this.setCapacite(capacite);
		this.setRue(rue);
		this.setArrondissement(arrondissement);
	}
	public ParkingDao(int numParking,int capacite) {
		this.setNumParking(numParking);
		this.setCapacite(capacite);
	}
	public ParkingDao(int numParking) {
		this.setNumParking(numParking);
	}
	
	public ParkingDao() {
		
	}

	public int getNumParking() {
		return numParking;
	}
	public void setNumParking(int numParking) {
		this.numParking = numParking;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getArrondissement() {
		return arrondissement;
	}
	public void setArrondissement(String arrondissement) {
		this.arrondissement = arrondissement;
	}
	public ArrayList<VoitureDao> getVoitures() {
		return voitures;
	}
	public void setVoitures(ArrayList<VoitureDao> voitures) {
		this.voitures = voitures;
	}
	
	public int Ajouter(ParkingDao parking) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("INSERT INTO `parking`(`num_parking`, `capacite`, `rue`, `arrondissement`) VALUES (?,?,?,?) ");
			preparedStatement.setInt(1, parking.getNumParking());
			preparedStatement.setInt(2, parking.getCapacite());
			preparedStatement.setString(3, parking.getRue());
			preparedStatement.setString(4, parking.getArrondissement());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public ResultSet Lister(){
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `parking` ORDER BY `num_parking`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int Modifier(ParkingDao parking) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `parking` SET `capacite`= ? ,"
					+ "`rue`= ? ,`arrondissement`= ? WHERE `num_parking`= ? ");
			preparedStatement.setInt(1, parking.getCapacite());
			preparedStatement.setString(2, parking.getRue());
			preparedStatement.setString(3, parking.getArrondissement());
			preparedStatement.setInt(4, parking.getNumParking());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Supprimer(ParkingDao parking) {
		this.statement=this.getStatement();
		String sql="DELETE FROM `parking` WHERE `num_parking`= ?";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setInt(1, numParking);
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public ResultSet AfficherVoitures(){
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `voiture` WHERE `num_parking`='"+numParking+"' ");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int AfficherPlacesVides(ParkingDao parking,VoitureDao voiture){
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT `capacite` FROM `parking` WHERE `num_parking`='"+parking.numParking+"' ");
			resultSet.next();
			return resultSet.getInt("capacite")-voiture.compterLeNombreVoitureEnParking(voiture);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Sortir(ParkingDao parking,VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `voiture` SET `num_parking`= ? WHERE `num_parking`= ? AND `num_immatriculation`= ?");
			preparedStatement.setString(1, null);
			preparedStatement.setInt(2, parking.getNumParking());
			preparedStatement.setInt(3, voiture.getNumImmatriculation());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Deposer(ParkingDao parking ,VoitureDao voiture) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `voiture` SET `num_parking`= ? WHERE `num_immatriculation`= ?");
			preparedStatement.setInt(1, parking.getNumParking());
			preparedStatement.setInt(2, voiture.getNumImmatriculation());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int RechercherParCode(ParkingDao parking) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `parking` WHERE `num_parking`= '"+parking.getNumParking()+"'");
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
	public int CapaciteParking(ParkingDao parking) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT `capacite` FROM `parking` WHERE `num_parking`='"+parking.getNumParking()+"'");
			resultSet.next();
			return resultSet.getInt("capacite");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public ResultSet Rechercher(ParkingDao parking) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `parking` WHERE `num_parking`='"+numParking+"'");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet CreateTable(){
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("CREATE TABLE Parkingvehiculs as (SELECT * FROM `parking` WHERE `num_parking` LIKE '"+numParking+"')");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int SetNull() {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `Parkingvehicules` SET `num_parking`= NULL");
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public long autoID() {
		this.statement=this.getStatement();
		String sql="select Max(num_parking) from parking";
		 try {
			this.resultSet=statement.executeQuery(sql);
			 resultSet.next();
			 resultSet.getString("Max(num_parking)");
			if(resultSet.getString("Max(num_parking)")==null) {
				return 0;
			}
			else {
				long id=Long.parseLong(resultSet.getString("Max(num_parking)"));
				id++;
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}

