package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;

/**
 * @author Mariam
 *		La classe réprésente le compte d'un  joueur 
 */
public class Compte {
	
	//Coucou Cisse
	
	/**
	 * le solde du compte 
	 */
	private int solde;

	/**
	 * 	Construit un compte avec un solde initial 
	 * 		@param solde le solde initial du compte 
	 */
	public Compte(int solde)  throws BadParametersException {
		if(solde>=0 )
		this.solde = solde;
		else
			throw new BadParametersException( solde +" n'est pas un solde valide dans compte(..) ");
		
	}

	/**
	 * Construit un compte avec un solde egal à 0
	 * 
	 */
	public Compte() {
		this.solde = 0;
	}

	/**
	 * @return le solde du compte 
	 */
	public int getSolde() {
		return solde;
	}

	/**
	 * Met a jour le solde du compte 
	 * 		@param solde le nouveau solde du compte
	 */
	public void setSolde(int solde) {
		if (solde>=0)
		this.solde = solde;
	}
	/**
	 *Enlève un montant  du solde d'un compte 
	 * 		@param  montant le montant à enlever 
	 * 		@throws BadParametersException
	 */
	public void debiterCompte(int montant) throws BadParametersException{
		if (montant<=solde && montant>=0){
			this.solde = solde-montant; 
		}else{
			throw new BadParametersException( montant +" est inferieur au solde "+this.solde +"ou n'est pas valide dans debiter compte"); 
			
		}
	}
	/**
	 * Ajoute un  montant  au  solde du compe 
	 * 		@param montant la somme a ajouté.
	 * 		@throws BadParametersException
	 */
	public void crebiterCompte(int montant) throws BadParametersException{
		if (montant>=0){
			this.solde = solde+montant; 
		}else{
			throw new BadParametersException( montant +" est un montant pas valide dans crediter(..) "); 
			
		}
	}
	
	

}
