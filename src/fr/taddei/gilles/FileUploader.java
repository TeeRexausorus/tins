package fr.taddei.gilles;

// Import required java libraries
import java.io.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import fr.taddei.gilles.common.Constants;
import fr.taddei.gilles.common.MyMail;

@MultipartConfig
public class FileUploader extends HttpServlet
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        // TODO Auto-generated method stub
        super.init(config);
        filePath = getServletContext().getInitParameter("file-upload");
    }

    private String filePath;

    public void init()
    {
        // Get the file location where it would be stored.
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        filePath = request.getParameter("root") != null ? request
                .getParameter("root") : "D:\\TINS";
        Part filePart = request.getPart("file"); // Retrieves <input type="file"
                                                 // name="file">
        int idProj = (request.getParameter("idProjet") != null) ? Integer
                .parseInt(request.getParameter("idProjet")) : 0;
        String filename = getFilename(filePart);
        InputStream filecontent = filePart.getInputStream();
        if (filename.endsWith(".zip"))
        {
            writeInputStreamToFile(filename, filecontent, "D:\\temp");
            File f = new File(filePath + "\\"
                    + filename.substring(0, filename.length() - 4));
            f.mkdir();
            File zip = new File("D:\\temp" + "/" + filename);
            MyZip.unzip(zip, f);
            FileRep.crawl(f, idProj);
        } else
        {
            // System.out.println(filePath + "/" + filename);
            writeInputStreamToFile(filename, filecontent, filePath);
            FileRep.crawl(new File(filePath + "/" + filename), idProj);
        }
        Misc.send(response, "ok");
        response.sendRedirect("AddFile.jsp?id=" + request.getParameter("id")
                + "&res=OK&idProjet="+idProj);
        MyMail.notifyUpdate(idProj);
    }

    private static void writeInputStreamToFile(String filename,
            InputStream filecontent, String filepath) throws IOException
    {
        // System.out.println(filepath + "\\" + filename);
        File f = new File(filepath + "/" + filename);
        if (!f.exists())
        {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            int bufferSize = 1;
            byte[] buffer = new byte[bufferSize];
            while (filecontent.read(buffer) != -1)
            {
                fos.write(buffer);
            }
            System.out.println(filepath + "/" + filename + " écrit");
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
}