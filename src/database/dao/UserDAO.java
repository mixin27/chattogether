package database.dao;

import common.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public interface UserDAO {

    /**
     *
     * @param user
     * @return
     * @throws SQLException
     */
    boolean add(User user) throws SQLException;

    /**
     *
     * @param username
     * @return
     * @throws SQLException
     */
    boolean delete(String username)throws SQLException;

    /**
     *
     * @param user
     * @return
     * @throws SQLException
     */
    boolean update(User user)throws SQLException;

    /**
     *
     * @return
     * @throws SQLException
     */
    ArrayList<User> get()throws SQLException;

    /**
     *
     * @param username
     * @return
     * @throws SQLException
     */
    User get(String username)throws SQLException;

}
