package Model;

import Database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;


/**This class controls appointment objects.*/
public class Appointment {

    /**This variable creates the appointment id.*/
    private int appointmentId;

    /**This variable creates the title.*/
    private String title;

    /**This variable creates the description.*/
    private String description;

    /**This variable creates the location.*/
    private String location;

    /**This variable creates the type.*/
    private String type;

    /**This variable creates the type.*/
    private LocalDateTime start;

    /**This variable creates the end.*/
    private LocalDateTime end;

    /**This variable creates the customer ID.*/
    private int customerId;

    /**This variable creates the user ID.*/
    private int userId;

    /**This variable creates the contact.*/
    private String contact;



    /**This method creates an appointment.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contact
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, String contact) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contact = contact;
    }

    /**This method gets the appointment ID.*/
    public int getAppointmentId() {
        return appointmentId;
    }

    /**This method sets the appointment ID.
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**This method gets the title.*/
    public String getTitle() {
        return title;
    }

    /**This method sets the title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**This method get description.*/
    public String getDescription() {
        return description;
    }

    /**This method sets the description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**This method gets the location .*/
    public String getLocation() {
        return location;
    }

    /**This method sets the location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**This method gets the type.*/
    public String getType() {
        return type;
    }

    /**This method sets the type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**This method gets the start.*/
    public LocalDateTime getStart() {
        return start;
    }

    /**This method sets the start.
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**This method gets the end.*/
    public LocalDateTime getEnd() {
        return end;
    }

    /**This method sets the end.
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**This method gets the customer ID.*/
    public int getCustomerId() {
        return customerId;
    }

    /**This method sets the customer ID.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**This method gets the user ID.*/
    public int getUserId() {
        return userId;
    }

    /**This method sets the user ID.
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**This method gets the contact.*/
    public String getContact() {
        return contact;
    }

    /**This method sets the contact.
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }



    /**This method gets the contact ID.
     * @param name
     * @throws SQLException
     */
    public int getContactId(String name) throws SQLException {

        try {
            String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Contact_ID");
                return id;
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


}
