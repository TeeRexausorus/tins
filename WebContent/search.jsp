<%@page import="java.sql.SQLException"%>
<%@page import="fr.taddei.gilles.BDConnector"%>
<%@page import="java.sql.*"%>
<%@page import="fr.taddei.gilles.rmi.ConnectionServeur"%>
<%@page import="fr.taddei.gilles.FileRep"%>
<%@page import="fr.taddei.gilles.common.formulaire.*"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="favicon.ico" />
<link href="styles.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recherche</title>
</head>
<body>
	<%!ConnectionServeur cs = new ConnectionServeur();
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

	%>
	<%
	    if (request.getSession().getAttribute("id") != null)
	    {
	        request.getSession().setAttribute("idProject",
	                request.getParameter("id"));
	%>
	<div class="global">
		<div class="top">
			<p class="right">
				<%@include file="top_right.jsp"%>
			</p>
			<p class="left">
				<%@include file="top_left.jsp"%>
			</p>
		</div>
		<div class="left">
			<%
			    if (request.getSession().getAttribute("id") != null)
			        {
			            out.println(cs.listerProject((Integer) request.getSession()
			                    .getAttribute("id")));
			            out.println(cs.listerProjectNotIn((Integer) request
			                    .getSession().getAttribute("id")));
			%>
			<%@include file="actions.jsp"%>

			<%
			    } else
			        {
			%>
			<%
			    }
			%>
		</div>
		<div class="center">
		<%
			if (request.getParameter("txtSearch") == null)
			{
			    String str = "";
		        ArrayList<Field> fields = new ArrayList<Field>();
		        fields.add(new Hidden("" + request.getParameter("idProject"),
		                "idProject"));
		        fields.add(new TextField("txtSearch", "", "recherche",
		                "Recherche : "));
		        fields.add(new Button("btnLaunchSearch", "lancer la recherche",
		                "", ""));// */
		        Formulaire form = new Formulaire("GET", "search", "search.jsp",
		                false, fields);
		        str += form;
		        out.println(str);
			}
			else
			{
		        String search = request.getParameter("txtSearch");
		        int idProject = Integer.parseInt(request.getParameter("idProject"));
		        String[] keywords = search.split(" ");
		        String strRequest = searchRequest(keywords, idProject);
		        ResultSet rs = BDConnector.execRequete(strRequest,
		                BDConnector.openConnection());
		        out.println("<fieldset><legend>Résultat de la recherche : </legend><ul>");
		        try
		        {
		            if (rs != null)
		            {
		                while (rs.next())
		                {
		                    	out.println("<li><a href=\"Downloader?id="+ rs.getInt("ID")
		                            + "&idProjet=" + rs.getInt("project") + "\">"
		                            + rs.getString("nom") + "</a></li>\n");
		                }
		            }
		            else{
		                out.println("Aucun fichier ne correspond à la recherche.");
		                out.println("<a href=\"search.jsp?idProject="+request.getParameter("idProject")+"\">Revenir à la recherche</a>");
		            }
		        } catch (SQLException e)
		        {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
				out.println("</ul></fieldset>");
			}
		%>
		</div>


	</div>
	<%
	    } else
	    {
	        response.sendRedirect("Connexion.jsp");
	    }
	%>
</body>
</html>