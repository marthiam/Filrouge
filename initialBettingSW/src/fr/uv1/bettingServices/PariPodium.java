package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;

public class PariPodium extends Pari {

	/** Le deuxième de la compétition */
	private Competitor second;
	
	
	/** Le troisième de la compétition */
	private Competitor third;

	/**
	 * Constructeur 
	 * 
	 * @param mise
	 *            Le nombre de jetons misé par le joueur
	 * @param winner
	 *            Le vainqueur de la competition
	 * @param second
	 *            Le deuxième de la compétition
	 * @param third
	 *            Le troisième de la compétition
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
	 * Met à jour le deuxieme  du pari .
	 * 
	 * @param third
	 *            Le nouveau competiteur  deuxieme  
	 * @throws BadParametersException
	 * 			est levée si cet competiteur n'est pas instancié.
	 */
	public void setSecond(Competitor second) throws BadParametersException {
		if (second == null)
			throw new BadParametersException(
					"Le deuxième n'a pas été instancié");
		this.second = second;
	}

	/**
	 * Renvoie le troisième du pari .
	 * @return 
	 * 		le competiteur troisième .
	 */
	public Competitor getThird() {
		return third;
	}

	/**
	 * Met à jour le troisième du pari .
	 * 
	 * @param third
	 *            Le nouveau competiteur  troisième  
	 * @throws BadParametersException
	 * 			est levée si cet competiteur n'est pas instancié.
	 */
	public void setThird(Competitor third) throws BadParametersException {
		if (third == null)
			throw new BadParametersException(
					"Le troisième n'a pas été instancié");
		this.third = third;
	}
	
	
	/**
	 * Retourne les informations concernant le  troisième du pari.
	 * @return
	 * 		 les information du competitteur troisième.
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
