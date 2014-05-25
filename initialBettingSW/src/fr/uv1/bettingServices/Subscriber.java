package fr.uv1.bettingServices;

import java.io.Serializable;
import java.util.Collection;

import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
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
/**
 * @author Mariam
 *
 */
/**
 * @author Mariam
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
	 *  identifiant du subscriber
	 */
	private long id_subscribe;
	
	/** 
	 * @uml.property name="username"
	 */
	private String username;
	
	/**
	 * the subscriber's password
	 */
	private String password;
	/**
	 * the subscriber's compte
	 */
	private Compte compte;
	
	
	/**
	 * the subscriber's bets
	 */
	private Collection<Pari> paris; 

	/*
	 *
	 * @return the compte 
	 */
	public Compte getCompte() {
		return compte;
	}

	/*
	 * @param compte the compte to set
	 */
	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	/*
	 *
	 * @return the number of token 
	 */
	public long getNumberToken() {
		return compte.getSolde();
	}
	/**
	 * @return the id_subscribe
	 */
	public long getId_subscribe() {
		return id_subscribe;
	}

	/**
	 * @param id_subscribe the id_subscribe to set
	 */
	public void setId_subscribe(long id_subscribe) {
		this.id_subscribe = id_subscribe;
	}

	/*
	 * the constructor calculates a password for the subscriber. No test on the
	 * validity of names
	 */
	public Subscriber(String a_name, String a_firstName, String borndate, String a_username) throws BadParametersException {
		super(a_name ,a_firstName,borndate);
		this.setUsername(a_username);
		// Generate password
		password = RandPass.getPass(Constraints.LONG_PWD);
		this.setPassword(password);
		this.compte=new Compte();
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
	
	public void authenticateSubscribe(String a_subscribersPwd)
			throws AuthenticationException {
		if (a_subscribersPwd == null)
			throw new AuthenticationException("invalid manager's password");

		if (!this.password.equals(a_subscribersPwd))
			throw new AuthenticationException("incorrect manager's password");
	}
	/**
	 * Change the subscriber's password
	 * @param password
	 * @throws BadParametersException
	 */
	public void changePassword(String newPwd, String currentPwd) throws BadParametersException , AuthenticationException{
			// Authenticate manager
			authenticateSubscribe(currentPwd);
			// Change password if valid
			setPassword(newPwd);
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
	public void crediter(long argent) throws BadParametersException{
		this.compte.crediterCompte(argent);
	}
	
	public void debiter(long argent) throws BadParametersException, SubscriberException{
		this.compte.debiterCompte(argent);
	}
	
	public long solde(){
		return this.compte.getSolde();
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

	/* (non-Javadoc)
	 * @see fr.uv1.bettingServices.Person#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}
	
	/**
	 * @return the string of the subscriber bets.
	 */
	public String getParis() {
		String result ="";
		for(Pari p : paris){
			result+= p +" ";
		}
		return  result;
	}
	
	
	
	/**
	 * @return the number of token betted.
	 */
	public long numberTokenBetted(){
		long result =0;
		for(Pari p : paris){
			result+= p.getMise();
		}
		return  result;
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