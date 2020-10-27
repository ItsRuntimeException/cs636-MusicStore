
package cs636.music.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import cs636.music.domain.Product;
import cs636.music.domain.Track;

import static cs636.music.dao.DBConstants.*;

/*
CREATE TABLE product(
    product_id INT NOT NULL,
    product_code VARCHAR(10) NOT NULL,
    product_description VARCHAR(100) NOT NULL,
    product_price DECIMAL(10,2) NOT NULL,  
    UNIQUE (product_code),   
    PRIMARY KEY (product_id)
);
*/

public class ProductDAO {

	private Connection connection;

	public ProductDAO (DbDAO db) {
		connection = db.getConnection();
	}
    
    // page 667 Murach JSP
    public Product findProductByCode(String product_code) throws SQLException {
        Statement stmt = connection.createStatement();
        Set<Track> trackSet = new HashSet<Track>();
        Product p = null;
        Track t = null;
        // implement track as well.
        String sqlString = "select * from "+ 
                            PRODUCT_TABLE+" p, "+ 
                            TRACK_TABLE+" t "+
                            " where p.product_code = '"+product_code+"'"+ 
                            " and p.product_id = t.product_id";
        try {
            ResultSet rs = stmt.executeQuery(sqlString);
            if (rs.next()){ // if the result is not empty
                p = new Product();
                p.setId(rs.getLong("product_id"));
                p.setCode(rs.getString("product_code"));
                p.setDescription(rs.getString("product_description"));
                p.setPrice(rs.getBigDecimal("product_price"));
                // tracks
                t = new Track();
                t.setId(rs.getLong("track_id"));
                t.setProduct(p);
                t.setTrackNumber(rs.getInt("track_number"));
                t.setTitle(rs.getString("title"));
                t.setSampleFilename(rs.getString("sample_filename"));
                trackSet.add(t);
                // has more?
                while (rs.next()) {
                    t.setId(rs.getLong("track_id"));
                    t.setProduct(p);
                    t.setTrackNumber(rs.getInt("track_number"));
                    t.setTitle(rs.getString("title"));
                    t.setSampleFilename(rs.getString("sample_filename"));
                    trackSet.add(t);
                }
                p.setTracks(trackSet);
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            stmt.close();
        }
        return p;
    }
    
    public Product findProductByPID(long product_id) throws SQLException {
        Statement stmt = connection.createStatement();
        Set<Track> trackSet = new HashSet<Track>();
        Product p = null;
        Track t = null;
        String sqlString = "select * from "+
                            PRODUCT_TABLE+" p, "+
                            TRACK_TABLE+" t "+
                            " where p.product_id = "+product_id+
                            " and p.product_id = t.product_id";
        try {
            ResultSet rs = stmt.executeQuery(sqlString);
            if (rs.next()){ // if the result is not empty
                p = new Product();
                p.setId(rs.getLong("product_id"));
                p.setCode(rs.getString("product_code"));
                p.setDescription(rs.getString("product_description"));
                p.setPrice(rs.getBigDecimal("product_price"));
                // tracks
                t = new Track();
                t.setId(rs.getLong("track_id"));
                t.setProduct(p);
                t.setTrackNumber(rs.getInt("track_number"));
                t.setTitle(rs.getString("title"));
                t.setSampleFilename(rs.getString("sample_filename"));
                trackSet.add(t);
                // has more?
                while (rs.next()) {
                    t.setId(rs.getLong("track_id"));
                    t.setProduct(p);
                    t.setTrackNumber(rs.getInt("track_number"));
                    t.setTitle(rs.getString("title"));
                    t.setSampleFilename(rs.getString("sample_filename"));
                    trackSet.add(t);
                }
                p.setTracks(trackSet);
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            stmt.close();
        }
        return p;
    }

    /**
	 * find all products by given a product id
     * referenced from DownloadDAO.java
	 * @return all products in a Set
	 * @throws SQLException
	 */
	public Set<Product> findAllProducts() throws SQLException {
		Set<Product> productSet = new HashSet<Product>();
		Statement stmt = connection.createStatement();
		String sqlString = "select * from " + PRODUCT_TABLE;
		try {
			ResultSet rs = stmt.executeQuery(sqlString);
			while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getLong("product_id"));
                p.setCode(rs.getString("product_code"));
                p.setDescription(rs.getString("product_description"));
                p.setPrice(rs.getBigDecimal("product_price"));
				productSet.add(p);
            }
            rs.close();
		} catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
			stmt.close();
        }
        return productSet;
    }
    
    /**
	 * find all tracks given product
	 * @return all tracks in a Set
	 * @throws SQLException
	 */
	public Set<Track> findAllTracks(Product p) throws SQLException {
        return p.getTracks();
    }

    /**
	 * find track by track_id
	 * @param trackId given track_id
	 * @return the track with given param trackId
	 * @throws SQLException
	 */
	public Track findTrackByTID(long trackId) throws SQLException {
        Statement stmt = connection.createStatement();
        Track t = null;
        String sqlString =  " select * from " + 
			                PRODUCT_TABLE+" p, " +
			                TRACK_TABLE+" t "+		
			                " where t.track_id = "+trackId+ 
                            " and t.product_id = p.product_id";
		try 
		{
			ResultSet rs = stmt.executeQuery(sqlString);
			if (rs.next()){ // if the result is not empty
				Product p = findProductByPID(rs.getInt("product_id"));
				if (p != null) {
					t = p.findTrackbyID((int)trackId);
                }
                rs.close();
            }
		} catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
			stmt.close();
        }
        return t;
    }
    
}