package fr.uv1.bd;
import fr.uv1.utils.DataBaseConnection;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import fr.uv1.bettingServices.*;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.tests.unit.*;;

/**
 * DAO class (<i>Data Access Object</i>) for the {@link Subscriber} class.
 * This class provides the CRUD functionalities :<br>
 * <ul>
 *   <li><b>C</b>: create a new competitor in the database.
 *   <li><b>R</b>: retrieve (or read) a (list of)subscriber(s) in the database.
 *   <li><b>U</b>: update the values stored in the database for a subscriber.
 *   <li><b>D</b>: delete a subscriber in the database.
 * </ul>
 * 
 * @author THIAM Mariam
 */
public class CompetitorsManager
{
  //-----------------------------------------------------------------------------
  /**
   * Store a subscriber in the database. This subscriber is not stored
   * yet, so his <code>id</code> value is <code>NULL</code>. Once the
   * subscriber is stored, the method returns the subscriber with the
   * <code>id</code> value setted.
   * 
   * @param competitor the competitor to be stored.
   * @return the subscriber with the updated value for the id.
   * @throws SQLException
 * @throws BadParametersException 
   */
  public static Competitor persist(Competitor competitor) throws SQLException, BadParametersException
  {
    // Two steps in this methods which must be managed in an atomic
    // (unique) transaction:
    //   1 - insert the new subscriber;
    //   2 - once the insertion is OK, in order to set up the value
    //       of the id, a request is done to get this value by
    //       requesting the sequence (subscribers_id_seq) in the
    //       database.
    Connection c = DataBaseConnection.getConnection();
    Statement stmt = null;
    try
    {
      c.setAutoCommit(false);
      
      if(competitor instanceof Individual ){
      PreparedStatement psPersist = c.prepareStatement("insert into personne(prenom,nom,borndate,type)  values (?,?,?,?)"
    		  									,PreparedStatement.RETURN_GENERATED_KEYS);
      Individual competiteur = (Individual) competitor;
      psPersist.setString(1, competiteur.getFirstname());
      psPersist.setString(2, competiteur.getLastname());
	
	  Date date = Date.valueOf(competiteur.getBorndateDate());
	  System.out.println( "voici la date"+ date);
      psPersist.setDate(3,date);
      psPersist.setString(4, "CompetiteurIndiv");
      
      psPersist.executeUpdate();
      PreparedStatement psIdValue = c.prepareStatement("select currval('personne_id_seq') as value_id");
      ResultSet resultSet = psIdValue.executeQuery();
      Integer id  = null;
      while(resultSet.next())
      {
        id = resultSet.getInt("value_id");
      }
      psPersist.close();
	  c.commit();
	  competiteur.setId_individual(id);
   
      c.setAutoCommit(true);
      c.close();
      
      return competiteur;
	  
	  
      }else{
			PreparedStatement psPersist = c.prepareStatement("insert into personne(nom,type)  values (?,?)"
								,PreparedStatement.RETURN_GENERATED_KEYS);
			Team competiteur = (Team) competitor;
			psPersist.setString(1, competiteur.getTeamName());
			psPersist.setString(2, "CompetiteurTeam");
			psPersist.executeUpdate();
			PreparedStatement psIdValue = c.prepareStatement("select currval('personne_id_seq') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer id  = null;
			while(resultSet.next())
			{
			id = resultSet.getInt("value_id");
			}
			psPersist.close();
			c.commit();
			competiteur.setId_team(id);
			
			//persist Team members 
			Collection<Competitor> members = competiteur.getMembers();
			for(Competitor member : members){
				Individual indiv = (Individual) member;
				if ( findById(indiv.getId_individual())==null){
					indiv= (Individual) CompetitorsManager.persist(indiv);
				}
				
			PreparedStatement psPersistMember = c.prepareStatement("insert into estMembreDe(membre_id,team_id)  values (?,?)"
						,PreparedStatement.RETURN_GENERATED_KEYS);
			
			psPersistMember.setLong(1,indiv.getId_individual());
			psPersistMember.setLong(2, competiteur.getId_team());	
			
		    c.setAutoCommit(true);
		    c.close();
		    
		    return competiteur;
			
			}
	
      }
    }
    catch (SQLException e)
    {
      try
      {
        c.rollback();
      }
      catch (SQLException e1)
      {
        e1.printStackTrace();
      }
      c.setAutoCommit(true);
      throw e;
    }
    
  
 	
  }
  //-----------------------------------------------------------------------------
  /**
   * Find a competitor by his id.
   * 
   * @param id the id of the subscriber to retrieve.
   * @return the subscriber or null if the id does not exist in the database.
   * @throws SQLException
 * @throws BadParametersException 
   */
  public static Competitor findById(long id) throws SQLException, BadParametersException
  {
    // 1 - Get a database connection from the class 'DatabaseConnection' 
    Connection c = DataBaseConnection.getConnection();

    // 2 - Creating a Prepared Statement with the SQL instruction.
    //     The parameters are represented by question marks. 
    PreparedStatement psSelect = c.prepareStatement("select * from personne where type id_personne=?");
    
    // 3 - Supplying values for the prepared statement parameters (question marks).
    psSelect.setLong(1, id);

    // 4 - Executing Prepared Statement object among the database.
    //     The return value is a Result Set containing the data.
    ResultSet resultSet = psSelect.executeQuery();
    
    // 5 - Retrieving values from the Result Set.
    Competitor competitor = null;
    while(resultSet.next())
    {
	    if(resultSet.getString("type").equals("CompetiteurIndiv")){
		     String [] s = resultSet.getString("borndate").split("-");
		     int annee = new Integer(s[0]);
		   	 int mois = new Integer(s[1]);
		   	 int jour = new Integer(s[2]);
		     competitor = new Individual(resultSet.getString("prenom"), 
		                                  resultSet.getString("nom"),jour+ "-"+mois+"-"+annee);
		      (Individual)competitor.;
    	}else{
    		
    		competitor = new Team(resultSet.getString("nom"));
    		
    	}
    }
    
    // 6 - Closing the Result Set
    resultSet.close();
    
    // 7 - Closing the Prepared Statement.
    psSelect.close();
    
    // 8 - Closing the database connection.
    c.close();
    
    return subscriber;
  }
  //-----------------------------------------------------------------------------
  /**
   * Find all the subscribers in the database.
   * 
   * @return
   * @throws SQLException
 * @throws BadParametersException 
   */
  public static List<Subscriber> findAll() throws SQLException, BadParametersException
  {
    Connection c = DataBaseConnection.getConnection();
    PreparedStatement psSelect = c.prepareStatement("select * from personne order by id_personne");
    ResultSet resultSet = psSelect.executeQuery();
    List<Subscriber> subscribers = new ArrayList<Subscriber>();
    Subscriber subscriber = null;
    while(resultSet.next())
    {
    	String [] s = resultSet.getString("borndate").split("-");
          int annee = new Integer(s[0]);
     	  int mois = new Integer(s[1]);
     	  int jour = new Integer(s[2]);
    	subscriber=new Subscriber(resultSet.getString("prenom"),resultSet.getString("nom"),jour+"-"+mois+"-"+annee,resultSet.getString("username"));
    	subscriber.setCompte(new Compte(resultSet.getInt("solde")));
        subscribers.add(subscriber);
    }
    resultSet.close();
    psSelect.close();
    c.close();
    
    return subscribers;
  }
  //-----------------------------------------------------------------------------
  /**
   * Update on the database the values from a subscriber.
   * 
   * @param subscriber the subscriber to be updated.
   * @throws SQLException
   */
  public static void update(Subscriber subscriber) throws SQLException
  {
    // 1 - Get a database connection from the class 'DatabaseConnection' 
    Connection c = DataBaseConnection.getConnection();

    // 2 - Creating a Prepared Statement with the SQL instruction.
    //     The parameters are represented by question marks. 
    PreparedStatement psUpdate = c.prepareStatement("update personne set prenom=?, nom=?,borndate=?,type=?,username=?,password=?,solde=?  where id_personne=?");

    // 3 - Supplying values for the prepared statement parameters (question marks).
    psUpdate.setString(1, subscriber.getFirstname());
    psUpdate.setString(2, subscriber.getLastname());
    Date date = Date.valueOf(subscriber.getBorndateDate());
    psUpdate.setDate(3,date );
    psUpdate.setString(4, "Joueur");
    psUpdate.setString(5, subscriber.getUsername());
    psUpdate.setString(6, subscriber.getPassword());
    psUpdate.setLong(7, subscriber.solde());
    psUpdate.setLong(8, subscriber.getId_subscribe());
    System.out.println(subscriber.getId_subscribe());
    // Executing the prepared statement object among the database.
    // If needed, a return value (int) can be obtained. It contains
    // how many rows of a table were updated.
    // int nbRows = psUpdate.executeUpdate();
    psUpdate.executeUpdate();

    // 6 - Closing the Prepared Statement.
    psUpdate.close();

    // 7 - Closing the database connection.
    c.close();
  }
  //-----------------------------------------------------------------------------
  /**
   * Delete from the database a specific subscriber.<br>
   * <i>Be careful: the delete functionality does not operate a delete
   * cascade on bets belonging to the subscriber.</i>
   * 
   * @param subscriber the subscriber to be deleted.
   * @throws SQLException
   */
  public static void delete(Subscriber subscriber) throws SQLException
  {
    Connection c = DataBaseConnection.getConnection();
    PreparedStatement psUpdate = c.prepareStatement("delete from personne where id_personne=?");
    psUpdate.setLong(1, subscriber.getId_subscribe());
    psUpdate.executeUpdate();
    psUpdate.close();
    c.close();
  }
  //-----------------------------------------------------------------------------
}
