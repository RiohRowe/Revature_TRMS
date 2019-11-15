package p1.webap.DBinterface;

import p1.webap.model.Employee;
import p1.webap.model.Request;
import p1.webap.service.DBService;

public class DBServiceTester 
{
	public static void main(String[] args) 
	{
		DBService ds = new DBService();
		Request[] allrequests = ds.getAllRequests();
		for(Request r : allrequests)
		{
			System.out.println(r.getRequestId());
		}
		
		Employee[] basics = ds.getEmployeesByLOL(0);
		Employee[] supervisors = ds.getEmployeesByLOL(1);
		Employee[] departmentHeads = ds.getEmployeesByLOL(2);
		Employee[] benefitCoordinators = ds.getEmployeesByLOL(3);
		
		System.out.println("All Basics:");
		for(Employee b: basics)
		{
			System.out.println(b.getEmployeeId());
		}
		System.out.println("All Supervisors:");
		for(Employee s: supervisors)
		{
			System.out.println(s.getEmployeeId());
		}
		System.out.println("All Department Heads:");
		for(Employee d: departmentHeads)
		{
			System.out.println(d.getEmployeeId());
		}
		System.out.println("All BenefitCoordinators:");
		for(Employee bc: benefitCoordinators)
		{
			System.out.println(bc.getEmployeeId());
		}
	}
}
