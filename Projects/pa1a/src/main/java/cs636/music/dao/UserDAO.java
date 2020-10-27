
package cs636.music.dao;

import static cs636.music.dao.DBConstants.USER_TABLE;
import static cs636.music.dao.DBConstants.SYS_TABLE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import cs636.music.dao.DbDAO;
import cs636.music.dao.UserDAO;
import cs636.music.domain.User;

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
	 * check login user name and password
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public Boolean findUser(String email) throws SQLException {
		Statement stmt = connection.createStatement();
		try {
			ResultSet set = stmt.executeQuery(" select user_id from " + USER_TABLE +
					" where email_address = '" + email + "')");
			if (set.next()){ // if the result is not empty
				set.close();
				return true;
			}
		} finally {
			stmt.close();
		}
		return false;
	}

    public void insertUser(String email) throws SQLException
    {
		System.out.println("insertUser called, email = "+email);
		
	}
	
}