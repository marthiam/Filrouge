package fr.uv1.tests.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Pari;
import fr.uv1.bettingServices.PariWinner;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
import fr.uv1.utils.MyCalendar;

public class TestPariWinner {
	private PariWinner pari;
	private Competition competition;
	private ArrayList<Competitor> competitors ;
	private Subscriber sub;
	private Competitor winner;
	@Before
	public void beforeTest() throws BadParametersException, CompetitionException{
		 competitors = new ArrayList<Competitor>();
		 competitors.add(new Individual("Cisse","Mamadou", "28-09-1992"));
		 competitors.add(new Individual("Cisse","Sanounou", "05-01-1989"));
		 competitors.add(new Individual("Cisse","Pinda", "07-10-1990"));
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		 winner = new Individual("Cisse","Mamadou", "28-09-1992");
		 }
	
	@Test
	public void TestPariWinner() throws BadParametersException, SubscriberException{
		pari = new PariWinner(100, sub, winner);
		assertTrue(pari.getMise()==100);
		assertTrue(pari.getSubscriber().equals(sub));
		assertTrue(pari.getWinner().equals(winner));
	}
	
	@Test (expected = BadParametersException.class)
	public void testMontantInvalide() throws BadParametersException, SubscriberException{
		new PariWinner(-100, sub, winner);
	}
	
	@Test (expected = BadParametersException.class)
	public void testNullSubscriberPari() throws BadParametersException{
		new PariWinner(100, null, winner);
	}	
	

	@Test (expected = BadParametersException.class)
	public void testNullWinner() throws BadParametersException, SubscriberException{
		new PariWinner(100, sub, null);
	}
	
	@Test
	public void testGetWinner() throws BadParametersException, SubscriberException{
		pari = new PariWinner(100, sub, winner);
		assertTrue(pari.getWinner().equals(winner));
	}
	
	@Test
	public void testSetWinner() throws BadParametersException, SubscriberException{
		pari = new PariWinner(100, sub, winner);
		pari.setWinner(new Individual("Cisse","Sanounou", "05-01-1989"));
		assertFalse(pari.getWinner().equals(winner));
	}
	

	@Test(expected = BadParametersException.class)
	public void testSetNullWinner() throws BadParametersException {
		pari = new PariWinner(100, sub, winner);
		pari.setWinner(null);
	}

	
}
