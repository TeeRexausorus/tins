package fr.taddei.gilles.common.formulaire;

import java.util.ArrayList;

public class Formulaire
{
	private ArrayList<Field> _field;
	private String _method;
	private String _name;
	private String _action;
	private Boolean _multi = false;

	public Formulaire(String method, String name, String action,
			Boolean multipart, Field... _fields)
	{
		_name = name;
		_method = method;
		_action = action;
		_multi = multipart;
		_field = new ArrayList<Field>();
		for (int i = 0; i < _fields.length; ++i)
		{
			_field.add(_fields[i]);
		}
	}

	public Formulaire(String method, String name, String action,Boolean multipart,
			ArrayList<Field> fields)
	{
		_name = name;
		_method = method;
		_action = action;
		_multi = multipart;
		_field = new ArrayList<Field>();
		
		_field.addAll(fields);
	
	}

	public String toString()
	{
		String multipart = _multi ? "enctype=\"multipart/form-data\"" : "";
		String str = "<form action=\"" + this._action + "\" method = \""
				+ this._method + "\" name=\"" + this._name + "\" " + multipart
				+ " ><table>\n";
		for (int i = 0; i < _field.size(); ++i)
		{
			str += "<tr><td>" + _field.get(i).get_description() + "</td>";
			str += "<td>" + _field.get(i).toString() + "</td></tr>\n";
		}
		str += "</table></form>\n";
		return str;
	}
}
