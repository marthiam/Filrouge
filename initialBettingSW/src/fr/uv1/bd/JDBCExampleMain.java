package fr.uv1.bd;

import java.sql.SQLException;
import java.util.List;


import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Team;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

/**
 * @author Philippe TANGUY
 */
public class JDBCExampleMain {
	// -----------------------------------------------------------------------------
	public JDBCExampleMain() throws SQLException, BadParametersException, ExistingCompetitorException {

		System.out.println("--- Start ---\n");

		System.out.println("All the competitors");
		displayAllCompetitiors();

		System.out.println("Competiteur #1: " + CompetitorsManager.findById(43));
		System.out.println("after");

		System.out.println("Creating a new subscriber");
		Competitor newCompetitor = new Individual(new String("Test"), new String("Maurice"),new String("13-8-1993"));
		newCompetitor = CompetitorsManager.persist(newCompetitor);
		System.out.println("newCompetitor 1 = " + newCompetitor);
		System.out.println();
		Competitor newCompetitor2 = new Individual(new String("Thiam"), new String("Mamadou"),new String("13-8-1993"));
		newCompetitor2 = CompetitorsManager.persist(newCompetitor2);
		System.out.println("newCompetitor 2 = " + newCompetitor2);
		System.out.println();
		Competitor newCompetitor1 = new Individual(new String("Toure"), new String("Mariam"),new String("13-8-1993"));
		newCompetitor = CompetitorsManager.persist(newCompetitor1);
		System.out.println("newCompetitor 3 = " + newCompetitor1);
		System.out.println();
		newCompetitor = new Team(new String("Barca"));
		newCompetitor.addMember(newCompetitor1);
		newCompetitor.addMember(new Individual(new String("Cisse"), new String("Mamadou"),new String("13-8-1993")));
		newCompetitor = CompetitorsManager.persist(newCompetitor);
		System.out.println("newCompetitor 4 = " + newCompetitor);
		System.out.println();

		System.out.println("All the subscribers after insertion");
		displayAllCompetitiors();

		System.out.println("Updating the new subscriber");
		((Team)newCompetitor).setTeamName("Real");
		CompetitorsManager.update(newCompetitor);
		((Individual)newCompetitor2).setFirstname("Mahama");
		((Individual)newCompetitor2).setFirstname("Thiamii");
		CompetitorsManager.update(newCompetitor2);
		((Team)newCompetitor).addMember(newCompetitor2);
		CompetitorsManager.update(newCompetitor);
		

		System.out.println("All the competitors after updating");
		displayAllCompetitiors();

		System.out.println("Deleting the new competitor");
		CompetitorsManager.delete(newCompetitor);

		System.out.println("All the competitors after delete");
		displayAllCompetitiors();
		
		System.out.println("--- End ---\n");
	}

	// -----------------------------------------------------------------------------
	public void displayAllCompetitiors() throws SQLException, BadParametersException {
		List<Competitor> competitors = CompetitorsManager.findAll();
		for (Competitor competitor : competitors) {
			System.out.println(competitor);
		}
		System.out.println();
	}


	// -----------------------------------------------------------------------------
	public static void main(String[] args) throws BadParametersException, ExistingCompetitorException {
		try {
			new JDBCExampleMain();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// -----------------------------------------------------------------------------
}
