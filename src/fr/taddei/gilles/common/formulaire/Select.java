package fr.taddei.gilles.common.formulaire;
import java.util.ArrayList;

public class Select extends Field
{
	private ArrayList<Option> _options;

	public Select(String name, String description, Option... options)
	{
		super(name, "", description);
		_options = new ArrayList<Option>();
		for (int i = 0; i < options.length; ++i)
		{
			_options.add(options[i]);
		}
	}
	
	public Select(String name, String description, ArrayList<Option> options)
	{
		super(name, "", description);
		_options = new ArrayList<Option>();
		_options.addAll(options);
	}

	public String toString()
	{
		String str = "<select name=\"" + this._name + "\">\n";
		for (int i =0; i < _options.size(); ++i)
		{
			str += _options.get(i) + "\n";
		}
		str += "</select>";
		return str;
	}

}
