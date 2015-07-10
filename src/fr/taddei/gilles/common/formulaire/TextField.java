package fr.taddei.gilles.common.formulaire;


public class TextField extends Field
{
	private String _placeHolder;

	public TextField(String name, String value, String placeHolder, String description)
	{
		super(name, value, description);
		this._placeHolder = placeHolder;
	}

	public String toString()
	{
		return "<input type=\"text\" placeholder=\"" + _placeHolder
				+ "\" name=\"" + _name + "\" value=\"" + _value + "\"/>";

	}
}
