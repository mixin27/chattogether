package database;

import utils.Consts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        System.out.println("Pass : " + Consts.DB_PASS);
        String setDB = String.format("jdbc:mysql://%s:%s/%s", Consts.HOST_NAME, Consts.DB_PORT, Consts.DB_NAME);
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(setDB, Consts.DB_USER, Consts.DB_PASS);
        return connection;
    }
}
