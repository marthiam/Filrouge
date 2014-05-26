package fr.uv1.bd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.uv1.bettingServices.Person;
import fr.uv1.bettingServices.Team;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.utils.DataBaseConnection;

public class PersonsManager {

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
				 * Find a Team's id using his teamName .
				 * 
				 * @param id
				 *            the id of the competitor to retrieve.
				 * @return the person's id or 0 if it does not exist in the database.
				 * @throws SQLException
				 * @throws BadParametersException
				 */
				public static long findByTeamName(Team team) throws SQLException,
						BadParametersException {
					// 1 - Get a database connection from the class 'DatabaseConnection'
					Connection c = DataBaseConnection.getConnection();

					// 2 - Creating a Prepared Statement with the SQL instruction.
					// The parameters are represented by question marks.
					PreparedStatement psSelect = c
							.prepareStatement("select id_personne from personne where nom=? and type=? ");

					// 3 - Supplying values for the prepared statement parameters (question
					// marks).
					psSelect.setString(1, team.getTeamName());
					psSelect.setString(2, "CompetitorTeam");


					// 4 - Executing Prepared Statement object among the database.
					// The return value is a Result Set containing the data.
					ResultSet resultSet = psSelect.executeQuery();
					long id = 0;
					while (resultSet.next()) {
						id = resultSet.getLong("id_personne");
					}

					return id;
				}
}
