package fr.uv1.bettingServices;

import java.util.Collection;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

public  class Individual extends Person implements Competitor {
	
	/**
	 * La taille minimum du nom d'une equipe
	 * 
	 */
	private static final int LONG_INDIV = 4;
	
	/**
	 * La contrainte que le nom de l'equipe doit verifier
	 */
	private static final String REGEX_INDIV = new String("[a-zA-Z][a-zA-Z\\-\\ ]*");
	private static final String REGEX_DATE= new String("(\\d{2})/(\\d{2})/(\\d{4})");
	

	public Individual(String lastname, String firstname ,String borndate)
			throws BadParametersException {
		super(lastname, firstname,borndate);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.getBorndate()== null) ? 0 : this.getBorndate().hashCode());
		result = prime * result
				+ ((this.getFirstname()== null) ? 0 : getFirstname().hashCode());
		result = prime * result
				+ ((getLastname() == null) ? 0 : getLastname().hashCode());
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
		Individual other = (Individual) obj;
		if (getBorndate() == null) {
			if (other.getBorndate() != null) {
				return false;
			}
		} else if (!getBorndate().equals(other.getBorndate())) {
			return false;
		}
		if (getFirstname() == null) {
			if (other.getFirstname() != null) {
				return false;
			}
		} else if (!getFirstname().equals(other.getFirstname())) {
			return false;
		}
		if (getLastname() == null) {
			if (other.getLastname() != null) {
				return false;
			}
		} else if (!getLastname().equals(other.getLastname())) {
			return false;
		}
		return true;
	}

	/**
	 * tells if the name of the competitor is a valid one.
	 * 
	 * @return true if the competitor has a valid name.
	 */
	public boolean hasValidName(){
		boolean result =this.getFirstname().length()>=LONG_INDIV && this.getFirstname().matches(REGEX_INDIV);
		result &=this.getLastname().length()>=LONG_INDIV && this.getLastname().matches(REGEX_INDIV);
		return result;
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
