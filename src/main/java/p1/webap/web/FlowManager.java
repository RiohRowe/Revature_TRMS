package p1.webap.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;

import p1.webap.model.ApprovalChain;
import p1.webap.model.Employee;
import p1.webap.model.Event;
import p1.webap.model.OptionalRequestInfo;
import p1.webap.model.Request;
import p1.webap.model.SessionObj;
import p1.webap.service.DBService;

@WebServlet(description = "Upload File To The Server", urlPatterns = { "/fileUploadServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class FlowManager extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public static final String UPLOAD_DIR = "files";
    private boolean good = true;

    public FlowManager() 
    {
        super();
    }
    
	public ApprovalChain[] assembleChain(int melol, int palol, int meid, Date currDate)
	{
		int daysToApprove = 3;
		Calendar approvaldate = Calendar.getInstance();
		approvaldate.add(Calendar.DAY_OF_YEAR, daysToApprove);
    	approvaldate.setTime(currDate);
		DBService dbs = new DBService();
		Employee[] benco = dbs.getEmployeesByLOL(3);
		Employee mine = dbs.getEmployeeByID(meid);
		ArrayList<Integer> empIDs = new ArrayList<Integer>();
		empIDs.add(benco[0].getEmployeeId());
		if(melol < 2 && palol < 2)
		{
			empIDs.add(mine.getDepartmentHeadId());
		}
		if(melol < 1 && palol < 1)
		{
			empIDs.add(mine.getManagerId());
		}
		ApprovalChain[] apch = new ApprovalChain[empIDs.size()+1];
		int tweight = 0;
		apch[0] = new ApprovalChain(0, meid, tweight, true, new Timestamp(approvaldate.getTime().getTime()), false);
		tweight += 1;
		for(int i = 0; i < empIDs.size(); i++, tweight++ )
		{
			approvaldate.add(Calendar.DAY_OF_YEAR, daysToApprove);
			apch[tweight] = new ApprovalChain(0,empIDs.get(i), tweight, null, new Timestamp(approvaldate.getTime().getTime()), false);
		}
		return apch;
	}
    public int getApprovalWeight(String apvtype)
    {
    	if(apvtype.equals("Supervisor"))
    	{
    		return 1;
    	}
    	else if(apvtype.equals("Department Head"))
    	{
    		return 2;
    	}
        else if(apvtype.equals("Benefits Coordinator"))
    	{
    		return 3;
    	}
        else
    	{
    		return 0;
    	}
    }
    public int EventTypetoInt(String evtype)
    {
    	if(evtype.equals("Certification"))
    	{
    		return 5;
    	}
    	else if(evtype.equals("Tech Training"))
    	{
    		return 4;
    	}
    	else if(evtype.equals("University Course"))
    	{
    		return 3;
    	}
    	else if(evtype.equals("Certification Prep"))
    	{
    		return 2;
    	}
    	else if(evtype.equals("Seminar"))

    	{
    		return 1;
    	}
    	else if(evtype.equals("other"))
    	{
    		return 0;
    	}
    	else
    	{
    		return -1;
    	}
    }
    public boolean isUrgent(Date now, Date evs)
    {
    	Calendar datenow = Calendar.getInstance();
    	Calendar dateevs = Calendar.getInstance();
    	datenow.setTime(now);
    	dateevs.setTime(evs);
    	datenow.add(Calendar.DAY_OF_YEAR,7);
    	if(dateevs.getTimeInMillis() < datenow.getTimeInMillis())
    	{
    		//Too Late to submit Request
    		good=false;
    	}
    	datenow.add(Calendar.DAY_OF_YEAR,7);
    	if(dateevs.getTimeInMillis() < datenow.getTimeInMillis())
    	{
    		//request is urgent
    		return true;
    	}
    	else
    	{
    		//Request is not urgent
    		return false;
    	}
    	
    }
    
    public float calcReimb(float cost, String etype)
    {
    	if(etype.equals("Certification"))
    	{
    		return cost*1.00f;
    	}
    	else if(etype.equals("Tech Training"))
    	{
    		return cost*0.90f;
    	}
    	else if(etype.equals("University Course"))
    	{
    		return cost*0.80f;
    	}
    	else if(etype.equals("Certification Prep"))
    	{
    		return cost*0.75f;
    	}
    	else if(etype.equals("Seminar"))

    	{
    		return cost*0.60f;
    	}
    	else if(etype.equals("other"))
    	{
    		return cost*0.30f;
    	}
    	else
    	{
    		return cost*0.00f;
    	}
    }
    
    public void saveFiles(Part[] parts)
    {
    	String applicationPath = "C:\\Users\\rrowe\\Desktop\\Repos\\Main&BranchRepoForClass\\082619jwa\\Week03\\project1\\src\\main\\webapp\\";//getServletContext().getRealPath("");
	    String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
	    
	    File fileUploadDirectory = new File(uploadPath);
	    if (!fileUploadDirectory.exists()) 
	    {
	        fileUploadDirectory.mkdirs();
	    }
	    
	    String fileName = "";
	    UploadDetail details = null;
	    List<UploadDetail> fileList = new ArrayList<UploadDetail>();
	    for (Part part : parts) 
	    {
	    		    	Collection<String> s = part.getHeaderNames();

	        	
	        String contentDisposition = part.getHeader("content-disposition");
	        String[] items = contentDisposition.split(";");
	        for (String item : items) 
	        {
	            if (item.trim().startsWith("filename")) 
	            {
	            	fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
	                break;
	            }
	        }
	        details = new UploadDetail();
         	details.setFileName(fileName);
         	details.setFileSize(part.getSize() / 1024);
         	try 
         	{
             	part.write(uploadPath + File.separator + fileName);
             	details.setUploadStatus("Success");
         	} 
         	catch (IOException e) 
         	{
        	 	details.setUploadStatus("Failure : "+ e.getMessage());
         	}
         	fileList.add(details);
	     }
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		response.setContentType("application/json");
		String uri = request.getRequestURI().replaceAll("/P1/[^/]+/api", "");
		
		
		//System.out.println("URI = <" + uri + ">");
		
//		Enumeration st = request.getParameterNames();
//		while(st.hasMoreElements())
//		{
//			System.out.println(st.nextElement());
//		}

		HelperDBService hdbs = new HelperDBService();
		//IF LOGIN (Request is sent to api.login with credentials)
		if(uri.equals("/login"))
		{
			//Verify
			SessionObj sesObj = hdbs.validateUser((String)request.getParameter("un"), (String)request.getParameter("pw"), request);

			if(sesObj != null)
			{
				//Give session
				HttpSession ses = request.getSession(false);
				if(ses == null)
				{
					ses = request.getSession();
				}
				//Populate Session Attributes.
				ses.setAttribute("sesobj", sesObj);
				response.setContentType("text/html");
				//Redirect to Home
				response.sendRedirect("/P1/Views/Home.html");
//				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Views/Home.html");
//				dispatcher.forward(request,response);

			}
			else
			{
				response.sendRedirect("/P1/index.html");
			}
		}
		//IF LOGOUT
		else if(uri.equals("/logout"))
		{
			//EndSession
			HttpSession ses = request.getSession(false);
			if(ses != null)
			{
				ses.invalidate();
			}
			//redirect to login
			response.sendRedirect("/P1/index.html");
		}
		//IF Request Requests
		else if(uri.contentEquals("/myrequests"))
		{
			response.getOutputStream().write(hdbs.processGet(request, response));
		}
		else if(uri.contentEquals("/yourrequests"))
		{
			response.getOutputStream().write(hdbs.processGet(request, response));
		}
			//IF Form Upload IE, files
		else if(uri.contentEquals("/newRequestForm"))
		{
			boolean goodinput = true;
			if(request.getParameter("fn").length() < 1)
			{
				goodinput = false;
				System.out.println("1");
			}
			if(request.getParameter("ln").length() < 1)
			{
				goodinput = false;
				System.out.println("2");
			}
			if(request.getParameter("pnum").length() < 7)
			{
				goodinput = false;
				System.out.println("3");
			}
			if(!request.getParameter("em").contains("@") || !request.getParameter("em").contains("."))
			{
				goodinput = false;
				System.out.println("4");
			}
			if(!request.getParameter("country").equals("United States"))
			{
				goodinput = false;
				System.out.println("5");
			}
			if(request.getParameter("state").contains("q") || 
							request.getParameter("state").length() > 12 ||
							request.getParameter("state").length() < 4)
			{
				System.out.println("6");
				goodinput = false;
			}
			if(request.getParameter("et").length() < 1)
			{
				System.out.println("7");
				goodinput = false;
			}
			if(request.getParameter("evdes").length() < 1)
			{
				System.out.println("8");
				goodinput = false;
			}
			if(request.getParameter("pg").length() < 1 ||
				request.getParameter("pg").length() > 3)
			{
				System.out.println("9");
				goodinput = false;
			}
			if(request.getParameter("evt").length() < 5 ||
				request.getParameter("evt").length() > 19)
			{
				System.out.println("10");
				goodinput = false;
			}
			if(request.getParameter("c").length()<1 ||
				request.getParameter("c").length()>4)
			{
				System.out.println("11");
				goodinput = false;
			}
			if(request.getParameter("sd").length() < 1)
			{
				System.out.println("12");
				goodinput = false;
			}
			if(request.getParameter("rftc").length() < 1)
			{
				System.out.println("13");
				goodinput = false;
			}
				
			if(goodinput)
			{
				DBService dbs = new DBService();
				HttpSession ses = request.getSession(false);
				if(ses == null)
				{
					System.out.println("InvalidSessionSubmitted Form (FromServlet)");
				}
				SimpleDateFormat format = new SimpleDateFormat("yyy-mm-dd");
				Date current = new Date(System.currentTimeMillis());
				Date evstart = null;
				try 
				{
					evstart = new Date(
						format.parse(request.getParameter("sd")).getTime());
				} 
				catch (ParseException e) 
				{
					e.printStackTrace();
				}
				float cost = new Float(request.getParameter("c"));
				int passingGrade = new Integer(request.getParameter("pg"));
				int preApprovalLevel = getApprovalWeight(request.getParameter("apvtype"));
				float reimbursement = calcReimb(
						new Float(request.getParameter("c")), request.getParameter("evt"));
				SessionObj so = (SessionObj)ses.getAttribute("sesobj");
				if(so == null)
				{
					ses.invalidate();
					response.sendRedirect("/P1/index.html");
				}
				else
				{
					Request r = new Request(
							0, 
							new Date(System.currentTimeMillis()),
							new Time(System.currentTimeMillis()), 
							request.getParameter("evdes"),
							request.getParameter("rftc"), 
							reimbursement, 
							reimbursement, 
							isUrgent(current, evstart),
							null, 
							null, 
							null, 
							so.employeeID);
					Event ev = new Event(
						0, 
						request.getParameter("et"), 
						evstart, 
						new Integer(request.getParameter("dor")), 
						request.getParameter("state"), 
						EventTypetoInt(request.getParameter("evt")),
						passingGrade, 
						cost);
					OptionalRequestInfo ori = new OptionalRequestInfo(
						0, 
						"../" + UPLOAD_DIR + "/" + request.getPart("evrf").getSubmittedFileName(), 
						"../" + UPLOAD_DIR + "/" + request.getPart("paf").getSubmittedFileName(), 
						preApprovalLevel);
					ApprovalChain[] ac = assembleChain(so.lol, preApprovalLevel, so.employeeID, current);
					r.setEvent(ev);
					r.setoRI(ori);
					r.setApprovalChain(ac);
					System.out.println("ApprovalChainLength:"+ac.length);
					dbs.saveRequest(r);
					
					
					
					Part[] fileParts = {request.getPart("evrf"), request.getPart("paf")};
					
					 /* Get The Absolute Path Of The Web Application */
					saveFiles(fileParts);
					response.sendRedirect("/P1/Views/ViewMyRequests.html");
				}
			}
			else
			{
				response.sendRedirect("/P1/Views/RequestReimbursement.html");
			}
		}
			//CancelRequest
		else if(uri.startsWith("/cancelrequest"))
		{
			HttpSession ses = request.getSession(false);
			if(ses == null)
			{
				System.out.println("InvalidSessionSubmitted Form (FromServlet)");
			}
			SessionObj so = (SessionObj)ses.getAttribute("sesobj");
			if(so == null)
			{
				ses.invalidate();
				response.sendRedirect("/P1/index.html");
			}
			else
			{
				int reqid = new Integer(uri.replaceAll("/cancelrequest", ""));
				DBService dbs = new DBService();
				dbs.denyRequest(dbs.getRequestByID(reqid), "Canceled By Requester", so.employeeID);
				response.sendRedirect("/P1/Views/ViewMyRequests.html");
			}
		}
			//ServeRequests
			
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//response.getOutputStream().write(hdbs.processGet(request, response));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
