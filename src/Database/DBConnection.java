package Database;

import java.sql.Connection;
import java.sql.DriverManager;


/**This class controls the database connection.*/
public abstract class DBConnection {

    /**This variable creates the protocol.*/
    private static final String protocol = "jdbc";

    /**This variable creates the vendor.*/
    private static final String vendor = ":mysql:";

    /**This variable creates the location.*/
    private static final String location = "//localhost/";

    /**This variable creates the database name.*/
    private static final String databaseName = "client_schedule";

    /**This variable creates the url.*/
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL

    /**This variable creates the driver.*/
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference

    /**This variable creates the username.*/
    private static final String userName = "sqlUser"; // Username

    /**This variable creates the password.*/
    private static String password = "Passw0rd!"; // Password

    /**This variable creates the connection.*/
    public static Connection connection;  // Connection Interface



    /**This method opens the connection to the database.*/
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }




    /**This method closes the connection to the database.*/
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }




    /**This method gets the connection to the database.*/
    public static Connection getConnection(){
        return connection;
    }


}

