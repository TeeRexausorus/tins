package fr.taddei.gilles;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.taddei.gilles.rmi.ConnectionServeur;

/**
 * Servlet implementation class AddContri
 */
public class AddContri extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddContri() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		int idProjet = Integer.parseInt(request.getParameter("id"));
		int idStatus = Integer.parseInt(request.getParameter("status"));
		Misc.send(response, login + " " + idStatus);
		ConnectionServeur bdw = new ConnectionServeur();
		bdw.insertContri(login, idStatus, idProjet);
		response.sendRedirect("affProject.jsp?id="+idProjet);
	}

}
