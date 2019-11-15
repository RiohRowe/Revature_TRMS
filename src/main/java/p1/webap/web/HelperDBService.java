package p1.webap.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import p1.webap.model.Employee;
import p1.webap.model.SessionObj;
import p1.webap.service.DBService;

public class HelperDBService 
{

	//Logger for debugging
	private static final Logger loge = LogManager.getLogger(HelperDBService.class);
	
	
	/*
	 * This Request Helper will be used to implement the 
	 * logic that determines what our Front Controller will 
	 * send back to the client side.
	 */
	public byte[] processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		loge.debug("In the Request Helper");
		
		
		
		String uri = request.getRequestURI().replaceAll("/P1/Views/api", "");
		loge.debug(uri);
		
		ObjectMapper mapper = new ObjectMapper();
		HttpSession ses = request.getSession(false);
		switch(uri)
		{
			//This endpoint returns a list of Basic Employees.
			case "/myrequests":
			{
				return mapper.writeValueAsBytes(new DBService().getRequestsIMade((Integer)ses.getAttribute("employeeID")));
			}
			case "/yourrequests":
			{
				return mapper.writeValueAsBytes(new DBService().getRequestsICanAccess((Integer)ses.getAttribute("employeeID")));
			}
			case "/allSupervisorEmployees":
			{
				return mapper.writeValueAsBytes(new DBService().getEmployeesByLOL(1));				
			}
			
			default:
				return mapper.writeValueAsBytes("No such endpoint");
		}
	}
	public SessionObj validateUser(String username, String password, HttpServletRequest req)
	{
		DBService dbs = new DBService();
		Employee[] basicEmployees = dbs.getEmployeesByLOL(0);
		Employee[] supervisors = dbs.getEmployeesByLOL(1);
		Employee[] departmentHeads = dbs.getEmployeesByLOL(2);
		Employee[] benefitCoordinators = dbs.getEmployeesByLOL(3);
		System.out.println("benefitCoordinators");
		System.out.println("Here1");
		SessionObj so = new SessionObj();
		for(Employee e: benefitCoordinators)
		{
			System.out.println("Upi = " + username + " U = " + e.getUsername() + "    Ppi = "+ password + " P = " + e.getPassword());
			if(e.getUsername().equals(username) && e.getPassword().contentEquals(password))
			{
				HttpSession ses = req.getSession();
				ses.setAttribute("LOL", 3);
				ses.setAttribute("username", username);
				ses.setAttribute("employeeID", e.getEmployeeId());
				so.employeeID = e.getEmployeeId();
				so.username = e.getUsername();
				so.lol = 3;
				return so;
			}
		}

		System.out.println("department Heads");
		for(Employee e: departmentHeads)
		{
			System.out.println("Upi = " + username + " U = " + e.getUsername() + "    Ppi = "+ password + " P = " + e.getPassword());
			if(e.getUsername().equals(username) && e.getPassword().contentEquals(password))
			{
				HttpSession ses = req.getSession();
				ses.setAttribute("LOL", 2);
				ses.setAttribute("username", username);
				ses.setAttribute("employeeID", e.getEmployeeId());
				so.employeeID = e.getEmployeeId();
				so.username = e.getUsername();
				so.lol = 2;
				return so;
			}
		}

		System.out.println("supervisors");
		for(Employee e: supervisors)
		{
			System.out.println("Upi = " + username + " U = " + e.getUsername() + "    Ppi = "+ password + " P = " + e.getPassword());
			if(e.getUsername().equals(username) && e.getPassword().contentEquals(password))
			{
				HttpSession ses = req.getSession();
				ses.setAttribute("LOL", 1);
				ses.setAttribute("username", username);
				ses.setAttribute("employeeID", e.getEmployeeId());
				so.employeeID = e.getEmployeeId();
				so.username = e.getUsername();
				so.lol = 1;
				return so;
			}
		}

		System.out.println("basicEmployees");
		for(Employee e: basicEmployees)
		{
			System.out.println("Upi = " + username + " U = " + e.getUsername() + "    Ppi = "+ password + " P = " + e.getPassword());
			if(e.getUsername().equals(username) && e.getPassword().contentEquals(password))
			{
				HttpSession ses = req.getSession();
				ses.setAttribute("LOL", 0);
				ses.setAttribute("username", username);
				ses.setAttribute("employeeID", e.getEmployeeId());
				so.employeeID = e.getEmployeeId();
				so.username = e.getUsername();
				so.lol = 0;
				return so;
			}
		}
		return null;
	}
}
