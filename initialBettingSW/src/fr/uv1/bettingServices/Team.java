package fr.uv1.bettingServices;

import java.util.Collection;
import java.util.HashSet;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

public  class Team implements Competitor {
	
	private String teamName;
	private Collection<Competitor> members;
	/**
	 * La taille minimum du nom d'une equipe
	 * 
	 */
	private static final int LONG_TEAMNAME = 4;
	
	/**
	 * La contrainte que le nom de l'equipe doit verifier
	 */
	private static final String REGEX_TEAMNAME = new String("[a-zA-Z0-9\\-\\\\_\\]*");
	
	
	

	/**
	 * Construit une equipe sans menbres 
	 * @param teamName le 
	 */
	public Team(String teamName) {
		this.teamName = teamName;
		this.members = new HashSet<Competitor>();
	}

	/**
	 * Constructeur  
	 * @param teamName
	 * @param menbers
	 */
	public Team(String teamName, Collection<Competitor> menbers) {
		this.teamName = teamName;
		this.members = menbers;
	}

	/**
	 * tells if the name of the competitor is a valid one.
	 * 
	 * @return true if the competitor has a valid name.
	 */
	public boolean hasValidName(){
		return teamName.length()>=LONG_TEAMNAME && teamName.matches(REGEX_TEAMNAME);
	}
	
	/**
	 * add a member to a team competitor.
	 * 
	 * @throws ExistingCompetitorException
	 *             raised if the member is already registered for the team.
	 * @throws BadParametersException
	 *             raised if the member is not instantiated.
	 */
	public void addMember(Competitor member) throws ExistingCompetitorException,
			BadParametersException{
		if(member==null || !member.hasValidName()) throw new BadParametersException(member +"est pas un parametre valide dans addMenber");
		if(members.contains(member)){
			members.add(member);
		}else{
			throw new ExistingCompetitorException(member +" existe déja "); 
		}
		
	}

	/**
	 * delete a member from a team competitor.
	 * 
	 * @return true if the competitor has been deleted from the team.
	 * @throws BadParametersException
	 *             raised if the member is not instantiated.
	 * @throws ExistingCompetitorException
	 *             raised if the member is not registered for the team.
	 */
	public void deleteMember(Competitor member) throws BadParametersException,
			ExistingCompetitorException{
		if(member==null || !member.hasValidName()) throw new BadParametersException(member +"est pas un parametre valide dans addMenber");
		if(members.contains(member)){
			members.remove(member);
		}else{
			throw new ExistingCompetitorException(member +"ne peut pas être supprimer car elle n'existe pas"); 
		}
	}
	  

}
