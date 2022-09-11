package Database;

import Model.Appointment;
import Utilities.ActiveUser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**This class controls the database connection to appointments.*/
public class DBAppointments {


    /**This method inserts an appointment into the database.
     * @param appointment
     * @throws SQLException
     */
    public static int insert(Appointment appointment) throws SQLException {

        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();
        LocalDateTime createDate = LocalDateTime.now();
        String createdBy = ActiveUser.getActiveUser();
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = ActiveUser.getActiveUser();
        int customerId = appointment.getCustomerId();
        int userId = appointment.getUserId();

        String contactName = appointment.getContact();
        int contactId = appointment.getContactId(contactName);

        String query = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(query);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(createDate));
        ps.setString(8, createdBy);
        ps.setTimestamp(9, Timestamp.valueOf(lastUpdate));
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }






    /**This method updates an appointment in the database.
     * @param appointment
     * @throws SQLException
     */
    public static int update(Appointment appointment) throws SQLException {

        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = ActiveUser.getActiveUser();
        int customerId = appointment.getCustomerId();
        int userId = appointment.getUserId();

        String contactName = appointment.getContact();
        int contactId = appointment.getContactId(contactName);

        int appointmentId = appointment.getAppointmentId();

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(lastUpdate));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, appointmentId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }





    /**This method deletes an appointment in the database.
     * @param id
     * @throws SQLException
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


}
