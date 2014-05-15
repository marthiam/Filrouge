/**
 * 
 */
package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.exceptions.SubscriberException;



/**
 * @author mcisse
 *
 */
public class PariWinner extends Pari{
	
	/** Le competiteur sur lequel le joueur a parié */
	private Competitor winner;
	
	
	
	/**
	 * @param mise Le nombre de jetons misé par le joueur
	 * @param winner Le competiteur sur lequel le joueur a parié
	 * @throws BadParametersException 
	 */
	public PariWinner(long mise, Subscriber subscriber, Competitor winner) throws BadParametersException {
		super(mise, subscriber);
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
		this.winner = winner;
	}

	

	
	
	


	
}
