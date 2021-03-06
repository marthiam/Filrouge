package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

public class Individual extends Person implements Competitor {

	/**
	 * La taille minimum du nom et prenom du competiteur 
	 */
	private static final int LONG_INDIV = 4;

	/**
	 * La contrainte que le nom et prenom du competiteur doit verifier
	 */
	private static final String REGEX_INDIV = new String(
			"[a-zA-Z][a-zA-Z\\-\\ ]*");

	/**
	 * identifiant du subscriber
	 */
	private long id_individual;

	
	/**
	 * Constructeur 
	 *@see fr.uv1.bettingServices.Person#Person(String, String, String)
	 */
	
	public Individual(String lastname, String firstname, String borndate)
			throws BadParametersException {
		super(lastname, firstname, borndate);
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
		result = prime
				* result
				+ ((this.getBorndate() == null) ? 0 : this.getBorndate()
						.hashCode());
		result = prime
				* result
				+ ((this.getFirstname() == null) ? 0 : getFirstname()
						.hashCode());
		result = prime * result
				+ ((getLastname() == null) ? 0 : getLastname().hashCode());
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
	 * Renvoie l'identifiant du competiteur 
	 *		 @return  id_individual
	 *						'identifiant du competiteur 
	 */
	public long getId_individual() {
		return id_individual;
	}

	/**
	 * Met � jour l'identifiant du competiteur 
	 * @param id_individual
	 *            le nouveau identifiant 
	 */
	public void setId_individual(long id_individual) {
		this.id_individual = id_individual;
	}

	/**
	 *  From Interface Competitor
	 */
	public boolean hasValidName() {
		boolean result = this.getFirstname().length() >= LONG_INDIV
				&& this.getFirstname().matches(REGEX_INDIV);
		result &= this.getLastname().length() >= LONG_INDIV
				&& this.getLastname().matches(REGEX_INDIV);
		return result;
	}

	/**
	 * From Interface Competitor
	 */
	public void addMember(Competitor member)
			throws ExistingCompetitorException, BadParametersException {

	}

	/**
	 * From interface Competitor
	 */
	public void deleteMember(Competitor member) throws BadParametersException,
			ExistingCompetitorException {

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return super.toString();
	}

}
