package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;

/**
 * @author Mariam
 *		La classe represente le compte d'un  joueur 
 */
public class Compte {
	
	/**
	 * le solde du compte 
	 */
	private long solde;

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
	public long getSolde() {
		return solde;
	}

	/**
	 * Met a jour le solde du compte 
	 * 		@param solde  le nouveau solde du compte
	 * 		@throws BadParametersException 
	 * 
	 */
	public void setSolde(long solde) throws BadParametersException {
		if (solde>=0)
			this.solde = solde;
		else
			throw new BadParametersException(solde +" n'est pas un solde valide dans setSolde() ");
	}
	/**
	 *Enleve un montant  du solde d'un compte 
	 * 		@param  montant le montant ‡ enlever 
	 * 		@throws BadParametersException
	 * 				est levé si le montant est negative
	 * 		@throws SubscriberException 
	 * 				est levé le montant > au solde du compte
	 */
	public void debiterCompte(long montant) throws BadParametersException, SubscriberException {
		if (montant<=this.solde && montant>=0){
			this.solde = this.solde-montant; 
		}else if (montant<0){
			throw new BadParametersException( montant +" est inferieur au solde "+this.solde +"ou n'est pas valide dans debiter compte");
		}else{
			throw new SubscriberException("Vous n'avez pas assez de jetons");
			
		}
	}
	/**
	 * Ajoute un  montant  au  solde du compe 
	 * 		@param montant la somme a ajouté.
	 * 		@throws BadParametersException
	 * 				est levée si le montant a credité es <0
	 */				
	public void crediterCompte(long montant) throws BadParametersException{
		if (montant>=0){
			this.solde = solde+montant; 
		}else{
			throw new BadParametersException( montant +" est un montant pas valide dans crediter(..) "); 
			
		}
	}
}
