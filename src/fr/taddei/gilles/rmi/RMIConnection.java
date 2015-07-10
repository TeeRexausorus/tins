package fr.taddei.gilles.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import fr.taddei.gilles.common.formulaire.Option;

public interface RMIConnection extends Remote
{
	public int insertUser(String nom, String prenom, String password,
			String mail, String login) throws RemoteException;

	public int insertProject(String project_name, String project_desc,
			int idPersonne) throws RemoteException;

	public String listerProject(int idPersonne) throws RemoteException;

	public String[] connect(String login, String password)
			throws RemoteException;

	public String listerRepProj(int idProject, int idPersonne)
			throws RemoteException;

	public String getStatus(int idPersonne, int idProjet)
			throws RemoteException;

	public ArrayList<Option> getStatus() throws RemoteException;

	public void insertContri(String login, int idStatus, int idProjet)
			throws RemoteException;

	public String getRep(int id) throws RemoteException;

	public String getName(int id) throws RemoteException;

}
