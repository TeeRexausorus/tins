package fr.taddei.gilles.common.formulaire;
public class Radio extends Field
{

	public Radio(String name, String value, String description)
	{
		super(name, value, description);
		// TODO Auto-generated constructor stub
	}

	public String toString()
	{
		return "<input type=\"radio\" name=\"" + this._name + "\" value=\""+this._value+"\">";
	}
}
