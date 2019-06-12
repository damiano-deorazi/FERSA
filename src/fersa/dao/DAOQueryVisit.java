package fersa.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class DAOQueryVisit {
    private static DAOQueryVisit daoQueryVisit = null;
    private DAOConnection daoConnection = DAOConnection.getInstance();
    private static final String DELETE_VISIT = "DELETE FROM visits WHERE username_renter = ? AND id_apartment = ?";
    private static final String GET_VISIT_BY_ID_N_TIME = "SELECT * FROM visits WHERE id_apartment = ? AND date = ? AND " +
            "time = ?";
    private static final String MODIFY_VISIT = "UPDATE visits SET date = ? , time = ? WHERE username_renter = ? AND " +
            "id_apartment = ?";

    private DAOQueryVisit(){}

    public static DAOQueryVisit getInstance() {
        if (daoQueryVisit == null){
            daoQueryVisit = new DAOQueryVisit();
        }
        return daoQueryVisit;
    }

    public boolean deleteVisit(String username, int id) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int r = 0;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(DELETE_VISIT);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            r = preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, null);
        }

        return r != 0;
    }

    public int getVisitByIdAndTime(int idApartment, LocalDate date, LocalTime time) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int n = 0;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(GET_VISIT_BY_ID_N_TIME);
            preparedStatement.setInt(1, idApartment);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setTime(3, Time.valueOf(time));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) n = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, resultSet);
        }
        return n;
    }

    public boolean modifyVisit(String username, int idApartment, LocalDate date, LocalTime time) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int r = 0;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(MODIFY_VISIT);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setTime(2, Time.valueOf(time));
            preparedStatement.setString(3, username);
            preparedStatement.setInt(4, idApartment);
            r = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, null);
        }

        return r != 0;
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
