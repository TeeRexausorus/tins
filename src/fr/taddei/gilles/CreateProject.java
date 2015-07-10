package fr.taddei.gilles;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.taddei.gilles.rmi.ConnectionServeur;

/**
 * Servlet implementation class CreateProject
 */
@MultipartConfig
public class CreateProject extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static String root = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateProject()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		response.sendRedirect("createProject.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		ConnectionServeur bdw = new ConnectionServeur();
		if (request.getParameter("project_name") == null
				|| request.getParameter("project_desc") == null)
		{
		} else
		{
			if (request.getSession() != null)
			{
			    int idProj =bdw.insertProject(request.getParameter("project_name"),
                        request.getParameter("project_desc"), (Integer) request
                        .getSession().getAttribute("id"));
				if (idProj >= 1)
				{
					String filePath = FileRep.getRoot();
					FileRep.handleFile(request, response, filePath
							+ "/" + request.getParameter("project_name"), idProj);
					response.sendRedirect("index.jsp");
				} else
				{
					response.sendRedirect("createProject.jsp");
				}

			} else
			{
				response.sendRedirect("Connexion.jsp");
			}
		}
	}

}
