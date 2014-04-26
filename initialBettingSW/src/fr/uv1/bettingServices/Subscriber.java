package fr.uv1.bettingServices;

import java.io.Serializable;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.utils.*;

/**
 * 
 * @author prou, segarra<br>
 * <br>
 *         This class represents a subscriber for a betting application. <br>
 * <br>
 *         The constructor of the class creates a password for the subscriber. <br>
 *         <ul>
 *         <li>subscriber's password validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>password size should be at least 8 characters</li>
 *         </ul>
 *         </li>
 *         <li>for the username validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>size should be at least 4 characters</li>
 *         </ul>
 *         </li>
 *         </ul>
 * 
 */
public class Subscriber implements Serializable {
	private static final long serialVersionUID = 6050931528781005411L;
	/*
	 * Minimal size for a subscriber's username
	 */
	private static final int LONG_USERNAME = 4;
	/*
	 * Constraints for last and firstname and username
	 */
	private static final String REGEX_NAME = new String("[a-zA-Z][a-zA-Z\\-\\ ]*");
	private static final String REGEX_USERNAME = new String("[a-zA-Z0-9]*");

	private String firstname;
	private String lastname;
	/** 
	 * @uml.property name="username"
	 */
	private String username;
	private String password;

	/*
	 * the constructor calculates a password for the subscriber. No test on the
	 * validity of names
	 */
	public Subscriber(String a_name, String a_firstName, String a_username)
			throws BadParametersException {
		this.setLastname(a_name);
		this.setFirstname(a_firstName);
		this.setUsername(a_username);
		// Generate password
		password = RandPass.getPass(Constraints.LONG_PWD);
		this.setPassword(password);
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setLastname(String lastname) throws BadParametersException {
		if (lastname == null)
			throw new BadParametersException("lastname is not valid");
		checkStringLastName(lastname);
		this.lastname = lastname;
	}

	public void setFirstname(String firstname) throws BadParametersException {
		if (firstname == null)
			throw new BadParametersException("firstname is not valid");
		checkStringFirstName(firstname);
		this.firstname = firstname;
	}

	public void setUsername(String username) throws BadParametersException {
		if (username == null)
			throw new BadParametersException("username is not valid");
		checkStringUsername(username);
		this.username = username;
	}
	
	private void setPassword(String password) throws BadParametersException {
		if (password == null)
			throw new BadParametersException("password is not valid");
		if (!BettingPasswordsVerifier.verify(password))
			throw new BadParametersException("password is not valid");
		this.password = password;
	}

	/*
	 * check if this subscriber has the username of the parameter
	 * 
	 * @param username the username to check
	 * 
	 * @return true if this username is the same as the parameter false
	 * otherwise
	 */
	public boolean hasUsername(String username) {
		if (username == null)
			return false;
		return this.username.equals(username);
	}

	/*
	 * Two subscribers are equal if they have the same username
	 */
	@Override
	public boolean equals(Object an_object) {
		if (!(an_object instanceof Subscriber))
			return false;
		Subscriber s = (Subscriber) an_object;
		return this.firstname.equals(s.getFirstname());
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public String toString() {
		return " " + firstname + " " + lastname + " " + username;
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
	 *             raised if invalid.
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

	/**
	 * check the validity of a string for a subscriber username, letters and
	 * digits are allowed. username length should at least be LONG_USERNAME
	 * characters
	 * 
	 * @param a_username
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.
	 */
	private static void checkStringUsername(String a_username)
			throws BadParametersException {
		if (a_username == null)
			throw new BadParametersException("username not instantiated");

		if (a_username.length() < LONG_USERNAME)
			throw new BadParametersException("username length less than "
					+ LONG_USERNAME + "characters");
		// Just letters and digits are allowed
		if (!a_username.matches(REGEX_USERNAME))
			throw new BadParametersException("the username " + a_username
					+ " does not verify constraints ");
	}
}