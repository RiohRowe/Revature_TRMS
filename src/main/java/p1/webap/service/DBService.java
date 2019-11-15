package p1.webap.service;


import p1.webap.model.AdditionalInfo;
import p1.webap.model.ApprovalChain;
import p1.webap.model.Employee;
import p1.webap.model.Request;
import p1.webap.repository.EmployeeRepositoryImpl;
import p1.webap.repository.RequestRepositoryImpl;

public class DBService 
{
	public void SaveEmployee(Employee emp)
	{
		emp.setEmployeeId(new EmployeeRepositoryImpl().CreateEmployee(emp));
	}
	
	public int[] ResetReimbursements()
	{
		return new EmployeeRepositoryImpl().ResetReimbursementCheck();
	}
	
	public void updateEmployee(Employee emp)
	{
		new EmployeeRepositoryImpl().updateEmployee(emp);
	}
	
	public Employee getEmployeeByUsername(String uname)
	{
		return new EmployeeRepositoryImpl().getEmployeeByUsername(uname);
	}
	
	public Employee getEmployeeByID(int id)
	{
		return new EmployeeRepositoryImpl().getEmployeeByID(id);
	}
	
	public Employee[] getEmployeesByLOL(int level)
	{
		return new EmployeeRepositoryImpl().getEmployeesByLOL(level);
	}
	
	public void saveRequest(Request r)
	{
		new RequestRepositoryImpl().saveRequest(r);
	}
	public void askForMoreInfo(Request r, String request, ApprovalChain sender, ApprovalChain Recipiant)
	{
		AdditionalInfo[] aia = r.getAdditionalInfo();
		if(aia == null)
		{
			aia = new AdditionalInfo[1];
		}
		else
		{
			aia = new AdditionalInfo[aia.length+1];
			for(int i = 0; i < r.getAdditionalInfo().length; i++)
			{
				aia[i] = r.getAdditionalInfo()[i];
			}
		}
		aia[aia.length-1] = new RequestRepositoryImpl().askForMoreInfo(request, r.getRequestId(), sender.getEmployee_id(), Recipiant.getEmployee_id());
	}
	
	public void respondToMoreInfoRequest(AdditionalInfo ai, String textResponse, String fileResponse)
	{
		new RequestRepositoryImpl().respondToMoreInfoRequest(ai.getAdditionalInfoId(), textResponse, fileResponse);
		ai.setText_info(textResponse);
		ai.setFile_info(fileResponse);
		ai.setIsanswered(true);
	}
		
	public void changeRefundAmount(Request r, Employee e, float newvalue, String reason, int bencoID)
	{
		new RequestRepositoryImpl().changeRefundAmount(r.getRequestId(), r.getProjected_reimbursement() - newvalue, reason, r.getEmployeeId(), bencoID);
		r.setReimbursement(newvalue);
		e.setTotalReimbursment(e.getTotalReimbursment()+r.getProjected_reimbursement()- newvalue);
		r.getTopApprovalChain().setApproved(true);
		r.getbottomApprovalChain().setApproved(false);
	}
	
	public void bencoApproves(Request r, int bencoID)
	{
		new RequestRepositoryImpl().bencoApproves(r.getRequestId(),bencoID);
		r.setApproved(true);
		r.getTopApprovalChain().setApproved(true);
	}
	
	public void eANRA(Request r)
	{
		//Employee Accepts New Reimbursement Amount
		new RequestRepositoryImpl().eANRA(r.getRequestId(), r.getEmployeeId());
		r.getbottomApprovalChain().setApproved(true);
		r.setApproved(true);
	}
	
	public void individualApproves(Request r, Employee e)
	{
		if(r.getTopApprovalChain().getEmployee_id() == e.getEmployeeId())
		{
			bencoApproves(r,e.getEmployeeId());
		}
		else if(r.getbottomApprovalChain().getEmployee_id() == e.getEmployeeId())
		{
			eANRA(r);
		}
		else
		{
			new RequestRepositoryImpl().individualApproves(r.getRequestId(), e.getEmployeeId());
			r.getApprovalChainByEmployeeID(e.getEmployeeId()).setApproved(true);
		}
	}
	
	public void ApprovalTimeout(Request[] requests, int[] bencoID)
	{
		int[] eIDs = new RequestRepositoryImpl().ApprovalTimeout(bencoID);
		for(Request r : requests)
		{
			for(int eID : eIDs)
			{
				if(r.getApprovalChainByEmployeeID(eID).getEmployee_id() == r.getTopApprovalChain().getEmployee_id())
				{
					//FLAG EMAIL SUPERIOR
				}
				else
				{
					r.getApprovalChainByEmployeeID(eID).setApproved(true);
				}
				r.getApprovalChainByEmployeeID(eID).setHasTimedOut(true);
			}
		}
	}
	
	public void denyRequest(Request r, String denialReason, int employeeID)
	{
		new RequestRepositoryImpl().denyRequest(r.getRequestId(), denialReason, employeeID);
		r.setApproved(false);
		r.setDenialReason(denialReason);
		r.getApprovalChainByEmployeeID(employeeID).setApproved(false);
	}
	
	public Request getRequestByID(int requestID)
	{
		return new RequestRepositoryImpl().getRequestByID(requestID);
	}
	
	public Request[] getRequestsIMade(int eID)
	{
		return new RequestRepositoryImpl().getRequestsByIDs( 
					new RequestRepositoryImpl().getRequestsIMadeID(eID));
	}
	
	public Request[] getRequestsINeedToApprove(int eID)
	{
		return new RequestRepositoryImpl().getRequestsByIDs( 
					new RequestRepositoryImpl().getRequestsINeedtoApproveID(eID));
	}
	
	public Request[] getRequestsThatRequireMore(int eID)
	{
		return new RequestRepositoryImpl().getRequestsByIDs( 
					new RequestRepositoryImpl().getRequestsThatRequireMoreID(eID));
	}
	
	public Request[] getRequestsICanAccess(int eID)
	{
		return new RequestRepositoryImpl().getRequestsByIDs( 
					new RequestRepositoryImpl().getRequestsICanAccessID(eID));
	}
	
	public Request[] getAllRequests()
	{
		return new RequestRepositoryImpl().getRequestsByIDs( 
					new RequestRepositoryImpl().getAllRequestsID());
	}
	
	public Request[] getRequestsByIDArray(int[] reqIDs)
	{
		return new RequestRepositoryImpl().getRequestsByIDs(reqIDs);
	}
}
