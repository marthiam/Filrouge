package fr.uv1.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Pari;
import fr.uv1.bettingServices.PariPodium;
import fr.uv1.bettingServices.PariWinner;
import fr.uv1.bettingServices.Team;
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
					.prepareStatement("insert into competition(nomcompetion," +
							"closingdate, montanttotalmise)" +
							" values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			psPersist.setString(1, competition.getNomCompetition());
			Date date = Date.valueOf(competition.getClosingdate());
			System.out.println("la date after valueof "+ date) ;  
			psPersist.setDate(2, date);
			psPersist.setLong(3, competition.getMontantTotalMise());
			
			psPersist.executeUpdate();

			
			// Retrieving the value of the id with a request on the
			// sequence (competition_id_seq).
			PreparedStatement psIdValue = c
					.prepareStatement("select currval('competition_id_seq') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer id = null;
			while (resultSet.next()) {
				id = resultSet.getInt("value_id");
			}
			psPersist.close();
			resultSet.close();
			psIdValue.close();
			c.commit();
			competition.setId_competition(id);
			
			for (Competitor competiteur : competition.getCompetitors()){
				try {
					if (competiteur instanceof Individual){
						if (CompetitorsManager.findById(((Individual) competiteur).getId_individual()) == null){
							CompetitorsManager.persist(competiteur);
						}
						PreparedStatement psPersistIndividual = c
								.prepareStatement("insert into estcompetiteurde(competiteur_id, competition_id)" +
										" values (?, ?)");
						psPersistIndividual.setLong(1, ((Individual) competiteur).getId_individual());
						psPersistIndividual.setInt(2, competition.getId_competition());
						psPersistIndividual.executeUpdate();
						psPersistIndividual.close();
					}
					if (competiteur instanceof Team){
						if (CompetitorsManager.findById(((Team) competiteur).getId_team()) == null){
							CompetitorsManager.persist(competiteur);
						}
						PreparedStatement psPersistTeam = c
								.prepareStatement("insert into estcompetiteurde(competiteur_id, competition_id" +
										"values (?, ?)");
						psPersistTeam.setLong(1, ((Team) competiteur).getId_team());
						psPersistTeam.setInt(2, competition.getId_competition());
						psPersistTeam.executeUpdate();
						psPersistTeam.close();
					}
				
				} catch (BadParametersException e) {
					
					e.printStackTrace();
				}
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

		c.setAutoCommit(true);
		c.close();
		
		
		return competition;
		
	}
	
	public static Competition findById(Integer id) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from competition where id_competition=?");
		psSelect.setInt(1, id);
		ResultSet resultSet = psSelect.executeQuery();
		
		
		Competition competition = null;
		

		while (resultSet.next()) {
			String nomCompetition = resultSet.getString("nomcompetion");
			Long montantTotalMise = resultSet.getLong("montanttotalmise");
			
			String closingDate = resultSet.getString("closingdate");
			
			String[] date = closingDate.split("-");

			MyCalendar dateCompetition = new MyCalendar(new Integer(date[0]),
					new Integer(date[1]), new Integer(date[2]));
			
			ArrayList<Competitor> competitors = new ArrayList<Competitor>();
			
			PreparedStatement psSelect2 = c
					.prepareStatement("select * from estcompetiteurde where competition_id=?");
			psSelect2.setInt(1, id);
			ResultSet resultSet2 = psSelect2.executeQuery();
			
			while (resultSet2.next()){
				try {
					Competitor competiteur = CompetitorsManager.findById(resultSet2.getLong("competiteur_id"));
					competitors.add(competiteur);
				} catch (BadParametersException e) {
					
					e.printStackTrace();
				}
			}
			
			ArrayList<Pari> betList = new ArrayList<Pari>();
			Pari pari;
			PreparedStatement psSelect3 = c
					.prepareStatement("select * from pari where id_competition=?");
			psSelect3.setInt(1, id);
			ResultSet resultSet3 = psSelect3.executeQuery();
			while (resultSet2.next()){
				pari = PariManager.findById(resultSet3.getInt("id_pari"));
				betList.add(pari);
			}
			
			try {
				competition = new Competition(nomCompetition, dateCompetition, competitors);
				competition.setMontantTotalMise(montantTotalMise);
				competition.setBetList(betList);
				competition.setId_competition(id);
			} catch (BadParametersException | CompetitionException e) {
				e.printStackTrace();
			}

		}
		resultSet.close();
		psSelect.close();
		c.close();
	
		return competition;
	}
	public static long findByName(Competition competition) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select id_competition from competition where nomcompetition=?");
		psSelect.setString(1, competition.getNomCompetition());
		ResultSet resultSet = psSelect.executeQuery();
		
		long id =0 ; 
		while (resultSet.next()) {
			 id = resultSet.getLong("id_competion");
			
		}
		
		resultSet.close();
		psSelect.close();
		c.close();
	
		return id;
	}
	
	public static List<Competition> findAll() throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from competition order by id_competition");
		ResultSet resultSet = psSelect.executeQuery();
		List<Competition> competitions = new ArrayList<Competition>();
		Competition competition;
		while (resultSet.next()) {
			competition = CompetitionManager.findById(resultSet.getInt("id_competition"));
			competitions.add(competition);
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return competitions;
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
	
	public static void update(Competition competition) throws SQLException {
		Connection c = DataBaseConnection.getConnection();
		
			PreparedStatement psUpdate = c
					.prepareStatement("update competition set nomcompetion=?, closingdate=?, montanttotalmise=? where id_competition=?");
			psUpdate.setString(1, competition.getNomCompetition());
			Date date = Date.valueOf(competition.getClosingdate());
			psUpdate.setDate(2, date);
			psUpdate.setLong(3, competition.getMontantTotalMise());
			psUpdate.setInt(4, competition.getId_competition());
			psUpdate.executeUpdate();
			psUpdate.close();
			c.close();

	}
	
	
	public static void main(String[] args) throws SQLException{
		
	}
}
