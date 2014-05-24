package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;


public class Pari {
	
	/** L'identifiant du pari */
	private int pari_id;
	
	/** Le nombre de jetons misé par le joueur */
	private long mise;
	
	/** Le joueur qui fait ce pari */
	private Subscriber subscriber;


	/**
	 * Constructeur 
	 * @param mise
	 * 			le montant a misé 
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
	 * @return l'identifiant du pari
	 */
	public int getPari_id() {
		return this.pari_id;
	}




	/**
	 * @param pari_id le nouvel identifiant du pari
	 */
	public void setPari_id(int pari_id) {
		this.pari_id = pari_id;
	}




	/**
	 * @return Le nombre de jetons misé par le joueur
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
	 * 
	 * @return Le joueur
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @param subscriber Le nouveau joueur
	 * @throws BadParametersException 
	 */
	public void setSubscriber(Subscriber subscriber) throws BadParametersException {
		if (subscriber==null)
			throw new BadParametersException("Le joueur n'a pas été instancié");
		this.subscriber = subscriber;
	}



}
