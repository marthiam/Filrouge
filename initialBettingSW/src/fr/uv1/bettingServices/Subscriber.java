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
public class Subscriber extends Person implements Serializable {
	private static final long serialVersionUID = 6050931528781005411L;
	
	
	/**
	 * La taille minimale pour le pseudo du joueur 
	 */
	private static final int LONG_USERNAME = 4;
	
	/**
	 * Contrainte  pour le pseudo du joueur 
	 */
	private static final String REGEX_USERNAME = new String("[a-zA-Z0-9]*");

	
	/**
	 * identifiant du subscriber
	 */
	private long id_subscribe;

	/**
	 * le pseudo du joueur 
	 * @uml.property name="username"
	 */
	private String username;
	
	

	/**
	 * le mot de passe du joueur 
	 */
	private String password;
	
	
	
	/**
	 * le compte du joueur 
	 */
	private Compte compte;
	
	

	/**
	 * la liste des paris du joueur 
	 */
	private Collection<Pari> paris;
	
	
	

	/**
	 * Renvoie le compte du joueur 
	 * @return compte
	 * 				le compte du joueur 
	 */
	public Compte getCompte() {
		return compte;
	}

	/**
	 * Met à jour le compte du joueur 
	 * 		@param compte 
	 * 				le nouveau compte du joueur 
	 */
	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	/**
	 * Renvoie le nombre de jetons du joueur 
	 * 		@return 
	 * 				le nombre de jetons du joueur 
	 */
	public long getNumberToken() {
		return compte.getSolde();
	}

	/**
	 * Renvoie l'identifiant du joueur 
	 * 		@return id_subscribe
	 * 				l'indentifiant du joueur 
	 */
	public long getId_subscribe() {
		return id_subscribe;
	}

	/**
	 * Met à jour le  identifiant du joueur 
	 * @param id_subscribe
	 *            le nouveau identifiant du joueur
	 */
	public void setId_subscribe(long id_subscribe) {
		this.id_subscribe = id_subscribe;
	}

	
	/**
	 * Constructeur 
	 *		 @param a_name
	 *					le nom du joueur 
	 * 		 @param a_firstName
	 * 					le prenom du joueur 
	 *  	 @param borndate
	 *  				la date de naissance du joueur 
	 *		 @param a_username
	 *					le pseudo du joueur 
	 * 		 @throws BadParametersException
	 * 				est levée si  l'un des parametres est non instancié ou pas valid 
	 */
	public Subscriber(String a_name, String a_firstName, String borndate,
			String a_username) throws BadParametersException {
		super(a_name, a_firstName, borndate);
		this.setUsername(a_username);
		// Generate password
		password = RandPass.getPass(Constraints.LONG_PWD); // calcule du mot de passe du joueur 
		this.setPassword(password);
		this.compte = new Compte();
	}

	/**
	 * Renvoie le pseusdo du joueur 
	 * @return 
	 * 		le pseudo du joueur 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Renvoie le mot de passe du joueur 
	 * @return
	 * 		le mot de passe du joueur 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Met à jour le pseudo du joueur 
	 * 		@param username
	 * 			le nouveau pseudo du joueur 
	 * @throws BadParametersException
	 * 		   est levée si nouveau pseudo est invalide ou non instancié 
	 */
	public void setUsername(String username) throws BadParametersException {
		if (username == null)
			throw new BadParametersException("username is not valid");
		checkStringUsername(username);
		this.username = username;
	}

	
	/**
	 * Met à jour le mot de passe  du joueur 
	 * 		@param password
	 * 			le nouveau mot de passe du joueur 
	 * @throws BadParametersException
	 * 		   est levée si nouveau mot de passe est invalide ou non instancié 
	 */
	
	private void setPassword(String password) throws BadParametersException {
		if (password == null)
			throw new BadParametersException("password is not valid");
		if (!BettingPasswordsVerifier.verify(password))
			throw new BadParametersException("password is not valid");
		this.password = password;
	}

	/**
	 * Authentifie le joueur avec son mot de passe 
	 * @param a_subscribersPwd
	 * 				le mot de passe du joueur 
	 * @throws AuthenticationException
	 * 				est  mot de passe est  non instancié ou pas correct
	 */
	public void authenticateSubscribe(String a_subscribersPwd)
			throws AuthenticationException {
		if (a_subscribersPwd == null)
			throw new AuthenticationException("invalid subscriber's password");

		if (!this.password.equals(a_subscribersPwd))
			throw new AuthenticationException("incorrect subscriber's password");
	}

	/**
	 * Change le mot de passe du joueur 
	 * 
	 * @param password
	 * 			le nouveau mot de passe 
	 * @throws BadParametersException
	 * 			est levée si le mot de passe est invalide 
	 *  @throws AuthenticationException
	 *  		est levée si le mot de passe est incorret 
	 */
	public void changePassword(String newPwd, String currentPwd)
			throws BadParametersException, AuthenticationException {
		// Authenticate manager
		authenticateSubscribe(currentPwd);
		// Change password if valid
		setPassword(newPwd);
	}

	/**
	 * Verifie si le joueur possède le pseudo passé en parametre comme pseudo 
	 * 
	 * @param username 
	 * 			le pseudo a verifié 
	 * 
	 * @return 
	 * 			Vraie si le pseudo passé en paramètre est le même que celui du joueur 
	 */
	public boolean hasUsername(String username) {
		if (username == null)
			return false;
		return this.username.equals(username);
	}
	
	
	/**
	 * Ajoute au compte du joueur le nombre de jetons passés en paramètre.
	 * @param argent
	 * 			le nombre de jetons a ajouté .
	 * @throws BadParametersException
	 * 			est levée si le nombre demandé est <0 
	 * @see fr.uv1.bettingServices.Compte#crediterCompte(long)
	 */
	public void crediter(long argent) throws BadParametersException {
		this.compte.crediterCompte(argent);
	}

	/**
	 * Envele du compte du joueur le nombre de jetons passés en paramètre.
	 * @param argent
	 * 			le nombre de jetons a enlevé .
	 * @throws BadParametersException
	 * 			est levée si le nombre demandé est <0 
	 * @throws SubscriberException
	 * 			est levée si le joueur n'a pas assez de jetons pour effectuer cette operation
	 * @see fr.uv1.bettingServices.Compte#debiterCompte(long)
	 */
	public void debiter(long argent) throws BadParametersException,
			SubscriberException {
		this.compte.debiterCompte(argent);
	}

	/**
	 * Renvoie le solde du compte du joueur.
	 * @return
	 * 		le nombre de jetons que possède le joueur.
	 */
	public long solde() {
		return this.compte.getSolde();
	}

	/**
	 * Renvoie si deux joueur sont égaux.
	 * @return 
	 * 		si deux joueurs ont le même psudo.
	 */

	@Override
	public boolean equals(Object an_object) {
		if (an_object instanceof Subscriber){
			Subscriber s = (Subscriber) an_object;
			return this.username.equals(s.getUsername());
			
		}else if (an_object instanceof Person ) return super.equals(an_object);
			return false;
		
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.uv1.bettingServices.Person#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}
	

	/**
	 * Renvoie la chaîne de caratères representant les paris en cours du joueurs.
	 * @return 
	 * 			les paris du joueur sous forme de chaône de caractères.
	 */
	public String getParis() {
		String result = "";
		for (Pari p : paris) {
			result += p + " ";
		}
		return result;
	}

	/**
	 * Renvoie le nombre de jetions  pariés  par le joueur.
	 * 		@return 
	 * 				le nombre de jetons pariés par le joueur.
	 */
	public long numberTokenBetted() {
		long result = 0;
		for (Pari p : paris) {
			result += p.getMise();
		}
		return result;
	}

	/*
	 * Verifie la validité de la cha^ne de caractère du pseudo du joueur 
	 * 
	 * @param a_username
	 *            la chaîne de caractère a verifié.
	 * 
	 * @throws BadParametersException
	 *             est levée si elle est invalide.
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