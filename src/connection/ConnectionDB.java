package connection;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDB {
	public static Connection GetConnection()
	{		 
    	try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;user=sa;password=123456;databaseName=PSICOLOGUES");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	return null;    	
	}
	
	public static void CloseConnection(CallableStatement cstmt, ResultSet rs)
	{
        if (rs != null) { 
        	try { rs.close(); } 
        	catch (SQLException ex) { }
        }
        if (cstmt != null) {
            try { cstmt.close(); } 
            catch (SQLException ex) { }
        }
	}
}
