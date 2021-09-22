package BusinessObjects;

import java.sql.Date;
import java.sql.ResultSet;

import dao.ClientDao;
import dao.ReservationDao;

public class Reservation {
	
	public int Ajouter(String codeReservation, Date dateReservation, Date dateDepart, Date dateRetour,String codeClient,String nomComplet) {
		ReservationDao reservation=new ReservationDao(codeReservation,dateReservation,dateDepart,dateRetour);
		ClientDao client=new ClientDao(codeClient,nomComplet);
		return reservation.Ajouter(reservation,client);
	}
	public int Modifier(String codeReservation, Date dateReservation, Date dateDepart, Date dateRetour,String codeClient,String nomComplet) {
		ReservationDao reservation=new ReservationDao(codeReservation,dateReservation,dateDepart,dateRetour);
		ClientDao client=new ClientDao(codeClient,nomComplet);
		return reservation.Modifier(reservation,client);
	}
	public ResultSet Rechercher(String codeReservation) {
		ReservationDao reservation =new ReservationDao(codeReservation);
		return reservation.Rechercher(reservation);
	}
	public ResultSet Lister() {
		ReservationDao reservation=new ReservationDao();
		return reservation.Lister();
	}
	public int Supprimer(String codeReservation) {
		ReservationDao reservation=new ReservationDao(codeReservation);
		return reservation.Supprimer(reservation);
	}
	public long AutoId() {
		ReservationDao reservation=new ReservationDao();
		return reservation.autoID();
	}
	public ResultSet AfficherParListe(String validation) {
		ReservationDao reservation =new ReservationDao();
		return reservation.AfficherParListe(reservation,validation);
	}
	public int Valider(String codeReservation,String validation) {
		ReservationDao reservation =new ReservationDao(codeReservation,validation);
		return reservation.Valider(reservation);
	}
	public String RechercherParCodeReservation(String codeReservation) {
		ReservationDao reservation=new ReservationDao(codeReservation);
		return reservation.RechercherParCodeReservation(reservation);
	}
	public void VerifierUneReservation() {
		ReservationDao reservation=new ReservationDao();
		reservation.VerifierUneReservation(reservation);
	}
}
