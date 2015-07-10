package fr.taddei.gilles;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class DeleteProject
 */
public class HandleProject extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleProject()
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

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        if (request.getSession().getAttribute("id") != null)
        {
            if (request.getParameter("idProject") != null)
            {
                // System.out.println("in if(idProject)");
                if (request.getParameter("btnDelete") != null)
                {
                    // System.out.println("in if(btnDelete)");
                    int idProjet = Integer.parseInt(request
                            .getParameter("idProject"));
                    BDConnector
                            .insertBD("DELETE FROM contribution where idProjet='"
                                    + idProjet + "';");
                    BDConnector.insertBD("DELETE FROM fileRep where idProjet='"
                            + idProjet + "';");
                    String testIDRoot = "SELECT IDRoot, path "
                            + "from `projet` p, filerep f " + "where p.id='"
                            + idProjet + "' AND p.IDRoot=f.id;";
                    ResultSet rs = BDConnector.execRequete(testIDRoot,
                            BDConnector.openConnection());
                    if (rs != null)
                    {
                        try
                        {
                            while (rs.next())
                            {
                                System.out.println("idRoot : "
                                        + rs.getInt("IDRoot"));
                                FileUtils.deleteDirectory(new File(rs
                                        .getString("path")));
                                FileRep.deleteCascadeFilerep(rs
                                        .getInt("IDRoot"));
                            }
                        } catch (SQLException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    BDConnector.insertBD("DELETE FROM projet where ID='"
                            + idProjet + "';");
                    response.sendRedirect("index.jsp");
                } else if (request.getParameter("btnSearch") != null)
                {
                    String str = "";
                    response.sendRedirect("search.jsp?idProject="
                            + request.getParameter("idProject"));
                }
            }
        }
    }

}
