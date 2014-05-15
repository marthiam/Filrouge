package fr.uv1.tests.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.utils.MyCalendar;

public class CompetitionTest {

	private Competition competition;
	

	@Test
	public void testCompetition() throws BadParametersException, CompetitionException{
		ArrayList<Competitor> competitors = new ArrayList<Competitor>();		
		competitors.add(new Individual("Cisse","Mamadou", "28-09-1992"));
		competitors.add(new Individual("Cisse","Sanounou", "05-01-1989"));
		competitors.add(new Individual("Cisse","Pinda", "07-10-1990"));
		
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), 0, competitors);
		
		assertTrue(competition.getNomCompetition().equalsIgnoreCase(new String("Tennis")));
		assertTrue(competition.getMontantTotalMise()==0);
		assertTrue(competition.getCompetitors().contains(new Individual("Cisse","Mamadou", "28-09-1992")));
		

	}
	
}
