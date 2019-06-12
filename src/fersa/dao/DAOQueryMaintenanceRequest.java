package fersa.dao;

import fersa.model.MaintenanceRequest;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DAOQueryMaintenanceRequest {
    private static DAOQueryMaintenanceRequest daoQueryRequestMaintenance = null;
    private DAOConnection daoConnection = DAOConnection.getInstance();
    private static final String GET_MAINTENANCE_REQUEST_QUERY = "SELECT M.id, M.id_apartment, M.username_renter, " +
            "M.date_request, M.time_request, M.description FROM users U JOIN apartments A ON U.username = A.username_lessor JOIN " +
            "maintenances M ON M.id_apartment = A.id WHERE U.username = ? AND M.is_accepted IS NULL";
    private static final String ACCEPT_MAINTENANCE_REQUEST_QUERY = "UPDATE maintenances SET is_accepted = TRUE " +
            "WHERE id = ?";
    private static final String REJECT_MAINTENANCE_REQUEST_QUERY = "UPDATE maintenances SET is_accepted = FALSE " +
            "WHERE id = ?";

    private DAOQueryMaintenanceRequest(){}

    public static DAOQueryMaintenanceRequest getInstance() {
        if (daoQueryRequestMaintenance == null){
            daoQueryRequestMaintenance = new DAOQueryMaintenanceRequest();
        }
        return daoQueryRequestMaintenance;
    }

    public ArrayList<MaintenanceRequest> searchMaintanceRequest(String usernameLessor) {
        ArrayList<MaintenanceRequest> requestList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(GET_MAINTENANCE_REQUEST_QUERY);
            preparedStatement.setString(1, usernameLessor);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                int idApartment = resultSet.getInt(2);
                String usernameRenter = resultSet.getString(3);
                LocalDate dateRequest = resultSet.getDate(4).toLocalDate();
                LocalTime timeRequest = resultSet.getTime(5).toLocalTime();
                String description = resultSet.getString(6);

                MaintenanceRequest maintenanceRequest= new MaintenanceRequest(id, idApartment, usernameRenter,
                        dateRequest, timeRequest, description);
                requestList.add(maintenanceRequest);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, resultSet);
        }

        return requestList;
    }

    public boolean acceptOrRejectMaintenanceRequest(int idMaintenanceRequest, boolean isAccepted) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int r = 0;
        try {
            conn = daoConnection.createConnection();
            if (isAccepted) preparedStatement = conn.prepareStatement(ACCEPT_MAINTENANCE_REQUEST_QUERY);
            else preparedStatement = conn.prepareStatement(REJECT_MAINTENANCE_REQUEST_QUERY);
            preparedStatement.setInt(1, idMaintenanceRequest);
            r = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, null);
        }

      return r != 0;
    }

    private void closeConnection(Connection conn, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null){
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
