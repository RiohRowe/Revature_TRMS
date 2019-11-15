package p1.webap.repository;

import p1.webap.model.Employee;

public interface EmployeeRepository 
{
	int CreateEmployee(Employee emp);
		
	int[] ResetReimbursementCheck();
	void updateEmployee(Employee emp);
	
	Employee getEmployeeByUsername(String uname);
	Employee getEmployeeByID(int eID);
	Employee[] getEmployeesByLOL(int level);
}
