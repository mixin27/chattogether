package server.service.impl;

import common.model.User;
import common.observer.ChatObserver;
import database.dao.UserDAO;
import database.impl.UserDAOImpl;
import server.observable.ChatObservable;
import server.service.ChatService;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatServiceImpl implements ChatService {
    private static final ChatObservable CHAT_OBSERVABLE=new ChatObservable();
    private UserDAO usersDAO = new UserDAOImpl();

    @Override
    public boolean notifyAllClients(String username, String message) throws RemoteException {
        return CHAT_OBSERVABLE.notifyAllClients(username, message);
    }

    @Override
    public ArrayList<User> getAll() throws RemoteException, SQLException {
        return usersDAO.get();
    }

    @Override
    public User get(String username) throws RemoteException, SQLException {
        return usersDAO.get(username);
    }

    @Override
    public boolean addChatObserver(ChatObserver chatObserver) throws RemoteException {
        return CHAT_OBSERVABLE.addChatObserver(chatObserver);
    }

    @Override
    public boolean removeChatObserver(ChatObserver chatObserver) throws RemoteException {
        return CHAT_OBSERVABLE.removeChatObserver(chatObserver);
    }

    @Override
    public boolean isReserved(String username) throws RemoteException {
        return CHAT_OBSERVABLE.isReserved(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) throws RemoteException, SQLException {
        User user = usersDAO.get(username);
        try {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) return true;
        }catch (NullPointerException ex){
            return false;
        }
        return false;
    }

    @Override
    public boolean updateClientLists() throws RemoteException {
        return CHAT_OBSERVABLE.updateUI();
    }

    @Override
    public boolean addNewUser(User user) throws RemoteException, SQLException {
        return usersDAO.add(user);
    }
}
