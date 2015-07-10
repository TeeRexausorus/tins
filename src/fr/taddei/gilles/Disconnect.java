package fr.taddei.gilles;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Disconnect
 */
public class Disconnect extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Disconnect()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		Enumeration <String> names = request.getSession().getAttributeNames();
		String name;
		while (names.hasMoreElements())
		{
			name = names.nextElement();
			if (request.getSession().getAttribute(name) != null)
			{
				request.getSession().setAttribute(name, null);
			}
			
		}
		response.sendRedirect("deco.jsp");
	}
}
