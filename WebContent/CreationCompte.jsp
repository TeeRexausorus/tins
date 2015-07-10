<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="favicon.ico" />
<link href="styles.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TINS - Inscription</title>
<script type="text/javascript">
	function test() {
		var good = true;
		var nom = document.getElementById("nom");
		var login = document.getElementById("login");
		var prenom = document.getElementById("prenom");
		var pass1 = document.getElementById("password");
		var pass2 = document.getElementById("password2");
		var mail = document.getElementById("mail");
		if (nom.value == "" || prenom.value == "" || pass1.value == ""
				|| pass2.value == "" || login.value == "" || pass.value != pass2.value
				|| mail.value == "") {
			good = false;

		}
		alert(good);

		return good;
	}
</script>
</head>
<body>
	<div class="global">
		<div class="top">
					<p class="right">
				<%@include file="top_right.jsp"%>
			</p>
			<p class="left">
				<%@include file="top_left.jsp"%>
			</p>
		
		</div>
		<fieldset class="center">
			<legend>Inscription</legend>
			<form action="InsertUser" method="post" onsubmit="return test();">
				<table>
					<tr>
						<td>login :</td>
						<td><input placeholder="Choisissez un Login" id="login" name="login" type="text" /></td>
						<td><span id="span_login"></span></td>
					</tr>
					<tr>
						<td>Nom :</td>
						<td><input placeholder="Entrez votre nom" id="nom" name="nom" type="text" /></td>
						<td><span id="span_nom"></span></td>
					</tr>
					<tr>
						<td>Prénom :</td>
						<td><input placeholder="Entrez votre prénom" id="prenom" name="prenom" type="text" /></td>
						<td><span id="span_prenom"></span></td>
					</tr>
					<tr>
						<td>Mot de passe :</td>
						<td><input placeholder="Choisissez un mot de passe" id="password" name="password" type="password" /></td>
						<td><span id="span_password"></span></td>
					</tr>
					<tr>
						<td>Répétez le mot de passe :</td>
						<td><input placeholder="Répétez votre mot de passe" id="password2" name="password2" type="password" /></td>
						<td><span id="span_password2"></span></td>
					</tr>
					<tr>
						<td>Adresse mail :</td>
						<td><input placeholder="Votre eMail" id="mail" name="mail" type="text" /></td>
						<td><span id="span_mail"></span></td>
					</tr>
					<tr>
						<td><input type="submit" value="Inscription !" /></td>
					</tr>
				</table>
			</form>
		</fieldset>
	</div>
</body>
</html>