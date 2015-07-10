<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="styles.css" rel="stylesheet" />
<link rel="icon" href="favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Création d'un projet</title>
</head>
<body>
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
		<div class="left"></div>
		<fieldset class="center">
			<legend>Création d'un projet</legend>

			<form name="form_project" action="CreateProject" method="post"
				enctype="multipart/form-data">
				<table>
					<tr>
						<td>Nom du projet :</td>
						<td><input placeholder="Nom de votre projet"
							name="project_name" type="text" /></td>
					</tr>
					<tr>
						<td>Description du projet :</td>
						<td><textarea placeholder="Description de votre projet"
								name="project_desc" type="text"></textarea></td>
					</tr>
					<!-- <tr>
						<td><label>Fichier (zip contenant votre projet) :</label></td>
						<td><input type="file" name="file" accept=".zip" /></td>
					</tr>-->
					<tr>
						<td><input type="submit" value="créer !" /></td>
					</tr>
				</table>
			</form>
		</fieldset>

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