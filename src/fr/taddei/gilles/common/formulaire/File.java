package fr.taddei.gilles.common.formulaire;


public class File extends Field
{

	public File(String name, String description)
	{
		super(name, "", description);
		// TODO Auto-generated constructor stub
	}

	public String toString()
	{
		return "<input type=\"file\" name=\"" + _name + "\"/>";
	}
}
