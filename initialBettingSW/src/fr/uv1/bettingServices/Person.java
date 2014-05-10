package fr.uv1.bettingServices;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.utils.MyCalendar;

public class Person {
	

	private static final String REGEX_NAME = new String("[a-zA-Z][a-zA-Z\\-\\ ]*");
	private static final String REGEX_DATE= new String("(\\d{2})/(\\d{2})/(\\d{4})");
	private String firstname;
	private String lastname;
	private String borndate;
	
	
	
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return the lastname 
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @return the borndate
	 */
	public String getBorndate() {
		return borndate;
	}

	/**
	 * @param borndate the borndate to set
	 * @throws BadParametersException 
	 */
	public void setBorndate(String borndate) throws BadParametersException {
		checkStringBorndate(borndate);
		this.borndate = borndate;
	}

	/**
	 * Constructeur 
	 * @param the lastname 
	 * @param the firstname
	 * @param the borndate
	 * @throws BadParametersException
	 */
	public Person(String lastname, String firstname,String borndate) throws BadParametersException {
		setLastname(lastname);
		setFirstname(firstname);
		setBorndate(borndate);
		
	}
	/**
	 * Constructeur 
	 * @param the lastname 
	 * @param the firstname
	 * @throws BadParametersException
	 */
	public Person(String lastname, String firstname) throws BadParametersException {
		setLastname(lastname);
		setFirstname(firstname);
		
	}
	
	

	/**
	 * Met à jour le nom d'une personne 
	 * @param new lastname 
	 * @throws BadParametersException
	 */
	public void setLastname(String lastname) throws BadParametersException {
		checkStringLastName(lastname);
		this.lastname = lastname;
	}

	/**
	 * Met à  jour le nom d'une personne 
	 * @param new  firstname
	 * @throws BadParametersException
	 * 			
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
	 * @param borndate
	 * 			String to check
	 * @throws BadParametersException
	 */
	private static void checkStringBorndate(String borndate)
			throws BadParametersException {
		if (borndate == null)
			throw new BadParametersException("date not instantiated");
		if (!borndate.matches(REGEX_DATE))
			throw new BadParametersException("the date " + borndate
					+ " does not verify constraints ");
	}

	/**
	 * check the validity of a string for a subscriber firstname, letters,
	 * dashes and spaces are allowed. First character should be a letter.
	 * firstname length should at least be 1 character
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

}
