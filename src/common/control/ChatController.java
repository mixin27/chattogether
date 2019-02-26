package common.control;

import common.model.User;
import common.observer.ChatObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public interface ChatController extends Remote {

    /**
     *
     * @param username username of a user.
     * @param message message to notify all users (clients)
     * @return Is success notify to all clients
     * @throws RemoteException Throwing an {@link RemoteException} for RMI.
     */
    boolean notifyAllClients(String username, String message) throws RemoteException;

    /**
     *
     * @return Return all users or clients.
     * @throws RemoteException Throwing an {@link RemoteException} for RMI.
     * @throws SQLException Throwing an {@link SQLException} for SQL operation.
     */
    ArrayList<User> getAll() throws RemoteException, SQLException;

    /**
     *
     * @param username username of a user.
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
    User get(String username) throws RemoteException, SQLException;

    /**
     *
     * @param chatObserver Observer to add.
     * @return
     * @throws RemoteException
     */
    boolean addChatObserver(ChatObserver chatObserver) throws RemoteException;

    /**
     *
     * @param chatObserver Observer to remove.
     * @return
     * @throws RemoteException
     */
    boolean removeChatObserver(ChatObserver chatObserver) throws RemoteException;

    /**
     *
     * @param username Username of a user.
     * @return
     * @throws RemoteException
     */
    boolean isReserved(String username) throws RemoteException;

    /**
     *
     * @param username Username of a user.
     * @param password Password of a user.
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
    boolean checkCredentials(String username, String password) throws RemoteException, SQLException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    boolean updateClientLists() throws RemoteException;

    /**
     *
     * @param user User object to add a new user.
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
    boolean addNewUser(User user) throws RemoteException, SQLException;

}
