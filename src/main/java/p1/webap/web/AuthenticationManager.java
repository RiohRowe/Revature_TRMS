package p1.webap.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AuthenticationManager implements Filter 
{
    public AuthenticationManager() 
    {}


	public void destroy() 
	{}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//Check if request has session. If not, 
		HttpSession session = req.getSession(false);
		if(session == null)
		{
			//If no session exists, redirect to login.
			resp.sendRedirect("../index.html");
		}
		else
			
		
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig fConfig) throws ServletException 
	{}

}
