package server.observable;

import common.observer.ChatObserver;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatObservable {

    private final ArrayList<ChatObserver> chatObserverList = new ArrayList<>();

    public boolean addChatObserver(ChatObserver chatObserver) throws RemoteException {
        return chatObserverList.add(chatObserver);
    }

    public boolean removeChatObserver(ChatObserver chatObserver) throws RemoteException {
        return chatObserverList.remove(chatObserver);
    }

    public boolean notifyAllClients(String username, String message) throws RemoteException {
        for (ChatObserver observer : chatObserverList) {
            observer.update(username, message);
        }
        return true;
    }

    public boolean isReserved(String username) throws RemoteException {
        for (ChatObserver observer : chatObserverList) {
            if (observer.getUserName().equals(username))
                return true;
        }

        return false;
    }

    public boolean updateUI() throws RemoteException {
        ArrayList<String> userLists = new ArrayList<>();
        for (ChatObserver observer : chatObserverList) {
            userLists.add(observer.getUserName());
        }

        for (ChatObserver observer : chatObserverList) {
            observer.updateUI(userLists);
        }
        return true;
    }

}
