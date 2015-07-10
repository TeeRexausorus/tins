package fr.taddei.gilles;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import fr.taddei.gilles.rmi.ConnectionServeur;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search()
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
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        Misc.send(response, "<link rel=\"icon\" href=\"favicon.ico\" />\n<link href=\"styles.css\" rel=\"stylesheet\" />");
        
        // TODO Auto-generated method stub
        String strRet = "";
        ConnectionServeur cs = new ConnectionServeur();
        strRet += "<div ";
        strRet += cs.listerProject((Integer) request.getSession().getAttribute(
                "id"));
        strRet += cs.listerProjectNotIn((Integer) request.getSession()
                .getAttribute("id"));
        String search = request.getParameter("txtSearch");
        int idProject = Integer.parseInt(request.getParameter("idProject"));
        String[] keywords = search.split(" ");
        String strRequest = searchRequest(keywords, idProject);
        ResultSet rs = BDConnector.execRequete(strRequest,
                BDConnector.openConnection());
        System.out.println(strRequest + " " + search);
        try
        {
            if (rs != null)
            {
                while (rs.next())
                {
                    strRet += "<li><a href=\"Downloader?id=" + rs.getInt("ID")
                            + "&idProjet=" + rs.getInt("project") + "\">"
                            + rs.getString("nom") + "</a></li>\n";
                }
            }
            else{
                strRet += "Aucun fichier ne correspond à la recherche.";
                strRet += "<a href=\"search.jsp?idProject="+request.getParameter("idProject")+"\">Revenir à la recherche</a>";
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Misc.send(response, strRet);
    }

    private String searchRequest(String[] keywords, int idProject)
    {
        String ret = "SELECT * FROM filerep WHERE file=1 AND project="
                + idProject + "";
        for (String keyword : keywords)
        {
            ret += " AND nom like '%" + keyword + "%'";
        }
        ret += ";";
        return ret;
    }
}
