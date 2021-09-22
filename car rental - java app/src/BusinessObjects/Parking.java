package BusinessObjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.ParkingDao;
import dao.VoitureDao;

public class Parking {
	
	public int AjouterParking(int numParking,int capacite,String rue,String arrondissement) {
		ParkingDao parking =new ParkingDao(numParking,capacite,rue,arrondissement);
		return parking.Ajouter(parking);
	}
	public int ModifierParking(int numParking,int capacite,String rue,String arrondissement) {
		ParkingDao parking =new ParkingDao(numParking,capacite,rue,arrondissement);
		return parking.Modifier(parking);
	}
	public int SupprimerParking(int numParking,int capacite) throws SQLException {
	    ParkingDao parking =new ParkingDao(numParking,capacite);
	    VoitureDao voiture=new VoitureDao(0,null,parking);
	    voiture.getParking().setNumParking(numParking);
	    int recordNumber = parking.AfficherPlacesVides(parking,voiture);
		if(recordNumber==parking.getCapacite()) {
			return parking.Supprimer(parking);
		}
		else {
			System.out.println("Alert ce parking contient des véhicules!!");
			return 0;
		}
	}
	public ResultSet RechercherParking(int numParking) {
	    ParkingDao parking =new ParkingDao(numParking);
		return parking.Rechercher(parking);
	}
	public ResultSet AfficherVoituresdeParking(int numParking) {
	    ParkingDao parking =new ParkingDao(numParking);
		return parking.AfficherVoitures();
	}
	public int AfficherPlaceVidedeParking(int numParking) {
	    ParkingDao parking =new ParkingDao(numParking);
	    VoitureDao voiture=new VoitureDao(0,null,parking);
		return parking.AfficherPlacesVides(parking,voiture);
	}
	public int DeposerVoiture(int numParking,int numeroImmatriculation) throws SQLException {
		ParkingDao parking=new ParkingDao(numParking);
		//parking.setCapacite(parking.CapaciteParking(parking));
		VoitureDao voiture=new VoitureDao(numeroImmatriculation,null,parking);
		voiture.getParking().setNumParking(numParking);
		int recordNumber = parking.AfficherPlacesVides(parking,voiture);
		if(recordNumber==0) {
			System.out.println("le parking est plein vous ne pouvez pas ajouter des véhicules");
			return 0;
		}
		else {
			return parking.Deposer(parking,voiture);
		}
	}
	public int SortirVoiture(int numParking,int numeroImmatriculation) {
	    ParkingDao parking =new ParkingDao(numParking);
		VoitureDao voiture=new VoitureDao(numeroImmatriculation);
		return parking.Sortir(parking,voiture);
	}
	public long autoId() {
		ParkingDao parking=new ParkingDao();
		return parking.autoID();
	}
	public ResultSet Lister() {
		ParkingDao parking=new ParkingDao();
		return parking.Lister();
	}
	public int RechercherParNumeroParking(int numeroParking) {
		ParkingDao parking=new ParkingDao(numeroParking);
		return parking.RechercherParCode(parking);
	}
}
