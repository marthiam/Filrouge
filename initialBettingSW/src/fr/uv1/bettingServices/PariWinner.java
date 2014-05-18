/**
 * 
 */
package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;



/**
 * @author mcisse
 *
 */
public class PariWinner extends Pari{
	
	/** Le competiteur sur lequel le joueur a pari� */
	private Competitor winner;
	
	
	
	/**
	 * @param mise Le nombre de jetons mis� par le joueur
	 * @param winner Le competiteur sur lequel le joueur a pari�
	 * @throws BadParametersException 
	 */
	public PariWinner(long mise, Subscriber subscriber, Competition competition, 
			Competitor winner) throws BadParametersException {
		super(mise, subscriber, competition);
		this.setWinner(winner);
	}



	/**
	 * @return the winner
	 */
	public Competitor getWinner() {
		return winner;
	}



	/**
	 * @param winner the winner to set
	 * @throws BadParametersException 
	 */
	public void setWinner(Competitor winner) throws BadParametersException {
		if (winner==null)
			throw new BadParametersException("Le vainqueur n'a pas �t� instanci�");
		this.winner = winner;
	}

	
}
