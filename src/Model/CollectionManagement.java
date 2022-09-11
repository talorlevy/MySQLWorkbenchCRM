package Model;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**This class handles local collections.*/
public class CollectionManagement {

    /**This variable creates an observable list for all customers.*/
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**This variable creates an observable list for all appointments.*/
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**This variable creates an observable list for filtered appointments.*/
    private static ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

    /**This variable creates an observable list for all countries.*/
    private static ObservableList<String> allCountries = FXCollections.observableArrayList();

    /**This variable creates an observable list for all divisions.*/
    private static ObservableList<String> allDivisions = FXCollections.observableArrayList();

    /**This variable creates an observable list for filtered divisions.*/
    private static ObservableList<String> filteredDivisions = FXCollections.observableArrayList();

    /**This variable creates an observable list for all customer ID's.*/
    private static ObservableList<Integer> allCustomerIds = FXCollections.observableArrayList();

    /**This variable creates an observable list for all contacts.*/
    private static ObservableList<String> allContacts = FXCollections.observableArrayList();

    /**This variable creates an observable list for all users.*/
    private static ObservableList<Integer> allUsers = FXCollections.observableArrayList();

    /**This variable creates an observable list for available start times.*/
    private static ObservableList<LocalTime> availableStartTimes = FXCollections.observableArrayList();

    /**This variable creates an observable list for available end times.*/
    private static ObservableList<LocalTime> availableEndTimes = FXCollections.observableArrayList();

    /**This variable creates an observable list for filtered end times.*/
    private static ObservableList<LocalTime> filteredEndTimes = FXCollections.observableArrayList();

    /**This variable creates an observable list for all types.*/
    private static ObservableList<String> allTypes = FXCollections.observableArrayList();

    /**This variable creates an observable list for all months.*/
    private static ObservableList<String> allMonths = FXCollections.observableArrayList();






    /**This method returns all customers.*/
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }





    /**This method refreshes all customers.
     * @throws SQLException
     */
    public static void refreshAllCustomers() throws SQLException {

        if (!getAllCustomers().isEmpty()) {
            getAllCustomers().clear();
        }

        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                int divisionId = rs.getInt("Division_ID");
                String division;
                int countryId;
                String country;
                String postalCode = rs.getString("Postal_Code");

                String sql2 = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
                PreparedStatement ps2 = DBConnection.connection.prepareStatement(sql2);
                ps2.setInt(1, divisionId);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    division = rs2.getString("Division");
                    countryId = rs2.getInt("Country_ID");

                    String sql3 = "SELECT * FROM countries WHERE Country_ID = ?";
                    PreparedStatement ps3 = DBConnection.connection.prepareStatement(sql3);
                    ps3.setInt(1, countryId);
                    ResultSet rs3 = ps3.executeQuery();
                    while (rs3.next()) {
                        country = rs3.getString("Country");
                        Customer customer = new Customer(id, name, phone, address, division, country, postalCode);
                        allCustomers.add(customer);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




    /**This method returns all appointments.*/
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }




    /**This method refreshes all appointments.
     * @throws SQLException
     */
    public static void refreshAllAppointments() throws SQLException {

        if (!getAllAppointments().isEmpty()) {
            getAllAppointments().clear();
        }

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                String sql2 = "SELECT * FROM contacts WHERE Contact_ID = ?";
                PreparedStatement ps2 = DBConnection.connection.prepareStatement(sql2);
                ps2.setInt(1, contactId);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String contactName = rs2.getString("Contact_Name");
                    Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactName);
                    allAppointments.add(appointment);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




/*
    public static void deleteAppointment(int id) {
        for (Appointment appointment : allAppointments) {
            if (appointment.getAppointmentId() == id) {
                allAppointments.remove(appointment);
            }
        }
    }
 */



    /**This method returns the filtered appointments.*/
    public static ObservableList<Appointment> getFilteredAppointments() {
        return filteredAppointments;
    }




    /**This method sets filtered appointments based on the month.
     * @param month
     * @throws SQLException
     */
    public static void setFilteredAppointments(String month) throws SQLException {

        filteredAppointments.clear();

        String string = month.toUpperCase(Locale.ROOT);
        Month filter = Month.valueOf(string);

        for (Appointment appointment : allAppointments) {
            if (appointment.getStart().getMonth().equals(filter)) {
                filteredAppointments.add(appointment);
            }
        }
    }



    /**This method sets filtered appointments to the current date through the next 7 days.
     * @throws SQLException
     */
    public static void setFilteredAppointments() throws SQLException {

        filteredAppointments.clear();

        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = LocalDate.now().plusWeeks(1);

        for (Appointment appointment : allAppointments) {
            LocalDate date = appointment.getStart().toLocalDate();
            if (date.isEqual(today) || date.isEqual(endOfWeek) || date.isAfter(today) && date.isBefore(endOfWeek)) {
                filteredAppointments.add(appointment);
            }
        }
    }




    /**This method gets all countries.*/
    public static ObservableList<String> getAllCountries() {
        return allCountries;
    }




    /**This method loads all countries.
     * @throws SQLException
     */
    public static void loadAllCountries() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString("Country");
            allCountries.add(name);
        }
    }




    /**This method gets all divisions.*/
    public static ObservableList<String> getAllDivisions() {
        return allDivisions;
    }




    /**This method refreshes all divisions.
     * @throws SQLException
     */
    public static void refreshAllDivisions() throws SQLException {
        if (!getAllDivisions().isEmpty()) {
            getAllDivisions().clear();
        }
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString("Division");
            allDivisions.add(name);
        }
    }




    /**This method returns filtered divisions.*/
    public static ObservableList<String> getFilteredDivisions() {
        return filteredDivisions;
    }




    /**This method filters divisions.
     * @param country
     * @throws SQLException
     */
    public static void filterDivisions(String country) throws SQLException {

        if (!getFilteredDivisions().isEmpty()) {
            getFilteredDivisions().clear();
        }

        try {
            String sql = "SELECT * FROM countries WHERE Country = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Country_ID");

                String sql2 = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
                PreparedStatement ps2 = DBConnection.connection.prepareStatement(sql2);
                ps2.setInt(1, id);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String name = rs2.getString("Division");
                    filteredDivisions.add(name);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




    /**This method gets all contacts.*/
    public static ObservableList<String> getAllContacts() {
        return allContacts;
    }




    /**This method refreshes all contacts.*/
    public static void refreshAllContacts() {
        if (!getAllContacts().isEmpty()) {
            getAllContacts().clear();
        }
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Contact_Name");
                allContacts.add(name);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




    /**This method gets all users.*/
    public static ObservableList<Integer> getAllUsers() {
        return allUsers;
    }




    /**This method refreshes all users.*/
    public static void refreshAllUsers() {
        if (!getAllUsers().isEmpty()) {
            getAllUsers().clear();
        }
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                allUsers.add(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




    /**This method gets all customer ID's.*/
    public static ObservableList<Integer> getAllCustomerIds() {
        return allCustomerIds;
    }




    /**This method refreshes all customer ID's.*/
    public static void refreshAllCustomerIds() {
        if (!getAllCustomerIds().isEmpty()) {
            getAllCustomerIds().clear();
        }
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                allCustomerIds.add(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




    /**This method gets available start times.*/
    public static ObservableList<LocalTime> getAvailableStartTimes() {
        return availableStartTimes;
    }




    /**This method sets available start times.*/
    public static void setAvailableStartTimes() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate utcDate = LocalDate.of(2022, 5, 29);
        LocalTime utcTime = LocalTime.of(12, 00);
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.of(utcDate, utcTime, utcZoneId);
        Instant timeChange = utcZDT.toInstant();
        ZonedDateTime localZDT = timeChange.atZone(zoneId);
        LocalTime startTime = localZDT.toLocalTime();

        int i;
        for (i = 0; i < 56; i++) {
            availableStartTimes.add(startTime);
            startTime = startTime.plus(15, ChronoUnit.MINUTES);
        }
    }




    /**This method gets available end times.*/
    public static ObservableList<LocalTime> getAvailableEndTimes() {
        return availableEndTimes;
    }




    /**This method refreshes available end times.*/
    public static void refreshAvailableEndTimes() {

        availableEndTimes.clear();

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate utcDate = LocalDate.of(2022, 5, 29);
        LocalTime utcTime = LocalTime.of(12, 15);
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.of(utcDate, utcTime, utcZoneId);
        Instant timeChange = utcZDT.toInstant();
        ZonedDateTime localZDT = timeChange.atZone(zoneId);
        LocalTime endTime = localZDT.toLocalTime();

        int i;
        for (i = 0; i < 56; i++) {
            availableEndTimes.add(endTime);
            endTime = endTime.plus(15, ChronoUnit.MINUTES);
        }
    }




    /**This method gets filtered end times.*/
    public static ObservableList<LocalTime> getFilteredEndTimes() {
        return filteredEndTimes;
    }




    /**This method refreshes filtered end times.
     * @param startTime
     */
    public static void refreshFilteredEndTimes(LocalTime startTime) {

        filteredEndTimes.clear();

        for (LocalTime endTime : availableEndTimes) {
            if (endTime.isAfter(startTime)) {
                filteredEndTimes.add(endTime);
            }
        }
    }




    /**This method gets all types.*/
    public static ObservableList<String> getAllTypes() {
        return allTypes;
    }




    /**This method refreshes all types.*/
    public static void refreshAllTypes() {
        allTypes.clear();
        String planningSession = "Planning Session";
        allTypes.add(planningSession);
        String deBriefing = "De-Briefing";
        allTypes.add(deBriefing);
        String general = "General";
        allTypes.add(general);
        String salesMeeting = "Sales Meeting";
        allTypes.add(salesMeeting);
    }




    /**This method gets all months.*/
    public static ObservableList<String> getAllMonths() {
        return allMonths;
    }




    /**This method sets all months.*/
    public static void setAllMonths() {
        allMonths.clear();
        String january = "January";
        allMonths.add(january);
        String february = "February";
        allMonths.add(february);
        String march = "March";
        allMonths.add(march);
        String april = "April";
        allMonths.add(april);
        String may = "May";
        allMonths.add(may);
        String june = "June";
        allMonths.add(june);
        String july = "July";
        allMonths.add(july);
        String august = "August";
        allMonths.add(august);
        String september = "September";
        allMonths.add(september);
        String october = "October";
        allMonths.add(october);
        String november = "November";
        allMonths.add(november);
        String december = "December";
        allMonths.add(december);
    }



}

