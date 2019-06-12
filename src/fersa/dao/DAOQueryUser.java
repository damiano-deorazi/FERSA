package fersa.dao;

import fersa.model.User;

import java.sql.*;

public class DAOQueryUser {
    private static DAOQueryUser daoQueryUser = null;
    private DAOConnection daoConnection = DAOConnection.getInstance();
    private static final String GET_USER_LOGIN_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE username = ?";
    private static final String GET_EMAIL_USER = "SELECT email FROM users WHERE username = ?";

    private DAOQueryUser(){}

    public static DAOQueryUser getInstance() {
        if (daoQueryUser == null){
            daoQueryUser = new DAOQueryUser();
        }
        return daoQueryUser;
    }

    /**metodo usato per il login*/
    public User getUser(String username, String password){
        User user = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(GET_USER_LOGIN_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = getUserFromSet(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, resultSet);
        }

        return user;
    }

    public User getUser(String username){
        User user = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(GET_USER_QUERY);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = getUserFromSet(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, resultSet);
        }

        return user;
    }

    public String getEmail(String usernameRenter) {
        String email = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(GET_EMAIL_USER);
            preparedStatement.setString(1, usernameRenter);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                email = resultSet.getString(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, resultSet);
        }

        return email;
    }

    private User getUserFromSet(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString(1);
        String lastName = resultSet.getString(2);
        String CF = resultSet.getString(3);
        String usrname = resultSet.getString(4);
        String passw = resultSet.getString(5);
        String email = resultSet.getString(6);
        boolean isLessor = resultSet.getBoolean(7);
        return new User(firstName, lastName, CF, usrname, passw,
                email, isLessor);
    }

    private void closeConnection(Connection conn, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();

            } catch (Exception rse) {
                rse.printStackTrace();
            }
        }
        try {
            preparedStatement.close();
        } catch (Exception pse){
            pse.printStackTrace();
        }
        try {
            conn.close();
        } catch (Exception ce){
            ce.printStackTrace();
        }
    }
}
