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
<title>Insert title here</title>
</head>
<body>
	<%! ConnectionServeur cs = new ConnectionServeur(); %>
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
			<a href="createProject.jsp">Créer un projet</a><br />
		</div>
		<div class="center">
			<%
				ArrayList<Field> fields = new ArrayList<Field>();
				fields.add(new Hidden("" + request.getParameter("id"), "id"));
				fields.add(new TextField("login", "", "Login du contributeur",
						"Login du contributeur à ajouter"));
				fields.add(new Select("status", "Statut du contributeur",
						cs.getStatus()));
				fields.add(new Button("btnContri", "Ajouter le contributeur !", ""));
				Formulaire f = new Formulaire("POST", "form_add_contri",
						"AddContri", false, fields);
				out.println(f);
			%>
		</div>
	</div>
</body>
</html>