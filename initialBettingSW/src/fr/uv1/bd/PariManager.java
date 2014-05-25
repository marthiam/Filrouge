package fr.uv1.bd;

import java.sql.*;
import java.util.*;

import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Pari;
import fr.uv1.bettingServices.PariWinner;
import fr.uv1.bettingServices.PariPodium;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.Team;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.utils.DataBaseConnection;

/**
 * DAO class (<i>Data Access Object</i>) for the {@link Bet} class. This class
 * provides the CRUD functionalities :<br>
 * <ul>
 * <li><b>C</b>: create a new bet in the database.
 * <li><b>R</b>: retrieve (or read) a (list of)bet(s) from the database.
 * <li><b>U</b>: update the values stored in the database for a bet.
 * <li><b>D</b>: delete a bet in the database.
 * </ul>
 * 
 * @author Philippe TANGUY
 */
public class PariManager {
	// -----------------------------------------------------------------------------
	/**
	 * Store a bet in the database for a specific subscriber (the subscriber is
	 * included inside the Bet object). This bet is not stored yet, so his
	 * <code>id</code> value is <code>NULL</code>. Once the bet is stored, the
	 * method returns the bet with the <code>id</code> value setted.
	 * 
	 * @param bet
	 *            the bet to be stored.
	 * @return the bet with the updated value for the id.
	 * @throws SQLException
	 */
	public static Pari persist(Pari pari) throws SQLException {
		// Two steps in this method which must be managed in an atomic
		// (unique) transaction:
		// 1 - insert the new bet;
		// 2 - once the insertion is OK, in order to set up the value
		// of the id, a request is done to get this value by
		// requesting the sequence (bets_id_seq) in the
		// database.
		Connection c = DataBaseConnection.getConnection();
		try {
			c.setAutoCommit(false);
			if (pari instanceof PariWinner){
				PreparedStatement psPersist = c
					.prepareStatement("insert into pari(id_pari, id_joueur, mise, " +
							"competiteurpremiereID, type, id_competition) " +
							"values (?, ?, ?, ?, ?, ?)");
			
			
			psPersist.setInt(1, pari.getPari_id());
			psPersist.setLong(2, pari.getSubscriber().getId_subscribe());
			psPersist.setLong(3, pari.getMise());
			if (((PariWinner) pari).getWinner() instanceof Individual)
				psPersist.setLong(4, ((Individual) ((PariWinner) pari).getWinner()).getId_individual());
			else
				psPersist.setLong(4, ((Team) ((PariWinner) pari).getWinner()).getId_team());
			psPersist.setString(5, "pariWinner");
			psPersist.setInt(6, pari.getCompetition_id());
			psPersist.executeUpdate();

			psPersist.close();

			// Retrieving the value of the id with a request on the
			// sequence (subscribers_id_seq).
			PreparedStatement psIdValue = c
					.prepareStatement("select currval('bets_id_seq') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer id = null;
			while (resultSet.next()) {
				id = resultSet.getInt("value_id");
			}
			resultSet.close();
			psIdValue.close();
			c.commit();
			pari.setPari_id(id);
			c.setAutoCommit(true);
			c.close();
			}
			
			
			else{
				PreparedStatement psPersist = c
						.prepareStatement("insert into pari(id_pari, id_joueur, mise, " +
								"competiteurpremiereID, competiteurdeuxiemeID, " +
								"competiteurtroisiemeID, type, id_competition) " +
								"values (?, ?, ?, ?, ?, ?)");
				
				
				psPersist.setInt(1, pari.getPari_id());
				psPersist.setLong(2, pari.getSubscriber().getId_subscribe());
				psPersist.setLong(3, pari.getMise());
				if (((PariPodium) pari).getWinner() instanceof Individual){
					psPersist.setLong(4, ((Individual) ((PariPodium) pari).getWinner()).getId_individual());
					psPersist.setLong(4, ((Individual) ((PariPodium) pari).getSecond()).getId_individual());
					psPersist.setLong(4, ((Individual) ((PariPodium) pari).getThird()).getId_individual());
				}
				else{
					psPersist.setLong(4, ((Team) ((PariPodium) pari).getWinner()).getId_team());
					psPersist.setLong(4, ((Team) ((PariPodium) pari).getSecond()).getId_team());
					psPersist.setLong(4, ((Team) ((PariPodium) pari).getThird()).getId_team());
				}
				psPersist.setString(5, "pariPodium");

				psPersist.executeUpdate();

				psPersist.close();

				// Retrieving the value of the id with a request on the
				// sequence (subscribers_id_seq).
				PreparedStatement psIdValue = c
						.prepareStatement("select currval('pari_id_seq') as value_id");
				ResultSet resultSet = psIdValue.executeQuery();
				Integer id = null;
				while (resultSet.next()) {
					id = resultSet.getInt("value_id");
				}
				resultSet.close();
				psIdValue.close();
				c.commit();
				pari.setPari_id(id);
				c.setAutoCommit(true);
				c.close();
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
		return pari;


	}

	// -----------------------------------------------------------------------------
	/**
	 * Find a bet by his id.
	 * 
	 * @param id
	 *            the id of the bet to retrieve.
	 * @return the bet or null if the id does not exist in the database.
	 * @throws SQLException
	 */
	public static Pari findById(Integer id) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from pari where id_pari=?");
		ResultSet resultSet = psSelect.executeQuery();
		Pari pari = null;
		while (resultSet.next()) {
			Subscriber subscriber;
			
			try {
				subscriber = SubscribersManager.findById(resultSet.getInt("id_joueur"));
				Long mise = resultSet.getLong("mise");
				if (resultSet.getString("type").equals("pariWinner")){
					Competitor winner;
					winner = CompetitorsManager.findById(resultSet.getInt("competiteurpremiereID"));
					
					int competition_id = resultSet.getInt("id_competition");
					pari = new PariWinner(mise, subscriber, winner);
					pari.setCompetition_id(competition_id);
	
				}
	
				
				else{
					Competitor winner;
					winner = CompetitorsManager.findById(resultSet.getInt("competiteurpremiereID"));
	
					Competitor second;
					second = CompetitorsManager.findById(resultSet.getInt("competiteurdeuxiemeID"));
	
					Competitor third;
					third = CompetitorsManager.findById(resultSet.getInt("competiteurtroisiemeID"));
	
					int competition_id = resultSet.getInt("id_competition");
					pari = new PariPodium(mise, subscriber, winner, second, third);
					pari.setCompetition_id(competition_id);
				}
			
			} catch (BadParametersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return pari;
	}

	// -----------------------------------------------------------------------------
	/**
	 * Find all the bets for a specific subscriber in the database.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<Pari> findBySubscriber(Subscriber subscriber)
			throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from Pari where id_joueur=? order by id_pari");
		psSelect.setLong(1, subscriber.getId_subscribe());
		ResultSet resultSet = psSelect.executeQuery();
		List<Pari> betList = new ArrayList<Pari>();
		Pari pari;
		while (resultSet.next()) {
			pari = PariManager.findById(resultSet.getInt("id_pari"));
			betList.add(pari);
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return betList;
	}

	// -----------------------------------------------------------------------------
	/**
	 * Find all the bets in the database.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<Pari> findAll() throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from pari order by id_joueur,id_pari");
		ResultSet resultSet = psSelect.executeQuery();
		List<Pari> betList = new ArrayList<Pari>();
		Pari pari;
		while (resultSet.next()) {
			pari = PariManager.findById(resultSet.getInt("id_pari"));
			betList.add(pari);
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return betList;
	}

	// -----------------------------------------------------------------------------
	/**
	 * Update on the database the values from a bet. Useful?
	 * 
	 * @param bet
	 *            the bet to be updated.
	 * @throws SQLException
	 */
	public static void update(Pari pari) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		
		if (pari instanceof PariWinner){
			PreparedStatement psUpdate = c
					.prepareStatement("update pari set id_joueur=?, mise=?, competiteurpremiereID=?, type=?, id_competition=? where id_pari=?");
			psUpdate.setLong(1, pari.getSubscriber().getId_subscribe());
			psUpdate.setLong(2, pari.getMise());
			if (((PariWinner) pari).getWinner() instanceof Individual){
				psUpdate.setLong(3, ((Individual) ((PariWinner) pari).getWinner()).getId_individual());
			}
			else{
				psUpdate.setLong(3, ((Team) ((PariWinner) pari).getWinner()).getId_team());
			}
			psUpdate.setString(4, "pariWinner");
			psUpdate.setInt(5, pari.getCompetition_id());
			psUpdate.executeUpdate();
			psUpdate.close();
			c.close();
		}
		
		else{
			PreparedStatement psUpdate = c
					.prepareStatement("update pari set id_joueur=?, mise=?, competiteurpremiereID=?, competiteurdeuxiemeID=?, competiteurtroisiemeID=?, type=?, id_competition=? where id_pari=?");
			psUpdate.setLong(1, pari.getSubscriber().getId_subscribe());
			psUpdate.setLong(2, pari.getMise());
			if (((PariPodium) pari).getWinner() instanceof Individual){
				psUpdate.setLong(3, ((Individual) ((PariPodium) pari).getWinner()).getId_individual());
				psUpdate.setLong(3, ((Individual) ((PariPodium) pari).getSecond()).getId_individual());
				psUpdate.setLong(3, ((Individual) ((PariPodium) pari).getThird()).getId_individual());
			}
			else{
				psUpdate.setLong(3, ((Team) ((PariPodium) pari).getWinner()).getId_team());
				psUpdate.setLong(3, ((Team) ((PariPodium) pari).getSecond()).getId_team());
				psUpdate.setLong(3, ((Team) ((PariPodium) pari).getThird()).getId_team());
			}
			psUpdate.setString(4, "pariPodium");
			psUpdate.setInt(5, pari.getCompetition_id());
			psUpdate.executeUpdate();
			psUpdate.close();
			c.close();
			
		}
	}

	// -----------------------------------------------------------------------------
	/**
	 * Delete from the database a specific bet.
	 * 
	 * @param bet
	 *            the bet to be deleted.
	 * @throws SQLException
	 */
	public static void delete(Pari pari) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from pari where id_pari=?");
		psUpdate.setInt(1, pari.getPari_id());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	// -----------------------------------------------------------------------------
}
