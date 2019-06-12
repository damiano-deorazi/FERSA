package fersa.dao;

import fersa.model.ApartmentRenterVisit;
import fersa.model.ApartmentLessorVisit;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DAOQueryApartment {
    private static DAOQueryApartment daoQueryApartment = null;
    private DAOConnection daoConnection = DAOConnection.getInstance();
    private static final String GET_APARTMENTS_BY_RENTER_QUERY = "SELECT A.id, A.country, A.city, A.address, " +
            "A.username_lessor, A.description, V.date, V.time FROM visits V JOIN apartments A ON " +
            "V.id_apartment = A.id JOIN users U ON V.username_renter = U.username WHERE U.username = ? AND V.date >= ?";
    private static final String GET_APARTMENTS_BY_LESSOR_QUERY ="SELECT A.id, A.country, A.city, A.address, " +
            "A.description, V.username_renter, V.date, V.time FROM users U JOIN apartments A ON " +
            "U.username = A.username_lessor JOIN visits V ON A.id = V.id_apartment WHERE U.username = ? AND V.date >= ?";

    private DAOQueryApartment(){}

    public static DAOQueryApartment getInstance() {
        if (daoQueryApartment == null){
            daoQueryApartment = new DAOQueryApartment();
        }
        return daoQueryApartment;
    }

    public ArrayList<ApartmentRenterVisit> getApartmentsByRenter(String usernameRenter, LocalDate date, LocalTime time){
        ArrayList<ApartmentRenterVisit> apartmentRenterVisitList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(GET_APARTMENTS_BY_RENTER_QUERY);
            preparedStatement.setString(1, usernameRenter);
            preparedStatement.setDate(2, Date.valueOf(date));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String country = resultSet.getString(2);
                String city = resultSet.getString(3);
                String address = resultSet.getString(4);
                String usernameLessor = resultSet.getString(5);
                String description = resultSet.getString(6);
                LocalDate dateVisit = resultSet.getDate(7).toLocalDate();
                LocalTime timeVisit = resultSet.getTime(8).toLocalTime();

                ApartmentRenterVisit apartmentRenterVisit = new ApartmentRenterVisit(id, country, city, address,
                        usernameLessor, description, dateVisit, timeVisit);
                apartmentRenterVisitList.add(apartmentRenterVisit);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, resultSet);
        }

        return apartmentRenterVisitList;
    }

    public ArrayList<ApartmentLessorVisit> getApartmentsByLessor(String usernameLessor, LocalDate date, LocalTime time) {
        ArrayList<ApartmentLessorVisit> apartmentList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = daoConnection.createConnection();
            preparedStatement = conn.prepareStatement(GET_APARTMENTS_BY_LESSOR_QUERY);
            preparedStatement.setString(1, usernameLessor);
            preparedStatement.setDate(2, Date.valueOf(date));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String country = resultSet.getString(2);
                String city = resultSet.getString(3);
                String address = resultSet.getString(4);
                String description = resultSet.getString(5);
                String usernameRenter = resultSet.getString(6);
                LocalDate dateVisit = resultSet.getDate(7).toLocalDate();
                LocalTime timeVisit = resultSet.getTime(8).toLocalTime();

                ApartmentLessorVisit apartment = new ApartmentLessorVisit(id, country, city, address, usernameLessor,
                        description, dateVisit, timeVisit, usernameRenter);
                apartmentList.add(apartment);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(conn, preparedStatement, resultSet);
        }

        apartmentList.removeIf(apartment -> apartment.getDateVisit().isEqual(date) &&
                apartment.getTimeVisit().isBefore(time));
        return apartmentList;
    }

    private void closeConnection(Connection conn, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            resultSet.close();

        } catch (Exception rse) {
            rse.printStackTrace();
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
