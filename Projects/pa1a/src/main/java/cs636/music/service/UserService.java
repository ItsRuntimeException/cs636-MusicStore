package cs636.music.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import cs636.music.dao.UserDAO;
import cs636.music.dao.DbDAO;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;

/**
 * 
 * Provide admin level services to user app through accessing DAOs 
 * @author Chung-Hsien (Jacky) Yu
 */
public class UserService {
	
	private DbDAO db;
	private UserDAO userDb;
	
	/**
	 * construct a admin service provider 
	 * @param dbDao
	 */
	public UserService(DbDAO dbDao, UserDAO UserDao /* TODO add other Dao's here */) {
		db = dbDao;	
		userDb = UserDao;
	}
	
	/**
	 * Clean all user table, not product and system table to empty
	 * and then set the index numbers back to 1
	 * @throws ServiceException
	 */
	public void initializeDB()throws ServiceException {
		try {
			db.initializeDb();
		} catch (SQLException e)
		{
			throw new ServiceException("Can't initialize DB: ", e);
		}	
	}
	
	/*
	public void registerUser(String email) throws ServiceException, SQLException
	{
    	System.out.println("registerUser called, email = " +email);
    	userDb.insertUser(email);
	}
	*/
}
