package fr.uv1.bettingServices;

import java.util.Collection;
import java.util.HashSet;


import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

public  class Team implements Competitor {
	
	private String pseudo;
	private Collection<Competitor> members;
	
	
	

	public Team(String pseudo) {
		this.pseudo = pseudo;
		this.members = new HashSet<Competitor>();
		
	}

	public Team(String pseudo, Collection<Competitor> menbers) {
		this.pseudo = pseudo;
		this.members = menbers;
	}

	/**
	 * tells if the name of the competitor is a valid one.
	 * 
	 * @return true if the competitor has a valid name.
	 */
	public boolean hasValidName(){
		return false;
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
		
	}
	  

}
