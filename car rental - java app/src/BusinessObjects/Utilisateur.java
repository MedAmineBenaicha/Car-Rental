package BusinessObjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.UtilisateurDao;

public class Utilisateur {
	
	public ResultSet Lister() throws Exception {
		UtilisateurDao utilisateur=new UtilisateurDao();
		return utilisateur.Lister(utilisateur);
	}
	
	public int Ajouter(String nomComplet,String codeUtilisateur,String adresse,String cin,int numTel,String motDePasse) throws Exception {
		UtilisateurDao utilisateur=new UtilisateurDao(codeUtilisateur,nomComplet,adresse,cin,numTel,motDePasse);
		return utilisateur.Ajouter(utilisateur);
	}
	
	public int Modifier(String codeUtilisateur,String nomComplet,String adresse,String cin,int numTel,String motDePasse) {
		UtilisateurDao utilisateur=new UtilisateurDao(codeUtilisateur,nomComplet,adresse,cin,numTel,motDePasse);
		return utilisateur.Modifier(utilisateur);
	}
	
	public long autoID() throws SQLException {
		UtilisateurDao utilisateur=new UtilisateurDao();
		return utilisateur.autoID();
	}
	public int Supprimer(String codeClient,String nomComplet,String motDePasse) {
		UtilisateurDao utilisateur =new UtilisateurDao(codeClient,nomComplet,motDePasse);
		return utilisateur.Supprimer(utilisateur);
	}
	public ResultSet Rechercher(String nomComplet) {
		UtilisateurDao utilisateur =new UtilisateurDao(nomComplet);
		return utilisateur.Rechercher(utilisateur);
	}
	public int Login(String nomComplet,String motDePasse) {
		UtilisateurDao utilisateur=new UtilisateurDao(nomComplet,motDePasse);
		return utilisateur.Login(utilisateur);
	}
	public String getCodeUtilisateur(String nomComplet,String motDePasse) {
		UtilisateurDao utilisateur=new UtilisateurDao(nomComplet,motDePasse);
		return utilisateur.getCodeUtilisateur(utilisateur);
	}
	public int LoginAdmin(String nomComplet,String motDePasse) {
		UtilisateurDao utilisateur=new UtilisateurDao(nomComplet,motDePasse);
		return utilisateur.LoginAdmin(utilisateur);
	}
	public int Suspendre(String codeUtilisateur) {
		UtilisateurDao utilisateur=new UtilisateurDao(codeUtilisateur,"","");
		return utilisateur.Suspendre(utilisateur);
	}
	public int Restituer(String codeUtilisateur) {
		UtilisateurDao utilisateur=new UtilisateurDao(codeUtilisateur,"","");
		return utilisateur.Restituer(utilisateur);
	}
	public int getStatut(String codeUtilisateur) {
		UtilisateurDao utilisateur=new UtilisateurDao(codeUtilisateur,"","");
		return utilisateur.getStatut(utilisateur);
	}
	
}
