package server.controller;

import common.control.ChatController;
import common.model.User;
import common.observer.ChatObserver;
import server.service.ChatService;
import server.service.impl.ChatServiceImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatControllerImpl extends UnicastRemoteObject implements ChatController {

    private ChatService service = new ChatServiceImpl();

    public ChatControllerImpl() throws RemoteException {
    }

    @Override
    public boolean notifyAllClients(String username, String message) throws RemoteException {
        return service.notifyAllClients(username, message);
    }

    @Override
    public ArrayList<User> getAll() throws RemoteException, SQLException {
        return service.getAll();
    }

    @Override
    public User get(String username) throws RemoteException, SQLException {
        return service.get(username);
    }

    @Override
    public boolean addChatObserver(ChatObserver chatObserver) throws RemoteException {
        return service.addChatObserver(chatObserver);
    }

    @Override
    public boolean removeChatObserver(ChatObserver chatObserver) throws RemoteException {
        return service.removeChatObserver(chatObserver);
    }

    @Override
    public boolean isReserved(String username) throws RemoteException {
        return service.isReserved(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) throws RemoteException, SQLException {
        return service.checkCredentials(username, password);
    }

    @Override
    public boolean updateClientLists() throws RemoteException {
        return service.updateClientLists();
    }

    @Override
    public boolean addNewUser(User user) throws RemoteException, SQLException {
        return service.addNewUser(user);
    }
}
