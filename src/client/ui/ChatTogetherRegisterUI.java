package client.ui;

import common.control.ChatController;
import common.model.User;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import utils.DateLableFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatTogetherRegisterUI extends JFrame implements ActionListener {

    private JFrame mFrame;
    private Container mContainer;
    private JPanel mRegisterMainPanel;

    private JButton btnRegister;

    private JTextField txtUserName, txtFullName, txtNic, txtContactNo;
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
        mFrame = new JFrame("Chat Together");
        mContainer = getContentPane();
        mRegisterMainPanel = new JPanel(new BorderLayout());

        mRegisterMainPanel.add(getHeaderPane(), BorderLayout.NORTH);
        mRegisterMainPanel.add(getRegisterFormPane(), BorderLayout.CENTER);
        mRegisterMainPanel.add(getFooterPane(), BorderLayout.SOUTH);

        mContainer.setLayout(new BorderLayout());
        mContainer.add(mRegisterMainPanel, BorderLayout.CENTER);

        mFrame.add(mContainer);
        mFrame.pack();
        mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mFrame.setLocation(150, 150);
        mFrame.setVisible(true);
    }

    private JPanel getHeaderPane() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel lblHeader = new JLabel("CREATE NEW ACCOUNT", JLabel.CENTER);
        headerPanel.add(lblHeader, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel getRegisterFormPane() {

        JPanel formPanel = new JPanel(new GridLayout(7,2));

        formPanel.add(new JLabel("Username", JLabel.CENTER));
        txtUserName = new JTextField();
        formPanel.add(txtUserName);

        formPanel.add(new JLabel("FullName", JLabel.CENTER));
        txtFullName = new JTextField();
        formPanel.add(txtFullName);

        // DatePicker implementation
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        mDatePicker = new JDatePickerImpl(datePanel, new DateLableFormatter());
        formPanel.add(new JLabel("Date Of Birth", JLabel.CENTER));
        formPanel.add(mDatePicker);

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

        formPanel.add(new JLabel("NIC", JLabel.CENTER));
        txtNic = new JTextField();
        formPanel.add(txtNic);

        formPanel.add(new JLabel("Contact No.", JLabel.CENTER));
        txtContactNo = new JTextField();
        formPanel.add(txtContactNo);

        return formPanel;
    }

    private JPanel getFooterPane() {
        JPanel footerPanel = new JPanel(new BorderLayout());

        btnRegister = new JButton("REGISTER");
        btnRegister.addActionListener(this);
        btnRegister.setActionCommand("REGISTER");

        footerPanel.add(btnRegister, BorderLayout.CENTER);
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
        User user = new User();
        String username = "", fullname = "", dob = "", gender = "", address = "", nic = "", contact = "";

        try {
            username = txtUserName.getText();
            fullname = txtFullName.getText();
            dob = (String) mDatePicker.getModel().getValue();
            gender = radFemale.isSelected() ? "Female" : "Male";
            address = txtAddress.getText();
            nic = txtNic.getText();
            contact = txtContactNo.getText();
        } catch (Exception e) {
            showError("Please fill all fields!");
        }

        ArrayList<String> list = new ArrayList<>();
        list.add(username);
        list.add(fullname);
        list.add(dob);
        list.add(gender);
        list.add(address);
        list.add(nic);
        list.add(contact);

        isFieldEmpty = isEmpty(list);

        if(isFieldEmpty||!nic.matches("[0-9]{9}[Vv]")||!contact.matches("[0-9]{10}")){
            showError("Please Ender Valid Details");
            return;
        }

        user.setUsername(username);
        user.setName(fullname);
        user.setDob(dob);
        user.setGender(gender);
        user.setAddress(address);
        user.setNic(nic);
        user.setContact(contact);

        try {
            boolean val = mController.addNewUser(user);

            if (val) {
                showAlert("Account Created!");
            } else {
                showError("Failed to create new account!");
            }

        } catch (RemoteException e) {
            e.printStackTrace();
            showError("Failed to create new account!");
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to create new account!");
        }
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
