package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;

public class PariPodium extends Pari{

	/** Le vainqueur de la competition */
	private Competitor winner;
	/** Le deuxi�me de la comp�tition */
	private Competitor second;
	/** Le troisi�me de la comp�tition */
	private Competitor third;
	
	
	/**
	 * @param mise Le nombre de jetons mis� par le joueur
	 * @param winner Le vainqueur de la competition
	 * @param second Le deuxi�me de la comp�tition
	 * @param third Le troisi�me de la comp�tition
	 * @throws BadParametersException 
	 * @throws SubscriberException 
	 */
	public PariPodium(long mise,Subscriber subscriber,
			Competitor winner, Competitor second, Competitor third) 
					throws BadParametersException{

		super(mise, subscriber);
		this.setWinner(winner);
		this.setSecond(second);
		this.setThird(third);
	}
	

	/**
	 * @return Le vainqueur de la competition
	 */
	public Competitor getWinner() {
		return winner;
	}


	/**
	 * @param winner Le vainqueur de la competition
	 * @throws BadParametersException 
	 */
	public void setWinner(Competitor winner) throws BadParametersException {
		if (winner==null)
			throw new BadParametersException("Le vainqueur n'a pas �t� instanci�");
		this.winner = winner;
	}


	/**
	 * @return Le deuxi�me de la comp�tition
	 */
	public Competitor getSecond() {
		return second;
	}


	/**
	 * @param second Le deuxi�me de la comp�tition
	 * @throws BadParametersException 
	 */
	public void setSecond(Competitor second) throws BadParametersException {
		if (second==null)
			throw new BadParametersException("Le deuxi�me n'a pas �t� instanci�");
		this.second = second;
	}


	/**
	 * @return Le troisi�me de la comp�tition 
	 */
	public Competitor getThird() {
		return third;
	}


	/**
	 * @param third Le troisi�me de la comp�tition
	 * @throws BadParametersException 
	 */
	public void setThird(Competitor third) throws BadParametersException {
		if (third==null)
			throw new BadParametersException("Le troisi�me n'a pas �t� instanci�");
		this.third = third;
	}
	
	
}
