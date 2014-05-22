package fr.uv1.bettingServices;


import java.util.Calendar;
import java.sql.Date;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.utils.MyCalendar;

public class Person {
	

	private static final String REGEX_NAME = new String("[a-zA-Z][a-zA-Z\\-\\ ]*");
	private static final String REGEX_DATE= new String("(\\d{1,2})-(\\d{1,2})-(\\d{4})");
	private String firstname;
	private String lastname;
	private String borndate;
	
	
	
	/**
	 * 
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

	public String getBorndateDate() {
		String [] s = this.borndate.split("-");
		String annee = s[2];
	 	String  mois = s[1];
	 	String jour =s[0];
		return annee+"-"+mois+"-"+jour;
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
	
	
	
	public static int getYears(int y,int m,int d)
	{
	  MyCalendar curr =  MyCalendar.getDate();
	  MyCalendar birth = new MyCalendar(y,m,d);
	  int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
	  curr.add(Calendar.YEAR,-yeardiff);
	  if(birth.after(curr))
	  {
	    yeardiff = yeardiff - 1;
	  }
	  return yeardiff;
	}
	
	public static boolean validDate(String date){
		String [] s = date.split("-");
		int jour = new Integer(s[0]);
		int mois = new Integer(s[1]);
		int annee = new Integer(s[2]);
		if(jour>=1 && jour<=31 && mois>=1 && mois<=12 ){
			long age= getYears(annee,mois,jour);
			return age>=18;
		}
		return false;
	}
	
	/**
	 * @param borndate
	 * 			String to check
	 * @throws BadParametersException
	 */
	private static void checkStringBorndate(String borndate) throws BadParametersException {
		if (borndate == null)
			throw new BadParametersException("date not instantiated");
		if (!borndate.matches(REGEX_DATE) || !validDate(borndate) )
			throw new BadParametersException("the borndate " + borndate
					+ " does not verify constraints ");
	}

	/**
	 * check the validity of a string for a person  firstname, letters,
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
	

	public boolean equals(Person p){
		return this.toString().equals(p.toString());
	}
	
	
	
	@Override
	public String toString() {
		return " " + this.getFirstname() + " " + this.getLastname()+" " + this.getBorndate() ;
	}

}
