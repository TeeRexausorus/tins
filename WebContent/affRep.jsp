<%@page import="fr.taddei.gilles.rmi.ConnectionServeur"%>
<%@page import="fr.taddei.gilles.common.formulaire.File"%>
<%@page import="fr.taddei.gilles.FileRep"%>
<%@page import="java.io.FileReader"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="favicon.ico" />
<link href="styles.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Affichage d'un répertoire</title>
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
			            out.println(cs.listerProjectNotIn((Integer) request
			                    .getSession().getAttribute("id")));
			%>
			<%@include file="actions.jsp"%>

			<%
			    }
			%>

		</div>
		<div class="center" contextmenu="mymenu">
			<fieldset class="scrollable">
				<legend>Contenu du répertoire :</legend>
				<%
				    if (request.getParameter("id") != null)
				        {
				            // 				            out.println(request
				            // 				                    .getParameter("id"));
				            //File rep = new File(request.getParameter("root"));
				            //out.println(FileRep.listerRep(rep));
				            out.println(FileRep.listerRepBD(Integer.parseInt(request
				                    .getParameter("id"))));
				%>
			</fieldset>
			<%
			    ConnectionServeur cs = new ConnectionServeur();
			            int idPersonne = (Integer) request.getSession()
			                    .getAttribute("id");
			            int idProjet = Integer.parseInt(request.getParameter("idProjet"));
			            String status = cs.getStatus(idPersonne, idProjet);
			            if (status.equals("lecteur"))
			            {
			            }
			            else if (status.equals("en attente")){
			                
			            }
			            else{
			%>
			<%
			    
			%>
			<fieldset>
				<legend>Actions</legend>
				<a
					href="CreateRep.jsp?id=<%out.print(request.getParameter("id") + "&idProjet="
                                + request.getParameter("idProjet"));%>">Créer
					un nouveau répertoire</a><br /> <a
					href="AddFile.jsp?id=<%out.print(request.getParameter("id") + "&idProjet="
                                + request.getParameter("idProjet"));%>">Téléverser
					un fichier</a>
				<%
				    }
				        }
				%>
			</fieldset>
		</div>
		<div class="right">
			<p></p>
		</div>
	</div>
	<%
	    } else
	    {
	        response.sendRedirect("Connexion.jsp");
	    }
	%>
	<menu type="context" id="mymenu">
		<menuitem label="Ajouter un fichier"
			onclick="window.location.href = 'AddFile.jsp?root=<%out.print(request.getParameter("root"));%>'"
			icon="/images/refresh-icon.png"></menuitem>
		<menuitem label="Créer un nouveau répertoire"
			onclick="window.location.href = 'CreateRep.jsp?root=<%out.print(request.getParameter("root"));%>'"
			icon="/images/refresh-icon.png"></menuitem>
	</menu>
	</menu>
</body>
</html>