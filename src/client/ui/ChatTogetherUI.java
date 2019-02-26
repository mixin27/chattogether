package client.ui;

import client.connector.ServerConnector;
import client.observer.ChatObserverImpl;
import common.control.ChatController;
import common.model.User;
import common.observer.ChatObserver;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatTogetherUI extends JFrame implements ChatObserver {

    private ChatObserver mChatObserver;
    private ChatController mChatController;
    private String mUserName = "user";


    public ChatTogetherUI() {

        init();

    }

    private void init() {
        try {
            mChatObserver = new ChatObserverImpl(this);
            mChatController = ServerConnector.getInstance().getController();
            mChatController.addChatObserver(mChatObserver);

            System.out.println("Connection Established...");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
        System.out.println(mUserName);

        updateClientList();
    }

    /**
     * Update client lists.
     */
    private void updateClientList() {
        try {
            mChatController.updateClientLists();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(String username, String message) throws RemoteException {
        return false;
    }

    @Override
    public ArrayList<User> getOnlineUsers() throws RemoteException {
        return null;
    }

    @Override
    public String getUserName() throws RemoteException {
        return mUserName;
    }

    @Override
    public boolean updateUI(ArrayList<String> clientLists) throws RemoteException {
        return false;
    }

    public ChatObserver getChatObserver() {
        return mChatObserver;
    }
}
