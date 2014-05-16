package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;

public class Pari {
	/** Le nombre de jetons misé par le joueur */
	private long mise;
	private Subscriber subscriber;


	/**
	 * @param mise
	 * @throws BadParametersException 
	 * @throws SubscriberException 
	 */
	public Pari(long mise, Subscriber subscriber) throws BadParametersException, SubscriberException {
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
	 * @param mise Le nouveau nombre de jetons
	 * @throws BadParametersException 
	 */
	public void setMise(long mise) throws BadParametersException {
		if (mise<=0)
			throw new BadParametersException("Le nombre de jetons"+ mise +" est invalide");
		this.mise = mise;
	}

	/**
	 * @return Le joueur
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @param subscriber Le nouveau joueur
	 * @throws BadParametersException 
	 */
	public void setSubscriber(Subscriber subscriber) throws SubscriberException {
		if(subscriber==null) throw new SubscriberException( null +"");
		this.subscriber = subscriber;
	}
/*
	
	*//**
	 * @return La competition sur laquell est fait le pari
	 *//*
	public Competition getCompetition() {
		return competition;
	}

	*//**
	 * @param competition La nouvelle compétition
	 *//*
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}*/
	
	

}
