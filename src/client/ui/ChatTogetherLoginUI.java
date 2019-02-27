package client.ui;

import client.connector.ServerConnector;
import common.control.ChatController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatTogetherLoginUI extends JFrame implements ActionListener {

    private ChatController mController;
    private ChatTogetherUI mChatController;

    private JFrame mLoginFrame;
    private Container mContainer;
    private JPanel mLoginMainPanel;

    JTextField txtUserName;
    JPasswordField txtPassword;

    private JButton btnLogin;

    public ChatTogetherLoginUI() {
        init();

        initConnector();
        // setLookAndFeel();
    }

    private void initConnector() {
        try {
            mController = ServerConnector.getInstance().getController();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setLookAndFeel() {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChatTogetherLoginUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ChatTogetherLoginUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ChatTogetherLoginUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ChatTogetherLoginUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {

        mLoginFrame = new JFrame("Chat Together");
        mContainer = getContentPane();
        mLoginMainPanel = new JPanel(new BorderLayout());
        mLoginMainPanel.add(getLoginPanel(), BorderLayout.CENTER);

        mLoginFrame.setLayout(new BorderLayout());
        mLoginFrame.setSize(640, 320);
        mLoginFrame.add(mLoginMainPanel, BorderLayout.CENTER);
        mLoginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mLoginFrame.setLocation(150, 150);
        mLoginFrame.setVisible(true);
    }

    private JPanel getLoginPanel() {

        JPanel loginPanel = new JPanel();

        JLabel lblTitle = new JLabel("LOGIN");
        lblTitle.setBounds(100, 30, 400, 30);

        JLabel lblUserName = new JLabel("Username");
        lblUserName.setBounds(80, 70, 200, 30);
        txtUserName = new JTextField();
        txtUserName.setBounds(300, 70, 200, 30);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(80, 110, 200, 30);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(300, 110, 200, 30);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 160, 100, 30);
        btnLogin.addActionListener(this);
        btnLogin.setActionCommand("LOGIN");

        loginPanel.add(lblTitle);
        loginPanel.add(lblUserName);
        loginPanel.add(txtUserName);
        loginPanel.add(lblPassword);
        loginPanel.add(txtPassword);
        loginPanel.add(btnLogin);

        loginPanel.setLayout(null);

        return loginPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("LOGIN")) {
            doLogin();
        }
    }

    private void doLogin() {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        if (!username.equals("") || !password.equals("")) {
            try {
                boolean isValid = mController.checkCredentials(username, password);

                if (isValid) {

                    if (!mController.isReserved(username)) {
                        mChatController = new ChatTogetherUI();
                        mChatController.setUserName(username);
                        mLoginFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                try {
                                    mController.removeChatObserver(mChatController.getChatObserver());
                                    mController.updateClientLists();
                                } catch (RemoteException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                        mChatController.show();
                        mLoginFrame.hide();
                    } else {
                        showError("You are logged in from another device");
                    }

                } else {
                    showError("Please Enter valid credentials");
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void showAlert(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Chat Together", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Chat Together", JOptionPane.ERROR_MESSAGE);
    }
}
