<%@page import="fr.taddei.gilles.rmi.ConnectionServeur"%>
<%@page import="fr.taddei.gilles.FileRep"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="favicon.ico" />
<link href="styles.css" rel="stylesheet" />
<script>
	function deleteProject() {
		return confirm("êtes-vous sùr de vouloir supprimer ce projet ?");
	}
	function crawlProject() {
		return confirm("êtes-vous sùr de vouloir ré-indexer ce projet ?");
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Affichage du projet</title>
</head>
<body>
	<%!ConnectionServeur cs = new ConnectionServeur();%>
	<%
		if (request.getSession().getAttribute("id") != null)
		{
		    request.getSession().setAttribute("idProject", request.getParameter("id"));
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
						out.println(cs.listerProjectNotIn((Integer) request.getSession()
								.getAttribute("id")));
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
		<%
			out.println(cs.listerRepProj(
						Integer.parseInt(request.getParameter("id")),
						(Integer) request.getSession().getAttribute("id")));
		%>
		<div class="right"></div>


	</div>
	<%
		} else
		{
			response.sendRedirect("Connexion.jsp");
		}
	%>
</body>
</html>