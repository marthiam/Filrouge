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
	 * La taille minimum du nom d'une comp�tition
	 */
	private static final int LONG_COMPETITION = 4;
	
	/**
	 * La contrainte que le nom de la comp�tition doit verifier
	 */
	private static final String REGEX_COMPETITION = new String("[a-zA-Z0-9\\-\\\\_\\]*");
	
	
	/**
	 * Le nom de la comp�tition
	 */
	private String nomCompetition;
	
	/**
	 * La date de la comp�tition
	 */
	private MyCalendar dateCompetition;	
	
	/**
	 * Le montant total mis� sur la comp�tition
	 */
	private static int montantTotalMise=0;
	

	/**
	 * 
	 * Construit une comp�tition avec son nom et sa date  
	 * @param nom Le nom de la comp�tition
	 * @param date La date de la comp�tition
	 * @throws BadParametersException 
	 */
	public Competition(String nomCompetition,  MyCalendar dateCompetition) throws BadParametersException{
		
		this.setNomCompetition(nomCompetition);
		this.setDateCompetition(dateCompetition);
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
	 */
	public void setDateCompetition( MyCalendar date) {
		 dateCompetition.setDate(date);
	}

	/**
	 * @return Le montant total mis� sur la comp�tition
	 */
	public static int getMontantTotalMise() {
		return montantTotalMise;
	}

	/**
	 * @param montantTotalMise Le montant total mis� sur la comp�tition 
	 * @throws BadParametersException 
	 */
	public static void setMontantTotalMise(int montantTotalMise) throws BadParametersException {
		if (montantTotalMise<0)
			throw new BadParametersException(montantTotalMise + " est un montant total invalide");
		Competition.montantTotalMise = montantTotalMise;
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
		// Seuls les lettres, les chiffres, les tirets et underscore sont autoris�s
		if (!nomCompetition.matches(REGEX_COMPETITION))
			throw new BadParametersException("la competition " + nomCompetition
					+ " ne verifie pas les contraintes ");
	}	
}
