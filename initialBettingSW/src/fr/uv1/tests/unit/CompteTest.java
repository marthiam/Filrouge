package fr.uv1.tests.unit;

import static org.junit.Assert.*;
import org.junit.Test;

import fr.uv1.bettingServices.Compte;
import fr.uv1.bettingServices.exceptions.BadParametersException;

public class CompteTest {
	
	private Compte compte;
	
	@Test
	public void testCompte() throws BadParametersException{
		
		compte = new Compte(100);
		assertTrue(compte.getSolde()==100);
	}
	
	@Test
	public void testCompteSansParametre() throws BadParametersException{
		
		compte = new Compte();
		assertTrue(compte.getSolde()==0);
	}
	
	
	@Test(expected = BadParametersException.class)
	public void testInvalideSoldeCompte() throws BadParametersException{
		new Compte(-100);
	}
	
	@Test
	public void testSetSolde() throws BadParametersException{
		compte = new Compte(100);
		compte.setSolde(200);
		assertFalse(compte.getSolde()==100);
		assertTrue(compte.getSolde()==200);
	}
	
	@Test(expected = BadParametersException.class)
	public void testSetInvalideSolde() throws BadParametersException{
		compte = new Compte(100);
		compte.setSolde(-200);
	}
	
	@Test
	public void testDebiterCompte() throws BadParametersException{
		compte = new Compte(100);
		compte.debiterCompte(30);
		assertTrue(compte.getSolde()==70);
		assertFalse(compte.getSolde()==100);
	}
	
	@Test (expected = BadParametersException.class)
	public void testDebiterCompteMontantSuperieurAuSolde() throws BadParametersException{
		compte = new Compte(100);
		compte.debiterCompte(250);
	}
	
	@Test (expected = BadParametersException.class)
	public void testDebiterCompteMontantNegatif() throws BadParametersException{
		compte = new Compte(100);
		compte.debiterCompte(-250);
	}	
	
	@Test
	public void testCrediterCompte() throws BadParametersException{
		compte = new Compte(100);
		compte.crediterCompte(50);
		assertTrue(compte.getSolde()==150);
		assertFalse(compte.getSolde()==100);
	}
	
	@Test (expected = BadParametersException.class)
	public void testCrediterCompteMontantNegatif() throws BadParametersException{
		compte = new Compte(100);
		compte.crediterCompte(-250);
	}	

}
