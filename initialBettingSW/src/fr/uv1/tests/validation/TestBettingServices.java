package fr.uv1.tests.validation;

import java.util.ArrayList;

import fr.uv1.bettingServices.Competition;
import fr.uv1.bettingServices.Competitor;
import fr.uv1.bettingServices.Individual;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.exceptions.*;
import fr.uv1.utils.MyCalendar;

/**
 * 
 * @author segarra
 * 
 */
public abstract class TestBettingServices extends ValidationTest {

	public TestBettingServices() {
		super();
		this.launchTests();
	}

	private void launchTests() {

		// ****************************
		// * Tests "List subscribers" *
		// ****************************
		System.out.println("\n * Tests pour lister joueurs\n");
		// Tests "entries" : null
		try {
			this.getBetting().listSubscribers(null);
			System.out
					.println("la consultation des joueurs avec mdp gestionnaire non instancié n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			// Tests "number"
			if (this.getBetting().listSubscribers(this.getManagerPassword())
					.size() != 0)
				System.out
						.println("il existe des joueurs alors que le métier vient d'être construit");

			System.out.println(">>>>> Tous les tests passés\n\n");

			// ********************************
			// * Tests "authenticate manager" *
			// ********************************
			System.out.println("\n * Tests pour autentifier manager\n");
			this.testAuthenticateMngr();
			System.out.println(">>>>> Tous les tests passés\n\n");

			// *************************************
			// * Tests "change manager's password" *
			// *************************************

			System.out.println("\n * Tests pour changer mdp manager\n");
			this.testChangeMngrPwd();
			System.out.println(">>>>> Tous les tests passés\n\n");

			// *********************
			// * Tests "subscribe" *
			// *********************
			System.out.println("\n * Tests pour inscrire un joueur\n");
			this.testSubscribe();
			System.out.println(">>>>> Tous les tests passés\n\n");

			// ****************************
			// * Tests "List subscribers" *
			// ****************************
			System.out.println("\n * Tests pour lister joueurs\n");
			// Tests number
			if (this.getBetting().listSubscribers(this.getManagerPassword())
					.size() != 6) {
				System.out.println("le nombre de joueurs est incorrect ");
				System.out.print("Il doit y avoir 6 joueurs. ");
				System.out.println("Il y en a "
						+ this.getBetting()
								.listSubscribers(this.getManagerPassword())
								.size());
			}

			this.getBetting().subscribe(new String("Prou"),
					new String("Bernard"), new String("02/11/1992"),
					new String("nanard"), this.getManagerPassword());
			if (this.getBetting().listSubscribers(this.getManagerPassword())
					.size() != 7) {
				System.out.println("le nombre de joueurs est incorrect ");
				System.out.print("Il doit y avoir 7 joueurs. ");
				System.out.println("Il y en a "
						+ this.getBetting()
								.listSubscribers(this.getManagerPassword())
								.size());
			}
			System.out.println(">>>>> Tous les tests passés\n\n");

			// ***********************
			// * Tests "Unsubscribe" *
			// ***********************
			System.out.println("\n * Tests pour désinscrire un joueur\n");

			testUnsubscribe();
			System.out.println(">>>>> Tous les tests passés\n\n");

		} catch (Exception e) {
			System.out.println("\n Exception imprévue : " + e);
			e.printStackTrace();
		}
	}

	private void testChangeMngrPwd() throws BadParametersException,
			AuthenticationException {
		// Tests parameters : null
		try {
			this.getBetting().changeMngrPwd(null, null);
			System.out
					.println("changer le mdp gestionnaire avec aucun paramètre instancié n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		try {
			this.getBetting().changeMngrPwd(new String("ilesCanaries"), null);
			System.out
					.println("l'utilisation d'un password gestionnaire non instancié n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			this.getBetting().changeMngrPwd(new String("sdhjuikl$*"),
					new String("qsd"));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			this.getBetting().changeMngrPwd(new String("ilesCanaries"),
					new String("qsd"));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			this.getBetting().changeMngrPwd(null, this.getManagerPassword());
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			this.getBetting().changeMngrPwd(new String("d"),
					this.getManagerPassword());
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire invalide (d) n'a pas levé d'exception  ");
		} catch (BadParametersException e) {
		}
		try {
			this.getBetting().changeMngrPwd(new String("ddfqsdqsdfqsfdf$"),
					this.getManagerPassword());
			System.out
					.println("l'utilisation d'un nouveau password gestionnaire invalide (ddfqsdqsdfqsfdf$) n'a pas levé d'exception  ");
		} catch (BadParametersException e) {
		}
	}

	private void testAuthenticateMngr() {
		try {
			this.getBetting().authenticateMngr(new String("ilesLofotens"));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
		try {
			this.getBetting().authenticateMngr(new String(" "));
			System.out
					.println("l'utilisation d'un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		try {
			this.getBetting().authenticateMngr(null);
			System.out
					.println("l'utilisation d'un password gestionnaire non instancié n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}
	}

	private void testSubscribe() throws AuthenticationException,
			ExistingSubscriberException, BadParametersException,
			SubscriberException {

		// Tests entries : null
		try {
			this.getBetting().subscribe(null, new String("Albert"),
					new String("02/11/1992"), new String("worldChamp"),
					this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un nom non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			this.getBetting().subscribe(new String("Duran"), null,
					new String("02/11/1992"), new String("worldChamp"),
					this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un prénom non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			this.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("02/11/1992"), null,
					this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un pseudo non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			this.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("02/11/1992"),
					new String("worldChamp"), null);
			System.out
					.println("l'ajout d'un joueur avec un mdp gestionnaire non instancié n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests entries : invalid format
		try {
			this.getBetting().subscribe(new String(" "), new String("Albert"),
					new String("02/11/1992"), new String("worldChamp"),
					this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un nom invalide ( ) n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			this.getBetting().subscribe(new String("Duran"), new String(" "),
					new String("02/11/1992"), new String("worldChamp"),
					this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un prénom invalide ( ) n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			this.getBetting().subscribe(new String("Duran87"),
					new String("Albert"), new String("02/11/1992"),
					new String("worldChamp"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un nom invalide (Duran87) n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {
			this.getBetting().subscribe(new String("87Duran87"),
					new String("Albert"), new String("02/11/1992"),
					new String("worldChamp"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un nom invalide (87Duran87) n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}

		try {
			this.getBetting().subscribe(new String("-Duran87"),
					new String("Albert"), new String("02/11/1992"),
					new String("worldChamp"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un nom invalide (-Duran87) n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}

		try {
			this.getBetting().subscribe(new String("Nobel"),
					new String("Alfred"), new String("02/11/1992"),
					new String("tnt"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un pseudo invalide (tnt) n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}

		try {
			this.getBetting().subscribe(new String("Nobel"),
					new String("Alfred"), new String("02/11/1992"),
					new String("tnt988987-"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un pseudo invalide (tnt988987-) n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}

		try {
			this.getBetting().subscribe(new String("Duran"),
					new String("Roberto"), new String("02/11/1992"),
					new String("worldChamp"), new String("abef"));
			System.out
					.println("l'ajout d'un joueur avec un password gestionnaire incorrect n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests with valid parameters
		try {
			this.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("02/11/1992"),
					new String("fanfan"), this.getManagerPassword());
		} catch (ExistingSubscriberException | BadParametersException e) {
			System.out
					.println("l'ajout d'un nouveau joueur (Duran, fanfan) a levé une exception "
							+ e.getClass());
		}
		try {
			this.getBetting().subscribe(new String("Duran Dorton"),
					new String("Albert"), new String("02/11/1992"),
					new String("fanfen"), this.getManagerPassword());
		} catch (ExistingSubscriberException | BadParametersException e) {
			System.out
					.println("l'ajout d'un nouveau joueur (Duran Dorton, fanfen) a levé une exception "
							+ e.getClass());
		}

		try {
			this.getBetting().subscribe(new String("Nobel"),
					new String("Alfred"), new String("02/11/1992"),
					new String("9tnt988987"), this.getManagerPassword());
		} catch (BadParametersException e) {
			System.out
					.println("l'ajout d'un nouveau joueur (Nobel Alfred tnt988987) a levé une exception");
		}

		// The same subscriber
		try {
			this.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("02/11/1992"),
					new String("fanfan"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur existant (Duran, fanfan) n'a pas levé d'exception");
		} catch (ExistingSubscriberException e) {
		}
		// same firstname, username ; different lastname
		try {
			this.getBetting().subscribe(new String("Durano"),
					new String("Albert"), new String("02/11/1992"),
					new String("fanfan"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur existant (Durano, fanfan) n'a pas levé d'exception");
		} catch (ExistingSubscriberException e) {
		}
		// same lastname, username; different firstname
		try {
			this.getBetting().subscribe(new String("Duran"),
					new String("Alfred"), new String("02/11/1992"),
					new String("fanfan"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur existant (Duran, fanfan) n'a pas levé d'exception ");
		} catch (ExistingSubscriberException e) {
		}
		// same lastname, firstname; different username
		try {
			this.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("02/11/1992"),
					new String("fanfin"), this.getManagerPassword());
		} catch (ExistingSubscriberException e) {
			System.out
					.println("l'ajout d'un joueur pas inscrit (Duran, fanfin) a levé l'exception "
							+ e.getClass());
		}

		// same firstname; different lastname, username
		try {
			this.getBetting().subscribe(new String("Durano"),
					new String("Albert"), new String("02/11/1992"),
					new String("fanfin"), this.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur inscrit (Durano, fanfin) n'a pas levé d'exception ");
		} catch (ExistingSubscriberException e) {
		}

		// same lastname; different username and firstname
		try {
			this.getBetting().subscribe(new String("Duran"),
					new String("Morgan"), new String("02/11/1992"),
					new String("fanfon"), this.getManagerPassword());
		} catch (ExistingSubscriberException e) {
			System.out
					.println("l'ajout d'un nouveau joueur (Duran, fanfon) a levé l'exception "
							+ e.getClass());
		}

		// different lastname, firstname and username
		try {
			this.getBetting().subscribe(new String("Mato"), new String("Anna"),
					new String("02/11/1992"), new String("salto"),
					this.getManagerPassword());
		} catch (ExistingSubscriberException e) {
			System.out
					.println("l'ajout d'un nouveau joueur (Mato, salto) a levé l'exception "
							+ e.getClass());
		}
	}

	private void testUnsubscribe() throws AuthenticationException,
			ExistingSubscriberException {
		// Tests parameters : null
		try {
			this.getBetting().unsubscribe(null, this.getManagerPassword());
			System.out
					.println("retirer un joueur avec un pseudo non instancié n'a pas levé d'exception");
		} catch (ExistingSubscriberException e) {
		}
		try {
			this.getBetting().unsubscribe(new String("nanard"), null);
			System.out
					.println("retirer un joueur avec un mdp gestionnaire non instancié n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		// Tests parameters: incorrect manager password
		try {
			this.getBetting()
					.unsubscribe(new String("nanard"), new String(" "));
			System.out
					.println(" retirer un joueur avec un mdp gestionnaire incorrect (\" \") n'a pas levé d'exception");
		} catch (AuthenticationException e) {
		}

		// Test number
		int number = this.getBetting()
				.listSubscribers(this.getManagerPassword()).size();
		if (number != 7) {
			System.out.println("le nombre de joueurs est incorrect");
			System.out.print("Il doit y avoir 7 joueurs. ");
			System.out.println("Il y en a "
					+ this.getBetting()
							.listSubscribers(this.getManagerPassword()).size()
					+ ")");
		}

		// Unsubscribe an existing subscriber
		try {
			this.getBetting().unsubscribe(new String("fanfan"),
					this.getManagerPassword());
		} catch (ExistingSubscriberException e) {
			System.out
					.println("retirer un joueur existant (fanfan) a levée une exception");
		}

		number = this.getBetting().listSubscribers(this.getManagerPassword())
				.size();

		// Unsubscribe an already unsubscriber subscriber
		try {
			this.getBetting().unsubscribe(new String("fanfan"),
					this.getManagerPassword());
			System.out
					.println("retirer un joueur déjà retiré (fanfan) n'a pas levé d'exception");
		} catch (ExistingSubscriberException e) {
		}

		// Unsubscribe a non existing subscriber
		try {
			this.getBetting().unsubscribe(new String("tito"),
					this.getManagerPassword());
			System.out
					.println("retirer un joueur non enregistré n'a levé d'exception");
		} catch (ExistingSubscriberException e) {
		}

		// Test number
		if (this.getBetting().listSubscribers(this.getManagerPassword()).size() != 6) {
			System.out.println("le nombre de joueurs est incorrect");
			System.out.print("Il doit y avoir 6 joueurs. ");
			System.out.println("Il y en a "
					+ this.getBetting()
							.listSubscribers(this.getManagerPassword()).size()
					+ ")");
		}
	}

	private void testBetOnPodium() throws AuthenticationException,
			CompetitionException, ExistingCompetitionException,
			SubscriberException, BadParametersException,
			ExistingSubscriberException {

		ArrayList<Competitor> competitors = new ArrayList<Competitor>();
		competitors.add(new Individual("Coulibaly", "Mamadou", "28-09-1992"));
		competitors.add(new Individual("Cisse", "Sanounou", "05-01-1989"));
		competitors.add(new Individual("Cisse", "Pinda", "07-10-1990"));
		competitors.add(new Individual("Thiam", "Sali", "05-01-1989"));
		competitors.add(new Individual("Taure", "Aminata", "07-10-1990"));
		Competition comp = new Competition("courseA", new MyCalendar(2014, 06,
				2), competitors);
		String joueurPW = this.getBetting().subscribe("Dupond", "Max",
				"02-10-2014", "dmax", this.getManagerPassword());

		// Tests parameters : null
		try {

			this.getBetting().betOnPodium(0, "courseA",
					new Individual("Taure", "Aminata", "07-10-1990"),
					new Individual("Thiam", "Sali", "05-01-1989"),
					new Individual("Coulibaly", "Mamadou", "28-09-1992"),
					"dmax", joueurPW);
			System.out.println("parier zero jeton n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {

			this.getBetting().betOnPodium(200, null,
					new Individual("Taure", "Aminata", "07-10-1990"),
					new Individual("Thiam", "Sali", "05-01-1989"),
					new Individual("Coulibaly", "Mamadou", "28-09-1992"),
					"dmax", joueurPW);
			System.out
					.println(" parier sur une competition dont le nom est non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {

			this.getBetting().betOnPodium(200, "courseA", null,
					new Individual("Thiam", "Sali", "05-01-1989"),
					new Individual("Coulibaly", "Mamadou", "28-09-1992"),
					"dmax", joueurPW);
			System.out
					.println("parier sur un competiteur non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}
		try {

			this.getBetting().betOnPodium(200, "courseA",
					new Individual("Taure", "Aminata", "07-10-1990"),
					new Individual("Thiam", "Sali", "05-01-1989"),
					new Individual("Coulibaly", "Mamadou", "28-09-1992"), null,
					joueurPW);
			System.out
					.println("le pari fait par un joueur dont le username est non instancié n'a pas levé d'exception");
		} catch (BadParametersException e) {
		}

	}
}