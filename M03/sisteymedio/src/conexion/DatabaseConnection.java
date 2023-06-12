package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	// Localhost
    private static final String DB_URL = "jdbc:mysql://localhost/sieteymediodb?serverTimezone=UTC";
    
    // Azure
    // private static final String DB_URL="jdbc:mysql://sieteymediodb.mysql.database.azure.com:3306/sieteymediodb?useSSL=true";

    
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "P@ssw0rd";

    public static Connection getConnection() throws SQLException {
    	
    	
    	
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        
        
    }}

