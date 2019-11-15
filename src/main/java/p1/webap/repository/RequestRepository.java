package p1.webap.repository;

import p1.webap.model.AdditionalInfo;
import p1.webap.model.Request;

public interface RequestRepository 
{
	void createRequest(Request req);
	
	AdditionalInfo askForMoreInfo(String request, int requestID, int senderID, int RecipiantID);
	void respondToMoreInfoRequest(int aiID, String textResponse, String fileResponse);
	void changeRefundAmount(int requestID, float valueIncrease, String reason, int submitterID, int bencoID);
	void bencoApproves(int requestID, int bencoID);
	//Employee Accepts New Reimbursement Amount
	void eANRA(int requestID, int employeeID);
	void individualApproves(int requestID, int employeeID);
	int[] ApprovalTimeout(int[] benco);
	void denyRequest(int requestID, String denialReason, int employeeID);
	
	Request getRequestByID(int ID);
	int[] getRequestsIMadeID(int eID);
	int[] getRequestsINeedtoApproveID(int eID);
	int[] getRequestsThatRequireMoreID(int eID);
	int[] getRequestsICanAccessID(int eID);
	int[] getAllRequestsID();
	Request[] getRequestsByIDs(int[] ID);
	
	void saveRequest(Request r);
}
