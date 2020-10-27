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

//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.DriverManager;
//import java.sql.ResultSet;

import cs636.music.dao.DbDAO;
import cs636.music.dao.UserDAO;
import cs636.music.domain.User;

public class Register
{
	private DbDAO db; // Connection connection = DriverManager.getConnection(dbUrl, usr, passwd);
	private UserDAO userDB;

	/**
	 * Insert user data into user
	 * 
	 * @param first first_name
	 * @param last  last_name
	 * @param email email_address
	 * @throws SQLException
	 */
	public Register(String dbUrl, String usr, String passwd) throws Exception {
		// Initialize Connection
		db = new DbDAO(dbUrl, usr, passwd);
		userDB = new UserDAO(db);
	}

	public void registerUser(String first, String last, String email) throws Exception {
		try {
			User user = new User(first, last, email); //cs636.music.domain.User;
			userDB.insertUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
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
			Register regObj = new Register(dbUrl, usr, pw);
			regObj.registerUser(first, last, email);
			System.out.println("User: " + "["+first+" "+last+", "+email+ "]" + " has been registered.");
		} catch (Exception e) {
			System.out.println("Error in registering user.");
			e.printStackTrace();
		}
	}
}
