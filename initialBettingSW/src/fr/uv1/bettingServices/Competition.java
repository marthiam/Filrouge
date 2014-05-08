/**
 * 
 */
package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.utils.MyCalendar;


/**
 * @author mcisse
 *
 */
public class Competition {
	/**
	 * La taille minimum du nom d'une compétition
	 */
	private static final int LONG_COMPETITION = 4;
	
	/**
	 * La contrainte que le nom de la compétition doit verifier
	 */
	private static final String REGEX_COMPETITION = new String("[a-zA-Z0-9\\-\\\\_\\]*");
	
	
	/**
	 * Le nom de la compétition
	 */
	private String nomCompetition;
	
	/**
	 * La date de la compétition
	 */
	private MyCalendar dateCompetition;	
	
	/**
	 * Le montant total misé sur la compétition
	 */
	private static int montantTotalMise=0;
	

	/**
	 * 
	 * Construit une compétition avec son nom et sa date  
	 * @param nom Le nom de la compétition
	 * @param date La date de la compétition
	 * @throws BadParametersException 
	 */
	public Competition(String nomCompetition,  MyCalendar dateCompetition) throws BadParametersException{
		
		this.setNomCompetition(nomCompetition);
		this.setDateCompetition(dateCompetition);
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
		if (nomCompetition==null)
			throw new BadParametersException("Le nom de la compétition n'est pas valide");
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
	 */
	public void setDateCompetition( MyCalendar date) {
		 dateCompetition.setDate(date);
	}

	/**
	 * @return Le montant total misé sur la compétition
	 */
	public static int getMontantTotalMise() {
		return montantTotalMise;
	}

	/**
	 * @param montantTotalMise Le montant total misé sur la compétition 
	 * @throws BadParametersException 
	 */
	public static void setMontantTotalMise(int montantTotalMise) throws BadParametersException {
		if (montantTotalMise<0)
			throw new BadParametersException(montantTotalMise + " est un montant total invalide");
		Competition.montantTotalMise = montantTotalMise;
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
		// Seuls les lettres, les chiffres, les tirets et underscore sont autorisés
		if (!nomCompetition.matches(REGEX_COMPETITION))
			throw new BadParametersException("la competition " + nomCompetition
					+ " ne verifie pas les contraintes ");
	}	
}
