package cs636.music.dao;

/**
 * @author Chung-Hsien (Jacky) Yu
 * 
 *         Database schema related constants.
 */
public interface DBConstants {
	public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	public static final String H2_DRIVER = "org.h2.Driver";
	public static final String H2_URL = "jdbc:h2:~/test-music";

	public static final String DOWNLOAD_TABLE = "download";
	public static final String SYS_TABLE = "music_sys_tab";
	public static final String INVOICE_TABLE = "invoice";
	public static final String LINEITEM_TABLE = "lineitem";
	public static final String PRODUCT_TABLE = "product";
	public static final String TRACK_TABLE = "track";
	public static final String USER_TABLE = "site_user";
	public static final String ADMIN_TABLE = "userpass";
}
