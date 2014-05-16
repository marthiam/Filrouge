/**
 * 
 */
package fr.uv1.bettingServices;

import java.util.ArrayList;

import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
import fr.uv1.utils.MyCalendar;


/**
 * @author mcisse
 *
 */

public class Competition {
	/** La taille minimum du nom d'une comp�tition */
	private static final int LONG_COMPETITION = 4;
	
	/**La contrainte que le nom de la comp�tition doit verifier */
	private static final String REGEX_COMPETITION = new String("[a-zA-Z0-9]*");
	
	
	/**Le nom de la comp�tition*/
	private String nomCompetition;
	
	/**La date de la comp�tition*/
	private MyCalendar dateCompetition;	
	
	/**Le montant total mis� sur la comp�tition*/
	private long montantTotalMise;
	
	
	/**La liste des comp�titeurs */
	//ce serait mieux d'utiliser des  Hashset � la place des ArrayList c'est dit dans les consigne cela nous aidera pour la base de donn�e
	private ArrayList<Competitor> competitors;
	
	/** La liste des paris */
	private ArrayList<Pari> betList;
	
	

	/**
	 * @param nomCompetition Le nom de la competition
	 * @param dateCompetition La date de la competition
	 * @param montantTotalMise Le montant total mis� sur la comp�tition
	 * @param competitors La liste des comp�titeurs
	 * @throws BadParametersException 
	 * @throws CompetitionException 
	 */
	public Competition(String nomCompetition, MyCalendar dateCompetition, ArrayList<Competitor> competitors) throws BadParametersException, CompetitionException {
	
		this.setNomCompetition(nomCompetition);
		this.setDateCompetition(dateCompetition);
		this.montantTotalMise=0;
		this.setCompetitors(competitors);
		this.betList = new ArrayList<Pari>();
	}

	/**
	 * @return le nom de la comp�tition
	 */
	public String getNomCompetition() {
		return nomCompetition;
	}

	/**
	 * @param nomCompetition le nouveau nom de la comp�tition
	 */
	public void setNomCompetition(String nomCompetition) throws BadParametersException{
		if (nomCompetition==null)
			throw new BadParametersException("Le nom de la comp�tition n'est pas valide");
		checkStringNomCompetition(nomCompetition);
		this.nomCompetition = nomCompetition;
	}

	/**
	 * @return la date de la comp�tition
	 */
	public  MyCalendar getDateCompetition() {
		return dateCompetition;
	}

	/**
	 * @param date la nouvelle date de la comp�tition
	 * @throws BadParametersException 
	 */
	public void setDateCompetition( MyCalendar newDate) throws BadParametersException {
		if (newDate.isInThePast())
			throw new BadParametersException("Cette date est pass�e");
		this.dateCompetition = newDate;
	}

	/**
	 * @return Le montant total mis� sur la comp�tition
	 */
	public  long getMontantTotalMise() {
		return montantTotalMise;
	}

	/**
	 * @param newMontantTotalMise Le montant total mis� sur la comp�tition 
	 * @throws BadParametersException 
	 */
	public void setMontantTotalMise(long newMontantTotalMise) throws BadParametersException {
		if (newMontantTotalMise<0)
			throw new BadParametersException(newMontantTotalMise + " est un montant total invalide");
		this.montantTotalMise = newMontantTotalMise;
	}	
	
	/**
	 * @return La liste des competiteurs de la competition
	 */
	public ArrayList<Competitor> getCompetitors() {
		return competitors;
	}

	/**
	 * @param competitors La nouvelle liste de competiteurs
	 * @throws CompetitionException 
	 */
	public void setCompetitors(ArrayList<Competitor> competitors) throws CompetitionException {
		if (competitors.size()<2){
			throw new CompetitionException("Une comp�tition doit avoir au moins deux competiteurs");
		}
		else{
			int i=0;
			if (competitors.get(i) instanceof Individual){
				while (i<competitors.size()){
					if (!(competitors.get(i) instanceof Individual)){
						throw new CompetitionException("Les comp�titeurs d'une comp�tition doivent �tre instance d'une " +
								"meme classe");
					}
					else
						i++;
				}
				this.competitors = competitors;
			}
			i=0;
			if (competitors.get(i) instanceof Team){
				while (i<competitors.size()){
					if (!(competitors.get(i) instanceof Team)){
						throw new CompetitionException("Les comp�titeurs d'une comp�tition doivent �tre instance d'une " +
								"meme classe");
					}
					else
						i++;
				}
				this.competitors = competitors;
			}
		}
	}
	

	/**
	 * @return La liste des paris sur la comp�tition
	 */
	public ArrayList<Pari> getBetList() {
		return betList;
	}

	/**
	 * @param betList La nouvelle liste de pari
	 */
	public void setBetList(ArrayList<Pari> betList) {
		this.betList = betList;
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
	
	public void parierSurLeVainqueur(long numberTokens, Competitor winner,
			Subscriber s) throws AuthenticationException,
			CompetitionException, ExistingCompetitionException,
			SubscriberException, BadParametersException{

		boolean trouve = false;

		for (Competitor competitor : this.getCompetitors()){
			if (competitor.equals(winner))
				trouve = true;
				break;
		}
		
		if (trouve==false)
			throw new CompetitionException("Ce competiteur ne participe pas � cette competition");
			
		if (this.isInThePast())
			throw new CompetitionException("La date de la competition est pass�e");
		
		if (winner instanceof Individual){
			for (Competitor competitor : this.getCompetitors()){
				if (competitor.equals(s))
					throw new CompetitionException("Le joueur est un competiteur de la competition");
			}
		}
		if (winner instanceof Team){
			Team team;
			for (Competitor competitor : this.getCompetitors()){
				team = (Team) competitor;
				for (Competitor member : team.getMembers()){
					if (member.equals(s))
						throw new CompetitionException("Le joueur fait partie d'une �quipe de la comp�tition");
				}
			}
		}
		s.getCompte().debiterCompte(numberTokens);
		Pari pari = new PariWinner(numberTokens,s, this, winner);
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
	public void parierSurLePodium(long numberTokens, Competitor winner,
			Competitor second, Competitor third, Subscriber s)
			throws CompetitionException, SubscriberException,
			BadParametersException{
		
		boolean trouveWinner = false;
		boolean trouveSecond = false;
		boolean trouveThird = false;
		for (Competitor competitor : this.getCompetitors()){
			if (competitor.equals(winner))
				trouveWinner = true;
			if (competitor.equals(second))
				trouveSecond = true;
			if (competitor.equals(third))
				trouveThird = true;
		}
		if (trouveWinner==false || trouveSecond==false || trouveThird==false)
			throw new CompetitionException("Un ou plusieurs de ces trois competiteurs ne participe pas " +
					"� cette comp�tition");
		
		if (this.isInThePast())
			throw new CompetitionException("La date de la competition est pass�e");
		
		
		if (winner instanceof Individual){
			for (Competitor competitor : this.getCompetitors()){
				if (competitor.equals(s))
					throw new CompetitionException("Le joueur est un competiteur de la competition");
			}
		}
		
		if (winner instanceof Team){
			Team team;
			for (Competitor competitor : this.getCompetitors()){
				team = (Team) competitor;
				for (Competitor member : team.getMembers()){
					if (member.equals(s))
						throw new CompetitionException("Le joueur fait partie d'une �quipe de la comp�tition");
				}
			}
		}
		
		
		s.getCompte().debiterCompte(numberTokens);
		Pari pari = new PariPodium(numberTokens,s, this, winner, second, third);
		
		// On ajoute le pari � la liste des paris de la comp�tition
		this.betList.add(pari);
		this.montantTotalMise += pari.getMise();

	}
	
	public void addCompetitor(Competitor newCompetitor) throws CompetitionException,ExistingCompetitionException{
		if (this.competitors.get(0) instanceof Team){
			if((newCompetitor instanceof Team)){
				if ( this.competitors.contains(newCompetitor)) throw new ExistingCompetitionException(" le competiteur est deja menbre de l'equipe " + this.nomCompetition);
					this.competitors.add(newCompetitor);
			}{
				throw new CompetitionException();
				
			}
		}else if(this.competitors.get(0) instanceof Individual){
			if((newCompetitor instanceof Individual)){
				if ( this.competitors.contains(newCompetitor)) throw new ExistingCompetitionException(" le competiteur est deja menbre de l'equipe " + this.nomCompetition);
				this.competitors.add(newCompetitor);
				}{
					throw new CompetitionException();
				}
		}
		
	}
	
	
	/**
	 * @return 
	 * 		Vrai si le closing date de la comp�tition est dans le pass�
	 * 		Faux sinon
	 */
	public boolean isInThePast(){
		return this.getDateCompetition().isInThePast();
	}
	
	
	/**
	 * Cette m�thode verifie la validit� du nom d'une comp�tition.
	 * Seuls les lettres, les chiffres, les tirets et underscore sont autoris�s.
	 * La taille de la chaine de caract�res doit �tre au moins LONG_COMPETITION caract�res
	 * 
	 * @param nomCompetition
	 *            Le nom de la comp�tition � verifier
	 * 
	 * @throws BadParametersException
	 *             exception lev� si le nom de la comp�tition est invalide
	 */
	private static void checkStringNomCompetition(String nomCompetition)
			throws BadParametersException {
		if (nomCompetition == null)
			throw new BadParametersException("Le nom de la comp�tition n'a pas �t� instanci�");
		
		if (nomCompetition.length() < LONG_COMPETITION)
			throw new BadParametersException("Le nom de la comp�tition est moins que "
					+ LONG_COMPETITION + "caract�res");
		// Seuls les lettres et les chiffres sont autoris�s
		if (!nomCompetition.matches(REGEX_COMPETITION))
			throw new BadParametersException("la competition " + nomCompetition
					+ " ne verifie pas les contraintes ");
	}	
}
