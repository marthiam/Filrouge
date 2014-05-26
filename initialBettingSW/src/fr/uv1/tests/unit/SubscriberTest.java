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
				new String("02-11-1992"), new String("worldChamp"));

		assertTrue(subs.getUsername().equals("worldChamp"));
		assertTrue(subs.getPassword() != null);
	}

	@Test(expected = BadParametersException.class)
	public void testNullUsernameSubscriber() throws BadParametersException {
		new Subscriber(new String("Duran"), new String("Miguel"), new String(
				"02-11-1992"), null);
	}

	@Test(expected = BadParametersException.class)
	public void testInvalidUsernameSubscriber() throws BadParametersException {
		new Subscriber(new String("Duran"), new String("Miguel"), new String(
				"02-11-1992"), new String(""));
	}

	@Test
	public void testHasUsername() throws BadParametersException {
		subs = new Subscriber(new String("Duran"), new String("Miguel"),
				new String("02-11-1992"), new String("worldChamp"));

		assertTrue(subs.hasUsername("worldChamp"));
		assertFalse(subs.hasUsername("wddfkjddfk"));
	}

	@Test
	public void testEqualsObject() throws BadParametersException {
		subs = new Subscriber(new String("Duran"), new String("Miguel"),
				new String("02-11-1992"), new String("worldChamp"));

		// Same subscriber = same username
		Subscriber s = new Subscriber(new String("Duran"),
				new String("Miguel"), new String("02-11-1992"), new String(
						"worldChamp"));
		assertTrue(subs.equals(s));

		s = new Subscriber(new String("Durano"), new String("Miguel"),
				new String("02-11-1992"), new String("worldChamp"));
		assertTrue(subs.equals(s));

		// Different subscriber = different username
		s = new Subscriber(new String("Duran"), new String("Miguelo"),
				new String("02-11-1992"), new String("worldC"));
		assertFalse(subs.equals(s));
	}

	@Test
	public void testSetUsername() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("02-11-1992"), new String("mcisse"));
		subs.setUsername(new String("bcisse"));
		assertFalse(subs.getUsername().equals(new String("mcisse")));
		assertTrue(subs.getUsername().equals(new String("bcisse")));
	}

	@Test(expected = BadParametersException.class)
	public void testSetNullUsername() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("02-11-1992"), new String("mcisse"));
		subs.setUsername(new String(""));
	}

	@Test(expected = BadParametersException.class)
	public void testSetNullFirstname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("02-11-1992"), new String("mcisse"));
		subs.setFirstname(new String(""));
	}

	@Test(expected = BadParametersException.class)
	public void testSetNullLastname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("02-11-1992"), new String("mcisse"));
		subs.setLastname(new String(""));
	}

	@Test
	public void testGetFirtsname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("02-11-1992"), new String("mcisse"));
		assertTrue(subs.getFirstname().equals(new String("Mariam")));
		assertFalse(subs.getFirstname().equals(new String("Kadidiatou")));
	}

	@Test
	public void testGetLastname() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("02-11-1992"), new String("mcisse"));
		assertTrue(subs.getLastname().equals(new String("Cisse")));
		assertFalse(subs.getLastname().equals(new String("Thiam")));
	}

	@Test
	public void testGetUsername() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("02-11-1992"), new String("mcisse"));
		assertTrue(subs.getUsername().equals(new String("mcisse")));
		assertFalse(subs.getUsername().equals(new String("bcisse")));
	}

	@Test
	public void testEquals() throws BadParametersException {
		subs = new Subscriber(new String("Cisse"), new String("Mariam"),
				new String("02-11-1992"), new String("mcisse"));
		Subscriber subs1 = new Subscriber(new String("Thiam"), new String(
				"Mamadou"), new String("02-11-1992"), new String("mcisse"));
		Subscriber subs2 = new Subscriber(new String("Thiam"), new String(
				"Mamadou"), new String("02-11-1992"), new String("mcisse1"));
		assertTrue(subs.equals(subs1));
		assertFalse(subs.equals(subs2));
	}
}