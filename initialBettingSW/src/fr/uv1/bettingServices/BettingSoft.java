package fr.uv1.bettingServices;

import java.util.*;

import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingSubscriberException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
import fr.uv1.utils.BettingPasswordsVerifier;

/**
 * 
 * @author prou + mallet <br>
 * <br>
 *         This class implements methods of the interface Betting. <br>
 * <br>
 *         <ul>
 *         <li>manager password validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>password size should be at least 8 characters</li>
 *         </ul>
 *         </li>
 *         </ul>
 */

public class BettingSoft implements Betting {

	/*
	 * Manager password
	 */
	private String managerPassword;

	/*
	 * Subscribers of the betting software
	 */
	/** 
	 * @uml.property name="subscribers"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="bettingSoft:fr.uv1.bettingServices.Subscriber"
	 */
	private Collection<Subscriber> subscribers;


	/**
	 * constructor of BettingSoft
	 * 
	 * @param managerPwd
	 *            manager password.
	 * 
	 * @throws BadParametersException
	 *             raised if a_managerPwd is incorrect.
	 */
	public BettingSoft(String a_managerPwd) throws BadParametersException {
		// The password should be valid
		setManagerPassword(a_managerPwd);
		this.subscribers = new ArrayList<Subscriber>();
	}

	private void setManagerPassword(String managerPassword)
			throws BadParametersException {
		if (managerPassword == null)
			throw new BadParametersException("manager's password not valid");
		// The password should be valid
		if (!BettingPasswordsVerifier.verify(managerPassword))
			throw new BadParametersException("manager's password not valid");
		this.managerPassword = managerPassword;
	}

	/**
	 * From Betting interface
	 */
	@Override
	public String subscribe(String lastName, String firstName, String username,
			String borndate, String managerPwd) throws AuthenticationException,
			ExistingSubscriberException, SubscriberException,
			BadParametersException {
		// Authenticate manager
		authenticateMngr(managerPwd);
		// Look if a subscriber with the same username already exists
		Subscriber s = searchSubscriberByUsername(username);
		if (s != null)
			throw new ExistingSubscriberException(
					"A subscriber with the same username already exists");
		// Creates the new subscriber
		s = new Subscriber(lastName, firstName, username);
		// Add it to the collection of subscribers
		subscribers.add(s);
		return s.getPassword();
		}

	/**
	 * From Betting interface
	 */
	@Override
	public long unsubscribe(String a_username, String a_managerPwd)
			throws AuthenticationException, ExistingSubscriberException {
		
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Look if a subscriber with the same username already exists
		Subscriber s = searchSubscriberByUsername(a_username);
		if (s != null){
			int result= s.getCompte().getSolde();
			subscribers.remove(s); // remove it
			
		}else
			throw new ExistingSubscriberException("Subscriber does not exist");
	    }

	/**
	 * From Betting interface
	 */
	@Override
	public ArrayList<ArrayList<String>> listSubscribers(String a_managerPwd)
			throws AuthenticationException {
		// Authenticate manager
		authenticateMngr(a_managerPwd);
		// Calculate the list of subscribers
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> subsData = new ArrayList<String>();
		for (Subscriber s : subscribers) {
			subsData.add(s.getLastname());
			subsData.add(s.getFirstname());
			subsData.add(s.getUsername());

			result.add(subsData);
		}
		return result;
	}

	/**
	 * From Betting interface
	 */
	@Override
	public void authenticateMngr(String a_managerPwd)
			throws AuthenticationException {
		if (a_managerPwd == null)
			throw new AuthenticationException("invalid manager's password");

		if (!this.managerPassword.equals(a_managerPwd))
			throw new AuthenticationException("incorrect manager's password");
	}

	/**
	 * From Betting interface
	 */
	@Override
	public void changeMngrPwd(String newPwd, String currentPwd)
			throws AuthenticationException, BadParametersException {
		// Authenticate manager
		authenticateMngr(currentPwd);
		// Change password if valid
		setManagerPassword(newPwd);
	}

	/**
	 * search a subscriber by username
	 * 
	 * @param a_username
	 *            the username of the subscriber.
	 * 
	 * @return the found subscriber or null
	 */
	private Subscriber searchSubscriberByUsername(String a_username) {
		if (a_username == null)
			return null;
		for (Subscriber s : subscribers) {
			if (s.hasUsername(a_username))
				return s;
		}
		return null;
	}
	

}