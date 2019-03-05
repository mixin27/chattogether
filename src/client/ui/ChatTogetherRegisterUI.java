package client.ui;

import client.connector.ServerConnector;
import common.control.ChatController;
import common.model.User;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatTogetherRegisterUI extends JFrame implements ActionListener {

    private JFrame mFrame;
    private Container mContainer;
    private JPanel mRegisterMainPanel;

    private JButton btnRegister;

    private JTextField txtUserName, txtFullName;
    private JPasswordField txtPassword;
    private JTextArea txtAddress;
    private JRadioButton radmale, radFemale;
    private ButtonGroup radGroup;

    private JDatePickerImpl mDatePicker;

    private boolean isFieldEmpty = false;
    private ChatController mController;


    public ChatTogetherRegisterUI() {

        init();

    }

    public void setController(ChatController controller) {
        this.mController = controller;
    }

    private void init() {

        initChatConnector();

        mFrame = new JFrame("Chat Together");
        mContainer = getContentPane();
        mRegisterMainPanel = new JPanel(new BorderLayout());

        mRegisterMainPanel.add(BorderLayout.NORTH, getHeaderPane());
        mRegisterMainPanel.add(BorderLayout.CENTER, getRegisterFormPane());
        mRegisterMainPanel.add(BorderLayout.SOUTH, getFooterPane());

        mContainer.setLayout(new BorderLayout());
        mContainer.add(mRegisterMainPanel, BorderLayout.CENTER);

        mFrame.add(mContainer);
        mFrame.setSize(640, 480);
        mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mFrame.setLocation(150, 150);
        mFrame.setVisible(true);
    }

    private void initChatConnector() {
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

    private JPanel getHeaderPane() {
        JPanel headerPanel = new JPanel();
        JLabel lblHeader = new JLabel("CREATE NEW ACCOUNT", JLabel.CENTER);
        headerPanel.add(lblHeader);
        return headerPanel;
    }

    private JPanel getRegisterFormPane() {

        JPanel formPanel = new JPanel(new GridLayout(7,2));

        formPanel.add(new JLabel("Username", JLabel.CENTER));
        txtUserName = new JTextField();
        formPanel.add(txtUserName);

        formPanel.add(new JLabel("Password", JLabel.CENTER));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        formPanel.add(new JLabel("FullName", JLabel.CENTER));
        txtFullName = new JTextField();
        formPanel.add(txtFullName);

        // DatePicker implementation
//        UtilDateModel model = new UtilDateModel();
//        Properties properties = new Properties();
//        properties.put("text.today", "Today");
//        properties.put("text.month", "Month");
//        properties.put("text.year", "Year");
//        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
//        mDatePicker = new JDatePickerImpl(datePanel, new DateLableFormatter());
//        formPanel.add(new JLabel("Date Of Birth", JLabel.CENTER));
//        formPanel.add(mDatePicker);

        formPanel.add(new JLabel("Gender", JLabel.CENTER));
        JPanel radPanel = new JPanel(new FlowLayout());
        radmale = new JRadioButton("Male");
        radmale.setSelected(true);
        radFemale = new JRadioButton("Female");
        radGroup = new ButtonGroup();
        radGroup.add(radmale);
        radGroup.add(radFemale);
        radPanel.add(radmale);
        radPanel.add(radFemale);
        formPanel.add(radPanel);

        formPanel.add(new JLabel("Address", JLabel.CENTER));
        txtAddress = new JTextArea(10, 10);
        formPanel.add(txtAddress);

        return formPanel;
    }

    private JPanel getFooterPane() {
        JPanel footerPanel = new JPanel();

        btnRegister = new JButton("REGISTER");
        btnRegister.addActionListener(this);
        btnRegister.setActionCommand("REGISTER");

        footerPanel.add(btnRegister);
//        footerPanel.add(new JLabel(), BorderLayout.NORTH);
//        footerPanel.add(new JLabel(), BorderLayout.SOUTH);
//        footerPanel.add(new JLabel(), BorderLayout.EAST);
//        footerPanel.add(new JLabel(), BorderLayout.WEST);

        return footerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("REGISTER")) {
            doRegister();
        }
    }

    private void doRegister() {
        String username = "", password = "", fullname = "", gender = "", address = "";

        try {
            username = txtUserName.getText().trim();
            password = txtPassword.getText().trim();
            fullname = txtFullName.getText().trim();
            gender = radFemale.isSelected() ? "Female" : "Male";
            address = txtAddress.getText();
        } catch (Exception e) {
            showError("Please fill all fields!");
        }

//        ArrayList<String> list = new ArrayList<>();
//        list.add(username);
//        list.add(fullname);
//        list.add(dob);
//        list.add(gender);
//        list.add(address);
//        list.add(nic);
//        list.add(contact);

//        isFieldEmpty = isEmpty(list);

//        if(isFieldEmpty||!nic.matches("[0-9]{9}[Vv]")||!contact.matches("[0-9]{10}")){
//            showError("Please Ender Valid Details");
//            return;
//        }

        User user = new User(username, password, fullname, gender, address);

        try {
            boolean val = mController.addNewUser(user);

            if (val) {
                // showAlert("Account Created!");
                System.out.println("Account created!");

                ChatTogetherUI chatController = new ChatTogetherUI();
                chatController.setUserName(txtUserName.getText());
                chatController.lblLoginUserName.setText("Login as: " + txtUserName.getText());
                chatController.show();
                mFrame.hide();
                mFrame.dispose();

                clearFields();
            } else {
                System.out.println("Failed to create new account!");
                showError("Failed to create new account!");
            }

        } catch (RemoteException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create new account!");
            showError("Failed to create new account!");
        }
    }

    private void clearFields() {
        txtUserName.setText("");
        txtPassword.setText("");
        txtFullName.setText("");
        txtAddress.setText("");

        radmale.setSelected(true);
    }

    private boolean isEmpty(ArrayList<String> list) {
        for(String s : list){
            if(s.equals(""))return true;
        }
        return false;
    }

    private void showAlert(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Chat Together", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Chat Together", JOptionPane.ERROR_MESSAGE);
    }
}
