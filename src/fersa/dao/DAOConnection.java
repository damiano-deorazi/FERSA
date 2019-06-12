package fersa.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOConnection {
    private static DAOConnection daoConnection = null;
    private String DRIVER;
    private String DBURL;
    private String USER;
    private String PASSWORD;

    private DAOConnection(){
        configureDB();
    }

    public static DAOConnection getInstance() {
        if (daoConnection == null){
            daoConnection = new DAOConnection();
        }
        return daoConnection;
    }

    private void setDRIVER(String DRIVER) {
        this.DRIVER = DRIVER;
    }

    private void setDBURL(String DBURL) {
        this.DBURL = DBURL;
    }

    private void setUSER(String USER) {
        this.USER = USER;
    }

    private void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public Connection createConnection(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void configureDB(){

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            String dbURL = prop.getProperty("dbURL");
            String driver = prop.getProperty("driver");
            this.setDRIVER(driver);
            this.setDBURL(dbURL);
            this.setUSER(user);
            this.setPASSWORD(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
