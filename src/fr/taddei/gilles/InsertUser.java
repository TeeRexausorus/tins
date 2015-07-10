package fr.taddei.gilles;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.taddei.gilles.rmi.ConnectionServeur;

/**
 * Servlet implementation class InsertUser
 */
public class InsertUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("CreationCompte.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nom, prenom, password, mail, login;
		nom = request.getParameter("nom");
		prenom = request.getParameter("prenom");
		password = request.getParameter("password");
		mail = request.getParameter("mail");
		login = request.getParameter("login");
		//System.out.println(nom + " | " + prenom + " | " + password + " | " + mail + " | " );
		ConnectionServeur bdw = new ConnectionServeur();
		if (bdw.insertUser(nom, prenom, password, mail, login) > 0)
		{
			response.sendRedirect("index.jsp");
		}
		else
		{
			response.sendRedirect("CreationCompte.jsp");
		}
	}

}
