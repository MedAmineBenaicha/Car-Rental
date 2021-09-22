package BusinessObjects;

import java.io.InputStream;
import java.sql.ResultSet;
import dao.ClientDao;

public class Client {
	
	
	public int AjouterClient(String codeClient, String nomComplet, String adresse,String cin, int numTelephon, InputStream imagePermis) {
		ClientDao client =new ClientDao(codeClient,nomComplet,adresse,cin,numTelephon,imagePermis);
		return client.Ajouter(client);
	}
	public int ModifierClient(String codeClient, String nomComplet, String adresse,String cin, int numTelephon) {
		ClientDao client =new ClientDao(codeClient,nomComplet,adresse,cin,numTelephon);
		return client.Modifier(client);
	}
	public int SupprimerClient(String codeClient,String nomComplet) {
		ClientDao client =new ClientDao(codeClient,nomComplet);
		return client.Supprimer(client);
	}
	public ResultSet RechercherClient(String nomComplet) {
		ClientDao client =new ClientDao(nomComplet);
		return client.Rechercher(client);
	}
	public ResultSet ListerClient() {
		ClientDao client =new ClientDao();
		return client.Lister();
	}
	public long AutoId() {
		ClientDao client =new ClientDao();
		return client.autoID();
	}
	public int RechercherParCodeClient(String codeClient,String nomComplet) {
		ClientDao client =new ClientDao(codeClient,nomComplet);
		return client.RechercherParCodeClient(client);
	}
	public ClientDao ClientDao(String codeClient,String nomComplet) {
		return new ClientDao(codeClient,nomComplet);
	}
	public int ChercherContratParClient(String codeClient,String nomComplet) {
		ClientDao client =new ClientDao(codeClient,nomComplet);
		return client.ChercherContratParClient(client);
	}
	public String getNomClient(String codeClient) {
		ClientDao client = new ClientDao(codeClient,"");
		return client.getClientName(client);
	}
}
