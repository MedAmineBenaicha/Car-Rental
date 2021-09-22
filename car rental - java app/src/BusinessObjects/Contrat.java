package BusinessObjects;

import java.sql.Date;
import java.sql.ResultSet;

import dao.ClientDao;
import dao.ContratDao;

public class Contrat {
	
	public int Ajouter(int numeroContrat, Date dateContrat, Date dateEcheance,
			String codeClient) {
		ClientDao client=new ClientDao(codeClient,"");
		ContratDao contrat=new ContratDao(numeroContrat,dateContrat,dateEcheance,client);
		return contrat.Ajouter(contrat);
	}
	public int Modifier(int numeroContrat, Date dateContrat, Date dateEcheance,
			String codeClient) {
		ClientDao client=new ClientDao(codeClient,"");
		ContratDao contrat=new ContratDao(numeroContrat,dateContrat,dateEcheance,client);
		return contrat.Modifier(contrat);
	}
	public ResultSet Rechercher(int numeroContrat) {
		ContratDao contrat=new ContratDao(numeroContrat);
		return contrat.Rechercher(contrat);
	}
	public ResultSet Lister() {
		ContratDao contrat=new ContratDao();
		return contrat.Lister();
	}
	public int Supprimer(int numeroContrat) {
		ContratDao contrat=new ContratDao(numeroContrat);
		return contrat.Supprimer(contrat);
	}
	public long AutoId() {
		ContratDao contrat=new ContratDao();
		return contrat.autoID();
	}
	public String RetournerCodeClient(int numeroContrat) {
		ContratDao contrat=new ContratDao(numeroContrat);
		return contrat.RetournerCodeClient(contrat);
	}
	public int RechercherParNumeroContrat(int numeroContrat) {
		ContratDao contrat=new ContratDao(numeroContrat);
		return contrat.RechercherParNumeroContrat(contrat);
	}

}
