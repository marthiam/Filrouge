package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;

public class PariPodium extends Pari{

	/** Le vainqueur de la competition */
	private Competitor winner;
	/** Le deuxième de la compétition */
	private Competitor second;
	/** Le troisième de la compétition */
	private Competitor third;
	
	
	/**
	 * @param mise Le nombre de jetons misé par le joueur
	 * @param winner Le vainqueur de la competition
	 * @param second Le deuxième de la compétition
	 * @param third Le troisième de la compétition
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
			throw new BadParametersException("Le vainqueur n'a pas été instancié");
		this.winner = winner;
	}


	/**
	 * @return Le deuxième de la compétition
	 */
	public Competitor getSecond() {
		return second;
	}


	/**
	 * @param second Le deuxième de la compétition
	 * @throws BadParametersException 
	 */
	public void setSecond(Competitor second) throws BadParametersException {
		if (second==null)
			throw new BadParametersException("Le deuxième n'a pas été instancié");
		this.second = second;
	}


	/**
	 * @return Le troisième de la compétition 
	 */
	public Competitor getThird() {
		return third;
	}


	/**
	 * @param third Le troisième de la compétition
	 * @throws BadParametersException 
	 */
	public void setThird(Competitor third) throws BadParametersException {
		if (third==null)
			throw new BadParametersException("Le troisième n'a pas été instancié");
		this.third = third;
	}
	
	
}
