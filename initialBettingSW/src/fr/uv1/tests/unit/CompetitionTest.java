package fr.uv1.tests.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Pari;
import fr.uv1.bettingServices.PariPodium;
import fr.uv1.bettingServices.PariWinner;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.Team;
import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.CompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;
import fr.uv1.bettingServices.exceptions.SubscriberException;
import fr.uv1.utils.MyCalendar;

public class CompetitionTest {

	private Competition competition;
	private ArrayList<Competitor> competitors ;
	private PariWinner pariwin;
	private PariPodium paripod;
	private Subscriber sub;
	private Competitor competiteur ;

	@Before
	public void beforeTest() throws BadParametersException{
		 competitors = new ArrayList<Competitor>();
		 competitors.add(new Individual("Cisse","Mamadou", "28-09-1992"));
		 competitors.add(new Individual("Cisse","Sanounou", "05-01-1989"));
		 competitors.add(new Individual("Thiam","Pierre", "07-10-1991"));
		 competitors.add(new Individual("Traore","Sami", "05-01-1983"));
		 competitors.add(new Individual("Cisse","Pinda", "07-10-1990"));
		 competiteur=new Individual("Cisse","Pinda", "07-10-1990");
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		
	}
	
	
	@Test
	public void testCompetition() throws BadParametersException, CompetitionException{
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);	
		assertTrue(competition.getNomCompetition().equalsIgnoreCase(new String("Tennis")));
		assertTrue(competition.getMontantTotalMise()==0);
		assertTrue(competition.getCompetitors().contains(new Individual("Cisse","Mamadou", "28-09-1992")));
		assertTrue(competition.getCompetitors().contains(new Individual("Cisse","Sanounou", "05-01-1989")));
		assertTrue(competition.getCompetitors().contains(new Individual("Cisse","Pinda", "07-10-1990")));
		assertTrue(competition.getCompetitors().size()==5);
	
	}
	
	@Test
	public void testAddCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitorException{
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);	
		competition.addCompetitor((new Individual("Thiam","Mariam", "07-10-1990")));
		assertTrue(competition.getCompetitors().contains(new Individual("Thiam","Mariam", "07-10-1990")));
		assertTrue(competition.getCompetitors().size()==6);
	}
	
	@Test(expected =  BadParametersException.class)
	public void testAddNullCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitorException{	
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);	
		competition.addCompetitor(null);
	}
	
	@Test(expected = ExistingCompetitorException.class)
	public void testAddExistingCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitorException, ExistingCompetitionException{
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);	
		int oldsize =competition.getCompetitors().size();
		competition.addCompetitor(new Individual("Cisse","Pinda", "07-10-1990"));
	}
	@Test(expected = BadParametersException.class)
	public void testAddBadCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitorException{
		competition.addCompetitor((new Individual("Thiam","Mariam", "07-10-200")));
	}
	@Test(expected = CompetitionException.class)
	public void testAddTeamCompetitor() throws BadParametersException, CompetitionException, ExistingCompetitorException{
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		Team t =new Team("barcelon");
		competition.addCompetitor(t);
	}
	
	@Test
	public void testParierSurLeVainqueur() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);

		competiteur=new Individual("Cisse","Sanounou", "05-01-1989");
		pariwin = new PariWinner(100,sub,competiteur);
		competition.parierSurLeVainqueur(pariwin);
		assertTrue(competition.getMontantTotalMise()==100);
		pariwin =new PariWinner(150,sub,competiteur);
		competition.parierSurLeVainqueur(pariwin);
		assertTrue(competition.getMontantTotalMise()==250);
	}
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLeVainqueurNullparierSurLeVainqueur() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		competition.parierSurLeVainqueur(null);
		
	}
	
	@Test(expected = BadParametersException.class)
	public void testparierSurLeVainqueurZeroMise() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		competiteur=new Individual("Cisse","Sanounou", "05-01-1989");
		pariwin =new PariWinner(0,sub,competiteur);
		competition.parierSurLeVainqueur(pariwin);
		
	}
	@Test(expected = BadParametersException.class)
	public void testParierSurLeVainqueurBadMise() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		pariwin =new PariWinner(-100,sub,competiteur);
		competition.parierSurLeVainqueur(pariwin);
		
	}

@Test(expected = SubscriberException.class)
public void testParierSurLeVainqueurBadMise1() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException {
	competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
	sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
	sub.crediter(300);
	pariwin =new PariWinner(400,sub,competiteur);
	competition.parierSurLeVainqueur(pariwin);
	
}
	
	@Test(expected = CompetitionException.class)
	public void testParierSurLeVainqueurByCompetitor() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		sub= new Subscriber("Cisse","Sanounou", "05-01-1989","scisse");
		sub.crediter(300);
		pariwin =new PariWinner(100,sub,competiteur);
		competition.parierSurLeVainqueur(pariwin);
		
	}
	@Test(expected = BadParametersException.class)
	public void testParierSurLeVainqueurByNullSubscriber() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		pariwin  = new PariWinner(300,null,competiteur);
		competition.parierSurLeVainqueur(pariwin);
		
	}
	@Test(expected = BadParametersException.class)
	public void testParierSurLeVainqueurOfPassCompetition() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException {
		competition = new Competition(new String("Tennis"),new MyCalendar(2014,4,1), competitors);
		sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		pariwin  = new PariWinner(300,sub,competiteur);
		competition.parierSurLeVainqueur(pariwin);
		
	}
	
	@Test
	public void testAddTeam() throws BadParametersException, CompetitionException, ExistingCompetitorException{
		 competitors = new ArrayList<Competitor>();
		 Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		 competition.addCompetitor(t3);
		 assertTrue(competition.getCompetitors().size()==3);
		 assertTrue(competition.getCompetitors().contains(t1));
		 assertTrue(competition.getCompetitors().contains(t2));
		 assertTrue(competition.getCompetitors().contains(t3));
	}
	
	
	@Test(expected=ExistingCompetitorException.class)
	public void testAddExistingTeam() throws BadParametersException, CompetitionException, ExistingCompetitorException{
		 competitors = new ArrayList<Competitor>();
		 Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		 competition.addCompetitor(t2);
		 
	}
	
	
	
	@Test(expected=BadParametersException.class)
	public void testAddNullTeam() throws BadParametersException, CompetitionException, ExistingCompetitorException{
		 competitors = new ArrayList<Competitor>();
		 Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competition = new Competition(new String("Tennis"),new MyCalendar(2014,12,1), competitors);
		 competition.addCompetitor(null);
		 
	}
	@Test(expected=BadParametersException.class)
	public void testAddTeamToPassCompetition() throws BadParametersException, CompetitionException, ExistingCompetitorException{
		 competitors = new ArrayList<Competitor>();
		 Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competition = new Competition(new String("Tennis"),new MyCalendar(2014,3,1), competitors);
		 competition.addCompetitor(t3);
		 
	}
	@Test
	public void testParierSurLePodium() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		 competitors = new ArrayList<Competitor>();
		 Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 paripod =new PariPodium(100,sub,t2,t1,t4);
		 competition.parierSurLePodium(paripod);
		 assertTrue(competition.getMontantTotalMise()==100);
		 assertTrue(sub.solde()==200);
		 paripod =new PariPodium(150,sub,t2,t1,t4);
		 competition.parierSurLePodium(paripod);
		 assertTrue(competition.getMontantTotalMise()==250);
		 assertTrue(competition.getCompetitors().size()==4);
		 assertTrue(sub.solde()==50);
	}
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLePodiumNull() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		competition.parierSurLePodium(null);
		
	}
	
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLePodiumZeroMise() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 paripod =new PariPodium(0,sub,t2,t1,t4);
		 competition.parierSurLePodium(paripod);
		
	}
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLePodiumBadMise() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 paripod =new PariPodium(-100,sub,t2,t1,t4);
		 competition.parierSurLePodium(paripod);
		
	}
	@Test(expected = SubscriberException.class)
	public void testParierSurLePodiumBadMise1() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		 Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 paripod =new PariPodium(400,sub,t2,t1,t4);
		 competition.parierSurLePodium(paripod);
		
	}
		
		@Test(expected = CompetitionException.class)
		public void testParierSurLePodiumByCompetitor() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
			competitors = new ArrayList<Competitor>();
			 Team t1 =new Team("Telecom");
			 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
			 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
			 Team t2 =new Team("Ensta");
			 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
			 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
			 Team t3 =new Team("Enib");
			 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
			 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
			 Team t4 =new Team("Esmisab");
			 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
			 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
			 competitors.add(t1);
			 competitors.add(t2);
			 competitors.add(t3);
			 competitors.add(t4);
			 sub = new Subscriber("Diarra","Doudou", "07-10-1990", "ddoudou" );
			 sub.crediter(300);
			 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
			 paripod =new PariPodium(120,sub,t2,t1,t4);
			 competition.parierSurLePodium(paripod);
			
		}
		
		@Test(expected = BadParametersException.class)
		public void testParierSurLePodiumByNullSubscriber() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
			competitors = new ArrayList<Competitor>();
			 Team t1 =new Team("Telecom");
			 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
			 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
			 Team t2 =new Team("Ensta");
			 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
			 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
			 Team t3 =new Team("Enib");
			 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
			 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
			 Team t4 =new Team("Esmisab");
			 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
			 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
			 competitors.add(t1);
			 competitors.add(t2);
			 competitors.add(t3);
			 competitors.add(t4);
			 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
			 paripod =new PariPodium(120,null,t2,t1,t4);
			 competition.parierSurLePodium(paripod);
		}
		@Test(expected = BadParametersException.class)
		public void testParierSurLePodiumOfPassCompetition() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
			competitors = new ArrayList<Competitor>();
			 Team t1 =new Team("Telecom");
			 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
			 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
			 Team t2 =new Team("Ensta");
			 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
			 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
			 Team t3 =new Team("Enib");
			 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
			 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
			 Team t4 =new Team("Esmisab");
			 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
			 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
			 competitors.add(t1);
			 competitors.add(t2);
			 competitors.add(t3);
			 competitors.add(t4);
			 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
			 sub.crediter(300);
			 competition = new Competition(new String("basket"),new MyCalendar(2014,3,1), competitors);
			 paripod =new PariPodium(100,sub,t2,t1,t4);
			 competition.parierSurLePodium(paripod);
			
		}
		
	@Test
	public void testParierSurLePodiumIndiv() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		 competitors = new ArrayList<Competitor>();
		 Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
		 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
		 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
		 Competitor c4=new Individual("Thiam","Sami", "05-01-1983");
		 Competitor c5=new Individual("Toure","Pinda", "07-10-1990");
		 Competitor c6=new Individual("Toure","Kadi", "02-11-1991");
		 competitors.add(c1);
		 competitors.add(c2);
		 competitors.add(c3);
		 competitors.add(c4);
		 competitors.add(c5);
		 competitors.add(c6);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 paripod = new PariPodium(100,sub,c2,c1,c4);
		 competition.parierSurLePodium(paripod);
		 assertTrue(competition.getMontantTotalMise()==100);
		 assertTrue(sub.solde()==200);
		 paripod =new PariPodium(150,sub,c2,c1,c4);
		 competition.parierSurLePodium(paripod);
		 assertTrue(competition.getMontantTotalMise()==250);
		 assertTrue(competition.getCompetitors().size()==6);
		 assertTrue(sub.solde()==50);
	}
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLePodiumNullIndiv() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		 Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
		 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
		 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
		 Competitor c4=new Individual("Thiam","Sami", "05-01-1983");
		 Competitor c5=new Individual("Toure","Pinda", "07-10-1990");
		 Competitor c6=new Individual("Toure","Kadi", "02-11-1991");
		 competitors.add(c1);
		 competitors.add(c2);
		 competitors.add(c3);
		 competitors.add(c4);
		 competitors.add(c5);
		 competitors.add(c6);
		competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		competition.parierSurLePodium(null);
		
	}
	
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLePodiumZeroMiseIndiv() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
		 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
		 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
		 Competitor c4=new Individual("Thiam","Sami", "05-01-1983");
		 Competitor c5=new Individual("Toure","Pinda", "07-10-1990");
		 Competitor c6=new Individual("Toure","Kadi", "02-11-1991");
		 competitors.add(c1);
		 competitors.add(c2);
		 competitors.add(c3);
		 competitors.add(c4);
		 competitors.add(c5);
		 competitors.add(c6);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 paripod =new PariPodium(0,sub,c2,c1,c4);
		 competition.parierSurLePodium(paripod);
		
	}
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLePodiumBadMiseInd() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
		 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
		 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
		 Competitor c4=new Individual("Thiam","Sami", "05-01-1983");
		 Competitor c5=new Individual("Toure","Pinda", "07-10-1990");
		 Competitor c6=new Individual("Toure","Kadi", "02-11-1991");
		 competitors.add(c1);
		 competitors.add(c2);
		 competitors.add(c3);
		 competitors.add(c4);
		 competitors.add(c5);
		 competitors.add(c6);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 paripod =new PariPodium(-100,sub,c2,c1,c4);
		 competition.parierSurLePodium(paripod);
		
	}
	
@Test(expected = SubscriberException.class)
public void testParierSurLePodiumBadMise1Indiv() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
	competitors = new ArrayList<Competitor>();
	competitors = new ArrayList<Competitor>();
	Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
	 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
	 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
	 Competitor c4=new Individual("Thiam","Sami", "05-01-1983");
	 Competitor c5=new Individual("Toure","Pinda", "07-10-1990");
	 Competitor c6=new Individual("Toure","Kadi", "02-11-1991");
	 competitors.add(c1);
	 competitors.add(c2);
	 competitors.add(c3);
	 competitors.add(c4);
	 competitors.add(c5);
	 competitors.add(c6);
	 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
	 sub.crediter(300);
	 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
	 paripod =new PariPodium(400,sub,c2,c1,c4);
	 competition.parierSurLePodium(paripod);
	
}
	
	@Test(expected = CompetitionException.class)
	public void testParierSurLePodiumByCompetitorIndiv() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
		 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
		 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
		 Competitor c4=new Individual("Thiam","Sami", "05-01-1983");
		 Competitor c5=new Individual("Toure","Pinda", "07-10-1990");
		 Competitor c6=new Individual("Diarra","Doudou", "07-10-1990");
		 competitors.add(c1);
		 competitors.add(c2);
		 competitors.add(c3);
		 competitors.add(c4);
		 competitors.add(c5);
		 competitors.add(c6);
		 sub = new Subscriber("Diarra","Doudou", "07-10-1990", "ddoudou" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 paripod =new PariPodium(120,sub,c2,c1,c4);
		 competition.parierSurLePodium(paripod);
		
	}
	
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLePodiumByNullSubscriberIndiv() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
		 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
		 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
		 Competitor c4=new Individual("Thiam","Sami", "05-01-1983");
		 Competitor c5=new Individual("Toure","Pinda", "07-10-1990");
		 Competitor c6=new Individual("Diarra","Doudou", "07-10-1990");
		 competitors.add(c1);
		 competitors.add(c2);
		 competitors.add(c3);
		 competitors.add(c4);
		 competitors.add(c5);
		 competitors.add(c6);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 paripod =new PariPodium(120,null,c2,c1,c4);
		 competition.parierSurLePodium(paripod);
	}
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLePodiumOfPassCompetitionIndiv() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
		 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
		 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
		 Competitor c4=new Individual("Thiam","Sami", "05-01-1983");
		 Competitor c5=new Individual("Toure","Pinda", "07-10-1990");
		 Competitor c6=new Individual("Diarra","Doudou", "07-10-1990");
		 competitors.add(c1);
		 competitors.add(c2);
		 competitors.add(c3);
		 competitors.add(c4);
		 competitors.add(c5);
		 competitors.add(c6);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,3,1), competitors);
		 paripod =new PariPodium(100,sub,c2,c1,c4);
		 competition.parierSurLePodium(paripod);
		
	}
	
	@Test
	public void testParierSurLeVainqueurTeam() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		 competitors = new ArrayList<Competitor>();
		 Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 pariwin =new PariWinner(100,sub,t2);
		 competition.parierSurLeVainqueur(pariwin);
		 assertTrue(competition.getMontantTotalMise()==100);
		 assertTrue(sub.solde()==200);
		 pariwin =new PariWinner(150,sub,t2);
		 competition.parierSurLeVainqueur(pariwin);
		 assertTrue(competition.getMontantTotalMise()==250);
		 assertTrue(competition.getCompetitors().size()==4);
		 assertTrue(sub.solde()==50);
	}
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLeVainqueurNullTeam() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 competition.parierSurLeVainqueur(null);
		
	}
	
	
	@Test(expected = BadParametersException.class)
	public void testParierSurLeVainqueurZeroMiseTeam() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 pariwin =new PariWinner(0,sub,t2);
		 competition.parierSurLeVainqueur(pariwin);
		
	}

	@Test(expected = BadParametersException.class)
	public void testParierSurLeVainqueurBadMiseTeam() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 pariwin =new PariWinner(-100,sub,t4);
		 competition.parierSurLeVainqueur(pariwin);
	}
	
	
	@Test(expected = SubscriberException.class)
	public void testParierSurLeVainqueurBadMise1Team() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
		competitors = new ArrayList<Competitor>();
		 Team t1 =new Team("Telecom");
		 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
		 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
		 Team t2 =new Team("Ensta");
		 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
		 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
		 Team t3 =new Team("Enib");
		 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
		 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
		 Team t4 =new Team("Esmisab");
		 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
		 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
		 competitors.add(t1);
		 competitors.add(t2);
		 competitors.add(t3);
		 competitors.add(t4);
		 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
		 sub.crediter(300);
		 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
		 pariwin =new PariWinner(400,sub,t4);
		 competition.parierSurLeVainqueur(pariwin);
		
	}
		
		@Test(expected = CompetitionException.class)
		public void testParierSurLeVainqueurByCompetitorTeam() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
			competitors = new ArrayList<Competitor>();
			 Team t1 =new Team("Telecom");
			 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
			 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
			 Team t2 =new Team("Ensta");
			 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
			 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
			 Team t3 =new Team("Enib");
			 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
			 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
			 Team t4 =new Team("Esmisab");
			 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
			 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
			 competitors.add(t1);
			 competitors.add(t2);
			 competitors.add(t3);
			 competitors.add(t4);
			 sub = new Subscriber("Diarra","Doudou", "07-10-1990", "ddoudou" );
			 sub.crediter(300);
			 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
			 pariwin =new PariWinner(120,sub,t4);
			 competition.parierSurLeVainqueur(pariwin);
			
		}
		
		@Test(expected = BadParametersException.class)
		public void testParierSurLeVainqueurByNullSubscriberTeam() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
			competitors = new ArrayList<Competitor>();
			 Team t1 =new Team("Telecom");
			 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
			 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
			 Team t2 =new Team("Ensta");
			 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
			 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
			 Team t3 =new Team("Enib");
			 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
			 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
			 Team t4 =new Team("Esmisab");
			 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
			 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
			 competitors.add(t1);
			 competitors.add(t2);
			 competitors.add(t3);
			 competitors.add(t4);
			 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
			 pariwin =new PariWinner(120,null,t4);
			 competition.parierSurLeVainqueur(pariwin);
		}
		
		@Test(expected = BadParametersException.class)
		public void testParierSurLeVainqueurOfPassCompetitionTeam() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
			competitors = new ArrayList<Competitor>();
			 Team t1 =new Team("Telecom");
			 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
			 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
			 Team t2 =new Team("Ensta");
			 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
			 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
			 Team t3 =new Team("Enib");
			 t3.addMember(new Individual("Toure","Pinda", "07-10-1990"));
			 t3.addMember(new Individual("Toure","Kadi", "02-11-1991"));
			 Team t4 =new Team("Esmisab");
			 t4.addMember(new Individual("Diarra","Doudou", "07-10-1990"));
			 t4.addMember(new Individual("Diarra","Oumou", "02-11-1991"));
			 competitors.add(t1);
			 competitors.add(t2);
			 competitors.add(t3);
			 competitors.add(t4);
			 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
			 sub.crediter(300);
			 competition = new Competition(new String("basket"),new MyCalendar(2014,3,1), competitors);
			 pariwin =new PariWinner(120,sub,t4);
			 competition.parierSurLeVainqueur(pariwin);
			
		}
		@Test(expected = CompetitionException.class)
		public void testParierSurLePodiumOfCompetionWith2CompetitorsTeam() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
			competitors = new ArrayList<Competitor>();
			 Team t1 =new Team("Telecom");
			 t1.addMember(new Individual("Cisse","Mamadou", "28-09-1992"));
			 t1.addMember(new Individual("Cisse","Sanounou", "05-01-1989"));
			 Team t2 =new Team("Ensta");
			 t2.addMember(new Individual("Thiam","Pierre", "07-10-1991"));
			 t2.addMember(new Individual("Thiam","Sami", "05-01-1983"));
			 Team t3 =new Team("Ensta");
			 t3.addMember(new Individual("Toure","Paul", "07-10-1991"));
			 t3.addMember(new Individual("Tour","Sali", "05-01-1983"));
			 competitors.add(t1);
			 competitors.add(t2);
			 sub = new Subscriber("Cisse","Kadi", "07-10-1990", "mcisse" );
			 sub.crediter(300);
			 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
			 paripod =new PariPodium(120,sub,t1,t2,t3);
			 competition.parierSurLePodium(paripod);
			
		}
		@Test(expected = CompetitionException.class)
		public void testParierSurLePodiumOfCompetionWith2CompetitorsIndiv() throws BadParametersException, CompetitionException, AuthenticationException, ExistingCompetitionException, SubscriberException, ExistingCompetitorException {
			competitors = new ArrayList<Competitor>();
			Competitor c1 =new Individual("Cisse","Mamadou", "28-09-1992");
			 Competitor c2= new Individual("Cisse","Sanounou", "05-01-1989");
			 Competitor c3=new Individual("Thiam","Pierre", "07-10-1991");
			 competitors.add(c1);
			 competitors.add(c2);
			 sub = new Subscriber("Diarra","Doudou", "07-10-1990", "ddoudou" );
			 sub.crediter(300);
			 competition = new Competition(new String("basket"),new MyCalendar(2014,12,1), competitors);
			 paripod =new PariPodium(120,sub,c1,c2,c3);
			 competition.parierSurLePodium(paripod);
			 
		}
		
		
	
	
	
}
