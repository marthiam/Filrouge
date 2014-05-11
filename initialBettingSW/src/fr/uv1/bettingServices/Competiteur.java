package fr.uv1.bettingServices;

import java.util.Collection;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

public  class Competiteur extends Person implements Competitor {
	
	private String pseudo;
	private Collection<Competitor> menbers;
	
	
	public Competiteur(String lastname, String firstname) {
		super(lastname, firstname);
		// TODO Auto-generated constructor stub
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
