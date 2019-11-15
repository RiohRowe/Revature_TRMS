package p1.webap.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import p1.webap.model.AdditionalInfo;
import p1.webap.model.ApprovalChain;
import p1.webap.model.Event;
import p1.webap.model.Request;
import p1.webap.model.OptionalRequestInfo;
import p1.webap.util.ConnectionClosers;
import p1.webap.util.ConnectionFactory;

public class RequestRepositoryImpl implements RequestRepository {

	public void createRequest(Request req) 
	{
		int reqID = 0;
		int eventID = 0;
		int addInfID = 0;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set=null;
		final String SQLEvent = "insert into events values(default, ?, ?, ?, "+
									"?, ?, ?, ?)returning event_id;";
		final String SQLRequest = "insert into request values(default, ?, ?, "+
									"?, ?, ?, ?, ?, ?, ?, ?, ?, ?)returning "+
										"request_id";
		final String SQLoRI = "insert into optionalrequestinfo values(?, ?, "+
									"?, ?);";
		final String SQLAChain = "insert into ApprovalChain values(?, ?, ?, "+
										"?, ?, ?)";
		final String SQLAddInf = "Insert into additionalinfo values(default, "+
										"?, ?, ?, ?, ?, ?, ?)returning inf_id";
		try 
		{
			conn = ConnectionFactory.getConnection();
			
			stmt = conn.prepareStatement(SQLEvent);
			stmt.setString(1, req.getEvent().getTitle());
			stmt.setDate(2, req.getEvent().getStartDate());
			stmt.setInt(3, req.getEvent().getLengthInDays());
			stmt.setString(4, req.getEvent().getLocation());
			stmt.setInt(5, req.getEvent().getEventType());
			stmt.setInt(6, req.getEvent().getPassingGrade());
			stmt.setFloat(7, req.getEvent().getCost());
			

			set = stmt.executeQuery();
			
			while(set.next())
			{
				eventID = set.getInt(1);
			}
			req.getEvent().setEventId(eventID);
			
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);			
			
			stmt = conn.prepareStatement(SQLRequest);
			stmt.setDate(1, req.getCreationDate());
			stmt.setTime(2, req.getCreationTime());
			stmt.setString(3, req.getEventDescription());
			stmt.setString(4, req.getWorkJustification());
			stmt.setFloat(5, req.getProjected_reimbursement());
			stmt.setFloat(6, req.getReimbursement());
			stmt.setBoolean(7, req.isUrgent());
			stmt.setBoolean(8, req.isApproved());
			stmt.setString(9, req.getDenialReason());
			stmt.setString(10, req.getRefundIncreaseJustification());
			stmt.setInt(11, req.getEmployeeId());
			stmt.setInt(12, eventID);
			
			set = stmt.executeQuery();
			
			while(set.next())
			{
				reqID = set.getInt(1);
			}
			req.setRequestId(reqID);
			
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);			
			
			if(req.getoRI() != null)
			{
				req.getoRI().setRequestId(reqID);
				stmt = conn.prepareStatement(SQLoRI);
				stmt.setInt(1, reqID);
				stmt.setString(2, req.getoRI().getEventRelatedFile());
				stmt.setString(3, req.getoRI().getPreapprovalFile());
				stmt.setInt(4, req.getoRI().getPreapprovalLevel());
			
				stmt.executeUpdate();
				
				ConnectionClosers.closeStatement(stmt);
			}
			
			if(req.getApprovalChain().length != 0)
			{
				stmt = conn.prepareStatement(SQLAChain);
				for( ApprovalChain ac : req.getApprovalChain())
				{
					ac.setRequest_id(reqID);
					stmt.setInt(1, reqID);
					stmt.setInt(2, ac.getEmployee_id());
					stmt.setInt(3, ac.getWeight());
					stmt.setBoolean(4, ac.isApproved());
					stmt.setTimestamp(5, ac.getTimeoutTime());
					stmt.setBoolean(6, ac.isHasTimedOut());
								
					stmt.executeUpdate();
				
				}
				ConnectionClosers.closeStatement(stmt);
			}

			if(req.getAdditionalInfo().length != 0)
			{
				stmt = conn.prepareStatement(SQLAddInf);
				for( AdditionalInfo ai : req.getAdditionalInfo())
				{
					stmt.setString(1, ai.getText_request());
					stmt.setString(2, ai.getText_info());
					stmt.setString(3, ai.getFile_info());
					stmt.setBoolean(4, ai.isIsanswered());
					stmt.setInt(6, ai.getRequestId());
					stmt.setInt(7, ai.getSenderId());
					stmt.setInt(8, ai.getRecipientId());
					
					set = stmt.executeQuery();
					
					while(set.next())
					{
						addInfID = set.getInt(1);
					}
					ai.setAdditionalInfoId(addInfID);
					
					ConnectionClosers.closeResultSet(set);			
				
				}
				ConnectionClosers.closeStatement(stmt);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
		}
	}

	public AdditionalInfo askForMoreInfo(String request, int requestID, int senderID, int RecipiantID) 
	{
		AdditionalInfo ai = null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set=null;
		final String SQL = "Insert into additionalinfo values(default, "+
										"?, ?, ?, ?, ?, ?, ?)returning inf_id";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, request);
			stmt.setString(2, null);
			stmt.setString(3, null);
			stmt.setBoolean(4, false);
			stmt.setInt(6, requestID);
			stmt.setInt(7, senderID);
			stmt.setInt(8, RecipiantID);
					
			set = stmt.executeQuery();
					
			while(set.next())
			{
				ai = new AdditionalInfo(
					set.getInt(1),
					request,
					null,
					null,
					false,
					requestID,
					senderID,
					RecipiantID
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
		return ai;
	}

	public void respondToMoreInfoRequest(int aiID, String textResponse, String fileResponse) 
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		final String SQL = "update only additionalinfo set text_info = ?, "+
							"file_info = ?, isanswered = ? where inf_id = ?";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, textResponse);
			stmt.setString(2, fileResponse);
			stmt.setBoolean(3, true);
			stmt.setInt(4, aiID);
					
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

	public void changeRefundAmount(int requestID, float valueIncrease, String reason, int submitterID, int bencoID) 
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		final String SQLreq = "update only request set reimbursement = "+
									"reimbursement + ?, "+
									"refund_increase_justification = ? "+
									"where request_id = ?";
		final String SQLemp = "update only employee set total_reimbursement ="+
								"total_reimbursement -? where employee_id = ?";
		final String SQLapvc1 = "update only approvalchain set approved=?, "+
								"has_timedout=? where request_id = ? and "+
								"employee_id = ?";
		final String SQLapvc2 = "update only approvalchain set approved=? "+
								"where request_id = ? and employee_id = ?";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQLreq);
			stmt.setFloat(1, valueIncrease);
			stmt.setString(2, reason);
			stmt.setInt(3, requestID);
					
			stmt.executeUpdate();
					
			ConnectionClosers.closeStatement(stmt);
			
			stmt = conn.prepareStatement(SQLemp);
			
			stmt.setFloat(1, valueIncrease);
			stmt.setInt(2, submitterID);
					
			stmt.executeUpdate();
			
			ConnectionClosers.closeStatement(stmt);
			
			stmt = conn.prepareStatement(SQLapvc1);
			
			stmt.setBoolean(1, true);
			stmt.setBoolean(2, false);
			stmt.setInt(3, requestID);
			stmt.setInt(4, bencoID);
					
			stmt.executeUpdate();			
			
			ConnectionClosers.closeStatement(stmt);
			
			stmt = conn.prepareStatement(SQLapvc2);
			
			stmt.setBoolean(1, false);
			stmt.setInt(2, requestID);
			stmt.setInt(3, submitterID);
					
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

	public void bencoApproves(int requestID, int bencoID) 
	{

		Connection conn=null;
		PreparedStatement stmt=null;
		final String SQLreq = "update only request set is_approved = ? where "+
									"request_id = ?";
		final String SQLapvc = "update only approvalchain set approved=?, "+
									"has_timedout=? where request_id = ? and "+
									"employee_id = ?";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQLreq);
			stmt.setBoolean(1, true);
			stmt.setInt(2, requestID);
					
			stmt.executeUpdate();
					
			ConnectionClosers.closeStatement(stmt);
			
			stmt = conn.prepareStatement(SQLapvc);
			
			stmt.setBoolean(1, true);
			stmt.setBoolean(2, false);
			stmt.setInt(3, requestID);
			stmt.setInt(4, bencoID);
					
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
	//Employee Accepts New Reimbursement Amount
	public void eANRA(int requestID, int employeeID) 
	{

		Connection conn=null;
		PreparedStatement stmt=null;
		final String SQLreq = "update only request set is_approved = ? where "+
									"request_id = ?";
		final String SQLapvc = "update only approvalchain set approved=true, "+
									"where request_id = ? and employee_id = ?";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQLreq);
			stmt.setBoolean(1, true);
			stmt.setInt(2, requestID);
					
			stmt.executeUpdate();
					
			ConnectionClosers.closeStatement(stmt);
			
			stmt = conn.prepareStatement(SQLapvc);

			stmt.setInt(1, requestID);
			stmt.setInt(2, employeeID);
					
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

	public void individualApproves(int requestID, int employeeID) 
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		final String SQL = "update only approvalchain set approved = true, "+
								"has_timedout=false where request_id=? and "+
								"employee_id=?";

		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, requestID);
			stmt.setInt(2, employeeID);
					
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

	public int[] ApprovalTimeout(int[] benco) 
	{
		ArrayList<Integer> eIDs = new ArrayList<Integer>();
		Connection conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		final String SQL = "update only approvalchain set approved=true, "+
								"has_timedout=true where approved is null "+
								"and time_left < current_timestamp returning "+
								"employee_id, request_id";
		final String SQLbc = "update only approvalchain set approved=false, "+
								"where employee_id = ? and request_id = ? ";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
					
			set = stmt.executeQuery(SQL);
			
			while(set.next())
			{
				eIDs.add(set.getInt(1));
				for(int bid : benco)
				{
					if(bid == set.getInt(1))
					{
						pstmt = conn.prepareStatement(SQLbc);
						pstmt.setInt(1, set.getInt(1));
						pstmt.setInt(2, set.getInt(2));
						pstmt.execute();
					}
				}
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
			ConnectionClosers.closeStatement(pstmt);
			ConnectionClosers.closeResultSet(set);
		}	
		int[] eIDsA = new int[eIDs.size()];
		for(int i = 0; i < eIDsA.length; i++)
		{
			eIDsA[i] = eIDs.get(i);
		}
		return eIDsA;
	}

	public void denyRequest(int requestID, String denialReason, int employeeID) 
	{
		Connection conn=null;
		PreparedStatement stmt=null;
		final String SQLRequest = "update only request set is_approved=?, "+
									"denial_reason = ? where request_id=?";

		final String SQLApproval = "update only approvalchain set approved= ?"+
										", has_timedout=? where request_id=? "+
										"and employee_id=?";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQLRequest);
			stmt.setBoolean(1, false);
			stmt.setString(2, denialReason);
			stmt.setInt(3, requestID);
			stmt.executeUpdate();
			
			ConnectionClosers.closeStatement(stmt);

			stmt = conn.prepareStatement(SQLApproval);
			stmt.setBoolean(1, false);
			stmt.setBoolean(2, false);
			stmt.setInt(3, requestID);
			stmt.setInt(4, employeeID);
					
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

	public Request getRequestByID(int ID) 
	{
		int eventID = 0;
		Request r = null;
		ArrayList<AdditionalInfo> ai = null;
		ArrayList<ApprovalChain> ac = null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set = null;
		final String SQLrequest = "SELECT * FROM request WHERE request_id = ?";

		final String SQLoptinf = "SELECT * FROM optionalrequestinfo WHERE "+
									"request_id = ?";
		
		final String SQLevents = "SELECT a.* FROM events AS a INNER JOIN "+
									"request AS b  ON a.event_id = b.event_id"+
									" WHERE b.request_id = ?";
		final String SQLaddinf = "SELECT * FROM additionalinfo WHERE "+
									"request_id = ?";
		final String SQLappchain = "SELECT * FROM approvalchain WHERE "+
										"request_id = ?";	
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQLrequest);
			stmt.setInt(1, ID);
					
			set = stmt.executeQuery();
			
			while(set.next())
			{
				
				r = new Request(
					set.getInt(1),
					set.getDate(2),
					set.getTime(3),
					set.getString(4),
					set.getString(5),
					set.getFloat(6),
					set.getFloat(7),
					set.getBoolean(8),
					set.getBoolean(9),
					set.getString(10),
					set.getString(11),
					set.getInt(12)
				);
				eventID = set.getInt(13);
			}
			
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);

			stmt = conn.prepareStatement(SQLoptinf);
			stmt.setInt(1, ID);
			
			set = stmt.executeQuery();
			
			while(set.next())
			{
				
				r.setoRI(
					new OptionalRequestInfo(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getInt(4)
					)
				);
			}
			
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
			
			stmt = conn.prepareStatement(SQLevents);
			stmt.setInt(1, eventID);
			
			set = stmt.executeQuery();
			
			while(set.next())
			{
				r.setEvent(new Event(
						set.getInt(1),
						set.getString(2),
						set.getDate(3),
						set.getInt(4),
						set.getString(5),
						set.getInt(6),
						set.getInt(7),
						set.getFloat(8)
					)
				);
			}
			
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
			
			stmt = conn.prepareStatement(SQLaddinf);
			stmt.setInt(1, ID);
			
			set = stmt.executeQuery();
			
			ai = new ArrayList<AdditionalInfo>();
			
			while(set.next())
			{
				ai.add(new AdditionalInfo(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getBoolean(5),
						set.getInt(6),
						set.getInt(7),
						set.getInt(8)
					)
				);
			}
			if(ai.size() > 0)
			{
				AdditionalInfo[] aiarr = new AdditionalInfo[ai.size()];
				for(int i = 0; i < ai.size(); ++i)
				{
					aiarr[i] = ai.get(i);
				}
				r.setAdditionalInfo(aiarr);
			}
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
			
			stmt = conn.prepareStatement(SQLappchain);
			stmt.setInt(1, ID);
			
			set = stmt.executeQuery();
			ac = new ArrayList<ApprovalChain>();
			while(set.next())
			{
				ac.add(new ApprovalChain(
						set.getInt(1),
						set.getInt(2),
						set.getInt(3),
						set.getBoolean(4),
						set.getTimestamp(5),
						set.getBoolean(6)
					)
				);
			}
			if(ac.size() > 0)
			{
				ApprovalChain[] acarr = new ApprovalChain[ac.size()];
				for(int i = 0; i < ac.size(); ++i)
				{
					acarr[i] = ac.get(i);
				}
				r.setApprovalChain(acarr);
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
		return r;
	}

	public int[] getRequestsIMadeID(int eID) 
	{
		ArrayList<Integer> requestIDs = new ArrayList<Integer>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set = null;
		final String SQL = "SELECT request_id FROM request WHERE "+
								"employee_id = ?";
		try 
		{
			System.out.println("eID = "+ eID);
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, eID);
					
			set = stmt.executeQuery();
			
			while(set.next())
			{
				requestIDs.add(set.getInt(1));
			}
			System.out.println("made it this far.");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
			System.out.println("close conn");
			ConnectionClosers.closeStatement(stmt);
			System.out.println("close stmt");
			ConnectionClosers.closeResultSet(set);
			System.out.println("close set");
		}	
		int[] rIDs = new int[requestIDs.size()];
		for(int i = 0; i < requestIDs.size(); i++)
		{
			rIDs[i] = requestIDs.get(i);
		}
		return rIDs;
	}

	public int[] getRequestsINeedtoApproveID(int eID) 
	{
		ArrayList<Integer> requestIDs = new ArrayList<Integer>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set = null;
		final String SQL = "select a.request_id from (select request_id, min("+
							"weight) as weight FROM approvalchain where appro"+
							"ved is null GROUP BY request_id) as a inner join"+
							" (select request_id, min(weight) as weight from "+
							"approvalchain where approved is null and employe"+
							"e_id=? group by request_id) as b on a.request_id"+
							" = b.request_id and a.weight = b.weight";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, eID);
					
			set = stmt.executeQuery();
			
			while(set.next())
			{
				requestIDs.add(set.getInt(1));
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
		int[] rIDs = new int[requestIDs.size()];
		for(int i = 0; i < requestIDs.size(); i++)
		{
			rIDs[i] = requestIDs.get(i);
		}
		return rIDs;
	}

	public int[] getRequestsThatRequireMoreID(int eID) 
	{
		ArrayList<Integer> requestIDs = new ArrayList<Integer>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set = null;
		final String SQL = "select request_id from additionalinfo where "+
								"recipient_id = ? and isanswered = false";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, eID);
					
			set = stmt.executeQuery();
			
			while(set.next())
			{
				requestIDs.add(set.getInt(1));
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
		int[] rIDs = new int[requestIDs.size()];
		for(int i = 0; i < requestIDs.size(); i++)
		{
			rIDs[i] = requestIDs.get(i);
		}
		return rIDs;
	}

	public int[] getRequestsICanAccessID(int eID) 
	{
		ArrayList<Integer> requestIDs = new ArrayList<Integer>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set = null;
		final String SQL = "select request_id from approvalchain where "+
								"employee_id = ? and weight >0";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, eID);
					
			set = stmt.executeQuery();
			
			while(set.next())
			{
				requestIDs.add(set.getInt(1));
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
		int[] rIDs = new int[requestIDs.size()];
		for(int i = 0; i < requestIDs.size(); i++)
		{
			rIDs[i] = requestIDs.get(i);
		}
		return rIDs;
	}

	public int[] getAllRequestsID() 
	{
		ArrayList<Integer> requestIDs = new ArrayList<Integer>();
		Connection conn=null;
		Statement stmt=null;
		ResultSet set = null;
		final String SQL = "select request_id from request";
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
					
			set = stmt.executeQuery(SQL);
			
			while(set.next())
			{
				requestIDs.add(set.getInt(1));
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
		int[] rIDs = new int[requestIDs.size()];
		for(int i = 0; i < requestIDs.size(); i++)
		{
			rIDs[i] = requestIDs.get(i);
		}
		return rIDs;
	}
	

	public Request[] getRequestsByIDs(int[] ID) 
	{
		int[] eventID = new int[ID.length];
		Request[] r = new Request[ID.length];
		ArrayList<ArrayList<AdditionalInfo>> ai = new ArrayList<ArrayList<AdditionalInfo>>();
		ArrayList<ArrayList<ApprovalChain>> ac = new ArrayList<ArrayList<ApprovalChain>>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set = null;
		final String SQLrequest = "SELECT * FROM request WHERE request_id = ?";
	
		final String SQLoptinf = "SELECT * FROM optionalrequestinfo WHERE "+
									"request_id = ?";
		
		final String SQLevents = "SELECT a.* FROM events AS a INNER JOIN "+
									"request AS b  ON a.event_id = b.event_id"+
									" WHERE b.request_id = ?";
		final String SQLaddinf = "SELECT * FROM additionalinfo WHERE "+
									"request_id = ?";
		final String SQLappchain = "SELECT * FROM approvalchain WHERE "+
										"request_id = ?";	
		try 
		{
			conn = ConnectionFactory.getConnection();
			for(int i = 0; i< ID.length; i++)
			{
				stmt = conn.prepareStatement(SQLrequest);
				stmt.setInt(1, ID[i]);
						
				set = stmt.executeQuery();
				
				while(set.next())
				{
					
					r[i]= new Request(
						set.getInt(1),
						set.getDate(2),
						set.getTime(3),
						set.getString(4),
						set.getString(5),
						set.getFloat(6),
						set.getFloat(7),
						set.getBoolean(8),
						(Boolean)set.getObject(9),
						set.getString(10),
						set.getString(11),
						set.getInt(12)
					);
					eventID[i] = set.getInt(13);
				}
				
				ConnectionClosers.closeStatement(stmt);
				ConnectionClosers.closeResultSet(set);		
				stmt=null;
				set = null;
			}
			for(int i = 0; i< ID.length; i++)
			{
				stmt = conn.prepareStatement(SQLoptinf);
				stmt.setInt(1, ID[i]);
				
				set = stmt.executeQuery();
				
				while(set.next())
				{
					
					r[i].setoRI(
						new OptionalRequestInfo(
							set.getInt(1),
							set.getString(2),
							set.getString(3),
							set.getInt(4)
						)
					);
				}
				
				ConnectionClosers.closeStatement(stmt);
				ConnectionClosers.closeResultSet(set);
				stmt=null;
				set = null;
			}
			for(int i = 0; i< ID.length; i++)
			{
				
				stmt = conn.prepareStatement(SQLevents);
				stmt.setInt(1, eventID[i]);
				
				set = stmt.executeQuery();
				
				while(set.next())
				{
					r[i].setEvent(new Event(
							set.getInt(1),
							set.getString(2),
							set.getDate(3),
							set.getInt(4),
							set.getString(5),
							set.getInt(6),
							set.getInt(7),
							set.getFloat(8)
						)
					);
				}
				
				ConnectionClosers.closeStatement(stmt);
				ConnectionClosers.closeResultSet(set);
				stmt=null;
				set = null;
			}
			for(int i = 0; i< ID.length; i++)
			{
				
				stmt = conn.prepareStatement(SQLaddinf);
				stmt.setInt(1, ID[i]);
				
				set = stmt.executeQuery();
				ai.add(null);
				ai.set(i, new ArrayList<AdditionalInfo>());
				
				while(set.next())
				{
					ai.get(i).add(new AdditionalInfo(
							set.getInt(1),
							set.getString(2),
							set.getString(3),
							set.getString(4),
							set.getBoolean(5),
							set.getInt(6),
							set.getInt(7),
							set.getInt(8)
						)
					);
				}
				if(ai.size() > 0)
				{
					AdditionalInfo[] tempAI = new AdditionalInfo[ai.get(i).size()];
					for(int j = 0; j < ai.get(i).size(); j++)
					{
						tempAI[j] = ai.get(i).get(j);
					}
					r[i].setAdditionalInfo(tempAI);

//					try
//					{
//						r[i].setAdditionalInfo((AdditionalInfo[])ai.get(i).toArray());
//					}
//					catch(ClassCastException e)
//					{
//						e.printStackTrace();
//					}
				}
				ConnectionClosers.closeStatement(stmt);
				ConnectionClosers.closeResultSet(set);
				stmt=null;
				set = null;
			}
			for(int i = 0; i< ID.length; i++)
			{
				
				stmt = conn.prepareStatement(SQLappchain);
				stmt.setInt(1, ID[i]);
				
				set = stmt.executeQuery();
				ac.add(null);
				ac.set(i, new ArrayList<ApprovalChain>());
				while(set.next())
				{
					ac.get(i).add(new ApprovalChain(
							set.getInt(1),
							set.getInt(2),
							set.getInt(3),
							(Boolean)set.getObject(4),
							set.getTimestamp(5),
							set.getBoolean(6)
						)
					);
				}
				if(ac.size() > 0)
				{
					ApprovalChain[] tempAC = new ApprovalChain[ac.get(i).size()];
					for(int j = 0; j < ac.get(i).size(); j++)
					{
						tempAC[j] = ac.get(i).get(j);
					}
					r[i].setApprovalChain(tempAC);
//					try
//					{
//						r[i].setApprovalChain((ApprovalChain[])ac.get(i).toArray());
//					}
//					catch(ClassCastException e)
//					{
//						e.printStackTrace();
//					}
				}
				ConnectionClosers.closeStatement(stmt);
				ConnectionClosers.closeResultSet(set);
				stmt=null;
				set = null;
			} 
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionClosers.closeConnection(conn);
		}	
		return r;
	}

	@Override
	public void saveRequest(Request r) 
	{
		//AdditionalInfo[] ai = r.getAdditionalInfo();
		ApprovalChain[] approvalChain = r.getApprovalChain();
		Event ev = r.getEvent();
		OptionalRequestInfo ori = r.getoRI();

		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet set = null;
		final String SQLevent = "insert into events values(default, ?, "+
										"?, ?, ?, ?, ?, ?)returning event_id";

		final String SQLrequest = "insert into request values(default, ?, ?, "+
									"?, ?, ?, ?, ?, ?, ?, ?, ?, ?)returning "+
									"request_id";
		
		final String SQLoptrinf = "insert into optionalrequestinfo values(?, "+
										"?, ?, ?)";
		final String SQLappchain = "insert into ApprovalChain values(?, ?, "+
										"?, ?, ?, ?)";	
		try 
		{
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(SQLevent);
			stmt.setString(1,ev.getTitle());
			stmt.setDate(2, ev.getStartDate());
			stmt.setInt(3, ev.getLengthInDays());
			stmt.setString(4, ev.getLocation());
			stmt.setInt(5, ev.getEventType());
			stmt.setInt(6, ev.getPassingGrade());
			stmt.setFloat(7, ev.getCost());
					
			set = stmt.executeQuery();
			
			while(set.next())
			{
				ev.setEventId(set.getInt(1));
			}
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);

			stmt = conn.prepareStatement(SQLrequest);
			stmt.setDate(1, r.getCreationDate());
			stmt.setTime(2, r.getCreationTime());
			stmt.setString(3, r.getEventDescription());
			stmt.setString(4, r.getWorkJustification());
			stmt.setFloat(5, r.getProjected_reimbursement());
			stmt.setFloat(6, r.getReimbursement());
			stmt.setBoolean(7, r.isUrgent());
			stmt.setObject(8, r.isApproved());
			stmt.setString(9, r.getDenialReason());
			stmt.setString(10, r.getRefundIncreaseJustification());
			stmt.setInt(11, r.getEmployeeId());
			stmt.setInt(12, ev.getEventId());
			
			set = stmt.executeQuery();
			
			while(set.next())
			{
				r.setRequestId(set.getInt(1));
			}

			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
			
			stmt = conn.prepareStatement(SQLoptrinf);
			stmt.setInt(1, r.getRequestId());
			stmt.setString(2, r.getoRI().getEventRelatedFile());
			stmt.setString(3, r.getoRI().getPreapprovalFile());
			stmt.setInt(4, r.getoRI().getPreapprovalLevel());
			
			stmt.executeUpdate();

			ConnectionClosers.closeStatement(stmt);
			stmt = conn.prepareStatement(SQLappchain);

			for(ApprovalChain ac : approvalChain)
			{
				stmt.setInt(1, r.getRequestId());
				stmt.setInt(2, ac.getEmployee_id());
				stmt.setInt(3, ac.getWeight());
				stmt.setObject(4, ac.isApproved());
				stmt.setTimestamp(5, ac.getTimeoutTime());
				stmt.setBoolean(6, ac.isHasTimedOut());


				stmt.executeUpdate();
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
		}
	}
}
























