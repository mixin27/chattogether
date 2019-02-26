package client.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Norm on 2/25/2019.
 */
public class ChatTogetherRegisterUI extends JFrame {

    private JFrame mFrame;
    private Container mContainer;
    private JPanel mRegisterMainPanel;

    private JButton btnRegister;

    private JTextField txtUserName, txtFullName, txtNic, txtContactNo;
    private JTextArea txtAddress;
    private JRadioButton radmale, radFemale;
    private ButtonGroup radGroup;



    public ChatTogetherRegisterUI() {

        init();

    }

    private void init() {
        mFrame = new JFrame("Chat Together");
        mContainer = getContentPane();
        mRegisterMainPanel = new JPanel(new BorderLayout());

        mRegisterMainPanel.add(getHeaderPane(), BorderLayout.NORTH);
//        mRegisterMainPanel.add(getRegisterFormPane(), BorderLayout.CENTER);
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

        JPanel formPanel = new JPanel(new BorderLayout());

        return formPanel;
    }

    private JPanel getFooterPane() {
        JPanel footerPanel = new JPanel(new BorderLayout());

        btnRegister = new JButton("REGISTER");

        footerPanel.add(btnRegister, BorderLayout.CENTER);
//        footerPanel.add(new JLabel(), BorderLayout.NORTH);
//        footerPanel.add(new JLabel(), BorderLayout.SOUTH);
//        footerPanel.add(new JLabel(), BorderLayout.EAST);
//        footerPanel.add(new JLabel(), BorderLayout.WEST);

        return footerPanel;
    }
}
