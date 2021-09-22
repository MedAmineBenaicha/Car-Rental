package BusinessObjects;

import java.sql.Date;
import java.sql.ResultSet;

import dao.ClientDao;
import dao.ContratDao;
import dao.FactureDao;

public class Facture {

	public int Ajouter(int numeroFacture, Date dateFacture,double montant,int numeroContrat,
			String codeClient) {
		ClientDao client=new ClientDao(codeClient,"");
		ContratDao contrat=new ContratDao(numeroContrat);
		FactureDao facture=new FactureDao(numeroFacture,dateFacture,montant,client,contrat);
		return facture.Ajouter(facture);
	}
	public int Modifier(int numeroFacture, Date dateFacture,double montant,int numeroContrat,
			String codeClient) {
		ClientDao client=new ClientDao(codeClient,"");
		ContratDao contrat=new ContratDao(numeroContrat);
		FactureDao facture=new FactureDao(numeroFacture,dateFacture,montant,client,contrat);
		return facture.Modifier(facture);
	}
	public ResultSet Rechercher(int numeroFacture) {
		FactureDao facture=new FactureDao(numeroFacture);
		return facture.Rechercher(facture);
	}
	public ResultSet Lister() {
		FactureDao facture=new FactureDao();
		return facture.Lister();
	}
	public int Supprimer(int numeroFacture) {
		FactureDao facture=new FactureDao(numeroFacture);
		return facture.Supprimer(facture);
	}
	public long AutoId() {
		FactureDao facture=new FactureDao();
		return facture.autoID();
	}
}
