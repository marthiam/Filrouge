package fr.uv1.bettingServices;

import java.util.Collection;
import java.util.HashSet;

import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

/**
 * @author Mariam
 * 
 */
public class Team implements Competitor {

	/**
	 * le nom de l'equipe 
	 */
	private String teamName;
	
	
	
	/**
	 * les membres de l'equipes 
	 */
	private Collection<Competitor> members;
	
	
	/**
	 * La taille minimum du nom d'une equipe
	 */
	private static final int LONG_TEAMNAME = 4;

	
	
	/**
	 * La contrainte que le nom de l'equipe doit verifier
	 */
	private static final String REGEX_TEAMNAME = new String(
			"[a-zA-Z][a-zA-Z0-9\\-\\ ]*");
	

	/**
	 * identifiant du subscriber
	 */
	private long id_team;
	

	/**
	 * Construit une equipe sans menbres
	 * 
	 * @param teamName
	 *            le nom de l'equipe 
	 * 
	 */
	public Team(String teamName) throws BadParametersException {
		setTeamName(teamName);
		this.members = new HashSet<Competitor>();
	}

	/**
	 * Constructeur  une equipe avec membres <br>
	 * 
	 * @param teamName
	 *            le nom de l'equipe
	 * @param menbers
	 *            la liste des membres de l'equipe
	 * @throws BadParametersException
	 *             est levee si le nom ou les membres de l'equipe ne sont pas
	 *             correct
	 */
	public Team(String teamName, Collection<Competitor> menbers)
			throws BadParametersException {
		setTeamName(teamName);
		setMembers(menbers);
	}

	/**
	 *Retourne si l'equipe a un nom valide .<br>
	 * 
	 * @return true si l'equie a une nom valide
	 */
	public boolean hasValidName() {
		return teamName.length() >= LONG_TEAMNAME
				&& teamName.matches(REGEX_TEAMNAME);
	}

	/**
	 * Retourne si le competiteur passé en paramètre est membre de l'equipe<br>
	 * 
	 * @param competitor
	 *            le competiteur a testé
	 * @return true si et seulement si c est un menbre de l'equie
	 * @throws BadParametersException
	 *             est levée si c n'est pas de type Individual
	 */
	public boolean hasMember(Competitor competitor) throws BadParametersException {
		if (competitor instanceof Individual) {
			for (Competitor i : this.members) {
				if (competitor.equals(i)) {
					return true;
				}
			}
		} else {
			throw new BadParametersException(
					"le type de competiteur doit etre de type Indivudual pour etre menbre d'une equipe ");
		}
		return false;
	}

	/**
	 * Ajoute un member à l'equipe .<br>
	 * 
	 * @throws ExistingCompetitorException
	 *             est levée si le membre a dejà été enregistré comme membre de
	 *             l'équipe.
	 * @throws BadParametersException
	 *             est levée si le membre n'est pas instancié.
	 */
	public void addMember(Competitor member)
			throws ExistingCompetitorException, BadParametersException {
		// System.out.println("on veut ajouter " +member +" à l'équipe "+
		// this.teamName);
		if (member == null || !member.hasValidName()
				|| !(member instanceof Individual))
			throw new BadParametersException(member
					+ "est pas un parametre valide dans addMenber");
		if (!members.contains(member)) {
			members.add(member);
			// System.out.println("il a été ajouté ");
		} else {
			throw new ExistingCompetitorException(member.toString()
					+ " existe déja ");
		}

	}

	/**
	 * Supprime un membre de l'equipe .<br>
	 * 
	 * @throws BadParametersException
	 *             est levée si le membre n'est pas instancié.
	 * @throws ExistingCompetitorException
	 *             est levée si le membre n'est un membre de l'équipe.
	 */
	public void deleteMember(Competitor member) throws BadParametersException,
			ExistingCompetitorException {
		if (member == null || !member.hasValidName())
			throw new BadParametersException(member
					+ "est pas un parametre valide dans addMenber");
		if (members.contains(member)) {
			members.remove(member);
		} else {
			throw new ExistingCompetitorException(member
					+ "ne peut pas être supprimer car elle n'existe pas");
		}
	}

	/**
	 * Verifie si le nom de l'equipe est correct <br>
	 * 
	 * @param nomTeam
	 *            le nom de l'equipe a verifier
	 * @throws BadParametersException
	 */
	private static void checkStringNomTeam(String nomTeam)
			throws BadParametersException {
		if (nomTeam == null)
			throw new BadParametersException(
					"Le nom de la compétition n'a pas été instancié");

		if (nomTeam.length() < LONG_TEAMNAME)
			throw new BadParametersException(
					"Le nom de l'equipe possède au moins " + LONG_TEAMNAME
							+ "caractères");
		// Seuls les lettres, les chiffres, les tirets et underscore sont
		// autorisés
		if (!nomTeam.matches(REGEX_TEAMNAME))
			throw new BadParametersException("le nom " + nomTeam
					+ " ne verifie pas les contraintes ");
	}

	/**
	 * Renvoie l'indentifiant de l'equipe 
	 *     @return id_team 
	 *     				l'identifiant de l'equipe 
	 */
	public long getId_team() {
		return id_team;
	}

	/**
	 * Met à jour l'identifiant de l'equipe
	 * @param id_team
	 *            le nouveau identifiant de l'equipe
	 */
	public void setId_team(long id_team) {
		this.id_team = id_team;
	}

	/**
	 * Renvoie le nom de l'equipe 
	 * 
	 * 		@return teamName 
	 * 					le nom de l'equipe
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * Renvoie la liste des membres de l'equipe
	 * 
	 *		 @return members
	 *					 la liste des membres de l'equipe
	 */
	public Collection<Competitor> getMembers() {
		return members;
	}

	/**
	 * Met à jour le nom de l'equipe <br>
	 * 
	 * @param teamName
	 *            le nouveau nom de l'equipe
	 * @throws BadParametersException
	 *             est levée si cet nom n'est pas correct.
	 */
	public void setTeamName(String teamName) throws BadParametersException {
		checkStringNomTeam(teamName);
		this.teamName = teamName;
	}

	/**
	 * Met à jour les membres de l'equipe
	 * @param members
	 *            la nouvelle liste des membres de l'equipe
	 * @throws BadParametersException
	 *             est levée si la liste est non instancié
	 */
	public void setMembers(Collection<Competitor> members)
			throws BadParametersException {
		if (members == null)
			throw new BadParametersException();
		this.members = members;
	}

	/**
	 *  Renvoie si les deux equipes ont le même nom 
	 *  
	 * @return true si le 2 equipes ont le même nom
	 */

	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof Team))
			return false;

		if (((Team) obj).getTeamName() == null)
			return false;

		return this.teamName.equals(((Team) obj).getTeamName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String menber = "";
		for (Competitor i : this.members) {
			menber += " " + i.toString();
		}
		return "Team [teamName=" + teamName + ", members=" + menber
				+ " de taille" + this.members.size() + " ]";
	}

}
