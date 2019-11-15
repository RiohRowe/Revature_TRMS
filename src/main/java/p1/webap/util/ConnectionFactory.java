package p1.webap.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory 
{
	private static Connection conn;
	
	public static Connection getConnection()
	{
		try 
		{
			Class.forName("org.postgresql.Driver");
			System.out.println(System.getenv("databaseurl"));
			conn = DriverManager.getConnection(
					System.getenv("databaseurl"),
					System.getenv("databaseusername"),
					System.getenv("databasepassword")
			);
			conn.setSchema("Project1");
			System.out.println(conn.getSchema());
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
		return conn;
		
	}
}
