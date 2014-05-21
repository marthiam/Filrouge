package fr.uv1.bd;

import java.sql.*;
import java.util.*;

import fr.uv1.bettingServices.*;
import fr.uv1.tests.unit.*;;

/**
 * DAO class (<i>Data Access Object</i>) for the {@link Subscriber} class.
 * This class provides the CRUD functionalities :<br>
 * <ul>
 *   <li><b>C</b>: create a new subscriber in the database.
 *   <li><b>R</b>: retrieve (or read) a (list of)subscriber(s) in the database.
 *   <li><b>U</b>: update the values stored in the database for a subscriber.
 *   <li><b>D</b>: delete a subscriber in the database.
 * </ul>
 * 
 * @author THIAM Mariam
 */
public class SubscribersManager
{
  //-----------------------------------------------------------------------------
  /**
   * Store a subscriber in the database. This subscriber is not stored
   * yet, so his <code>id</code> value is <code>NULL</code>. Once the
   * subscriber is stored, the method returns the subscriber with the
   * <code>id</code> value setted.
   * 
   * @param subscriber the subscriber to be stored.
   * @return the subscriber with the updated value for the id.
   * @throws SQLException
   */
  public static Subscriber persist(Subscriber subscriber) throws SQLException
  {
    // Two steps in this methods which must be managed in an atomic
    // (unique) transaction:
    //   1 - insert the new subscriber;
    //   2 - once the insertion is OK, in order to set up the value
    //       of the id, a request is done to get this value by
    //       requesting the sequence (subscribers_id_seq) in the
    //       database.
    Connection c = DatabaseConnection.getConnection();
    try
    {
      c.setAutoCommit(false);
      PreparedStatement psPersist = c.prepareStatement("insert into person(prenom,nom,borndate,type,username,password,solde)  values (?, ?,?,?,?,?)");

      psPersist.setString(1, subscriber.getFirstname());
      psPersist.setString(2, subscriber.getLastname());
      psPersist.setString(3, subscriber.getBorndate());
      psPersist.setString(4, "Joueur");
      psPersist.setString(5, subscriber.getUsername());
      psPersist.setString(3, subscriber.getPassword());
      psPersist.setLong(3, subscriber.solde());
      psPersist.executeUpdate();
      
      psPersist.close();
      
      // Retrieving the value of the id with a request on the
      // sequence (subscribers_id_seq).
      PreparedStatement psIdValue = c.prepareStatement("select currval('id_personne') as value_id");
      ResultSet resultSet = psIdValue.executeQuery();
      Integer id  = null;
      while(resultSet.next())
      {
        id = resultSet.getInt("value_id");
      }
      resultSet.close();
      psIdValue.close();
      c.commit();
      subscriber.setId(id);
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
    
    c.setAutoCommit(true);
    c.close();
    
    return subscriber;
  }
  //-----------------------------------------------------------------------------
  /**
   * Find a subscriber by his id.
   * 
   * @param id the id of the subscriber to retrieve.
   * @return the subscriber or null if the id does not exist in the database.
   * @throws SQLException
   */
  public static Subscriber findById(Integer id) throws SQLException
  {
    // 1 - Get a database connection from the class 'DatabaseConnection' 
    Connection c = DatabaseConnection.getConnection();

    // 2 - Creating a Prepared Statement with the SQL instruction.
    //     The parameters are represented by question marks. 
    PreparedStatement psSelect = c.prepareStatement("select * from subscribers where id=?");
    
    // 3 - Supplying values for the prepared statement parameters (question marks).
    psSelect.setInt(1, id);

    // 4 - Executing Prepared Statement object among the database.
    //     The return value is a Result Set containing the data.
    ResultSet resultSet = psSelect.executeQuery();
    
    // 5 - Retrieving values from the Result Set.
    Subscriber subscriber = null;
    while(resultSet.next())
    {
      subscriber = new Subscriber(resultSet.getInt("id_personne"),
                                  resultSet.getString("prenom"), 
                                  resultSet.getString("n"));
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
   */
  public static List<Subscriber> findAll() throws SQLException
  {
    Connection c = DatabaseConnection.getConnection();
    PreparedStatement psSelect = c.prepareStatement("select * from subscribers order by id");
    ResultSet resultSet = psSelect.executeQuery();
    List<Subscriber> subscribers = new ArrayList<Subscriber>();
    while(resultSet.next())
    {
      subscribers.add(new Subscriber(resultSet.getInt("id"),
                                     resultSet.getString("firstname"),
                                     resultSet.getString("lastname")));
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
    Connection c = DatabaseConnection.getConnection();

    // 2 - Creating a Prepared Statement with the SQL instruction.
    //     The parameters are represented by question marks. 
    PreparedStatement psUpdate = c.prepareStatement("update subscribers set firstname=?, lastname=? where id=?");

    // 3 - Supplying values for the prepared statement parameters (question marks).
    psUpdate.setString(1, subscriber.getFirstname());
    psUpdate.setString(2, subscriber.getLastname());
    psUpdate.setInt(3, subscriber.getId());

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
    Connection c = DatabaseConnection.getConnection();
    PreparedStatement psUpdate = c.prepareStatement("delete from subscribers where id=?");
    psUpdate.setInt(1, subscriber.getId());
    psUpdate.executeUpdate();
    psUpdate.close();
    c.close();
  }
  //-----------------------------------------------------------------------------
}
