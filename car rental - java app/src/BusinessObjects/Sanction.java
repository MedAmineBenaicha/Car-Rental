package BusinessObjects;

import java.sql.Date;
import java.sql.ResultSet;

import dao.SanctionDao;

public class Sanction {
	
	public int Ajouter(int numeroContrat, Date dateEcheance, int ammende) {
		SanctionDao sanction=new SanctionDao(numeroContrat,dateEcheance,ammende);
		return sanction.Ajouter(sanction);
	}
	
	public int calculerAmmende() {
		SanctionDao sanction = new SanctionDao();
		return sanction.calculerAmmende();
	}
	
	public ResultSet Afficher() {
		SanctionDao sanction = new SanctionDao();
		return sanction.Afficher();
	}
	
	public String getNomClient(int numContrat) {
		SanctionDao sanction = new SanctionDao (numContrat);
		return sanction.getNomClient(sanction);
	}
	
	public int Payer(int numSanction,int numContrat) {
		SanctionDao sanction = new SanctionDao(numSanction,numContrat);
		return sanction.Payer(sanction);
	}
}
