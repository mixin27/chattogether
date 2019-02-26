package client.observer;

import common.model.User;
import common.observer.ChatObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatObserverImpl extends UnicastRemoteObject implements ChatObserver {

    private ChatObserver mChatObserver;

    public ChatObserverImpl(ChatObserver chatObserver) throws RemoteException {
        this.mChatObserver = chatObserver;
    }

    @Override
    public boolean update(String username, String message) throws RemoteException {
        return mChatObserver.update(username, message);
    }

    @Override
    public ArrayList<User> getOnlineUsers() throws RemoteException {
        return mChatObserver.getOnlineUsers();
    }

    @Override
    public String getUserName() throws RemoteException {
        return mChatObserver.getUserName();
    }

    @Override
    public boolean updateUI(ArrayList<String> clientLists) throws RemoteException {
        return mChatObserver.updateUI(clientLists);
    }
}
