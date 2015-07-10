package fr.taddei.gilles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.*;

public class BDConnector
{
    //public static String     url_ = "jdbc:mysql://localhost/tins?autoReconnect=true";
    public static String     url_ = "jdbc:mysql://localhost/roneos?autoReconnect=true";
    public static Connection co   = null;

    public static Connection openConnection()
    {
        if (co != null)
        {
            return co;
        } else
        {

            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                String username = "root";
                //String password = "";
                String password = "roneos";

                co = (Connection) DriverManager.getConnection(url_, username,
                        password);
                Statement st = (Statement) co.createStatement();
            } catch (ClassNotFoundException e)
            {
                System.out.println("il manque le driver mysql");
            } catch (SQLException e)
            {
                System.out.println("impossible de se connecter à l'url");
            }

            return co;
        }
    }

    // le string est la requete à effectuer, la Connection est la référence vers
    // la connexion ouverte,
    // le type permet de choisir la manière de créer le Statement
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
                System.out.println("fail requete : " + requete);
                exRSMD.printStackTrace();
            }
            if (rs == null)
            {
                rs = execRequete(requete, co);
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

    // la Connection est la référence vers la connexion ouverte
    public static void closeConnection(Connection co)
    {
        try
        {
            co.close();
            System.out.println("Connexion fermée!");
        } catch (SQLException e)
        {
            System.out.println("Impossible de fermer la connexion");
        }
    }
}
