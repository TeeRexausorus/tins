package fr.taddei.gilles.common.formulaire;


public class TextArea extends Field
{
	private String _placeHolder;

	public TextArea(String name, String placeHolder, String description)
	{
		super(name, "", description);
		this._placeHolder = placeHolder;
	}

	public String toString()
	{
		return "<textarea placeholder=\"" + _placeHolder + "\" name=\"" + _name
				+ "\"></textarea>";

	}
}
