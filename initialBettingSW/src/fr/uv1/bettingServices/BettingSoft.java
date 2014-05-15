package fr.uv1.bettingServices;

import java.util.*;

import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;
import fr.uv1.bettingServices.exceptions.ExistingSubscriberException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
import fr.uv1.utils.BettingPasswordsVerifier;

/**
 * 
 * @author prou + mallet <br>
 * <br>
 *         This class implements methods of the interface Betting. <br>
 * <br>
 *         <ul>
 *         <li>manager password validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>password size should be at least 8 characters</li>
 *         </ul>
 *         </li>
 *         </ul>
 */

public class BettingSoft implements Betting {

	/*
	 * Manager password
	 */
	private String managerPassword;

	/*
	 * Subscribers of the betting software
	 */
	/** 
	 * @uml.property name="subscribers"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="bettingSoft:fr.uv1.bettingServices.Subscriber"
	 */
	private Collection<Subscriber> subscribers;
	/*
	 * Subscribers of the betting software
	 */
	private Collection<Competition> competitions;
	
	


	/**
	 * constructor of BettingSoft
	 * 
	 * @param managerPwd
	 *            manager password.
	 * 
	 * @throws BadParametersException
	 *             raised if a_managerPwd is incorrect.
	 */
	public BettingSoft(String a_managerPwd) throws BadParametersException {
		// The password should be valid
		setManagerPassword(a_managerPwd);
		this.subscribers = new ArrayList<Subscriber>();
	}

	private void setManagerPassword(String managerPassword)
			throws BadParametersException {
		if (managerPassword == null)
			throw new BadParametersException("manager's password not valid");
		// The password should be valid
		if (!BettingPasswordsVerifier.verify(managerPassword))
			throw new BadParametersException("manager's password not valid");
		this.managerPassword = managerPassword;
	}

	/**
	 * From Betting interface
	 */
	@Override
	public String subscribe(String lastName, String firstName, String username,
			String borndate, String managerPwd) throws AuthenticationException,
			ExistingSubscriberException, SubscriberException,
			BadParametersException {
		// Authenticate manager
		authenticateMngr(managerPwd);
		// Look if a subscriber with the same username already exists
		Subscriber s = searchSubscriberByUsername(username);
		if (s != null)
			throw new ExistingSubscriberException(
					"A subscriber with the same username already exists");
		// Creates the new subscriber
		s = new Subscriber(lastName, firstName,borndate,username);
		// Add it to the collection of subscribers
		subscribers.add(s);
		return s.getPassword();
		}

	/**
	 * From Betting interface
	 */
	@Override
	public long unsubscribe(String a_username, String a_managerPwd)
			throws AuthenticationException, ExistingSubscriberException {
		
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Look if a subscriber with the same username already exists
		Subscriber s = searchSubscriberByUsername(a_username);
		if (s != null){
			long result= s.getCompte().getSolde();
			System.out.println(result);
			subscribers.remove(s); // remove it
			return result;
		}else{
			throw new ExistingSubscriberException("Subscriber does not exist");
		}
	    }

	/**
	 * From Betting interface
	 */
	@Override
	public ArrayList<ArrayList<String>> listSubscribers(String a_managerPwd)
			throws AuthenticationException {
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Calculate the list of subscribers
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> subsData = new ArrayList<String>();
		for (Subscriber s : subscribers) {
			subsData.add(s.getLastname());
			subsData.add(s.getFirstname());
			subsData.add(s.getBorndate());
			subsData.add(s.getUsername());

			result.add(subsData);
		}
		return result;
	}

	/**
	 * From Betting interface
	 */

	public void authenticateMngr(String a_managerPwd)
			throws AuthenticationException {
		if (a_managerPwd == null)
			throw new AuthenticationException("invalid manager's password");

		if (!this.managerPassword.equals(a_managerPwd))
			throw new AuthenticationException("incorrect manager's password");
	}

	/**
	 * From Betting interface
	 */
	@Override
	public void changeMngrPwd(String newPwd, String currentPwd)
			throws AuthenticationException, BadParametersException {
		// Authenticate manager
		authenticateMngr(currentPwd);
		// Change password if valid
		setManagerPassword(newPwd);
	}

	/**
	 * search a subscriber by username
	 * 
	 * @param a_username
	 *            the username of the subscriber.
	 * 
	 * @return the found subscriber or null
	 */
	
	private Subscriber searchSubscriberByUsername(String a_username) {
		if (a_username == null)
			return null;
		for (Subscriber s : subscribers) {
			System.out.println(s.getUsername());
			if (s.hasUsername(a_username))
				return s;
		}
		return null;
	}
	
	/**
	 * search a competition by name
	 * 
	 * @param competitionName
	 *            the name of the competition.
	 * 
	 * @return the found competition or null
	 */
	
	private Competition searchCompetitionByName(String competitionName) {
		if (competitionName == null)
			return null;
		for (Competition c : competitions) {
			if (c.getNomCompetition().equals(competitionName))
				return c;
		}
		return null;
	}
	
	
	
	/**
	 * add a competition.
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param closingDate
	 *            last date to bet.
	 * @param competitors
	 *            the collection of competitors for the competition.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if a competition with the same name already exists.
	 * @throws CompetitionException
	 *             raised if closing date is in the past (competition closed);
	 *             there are less than two competitors; two or more competitors
	 *             are the same (firstname, lastname, borndate).
	 * @throws BadParametersException
	 *             raised if name of competition is invalid; list of competitors
	 *             not instantiated; (firstname, lastname, borndate or name if a
	 *             team competitor) of one or more of the competitors is
	 *             invalid.
	 */
@Override
	public void addCompetition(String competition, Calendar closingDate,
			Collection<Competitor> competitors, String managerPwd)
			throws AuthenticationException, ExistingCompetitionException,
			CompetitionException, BadParametersException{
		   
		
	}
	/**
	 * create a competitor (person) instance. If the competitor is already
	 * registered, the existing instance is returned. The instance is not
	 * persisted.
	 * 
	 * @param lastName
	 *            the last name of the competitor.
	 * @param firstName
	 *            the first name of the competitor.
	 * @param borndate
	 *            the borndate of the competitor.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the manager's password is incorrect.
	 * @throws BadParametersException
	 *             raised if last name, first name or borndate are invalid.
	 * 
	 * @return Competitor instance.
	 */
	@Override
	public Competitor createCompetitor(String lastName, String firstName,
			String borndate, String managerPwd) throws AuthenticationException, BadParametersException{
		this.authenticateMngr(managerPwd);
		Competitor indiv= new Individual(lastName,firstName,borndate);
		return indiv;
	}

	/**
	 * create competitor (team) instance. If the competitor is already
	 * registered, the existing instance is returned. The instance is not
	 * persisted.
	 * 
	 * @param name
	 *            the name of the team.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the manager's password is incorrect.
	 * @throws BadParametersException
	 *             raised if name is invalid.
	 * 
	 * @return Competitor instance.
	 */
	@Override
	public Competitor createCompetitor(String name, String managerPwd)
			throws AuthenticationException,
			BadParametersException{
		this.authenticateMngr(managerPwd);
		Competitor team= new Team(name);
		return team;
	}

	/**
	 * delete a competitor for a competition.
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param competitor
	 *            infos about the competitor.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if the closing date of the competition is in the past
	 *             (competition closed) ; the number of remaining competitors is
	 *             2 before deleting.
	 * @throws ExistingCompetitorException
	 *             raised if the competitor is not registered for the
	 *             competition.
	 */
	@Override
	public void deleteCompetitor(String competition, Competitor competitor,
			String managerPwd) throws AuthenticationException,
			ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException{
		
	}

	/**
	 * credit number of tokens of a subscriber.
	 * 
	 * @param username
	 *            subscriber's username.
	 * @param numberTokens
	 *            number of tokens to credit.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * 
	 * @throws AuthenticationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingSubscriberException
	 *             raised if the subscriber (username) is not registered.
	 * @throws BadParametersException
	 *             raised if number of tokens is less than (or equals to) 0.
	 */
	@Override
	public void creditSubscriber(String username, long numberTokens, String managerPwd)
			throws AuthenticationException, ExistingSubscriberException,
			BadParametersException{
		this.authenticateMngr(managerPwd);
		Subscriber subs= this.searchSubscriberByUsername(username);
		subs.getCompte().crediterCompte(numberTokens);
	}

	/**
	 * debit a subscriber account with a number of tokens.
	 * 
	 * @param username
	 *            subscriber's username.
	 * @param numberTokens
	 *            number of tokens to debit.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingSubscriberException
	 *             raised if the subscriber (username) is not registered.
	 * @throws SubscriberException
	 *             raised if number of tokens not enough.
	 * @throws BadParametersException
	 *             raised if number of tokens is less than (or equals to) 0.
	 * 
	 */
	public void debitSubscriber(String username, long numberTokens, String managerPwd)
			throws AuthenticationException, ExistingSubscriberException,
			SubscriberException, BadParametersException{
			this.authenticateMngr(managerPwd);
			Subscriber subs= this.searchSubscriberByUsername(username);
			subs.getCompte().debiterCompte(numberTokens);
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
	 */
	public void settleWinner(String competition, Competitor winner, String managerPwd)
			throws AuthenticationException, ExistingCompetitionException,
			CompetitionException{
		
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
	 */
	@Override
	public void settlePodium(String competition, Competitor winner, Competitor second,
			Competitor third, String managerPwd)
			throws AuthenticationException, ExistingCompetitionException,
			CompetitionException{
		
	}

	/***********************************************************************
	 * SUBSCRIBERS FONCTIONNALITIES
	 ***********************************************************************/

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
	@Override
	public void betOnWinner(long numberTokens, String competition, Competitor winner,
			String username, String pwdSubs) throws AuthenticationException,
			CompetitionException, ExistingCompetitionException,
			SubscriberException, BadParametersException{
		
		Subscriber s = searchSubscriberByUsername(username);
		Competition c = searchCompetitionByName(competition);
		
		
		boolean trouve = false;
		if (s==null)
			throw new SubscriberException("Il n'existe pas de joueur dont le username est: " + username);
		if (!(s.getPassword().equals(pwdSubs)))
			throw new AuthenticationException("Le mot de passe saisi est un incorrect");
		if (c==null)
			throw new ExistingCompetitionException("La competition "+ competition +"n'existe pas");

		for (Competitor competitor : c.getCompetitors()){
			if (competitor.equals(winner))
				trouve = true;
				break;
		}
		
		if (trouve==false)
			throw new CompetitionException("Ce competiteur ne participe pas � cette competition");
			
		if (c.getDateCompetition().isInThePast())
			throw new CompetitionException("La date de la competition est pass�e");
		
		if (winner instanceof Individual){
			for (Competitor competitor : c.getCompetitors()){
				if (competitor.equals(s))
					throw new CompetitionException("Le joueur est un competiteur de la competition");
			}
		}
		if (winner instanceof Team){
			Team team;
			for (Competitor competitor : c.getCompetitors()){
				team = (Team) competitor;
				for (Competitor member : team.getMembers()){
					if (member.equals(s))
						throw new CompetitionException("Le joueur fait partie d'une �quipe de la comp�tition");
				}
			}
		}
		s.getCompte().debiterCompte(numberTokens);
		Pari pari = new PariWinner(numberTokens, winner);
		c.addPari(pari);
		
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
	public void betOnPodium(long numberTokens, String competition, Competitor winner,
			Competitor second, Competitor third, String username, String pwdSubs)
			throws AuthenticationException, CompetitionException,
			ExistingCompetitionException, SubscriberException,
			BadParametersException{
	
		Subscriber s = searchSubscriberByUsername(username);
		Competition c = searchCompetitionByName(competition);
		
		
		boolean trouveWinner = false;
		boolean trouveSecond = false;
		boolean trouveThird = false;
		if (s==null)
			throw new SubscriberException("Il n'existe pas de joueur dont le username est: " + username);
		if (!(s.getPassword().equals(pwdSubs)))
			throw new AuthenticationException("Le mot de passe saisi est un incorrect");
		if (c==null)
			throw new ExistingCompetitionException("La competition "+ competition +"n'existe pas");
		
		for (Competitor competitor : c.getCompetitors()){
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
		
		if (c.getDateCompetition().isInThePast())
			throw new CompetitionException("La date de la competition est pass�e");
		
		
		if (winner instanceof Individual){
			for (Competitor competitor : c.getCompetitors()){
				if (competitor.equals(s))
					throw new CompetitionException("Le joueur est un competiteur de la competition");
			}
		}
		
		if (winner instanceof Team){
			Team team;
			for (Competitor competitor : c.getCompetitors()){
				team = (Team) competitor;
				for (Competitor member : team.getMembers()){
					if (member.equals(s))
						throw new CompetitionException("Le joueur fait partie d'une �quipe de la comp�tition");
				}
			}
		}
		
		
		s.getCompte().debiterCompte(numberTokens);
		Pari pari = new PariPodium(numberTokens, winner, second, third);
		
		// On ajoute le pari � la liste des paris de la comp�tition
		c.addPari(pari);

	}

	/**
	 * change subscriber's password.
	 * 
	 * @param username
	 *            username of the subscriber.
	 * @param newPwd
	 *            the new subscriber password.
	 * @param currentPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if (username, password) does not exist.
	 * 
	 * @throws BadParametersException
	 *             raised if the new password is invalid.
	 */
	@Override
	public void changeSubsPwd(String username, String newPwd, String currentPwd)
			throws AuthenticationException, BadParametersException{
			Subscriber subs= this.searchSubscriberByUsername(username);
			if(!newPwd.equals(currentPwd)) subs.changePassword(newPwd,currentPwd);
	}

	/**
	 * consult informations about a subscriber
	 * 
	 * @param username
	 *            subscriber's username.
	 * @param pwdSubs
	 *            subscriber's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if (username, password) does not exist.
	 * 
	 * @return list of String with:
	 *         <ul>
	 *         <li>subscriber's lastname</li>
	 *         <li>subscriber's firstname</li>
	 *         <li>subscriber's borndate</li>
	 *         <li>subscriber's username</li>
	 *         <li>number of tokens</li>
	 *         <li>tokens betted</li>
	 *         <li>list of current bets</li>
	 *         </ul>
	 * <br>
	 *         All the current bets of the subscriber.
	 */
	@Override
	public ArrayList<String> infosSubscriber(String username, String pwdSubs)
			throws AuthenticationException{
		ArrayList<String> subsData= new ArrayList<String>();
		Subscriber s= this.searchSubscriberByUsername(username);
		subsData.add(s.getLastname());
		subsData.add(s.getFirstname());
		subsData.add(s.getBorndate());
		subsData.add(s.getUsername());
		subsData.add(""+s.getNumberToken());
		subsData.add(""+s.numberTokenBetted());
		subsData.add(s.getParis());
		return subsData; 
		
	}

	/**
	 * delete all bets made by a subscriber on a competition.<br>
	 * subscriber's account is credited with a number of tokens corresponding to
	 * the bets made by the subscriber for the competition.
	 * 
	 * @param competition
	 *            competition's name.
	 * @param username
	 *            subscriber's username.
	 * @param pwdSubs
	 *            subscriber's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if (username, password) does not exist.
	 * @throws CompetitionException
	 *             raised if closed competition (closing date is in the past).
	 * @throws ExistingCompetitionException
	 *             raised if there is no competition a_competition.
	 */
	@Override
	public void deleteBetsCompetition(String competition, String username,
			String pwdSubs) throws AuthenticationException,
			CompetitionException, ExistingCompetitionException{
		
	}

	/***********************************************************************
	 * VISITORS FONCTIONNALITIES
	 ***********************************************************************/
	/**
	 * list competitions.
	 * 
	 * @return a collection of competitions represent a competition data:
	 *         <ul>
	 *         <li>competition's name</li>
	 *         <li>competition's closing date</li>
	 *         <li>competition's competitors</li>
	 *         </ul>
	 */
	@Override
	public Collection<Competition> listCompetitions(){
		return null;
	}

	/**
	 * list competitors.
	 * 
	 * @param competition
	 *            competition's name.
	 * 
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if competition closed.
	 * @return a collection of competitors for a competition. For each person
	 *         competitor
	 *         <ul>
	 *         <li>competitor's firstname</li>
	 *         <li>competitor's lastname</li>
	 *         <li>competitor's borndate</li>
	 *         </ul>
	 *         For each team competitor <li>competitor's name</li> </ul>
	 */
	@Override
   public Collection<Competitor> listCompetitors(String competition)
			throws ExistingCompetitionException, CompetitionException{
		    return null;
	}

	/**
	 * consult bets on a competition.
	 * 
	 * @param competition
	 *            competition's name.
	 * 
	 * @throws ExistingCompetitionException
	 *             raised if it does not exist a competition of the name
	 *             a_competition.
	 * 
	 * @return a list of String containing the bets for the competition.
	 */
	@Override
	public ArrayList<String> consultBetsCompetition(String competition)
			throws ExistingCompetitionException{
		return null;
	}
	/**
	 * add a competitor to a competition.
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param competitor
	 *            infos about the competitor.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if the closing date of the competition is in the past
	 *             (competition closed).
	 * @throws ExistingCompetitorException
	 *             raised if the competitor is already registered for the
	 *             competition.
	 * @throws BadParametersException
	 *             raised if the (firstname, lastname, borndate or name if team
	 *             competitors) of the competitor is invalid.
	 */
	@Override
	public void addCompetitor(String competition, Competitor competitor,
			String managerPwd) throws AuthenticationException,
			ExistingCompetitionException, CompetitionException,
			ExistingCompetitorException, BadParametersException{
		
	}
	/**
	 * cancel a competition.
	 * 
	 * @param competition
	 *            the name of the competition.
	 * @param managerPwd
	 *            the manager's password.
	 * 
	 * @throws AuthenticationException
	 *             raised if the the manager's password is incorrect.
	 * @throws ExistingCompetitionException
	 *             raised if the competition does not exist.
	 * @throws CompetitionException
	 *             raised if the closing date is in the past (competition
	 *             closed).
	 */
	@Override
	public void cancelCompetition(String competition, String managerPwd)
			throws AuthenticationException, ExistingCompetitionException,
			CompetitionException{
		
	}

	

}