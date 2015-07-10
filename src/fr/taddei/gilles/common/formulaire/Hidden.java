package fr.taddei.gilles.common.formulaire;
public class Hidden extends Field
{

	public Hidden(String value, String name)
	{
		super(name, value, "");
	}

	public String toString()
	{
		return "<input type=\"hidden\"name=\"" + this._name + "\" value=\""
				+ this._value + "\"/>";
	}
}
