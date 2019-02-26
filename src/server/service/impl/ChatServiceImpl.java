package server.service.impl;

import common.control.ChatController;
import common.model.User;
import common.observer.ChatObserver;
import database.dao.UserDAO;
import database.impl.UserDAOImpl;
import server.observable.ChatObservable;
import server.service.ChatService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {

    private static final ChatObservable OBSERVABLE = new ChatObservable();
    private UserDAO userDAO = new UserDAOImpl();

    public ChatServiceImpl() throws RemoteException {
    }

    @Override
    public boolean notifyAllClients(String username, String message) throws RemoteException {
        return OBSERVABLE.notifyAllClients(username, message);
    }

    @Override
    public ArrayList<User> getAll() throws RemoteException, SQLException {
        return userDAO.get();
    }

    @Override
    public User get(String username) throws RemoteException, SQLException {
        return userDAO.get(username);
    }

    @Override
    public boolean addChatObserver(ChatObserver chatObserver) throws RemoteException {
        return OBSERVABLE.addChatObserver(chatObserver);
    }

    @Override
    public boolean removeChatObserver(ChatObserver chatObserver) throws RemoteException {
        return OBSERVABLE.removeChatObserver(chatObserver);
    }

    @Override
    public boolean isReserved(String username) throws RemoteException {
        return OBSERVABLE.isReserved(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) throws RemoteException, SQLException {

        User user = userDAO.get(username);

        try {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateClientLists() throws RemoteException {
        return OBSERVABLE.updateUI();
    }

    @Override
    public boolean addNewUser(User user) throws RemoteException, SQLException {
        return userDAO.add(user);
    }
}
