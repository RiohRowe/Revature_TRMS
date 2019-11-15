package p1.webap.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// Handles exceptions raised from closing the connection with the DB.
public class ConnectionClosers 
{
	// Ends a secured connection with the DataBase. //
	public static void closeConnection(Connection conn) 
	{
		try 
		{
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	// Closes the SQL Query holder provided by the secure connection. //
	public static void closeStatement(Statement stmt) 
	{
		try 
		{
			stmt.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	// Closes the SQL Results holder provided by the stmt. //
	public static void closeResultSet(ResultSet set) 
	{
		try 
		{
			set.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
