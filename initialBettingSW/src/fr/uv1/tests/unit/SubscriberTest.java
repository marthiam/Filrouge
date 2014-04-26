package fr.uv1.tests.unit;

import static org.junit.Assert.*;

import org.junit.*;

import fr.uv1.bettingServices.*;
import fr.uv1.bettingServices.exceptions.BadParametersException;

public class SubscriberTest {

	private Subscriber subs;


	/*
	 * Subscribers should be created with valid strings
	 */
	

	@Test
	public void testSubscriber() throws BadParametersException {
		subs = new Subscriber(new String("Duran"), new String("Miguel"),
				new String("worldChamp"));
		assertTrue(subs.getFirstname().equals("Miguel"));
		assertTrue(subs.getLastname().equals("Duran"));
		assertTrue(subs.getUsername().equals("worldChamp"));
		assertTrue(subs.getPassword() != null);
	}
	

	@Test(expected = BadParametersException.class)
	public void testNullLastnameSubscriber() throws BadParametersException {
		new Subscriber(null, new String("Miguel"), new String("worldChamp"));
	}

	@Test(expected = BadParametersException.class)
	public void testNullFirstnameSubscriber() throws BadParametersException {
		new Subscriber(new String("Duran"), null, new String("worldChamp"));
	}

	@Test(expected = BadParametersException.class)
	public void testNullUsernameSubscriber() throws BadParametersException {
		new Subscriber(new String("Duran"), new String("Miguel"), null);
	}

	@Test(expected = BadParametersException.class)
	public void testInvalidLastnameSubscriber() throws BadParametersException {
		new Subscriber(new String(" "), new String("Miguel"), new String(
				"worldChamp"));
	}

	@Test(expected = BadParametersException.class)
	public void testInvalidFirstnameSubscriber() throws BadParametersException {
		new Subscriber(new String("Duran"), new String(""), new String(
				"worldChamp"));
	}

	@Test(expected = BadParametersException.class)
	public void testInvalidUsernameSubscriber() throws BadParametersException {
		new Subscriber(new String("Duran"), new String("Miguel"),
				new String(""));
	}

	@Test
	public void testHasUsername() throws BadParametersException {
		subs = new Subscriber(new String("Duran"), new String("Miguel"),
				new String("worldChamp"));
		
		assertTrue(subs.getUsername().equals("worldChamp"));
		assertFalse(subs.getUsername().equals("wddfkjddfk"));
	}

	@Test
	public void testEqualsObject() throws BadParametersException {
		subs = new Subscriber(new String("Duran"), new String("Miguel"),
				new String("worldChamp"));
		
		// Same subscriber = same username
		Subscriber s = new Subscriber(new String("Duran"),
				new String("Miguel"), new String("worldChamp"));
		assertTrue(subs.equals(s));

		s = new Subscriber(new String("Durano"), new String("Miguel"),
				new String("worldChamp"));
		assertTrue(subs.equals(s));

		// Different subscriber = different username
		s = new Subscriber(new String("Duran"), new String("Miguelo"),
				new String("worldC"));
		assertFalse(subs.equals(s));
	}
	
	@Test
	public void testSetFirstname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		subs.setFirstname(new String("Kadidiatou"));
		assertFalse(subs.getFirstname().equals(new String("Mariam")));
		assertTrue(subs.getFirstname().equals(new String("Kadidiatou")));
		}
	
	@Test
	public void testSetLastname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		subs.setLastname(new String("Thiam"));
		assertFalse(subs.getLastname().equals(new String("Cisse")));
		assertTrue(subs.getLastname().equals(new String("Thiam")));
		}
	
	@Test
	public void testSetUsername() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		subs.setUsername(new String("bcisse"));
		assertFalse(subs.getUsername().equals(new String("mcisse")));
		assertTrue(subs.getUsername().equals(new String("bcisse")));
		}

	@Test(expected = BadParametersException.class)
	public void testSetNullUsername() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		subs.setUsername(new String(""));
	}
	
	@Test(expected = BadParametersException.class)
	public void testSetNullFirstname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		subs.setFirstname(new String(""));
	}
	
	@Test(expected = BadParametersException.class)
	public void testSetNullLastname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		subs.setLastname(new String(""));
	}
	
	@Test
	public void testGetFirtsname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		assertTrue(subs.getFirstname().equals(new String("Mariam")));
		assertFalse(subs.getFirstname().equals(new String("Kadidiatou")));
		}
	
	@Test
	public void testGetLastname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		assertTrue(subs.getLastname().equals(new String("Cisse")));
		assertFalse(subs.getLastname().equals(new String("Thiam")));
		}
	
	@Test
	public void testGetUsername() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("mcisse"));
		assertTrue(subs.getUsername().equals(new String("mcisse")));
		assertFalse(subs.getUsername().equals(new String("bcisse")));
		}
}