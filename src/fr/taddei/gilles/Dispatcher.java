package fr.taddei.gilles;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.taddei.gilles.common.*;
import fr.taddei.gilles.rmi.*;

/**
 * Servlet implementation class Dispatcher
 */
public class Dispatcher extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dispatcher()
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
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String previousPage = request.getParameter("prev");
		ConnectionServeur bdw = new ConnectionServeur();
		if (request.getParameter("login") == null
				|| request.getParameter("login") == ""
				|| request.getParameter("password") == null
				|| request.getParameter("password") == "")
		{
			response.sendRedirect("Connexion.jsp");
		} else
		{
			String[] connectValues;
			connectValues = bdw.connect(request.getParameter("login"),
					request.getParameter("password"));
			if (connectValues != null)
			{
				if (Integer.parseInt(connectValues[0]) > 0)
				{
					request.getSession().setAttribute("id",
							Integer.parseInt(connectValues[1]));
					request.getSession()
							.setAttribute("login", connectValues[2]);
					request.getSession().setAttribute("nom", connectValues[3]);
					request.getSession().setAttribute("prenom",
							connectValues[4]);
					System.out.println(previousPage);
					response.sendRedirect(previousPage == null ? "index.jsp"
							: previousPage.contains("Connexion.jsp") ? "index.jsp"
									: previousPage);
				} else
				{
					response.sendRedirect("Connexion.jsp?msg=fail");
				}
			} else
			{
				response.sendRedirect("Connexion.jsp");
			}
		}
	}
}