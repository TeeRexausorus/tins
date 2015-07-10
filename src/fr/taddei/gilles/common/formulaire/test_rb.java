package fr.taddei.gilles.common.formulaire;

import java.util.ArrayList;

public class test_rb
{
	private static ArrayList<String> _str;
	public test_rb(String...strgs)
	{
		_str = new ArrayList<String>();
		for (int i = 0; i < strgs.length; ++i)
		{
			_str.add(strgs[i]);
		}
	}
	
	public void write_shit()
	{
		for (int i = 0; i < _str.size(); ++i)
		{
			System.out.println(_str.get(i));
		}
	}
	
	public static void main(String[] args)
	{
		test_rb toto = new test_rb("azerty", "truc", "poney", "Hello World maggle");
		toto.write_shit();
	}
}
