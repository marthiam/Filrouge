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
public class Subscriber extends Person implements Serializable {
	private static final long serialVersionUID = 6050931528781005411L;
	/*
	 * Minimal size for a subscriber's username
	 */
	private static final int LONG_USERNAME = 4;
	/*
	 * Constraints for a username
	 */
	private static final String REGEX_USERNAME = new String("[a-zA-Z0-9]*");


	/** 
	 * @uml.property name="username"
	 */
	private String username;
	private String password;
	private Compte compte;

	/**
	 *
	 * @return the compte 
	 */
	public Compte getCompte() {
		return compte;
	}

	/**
	 * @param compte the compte to set
	 */
	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	/*
	 * the constructor calculates a password for the subscriber. No test on the
	 * validity of names
	 */
	public Subscriber(String a_name, String a_firstName, String a_username) throws BadParametersException {
		super(a_name ,a_firstName);
		this.setUsername(a_username);
		// Generate password
		password = RandPass.getPass(Constraints.LONG_PWD);
		this.setPassword(password);
	}

	

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
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
		return this.username.equals(s.getUsername());
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public String toString() {
		return " " + super.getFirstname() + " " + super.getLastname() + " " + username;
	}


	/**
	 * check the validity of a string for a subscriber username, letters and
	 * digits are allowed. username length should at least be LONG_USERNAME
	 * charactersserialVersionUID
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