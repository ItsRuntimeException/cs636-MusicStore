
package cs636.music.dao;

import static cs636.music.dao.DBConstants.SYS_TABLE;
import static cs636.music.dao.DBConstants.DOWNLOAD_TABLE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import cs636.music.domain.Download;

/**
 * 
 * Access Download table through this class. 
 * @author Chung-Hsien (Jacky) Yu
 */
public class DownloadDAO {
	
	private Connection connection;
//  Implement UserDAO and ProductDAO, uncomment the following
	private UserDAO userdb;
	private ProductDAO proddb;

	/**
	 * An Data Access Object for Download table
	 * @param db the database connection
	 * @param user_db using the site user table 
	 * @throws SQLException
	 */
	// Implement UserDAO and ProductDAO
	public DownloadDAO(DbDAO db, UserDAO user_db, ProductDAO product_db ){
		connection = db.getConnection();
		userdb = user_db;
		proddb = product_db;
	}
	
	/**
	 * Increase download_id by 1 in the system table
	 * @throws SQLException
	 */
	private void advanceDownloadID() throws SQLException
	{
		Statement stmt = connection.createStatement();
		try {
			stmt.executeUpdate(" update " + SYS_TABLE
					+ " set download_id = download_id + 1");
		} finally {
			stmt.close();
		}
	}
	
	/**
	 * Get the available download id 
	 * @return the download id available 
	 * @throws SQLException
	 */
	private int getNextDownloadID() throws SQLException
	{
		int nextDID;
		Statement stmt = connection.createStatement();
		try {
			ResultSet set = stmt.executeQuery(" select download_id from " + SYS_TABLE);
			set.next();
			nextDID = set.getInt("download_id");
		} finally {
			stmt.close();
		}
		advanceDownloadID(); // the id has been taken, so set +1 for next one
		return nextDID;
	}
	
	/**
	 * insert a download history to download table
	 * @param download
	 * @throws SQLException
	 */
	public void insertDownload(Download dl) throws SQLException {
		Statement stmt = connection.createStatement();
		int dlID =  getNextDownloadID();
		dl.setDownloadId(dlID);
		try{
			String sqlString = "insert into "+ DOWNLOAD_TABLE + " values (" + dlID + 
			", " + dl.getUser().getId() +
			//this won't work: dl.getUser().getId() Referential 
			//integrity constraint violation: "CONSTRAINT_83C:
			//PUBLIC.DOWNLOAD FOREIGN KEY(USER_ID) 
			//REFERENCES PUBLIC.SITE_USER(USER_ID) (0)"; SQL statement:(..)
			
			", " + "current_timestamp, " +
			dl.getTrack().getId() + ")";
			stmt.execute(sqlString);
		} finally {
			stmt.close();
		}
	}

	/**
	 * find all downlaod history by given a download id
	 * @return all downlaod history in a Set
	 * @throws SQLException
	 */
	public Set<Download> findAllDownloads() throws SQLException {
		Download download=null;
		Set<Download> downloads = new HashSet<Download>();
		Statement stmt = connection.createStatement();
		String sqlString = "select * from "+ DOWNLOAD_TABLE;
		try{
			ResultSet set = stmt.executeQuery(sqlString);
			while (set.next()){
				download = new Download();
				download.setDownloadId(set.getLong("download_id"));
				download.setDownloadDate(set.getTimestamp("download_date"));
				// Implement UserDAO and ProductDAO
				download.setUser(userdb.findUserByID(set.getLong("user_id")));
				download.setTrack(proddb.findTrackByTID(set.getLong("track_id")));
				downloads.add(download);
			}
		} finally{
			stmt.close();
		}
		return downloads;
	}
}
