package fr.uv1.utils;

import java.sql.*;

/**
 * Utility class for managing the connection to the database (PostgreSQL).
 * 
 * @author Philippe TANGUY
 */
public class DataBaseConnection {
	 //-----------------------------------------------------------------------------
	  // Connection parameters
	  private static String username        = "";
	  private static String password        = "";
	  private static String host            = "localhost";
	  private static String numPort         = "54321";
	  private static String base            = "";
	  private static String connectString   = "jdbc:postgresql://" + host + ":" + numPort + "/" + base;
	  //-----------------------------------------------------------------------------
	  // Registration of the PostgreSQL JDBC Driver.
	  static
	  {
	    try
	    {
	      DriverManager.registerDriver(new org.postgresql.Driver());
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	  }
	  //-----------------------------------------------------------------------------
	  /**
	   * Obtaining a connection to the database.
	   *  
	   * @return an instance of the Connection class.
	   * @throws SQLException
	   */
	  public static Connection getConnection() throws SQLException
	  {
	    return DriverManager.getConnection(connectString,username,password);
	  }
	  //-----------------------------------------------------------------------------
}
