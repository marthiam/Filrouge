package fr.uv1.tests.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.PariPodium;
import fr.uv1.bettingServices.PariWinner;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.utils.MyCalendar;

public class TestPariPodium {
	private PariPodium pari;
	private Competition competition;
	private ArrayList<Competitor> competitors ;
	private Subscriber sub;
	private Competitor winner;
	private Competitor second;
	private Competitor third;
	
	@Before
	public void beforeTest() throws BadParametersException, CompetitionException{
		 competitors = new ArrayList<Competitor>();
		 competitors.add(new Individual("Cisse","Mamadou", "28-09-1992"));
		 competitors.add(new Individual("Cisse","Sanounou", "05-01-1989"));
		 competitors.add(new Individual("Cisse","Pinda", "07-10-1990"));
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		 winner = new Individual("Cisse","Mamadou", "28-09-1992");
		 second = new Individual("Cisse","Sanounou", "05-01-1989");
		 third = new Individual("Cisse","Pinda", "07-10-1990");
		 }
	
	@Test
	public void TestPariPodium() throws BadParametersException{
		pari = new PariPodium(100, sub, competition, winner, second, third);
		assertTrue(pari.getMise()==100);
		assertTrue(pari.getSubscriber().equals(sub));
		assertTrue(pari.getCompetition().equals(competition));
		assertTrue(pari.getWinner().equals(winner));
		assertTrue(pari.getSecond().equals(second));
		assertTrue(pari.getThird().equals(third));
	}
	
	@Test (expected = BadParametersException.class)
	public void testMontantInvalide() throws BadParametersException{
		new PariPodium(-100, sub, competition, winner, second, third);
	}
	
	@Test (expected = BadParametersException.class)
	public void testNullSubscriberPari() throws BadParametersException{
		new PariPodium(100, null, competition, winner, second, third);
	}	
	
	@Test (expected = BadParametersException.class)
	public void testNullSubscriber() throws BadParametersException{
		new PariPodium(100, sub, null, winner, second, third);
	}
	
	@Test (expected = BadParametersException.class)
	public void testNullWinner() throws BadParametersException{
		new PariPodium(100, sub, competition, null, second, third);
	}
	
	@Test (expected = BadParametersException.class)
	public void testNullSecond() throws BadParametersException{
		new PariPodium(100, sub, competition, winner, null, third);
	}
	
	@Test (expected = BadParametersException.class)
	public void testNullThird() throws BadParametersException{
		new PariPodium(100, sub, competition, winner, second, null);
	}
	
	@Test
	public void testGetWinner() throws BadParametersException{
		pari = new PariPodium(100, sub, competition, winner, second, third);
		assertTrue(pari.getWinner().equals(winner));
	}
	
	@Test
	public void testGetSecond() throws BadParametersException{
		pari = new PariPodium(100, sub, competition, winner, second, third);
		assertTrue(pari.getSecond().equals(second));
	}
	
	@Test
	public void testGetThird() throws BadParametersException{
		pari = new PariPodium(100, sub, competition, winner, second, third);
		assertTrue(pari.getThird().equals(third));
	}
	
	@Test
	public void testSetWinner() throws BadParametersException{
		pari = new PariPodium(100, sub, competition, winner, second, third);
		pari.setWinner(new Individual("Cisse","Pinda", "07-10-1990"));
		assertFalse(pari.getWinner().equals(winner));
	}
	
	@Test
	public void testSetSecond() throws BadParametersException{
		pari = new PariPodium(100, sub, competition, winner, second, third);
		pari.setSecond(new Individual("Cisse","Mamadou", "28-09-1992"));
		assertFalse(pari.getSecond().equals(second));
	}
	
	@Test
	public void testSetThird() throws BadParametersException{
		pari = new PariPodium(100, sub, competition, winner, second, third);
		pari.setThird(new Individual("Cisse","Sanounou", "05-01-1989"));
		assertFalse(pari.getThird().equals(third));
	}
	
	@Test(expected = BadParametersException.class)
	public void testSetNullWinner() throws BadParametersException {
		pari = new PariPodium(100, sub, competition, winner, second, third);
		pari.setWinner(null);
	}
	
	@Test(expected = BadParametersException.class)
	public void testSetNullSecond() throws BadParametersException {
		pari = new PariPodium(100, sub, competition, winner, second, third);
		pari.setSecond(null);
	}
	
	@Test(expected = BadParametersException.class)
	public void testSetNullThird() throws BadParametersException {
		pari = new PariPodium(100, sub, competition, winner, second, third);
		pari.setThird(null);
	}
	
}
