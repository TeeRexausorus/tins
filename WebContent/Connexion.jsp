<%@page import="fr.taddei.gilles.common.formulaire.Button"%>
<%@page import="fr.taddei.gilles.common.formulaire.Password"%>
<%@page import="fr.taddei.gilles.common.formulaire.TextField"%>
<%@page import="fr.taddei.gilles.common.formulaire.Hidden"%>
<%@page import="fr.taddei.gilles.common.formulaire.Field"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fr.taddei.gilles.common.formulaire.Formulaire"%>
<%@page
	import="com.sun.xml.internal.messaging.saaj.packaging.mime.Header"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="favicon.ico" />
<link href="styles.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TINS - Connexion</title>
</head>
<body>
	<div class="global">
		<div class="top">
			<p class="left">
				<%@include file="top_left.jsp"%>
			</p>
			<p class="right">
				<%@include file="top_right.jsp"%>
			</p>
		</div>
		<div class="center">
			<%
				System.out.println(request.getParameter("msg"));
				if (request.getParameter("msg") != null && request.getParameter("msg").equals("fail"))
				{
					out.println("Login ou mot de passe incorrect");
				}
				ArrayList<Field> arrayForm = new ArrayList<Field>();
				arrayForm.add(new Hidden(
						request.getHeader("Referer") == null ? "index.jsp"
								: request.getHeader("Referer"), "prev"));

				arrayForm.add(new TextField("login", "", "login", "login : "));

				arrayForm.add(new Password("password", "password",
						"Mot de passe : "));

				arrayForm.add(new Button("btnGo", "Go !", ""));

				Formulaire f = new Formulaire("POST", "form_qcm", "Dispatcher",
						false, arrayForm);

				out.println(f);
			%>
		</div>
	</div>
</body>
</html>