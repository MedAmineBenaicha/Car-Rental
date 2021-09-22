package BusinessObjects;

import java.sql.ResultSet;

import dao.ClientDao;
import dao.VoitureDao;

public class Voiture {
	
	public int AjouterVoiture(int numImmatriculation, String marque, String type, String carburant, int compteurKm,java.sql.Date dateMisEnCirculation) {
		VoitureDao voiture =new VoitureDao(numImmatriculation,marque,type,carburant,compteurKm,dateMisEnCirculation);
		return voiture.Ajouter(voiture);
	}
	public int ModifierVoiture(int numImmatriculation, String marque, String type, String carburant, int compteurKm,java.sql.Date dateMisEnCirculation) {
		VoitureDao voiture =new VoitureDao(numImmatriculation,marque,type,carburant,compteurKm,dateMisEnCirculation);
		return voiture.Modifier(voiture);
	}
	public int SupprimerVoiture(int numImmatriculation) {
		VoitureDao voiture =new VoitureDao(numImmatriculation);
		return voiture.Supprimer(voiture);
	}
	public ResultSet RechercherVoiture (int numImmatriculation) {
		VoitureDao voiture =new VoitureDao(numImmatriculation);
		return voiture.Rechercher(voiture);
	}
	public ResultSet ListerVoiture() {
		VoitureDao voiture =new VoitureDao();
		return voiture.Lister();
	}
	public int LouerVoiture(int numImmatriculation,String codeClient,String nomComplet) {
		ClientDao client=new ClientDao(codeClient,nomComplet);
		VoitureDao voiture =new VoitureDao(numImmatriculation,client);
		return voiture.louerVoiture(voiture);
	}
	public int RechercherVoitureParCode(int numeroImmatriculation) {
		VoitureDao voiture=new VoitureDao(numeroImmatriculation);
		return voiture.RechercherParCode(voiture);
	}
	public boolean LouerOuNon(int numeroImmatriculation) {
		VoitureDao voiture=new VoitureDao(numeroImmatriculation);
		return voiture.LouerOuNon(voiture);
	}
	public int Restituer(int numeroImmatriculation) {
		VoitureDao voiture=new VoitureDao(numeroImmatriculation);
		return voiture.restituer(voiture);
	}
	public boolean RestituerOuNon(int numeroImmatriculation) {
		VoitureDao voiture=new VoitureDao(numeroImmatriculation);
		return voiture.LouerOuNon(voiture);
	}
}
