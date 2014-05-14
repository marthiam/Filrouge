package fr.uv1.tests.unit;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Team;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.bettingServices.exceptions.ExistingCompetitorException;

public class TeamTest {
	private Team myTeam; 

	@Test
	public void testTeamSansMembre() throws BadParametersException{
		myTeam = new Team("Barcelon");
		assertTrue(myTeam.getTeamName().equals("Barcelon"));
		assertFalse(myTeam.getMembers().equals(null));	
	}
	
	@Test(expected = BadParametersException.class)
	public void testInvalidTeamSansMembre() throws BadParametersException{
		myTeam = new Team("");
	}
	
	@Test(expected = BadParametersException.class)
	public void testNullTeamSansMembre() throws BadParametersException{
		myTeam = new Team(null);
	}
	
	@Test
	public void testTeam() throws BadParametersException{
		Collection<Competitor> members =new HashSet<Competitor>();
		members.add(new Individual("Thiam","Maurice", "22-09-1993"));
		members.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		myTeam = new Team("Barcelon2",members);
		assertTrue(myTeam.getTeamName().equals("Barcelon2"));
		assertTrue(myTeam.getMembers().equals(members));
	}
	
	@Test(expected = BadParametersException.class)
	public void testInvalidMembersTeam() throws BadParametersException{
		myTeam = new Team("Barcelon",null);
	}
	
	@Test(expected = BadParametersException.class)
	public void testInvalidNameTeam() throws BadParametersException{
		Collection<Competitor> members =new HashSet<Competitor>();
		members.add(new Individual("Thiam","Maurice", "22-09-1992"));
		members.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		myTeam = new Team("",members);
	}
	
	@Test(expected = BadParametersException.class)
	public void testNullNameTeam() throws BadParametersException{
		Collection<Competitor> members =new HashSet<Competitor>();
		members.add(new Individual("Thiam","Maurice", "22-09-1992"));
		members.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		myTeam = new Team(null,members);
	}
	
	
	@Test
	public void testAddMember() throws BadParametersException, ExistingCompetitorException{
		Collection<Competitor> members =new HashSet<Competitor>();
		members.add(new Individual("Thiam","Maurice", "22-09-1992"));
		members.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		myTeam = new Team("Barcelon",members);
		myTeam.addMember(new Individual("Thiam","Fatou","11-03-1992"));
		assertTrue(myTeam.getMembers().size()==3);
		assertTrue(myTeam.getMembers().contains(new Individual("Thiam","Fatou","11-03-1992")));
		
	}
	@Test(expected = ExistingCompetitorException.class)
	public void testInvalidAddMember() throws BadParametersException, ExistingCompetitorException{
		Collection<Competitor> members =new HashSet<Competitor>();
		members.add(new Individual("Thiam","Maurice", "22-09-1992"));
		members.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		myTeam = new Team("Barcelon",members);
		myTeam.addMember(new Individual("Thiam","Maurice", "22-09-1992"));
		assertTrue(myTeam.getMembers().size()==2);
		
	}
	@Test
	public void testDeletMember() throws BadParametersException, ExistingCompetitorException{
		Collection<Competitor> members =new HashSet<Competitor>();
		members.add(new Individual("Thiam","Maurice", "22-09-1992"));
		members.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		myTeam = new Team("Barcelon",members);
		myTeam.deleteMember(new Individual("Ketevi","Mariam", "02-11-1992"));
		assertTrue(myTeam.getMembers().size()==1);
		assertFalse(myTeam.getMembers().contains(new Individual("Ketevi","Mariam", "02-11-1992")));
		
	}
	@Test(expected = ExistingCompetitorException.class)
	public void testInvalidRemoveMember() throws BadParametersException, ExistingCompetitorException{
		Collection<Competitor> members =new HashSet<Competitor>();
		members.add(new Individual("Thiam","Maurice", "22-09-1992"));
		members.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		myTeam = new Team("Barcelon",members);
		myTeam.deleteMember(new Individual("Thiam","Maurine", "22-09-1992"));
		assertTrue(myTeam.getMembers().size()==2);
		
	}
	@Test
	public void testEquals() throws BadParametersException, ExistingCompetitorException{
		Collection<Competitor> members =new HashSet<Competitor>();
		members.add(new Individual("Thiam","Maurice", "22-09-1992"));
		members.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		myTeam = new Team("Barcelon",members);
		Collection<Competitor> members1 =new HashSet<Competitor>();
		members1.add(new Individual("Thiam","Maurice", "22-09-1992"));
		members1.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		Team myTeam1 = new Team("Cisse",members1);
		Collection<Competitor> members2 =new HashSet<Competitor>();
		members2.add(new Individual("Thiami","Maurice", "22-09-1992"));
		members2.add(new Individual("Ketevi","Mariam", "02-11-1992"));
		Team myTeam2 = new Team("Barcelon",members2);
		assertTrue(myTeam.equals(myTeam1));
		assertFalse(myTeam.equals(myTeam2));
		
	}

}
