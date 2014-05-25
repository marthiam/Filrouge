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
					.prepareStatement("insert into competition(id_competition, nomcompetion," +
							"closingdate, montanttotalmise)" +
							"values (?, ?, ?, ?)");
			
			psPersist.setInt(1, competition.getId_competition());
			psPersist.setString(2, competition.getNomCompetition());
			Date date = Date.valueOf(competition.getClosingdate());
			psPersist.setDate(3, date);
			psPersist.setLong(4, competition.getMontantTotalMise());

			for (Competitor competiteur : competition.getCompetitors()){
				try {
					if (competiteur instanceof Individual){
						if (CompetitorsManager.findById(((Individual) competiteur).getId_individual()) == null){
							CompetitorsManager.persist(competiteur);
						}
					}
					if (competiteur instanceof Team){
						if (CompetitorsManager.findById(((Team) competiteur).getId_team()) == null){
							CompetitorsManager.persist(competiteur);
						}
						PreparedStatement psPersistTeam = c
								.prepareStatement("insert into estcompetiteurde(competiteurID, competitionID" +
										"values (?, ?)");
						psPersistTeam.setLong(1, ((Team) competiteur).getId_team());
						psPersistTeam.setInt(2, competition.getId_competition());
						psPersistTeam.executeUpdate();
						psPersistTeam.close();
					}
				
				} catch (BadParametersException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
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
				.prepareStatement("select * from competition where id_competition=?");
		psSelect.setInt(1, id);
		ResultSet resultSet = psSelect.executeQuery();
		
		
		Competition competition = null;
		

		while (resultSet.next()) {
			String nomCompetition = resultSet.getString("nomcompetion");
			Long montantTotalMise = resultSet.getLong("montanttotalmise");
			Date closingDate = resultSet.getDate("closingdate");
			MyCalendar dateCompetition = new MyCalendar(closingDate.getYear(),
					closingDate.getMonth(), closingDate.getDate());
			
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
					// TODO Auto-generated catch block
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
			} catch (BadParametersException | CompetitionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		resultSet.close();
		psSelect.close();
		c.close();
	
		return competition;
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
	
	
	
	
	public static void main(String[] args) throws SQLException{
		
		try {
			ArrayList<Competitor> competitors = new ArrayList<Competitor>();
			Competitor c1 = new Individual("Cisse","Mamadou", "28-09-1992");
			Competitor c2 = new Individual("Cisse","Sanounou", "05-01-1989");
			((Individual) c1).setId_individual(1);
			((Individual) c2).setId_individual(2);
			competitors.add(c1);
			competitors.add(c2);
			Competition competition = new Competition("Course", new MyCalendar(2014,12,1) , competitors);
			competition.setId_competition(1);
			persist(competition);
		} catch (BadParametersException | CompetitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
