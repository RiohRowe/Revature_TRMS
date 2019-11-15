package p1.webap.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import p1.webap.model.Employee;
import p1.webap.util.ConnectionClosers;
import p1.webap.util.ConnectionFactory;


public class EmployeeRepositoryImpl implements EmployeeRepository {

	public int CreateEmployee(Employee emp) 
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set=null;
		final String SQL = "INSERT INTO employee Values (default, ?, ?, ?, ?,"+
							" now(), ?, ?, ?)";
		int employeeID = 0;
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, emp.getUsername());
			stmt.setString(2, emp.getPassword());
			stmt.setString(3, emp.getFullName());
			stmt.setFloat(4, emp.getTotalReimbursment());
			stmt.setInt(5, emp.getManagerId());
			stmt.setInt(6, emp.getDepartmentHeadId());
			stmt.setInt(7, emp.getLevelOfLeadership());

			set = stmt.executeQuery();
			
			while(set.next())
			{
				employeeID = set.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		return employeeID;
	}

	public int[] ResetReimbursementCheck() 
	{
		ArrayList<Integer> employeeID = new ArrayList<Integer>();
		int[] eIDArray = null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet set=null;
		final String SQL = "update only employee set total_reimbursement = "+
							"1000, reimbursement_reset_date = "+
							"reimbursement_reset_date + '1 year' where "+
							"reimbursement_reset_date + '1 year' <= "+
							"current_date returning employee_id";
		
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
			set = stmt.executeQuery(SQL);
					
			while(set.next())
			{
				employeeID.add(set.getInt(1));
			}
			eIDArray = new int[employeeID.size()];
			for(int i = 0; i < employeeID.size(); ++i)
			{
				eIDArray[i]=employeeID.get(i);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		return eIDArray;
	}

	public void updateEmployee(Employee emp) 
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		final String SQL = "update only employee set uname = ?, pword = ?, "+
							"full_name = ?, total_reimbursement = ?, "+
							"reimbursement_reset_date = ?, manager_id = ?, "+
							"department_head_id = ?, level_of_leadership = ?)";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, emp.getUsername());
			stmt.setString(2, emp.getPassword());
			stmt.setString(3, emp.getFullName());
			stmt.setFloat(4, emp.getTotalReimbursment());
			stmt.setDate(5, emp.getReimbursementResetDate());
			stmt.setInt(6, emp.getManagerId());
			stmt.setInt(7, emp.getDepartmentHeadId());
			stmt.setInt(8, emp.getLevelOfLeadership());

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
		}
	}

	public Employee getEmployeeByUsername(String uname) 
	{
		Employee emp = null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set=null;
		final String SQL = "SELECT * FROM employee where uname = ?";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, uname);

			set = stmt.executeQuery();
			
			while(set.next())
			{
				emp = new Employee(
					set.getInt(1),
					set.getString(2),
					set.getString(3),
					set.getString(4),
					set.getFloat(5),
					set.getDate(6),
					set.getInt(7),
					set.getInt(8),
					set.getInt(9)
				);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		return emp;
	}

	public Employee getEmployeeByID(int eID) 
	{
		Employee emp = null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set=null;
		final String SQL = "SELECT * FROM employee where employee_id = ?";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, eID);

			set = stmt.executeQuery();
			
			while(set.next())
			{
				emp = new Employee(
					set.getInt(1),
					set.getString(2),
					set.getString(3),
					set.getString(4),
					set.getFloat(5),
					set.getDate(6),
					set.getInt(7),
					set.getInt(8),
					set.getInt(9)
				);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		return emp;
	}

	public Employee[] getEmployeesByLOL(int level) 
	{
		ArrayList<Employee> empAL = new ArrayList<Employee>();
		Employee[] empA = null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set=null;
		final String SQL = "SELECT * FROM employee where level_of_leadership = ?";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, level);
			System.out.println("level = "+level);
			set = stmt.executeQuery();
			
			while(set.next())
			{
				empAL.add(
					new Employee(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getFloat(5),
						set.getDate(6),
						set.getInt(7),
						set.getInt(8),
						set.getInt(9)
					)
				);
			}
			empA = new Employee[empAL.size()];
			for(int i = 0; i < empAL.size(); ++i)
			{
				empA[i] = empAL.get(i);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		return empA;
	}
}
