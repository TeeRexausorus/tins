package fr.taddei.gilles;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.taddei.gilles.rmi.ConnectionServeur;

/**
 * Servlet implementation class AskAccess
 */
public class AskAccess extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AskAccess()
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
        if (request.getSession().getAttribute("id") != null)
        {
            String prevPage = request.getHeader("Referer");
            String id = (String) request.getSession().getAttribute("id");
            ConnectionServeur cs = new ConnectionServeur();
            //cs.insertContri(login, idStatus, idProjet);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
    }

}
