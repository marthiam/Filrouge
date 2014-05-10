package fr.uv1.tests.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.exceptions.BadParametersException;

public class IndividualTest {
	private Individual indiv; 

	@Test
	public void testIndividual() throws BadParametersException{
		indiv= new Individual("Thiam","Maurice", "22/09/2014");
		assertTrue(indiv.getFirstname().equals("Maurice"));
		assertTrue(indiv.getLastname().equals("Ketevi"));
		assertTrue(indiv.getBorndate().equals("22/09/2014"));
	}

	@Test
	public void testEqual() throws BadParametersException{
		indiv= new Individual("Thiam","Maurice", "22/09/2014");
		Individual indiv2= new Individual("Ketevi","Maurice", "22/09/2014");
		assertTrue(indiv2.equals(indiv));
		assertFalse(indiv2.equals(new Individual("Ketevi","Maurice", "22/10/2014")));
	}
	
	@Test
	public void testHasValidNam() throws BadParametersException{
		indiv= new Individual("Thiam","Maurice", "22/09/2014");
		assertTrue(indiv.hasValidName());
	}

	

}
