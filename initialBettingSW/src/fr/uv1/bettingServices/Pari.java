package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;

public class Pari {

	/** L'identifiant du pari */
	private int pari_id;

	/** Le nombre de jetons mis� par le joueur */
	private long mise;

	/** Le joueur qui fait ce pari */
	private Subscriber subscriber;

	/** La competition du pari */
	private Competition competition;

	/** L'identifiant de la comp�tition sur laquelle est fait le pari */
	private int competition_id;
	
	
	/** Le competiteur sur lequel le joueur a pari� */
	private Competitor winner;

	/**
	 * Constructeur
	 * 
	 * @param mise
	 *            le montant a mis�
	 * @param subscriber
	 *            le joueur qui pari
	 *  @param winner
	 *            Le competiteur sur lequel le joueur a pari�
	 *            
	 * @throws BadParametersException
	 *             est lev�e la mise est <=0 ou si le subscriber ou le winner est non instanci� 
	 * 
	 */
	public Pari(long mise, Subscriber subscriber,Competitor winner) throws BadParametersException {
		super();
		this.setMise(mise);
		this.setSubscriber(subscriber);
		this.setWinner(winner);

	}

	/**
	 * Renvoie l'identifiant du pari
	 * 
	 * @return l'identifiant du pari
	 */
	public int getPari_id() {
		return this.pari_id;
	}

	/**
	 * Met � jour l'identifiant du pari
	 * 
	 * @param pari_id
	 *            le nouvel identifiant du pari
	 */
	public void setPari_id(int pari_id) {
		this.pari_id = pari_id;
	}

	/**
	 * Renvoie la competition du pari
	 * 
	 * @return la competition du pari
	 */
	public Competition getCompetition() {
		return competition;
	}

	/**
	 * Met � jour La competition du pari
	 * 
	 * @param competition
	 *            la nouvelle competition
	 */
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	/**
	 * Renvoie la mise du pari
	 * 
	 * @return Le nombre de jetons mis� par le joueur
	 */
	public long getMise() {
		return mise;
	}

	/**
	 * Met � jour la mise
	 * 
	 * @param mise
	 *            Le nouveau nombre de jetons
	 * @throws BadParametersException
	 * 			est lev�e si la mise est <=0
	 */
	public void setMise(long mise) throws BadParametersException {
		if (mise <= 0)
			throw new BadParametersException("Le nombre de jetons" + mise
					+ " est invalide");
		this.mise = mise;
	}

	/**
	 * Renvoie le parieur
	 * 
	 * @return 
	 * 		le  parieur 
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * Met � jour le joueur ayant pari�
	 * 
	 * @param subscriber
	 *            Le nouveau parieur
	 * @throws BadParametersException
	 *             est lev�e si le joueur est non instanci� .
	 */
	public void setSubscriber(Subscriber subscriber)
			throws BadParametersException {
		if (subscriber == null)
			throw new BadParametersException("Le joueur n'a pas �t� instanci�");
		this.subscriber = subscriber;
	}
	
	/**
	 * Renvoie le competiteur  sur le quel on pari
	 * @return  winner
	 * 				le  competiteur gagnant du pari 
	 */
	public Competitor getWinner() {
		return winner;
	}
	
	/**
	 * @param winner
	 *            the winner to set
	 * @throws BadParametersException
	 */
	public void setWinner(Competitor winner) throws BadParametersException {
		if (winner == null)
			throw new BadParametersException(
					"Le vainqueur n'a pas �t� instanci�");
		this.winner = winner;
	}
	
	
	public String getSubscriberInfo() {
		return   this.getSubscriber().toString() + " "+ this.mise;
	}
	public String winnerInfo() {
		return   this.getWinner().toString();
	}

}
