package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Norm on 2/26/2019.
 */
public class ChatTogetherStartUI extends JFrame implements ActionListener {

    JFrame frame;
    JTextField txtName;

    public ChatTogetherStartUI() {
        frame = new JFrame("Chat Together");
        frame.setSize(300, 300);
        frame.setLocation(150, 150);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container container = getContentPane();
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Chat Together", JLabel.CENTER));

        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Enter Name", JLabel.CENTER));

        txtName = new JTextField("user", 20);
        inputPanel.add(txtName);

        panel.add(inputPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(new JPanel());

        JButton start = new JButton("Join");
        start.setActionCommand("START");
        start.addActionListener(this);
        buttonPanel.add(start);

        panel.add(buttonPanel);

        container.add(panel, BorderLayout.CENTER);

        frame.add(container);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("START")) {

            if (!txtName.getText().isEmpty()) {
                new ChatTogetherUI();
                frame.hide();
            }
        }
    }

    public static void main(String[] args) {
        new ChatTogetherStartUI();
    }
}
