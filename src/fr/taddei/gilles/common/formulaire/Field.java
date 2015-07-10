package fr.taddei.gilles.common.formulaire;


public class Field
{
	protected String _value;
	protected String _name;
	protected String _description;
	public Field(String name, String value, String description)
	{
		this._name = name;
		this._value = value;
		this._description = description;
	}
	public String get_description()
	{
		// TODO Auto-generated method stub
		return this._description;
	}
	public String get_name()
	{
		// TODO Auto-generated method stub
		return this._name;
	}
	public String get_value()
	{
		// TODO Auto-generated method stub
		return this._description;
	}
	
}
