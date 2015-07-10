package fr.taddei.gilles;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import fr.taddei.gilles.common.formulaire.Field;
import fr.taddei.gilles.common.formulaire.Formulaire;
import fr.taddei.gilles.common.formulaire.Hidden;
import fr.taddei.gilles.common.formulaire.TextField;

/**
 * Servlet implementation class Misc
 */
@WebServlet("/Misc")
public class Misc
{
	public static void send(HttpServletResponse response, String str)
			throws IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(str);
	}
	
	public static String formSearch(int project, int rep){
	    TextField tf = new TextField("search", "", "recherche", "");
        Hidden h1 = new Hidden("" + project, "project");
        Hidden h2 = new Hidden("" + rep, "rep");
	    ArrayList<Field> fields = new ArrayList<Field>();
	    fields.add(h1);
	    fields.add(h2);
        fields.add(tf);
	    Formulaire f = new Formulaire("POST", "formSearch", "Search", false, fields);
	    return f.toString();
	}
	
	public static void main(String[] args)
    {
        System.out.println(formSearch(42, 42));
    }
}
