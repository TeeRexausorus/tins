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
 * Servlet implementation class CrawlProject
 */
public class CrawlProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrawlProject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("id") != null)
		{
			if (request.getParameter("idProject") != null)
			{
				String path = null;
				int idRoot = 0;
				int idProjet = Integer.parseInt(request
						.getParameter("idProject"));
				String testIDRoot = "SELECT p.IDRoot, path "
						+ "from `projet` p, filerep f " + "where p.id='"
						+ idProjet + "' AND p.IDRoot=f.id;";
				final ResultSet rs = BDConnector.execRequete(testIDRoot,
						BDConnector.openConnection());
				if (rs != null)
				{
					try
					{
						while (rs.next())
						{
							path = rs.getString("path");
							idRoot = rs.getInt("IdRoot");
						}
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
					final String Tpath = path;
					final int TIdRoot = idRoot;
					final int TIdProj = idProjet;
					Thread t = new Thread(new Runnable()
					{
						
						@Override
						public void run()
						{
								FileRep.deleteCascadeFilerep(TIdRoot);
								FileRep.crawl(new File(Tpath), TIdProj);
							
						}
					});
								t.start();
				}
			}
			response.sendRedirect("index.jsp");
		}
	}
}
