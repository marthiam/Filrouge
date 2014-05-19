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
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
import fr.uv1.utils.MyCalendar;

public class PariTest {
	private Pari pari;
	private Competition competition;
	private ArrayList<Competitor> competitors ;
	private Subscriber sub;

	@Before
	public void beforeTest() throws BadParametersException, CompetitionException{
		 competitors = new ArrayList<Competitor>();
		 competitors.add(new Individual("Cisse","Mamadou", "28-09-1992"));
		 competitors.add(new Individual("Cisse","Sanounou", "05-01-1989"));
		 competitors.add(new Individual("Cisse","Pinda", "07-10-1990"));
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		 }
	
	@Test
<<<<<<< HEAD
	public void TestPari() throws BadParametersException{
		pari = new Pari(100, sub);
		assertTrue(pari.getMise()==100);
		assertTrue(pari.getSubscriber().equals(sub));
	}
	
	@Test (expected = BadParametersException.class)
	public void testMontantInvalide() throws BadParametersException{
=======
	public void TestPari() throws BadParametersException, SubscriberException{
		pari = new Pari(100, sub);
		assertTrue(pari.getMise()==100);
		assertTrue(pari.getSubscriber().equals(sub));
		
	}
	
	@Test (expected = BadParametersException.class)
	public void testMontantInvalide() throws BadParametersException, SubscriberException{
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
		new Pari(-100, sub);
	}
	
	@Test (expected = BadParametersException.class)
<<<<<<< HEAD
	public void testNullSubscriberPari() throws BadParametersException{
=======
	public void testNullSubscriberPari() throws BadParametersException, SubscriberException{
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
		new Pari(100, null);
	}	
	
	
	@Test
<<<<<<< HEAD
	public void testGetMise() throws BadParametersException{
=======
	public void testGetMise() throws BadParametersException, SubscriberException{
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
		pari = new Pari(100, sub);
		assertTrue(pari.getMise()==100);
	}
	
	@Test
<<<<<<< HEAD
	public void testGetSubscriber() throws BadParametersException{
=======
	public void testGetSubscriber() throws BadParametersException, SubscriberException{
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
		pari = new Pari(100, sub);
		assertTrue(pari.getSubscriber().equals(sub));
	}
	
<<<<<<< HEAD
	
	@Test
	public void testSetMise() throws BadParametersException{
=======
	
	
	@Test
	public void testSetMise() throws BadParametersException, SubscriberException{
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
		pari = new Pari(100, sub);
		pari.setMise(200);
		assertFalse(pari.getMise()==100);
	}
	
	@Test
<<<<<<< HEAD
	public void testSetSubscriber() throws BadParametersException{
=======
	public void testSetSubscriber() throws BadParametersException, SubscriberException{
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
		pari = new Pari(100, sub);
		pari.setSubscriber(new Subscriber("Cisse","Nene", "26-03-1996", "ncisse" ));
		assertFalse(pari.getSubscriber().equals(sub));
	}
	
<<<<<<< HEAD
	@Test(expected = BadParametersException.class)
	public void testSetMontantInvalide() throws BadParametersException {
=======
	@Test
	public void testSetCompetition() throws BadParametersException, CompetitionException, SubscriberException{
		pari = new Pari(100, sub);
		ArrayList<Competitor> newcompetitors = new ArrayList<Competitor>();
		newcompetitors.add(new Individual("Cisse","Tenin", "28-09-1987"));
		newcompetitors.add(new Individual("Cisse","Aminata", "05-01-1991"));
		newcompetitors.add(new Individual("Cisse","Bintou", "07-10-1993"));
	}
		
	
	@Test(expected = BadParametersException.class)
	public void testSetMontantInvalide() throws BadParametersException, SubscriberException {
>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
		pari = new Pari(100, sub);
		pari.setMise(-100);
	}
	
	@Test(expected = BadParametersException.class)
<<<<<<< HEAD
	public void testSetNullSubscriber() throws BadParametersException {
		pari = new Pari(100, sub);
		pari.setSubscriber(null);
	}

}
	
=======
	public void testSetNullSubscriber() throws BadParametersException, SubscriberException {
		pari = new Pari(100, sub);
		pari.setSubscriber(null);
	}
	
}

>>>>>>> bff4f9f65972eaf9d74b91a5993557f944945eb6
