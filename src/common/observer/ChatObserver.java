package common.observer;

import common.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public interface ChatObserver extends Remote {

    /**
     *
     * @param username
     * @param message
     * @return
     * @throws RemoteException
     */
    boolean update(String username, String message) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    ArrayList<User> getOnlineUsers() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    String getUserName() throws RemoteException;

    /**
     *
     * @param clientLists
     * @return
     * @throws RemoteException
     */
    boolean updateUI(ArrayList<String> clientLists) throws RemoteException;

}
