/**
 * 
 */
package fr.uv1.bettingServices;

import java.util.ArrayList;
import java.util.Collection;

import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
import fr.uv1.utils.MyCalendar;


/**
 * @author mcisse
 *
 */
public class Competition {
	/** La taille minimum du nom d'une compétition */
	private static final int LONG_COMPETITION = 4;
	
	/**La contrainte que le nom de la compétition doit verifier */
	private static final String REGEX_COMPETITION = new String("[a-zA-Z0-9]*");
	
	
	/**Le nom de la compétition*/
	private String nomCompetition;
	
	/**La date de la compétition*/
	private MyCalendar dateCompetition;	
	
	/**Le montant total misé sur la compétition*/
	private long montantTotalMise;
	
	
	/**La liste des compétiteurs */
	//ce serait mieux d'utiliser des  Hashset à la place des ArrayList c'est dit dans les consigne cela nous aidera pour la base de donnée
	private Collection<Competitor> competitors;
	
	/** La liste des paris */
	private ArrayList<Pari> betList;
	
	

	/**
	 * @param nomCompetition Le nom de la competition
	 * @param dateCompetition La date de la competition
	 * @param montantTotalMise Le montant total misé sur la compétition
	 * @param competitors La liste des compétiteurs
	 * @throws BadParametersException 
	 * @throws CompetitionException 
	 * @throws ExistingCompetitorException 
	 */
	public Competition(String nomCompetition, MyCalendar dateCompetition, ArrayList<Competitor> competitors) throws BadParametersException, CompetitionException{
	
		this.setNomCompetition(nomCompetition);
		this.setDateCompetition(dateCompetition);
		this.montantTotalMise=0;
		this.setCompetitors(competitors);
		this.betList = new ArrayList<Pari>();
	}

	/**
	 * @return le nom de la compétition
	 */
	public String getNomCompetition() {
		return nomCompetition;
	}

	/**
	 * @param nomCompetition le nouveau nom de la compétition
	 */
	public void setNomCompetition(String nomCompetition) throws BadParametersException{
		checkStringNomCompetition(nomCompetition);
		this.nomCompetition = nomCompetition;
	}

	/**
	 * @return la date de la compétition
	 */
	public  MyCalendar getDateCompetition() {
		return dateCompetition;
	}

	/**
	 * @param date la nouvelle date de la compétition
	 * @throws BadParametersException 
	 */

	public void setDateCompetition( MyCalendar newDate) throws BadParametersException {
		if (newDate.isInThePast() ||newDate==null)
			throw new BadParametersException("Cette date est passée");

		this.dateCompetition = newDate;
	}

	/**
	 * @return Le montant total misé sur la compétition
	 */
	public  long getMontantTotalMise() {
		return montantTotalMise;
	}

	/**
	 * @param newMontantTotalMise Le montant total misé sur la compétition 
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
	public Collection<Competitor> getCompetitors() {
		return competitors;
	}

	/**
	 * @param competitors La nouvelle liste de competiteurs
	 * @throws CompetitionException 
	 * @throws BadParametersException 
	 * @throws ExistingCompetitorException 
	 */
	public void setCompetitors(Collection<Competitor> competitors) throws CompetitionException, BadParametersException {
		if (competitors==null)
			throw new BadParametersException("La liste des compétiteurs n'a pas été instancié");
		
		if (competitors.size()<2){
			throw new CompetitionException("Une compétition doit avoir au moins deux competiteurs");
		}

			int i=0;
			if (((ArrayList<Competitor>) competitors).get(i) instanceof Individual){
				while (i<competitors.size()){
					if (!(((ArrayList<Competitor>) competitors).get(i) instanceof Individual)){
						throw new CompetitionException("Les compétiteurs d'une compétition doivent être instance d'une " +
								"meme classe");
					}else
						this.addCompetitor(((ArrayList<Competitor>) competitors).get(i));
						i++;
				}
				
			}else{
			
			i=0;
			if (((ArrayList<Competitor>) competitors).get(i) instanceof Team){
				while (i<competitors.size()){
					if (!(((ArrayList<Competitor>) competitors).get(i) instanceof Team)){
						throw new CompetitionException("Les compétiteurs d'une compétition doivent être instance d'une " +
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
	 * @return La liste des paris sur la compétition
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
	
	public void supprimerParisCompetition(Subscriber subscriber) throws CompetitionException{
		
		long numberTokens = 0;

		if (this.isInThePast())
			throw new CompetitionException("La compétition est fermée");
		for (Pari pari : this.betList){
			if (pari.getSubscriber().equals(subscriber))
				numberTokens += pari.getMise();
				this.betList.remove(pari);
		}
		try {
			subscriber.getCompte().crediterCompte(numberTokens);
		} catch (BadParametersException e) {
			// TODO Auto-generated catch block
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
	
	public void parierSurLeVainqueur(PariWinner pari
			) throws AuthenticationException,
			CompetitionException, ExistingCompetitionException,
			SubscriberException, BadParametersException{
		if(pari==null) throw new BadParametersException("paramètre pari non instancié");
		boolean trouve = this.getCompetitors().contains(pari.getWinner());

		if (!trouve){
			throw new CompetitionException("Ce competiteur ne participe pas à cette competition");
		}
		
		if (this.isInThePast())
			throw new CompetitionException("La date de la competition est passée");
		
		
		if (pari.getWinner() instanceof Individual){
			Person subcriber = (Person)pari.getSubscriber();
			Person competiteur;
			for( Competitor c : this.getCompetitors() ){
				competiteur = (Person)c;
				if( competiteur.equals(subcriber)){
					throw new CompetitionException("Le joueur est un competiteur de la competition");
				}
			}
		}else if (pari.getWinner() instanceof Team){
			Team team;
			Person competiteur = null;
			for (Competitor competitor : this.getCompetitors()){
				team = (Team) competitor;
				for(Competitor member : team.getMembers()){
				competiteur = (Person) member;
				if(pari.getSubscriber().equals(competiteur)){
						throw new CompetitionException("Le joueur fait partie d'une équipe de la compétition");
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
			BadParametersException{
		
		if (paripod==null) throw new BadParametersException("pari avec un pariPodium non instancié");
		boolean trouveWinner = this.getCompetitors().contains(paripod.getWinner());
		boolean trouveSecond = this.getCompetitors().contains(paripod.getSecond());
		boolean trouveThird = this.getCompetitors().contains(paripod.getThird());
		
		if (!trouveWinner || !trouveSecond || !trouveThird)
			throw new CompetitionException("Un ou plusieurs de ces trois competiteurs ne participe pas " +
					"à cette compétition");
		
		if (this.isInThePast())
			throw new CompetitionException("La date de la competition est passée");
		
		if (paripod.getWinner() instanceof Individual){
			Person competiteur;
			for (Competitor competitor : this.getCompetitors()){
				competiteur=(Person) competitor;
				if (paripod.getSubscriber().equals(competiteur))
					throw new CompetitionException("Le joueur est un competiteur de la competition");
			}
			
		} else if (paripod.getWinner() instanceof Team){
			Person competiteur;
			Team team;
			for (Competitor competitor : this.getCompetitors()){
				team = (Team) competitor;
				if (team.getMembers().isEmpty()) throw new BadParametersException(" On peut pas parier sur une equipe sans menbre " );
				for (Competitor member : team.getMembers()){
					competiteur=(Person)member;
					if (paripod.getSubscriber().equals(competiteur)){
						throw new CompetitionException("Le joueur fait partie d'une équipe de la compétition");
					}
				}
			}
		}
		
		
		paripod.getSubscriber().debiter(paripod.getMise());
		// On ajoute le pari à la liste des paris de la compétition

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
	
	public void solderPariWinner(Competitor winner) throws CompetitionException, BadParametersException{

		if (winner == null)
			throw new BadParametersException("Le vainqueur n'a pas été instancié");
		
		long tokensBettedForWinner = 0;
		long tokensWonBySubscriber = 0;

		boolean trouve = this.competitors.contains(winner);
		boolean found = false;
		
		if (!trouve)
			throw new CompetitionException("Ce competiteur ne participe pas à cette competition");
			
		if (!(this.isInThePast()))
			throw new CompetitionException("Cette compétition est toujours ouverte");
		
		for (Pari pari : this.betList){
			if (pari instanceof PariWinner && ((PariWinner) pari).getWinner().equals(winner)){
					tokensBettedForWinner += pari.getMise();
			}
		}
		
		for (Pari pari : this.betList){
			if (pari instanceof PariWinner){
				if (((PariWinner) pari).getWinner().equals(winner)){
					found = true;
					tokensWonBySubscriber = (pari.getMise()*this.montantTotalMise)/tokensBettedForWinner;
					try {
						pari.getSubscriber().crediter(tokensWonBySubscriber);
					} catch (BadParametersException e) {
						System.out.println("bug");
					}
				}
			}
		}
		if (!found){
			for (Pari pari : this.betList){
				this.supprimerParisCompetition(pari.getSubscriber());
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
	
	public void solderPariPodium(Competitor winner, Competitor second, Competitor third)throws CompetitionException, BadParametersException{
		
		if (winner == null)
			throw new BadParametersException("Le vainqueur n'a pas été instancié");
		
		if (second == null)
			throw new BadParametersException("Le deuxième n'a pas été instancié");
		
		if (third == null)
			throw new BadParametersException("Le troisième n'a pas été instancié");
		
		long tokensBettedForPodium = 0;
		long tokensWonBySubscriber = 0;
		boolean found = false;
		boolean trouveWinner = this.competitors.contains(winner);
		boolean trouveSecond = this.competitors.contains(second);
		boolean trouveThird = this.competitors.contains(third);

		if (!trouveWinner || !trouveSecond || !trouveThird)
			throw new CompetitionException("Un ou plusieurs de ces trois competiteurs ne participe pas " +
					"à cette compétition");
		
		if (!(this.isInThePast()))
			throw new CompetitionException("Cette compétition est toujours ouverte");
		
		for (Pari pari : this.betList){
			if (pari instanceof PariPodium){
				if (((PariPodium) pari).getWinner().equals(winner) 
					&& ((PariPodium) pari).getSecond().equals(second) 
					&& ((PariPodium) pari).getThird().equals(third)){
					tokensBettedForPodium += pari.getMise();
				}
			}
		}
		
		for (Pari pari : this.betList){
			if (pari instanceof PariWinner){
				if (((PariPodium) pari).getWinner().equals(winner) 
					&& ((PariPodium) pari).getSecond().equals(second) 
					&& ((PariPodium) pari).getThird().equals(third)){
						found = true;
						tokensWonBySubscriber = (pari.getMise()*this.montantTotalMise)/tokensBettedForPodium;
					try {
						pari.getSubscriber().crediter(tokensWonBySubscriber);
					} catch (BadParametersException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		if (found==false){
			for (Pari pari : this.betList){
				this.supprimerParisCompetition(pari.getSubscriber());
			}
		}
		
	}
	

	public void addCompetitor(Competitor newCompetitor) throws CompetitionException, BadParametersException{
		if (newCompetitor==null)throw new BadParametersException(" competiteur non instancié");
		if ( this.competitors.contains(newCompetitor)) throw new CompetitionException(" le competiteur  " + newCompetitor.toString() + " a deja été ajouter");
		this.competitors.add(newCompetitor);


		}
		

	
	/**
	 * @return 
	 * 		Vrai si le closing date de la compétition est dans le passé
	 * 		Faux sinon
	 */
	public boolean isInThePast(){
		return this.getDateCompetition().isInThePast();
	}
	
	

	public boolean equals(Object object){
		if (object == null)
			return false;
		if (!(object instanceof Competition))
			return false;
		if (((Competition) object).getNomCompetition()==null)
			return false;
		return this.nomCompetition==((Competition) object).getNomCompetition();
	}

	
	/**
	 * @return 
	 * 		Vrai si la liste des paris de la compétition est vide
	 * 		Faux sinon
	 */
	public boolean IsEmptybetList(){
		return this.betList.isEmpty();
	}
	
	/**
	 * Cette méthode verifie la validité du nom d'une compétition.
	 * Seuls les lettres, les chiffres, les tirets et underscore sont autorisés.
	 * La taille de la chaine de caractères doit être au moins LONG_COMPETITION caractères
	 * 
	 * @param nomCompetition
	 *            Le nom de la compétition à verifier
	 * 
	 * @throws BadParametersException
	 *             exception levé si le nom de la compétition est invalide
	 */
	private static void checkStringNomCompetition(String nomCompetition)
			throws BadParametersException {
		if (nomCompetition == null)
			throw new BadParametersException("Le nom de la compétition n'a pas été instancié");
		
		if (nomCompetition.length() < LONG_COMPETITION)
			throw new BadParametersException("Le nom de la compétition est moins que "
					+ LONG_COMPETITION + "caractères");
		// Seuls les lettres et les chiffres sont autorisés
		if (!nomCompetition.matches(REGEX_COMPETITION))
			throw new BadParametersException("la competition " + nomCompetition
					+ " ne verifie pas les contraintes ");
	}	
	
}
