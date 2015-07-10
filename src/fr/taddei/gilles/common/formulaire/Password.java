package fr.taddei.gilles.common.formulaire;

public class Password extends Field
{

	private String _placeHolder;

	public Password(String name, String placeHolder, String description)
	{
		super(name, "", description);
		this._placeHolder = placeHolder;
	}
	
	public String toString()
	{
		return "<input type=\"password\" placeholder=\"" + _placeHolder + "\" name=\"" + _name
				+ "\"/>";

	}


}
