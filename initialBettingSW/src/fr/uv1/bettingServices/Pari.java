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
<<<<<<< HEAD
	public void setSubscriber(Subscriber subscriber) throws BadParametersException {
		if (subscriber==null)
			throw new BadParametersException("Le joueur n'a pas été instancié");
		this.subscriber = subscriber;
	}

	/**
	 * @return La competition sur laquelle est fait le pari
	 */
=======
	public void setSubscriber(Subscriber subscriber) throws SubscriberException {
		if(subscriber==null) throw new SubscriberException( null +"");
		this.subscriber = subscriber;
	}
/*
	
	*//**
	 * @return La competition sur laquell est fait le pari
	 *//*
>>>>>>> 9c2186010aaa271256aec8bf09b309ecd02a771a
	public Competition getCompetition() {
		return competition;
	}

	*//**
	 * @param competition La nouvelle compétition
<<<<<<< HEAD
	 * @throws BadParametersException 
	 */
	public void setCompetition(Competition competition) throws BadParametersException {
		if (competition==null)
			throw new BadParametersException("La compétition n'a pas été instanciée");
=======
	 *//*
	public void setCompetition(Competition competition) {
>>>>>>> 9c2186010aaa271256aec8bf09b309ecd02a771a
		this.competition = competition;
	}*/
	
	

}
