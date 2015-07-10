<%@page import="fr.taddei.gilles.rmi.ConnectionServeur"%>
<%@page import="fr.taddei.gilles.common.formulaire.*"%>
<%@page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="favicon.ico" />
<link href="styles.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Création d'un répertoire</title>
</head>
<body>
	<%! ConnectionServeur cs = new ConnectionServeur(); %>
	<%
		if (request.getSession().getAttribute("id") != null)
		{
			if (request.getParameter("id") != null)
			{
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
				out.println(cs.listerProject((Integer) request.getSession().getAttribute("id")));
			}
		%>
		
		</div>
		<div class="center">
			<div class="miniTop">
			<%
				if (request.getParameter("status") != null && request.getParameter("status") != "")
				{
					String status = request.getParameter("status");
					if (status.equals("OK"))
					{
						%>
						<span class="greenBig">Création du répertoire effectuée avec succès.</span>
						<%
					}
					else if (status.equals("NOK"))
					{
						%>
							<span class="redBig">Echec de création du répertoire</span>
						<%
					}
				}
			%>
			</div>
			<%
				ArrayList<Field> fields = new ArrayList<Field>();
				fields.add(new TextField("rep_name", "", "Nom du répertoire", "Répertoire à créer : "));
				String root = cs.getRep(Integer.parseInt(request.getParameter("id")));
				int id = Integer.parseInt(request.getParameter("id"));
				int idProjet = Integer.parseInt(request.getParameter("idProjet"));
				
				fields.add(new Hidden(root, "root"));
				fields.add(new Hidden("" + id, "id"));
				fields.add(new Hidden("" + idProjet, "idProjet"));
				fields.add(new Button("btn_send", "Créer !", ""));
				Formulaire f = new Formulaire("post", "form_create", "CreateRep",false, fields);
				out.println(f);
			%>
		</div>
		<div class="right"></div>


	</div>
	<%
		}
		} else
		{
			response.sendRedirect("Connexion.jsp");
		}
	%>
</body>
</html>