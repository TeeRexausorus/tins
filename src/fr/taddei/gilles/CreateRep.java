package fr.taddei.gilles;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class CreateRep
 */
public class CreateRep extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateRep()
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
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getParameter("rep_name") != null)
		{
			File f = new File(request.getParameter("root") + "/"
					+ request.getParameter("rep_name"));
			System.out.println(request.getParameter("root") + "/"
					+ request.getParameter("rep_name"));
			if (f.mkdir())
			{
				FileRep.crawl(f.getAbsoluteFile(), Integer.parseInt(request.getParameter("idProjet") ) );
				response.sendRedirect("CreateRep.jsp?id="
						+ request.getParameter("id") + "&status=OK&idProjet="+request.getParameter("idProjet"));
			} else
			{
				response.sendRedirect("CreateRep.jsp?id="
						+ request.getParameter("id") + "&status=NOK&idProjet="+request.getParameter("idProjet"));
			}
		}
	}

}
