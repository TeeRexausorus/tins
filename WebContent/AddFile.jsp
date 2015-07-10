<%@page import="fr.taddei.gilles.rmi.ConnectionServeur"%>
<%@page import="fr.taddei.gilles.common.formulaire.Button"%>
<%@page import="fr.taddei.gilles.common.formulaire.Formulaire"%>
<%@page import="fr.taddei.gilles.common.formulaire.Hidden"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fr.taddei.gilles.common.formulaire.File"%>
<%@page import="fr.taddei.gilles.common.formulaire.Field"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="favicon.ico" />
<link href="styles.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ajout de fichier</title>
</head>
<body>
	<%!ConnectionServeur cs = new ConnectionServeur();%>
	<%
	    if (request.getSession().getAttribute("id") != null)
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
			            out.println(cs.listerProject((Integer) request.getSession()
			                    .getAttribute("id")));
			        }
			%>

		</div>
		<div class="center">
			<div class="top">
				<%
				    String status = request.getParameter("res");
				        if (status != null && !status.equals(""))
				        {
				            if (status.equals("OK"))
				            {
				%>
				<span class="greenBig">Téléversement du fichier effectué avec
					succès.</span>
				<%
				    } else if (status.equals("NOK"))
				            {
				%>
				<span class="redBig">Echec lors du téléversement du fichier</span>
				<%
				    }
				        }
				%>
			</div>
			<%
			    ArrayList<Field> fields = new ArrayList<Field>();
			        String root = cs.getRep(Integer.parseInt((request
			                .getParameter("id"))));
			        int id = Integer.parseInt(request.getParameter("id"));
			        int idProjet = Integer.parseInt(request.getParameter("idProjet"));
			        fields.add(new Hidden("" + id, "id"));
			        fields.add(new Hidden(root, "root"));
			        fields.add(new Hidden("" + idProjet, "idProjet"));
			        fields.add(new File("file",
			                "Le fichier à ajouter au répertoire"));
			        fields.add(new Button("btnGo", "Envoyer !", ""));
			        out.print(new Formulaire("POST", "form_add_file",
			                "FileUploader", true, fields));
			%>
		</div>
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