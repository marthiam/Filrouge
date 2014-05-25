package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;


public class Pari {
	
	/** L'identifiant du pari */
	private int pari_id;
	
	/** Le nombre de jetons misé par le joueur */
	private long mise;
	
	/** Le joueur qui fait ce pari */
	private Subscriber subscriber;
	
	/** La  competition du  pari */
	private Competition competition;
	



	/** L'identifiant de la compétition sur laquelle est fait le pari */
	private int competition_id;
	
	
	/**
	 * Constructeur  
	 * @param mise
	 * 			le montant a misé 
	 *  @param subscriber 
	 *  		le joueur qui pari 
	 * @throws BadParametersException 
	 * 			est levée la mise est <=0 ou si le subscriber est null 
	 *
	 */
	public Pari(long mise, Subscriber subscriber) throws BadParametersException{
		super();
		this.setMise(mise);
		this.setSubscriber(subscriber);
		
	}
	
	
	
	
	/**
	 * Renvoie l'identifiant du pari
	 *		 @return l'identifiant du pari
	 */
	public int getPari_id() {
		return this.pari_id;
	}




	/**
	 * Met à jour l'identifiant du pari 
	 * 		@param pari_id le nouvel identifiant du pari
	 */
	public void setPari_id(int pari_id) {
		this.pari_id = pari_id;
	}


	/**
	 * Renvoie la competition du pari
	 *		 @return la competition du pari 
	 */
	public Competition getCompetition() {
		return competition;
	}


	/**
	 * La competition du pari 
	 * 			@param competition la nouvelle competition 
	 */
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}




	/**
	 * Renvoie la mise du pari 
	 * 			@return Le nombre de jetons misé par le joueur
	 */
	public long getMise() {
		return mise;
	}

	/**
	 * Met à jour la mise 
	 * 			@param mise Le nouveau nombre de jetons
	 * 			@throws BadParametersException 
	 */
	public void setMise(long mise) throws BadParametersException {
		if (mise<=0)
			throw new BadParametersException("Le nombre de jetons"+ mise +" est invalide");
		this.mise = mise;
	}

	/**
	 * Renvoie le parieur 
	 * 			@return Le joueur
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * Met à jour le joueur ayant parié 
	 * 			@param subscriber Le nouveau joueur
	 * 			@throws BadParametersException 
	 * 			est levée si le joueur est non instancié .
	 */
	public void setSubscriber(Subscriber subscriber) throws BadParametersException {
		if (subscriber==null)
			throw new BadParametersException("Le joueur n'a pas été instancié");
		this.subscriber = subscriber;
	}



}
