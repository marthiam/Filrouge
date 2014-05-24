package fr.uv1.bettingServices;

import java.util.Collection;
import java.util.HashSet;


import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

/**
 * @author Mariam
 *
 */
/**
 * @author Mariam
 *
 */
public  class Team implements Competitor {
	
	private String teamName;
	private Collection<Competitor> members;
	/**
	 * La taille minimum du nom d'une equipe
	 * 
	 */
	private static final int LONG_TEAMNAME = 4;
	
	/**
	 * La contrainte que le nom de l'equipe doit verifier
	 */
	private static final String REGEX_TEAMNAME = new String("[a-zA-Z][a-zA-Z0-9\\-\\ ]*");
	
	/** 
	 *  identifiant du subscriber
	 */
	private long id_team;
	

	/**
	 * Construit une equipe sans menbres 
	 * @param teamName le 
	 */
	public Team(String teamName) throws BadParametersException{
		setTeamName(teamName);
		this.members = new HashSet<Competitor>();
	}

	/**
	 * Constructeur  
	 * @param teamName
	 * @param menbers
	 * @throws BadParametersException 
	 */
	public Team(String teamName, Collection<Competitor> menbers) throws BadParametersException {
		setTeamName(teamName);
		setMembers(menbers);
	}

	/**
	 * tells if the name of the competitor is a valid one.
	 * 
	 * @return true if the competitor has a valid name.
	 */
	public boolean hasValidName(){
		return teamName.length()>=LONG_TEAMNAME && teamName.matches(REGEX_TEAMNAME);
	}
	
	
	/**
	 * Renvoie si la competiteur est un menbre de l'equipe
	 * 			@param c le competiteur a testé
	 * 			@return true si et seulement si  c est un menbre de l'equie 
	 * @throws BadParametersException
	 * 				est levée si c n'est pas de type Individual
	 */
	public boolean hasMember(Competitor c) throws  BadParametersException{
		if (c instanceof  Individual) {
			for ( Competitor i:this.members ){
				if(c.equals(i) ){
					return true;
				}
			}
		}else{
			throw new  BadParametersException("le type de competiteur doit etre de type Indivudual pour etre menbre d'une equipe ");
		}
		return false;
	}
	/**
	 * add a member to a team competitor.
	 * 
	 * @throws ExistingCompetitorException
	 *             raised if the member is already registered for the team.
	 * @throws BadParametersException
	 *             raised if the member is not instantiated.
	 */
	public void addMember(Competitor member) throws ExistingCompetitorException,
			BadParametersException{
		//System.out.println("on veut ajouter " +member +" à l'équipe "+ this.teamName);
		if(member==null || !member.hasValidName() || !(member instanceof Individual)) throw new BadParametersException(member +"est pas un parametre valide dans addMenber");
		if(!members.contains(member)){
			members.add(member);
			//System.out.println("il a été ajouté ");
		}else{
			throw new ExistingCompetitorException(member.toString() +" existe déja "); 
		}
		
		
	}

	/**
	 * delete a member from a team competitor.
	 * 
	 * @return true if the competitor has been deleted from the team.
	 * @throws BadParametersException
	 *             raised if the member is not instantiated.
	 * @throws ExistingCompetitorException
	 *             raised if the member is not registered for the team.
	 */
	public void deleteMember(Competitor member) throws BadParametersException,
			ExistingCompetitorException{
		if(member==null || !member.hasValidName()) throw new BadParametersException(member +"est pas un parametre valide dans addMenber");
		if(members.contains(member)){
			members.remove(member);
		}else{
			throw new ExistingCompetitorException(member +"ne peut pas être supprimer car elle n'existe pas"); 
		}
	}
	private static void checkStringNomTeam(String nomTeam)
			throws BadParametersException {
		if (nomTeam == null)
			throw new BadParametersException("Le nom de la compétition n'a pas été instancié");
		
		if (nomTeam.length() < LONG_TEAMNAME)
			throw new BadParametersException("Le nom de l'equipe possède au moins "
					+ LONG_TEAMNAME + "caractères");
		// Seuls les lettres, les chiffres, les tirets et underscore sont autorisés
		if (!nomTeam.matches(REGEX_TEAMNAME))
			throw new BadParametersException("le nom " + nomTeam
					+ " ne verifie pas les contraintes ");
	}

	/**
	 * @return the id_team
	 */
	public long getId_team() {
		return id_team;
	}

	/**
	 * @param id_team the id_team to set
	 */
	public void setId_team(long id_team) {
		this.id_team = id_team;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @return the members
	 */
	public Collection<Competitor> getMembers() {
		return members;
	}

	/**
	 * @param teamName the teamName to set
	 * @throws BadParametersException 
	 */
	public void setTeamName(String teamName) throws BadParametersException {
		checkStringNomTeam(teamName);
		this.teamName = teamName;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(Collection<Competitor> members)throws BadParametersException {
		if(members==null) throw new BadParametersException();
		this.members = members;
	}	
	
	/**
	 *  return true si le 2 equipes on le même nom 
	 */

	public boolean equals(Object obj){
		if (obj==null)
			return false;
		
		if (!(obj instanceof Team))
			return false;
		
		if (((Team) obj).getTeamName()==null)
			return false;
		
		
		return this.teamName.equals(((Team) obj).getTeamName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String menber = "";
		for(Competitor i :this.members){
			menber += " "+ i.toString();
		}
		return "Team [teamName=" + teamName + ", members=" + menber+" de taille" +this.members.size()+ " ]";
	}
	  
}
