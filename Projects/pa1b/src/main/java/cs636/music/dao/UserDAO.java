
package cs636.music.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs636.music.dao.DbDAO;
import cs636.music.domain.User;

import static cs636.music.dao.DBConstants.*;

/**
 * 
 * Access admin related tables through this class.
 * @author 
 */
public class UserDAO {

	private Connection connection;

	public UserDAO (DbDAO db) {
		connection = db.getConnection();
	}

	/**
	 * find user if it already exist
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public User findUserByEmail(String email) throws SQLException {
		Statement stmt = connection.createStatement();
		User user = null;
		try {
			ResultSet set = stmt.executeQuery("select * from " + USER_TABLE + " where email_address = " + "'"+email+"'");
			if (set.next()){ // if the result is not empty
				user = new User();
				user.setId(set.getLong("user_id"));
				user.setFirstname(set.getString("firstname"));
				user.setLastname(set.getString("lastname"));
				user.setEmailAddress(set.getString("email_address"));
				set.close();
			}
		} finally {
			stmt.close();
		}
		return user;
	}

    public void insertUser(User user) throws SQLException
    {
		//System.out.println("insertUser called, email = "+email);
		Statement stmt = connection.createStatement();
		String email = user.getEmailAddress();
		String first = user.getFirstname();
		String last = user.getLastname();
		try {
			long userID = getNextUserID();
			user.setId(userID);
			String sqlString = "insert into " + USER_TABLE + 
			" (user_id, firstname, lastname, email_address) values ("
			+ user.getId() + ", " + "'"+first+"'" + ", "
			+ "'" +last+ "'" + ", " + "'" +email+ "'" + ") ";
			stmt.execute(sqlString);
			System.out.println("SQL code: " + sqlString);
			System.out.println("Register user complete, exiting...");
		} finally {
			stmt.close();
		}
	}

/**
	 * Get the available user id 
	 * @return the line item id available 
	 * @throws SQLException
	 */
	private long getNextUserID() throws SQLException
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
	private void advanceUserID() throws SQLException
	{
		Statement stmt = connection.createStatement();
		try {
			stmt.executeUpdate(" update " + SYS_TABLE + " set user_id = user_id + 1");
		} finally {
			stmt.close();
		}
	}

	public User findUserByID (long user_id) throws SQLException {
		Statement stmt = connection.createStatement();
		User user = null;
		try {
			String sqlString = "select * from " + 
								USER_TABLE + 
								" where user_id = " + user_id;

			ResultSet set = stmt.executeQuery(sqlString);
			if (set.next()){ // if the result is not empty
				user = new User();
				user.setId(set.getInt("user_id"));
				user.setFirstname(set.getString("firstname"));
				user.setLastname(set.getString("lastname"));
				user.setEmailAddress(set.getString("email_address"));
				set.close();
			}
			else {
				System.out.println("User not found.");
				return null;
			}
		} finally {
			stmt.close();
		}
		return user;
	}
}