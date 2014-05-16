package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;

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
	 */
	public PariPodium(long mise,Subscriber subscriber, Competition competition, 
			Competitor winner, Competitor second,
			Competitor third) throws BadParametersException {
		super(mise, subscriber, competition);
		this.winner = winner;
		this.second = second;
		this.third = third;
	}
	

	/**
	 * @return Le vainqueur de la competition
	 */
	public Competitor getWinner() {
		return winner;
	}


	/**
	 * @param winner Le vainqueur de la competition
	 */
	public void setWinner(Competitor winner) {
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
	 */
	public void setSecond(Competitor second) {
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
	 */
	public void setThird(Competitor third) {
		this.third = third;
	}
	

	
	
	
}
