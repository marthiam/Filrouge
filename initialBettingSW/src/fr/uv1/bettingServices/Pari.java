package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;

public class Pari {
	/** Le nombre de jetons misé par le joueur */
	private long mise;
	private Subscriber subscriber;
	private Competition competition;

	/**
	 * @param mise
	 * @throws BadParametersException 
	 */
	public Pari(long mise, Subscriber subscriber, Competition competition) throws BadParametersException {
		super();
		this.setMise(mise);
		this.setSubscriber(subscriber);
		this.setCompetition(competition);
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
		if (mise<0)
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
	public void setSubscriber(Subscriber subscriber) throws BadParametersException {
		if (subscriber==null)
			throw new BadParametersException("Le joueur n'a pas été instancié");
		this.subscriber = subscriber;
	}

	/**
	 * @return La competition sur laquelle est fait le pari
	 */
	public Competition getCompetition() {
		return competition;
	}

	/**
	 * @param competition La nouvelle compétition
	 * @throws BadParametersException 
	 */
	public void setCompetition(Competition competition) throws BadParametersException {
		if (competition==null)
			throw new BadParametersException("La compétition n'a pas été instanciée");
		this.competition = competition;
	}
	
	

}
