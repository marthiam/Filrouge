package fr.uv1.bettingServices;

import java.util.Calendar;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.utils.MyCalendar;

public class Person {

	/**
	 * la contrainte sur le nom d'une personne 
	 */
	private static final String REGEX_NAME = new String(
			"[a-zA-Z][a-zA-Z\\-\\ ]*");
	/**
	 * la contrainte sur la date.
	 */
	private static final String REGEX_DATE = new String(
			"(\\d{1,2})-(\\d{1,2})-(\\d{4})");
	
	/**
	 * le prenom de la personne .
	 */
	private String firstname;
	
	/**
	 * le prenom de la personne.
	 */
	private String lastname;
	
	
	/**
	 * la date de naissance de la personne .
	 */
	private String borndate;

	/**
	 * Renvoie le prenom d'une personne.
	 * 
	 * @return  firstname
	 * 				le  prenom .
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 *  Renvoie le nom d'une personne.
	 * @return  lastname
	 * 				le nom .
	 */
	public String getLastname() {
		return lastname;
	}

	/**Renvoie la date de naissance d'une personne.
	 * 
	 * @return borndate
	 * 				la date de naissance.
	 */
	public String getBorndate() {
		return borndate;
	}

	/**
	 * Renvoie la date de naissance format yyyy-mm-dd.
	 * @return 
	 * 		la date de naissance sous format yyyy-mm-dd.
	 */
	public String getBorndateDate() {
		String[] s = this.borndate.split("-");
		String annee = s[2];
		String mois = s[1];
		String jour = s[0];
		return annee + "-" + mois + "-" + jour;
	}

	/**
	 * Met � jour la date de naissance.
	 * @param borndate
	 *            la nouvelle  date de  naissance.
	 * @throws BadParametersException
	 * 				est lev�e si la date de naissance est invalide ou si l'age de la personne est <18 ans.
	 */
	public void setBorndate(String borndate) throws BadParametersException {
		checkStringBorndate(borndate);
		this.borndate = borndate;
	}

	/**
	 * Constructeur 
	 * 
	 * @param  lastname
	 * 				le nom .
	 * @param firstname
	 * 				le prenom .
	 *            
	 * @param  borndate
	 * 				la date de naissance sous format jj-mm-aaaa.
	 * @throws BadParametersException
	 * 				est lev�e si l'un des param�tres est invalide ou non instanci�.
	 */
	public Person(String lastname, String firstname, String borndate)
			throws BadParametersException {
		setLastname(lastname);
		setFirstname(firstname);
		setBorndate(borndate);

	}

	/**
	 * Constructeur 
	 * 
	 * @param  lastname
	 * 				le nom .
	 * @param firstname
	 * 				le prenom .
	 * @throws BadParametersException
	 * 				est lev�e si l'un des param�tres est invalide ou non instanci�.
	 */
	public Person(String lastname, String firstname)
			throws BadParametersException {
		setLastname(lastname);
		setFirstname(firstname);

	}

	/**
	 * Met � jour le nom d'une personne
	 * 
	 * @param  lastname
	 * 				le nouveau nom 
	 * @throws BadParametersException
	 * 				est lev�e si le nom est invalide ou non instanci�.
	 */
	public void setLastname(String lastname) throws BadParametersException {
		checkStringLastName(lastname);
		this.lastname = lastname;
	}

	/**
	 * Met � jour le nom d'une personne
	 * 
	 * @param new firstname
	 * @throws BadParametersException
	 * 			est lev�e si le prenom est invalide ou non instanci�.
	 */
	public void setFirstname(String firstname) throws BadParametersException {
		checkStringFirstName(firstname);
		this.firstname = firstname;
	}

	/**
	 * check the validity of a string for a subscriber lastname, letters, dashes
	 * and spaces are allowed. First character should be a letter. lastname
	 * length should at least be 1 character
	 * 
	 * @param a_lastname
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.serialVersionUID
	 */
	private static void checkStringLastName(String a_lastname)
			throws BadParametersException {

		if (a_lastname == null)
			throw new BadParametersException("name not instantiated");
		if (a_lastname.length() < 1)
			throw new BadParametersException(
					"name length less than 1 character");
		// First character should be a letter ; then just letters, dashes or
		// spaces
		if (!a_lastname.matches(REGEX_NAME))
			throw new BadParametersException("the name " + a_lastname
					+ " does not verify constraints ");
	}

	/**
	 * Renvoie l'age �  partir du jour ,mois et ann�e 
	 * @param y
	 * 			l'ann�e 
	 * @param m
	 * 			le mois 
	 * @param d
	 * 			le jour 
	 * @return
	 * 			le nombre ann�e depuis d-m-y jusqu'� la date virtuelle definit dans MyCalendar.
	 */
	public static int getYears(int y, int m, int d) {
		MyCalendar curr = MyCalendar.getDate(); 	// calcule de la date virtuelle d'aujourd'hui 
		MyCalendar birth = new MyCalendar(y, m, d); // calcule de la date de anniversaire 
		int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR); //la dffirence des ann�es
		curr.add(Calendar.YEAR, -yeardiff);   // on enleve la difference des ann�e � la date courante
		if (birth.after(curr)) {    // si la date de d'anniverssaire est avant ( en  jour ou moi ) la date d'aujourd'huit - la difference des ann�e
			yeardiff = yeardiff - 1; // la personne n'a pas encore atteint l'age au de�u 
		}
		return yeardiff; 
	}

	/**
	 * Renvois si un date est valide et que l'age est >=18  
	 * @param date
	 * 			la date a verifi� 
	 * @return
	 * 			vrai si la date est correct et que l'age est >=18
	 */
	public static boolean validDate(String date) {
		String[] s = date.split("-");
		int jour = new Integer(s[0]);
		int mois = new Integer(s[1]);
		int annee = new Integer(s[2]);
		if (jour >= 1 && jour <= 31 && mois >= 1 && mois <= 12) {
			long age = getYears(annee, mois, jour); 
			return age >= 18;
		}
		return false;
	}

	/*
	 * Verifie la validit� de la date de naissance 
	 * @param borndate
	 *            la cha�ne de caract�re a verifi�. 
	 * @throws BadParametersException
	 * 			est lev�e is la date est incorrect ou non instanci�.
	 */
	private static void checkStringBorndate(String borndate)
			throws BadParametersException {
		if (borndate == null)
			throw new BadParametersException("date not instantiated");
		if (!borndate.matches(REGEX_DATE) || !validDate(borndate))
			throw new BadParametersException("the borndate " + borndate
					+ " does not verify constraints ");
	}

	/*
	 * check the validity of a string for a person firstname, letters, dashes
	 * and spaces are allowed. First character should be a letter. firstname
	 * length should at least be 1 character
	 * 
	 * @param a_firstname
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.
	 */
	private static void checkStringFirstName(String a_firstname)
			throws BadParametersException {
		// Same rules as for the last name
		checkStringLastName(a_firstname);

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object p) {

		if (p instanceof Person) {
			return false;
		}
		return this.toString().equals(p.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return " " + this.getFirstname() + " " + this.getLastname() + " "
				+ this.getBorndate();
	}

}
