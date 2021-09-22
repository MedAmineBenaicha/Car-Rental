package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class SanctionDao extends ConnectionStatement {
	
	private Statement statement=null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private Date dateEcheance;
	private int num_sanction;
	private int num_contrat;
	private int ammende;
	
	public SanctionDao(int num_sanction,int num_contrat,Date dateEcheance, int ammende) {
		super();
		this.dateEcheance = dateEcheance;
		this.num_sanction = num_sanction;
		this.num_contrat=num_contrat;
		this.ammende = ammende;
	}
	public SanctionDao(int num_contrat,Date dateEcheance, int ammende) {
		super();
		this.dateEcheance = dateEcheance;
		this.num_contrat=num_contrat;
		this.ammende = ammende;
	}
	public SanctionDao(int num_sanction,int num_contrat) {
		this.num_sanction=num_sanction;
		this.num_contrat=num_contrat;
	}
	public SanctionDao(int num_contrat) {
		this.num_contrat=num_contrat;
	}
	public SanctionDao() {
		super();
	}
	
	public ResultSet Rechercher(SanctionDao sanction) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `sanction` WHERE `num_sanction`='"+sanction.getNum_sanction()+"' ORDER BY `date_echeance`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int Ajouter(SanctionDao sanction) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("INSERT INTO `sanction`(`num_contrat`, `date_echeance`, `amende`) VALUES (?,?,?) ");
			preparedStatement.setInt(1, sanction.getNum_contrat());
			preparedStatement.setDate(2,sanction.getDateEcheance());
			preparedStatement.setInt(3, sanction.getAmende());
			preparedStatement.executeUpdate();
			//client.getReservations().add(reservation);
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int calculerAmmende() {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `sanction` ORDER BY `date_echeance`");
			java.sql.Date dateActuelle = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			while(this.resultSet.next()) {
				long difference = dateActuelle.getTime() - (this.resultSet.getDate("date_echeance")).getTime();
				float nbDeJours=(difference / (1000*60*60*24));
				if(nbDeJours < 0 ) {
					break;
					// Ça veut dire que la date d'échéance n'a pas depacé la date actuelle !!!
				}
				preparedStatement=this.getCon().prepareStatement("UPDATE `sanction` SET `amende`= ?  WHERE `num_sanction` = ? ");
				preparedStatement.setInt(1, (int) nbDeJours*2000);
				preparedStatement.setInt(2, this.resultSet.getInt("num_sanction"));
				preparedStatement.executeUpdate();
			}
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public ResultSet Afficher() {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `sanction` WHERE `amende` > 0 ");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String getNomClient(SanctionDao sanction) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT `code_client` FROM `contrat` WHERE `num_contrat` ="+sanction.getNum_contrat()+" ");
			if(this.resultSet.next()) {
				return this.resultSet.getString("code_client");
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public int Payer(SanctionDao sanction) {
		this.statement=this.getStatement();
		String sql="DELETE FROM `sanction` WHERE `num_sanction`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setInt(1, sanction.getNum_sanction());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public int getAmende() {
		return ammende;
	}

	public void setAmende(int amende) {
		this.ammende = amende;
	}

	public int getNum_sanction() {
		return num_sanction;
	}

	public void setNum_sanction(int num_sanction) {
		this.num_sanction = num_sanction;
	}

	public int getNum_contrat() {
		return num_contrat;
	}

	public void setNum_contrat(int num_contrat) {
		this.num_contrat = num_contrat;
	}
	
}
