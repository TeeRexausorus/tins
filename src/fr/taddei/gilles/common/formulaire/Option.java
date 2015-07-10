package fr.taddei.gilles.common.formulaire;

public class Option extends Field
{
	private String _text;

	public Option(String value, String text)
	{
		super("", value, "");
		_text = text;
	}

	public String toString()
	{
		return "<option value=\"" + this._value + "\">" + this._text
				+ "</option>";
	}
}
