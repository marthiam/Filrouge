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
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-1-1992"));
		assertTrue(person.getFirstname().equals("Jean"));
		assertTrue(person.getLastname().equals("Dupont"));
		assertTrue(person.getBorndate().equals("02-1-1992"));
	}

	@Test(expected = BadParametersException.class)
	public void testNullLastnamePerson() throws BadParametersException {
		new Person(null, new String("Miguel"),new String("02-11-1992"));
	}

	@Test(expected = BadParametersException.class)
	public void testNullFirstnamePerson() throws BadParametersException {
		new Person(new String("Duran"),null,new String("02-11-1992"));
	}
	@Test(expected = BadParametersException.class)
	public void testNullBorndatePerson() throws BadParametersException {
		new Person(new String("Duran"), new String("Miguel"),null);
	}
	
	@Test(expected = BadParametersException.class)
	public void testInvalidLastnamePerson() throws BadParametersException {
		new Person(new String(""), new String("Miguel"),new String("02-11-1992"));
	}

	@Test(expected = BadParametersException.class)
	public void testInvalidFirstnamePerson() throws BadParametersException {
		new Person(new String("Duran"), new String(""),new String("02-11-1992"));
	}
	@Test(expected = BadParametersException.class)
	public void testInvalidBorndatePerson() throws BadParametersException {
		new Person(new String("Duran"), new String("Miguel"),new String("02/11/1992"));
	}
	@Test(expected = BadParametersException.class)
	public void testInvalidBorndateEmptyPerson() throws BadParametersException {
		new Person(new String("Duran"), new String("Miguel"),new String(""));
	}
	@Test(expected = BadParametersException.class)
	public void testInvalidBorndate2Person() throws BadParametersException {
		new Person(new String("Duran"), new String("Miguel"),new String("02-11-1997"));
	}
	
	@Test(expected = BadParametersException.class)
	public void testInvalidBorndate3Person() throws BadParametersException {
		new Person(new String("Duran"), new String("Miguel"),new String("52-11-1992"));
	}
	@Test(expected = BadParametersException.class)
	public void testInvalidBorndate4Person() throws BadParametersException {
		new Person(new String("Duran"), new String("Miguel"),new String("12-13-1992"));
	}
	@Test(expected = BadParametersException.class)
	public void testInvalidBorndate5Person() throws BadParametersException {
		new Person(new String("Duran"), new String("Miguel"),new String("12-0-1992"));
	}
	@Test(expected = BadParametersException.class)
	public void testInvalidBorndate6Person() throws BadParametersException {
		new Person(new String("Duran"), new String("Miguel"),new String("12-0-1000"));
	}
	
	@Test
	public void testSetFirstname() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setFirstname(new String("Kadidiatou"));
		assertFalse(person.getFirstname().equals(new String("Mariam")));
		assertTrue(person.getFirstname().equals(new String("Kadidiatou")));
		}
	
	@Test
	public void testSetLastname() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setLastname(new String("Thiam"));
		assertFalse(person.getLastname().equals(new String("Cisse")));
		assertTrue(person.getLastname().equals(new String("Thiam")));
		}
	@Test
	public void testSetBorndate() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setBorndate(new String("02-11-1993"));
		assertFalse(person.getBorndate().equals(new String("02-11-1992")));
		assertTrue(person.getBorndate().equals(new String("02-11-1993")));
		}
	@Test(expected = BadParametersException.class)
	public void testInvalideSetFirstname() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setFirstname(new String(""));
		}
	
	@Test(expected = BadParametersException.class)
	public void testInvalideSetLastname() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setLastname(new String(""));
		}
	@Test(expected = BadParametersException.class)
	public void testInvalideSetBorndate() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setBorndate(new String("02/11/2013"));
		
		}
	@Test(expected = BadParametersException.class)
	public void testNullSetFirstname() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setFirstname(null);
		}
	
	@Test(expected = BadParametersException.class)
	public void testNullSetLastname() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setLastname(null);
		}
	@Test(expected = BadParametersException.class)
	public void testNullSetBorndate() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		person.setBorndate(null);
		
		}
	@Test
	public void testEquals() throws BadParametersException {
		person = new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"));
		assertTrue(person.equals(new Person(new String("Dupont"), new String("Jean"),new String("02-11-1992"))));
		assertTrue(person.equals(new Subscriber(new String("Dupont"), new String("Jean"),"02-11-1992","djean")));
		assertFalse(person.equals(new Person(new String("Dupond"), new String("Jean"),"02-11-1992")));
		}
	
	
	
}