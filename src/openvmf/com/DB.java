package openvmf.com;

import openvmf.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
    private static final String DB_IP = "10.3.1.147";
    //private static final String DB_IP = "localhost";
    //private static final String DB_IP = "10.42.0.163";
    private static Connection dbConnection;

    public DB(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":3306/ADP?autoReconnect=true&useSSL=false", username, password);
            Logger.log("DB connection started");
        } catch (ClassNotFoundException e) {
            Logger.log("[ERROR] com.mysql.jdbc.Driver not found");
        } catch (SQLException e) {
            Logger.log("[ERROR] Unable to connect to the DB");
        }
    }

    public static void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logDB(String message) {
        try {
            dbConnection.createStatement().execute("INSERT INTO Log (Time, Message) VALUES (NOW(), '" + message + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[CRITICAL] Unable to connecto to the DB");
        }
    }

    public static String getSettings(String option) {
        try {
            ResultSet result = dbConnection.createStatement().executeQuery("SELECT value FROM Settings WHERE Name = '" + option + "'");
            String out = "";
            while (result.next()) {
                out = result.getString(1);
            }
            result.close();
            return out;
        } catch (SQLException e) {
            Logger.log("[ERROR] SQL Exception while retrieving setting");
            return null;
        }
    }

    public static void insertPosition(int x, int y, int heading) {
        try {
            dbConnection.createStatement().execute("INSERT INTO Positions (PosX, PosY, Heading) VALUES ('" + x + "', '" + y + "', '" + heading + "')");
        } catch (SQLException e) {
            Logger.log("[ERROR] SQL Exception while inserting position");
        }
    }

    public static void insertPoints(int s1, int s2, int s3, int s4, int rotation) {
        try {
            dbConnection.createStatement().execute("INSERT INTO Points (Position, Rotation, Distance0, Distance1, Distance2, Distance3) VALUES ((SELECT MAX(Id) FROM Positions), '" + rotation + "', '" + s1 + "', '" + s2 + "', '" + s3 + "', '" + s4 + "')");
        } catch (SQLException e) {
            Logger.log("[ERROR] SQL Exception while inserting points");
            e.printStackTrace();
        }
    }

    static String getLastCompass() {
        try {
            ResultSet result = dbConnection.createStatement().executeQuery("SELECT Heading FROM Positions ORDER BY Id DESC LIMIT 1");
            result.next();
            String out = result.getString(1);
            result.close();
        } catch (SQLException e) {
            Logger.log("[ERROR] SQL Exception while retrieving compass");
            return "null";
        }
        return "null";
    }

    static Connection getConnection() {
        return dbConnection;
    }
}
