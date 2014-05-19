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

=======
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
	public void setSubscriber(Subscriber subscriber) throws BadParametersException {
		if (subscriber==null)
			throw new BadParametersException("Le joueur n'a pas été instancié");
		this.subscriber = subscriber;
	}

<<<<<<< HEAD

=======
	
/*
	
	*//**
	 * @return La competition sur laquell est fait le pari
	 *//*
>>>>>>> 9c2186010aaa271256aec8bf09b309ecd02a771a
	public Competition getCompetition() {
		return competition;
	}

	/*
	public void setCompetition(Competition competition) {
>>>>>>> 9c2186010aaa271256aec8bf09b309ecd02a771a
		this.competition = competition;
	}*/
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
	
	

}
