package DvdPackage;

import java.sql.*;

/**
 * Class to get connection to the javadb database. It is parent of Person Class,
 * so it is indirect parent for owner and customer classes. Because this class
 * inherits from Services class, so owner and customer can also access the
 * Services class methods and use them.
 * 
 * @author Shadiar's group
 *
 */
public abstract class DBconnection extends Services {

	protected static Connection conn;
	protected Statement stmt;
	protected ResultSet result;

	/**
	 * connect to javadb database which is already created beforehand
	 */
	public static void connectToDB() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/javadb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
