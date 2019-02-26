package client.connector;

import common.control.ChatController;
import utils.Consts;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Norm on 2/25/2019.
 */
public class ServerConnector {

    // This is a singleton class.
    private static volatile ServerConnector sServerConnector;

    private ChatController mController;

    private ServerConnector() throws NotBoundException, MalformedURLException, RemoteException {

        System.out.println(Consts.HOST_NAME);
        mController = (ChatController) Naming.lookup(String.format("rmi://%s:%s/%s", Consts.HOST_NAME, Consts.PORT, Consts.SERVICE_NAME));

    }

    public static ServerConnector getInstance() throws NotBoundException, MalformedURLException, RemoteException {
        if (sServerConnector == null) {
            sServerConnector = new ServerConnector();
        }
        return sServerConnector;
    }

    public ChatController getController() {
        return mController;
    }

}
