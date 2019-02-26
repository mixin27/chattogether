package server.main;

import server.controller.ChatControllerImpl;
import utils.Consts;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {

    public static void main(String[] args) {

        try {

//            java.rmi.registry.LocateRegistry.createRegistry(Consts.PORT);
//            System.out.println("[RMI] Server ready.");
//            ChatController service = new ChatControllerImpl();
//            Naming.rebind("rmi://" + Consts.HOST_NAME + "/" + Consts.SERVICE_NAME, service);

            System.setProperty("java.rmi.server.hostname", Consts.HOST_NAME);
            Registry registry = LocateRegistry.createRegistry(Consts.PORT);
            registry.rebind(Consts.SERVICE_NAME, new ChatControllerImpl());

            System.out.println("Server Started...");
            System.out.println("HOST : " + Consts.HOST_NAME);
            System.out.println("PORT : " + Consts.PORT);

//            String[] ar = {"cmd", "/c", "start", "powershell.exe", "-command", "Read-Host", " Server Started", "Press Enter to Exit......"};
//
//            Process process = Runtime.getRuntime().exec(ar);
//            InputStream is = process.getErrorStream();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            reader.readLine();
//            System.out.println("Existing...");
//            reader.close();
//            process.destroy();
//
//            Runtime.getRuntime().exit(0);

        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
