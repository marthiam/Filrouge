package fr.uv1.bd;

import java.sql.SQLException;
import java.util.List;

import fr.uv1.bd.*;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.exceptions.BadParametersException;

/**
 * @author Philippe TANGUY
 */
public class JDBCExampleMain {
	// -----------------------------------------------------------------------------
	public JDBCExampleMain() throws SQLException, BadParametersException {

		System.out.println("--- Start ---\n");

		System.out.println("All the subscribers");
		displayAllSubscribers();

	//	System.out.println("Subscriber #3: " + SubscribersManager.findById(3));
		System.out.println("after");

		System.out.println("Creating a new subscriber");
		Subscriber newSubscriber = new Subscriber(new String("Duran"), new String("Miguel"),new String("02-11-1995"),
				new String("worldChamp"));
		newSubscriber = SubscribersManager.persist(newSubscriber);
		System.out.println("newSubscriber = " + newSubscriber);
		System.out.println();

		System.out.println("All the subscribers after insertion");
		displayAllSubscribers();

		System.out.println("Updating the new subscriber");
		newSubscriber.setFirstname("_Marcel");
		newSubscriber.setLastname("_DUPONT");
		SubscribersManager.update(newSubscriber);

		System.out.println("All the subscribers after updating");
		displayAllSubscribers();

		System.out.println("Deleting the new subscriber");
		SubscribersManager.delete(newSubscriber);

		System.out.println("All the subscribers after delete");
		displayAllSubscribers();

		System.out.println("--- End ---\n");
	}

	// -----------------------------------------------------------------------------
	public void displayAllSubscribers() throws SQLException, BadParametersException {
		List<Subscriber> subscribers = SubscribersManager.findAll();
		for (Subscriber subscriber : subscribers) {
			System.out.println(subscriber);
		}
		System.out.println();
	}


	// -----------------------------------------------------------------------------
	public static void main(String[] args) throws BadParametersException {
		try {
			new JDBCExampleMain();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// -----------------------------------------------------------------------------
}
