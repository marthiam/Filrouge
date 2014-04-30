package fr.uv1.tests.unit;

import static org.junit.Assert.assertFalse;
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
	@Test
	public void testSetFirstname() throws BadParametersException {
		person = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		person.setFirstname(new String("Kadidiatou"));
		assertFalse(person.getFirstname().equals(new String("Mariam")));
		assertTrue(person.getFirstname().equals(new String("Kadidiatou")));
		}
	
	@Test
	public void testSetLastname() throws BadParametersException {
		person = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		person.setLastname(new String("Thiam"));
		assertFalse(person.getLastname().equals(new String("Cisse")));
		assertTrue(person.getLastname().equals(new String("Thiam")));
		}
}