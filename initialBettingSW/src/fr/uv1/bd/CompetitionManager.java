package fr.uv1.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Pari;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.utils.DataBaseConnection;
import fr.uv1.utils.MyCalendar;

public class CompetitionManager {
	
	public static Competition persist(Competition competition) throws SQLException{
		
		Connection c = DataBaseConnection.getConnection();
		try {
			c.setAutoCommit(false);
				
			
			PreparedStatement psPersist = c
					.prepareStatement("insert into competition(id_competition, nomcompetion," +
							"closingdate, montanttotalmise)" +
							"values (?, ?, ?, ?)");
			
			
			psPersist.setInt(1, competition.getId_competition());
			psPersist.setString(2, competition.getNomCompetition());
			Date date = Date.valueOf(competition.getClosingdate());
			psPersist.setDate(3, date);
			psPersist.setLong(4, competition.getMontantTotalMise());
			
			psPersist.executeUpdate();

			psPersist.close();

			// Retrieving the value of the id with a request on the
			// sequence (competition_id_seq).
			PreparedStatement psIdValue = c
					.prepareStatement("select currval('competition_id_seq') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer id = null;
			while (resultSet.next()) {
				id = resultSet.getInt("value_id");
			}
			resultSet.close();
			psIdValue.close();
			c.commit();
			competition.setId_competition(id);
		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			c.setAutoCommit(true);
			throw e;
		}

		c.setAutoCommit(true);
		c.close();
		
		
		return competition;
		
	}
	
	public static Competition findById(Integer id) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from competition where id=?");
		psSelect.setInt(1, id);
		ResultSet resultSet = psSelect.executeQuery();
		
		
		Competition competition = null;
		
		String nomCompetition = resultSet.getString("nomcompetion");
		Long montantTotalMise = resultSet.getLong("montanttotalmise");
		Date closingDate = resultSet.getDate("closingdate");
		MyCalendar dateCompetition = new MyCalendar(closingDate.getYear(),
				closingDate.getMonth(), closingDate.getDate());
		
		/*
		
		
		while (resultSet.next()) {
			competition = new Competition(resultSet.getInt("id"),
					resultSet.getInt("number_of_tokens"),
					resultSet.getInt("id_subscriber"));
		}
		resultSet.close();
		psSelect.close();
		c.close();
	*/
		return null;
	}
	
	
	
	public static void delete(Competition competition) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from competition where id_competition=?");
		psUpdate.setInt(1, competition.getId_competition());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	
	
	
	
	public static void main(String[] args) throws SQLException{
		
		try {
			ArrayList<Competitor> competitors = new ArrayList<Competitor>();;
			competitors.add(new Individual("Cisse","Mamadou", "28-09-1992"));
			competitors.add(new Individual("Cisse","Sanounou", "05-01-1989"));
			competitors.add(new Individual("Thiam","Pierre", "07-10-1991"));
			competitors.add(new Individual("Traore","Sami", "05-01-1983"));
			competitors.add(new Individual("Cisse","Pinda", "07-10-1990"));
			Competition competition = new Competition("Course", new MyCalendar(2014,12,1) , competitors);
			competition.setId_competition(1);
			delete(competition);
		} catch (BadParametersException | CompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
