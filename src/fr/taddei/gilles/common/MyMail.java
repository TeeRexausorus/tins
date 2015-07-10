package fr.taddei.gilles.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import fr.taddei.gilles.BDConnector;

public class MyMail
{

    private static String USER_NAME = "tinsroot";               // GMail user
                                                                 // name (just
                                                                 // the part
                                                                 // before
                                                                 // "@gmail.com")
    private static String PASSWORD  = "2lx|fxGx";               // GMail
                                                                 // password
    private static String RECIPIENT = "gilles.taddei@u-psud.fr";

    public static void main(String[] args)
    {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to =
        { RECIPIENT, RECIPIENT }; // list of recipient email addresses
        String subject = "Java send mail example";
        String body = "Welcome to JavaMail!";

        sendFromGMail(from, pass, to, subject, body);
    }

    public static void notifyUpdate(int idProjet)
    {
        String req = "select distinct mail, Pr.nom from personne P, contribution c, status s, "
                + "projet Pr where c.IDPersonne=P.ID AND c.IDStatus=s.ID AND c.IDProjet=Pr.ID AND c.IDProjet="
                + idProjet 
                + " AND s.nom <> 'en attente'"; /*
                */
        ResultSet rs = BDConnector.execRequete(req,
                BDConnector.openConnection());
        String nameProj = "";
        ArrayList<String> alMails = new ArrayList<String>();
        int i = 0;
        try
        {
            while (rs.next())
            {
                nameProj = rs.getString("nom");
                alMails.add(rs.getString("mail"));
            }
            sendFromGMail(
                    USER_NAME,
                    PASSWORD,
                    alMails,
                    "Ajout d'un fichier au projet " + nameProj,
                    "Bonjour, un fichier vient d'être ajouté au projet "
                            + nameProj
                            + ", n'oublie de venir voir les nouveautés sur http://taddei.zapto.org/TINS !");
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Works, reste a definir quand/comment envoyer les mails
    private static void sendFromGMail(String from, String pass, String[] to,
            String subject, String body)
    {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try
        {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++)
            {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++)
            {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae)
        {
            ae.printStackTrace();
        } catch (MessagingException me)
        {
            me.printStackTrace();
        }
    }

    private static void sendFromGMail(String from, String pass,
            ArrayList<String> to, String subject, String body)
    {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props);
        InternetAddress[] toAddress = new InternetAddress[to.size()];
        int j = 0;
        try
        {
            for (String mail : to)
            {
                toAddress[j++] = new InternetAddress(mail);
            }
            for (int i = 0; i < toAddress.length; i++)
            {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
                message.setSubject(subject);
                message.setText(body);
                Transport transport = session.getTransport("smtp");
                transport.connect(host, from, pass);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            }
        } catch (AddressException ae)
        {
            ae.printStackTrace();
        } catch (MessagingException me)
        {
            me.printStackTrace();
        }
    }
}