package fr.uv1.tests.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.uv1.bettingServices.Person;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.exceptions.BadParametersException;

public class PersonTest {
	private Person person;
	/*
	 * Person should be created with valid strings
	 */
	

	@Test
	public void testPerson() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"));
		assertTrue(person.getFirstname().equals("Jean"));
		assertTrue(person.getLastname().equals("Dupont"));
	}

	@Test(expected = BadParametersException.class)
	public void testNullLastnamePerson() throws BadParametersException {
		new Person(null, new String("Miguel"));
	}

	@Test(expected = BadParametersException.class)
	public void testNullFirstnamePerson() throws BadParametersException {
		new Person(new String("Duran"), null);
	}
	
	@Test(expected = BadParametersException.class)
	public void testInvalidLastnamePerson() throws BadParametersException {
		new Person(new String(" "), new String("Miguel"));
	}

	@Test(expected = BadParametersException.class)
	public void testInvalidFirstnamePerson() throws BadParametersException {
		new Person(new String("Duran"), new String(""));
	}
}