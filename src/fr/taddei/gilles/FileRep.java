package fr.taddei.gilles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import fr.taddei.gilles.common.Constants;
import fr.taddei.gilles.rmi.*;

public class FileRep
{
    public static String echapper(String str)
    {
        return str.replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\\\"", "\\\\\\\"").replaceAll("'", "\\\\'");
    }

    public static void handleFile(HttpServletRequest request,
            HttpServletResponse response, String filePath, int idProj)
            throws ServletException, IOException
    {
        System.out.println("*******************************");
        File f = new File(filePath);
        if (request.getPart("file") != null)
        {
            Part filePart = request.getPart("file"); // Retrieves <input
                                                     // type="file"
                                                     // name="file">
            System.out.println(filePart.toString());
            String filename = getFilename(filePart);
            System.out.println("*" + filename + "*");
            InputStream filecontent = filePart.getInputStream();
            if (filename != null && filename != "")
            {
                writeInputStreamToFile(filename, filecontent, filePath);
                f.mkdir();
                if (filename.endsWith(".zip"))
                {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>coucou");
                    File zip = new File(filePath + "/" + filename);
                    MyZip.unzip(zip, f);
                    zip.delete();
                }
                FileRep.crawl(f, idProj);
            }
        } else
        {
            System.out.println("*else*");
            FileRep.crawl(f, idProj);
        }
    }

    public static String getRoot()
    {
        // recuperation de la racine du serveur
        String root = "";
        String req = "SELECT * FROM config WHERE `key`='root';";
        ResultSet rs = BDConnector.execRequete(req,
                BDConnector.openConnection());
        try
        {
            if (rs != null)
            {
                while (rs.next())
                {
                    root = rs.getString("value");
                }
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return root;
    }

    public static void deleteCascadeFilerep(int idRoot)
    {
        String children = "SELECT * FROM `filerep` WHERE idParent=" + idRoot
                + ";";
        System.out.println(children);
        ResultSet rs = BDConnector.execRequete(children,
                BDConnector.openConnection());
        if (rs != null)
        {
            try
            {
                while (rs.next())
                {
                    System.out.println(rs.getBoolean("file"));
                    if (rs.getBoolean("file"))
                    {
                        deleteCascadeFilerep(rs.getInt("id"));
                    }
                    String dltChild = "DELETE FROM filerep where id="
                            + rs.getInt("id") + ";";
                    System.out.println(dltChild);
                    BDConnector.insertBD(dltChild);
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            String dltChild = "DELETE FROM filerep where id=" + idRoot + ";";
            System.out.println(dltChild);
            BDConnector.insertBD(dltChild);
        }
        String dltChild = "DELETE FROM filerep where id=" + idRoot + ";";
        System.out.println(dltChild);
        BDConnector.insertBD(dltChild);
    }

    private static void writeInputStreamToFile(String filename,
            InputStream filecontent, String filepath) throws IOException
    {
        File f = new File(filepath + "/" + filename);
        System.out.println(">>>>>>>" + filepath + "/" + filename);
        if (!f.exists())
        {
            System.out.println(filepath + "/" + filename);
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            int bufferSize = 1;
            byte[] buffer = new byte[bufferSize];
            while (filecontent.read(buffer) != -1)
            {
                fos.write(buffer);
            }
            System.out.println(filepath + "/" + filename + " �crit");
            fos.close();
        }
    }

    private static String getFilename(Part part)
    {
        for (String cd : part.getHeader("content-disposition").split(";"))
        {
            if (cd.trim().startsWith("filename"))
            {
                String filename = cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1)
                        .substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public static byte[] getEncodingBytes(String str)
    {
        byte[] tab;
        try
        {
            tab = str.getBytes(Constants.ENCODING_TYPE);
        } catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("ERROR : " + Constants.ENCODING_TYPE
                    + " not supported !");
        }
        return tab;
    }

    public static String getEncodingString(byte[] tab)
    {
        String str;
        try
        {
            str = new String(tab, Constants.ENCODING_TYPE);
        } catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("ERROR : " + Constants.ENCODING_TYPE
                    + " not supported !");
        }
        return str;
    }

    public static String getEncodingString(byte[] tab, int offset, int length)
    {
        String str;
        try
        {
            str = new String(tab, offset, length, Constants.ENCODING_TYPE);
        } catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("ERROR : " + Constants.ENCODING_TYPE
                    + " not supported !");
        }
        return str;
    }

    public static String listerRep(File repertoire)
    {
        String ret = "";
        try
        {
            if (repertoire.getCanonicalFile().isDirectory())
            {
                int i;
                String[] listefichiers;
                listefichiers = repertoire.list();
                ret += "<ul>\n";
                /*
                 * ret += "<li><a href=\"affRep.jsp?root=" +
                 * repertoire.getCanonicalFile() + "\\.." + "\">..</a></li>";//
                 */
                if (listefichiers != null)
                {
                    for (i = 0; i < listefichiers.length; i++)
                    {
                        ret += "<li>\n";
                        File f = new File(repertoire.getAbsolutePath() + "/"
                                + listefichiers[i]);
                        if (f.getCanonicalFile().isDirectory())
                        {
                            // ret += listerRep(f);
                            ret += "<a href=\"affRep.jsp?root="
                                    + repertoire.getCanonicalPath() + "/"
                                    + listefichiers[i] + "\">"
                                    + listefichiers[i] + "</a><br/>\n";
                        } else
                        {

                            ret += "<a href=\"file://"
                                    + repertoire.getCanonicalPath() + "/"
                                    + listefichiers[i] + "\"> "
                                    + listefichiers[i] + "</a><br/>\n";
                        }
                        ret += "</li>\n";
                    }
                }
                ret += "</ul>\n";
            }
        } catch (IOException e)
        {

        }
        return ret;
        // return "";
    }

    public static String listerRepBD(int IDRep)
    {
        String str = "", req = "SELECT * FROM filerep WHERE idParent=" + IDRep
                + ";";
        System.out.println(req);
        ResultSet rs = BDConnector.execRequete(req,
                BDConnector.openConnection());
        // str += ""+IDRep;

        try
        {
            str += "<ul>\n";
            System.out.println("kikoooo");
            if (rs != null)
            {
                while (rs.next())
                {
                    if (!rs.getBoolean("file"))
                    {
                        str += "<li><a href=\"affRep.jsp?id=" + rs.getInt("ID")
                                + "&idProjet=" + rs.getInt("project") + "\">"
                                + rs.getString("nom") + "</a></li>\n";
                    } else
                    {
                        str += "<li><a href=\"Downloader?id=" + rs.getInt("ID")
                                + "&idProjet=" + rs.getInt("project") + "\">"
                                + rs.getString("nom") + "</a></li>\n";
                    }
                }
            } else
            {
                System.out.println("failBD");
            }
        } catch (SQLException e)
        {
            System.out.println(req);
            // e.printStackTrace();
        }
        // */
        str += "</ul>";
        return str;
    }

    public static String crawl(File repertoire, int idProj)
    {
        String ret = "";
        String req = "";
        System.out.println("crawling...");
        try
        {
            if (repertoire.getCanonicalFile().isDirectory())
            {
                System.out.println(repertoire.getCanonicalFile());
                System.out.println("Is Directory");

                int i;
                String[] listefichiers;
                listefichiers = repertoire.list();
                if (listefichiers != null)
                {
                    System.out.println("Has Liste ; taille : "
                            + listefichiers.length);

                    if (listefichiers.length > 0)
                    {
                        for (i = 0; i < listefichiers.length; i++)
                        {
                            File f = new File(repertoire.getAbsolutePath()
                                    + "/" + listefichiers[i]);
                            if (f.getCanonicalFile().isDirectory())
                            {
                                req = "INSERT INTO filerep (IDParent, nom, droit, path, file, project) SELECT ID, '"
                                        + echapper(f.getName())
                                        + "', 777,'"
                                        + echapper(f.getCanonicalPath())
                                        + "', 0, "
                                        + idProj
                                        + " FROM filerep WHERE path like '"
                                        + echapper(repertoire
                                                .getCanonicalPath())
                                        + "' ON DUPLICATE KEY UPDATE nom='"
                                        + echapper(f.getName()) + "';";
                                System.out.println(">>>" + req);
                                BDConnector.insertBD(req);
                                ret += req + "<br/><br/>";
                                ret += crawl(f, idProj);
                            }

                            else
                            {
                                System.out.println("doesn't have list");
                                req = "INSERT INTO filerep (IDParent, nom, droit, path, file, project) SELECT ID, '"
                                        + echapper(f.getName())
                                        + "', 777,'"
                                        + echapper(f.getCanonicalPath())
                                        + "', 1 , "
                                        + idProj
                                        + " FROM filerep WHERE path like '"
                                        + echapper(repertoire
                                                .getCanonicalPath())
                                        + "' ON DUPLICATE KEY UPDATE nom='"
                                        + echapper(f.getName()) + "';";
                                System.out.println(">>>" + req);
                                BDConnector.insertBD(req);
                                ret += req + "<br/><br/>";
                            }
                        }
                    } else
                    {
                        req = "INSERT INTO filerep (IDParent, nom, droit, path, file, project) SELECT ID, '"
                                + echapper(repertoire.getName())
                                + "', 777,'"
                                + echapper(repertoire.getCanonicalPath())
                                + "', 0, "
                                + idProj
                                + " FROM filerep"
                                + " WHERE path like '"
                                + echapper(repertoire.getParent())
                                + "' ON DUPLICATE KEY UPDATE nom='"
                                + echapper(repertoire.getName()) + "';";
                        System.out.println(">>>" + req);
                        BDConnector.insertBD(req);
                    }
                }

            } else
            {
                System.out.println("Is File");

                req = "INSERT INTO filerep (IDParent, nom, droit, path, file, project) SELECT ID, '"
                        + echapper(repertoire.getName())
                        + "', 777,'"
                        + echapper(repertoire.getCanonicalPath())
                        + "', 1, "
                        + idProj
                        + " FROM filerep WHERE path like '"
                        // + repertoire.getAbsolutePath()
                        + echapper(repertoire.getParent())
                        + "' ON DUPLICATE KEY UPDATE nom='"
                        + echapper(repertoire.getName()) + "';";
                System.out.println(">>>" + req);
                BDConnector.insertBD(req);
                ret += req + "<br/><br/>";
            }
        } catch (IOException e)
        {

        }
        System.out.println();
        return ret;
    }

    public static String listerFiles(File repertoire)
    {
        String ret = "";
        try
        {
            int i;
            String[] listefichiers;
            if (repertoire.isDirectory())
            {

                listefichiers = repertoire.list();
                ret += "<ul>\n";
                for (i = 0; i < listefichiers.length; i++)
                {
                    if ((new File(repertoire.getAbsolutePath() + "/"
                            + listefichiers[i]).getCanonicalFile().isFile()))
                    {
                        System.out.println(listefichiers[i]);
                        ret += "<li>" + listefichiers[i] + "\n";
                    }
                    ret += "</li>\n";
                }
                ret += "</ul>\n";
            }
        } catch (IOException e)
        {

        }

        // return "";
        return ret;
    }

    public static void download(HttpServletResponse response, int idFile)
    {
        String filename, filepath;
        ConnectionServeur bdw = new ConnectionServeur();
        try
        {
            filepath = bdw.getRep(idFile);
            filename = bdw.getName(idFile);
            File f = new File(filepath);
            System.out.println("Telechargement de : " + filepath);
            if (f.exists())
            {

                // recuperation du fichier par bytes

                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + f.getName() + "\"");
                System.out
                        .println(f.getTotalSpace() + " _ " + f.getFreeSpace());
                response.setHeader("Content-Length", "" + (f.length()));
                // int t = (int) (f.getTotalSpace() - f.getFreeSpace());
                try
                {
                    InputStream in = new FileInputStream(f);
                    OutputStream outs = response.getOutputStream();
                    int bit = in.read();
                    while ((bit) >= 0)
                    {
                        outs.write(bit);
                        bit = in.read();
                    }
                    outs.flush();
                    in.close();
                } catch (Exception e)
                {
                }
            }
        } catch (RemoteException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
