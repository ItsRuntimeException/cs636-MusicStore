/**
 * Create the tables in Oracle first and write a program Register (in cs636.music.presentation) 
 * to insert a new user, directly, no User object, no "DAO" yet. 
 * This brute force starter program follows the idea of using 
 * all the pieces of the needed technology as soon as possible.  
 * Elegance can wait a bit.
 * 
 * Register.java should accept database information the way SystemTest does, but it has no input file. 
 * Don’t take any user input for the information about the new user but rather just invent values in the program. 
 * Note that you can run Register using the already existent scripts runOnH2, runOnOracle, and runOnMysql.
 */
package cs636.music.presentation;

import static cs636.music.dao.DBConstants.USER_TABLE;
import static cs636.music.dao.DBConstants.SYS_TABLE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import static cs636.music.dao.DBConstants.*;

public class Register
{
	private static Connection connection;

	/**
	 * Insert user data into user
	 * 
	 * @param first first_name
	 * @param last  last_name
	 * @param email email_address
	 * @throws SQLException
	 */
	public static void registerUser(String first, String last, String email) throws SQLException {
		/**
		 * CREATE TABLE site_user ( 
		 * user_id INT NOT NULL, 
		 * firstname VARCHAR(50) NOT NULL, 
		 * lastname VARCHAR(50) NOT NULL, 
		 * email_address VARCHAR(50) NOT NULL,
		 * ...
		 * UNIQUE(email_address), 
		 * PRIMARY KEY (user_id) 
		 * );
		 */
		// COMMON PROBLEM WITH EMAIL, and strings in general:
		// https://stackoverflow.com/questions/42186938/sql-syntax-error-mariadb-server-version-for-the-right-syntax-to-use-near-hotma
		Statement stmt = connection.createStatement();
		try {
			if (findUser(email))
			{
				System.out.println("User with email: '" + email + "' already exist!");
			}
			else
			{
				long userID = getNextUserID();
				String sqlString = "insert into " + USER_TABLE + 
				" (user_id, firstname, lastname, email_address) values ("
				+ userID + ", " + "'"+first+"'" + ", "
				+ "'" +last+ "'" + ", " + "'" +email+ "'" + ") ";
				stmt.execute(sqlString);
				System.out.println("SQL code: " + sqlString);
				System.out.println("Register user complete, exiting...");
			}
		} finally {
			stmt.close();
		}
	}

	/**
	 * Get the available user id 
	 * @return the line item id available 
	 * @throws SQLException
	 */
	private static long getNextUserID() throws SQLException
	{
		int nextLID;
		Statement stmt = connection.createStatement();
		try {
			ResultSet set = stmt.executeQuery(" select user_id from " + SYS_TABLE);
			set.next();
			nextLID = set.getInt("user_id");
		} finally {
			stmt.close();
		}
		advanceUserID(); // the id has been taken, so set +1 for next one
		return nextLID;
	}

	/**
	 * Increase user_id by 1 in the system table
	 * @throws SQLException
	 */
	private static void advanceUserID() throws SQLException
	{
		Statement stmt = connection.createStatement();
		try {
			stmt.executeUpdate(" update " + SYS_TABLE + " set user_id = user_id + 1");
		} finally {
			stmt.close();
		}
	}

	/**
	 * find user if it already exist
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public static Boolean findUser(String email) throws SQLException {
		Statement stmt = connection.createStatement();
		try {
			ResultSet set = stmt.executeQuery("select user_id from " + USER_TABLE + " where email_address = " + "'"+email+"'");
			if (set.next()){ // if the result is not empty
				set.close();
				return true;
			}
		} finally {
			stmt.close();
		}
		return false;
	}
	
	// borrowed from DbDAO.java
	public static void connect(String dbUrl, String usr, String passwd) throws SQLException {
		if (dbUrl == null) {
			System.out.println("DbDAO constructor: replacing null dbUrl with " + H2_URL);
			dbUrl = H2_URL; // default to H2, an embedded DB
			usr = "test";
			passwd = "";
		} else {
			System.out.println("DbDAO constructor called with " + dbUrl);
		}

		// Although simple JDBC apps no longer need Class.forName lookups, we are
		// using a jar with all three drivers in it and this confuses the
		// automatic driver location by JDBC. So we do it the old way.
		String dbDriverName;
		if (dbUrl.contains("mysql"))
			dbDriverName = MYSQL_DRIVER;
		else if (dbUrl.contains("oracle"))
			dbDriverName = ORACLE_DRIVER;
		else if (dbUrl.contains("h2"))
			dbDriverName = H2_DRIVER;
		else
			throw new SQLException("Unknown DB URL " + dbUrl);

		try {
			Class.forName(dbDriverName); // as done with JDBC before v4
		} catch (Exception e) {
			System.out.println("can't find driver " + dbDriverName);
		}
		connection = DriverManager.getConnection(dbUrl, usr, passwd);
	}

	public static void main(String[] args) {
		String dbUrl = null;
		String usr = null;
		String pw = null;
		String first = "Run";
		String last = "Lin";
		String email = "runlin@umb.edu";

		if (args.length == 0) {
			// handle H2
		}
		else if (args.length == 3) {
			dbUrl = args[0];
			usr = args[1];
			pw = args[2];
		} else {
			System.out.println("usage:java <dbURL> <user> <passwd> ");
			return;
		}
		System.out.println("dbUrl : " + dbUrl);
		System.out.println("usr : " + usr);
		System.out.println("pw : " + pw);
		/**
		 * Don’t take any user input for the information about the new user but rather
		 * just invent values in the program.
		 */
		try {
			connect(dbUrl, usr, pw);
			//connection = DriverManager.getConnection(dbUrl, usr, pw);
			registerUser( first, last, email );
		} catch (Exception e) {
			System.out.println("Error in registering user.");
			e.printStackTrace();
		}
	}
}
