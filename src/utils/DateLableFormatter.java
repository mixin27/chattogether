package utils;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Norm on 2/26/2019.
 */
public class DateLableFormatter extends JFormattedTextField.AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return sdf.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {

        if (value != null) {
            Calendar calendar = (Calendar) value;
            return sdf.format(calendar.getTime());
        }

        return "";
    }
}
