package fr.taddei.gilles.common.formulaire;
public class Checkbox extends Field
{

	public Checkbox(String name, String value, String description)
	{
		super(name, value, description);
		// TODO Auto-generated constructor stub
	}

	public String toString()
	{
		return "<input type=\"checkbox\" name=\"" + this._name + "\" value=\""+this._value+"\">";
	}
}
