<%@page import="fr.taddei.gilles.rmi.ConnectionServeur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="favicon.ico" />
<link href="styles.css" rel="stylesheet" />
<meta charset="ISO-8859-1">
<title>TINS Is Not Sharepoint</title>
</head>
<%
	/**
	 @TODO
	 - Interface utilisateur : 
	 - changement de mot de passe
	 - photo de profil
	 - Chat/Messagerie
	 - 
	 - Interface Administrateur :
	 - Gérer les status/droits
	 - 

	 */
%>
<body>
	<div class="global">
		<div class="top">
			<p class="left">
				<%@include file="top_left.jsp"%>
			</p>
			<p class="right">
				<%
					if (request.getSession().getAttribute("id") == null)
					{
				%>
				<a href="Connexion.jsp">Se connecter</a><br /> <a
					href="CreationCompte.jsp">S'Inscrire</a><br />
				<%
					} else
					{
				%>
				<%@include file="top_right.jsp"%>

				<%
					}
				%>
			</p>
		</div>
		<div class="left">
			<%
				if (request.getSession().getAttribute("id") != null)
				{
					ConnectionServeur cs = new ConnectionServeur();
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
		<div class="center"> 
			<%
				if (request.getSession().getAttribute("id") != null)
				{
			%>
			<fieldset>
				<legend>Bienvenue !</legend>
				Vous êtes maintenant connecté sur TINS, le site de partage
				collaboratif.<br /> Vous pouvez maintenant créer un nouveau projet,
				ou en rejoindre un après y avoir été ajouté.<br />
			</fieldset>
			<%
				} else
				{
			%>
			<fieldset>
				<legend>Bienvenue !</legend>
				Soyez la bienvenue sur TINS (TINS Is Not Sharepoint), le site de partage
				collaboratif.<br />
				Vous pouvez dès à présent créer un compte, après quoi vous pourrez créer un nouveau projet,
				ou en rejoindre un après y avoir été ajouté en tant que contributeur.<br />
			</fieldset>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>