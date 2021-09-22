package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationDao extends ConnectionStatement{
	
	private Statement statement=null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	private String codeReservation;
	private Date dateReservation;
	private Date dateDepart;
	private Date dateRetour;
	private String validation;
	//chaque réservation à un client!!
	private ClientDao client;
	
	public ReservationDao(String codeReservation, Date dateReservation, Date dateDepart, Date dateRetour,
			String validation) {
		this.codeReservation = codeReservation;
		this.dateReservation = dateReservation;
		this.dateDepart = dateDepart;
		this.dateRetour = dateRetour;
		this.validation = validation;
	}

	public ReservationDao(String codeReservation, Date dateReservation, Date dateDepart, Date dateRetour) {
		this.codeReservation = codeReservation;
		this.dateReservation = dateReservation;
		this.dateDepart = dateDepart;
		this.dateRetour = dateRetour;
	}
	public ReservationDao(String codeReservation,String validation) {
		this.codeReservation = codeReservation;
		this.validation=validation;
	}
	
	public ReservationDao(String codeReservation) {
		this.codeReservation = codeReservation;
	}
	public ReservationDao() {
		
	}
	public ResultSet Lister(){
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `reservation` ORDER BY `date_reservation`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int Ajouter(ReservationDao reservation,ClientDao client) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("INSERT INTO `reservation`(`code_reservation`, `date_reservation`, `date_depart`, `date_retour`,`validation`,`code_client`) VALUES (?,?,?,?,?,?) ");
			preparedStatement.setString(1, reservation.getCodeReservation());
			preparedStatement.setDate(2, reservation.getDateReservation());
			preparedStatement.setDate(3, reservation.getDateDepart());
			preparedStatement.setDate(4, reservation.getDateRetour());
			preparedStatement.setString(5, "non valideé");
			preparedStatement.setString(6, client.getCodeClient());
			preparedStatement.executeUpdate();
			//client.getReservations().add(reservation);
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public ResultSet Rechercher(ReservationDao reservation) {
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `reservation` WHERE `code_reservation`='"+reservation.getCodeReservation()+"' ORDER BY `date_reservation`");
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int Modifier(ReservationDao reservation,ClientDao client) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `reservation` SET `date_reservation`= ? ,`date_depart`= ? ,"
					+ "`date_retour`= ? , `code_client`= ?  WHERE `code_reservation`= ? ");
			preparedStatement.setDate(1, reservation.dateReservation);
			preparedStatement.setDate(2, reservation.getDateDepart());
			preparedStatement.setDate(3, reservation.getDateRetour());
			preparedStatement.setString(4, client.getCodeClient());
			preparedStatement.setString(5, reservation.getCodeReservation());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Supprimer(ReservationDao reservation) {
		this.statement=this.getStatement();
		String sql="DELETE FROM `reservation` WHERE `code_reservation`= ? ";
		try {
			preparedStatement=this.getCon().prepareStatement(sql);
			preparedStatement.setString(1, reservation.getCodeReservation());
			preparedStatement.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int Valider(ReservationDao reservation) {
		this.statement=this.getStatement();
		try {
			preparedStatement=this.getCon().prepareStatement("UPDATE `reservation` SET `validation`= ? WHERE `code_reservation`= ? ");
			preparedStatement.setString(1, reservation.getValidation());
			preparedStatement.setString(2, reservation.getCodeReservation());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public long autoID() {
		this.statement=this.getStatement();
		String sql="select Max(code_reservation) from reservation";
		 try {
			this.resultSet=statement.executeQuery(sql);
			 resultSet.next();
			 resultSet.getString("Max(code_reservation)");
			if(resultSet.getString("Max(code_reservation)")==null) {
				return 0;
			}
			else {
				long id=Long.parseLong(resultSet.getString("Max(code_reservation)").substring(2, resultSet.getString("Max(code_reservation)").length()));
				id++;
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public ResultSet AfficherParListe(ReservationDao reservation,String validation) {
		this.statement=this.getStatement();
		String sql="";
		switch(validation) {
		case "validée":
			sql="SELECT * FROM `reservation` WHERE `validation`='valideé' ORDER BY `date_reservation`";
			break;
		case "non validée":
			sql="SELECT * FROM `reservation` WHERE `validation`='non valideé' ORDER BY `date_reservation`";
			break;
		case "annulée":
			sql="SELECT * FROM `reservation` WHERE `validation`='annulée' ORDER BY `date_reservation`";
			break;
		}
		try {
			this.resultSet=statement.executeQuery(sql);
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String RechercherParCodeReservation(ReservationDao reservation) {
		String codeReservation=reservation.getCodeReservation();
		java.util.Date uDate = new java.util.Date();
		java.sql.Date dateReservation1 = new java.sql.Date(uDate.getTime());
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `reservation` WHERE `code_reservation`='"+codeReservation+"' ");
			if(resultSet.next()) {
				String validation=resultSet.getString("validation");
				String codeClient=resultSet.getString("code_client");
				java.sql.Date dateReservation=resultSet.getDate("date_reservation");
				if(validation.equals("validée")) {
					if(Math.abs(dateReservation1.getTime()-dateReservation.getTime()) > 2) {
						return codeClient;
					}
					else {
						return "dateNonValide";
					}
				}
				else {
					return "ReservationNonValide";
				}
			}
			else {
				return null;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public void VerifierUneReservation(ReservationDao reservation) {
		java.util.Date uDate = new java.util.Date();
		java.sql.Date dateAujourdhui= new java.sql.Date(uDate.getTime());
		this.statement=this.getStatement();
		try {
			this.resultSet=statement.executeQuery("SELECT * FROM `reservation` ");
			if(resultSet.next()) {
				String codeReservation=resultSet.getString("code_reservation");
				String validation=resultSet.getString("validation");
				//String codeClient=resultSet.getString("code_client");
				java.sql.Date dateDepart=resultSet.getDate("date_depart");
				if(Math.abs(dateDepart.getTime()-dateAujourdhui.getTime()) < 2) {
					if(validation.equals("validée")) {
						
					}
					else {
						preparedStatement=this.getCon().prepareStatement("UPDATE `reservation` SET `validation`= ? WHERE `code_reservation`= ? ");
						preparedStatement.setString(1, "annulée");
						preparedStatement.setString(2, codeReservation);
						preparedStatement.executeUpdate();
					}
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCodeReservation() {
		return codeReservation;
	}

	public void setCodeReservation(String codeReservation) {
		this.codeReservation = codeReservation;
	}

	public Date getDateReservation() {
		return dateReservation;
	}

	public void setDateReservation(Date dateReservation) {
		this.dateReservation = dateReservation;
	}

	public Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public ClientDao getClient() {
		return client;
	}

	public void setClient(ClientDao client) {
		this.client = client;
	}

}
