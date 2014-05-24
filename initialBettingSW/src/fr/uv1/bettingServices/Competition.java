/**
 * 
 */
package fr.uv1.bettingServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


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
	private static final String REGEX_COMPETITION = new String("[a-zA-Z0-9\\-\\_]*");
	
	/** L'identifiant de la compétition */
	private int id_competition;
	
	/**Le nom de la compétition*/

	
	private String nomCompetition;
	
	

	
	/**La date de la compétition*/
	private MyCalendar dateCompetition;	
	
	/**Le montant total misé sur la compétition*/

	private long montantTotalMise;
	
	
	/**La liste des compétiteurs */
	
	
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
		this.competitors= new ArrayList<Competitor>();
		this.setCompetitors(competitors);
		this.betList = new ArrayList<Pari>();
		
	}

	
	
	
	/**
	 * @return the id_compétition
	 */
	public int getId_competition() {
		return id_competition;
	}




	/**
	 * @param id_compétition the id_compétition to set
	 */
	public void setId_competition(int id_competition) {
		this.id_competition = id_competition;
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

	public void setDateCompetition( MyCalendar newDate) throws CompetitionException {
		if (newDate.isInThePast() || newDate==null)
			throw new CompetitionException("Cette date est passée");

		this.dateCompetition = newDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Competition [nomCompetition=" + nomCompetition + "]";
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
	public void setCompetitors(ArrayList<Competitor> competitors) throws CompetitionException, BadParametersException {
		
		//try {
		if (competitors==null || competitors.size()==0)
			throw new BadParametersException("La liste des compétiteurs n'a pas été instancié");
		if (competitors.size()<2){
			throw new CompetitionException("Une compétition doit avoir au moins deux competiteurs");
		}
			int i=0;

			if (competitors.get(i) instanceof Individual){
				while (i<competitors.size()){
					if (!( competitors.get(i) instanceof Individual)){
						throw new CompetitionException("Les compétiteurs d'une compétition doivent être instance d'une " +
								"meme classe");
					}else{
						this.addCompetitor(((ArrayList<Competitor>) competitors).get(i));
						i++;
					}
				}
				
			}else{
			
			i=0;
			if (competitors.get(i) instanceof Team){
				Team team = (Team) ((ArrayList<Competitor>) competitors).get(i);
				while (i<competitors.size()){
					if (!( competitors.get(i) instanceof Team)){
						
						throw new CompetitionException("Les compétiteurs d'une compétition doivent être instance d'une " +
								"meme classe");
					}else{
						if (team.getMembers()==null ) throw new CompetitionException();
						this.addCompetitor(((ArrayList<Competitor>) competitors).get(i));
						i++;
					}
						
				}
							 }
			}
		//}catch(Exception e){
		//	System.out.println(e);
		//	throw e; 
		//}
		
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
			subscriber.crediter(numberTokens);
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
	
		//System.out.println("Voici le pari "+ paripod.getMise() +" sur "+ paripod.getWinner() +" "+paripod.getSecond()+" "+paripod.getThird() ) ;
		boolean trouveWinner = this.getCompetitors().contains(paripod.getWinner());
		boolean trouveSecond = this.getCompetitors().contains(paripod.getSecond());
		boolean trouveThird = this.getCompetitors().contains(paripod.getThird());
		
		/*System.out.println(" la lise de competiteur de la competition"+this.nomCompetition +" est"); 
		
		for ( Competitor cp : this.competitors){
			System.out.println("l'element a la position i est "+cp.toString()); 
		}*/
		if (!trouveWinner /*|| !trouveSecond || !trouveThird*/)
			throw new CompetitionException("Un 1 ou plusieurs de ces trois competiteurs ne participe pas " +
					"à cette compétition");
		if (!trouveSecond /*|| !trouveSecond || !trouveThird*/)
			throw new CompetitionException("Un 2 ou plusieurs de ces trois competiteurs ne participe pas " +
					"à cette compétition");
		if (!trouveThird /*|| !trouveSecond || !trouveThird*/)
			throw new CompetitionException("Un 3 ou plusieurs de ces trois competiteurs ne participe pas " +
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
				if( team !=null){
				}
				
			
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
			if (pari instanceof PariPodium){
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
		
		if (!found){
			for (Pari pari : this.betList){
				pari.getSubscriber().crediter(pari.getMise());
				this.betList.remove(pari);
			}
		}
		
	}
	
	/**
	 * consult bets on a competition.
	 * @return a list of String containing the bets for the competition.
	 */

	public ArrayList<String> consulterParis(){
		
		String infoPariSubscriber;
		ArrayList<String> infoPari = new ArrayList<String>();
		if (((ArrayList<Competitor>) this.competitors).get(0) instanceof Individual){
			for (Pari pari : this.betList){
				infoPariSubscriber = "";
				if (pari instanceof PariWinner)
					infoPariSubscriber+= pari.getSubscriber().getUsername()+" "
							+((Person)((PariWinner)pari).getWinner()).getFirstname()+" "
							+((Person)((PariWinner)pari).getWinner()).getLastname()+" "
							+pari.getMise();
					infoPari.add(infoPariSubscriber);
				if (pari instanceof PariPodium)
					infoPariSubscriber+= pari.getSubscriber().getUsername()+" "
							+((Person)((PariPodium)pari).getWinner()).getFirstname()+" "
							+((Person)((PariPodium)pari).getWinner()).getLastname()+" "
							+((Person)((PariPodium)pari).getSecond()).getFirstname()+" "
							+((Person)((PariPodium)pari).getSecond()).getLastname()+" "
							+((Person)((PariPodium)pari).getThird()).getFirstname()+" "
							+((Person)((PariPodium)pari).getThird()).getLastname()+" "
							+pari.getMise();
					infoPari.add(infoPariSubscriber);
			}
		}
		
		if (((ArrayList<Competitor>) this.competitors).get(0) instanceof Team){
			for (Pari pari : this.betList){
				infoPariSubscriber = "";
				if (pari instanceof PariWinner)
					infoPariSubscriber+= pari.getSubscriber().getUsername()+" "
							+((Team)((PariWinner)pari).getWinner()).getTeamName()+" "
							+pari.getMise();
					infoPari.add(infoPariSubscriber);
				if (pari instanceof PariPodium)
					infoPariSubscriber+= pari.getSubscriber().getUsername()+" "
							+((Team)((PariPodium)pari).getWinner()).getTeamName()+" "
							+((Team)((PariPodium)pari).getSecond()).getTeamName()+" "
							+((Team)((PariPodium)pari).getThird()).getTeamName()+" "
							+pari.getMise();
					infoPari.add(infoPariSubscriber);
			}
		}
		return infoPari;
		
		
	}
	
	
	
	
	
	

	public void addCompetitor(Competitor newCompetitor) throws CompetitionException, BadParametersException{
		if (newCompetitor==null)throw new BadParametersException("competiteur non instancié");

		if ( this.competitors!=null && this.competitors.contains(newCompetitor)) {
			throw new CompetitionException(" le competiteur  " + newCompetitor.toString() + " a deja été ajouter");
		} 
		if (this.competitors==null) 
			throw new CompetitionException(" bug");
		
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
	
	

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
	
	public String getClosingdate() {
		return this.getDateCompetition().get(Calendar.YEAR)+"-"+this.getDateCompetition().get(Calendar.MONTH)+
				"-"+this.getDateCompetition().get(Calendar.DATE);
	}
	
	public static void main(String[] args){
		MyCalendar closingdate = new MyCalendar(2014,05,01);
		System.out.println(closingdate);
		System.out.println(closingdate.get(Calendar.YEAR)+"-"+closingdate.get(Calendar.MONTH)+
				"-"+closingdate.get(Calendar.DATE));
		
		Date date = new Date(2014,12,1);
		System.out.println(date.getMonth());
	}
	
}
