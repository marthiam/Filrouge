/**
 * 
 */
package fr.uv1.bettingServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
import fr.uv1.utils.MyCalendar;


/**
 * @author Mariam
 * 
 */
/**
 * @author Mariam
 *
 */
public class Competition {

	/** La taille minimum du nom d'une comp�tition */
	private static final int LONG_COMPETITION = 4;

	/** La contrainte que le nom de la comp�tition doit verifier */
	private static final String REGEX_COMPETITION = new String(
			"[a-zA-Z0-9\\-\\_]*");

	/** L'identifiant de la comp�tition */
	private int id_competition;

	/** Le nom de la comp�tition */

	private String nomCompetition;

	/** La date de la comp�tition */
	private MyCalendar dateCompetition;

	/** Le montant total mis� sur la comp�tition */

	private long montantTotalMise;

	/** La liste des comp�titeurs */

	private Collection<Competitor> competitors;

	/** La liste des paris */
	private ArrayList<Pari> betList;
	

	/**
	 * Constructeur 
	 * @param nomCompetition
	 *            Le nom de la competition
	 * @param dateCompetition
	 *            La date de la competition
	 * @param montantTotalMise
	 *            Le montant total mis� sur la comp�tition
	 * @param competitors
	 *            La liste des comp�titeurs
	 * @throws BadParametersException
	 * 				est lev�e si l'un de param�tre est invalide ou non  instanci� 
	 * 			
	 * @throws CompetitionException
	 * 				est lev�e si la date de fermeture de la competition est pass�e
	
	 * 				
	 */
	public Competition(String nomCompetition, MyCalendar dateCompetition,
			ArrayList<Competitor> competitors) throws BadParametersException,
			CompetitionException {

		this.setNomCompetition(nomCompetition);
		this.setDateCompetition(dateCompetition);
		this.montantTotalMise = 0;
		this.competitors = new ArrayList<Competitor>();
		this.setCompetitors(competitors);
		this.betList = new ArrayList<Pari>();

	}

	/**
	 * Renvoie l'identifiant de la competition 
	 * @return  id_comp�tition
	 * 				l'identifiant de la competition 
	 */
	public int getId_competition() {
		return id_competition;
	}

	/**
	 * Met � jour l'identifiant de la competition 
	 * @param id_comp�tition
	 *           le nouveau identifiant  
	 */
	public void setId_competition(int id_competition) {
		this.id_competition = id_competition;
	}

	/**
	 * Renvoie  le  nom de la comp�tition 
	 * @return 
	 * 		le nom de la comp�tition
	 */
	public String getNomCompetition() {
		return nomCompetition;
	}

	/**
	 * Met � le jour nom de la comp�tition
	 * @param nomCompetition
	 *            le nouveau nom de la comp�tition
	 */
	public void setNomCompetition(String nomCompetition)
			throws BadParametersException {
		checkStringNomCompetition(nomCompetition);
		this.nomCompetition = nomCompetition;
	}

	/**
	 * Renvoie la date de fermetue de competion 
	 * @return 
	 * 			la date de fermeture de la comp�tition
	 */
	public MyCalendar getDateCompetition() {
		return dateCompetition;
	}

	/**
	 * Met � jour la date de fermetue de competion 
	 * @param date
	 *            la nouvelle date de la comp�tition
	 * @throws BadParametersException
	 * 				est lev�e si la date n'est pas instanci� 
	 * @throws CompetitionException
	 * 				est lev�e si la date est pass�e 
	 */

	public void setDateCompetition(MyCalendar newDate)
			throws CompetitionException, BadParametersException {
		if(newDate ==null)throw new BadParametersException("la date de fermeture doit �tre instanci� ");
		if (newDate.isInThePast() )
			throw new CompetitionException("Cette date est pass�e");

		this.dateCompetition = newDate;
	}

	/**
	 * Renvoie le montant total mis� sur la competition 
	 * @return
	 * 			 Le montant total mis� sur la comp�tition
	 */
	public long getMontantTotalMise() {
		return montantTotalMise;
	}

	/**
	 * Met � jour le montant total mis� sur la competition 
	 * @param newMontantTotalMise
	 *            Le nouveau montant total mis� sur la comp�tition
	 * @throws BadParametersException
	 * 			  est leve� si ce montant est <0
	 */
	public void setMontantTotalMise(long newMontantTotalMise)
			throws BadParametersException {
		if (newMontantTotalMise < 0)
			throw new BadParametersException(newMontantTotalMise
					+ " est un montant total invalide");
		this.montantTotalMise = newMontantTotalMise;
	}

	/**
	 * Renvoie les competiteurs de la competition
	 * @return 
	 * 		La liste des competiteurs de la competition
	 */
	public Collection<Competitor> getCompetitors() {
		return competitors;
	}

	/**
	 * Met � jour le  competiteurs de competition 
	 * @param competitors
	 *            La nouvelle liste de competiteurs
	 * @throws CompetitionException
	 * 			est lev�e si le nombre de competiteur est <2 ou si ils ne sont pas du m�me type reel 
	 * @throws BadParametersException
	 * 			est lev�e si la liste ou  l'un des competiteur  est non instanci�  ou si  elle est vide
	 * @see fr.uv1.bettingServices.Competition#addCompetitor(Competitor)
	 */
	public void setCompetitors(ArrayList<Competitor> competitors)
			throws CompetitionException, BadParametersException {

		if (competitors == null || competitors.size() == 0)
			throw new BadParametersException(
					"La liste des comp�titeurs n'a pas �t� instanci�");
		if (competitors.size() < 2) {
			throw new CompetitionException(
					"Une comp�tition doit avoir au moins deux competiteurs");
		}
		int i = 0;

		if (competitors.get(i) instanceof Individual) {
			while (i < competitors.size()) {
				if (!(competitors.get(i) instanceof Individual)) {
					throw new CompetitionException(
							"Les comp�titeurs d'une comp�tition doivent �tre instance d'une "
									+ "meme classe");
				} else {
					this.addCompetitor(((ArrayList<Competitor>) competitors)
							.get(i));
					i++;
				}
			}

		} else {

			i = 0;
			if (competitors.get(i) instanceof Team) {
				Team team = (Team) ((ArrayList<Competitor>) competitors).get(i);
				while (i < competitors.size()) {
					if (!(competitors.get(i) instanceof Team)) {

						throw new CompetitionException(
								"Les comp�titeurs d'une comp�tition doivent �tre instance d'une "
										+ "meme classe");
					} else {
						if (team.getMembers() == null)
							throw new CompetitionException();
						this.addCompetitor(((ArrayList<Competitor>) competitors)
								.get(i));
						i++;
					}

				}
			}
		}

	}

	/**
	 * Renvoie les paris d'une competition 
	 * 
	 * @return 
	 * 			La liste des paris sur la comp�tition
	 */
	public ArrayList<Pari> getBetList() {
		return betList;
	}

	/**
	 * Met � jour les paris d'une competition 
	 * @param betList
	 *            La nouvelle liste de pari
	 * @throws BadParametersException 
	 * 			est lev�e si la nouvelle liste de paris ne correspond pas � cette competition.
	 */
	public void setBetList(ArrayList<Pari> betList) throws BadParametersException {
		
		for(Pari p : betList){
			if(p instanceof PariWinner  ){
				if(!(this.participe(((PariWinner) p).getWinner()))){
					throw new  BadParametersException("cette liste de paris ne correspont  pas � cette competition");
				}
			}else{
				if(!(this.participe(((PariPodium) p).getWinner())) ||! (this.participe(((PariPodium) p).getSecond())) || !(this.participe(((PariPodium) p).getThird())) ){
					throw new  BadParametersException("cette liste de paris ne correspont  pas � cette competition");
				}	
			}
			this.montantTotalMise +=p.getMise();
			
			}
		
	this.betList = betList;
	}


	/**
	 * Renvoie si le competiteur participe � la competirion 
	 * @param competitor
	 * 			le competitor � regarder 
	 * @return
	 *  		vraie  si le competiteur est un competiteur de cette competition
	 */
	public boolean participe(Competitor competitor ){
		
		for (Competitor c :this.competitors ){
			if(c.equals(competitor)){
				return true;
			}
		}
		return false ; 
	}

	

	/**
	 *Supprime les paris d'un joueur 
	 * @param subscriber
	 * 				le joueur dont on doit supprimer les paris 
	 * @throws CompetitionException
	 * 				est lev�e si la date fermeture de la competition est pass�e 
	 */
	public void supprimerParisCompetition(Subscriber subscriber)
			throws CompetitionException {

		long numberTokens = 0;

		if (this.isInThePast())
			throw new CompetitionException("La comp�tition est ferm�e");
		for (Pari pari : this.betList) {
			if (pari.getSubscriber().equals(subscriber))
				numberTokens += pari.getMise();
			this.betList.remove(pari);
		}
		try {
			subscriber.crediter(numberTokens);
		} catch (BadParametersException e) {
			e.printStackTrace();
		}
	}

	/**
	 * bet a winner for a competition <br>
	 * The number of tokens of the subscriber is debited.
	 * 
	 * @param numberTokens
	 *            number of tokens to bet.
	 * @param competition
	 *            name of the competition.
	 * @param winner
	 *            competitor to bet (winner).
	 * @param username
	 *            subscriber's username.
	 * @param pwdSubs
	 *            subscriber's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if (username, password) does not exist.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if there is no competitor a_winner for the
	 *             competition; competition is closed (closing date is in the
	 *             past); the player is a competitor of the competition.
	 * @throws SubscriberException
	 *             raised if subscriber has not enough tokens.
	 * @throws BadParametersException
	 *             raised if number of tokens less than 0.
	 * 
	 */

	public void parierSurLeVainqueur(PariWinner pari)
			throws AuthenticationException, CompetitionException,
			ExistingCompetitionException, SubscriberException,
			BadParametersException {
		if (pari == null)
			throw new BadParametersException("param�tre pari non instanci�");
		boolean trouve = this.getCompetitors().contains(pari.getWinner());

		if (!trouve) {
			throw new CompetitionException(
					"Ce competiteur ne participe pas � cette competition");
		}

		if (this.isInThePast())
			throw new CompetitionException(
					"La date de la competition est pass�e");

		if (pari.getWinner() instanceof Individual) {
			Person subcriber = (Person) pari.getSubscriber();
			Person competiteur;
			for (Competitor c : this.getCompetitors()) {
				competiteur = (Person) c;
				if (competiteur.equals(subcriber)) {
					throw new CompetitionException(
							"Le joueur est un competiteur de la competition");
				}
			}
		} else if (pari.getWinner() instanceof Team) {
			Team team;
			Person competiteur = null;
			for (Competitor competitor : this.getCompetitors()) {
				team = (Team) competitor;
				for (Competitor member : team.getMembers()) {
					competiteur = (Person) member;
					if (pari.getSubscriber().equals(competiteur)) {
						throw new CompetitionException(
								"Le joueur fait partie d'une �quipe de la comp�tition");
					}
				}
			}
		}

		pari.getSubscriber().debiter(pari.getMise());
		betList.add(pari);
		this.montantTotalMise += pari.getMise();

	}

	/**
	 * bet on podium <br>
	 * The number of tokens of the subscriber is debited.
	 * 
	 * @param username
	 *            subscriber's username.
	 * @param pwdSubs
	 *            subscriber's password.
	 * @param numberTokens
	 *            number of tokens to bet.
	 * @param competition
	 *            the name of the competition.
	 * @param winner
	 *            winner to bet.
	 * @param second
	 *            second place.
	 * @param third
	 *            third place.
	 * 
	 * @throws AuthenticationException
	 *             raised if (username, password) does not exist.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if there is no competitor with name a_winner, a_second
	 *             or a_third for the competition; competition is closed
	 *             (closing date is in the past); the player is a competitor of
	 *             the competition.
	 * @throws SubscriberException
	 *             raised if subscriber has not enough tokens.
	 * @throws BadParametersException
	 *             raised if number of tokens less than 0.
	 */
	public void parierSurLePodium(PariPodium paripod)
			throws CompetitionException, SubscriberException,
			BadParametersException {

		if (paripod == null)
			throw new BadParametersException(
					"pari avec un pariPodium non instanci�");

		boolean trouveWinner = this.getCompetitors().contains(
				paripod.getWinner());
		boolean trouveSecond = this.getCompetitors().contains(
				paripod.getSecond());
		boolean trouveThird = this.getCompetitors()
				.contains(paripod.getThird());

		if (!trouveWinner || !trouveSecond || !trouveThird)
			throw new CompetitionException(
					"Un 1 ou plusieurs de ces trois competiteurs ne participe pas "
							+ "� cette competition");

		if (this.isInThePast())
			throw new CompetitionException(
					"La date de la competition est pass�e");

		if (paripod.getWinner() instanceof Individual) {
			Person competiteur;
			for (Competitor competitor : this.getCompetitors()) {
				competiteur = (Person) competitor;
				if (paripod.getSubscriber().equals(competiteur))
					throw new CompetitionException(
							"Le joueur est un competiteur de la competition");
			}

		} else if (paripod.getWinner() instanceof Team) {
			Person competiteur;
			Team team;
			for (Competitor competitor : this.getCompetitors()) {
				team = (Team) competitor;
				if (team != null) {
				}

				for (Competitor member : team.getMembers()) {
					competiteur = (Person) member;
					if (paripod.getSubscriber().equals(competiteur)) {
						throw new CompetitionException(
								"Le joueur fait partie d'une equipe de la competition");
					}
				}
			}
		}

		paripod.getSubscriber().debiter(paripod.getMise());
		// On ajoute le pari � la liste des paris de la comp�tition

		this.betList.add(paripod);
		this.montantTotalMise += paripod.getMise();
	}

	/**
	 * settle bets on winner. <br>
	 * Each subscriber betting on this competition with winner a_winner is
	 * credited with a number of tokens equals to: <br>
	 * (number of tokens betted * total tokens betted for the competition) /
	 * total number of tokens betted for the winner <br>
	 * If no subscriber bets on the right competitor (the real winner), the
	 * tokens betted are credited to subscribers betting on the competition
	 * according to the number of tokens they betted. The competition is then
	 * deleted if no more bets exist for the competition.<br>
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param winner
	 *            competitor winner.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if there is no competitor a_winner for the
	 *             competition; competition still opened.
	 * @throws BadParametersException
	 */

	public void solderPariWinner(Competitor winner)
			throws CompetitionException, BadParametersException {

		if (winner == null)
			throw new BadParametersException(
					"Le vainqueur n'a pas �t� instanci�");

		long tokensBettedForWinner = 0;
		long tokensWonBySubscriber = 0;

		boolean trouve = this.competitors.contains(winner);
		boolean found = false;

		if (!trouve)
			throw new CompetitionException(
					"Ce competiteur ne participe pas � cette competition");

		if (!(this.isInThePast()))
			throw new CompetitionException(
					"Cette comp�tition est toujours ouverte");

		for (Pari pari : this.betList) {
			if (pari instanceof PariWinner
					&& ((PariWinner) pari).getWinner().equals(winner)) {
				tokensBettedForWinner += pari.getMise();
			}
		}

		for (Pari pari : this.betList) {
			if (pari instanceof PariWinner) {
				if ( pari.getWinner().equals(winner)) {
					found = true;
					tokensWonBySubscriber = (pari.getMise() * this.montantTotalMise)
							/ tokensBettedForWinner;
					try {
						pari.getSubscriber().crediter(tokensWonBySubscriber);
					} catch (BadParametersException e) {
						System.out.println("bug");
					}
				}
			}
		}
		if (!found) {
			for (Pari pari : this.betList) {
				pari.getSubscriber().crediter(pari.getMise());
				this.betList.remove(pari);
			}
		}

	}

	/**
	 * settle bets on podium. <br>
	 * Each subscriber betting on this competition with the right podium is
	 * credited with a number of tokens equals to: <br>
	 * (number of tokens betted * total tokens betted for the competition) /
	 * total number of tokens betted for the podium <br>
	 * If no subscriber bets on the right podium, the tokens betted are credited
	 * to subscribers betting on the competition according to the number of
	 * tokens they betted. The competition is then deleted if no more bets exist
	 * for the competition.<br>
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param winner
	 *            the winner.
	 * @param second
	 *            the second.
	 * @param third
	 *            the third.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if two competitors in the podium are the same; no
	 *             competitor (firstname, lastname, borndate or name for teams)
	 *             a_winner, a_second or a_third for the competition;
	 *             competition still opened
	 * @throws BadParametersException
	 */

	public void solderPariPodium(Competitor winner, Competitor second,
			Competitor third) throws CompetitionException,
			BadParametersException {

		if (winner == null)
			throw new BadParametersException(
					"Le vainqueur n'a pas �t� instanci�");

		if (second == null)
			throw new BadParametersException(
					"Le deuxi�me n'a pas �t� instanci�");

		if (third == null)
			throw new BadParametersException(
					"Le troisi�me n'a pas �t� instanci�");

		long tokensBettedForPodium = 0;
		long tokensWonBySubscriber = 0;
		boolean found = false;
		boolean trouveWinner = this.competitors.contains(winner);
		boolean trouveSecond = this.competitors.contains(second);
		boolean trouveThird = this.competitors.contains(third);

		if (!trouveWinner || !trouveSecond || !trouveThird)
			throw new CompetitionException(
					"Un ou plusieurs de ces trois competiteurs ne participe pas "
							+ "� cette comp�tition");

		if (!(this.isInThePast()))
			throw new CompetitionException(
					"Cette comp�tition est toujours ouverte");

		for (Pari pari : this.betList) {
			if (pari instanceof PariPodium) {
				if (((PariPodium) pari).getWinner().equals(winner)
						&& ((PariPodium) pari).getSecond().equals(second)
						&& ((PariPodium) pari).getThird().equals(third)) {
					tokensBettedForPodium += pari.getMise();
				}
			}
		}

		for (Pari pari : this.betList) {
			if (pari instanceof PariPodium) {
				if (((PariPodium) pari).getWinner().equals(winner)
						&& ((PariPodium) pari).getSecond().equals(second)
						&& ((PariPodium) pari).getThird().equals(third)) {
					found = true;
					tokensWonBySubscriber = (pari.getMise() * this.montantTotalMise)
							/ tokensBettedForPodium;
					try {
						pari.getSubscriber().crediter(tokensWonBySubscriber);
					} catch (BadParametersException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (!found) {
			for (Pari pari : this.betList) {
				pari.getSubscriber().crediter(pari.getMise());
				this.betList.remove(pari);
			}
		}

	}

	/**
	 * Consulte la liste des paris de la  competition.
	 * 
	 * @return 
	 * 		une liste de cha�ne contenant les pariss de la competition.
	 */

	public ArrayList<String> consulterParis() {

		String infoPari;
		ArrayList<String> infoParis = new ArrayList<String>();
		
			for (Pari pari : this.betList) {
				infoPari = pari.getSubscriberInfo();
				infoParis.add(infoPari);
				infoPari= pari.winnerInfo();
				infoParis.add(infoPari);
					if (pari instanceof PariPodium){
						infoPari=( (PariPodium) pari).secondInfo();
						infoParis.add(infoPari);
						infoPari=( (PariPodium)pari).thirdInfo();
						infoParis.add(infoPari);
					}
			}
		return infoParis;

	}

	/**
	 * Ajoute un competiteur � la comp�tition <br>
	 * 
	 * @param newCompetitor
	 *            le nouveau competiteur
	 * @throws CompetitionException
	 *             est lev�e si le competiteur a dej� �t� ajout� .
	 * @throws BadParametersException
	 *             est lev�e si le competiteur n'est pas instanci� .
	 */
	public void addCompetitor(Competitor newCompetitor)
			throws CompetitionException, BadParametersException {
		if (newCompetitor == null)
			throw new BadParametersException("competiteur non instanci�");

		if (this.competitors != null
				&& this.competitors.contains(newCompetitor)) {
			throw new CompetitionException(" le competiteur  "
					+ newCompetitor.toString() + " a deja �t� ajouter");
		}

		this.competitors.add(newCompetitor);// ajoute un nouveau competiteur
	}

	/**
	 * Renvoie si la competition � une date pass�e
	 * 
	 * @return vrai si le closing date de la comp�tition est dans le pass� Faux
	 *         sinon
	 * 
	 */
	public boolean isInThePast() {
		return this.getDateCompetition().isInThePast();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nomCompetition == null) ? 0 : nomCompetition.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Competition other = (Competition) obj;
		if (nomCompetition == null) {
			if (other.nomCompetition != null) {
				return false;
			}
		} else if (!nomCompetition.equals(other.nomCompetition)) {
			return false;
		}
		return true;
	}

	/**
	 * Renvoie si la liste des paris de la competition est vide
	 * @return 
	 * 			vrai si la liste des paris de la comp�tition est vide Faux sinon
	 */
	public boolean IsEmptybetList() {
		return this.betList.isEmpty();
	}

	/**
	 * supprime un competiteur de la competition.
	 * 
	 * @throws ExistingCompetitorException
	 *             est lev�e si le competiteur ne participe pas � la competition
	 * */
	public void removeCompetitor(Competitor competitor)
			throws ExistingCompetitorException {
		if (!(this.competitors.contains(competitor))) {
			throw new ExistingCompetitorException("le competiteur "
					+ competitor + " ne participe � la compeetition "
					+ this.nomCompetition);
		}
		this.competitors.remove(competitor);

	}

	/**
	 * Cette m�thode verifie la validit� du nom d'une comp�tition. Seuls les
	 * lettres, les chiffres, les tirets et underscore sont autoris�s. La taille
	 * de la chaine de caract�res doit �tre au moins LONG_COMPETITION caract�res
	 * 
	 * @param nomCompetition
	 *            Le nom de la comp�tition � verifier
	 * 
	 * @throws BadParametersException
	 *             est lev� si le nom de la comp�tition est invalide
	 */
	private static void checkStringNomCompetition(String nomCompetition)
			throws BadParametersException {
		if (nomCompetition == null)
			throw new BadParametersException(
					"Le nom de la comp�tition n'a pas �t� instanci�");

		if (nomCompetition.length() < LONG_COMPETITION)
			throw new BadParametersException(
					"Le nom de la comp�tition est moins que "
							+ LONG_COMPETITION + "caract�res");
		// Seuls les lettres et les chiffres sont autoris�s
		if (!nomCompetition.matches(REGEX_COMPETITION))
			throw new BadParametersException("la competition " + nomCompetition
					+ " ne verifie pas les contraintes ");
	}

	/**
	 * Renvoie la date de fermeture de la competition sous forme de chaine de
	 * caract�re
	 * 
	 * @return la chaine de caract�re correspondant
	 */
	public String getClosingdate() {
		return this.getDateCompetition().get(Calendar.YEAR) + "-"
				+ (this.getDateCompetition().get(Calendar.MONTH) + 1) + "-"
				+ this.getDateCompetition().get(Calendar.DATE);
	}

	@Override
	public String toString() {
		return this.nomCompetition + " " + this.getClosingdate();
	}
}
