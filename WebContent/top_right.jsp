<%
	if (request.getSession().getAttribute("id") != null)
	{
%>
	<a href="Disconnect">Déconnexion</a><br/>
	<!-- <a href="account.jsp">Mon compte</a>-->
<%
	} else
	{
%>
<a href="Connexion.jsp">Se connecter</a>
<br />
<a href="CreationCompte.jsp">S'Inscrire</a>
<br />
<%
	}
%>