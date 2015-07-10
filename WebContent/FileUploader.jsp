<form action="FileUploader" method="post" enctype="multipart/form-data"
	multiple="multiple">
	<table>
		<tr>
		<td>
			<input type="hidden" name="idProjet" value="+<% out.println(request.getParameter("id")); %>+"/>
			
		</td><td></td>
		</tr>
		<tr>
			<td><label>Fichier :</label></td>
			<td><input type="file" name="file" /></td>
		</tr>
		<tr>
			<td><input type="submit" value="Go" /></td>
		</tr>
	</table>
</form>
