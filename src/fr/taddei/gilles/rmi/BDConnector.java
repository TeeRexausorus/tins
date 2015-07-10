package fr.taddei.gilles.rmi;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.*;

import fr.taddei.gilles.FileRep;

public class BDConnector
{
	public static String url_ = "jdbc:mysql://localhost/tins?autoReconnect=true";
	public static Connection co = null;

	public static Connection openConnection()
	{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				String username = "root";
				String password = "";

				co = (Connection) DriverManager.getConnection(url_, username,
						password);
				Statement st = (Statement) co.createStatement();
			} catch (ClassNotFoundException e)
			{
				System.out.println("il manque le driver mysql");
			} catch (SQLException e)
			{
				System.err.println(url_ + "impossible de se connecter a l'url");
			}
			return co;
	}

	// le string est la requete � effectuer, la Connection est la r�f�rence vers
	// la connexion ouverte,
	// le type permet de choisir la mani�re de cr�er le Statement
	public static ResultSet execRequete(String requete, Connection co)
	{
		if (co == null)
		{
			openConnection();
		}
		if (co != null)
		{
			ResultSet rs = null;
			try
			{
				Statement st = (Statement) co.createStatement();
				rs = st.executeQuery(requete);
			} catch (SQLException exRSMD)
			{
				System.err.println("fail requete : " + requete);
				exRSMD.printStackTrace();
			}

			return rs;
		} else
		{
			return null;
		}
	}

	public static int insertBD(String requete)
	{
		boolean fail = false;
		if (co == null)
		{
			openConnection();
		}
		Statement st = null;
		try
		{
			st = (Statement) co.createStatement();
		} catch (SQLException e1)
		{
			System.err.print("fail requete : " + requete);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int nb = 0;
		try
		{
			nb = st.executeUpdate(requete);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
			fail = true;
		}
		try
		{
			st.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
			fail = true;
		}
		if (!fail)
		{
			System.out.println("requete d'ajout/update finie");
		}
		return nb;
	}

	// la Connection est la r�f�rence vers la connexion ouverte
	public static void closeConnection(Connection co)
	{
		try
		{
			co.close();
			System.out.println("Connexion ferm�e!");
		} catch (SQLException e)
		{
			System.out.println("Impossible de fermer la connexion");
		}
	}
}
