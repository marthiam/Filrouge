package fr.uv1.bd;

import fr.uv1.utils.DataBaseConnection;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import fr.uv1.bettingServices.*;
import fr.uv1.bettingServices.exceptions.BadParametersException;

/**
 * DAO class (<i>Data Access Object</i>) for the {@link Competitor} interface.
 * This class provides the CRUD functionalities :<br>
 * <ul>
 * <li><b>C</b>: create a new competitor in the database.
 * <li><b>R</b>: retrieve (or read) a (list of)competitor(s) in the database.
 * <li><b>U</b>: update the values stored in the database for a competitor.
 * <li><b>D</b>: delete a competitor in the database.
 * </ul>
 * 
 * @author THIAM Mariam
 */
public class CompetitorsManager {
	// -----------------------------------------------------------------------------
	/**
	 * Store a competitor in the database. This competitor is not stored yet, so
	 * his <code>id</code> value is <code>NULL</code>. Once the competitor is
	 * stored, the method returns the competitor with the <code>id</code> value
	 * setted.
	 * 
	 * @param competitor
	 *            the competitor to be stored.
	 * @return the competitor with the updated value for the id.
	 * @throws SQLException
	 * @throws BadParametersException
	 */
	public static Competitor persist(Competitor competitor)
			throws SQLException, BadParametersException {
		// Two steps in this methods which must be managed in an atomic
		// (unique) transaction:
		// 1 - insert the new subscriber;
		// 2 - once the insertion is OK, in order to set up the value
		// of the id, a request is done to get this value by
		// requesting the sequence (subscribers_id_seq) in the
		// database.
		Connection c = DataBaseConnection.getConnection();
		try {
			c.setAutoCommit(false);

			if (competitor instanceof Individual) {
				PreparedStatement psPersist = c
						.prepareStatement(
								"insert into personne(prenom,nom,borndate,type)  values (?,?,?,?)",
								PreparedStatement.RETURN_GENERATED_KEYS);
				Individual competiteur = (Individual) competitor;
				psPersist.setString(1, competiteur.getFirstname());
				psPersist.setString(2, competiteur.getLastname());

				Date date = Date.valueOf(competiteur.getBorndateDate());
				System.out.println("voici la date" + date);
				psPersist.setDate(3, date);
				psPersist.setString(4, "CompetiteurIndiv");

				psPersist.executeUpdate();
				PreparedStatement psIdValue = c
						.prepareStatement("select currval('personne_id_seq') as value_id");
				ResultSet resultSet = psIdValue.executeQuery();
				Integer id = null;
				while (resultSet.next()) {
					id = resultSet.getInt("value_id");
				}
				psPersist.close();
				c.commit();
				competiteur.setId_individual(id);
				resultSet.close();
				psIdValue.close();
				c.setAutoCommit(true);
				c.close();

				return competiteur;

			} else {
				PreparedStatement psPersist = c.prepareStatement(
						"insert into personne(nom,type)  values (?,?)",
						PreparedStatement.RETURN_GENERATED_KEYS);
				Team competiteur = (Team) competitor;
				psPersist.setString(1, competiteur.getTeamName());
				psPersist.setString(2, "CompetiteurTeam");
				psPersist.executeUpdate();
				PreparedStatement psIdValue = c
						.prepareStatement("select currval('personne_id_seq') as value_id");
				ResultSet resultSet = psIdValue.executeQuery();
				Integer id = null;
				while (resultSet.next()) {
					id = resultSet.getInt("value_id");
				}
				psPersist.close();
				c.commit();
				competiteur.setId_team(id);

				// persist Team members
				Collection<Competitor> members = competiteur.getMembers();
				for (Competitor member : members) {
					System.out.println("membre de l'equipe "
							+ competiteur.getTeamName() + " :" + member);
					Individual indiv = (Individual) member;
					Long ind = findByName(indiv);
					if (ind == 0) {
						indiv = (Individual) CompetitorsManager.persist(indiv);
					}

					PreparedStatement psPersistMember = c
							.prepareStatement("insert into estMembreDe(member_id,team_id)  values (?,?)");
					psPersistMember.setLong(1, indiv.getId_individual());
					psPersistMember.setLong(2, competiteur.getId_team());
					psPersistMember.executeUpdate();
					psPersistMember.close();

				}

				c.setAutoCommit(true);
				c.close();
				return competiteur;
			}
		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			c.setAutoCommit(true);
			throw e;
		}

	}

	// -----------------------------------------------------------------------------
	/**
	 * Find a competitor by his id.
	 * 
	 * @param id
	 *            the id of the competitor to retrieve.
	 * @return the competitor or null if the id does not exist in the database.
	 * @throws SQLException
	 * @throws BadParametersException
	 */
	public static Competitor findById(long id) throws SQLException,
			BadParametersException {

		// 1 - Get a database connection from the class 'DatabaseConnection'
		Connection c = DataBaseConnection.getConnection();

		// 2 - Creating a Prepared Statement with the SQL instruction.
		// The parameters are represented by question marks.
		PreparedStatement psSelect = c
				.prepareStatement("select * from personne where id_personne=? and (type=? or type=?)");

		// 3 - Supplying values for the prepared statement parameters (question
		// marks).
		psSelect.setLong(1, id);
		psSelect.setString(2, "CompetiteurIndiv");
		psSelect.setString(3, "CompetiteurTeam");

		// 4 - Executing Prepared Statement object among the database.
		// The return value is a Result Set containing the data.
		ResultSet resultSet = psSelect.executeQuery();

		// 5 - Retrieving values from the Result Set.
		Competitor competitor = null;
		Individual indiv = null;
		Team team = null;
		while (resultSet.next()) {

			if (resultSet.getString("type").equals("CompetiteurIndiv")) {
				String[] s = resultSet.getString("borndate").split("-");
				int annee = new Integer(s[0]);
				int mois = new Integer(s[1]);
				int jour = new Integer(s[2]);
				indiv = new Individual(resultSet.getString("prenom"),
						resultSet.getString("nom"), jour + "-" + mois + "-"
								+ annee);
				indiv.setId_individual(id);
				// 6 - Closing the Result Set
				resultSet.close();

				// 7 - Closing the Prepared Statement.
				psSelect.close();

				// 8 - Closing the database connection.
				c.close();
				return indiv;

			} else {

				team = new Team(resultSet.getString("nom"));
				team.setId_team(id);
				// 6 - Closing the Result Set
				resultSet.close();

				// 7 - Closing the Prepared Statement.
				psSelect.close();

				PreparedStatement psSelectMembers = c
						.prepareStatement("select * from personne P inner join estmembrede E on  P.id_personne= E.member_id where E.team_id=?");
				psSelectMembers.setLong(1, team.getId_team());
				ResultSet resultSetMembers = psSelectMembers.executeQuery();
				Collection<Competitor> competitors = new HashSet<Competitor>();
				Individual member = null;
				while (resultSetMembers.next()) {
					String[] s = resultSetMembers.getString("borndate").split(
							"-");
					int annee = new Integer(s[0]);
					int mois = new Integer(s[1]);
					int jour = new Integer(s[2]);
					member = new Individual(
							resultSetMembers.getString("prenom"),
							resultSetMembers.getString("nom"), jour + "-"
									+ mois + "-" + annee);
					member.setId_individual(resultSetMembers
							.getLong("id_personne"));
					competitors.add(member);
					System.out.println("membre " + member);
				}

				resultSetMembers.close();
				psSelectMembers.close();

				team.setMembers(competitors);
				c.close();

				return team;

			}
		}

		return competitor;

	}

	// -----------------------------------------------------------------------------
	/**
	 * Find all the subscribers in the database.
	 * 
	 * @return the list of competitors
	 * @throws SQLException
	 * @throws BadParametersException
	 */
	public static List<Competitor> findAll() throws SQLException,
			BadParametersException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from personne where (type=? or type=? ) order by id_personne");
		psSelect.setString(1, "CompetiteurTeam");
		psSelect.setString(2, "CompetiteurIndiv");
		ResultSet resultSet = psSelect.executeQuery();
		List<Competitor> competitors = new ArrayList<Competitor>();
		Competitor competitor = null;

		while (resultSet.next()) {
			long id = resultSet.getLong("id_personne");
			competitor = CompetitorsManager.findById(id);
			competitors.add(competitor);
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return competitors;
	}

	// -----------------------------------------------------------------------------
	/**
	 * Find a person's id using his firstnam, lastname and borndate.
	 * 
	 * @param id
	 *            the id of the competitor to retrieve.
	 * @return the person's id or 0 if it does not exist in the database.
	 * @throws SQLException
	 * @throws BadParametersException
	 */
	public static long findByName(Person p) throws SQLException,
			BadParametersException {
		// 1 - Get a database connection from the class 'DatabaseConnection'
		Connection c = DataBaseConnection.getConnection();

		// 2 - Creating a Prepared Statement with the SQL instruction.
		// The parameters are represented by question marks.
		PreparedStatement psSelect = c
				.prepareStatement("select id_personne from personne where prenom=? and nom=? and borndate=? ");

		// 3 - Supplying values for the prepared statement parameters (question
		// marks).
		psSelect.setString(1, p.getFirstname());
		psSelect.setString(2, p.getLastname());
		psSelect.setDate(3, Date.valueOf(p.getBorndateDate()));

		// 4 - Executing Prepared Statement object among the database.
		// The return value is a Result Set containing the data.
		ResultSet resultSet = psSelect.executeQuery();
		long id = 0;
		while (resultSet.next()) {
			id = resultSet.getLong("id_personne");
		}

		return id;
	}

	// -----------------------------------------------------------------------------
	/**
	 * Update on the database the values from a competitor.
	 * 
	 * @param competitor
	 *            the competitor to be updated.
	 * @throws SQLException
	 */
	public static void update(Competitor competitor) throws SQLException {
		// 1 - Get a database connection from the class 'DatabaseConnection'
		Connection c = DataBaseConnection.getConnection();

		if (competitor instanceof Individual) {

			// 2 - Creating a Prepared Statement with the SQL instruction.
			// The parameters are represented by question marks.
			PreparedStatement psUpdate = c
					.prepareStatement("update personne set prenom=?, nom=?,borndate=? where id_personne=?");

			// 3 - Supplying values for the prepared statement parameters
			// (question marks).
			psUpdate.setString(1, ((Individual) competitor).getFirstname());
			psUpdate.setString(2, ((Individual) competitor).getLastname());
			Date date = Date.valueOf(((Individual) competitor)
					.getBorndateDate());
			psUpdate.setDate(3, date);
			psUpdate.setLong(4, ((Individual) competitor).getId_individual());
			// Executing the prepared statement object among the database.
			// If needed, a return value (int) can be obtained. It contains
			// how many rows of a table were updated.
			// int nbRows = psUpdate.executeUpdate();
			psUpdate.executeUpdate();

			// 6 - Closing the Prepared Statement.
			psUpdate.close();

			// 7 - Closing the database connection.
			c.close();

		} else {
			PreparedStatement psUpdate = c
					.prepareStatement("update personne set  nom=? where id_personne=?");
			psUpdate.setString(1, ((Team) competitor).getTeamName());
			psUpdate.setLong(2, ((Team) competitor).getId_team());
			Collection<Competitor> members = ((Team) competitor).getMembers();
			for (Competitor member : members) {
				CompetitorsManager.update(member);
			}

			psUpdate.executeUpdate();

			// 6 - Closing the Prepared Statement.
			psUpdate.close();

			// 7 - Closing the database connection.
			c.close();

		}
	}

	// -----------------------------------------------------------------------------
	/**
	 * Delete from the database a specific competitor.<br>
	 * 
	 * 
	 * @param competitor
	 *            the competitor to be deleted.
	 * @throws SQLException
	 */
	public static void delete(Competitor competitor) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psUpdate;
		if (competitor instanceof Individual) {
			psUpdate = c
					.prepareStatement("delete from personne P where P.id_personne=? and P.id_personne not in (select member_id from estmembrede where true)");
			psUpdate.setLong(1, ((Individual) competitor).getId_individual());
		} else {
			psUpdate = c
					.prepareStatement("delete from personne where id_personne=? ");
			psUpdate.setLong(1, ((Team) competitor).getId_team());

		}
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	// -----------------------------------------------------------------------------
}
