package utils;

import javax.swing.*;
import java.util.Calendar;

/**
 * Created by Norm on 2/26/2019.
 */
public class DatePicker {

    public int month = java.util.Calendar.getInstance().get(Calendar.MONTH);
    public int year = java.util.Calendar.getInstance().get(Calendar.YEAR);

    JLabel lbl = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog mDialog;
    JButton[] buttons = new JButton[49];

    public DatePicker(JFrame parent) {
        mDialog = new JDialog();
        mDialog.setModal(true);

        String[] headers = {};
    }

}
