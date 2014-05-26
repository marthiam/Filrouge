package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;

public class PariPodium extends Pari {

	/** Le deuxi�me de la comp�tition */
	private Competitor second;
	
	
	/** Le troisi�me de la comp�tition */
	private Competitor third;

	/**
	 * Constructeur 
	 * 
	 * @param mise
	 *            Le nombre de jetons mis� par le joueur
	 * @param winner
	 *            Le vainqueur de la competition
	 * @param second
	 *            Le deuxi�me de la comp�tition
	 * @param third
	 *            Le troisi�me de la comp�tition
	 * @throws BadParametersException
	 * @throws SubscriberException
	 */
	public PariPodium(long mise, Subscriber subscriber, Competitor winner,
			Competitor second, Competitor third) throws BadParametersException {
		super(mise, subscriber,winner);
		this.setSecond(second);
		this.setThird(third);
	}



	/**
	 * Renvoie le deuxieme du pari .
	 * @return 
	 * 		le competiteur deuxieme .
	 */
	public Competitor getSecond() {
		return second;
	}

	/**
	 * Met � jour le deuxieme  du pari .
	 * 
	 * @param third
	 *            Le nouveau competiteur  deuxieme  
	 * @throws BadParametersException
	 * 			est lev�e si cet competiteur n'est pas instanci�.
	 */
	public void setSecond(Competitor second) throws BadParametersException {
		if (second == null)
			throw new BadParametersException(
					"Le deuxi�me n'a pas �t� instanci�");
		this.second = second;
	}

	/**
	 * Renvoie le troisi�me du pari .
	 * @return 
	 * 		le competiteur troisi�me .
	 */
	public Competitor getThird() {
		return third;
	}

	/**
	 * Met � jour le troisi�me du pari .
	 * 
	 * @param third
	 *            Le nouveau competiteur  troisi�me  
	 * @throws BadParametersException
	 * 			est lev�e si cet competiteur n'est pas instanci�.
	 */
	public void setThird(Competitor third) throws BadParametersException {
		if (third == null)
			throw new BadParametersException(
					"Le troisi�me n'a pas �t� instanci�");
		this.third = third;
	}
	
	
	/**
	 * Retourne les informations concernant le  troisi�me du pari.
	 * @return
	 * 		 les information du competitteur troisi�me.
	 * 		
	 */
	public String  thirdInfo() {
		return this.third.toString();
	}
	
	/**
	 * Retourne les informations concernant le  second du pari.
	 * @return
	 * 		 les information du competitteur second.
	 * 		
	 */
	public String  secondInfo() {
		return this.second.toString();
	}

}
