package Utilities;

import Database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class ActiveUser {

    private static String activeUser;
    private static LocalTime loginTime;
    private static  int id;





    public static String getActiveUser() {
        return activeUser;
    }






    public static void setActiveUser(String activeUser) {
        ActiveUser.activeUser = activeUser;
    }






    public static LocalTime getLoginTime() {
        return loginTime;
    }






    public static void setLoginTime(LocalTime time) {
        ActiveUser.loginTime = time;
    }




    public static int getId() {
        return id;
    }





    public static void setId(int thisId) throws SQLException {
        id = thisId;
    }

}
