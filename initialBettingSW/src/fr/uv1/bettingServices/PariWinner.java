/**
 * 
 */
package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;

/**
 * @author mcisse
 * 
 */
public class PariWinner extends Pari {

	

	

	/**
	 * Construicteur
	 * 
	 * @param mise
	 *            Le nombre de jetons mis� par le joueur
	 * @param subscriber 
	 *            Le parieur 
	 * @param winner
	 *            Le competiteur sur lequel le joueur a pari�
	 * @throws BadParametersException
	 * 				est lev�e si l'un des param�tre est invalide 
	 */
	public PariWinner(long mise, Subscriber subscriber, Competitor winner)
			throws BadParametersException {
		super(mise, subscriber,winner);
	
	}

	


	
	


}
