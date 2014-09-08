package myJFrameTest;
// This one works!
import java.sql.*;
import java.util.Properties;

public class database_access2 {
	
	public static Connection openConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Properties properties = new Properties();
		properties.put("user", "root");
		properties.put("password", "Spamalot");
		properties.put("characterEncoding", "ISO-8859-1");
		properties.put("useUnicode", "true");
		String url = "jdbc:mysql://localhost:3306/mysql";
		
		//Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection c = DriverManager.getConnection(url,properties);
		return c;
	}
	
	public static String getFirstName(Connection conn, int id) throws SQLException {
		Statement st = conn.createStatement();
		String sql = "SELECT firstname FROM customer WHERE custID = " + id;
		ResultSet rs = st.executeQuery(sql);
		String theFirstName = "unknown";
		try{
			while(rs.next()){
				theFirstName = rs.getString(1);
			}
		} finally {
			rs.close();
		}
		return theFirstName;
	}
	
	public static String getLastName(Connection conn, int id) throws SQLException {
		Statement st = conn.createStatement();
		String sql = "SELECT lastname FROM customer WHERE custID = " + id;
		ResultSet rs = st.executeQuery(sql);
		String theLastName = "unknown";
		try{
			while(rs.next()){
				theLastName = rs.getString(1);
			}
		} finally {
			rs.close();
		}
		return theLastName;
	}
	
	public static int getID(Connection conn, String firstName, String lastName) throws SQLException {
		PreparedStatement sql = conn.prepareStatement("SELECT custID FROM customer WHERE lastname = ? AND firstname = ? ");
		sql.setString(1,lastName);
		sql.setString(2, firstName);
		ResultSet rs = sql.executeQuery();
		int theID = 0;
		try{
			while(rs.next()){
				theID = rs.getInt(1);
			}
		} finally {
			rs.close();
		}
		return theID;
	}
	
	public static int checkID(Connection conn, int id) throws SQLException {
		PreparedStatement sql = conn.prepareStatement("SELECT * FROM customer WHERE custID = ? ");
		sql.setInt(1,id);
		ResultSet rs = sql.executeQuery();
		int boolID = 0;
		try{
			while(rs.next()){
				boolID = rs.getInt(1);
			}
		} finally {
			rs.close();
		}
		return boolID;
	}
	
	public static int getMaxCustID(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		String sql = "SELECT MAX(custID) AS LargestID FROM customer";
		ResultSet rs = st.executeQuery(sql);
		int theLargestID = 0;
		try{
			while(rs.next()){
				theLargestID = rs.getInt(1);
			}
		} finally {
			rs.close();
		}
		return theLargestID;
	}
	
	public static void newEntry(Connection conn, int id, String newFirstName, String newLastName) throws SQLException {
//		Statement st = conn.createStatement();
//		String sql = "INSERT INTO customer (custID,firstname,lastname) VALUES (6,'Elisabeth','Mueller')";
		String sql = "INSERT INTO customer (custID,firstname,lastname) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.setString(2, newFirstName);
		ps.setString(3, newLastName);
		try{
//			st.executeUpdate(sql); 
			ps.execute();
	    } 
	    catch (Exception ex){ 
	    	// error executing SQL statement 
	    	System.out.println("Error: " + ex); 
	    }
	}

	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection conn = openConnection();
		int maxID = getMaxCustID(conn);
		int newID = maxID + 1;
		newEntry(conn,newID,"Maxwell","Smart");
		int id = newID;
		String theFirstName = getFirstName(conn,id);
		System.out.println(theFirstName);
	}

}
