package fr.uv1.tests.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Pari;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.Team;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitionException;
import fr.uv1.utils.MyCalendar;

public class CompetitionTest {

	private Competition competition;
	private ArrayList<Competitor> competitors ;
	private Pari pari;
	private Subscriber sub;
	private Competitor competiteur ;

	@Before
	public void beforeTest() throws BadParametersException{
		 competitors = new ArrayList<Competitor>();
		 competitors.add(new Individual("Cisse","Mamadou", "28-09-1992"));
		 competitors.add(new Individual("Cisse","Sanounou", "05-01-1989"));
		 competitors.add(new Individual("Cisse","Pinda", "07-10-1990"));
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		
	}
	
	
	@Test
	public void testCompetition() throws BadParametersException, CompetitionException{
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);	
		assertTrue(competition.getNomCompetition().equalsIgnoreCase(new String("Tennis")));
		assertTrue(competition.getMontantTotalMise()==0);
		assertTrue(competition.getCompetitors().contains(new Individual("Cisse","Mamadou", "28-09-1992")));
		assertTrue(competition.getCompetitors().contains(new Individual("Cisse","Sanounou", "05-01-1989")));
		assertTrue(competition.getCompetitors().contains(new Individual("Cisse","Pinda", "07-10-1990")));
		assertTrue(competition.getCompetitors().size()==3);
	
	}
	
	@Test
	public void testAddCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitionException{
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);	
		competition.addCompetitor((new Individual("Thiam","Mariam", "07-10-1990")));
		assertTrue(competition.getCompetitors().contains(new Individual("Thiam","Mariam", "07-10-1990")));
		assertTrue(competition.getCompetitors().size()==4);
	}
	
	@Test(expected =  BadParametersException.class)
	public void testAddNullCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitionException{	
		competition.addCompetitor(null);
	}
	
	@Test(expected =   ExistingCompetitionException.class)
	public void testAddExistingCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitionException{
		competition.addCompetitor((new Individual("Thiam","Mariam", "07-10-1990")));
		assertTrue(competition.getCompetitors().size()==4);
	}
	@Test(expected = BadParametersException.class)
	public void testAddBadCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitionException{
		competition.addCompetitor((new Individual("Thiam","Mariam", "07-10-200")));
	}
	@Test(expected = CompetitionException.class)
	public void testAddTeamCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitionException{
		Team t =new Team("barcelon");
		competition.addCompetitor(t);
	}
	
	@Test
	public void testParierSurLeVainqueur() throws BadParametersException, CompetitionException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		competitor(or)
		pari = new PariWinner(100,sub,  );
		competition.parierSurLeVainqueur(pari);
		assertTrue(competition.getMontantTotalMise()==100);
		pari = new Pari(150, sub );
		competition.parierSurLeVainqueur(pari);
		assertTrue(competition.getMontantTotalMise()==250);
	}
	
	@Test(expected = BadParametersException.class)
	public void testAddNullparierSurLeVainqueur() throws BadParametersException, CompetitionException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		competition.parierSurLeVainqueur(null);
		
	}
	
	@Test(expected = BadParametersException.class)
	public void testAddZeroMisePari() throws BadParametersException, CompetitionException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		pari = new PariWinner(0, sub );
		competition.parierSurLeVainqueur(pari);
		
	}
	@Test(expected = BadParametersException.class)
	public void testAddBadMisePari() throws BadParametersException, CompetitionException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		pari = new Pari(-10, sub );
		competition.parierSurLeVainqueur(pari);
		
	}
	
	@Test(expected = CompetitionException.class)
	public void testAddCompetitorPari() throws BadParametersException, CompetitionException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		sub= new Subscriber("Cisse","Mamadou", "28-09-1992","mcisse");
		pari = new Pari(-10, sub );
		competition.parierSurLeVainqueur(pari);
		
	}
	@Test(expected = BadParametersException.class)
	public void testAddPariByNullSubscriber() throws BadParametersException, CompetitionException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		pariWinner  = new Pari(-10, null,);
		competition.parierSurLeVainqueur(pari);
		
	}
	
	
}
