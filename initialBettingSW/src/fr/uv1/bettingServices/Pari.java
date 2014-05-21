package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;

public class Pari {
	/** Le nombre de jetons misé par le joueur */
	private long mise;
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
