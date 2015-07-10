package fr.taddei.gilles.rmi;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import fr.taddei.gilles.FileRep;
import fr.taddei.gilles.common.formulaire.Button;
import fr.taddei.gilles.common.formulaire.Field;
import fr.taddei.gilles.common.formulaire.Formulaire;
import fr.taddei.gilles.common.formulaire.Hidden;
import fr.taddei.gilles.common.formulaire.Option;

public class ConnectionServeur implements RMIConnection
{
    public int insertUser(String nom, String prenom, String password,
            String mail, String login) throws RemoteException
    {
        String req = "INSERT INTO personne (`nom`, `prenom`, `login`, `password`, `mail`) SELECT '"
                + nom
                + "', '"
                + prenom
                + "', '"
                + login
                + "', PASSWORD('"
                + password + "')" + ", '" + mail + "';";
        // System.out.println(req);
        return BDConnector.insertBD(req);
    }

    public int insertProject(String project_name, String project_desc,
            int idPersonne) throws RemoteException
    {
        String root = "", req_project, req_ins_project, req_ins_file, req_ins_cont, req_id_proj;
        int nb_insert = 0;
        // Recuperation du repertoire racine du serveur
        String req_root = "SELECT * FROM config WHERE `key`='root'";
        ResultSet rs = BDConnector.execRequete(req_root,
                BDConnector.openConnection());
        try
        {
            while (rs.next())
                root = rs.getString("value");
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // creation/insertion du repertoire
        System.out.println("Insert Projet : " + root + "/" + project_name);
        req_ins_file = "INSERT INTO filerep (IDParent, nom, droit, path, file) SELECT 0, '"
                + FileRep.echapper(project_name)
                + "', 777,'"
                + FileRep.echapper(root + "/" + project_name) + "', 0;";
        System.out.println(req_ins_file);
        nb_insert += BDConnector.insertBD(req_ins_file);
        // Ajout du rï¿½pertoire racine du projet
        File f = new File(root + "/" + project_name);
        f.mkdir();
        /*
         * if (result) { System.out.println("Directory " + root + "/" +
         * project_name + " created"); }//
         */

        // Recuperation du repertoire racine du projet
        req_project = "SELECT * FROM filerep where idparent=0 and nom='"
                + FileRep.echapper(project_name) + "';";
        System.out.println(req_project);
        ResultSet rs2 = BDConnector.execRequete(req_project,
                BDConnector.openConnection());
        int idF = 0;
        try
        {
            while (rs2.next())
            {
                idF = rs2.getInt("id");
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Insertion du projet dans la base
        req_ins_project = "INSERT INTO projet (nom, description, IDRoot) SELECT '"
                + FileRep.echapper(project_name)
                + "', '"
                + FileRep.echapper(project_desc) + "'," + idF + ";";
        System.out.println(req_ins_project);
        nb_insert += BDConnector.insertBD(req_ins_project);

        // recuperation de l'id du projet
        req_id_proj = "SELECT * FROM projet WHERE nom = '"
                + FileRep.echapper(project_name) + "';";
        System.out.println(req_id_proj);
        ResultSet rs3 = BDConnector.execRequete(req_id_proj,
                BDConnector.openConnection());
        int id=0;
        try
        {
            while (rs3.next())
            {
                id = rs3.getInt("id");
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //modification du projet dans filerep
        String req_upd_filerep = "UPDATE filerep set project="+id+" WHERE ID="+idF;
        
        BDConnector.insertBD(req_upd_filerep);
        // Insertion de la "contribution" dans la base
        req_ins_cont = "INSERT INTO contribution (idPersonne, IDProjet, IDStatus) SELECT "
                + idPersonne
                + ", "
                + id
                + ", s.id from status s WHERE s.nom='Createur';";
        System.out.println(req_ins_cont);
        nb_insert += BDConnector.insertBD(req_ins_cont);
        return id;
    }

    public String listerProject(int idPersonne)
    {
        String str = "<fieldset><legend>Mes projets</legend><ul>\n", req;
        req = "SELECT p.nom, p.ID from projet p, contribution c where c.IDProjet=p.ID and c.IDPersonne="
                + idPersonne + ";";
        // str += req;
        ResultSet rs = BDConnector.execRequete(req,
                BDConnector.openConnection());
        try
        {
            while (rs.next())
            {
                str += "<li><a href=\"affProject.jsp?id=" + rs.getInt("ID")
                        + "\">" + rs.getString("nom") + "</a></li>\n";
            }
        } catch (SQLException e)
        {
            System.err.println(req);
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        str += "</ul></fieldset>\n";
        return str;
    }

    public String listerProjectNotIn(int idPersonne)
    {
        String str = "<fieldset><legend>Les autres projets</legend><ul>\n", req;
        req = "SELECT p.nom, p.ID from projet p where p.ID not in (SELECT p.ID from projet p, contribution c where c.IDProjet=p.ID and c.IDPersonne="
                + idPersonne + ");";
        // str += req;
        System.out.println(req);
        ResultSet rs = BDConnector.execRequete(req,
                BDConnector.openConnection());
        try
        {
            while (rs.next())
            {
                str += "<li><a href=\"affProject.jsp?id=" + rs.getInt("ID")
                        + "\">" + rs.getString("nom") + "</a></li>\n";
            }
        } catch (SQLException e)
        {
            System.err.println(req);
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        str += "</ul></fieldset>\n";
        return str;
    }

    public String[] connect(String login, String password)
            throws RemoteException
    {
        String[] ret = new String[5];
        String prenom = "", nom = "", login2 = "";
        Connection co = BDConnector.openConnection();
        ResultSet rs = BDConnector.execRequete(
                "SELECT * FROM personne WHERE login like binary '" + login
                        + "' AND password=PASSWORD('" + password + "');", co);
        int size = 0, id = 0;
        try
        {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
            while (rs.next())
            {
                id = rs.getInt("ID");
                prenom = rs.getString("prenom");
                nom = rs.getString("nom");
                login2 = rs.getString("login");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if (size > 0)
        {
            ret[0] = "" + size;
            ret[1] = "" + id;
            ret[2] = "" + login2;
            ret[3] = "" + nom;
            ret[4] = "" + prenom;
        } else
        {
            ret[0] = "0";
        }
        return ret;
    }

    public String listerRepProj(int idProject, int idPersonne)
            throws RemoteException
    {
        String str = "<fieldset class=\"center\">\n"
                + "<legend>Affichage d'un projet</legend>\n" + "<fieldset>\n"
                + "<legend>Description</legend>\n";
        String req_app_proj = "select c.IDPersonne, c.idStatus from contribution c where c.IDPersonne="
                + idPersonne + " and c.IDprojet=" + idProject + ";";
        System.out.println(req_app_proj);
        String req_root = "select f.nom, f.path, f.id, p.description from filerep f, projet p where p.idRoot=f.id and p.id="
                + idProject + ";";
        System.out.println(req_app_proj);
        ResultSet rs = BDConnector.execRequete(req_root,
                BDConnector.openConnection());
        try
        {
            while (rs.next())
            {
                str += rs.getString("description") + "</fieldset>\n";

                ResultSet rsApprob = BDConnector.execRequete(req_app_proj,
                        BDConnector.openConnection());

                if (rsApprob.isBeforeFirst())
                {
                    rsApprob.next();
                    if (rsApprob.getInt("idStatus") != 4)
                        str += "<fieldset>\n<legend>Contenu</legend>\n"
                                + "<a href=\"affRep.jsp?" + "id="
                                + rs.getInt("id") + "&idProjet="+idProject+"\">" + rs.getString("nom")
                                + "</a>\n</fieldset>\n";
                } else
                {
                    String req_name = "select login from personne where ID=\""
                            + idPersonne + "\"";
                    System.out.println(req_name);
                    ResultSet rsName = BDConnector.execRequete(req_name,
                            BDConnector.openConnection());
                    rsName.next();
                    
                    str += "<a href=\"AddContri?login=" + rsName.getString("login")+ "&id="
                            + idProject + "&status=4\">demander l'accès</a>";
                }
                str += "<fieldset>\n"
                        + "<legend>Contributeurs</legend><p class=\"contributeur\">\n";
                String req_collab = "SELECT p.login, s.nom AS status FROM contribution c, personne p, status s WHERE c.IDProjet="
                        + idProject
                        + " AND p.ID=c.IDPersonne AND s.ID=c.IDStatus;";
                ResultSet rs2 = BDConnector.execRequete(req_collab,
                        BDConnector.openConnection());
                while (rs2.next())
                {
                    str += rs2.getString("login") + " "
                            + rs2.getString("status") + "<br/>\n";
                }
                str += "</p>";
                if (getStatus(idPersonne, idProject).equals("Createur"))
                {
                    str += "<br/><a href=\"addContri.jsp?id=" + idProject
                            + "\">Ajouter un contributeur</a></fieldset>";
                    ArrayList<Field> fields = new ArrayList<Field>();
                    fields.add(new Hidden("" + idProject, "idProject"));
                    // *
                    fields.add(new Button("btnSearch","Chercher un ficher dans ce projet","",""));
                    fields.add(new Button("btnDelete", "Supprimer ce projet",
                            "", "return deleteProject();"));// */
                    Formulaire form = new Formulaire("POST", "del",
                            "HandleProject", false, fields);
                    str += form;
                    /*
                     * ArrayList<Field> fields2 = new ArrayList<Field>();
                     * fields2.add(new Hidden("" + idProject, "idProject"));
                     * 
                     * fields2.add(new Button("btnCrawl", "Indexer ce projet",
                     * "", "return crawlProject();")); Formulaire form2 = new
                     * Formulaire("POST", "del", "CrawlProject", false,
                     * fields2); str += form2;//
                     */

                } else
                {
                    str += "</fieldset>";
                }
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList<Option> getStatus() throws RemoteException
    {
        ArrayList<Option> opts = new ArrayList<Option>();
        String req_status = "SELECT * from status;";
        ResultSet rs = BDConnector.execRequete(req_status,
                BDConnector.openConnection());
        try
        {
            while (rs.next())
            {
                opts.add(new Option(rs.getString("ID"), rs.getString("nom")));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return opts;
    }

    public void insertContri(String login, int idStatus, int idProjet)
            throws RemoteException
    {
        String req_ins = "INSERT INTO contribution (IDPersonne, IDProjet, IDStatus) "
                + "SELECT p.id, "
                + idProjet
                + " ,"
                + idStatus
                + " FROM personne p WHERE p.login='"
                + FileRep.echapper(login)
                + "' ON DUPLICATE KEY UPDATE IDStatus=" + idStatus + ";";
        System.out.println(req_ins);
        BDConnector.insertBD(req_ins);
    }

    public String getRep(int id) throws RemoteException
    {
        String ret = "";
        String req = "SELECT * FROM filerep WHERE id=" + id + ";";
        ResultSet rs = BDConnector.execRequete(req,
                BDConnector.openConnection());
        try
        {
            while (rs.next())
            {
                ret = rs.getString("path");
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }

    public String getName(int id) throws RemoteException
    {
        String ret = "";
        String req = "SELECT * FROM filerep WHERE id=" + id + ";";
        ResultSet rs = BDConnector.execRequete(req,
                BDConnector.openConnection());
        try
        {
            while (rs.next())
            {
                ret = rs.getString("nom");
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }

    public String getStatus(int idPersonne, int idProjet)
    {
        String ret = "";
        String strCreator = "SELECT s.nom FROM status s, contribution c WHERE s.ID=c.IDStatus "
                + " AND c.IDProjet="
                + idProjet
                + " AND c.IDPersonne="
                + idPersonne + ";";
        System.out.println(strCreator);
        ResultSet rsCrea = BDConnector.execRequete(strCreator,
                BDConnector.openConnection());
        try
        {
            if (rsCrea.isBeforeFirst())
            {
                rsCrea.next();
                ret = rsCrea.getString("nom");
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // System.out.println(ret);
        return ret;
    }

    public static void main(String[] args)
    {
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }
        try
        {
            System.setSecurityManager(null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
