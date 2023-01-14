package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    //jdbc:mysql://wgudb.ucertify.com:3306/WJ06YG0
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * open sql database connection
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * getter for the sql connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * close the sql database connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            // System.out.println("Error:" + e.getMessage());
            // closing application, no need to do anything
        }
    }

    public static ZonedDateTime convertToDBTime(ZonedDateTime localZonedTime) {
        ZonedDateTime dbTime = localZonedTime.withZoneSameInstant(ZoneId.of("-00:00"));
        return dbTime;
    }
}