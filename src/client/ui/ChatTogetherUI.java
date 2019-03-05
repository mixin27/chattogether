package client.ui;

import client.connector.ServerConnector;
import client.observer.ChatObserverImpl;
import common.control.ChatController;
import common.model.User;
import common.observer.ChatObserver;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatTogetherUI extends JFrame implements ChatObserver, ActionListener {

    private ChatObserver mChatObserver;
    private ChatController mChatController;
    private String mUserName;

    private JFrame mFrame;
    private Container mContainer;

    private Font meiryoFont = new Font("Meiryo", Font.PLAIN, 14);
    private Border blankBorder = BorderFactory.createEmptyBorder(10, 10, 20, 10);//top,r,b,l

    private JTextArea txtArea;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> userLists;

    private JTextField txtMessage;

    private JButton btnSendMessage;

    JLabel lblLoginUserName;


    public ChatTogetherUI() {
//        setUserName(userName);

        try {
            init();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        mFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    mChatController.removeChatObserver(mChatObserver);
                    mChatController.updateClientLists();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }

                System.exit(0);
            }
        });

    }

    private void init() throws RemoteException {

        // setLookAndFeel();

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

        mFrame = new JFrame("Chat Together");
        mContainer = getContentPane();

        lblLoginUserName = new JLabel();
        mContainer.add(BorderLayout.NORTH, lblLoginUserName);
        mContainer.add(BorderLayout.SOUTH, getFooterPanel());
        mContainer.add(BorderLayout.CENTER, getContentPanel());

        mFrame.add(mContainer);
        mFrame.setSize(640, 480);
        mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mFrame.setLocation(150, 150);
        mFrame.setVisible(true);
    }

    // Footer Panel
    // TextField - to enter message
    // Button - to send message
    private JPanel getFooterPanel() {
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Enter your message");
        txtMessage = new JTextField(20);
        txtMessage.setFont(meiryoFont);

        btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(this);

        panel.add(lbl);
        panel.add(txtMessage);
        panel.add(btnSendMessage);
        return  panel;
    }

    // Content Panel
    private JPanel getContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String welcome = "Start conversation:";
        txtArea = new JTextArea(welcome, 14, 34);
        //txtArea.setMargin(new Insets(10, 10, 10, 10));
        txtArea.setFont(meiryoFont);
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtArea);
        panel.add(BorderLayout.CENTER, scrollPane);
        panel.add(BorderLayout.EAST, getUserListPanel());

        return panel;
    }

    private JPanel getUserListPanel() {

        JPanel userPanel = new JPanel(new BorderLayout());

        String title = "Active User";

        JLabel userLable = new JLabel(title, JLabel.CENTER);
        userPanel.add(userLable, BorderLayout.NORTH);
        userLable.setFont(new Font("Meiryo", Font.PLAIN, 14));

        JPanel clientPanel = new JPanel(new BorderLayout());
        userLists = new JList<>(listModel);
        userLists.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        userLists.setFont(meiryoFont);

        JScrollPane scrollPane = new JScrollPane(userLists);
        clientPanel.add(scrollPane, BorderLayout.CENTER);
        userPanel.add(clientPanel, BorderLayout.CENTER);

        return userPanel;
    }

    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

        if (!this.mUserName.equals(username))
            txtArea.append("\n<" + username + "> : " + message);
        else
            txtArea.append("\n<" + mUserName + "> : " + message);
        return true;
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
        listModel.clear();
        for (String client : clientLists) {
            if (client.equals(this.mUserName))
                continue;

            User user;
            try {
                user = mChatController.get(client);
                listModel.addElement(user.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public ChatObserver getChatObserver() {
        return mChatObserver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSendMessage) {
            try {
                if (txtMessage.getText().trim().equals(""))
                    return;

                mChatController.notifyAllClients(mUserName, txtMessage.getText().trim());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

            txtMessage.setText("");
            txtMessage.requestFocus();
        }
    }
}
