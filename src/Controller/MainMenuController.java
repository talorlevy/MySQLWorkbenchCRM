package Controller;

import Database.DBAppointments;
import Database.DBConnection;
import Database.DBCustomers;
import Model.*;
import Utilities.ActiveUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;



/**This class controls the main menu.*/
public class MainMenuController implements Initializable {

    /**
     * This variable creates the stage for the GUI of the main menu.
     */
    Stage stage;

    /**
     * This variable creates the scene for the GUI of the main menu.
     */
    Parent scene;




    /**This FXML variable handles the contact column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;

    /**This FXML variable handles the customer column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIdCol;

    /**This FXML variable handles the description column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;

    /**This FXML variable handles the end column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentEndCol;

    /**This FXML variable handles the appointment id column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    /**This FXML variable handles the location column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    /**This FXML variable handles the start column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartCol;

    /**This FXML variable handles the appointment table view.*/
    @FXML
    private TableView<Appointment> appointmentTableView;

    /**This FXML variable handles the title column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    /**This FXML variable handles the type column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    /**This FXML variable handles the user id column in the appointment table.*/
    @FXML
    private TableColumn<Appointment, Integer> appointmentUserIdCol;

    /**This FXML variable handles the address column in the customer table.*/
    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    /**This FXML variable handles the country column in the customer table.*/
    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    /**This FXML variable handles the division column in the customer table.*/
    @FXML
    private TableColumn<Customer, String> customerDivisionCol;

    /**This FXML variable handles the customer id column in the customer table.*/
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    /**This FXML variable handles the name column in the customer table.*/
    @FXML
    private TableColumn<Customer, String> customerNameCol;

    /**This FXML variable handles the phone column in the customer table.*/
    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    /**This FXML variable handles the postal code column in the customer table.*/
    @FXML
    private TableColumn<Customer, Integer> customerPostalCodeCol;

    /**This FXML variable handles the customer table.*/
    @FXML
    private TableView<Customer> customerTableView;

    /**This FXML variable handles the user label.*/
    @FXML
    private Label userLbl;

    /**This FXML variable handles the appointment month filter combo box.*/
    @FXML
    private ComboBox<String> monthComboBox;

    /**This FXML variable handles the report type combo box.*/
    @FXML
    private ComboBox<String> reportTypeComboBox;

    /**This FXML variable handles the report month combo box.*/
    @FXML
    private ComboBox<String> reportMonthComboBox;

    /**This FXML variable handles the report contact combo box.*/
    @FXML
    private ComboBox<String> reportContactComboBox;



    /**This FXML method deletes a selected appointment.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
        int id = appointment.getAppointmentId();
        String type = appointment.getType();
        DBAppointments.delete(appointment.getAppointmentId());
        CollectionManagement.refreshAllAppointments();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("APPOINTMENT CANCELLED");
        alert.setContentText("You have cancelled " + type + " appointment #" + id);
        alert.show();

    }




    /**This FXML method deletes a customer.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {

        int id = customerTableView.getSelectionModel().getSelectedItem().getId();

        for (Appointment appointment : CollectionManagement.getAllAppointments()) {
            if (appointment.getCustomerId() == id) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("DELETE CUSTOMER'S APPOINTMENTS FIRST");
                alert.setContentText("Please delete ALL appointments with this customer before deleting!");
                alert.show();
                return;
            }
        }

        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        String name = customer.getName();
        DBCustomers.delete(customer.getId());
        CollectionManagement.refreshAllCustomers();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("CUSTOMER DELETED");
        alert.setContentText("You have successfully deleted " + name);
        alert.show();

    }




    /**This FXML method opens the add customer menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayAddCustomerMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }




    /**This FXML method opens the create appointment menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayCreateAppointmentMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CreateAppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }




    /**This FXML method opens the update appointment menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayUpdateAppointmentMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateAppointmentMenu.fxml"));
        loader.load();

        UpdateAppointmentController appointmentController = loader.getController();
        appointmentController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }




    /**This FXML method opens the update customer menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayUpdateCustomerMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateCustomerMenu.fxml"));
        loader.load();

        UpdateCustomerController customerController = loader.getController();
        customerController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }




    /**This FXML method returns to the portal page.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Portal.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }




    /**This FXML method filters the appointment table to show all appointments.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionShowAllAppointments(ActionEvent event) throws SQLException {

        monthComboBox.getSelectionModel().clearSelection();
        appointmentTableView.setItems(CollectionManagement.getAllAppointments());


    }




    /**This FXML method filters the appointment menu by month.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionFilterByMonth(ActionEvent event) throws SQLException {
        CollectionManagement.setFilteredAppointments(monthComboBox.getSelectionModel().getSelectedItem());
        appointmentTableView.setItems(CollectionManagement.getFilteredAppointments());
    }




    /**This FXML method filters the appointment menu by the current week.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionFilterByWeek(ActionEvent event) throws SQLException {

        CollectionManagement.setFilteredAppointments();
        appointmentTableView.setItems(CollectionManagement.getFilteredAppointments());

    }




    /**This FXML method generates a report of the total number of appointments filtered by type and month using a lambda expression. The lambda expression returns a string, which allows three different reports to be created with different expressions using one interface.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionGenerateNumOfAppointments(ActionEvent event) throws IOException {

        if (reportTypeComboBox.getSelectionModel().getSelectedItem().equals(null) || reportMonthComboBox.getSelectionModel().getSelectedItem().equals(null)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ALERT");
            alert.setContentText("Merci d'entrer un nom d'utilisateur valide!");
            alert.show();
        }

        Report customReport = () -> {

            String type = reportTypeComboBox.getSelectionModel().getSelectedItem();
            String month = reportMonthComboBox.getSelectionModel().getSelectedItem().toUpperCase(Locale.ROOT);
            String monthCasing = reportMonthComboBox.getSelectionModel().getSelectedItem();

            int count = 0;

            for (Appointment appointment : CollectionManagement.getAllAppointments()) {

                LocalDateTime date = appointment.getStart();
                String thisMonth = String.valueOf(date.getMonth());

                if (thisMonth.equals(month) && appointment.getType().equals(type)) {
                    count = count + 1;
                }

            }

            if (count == 1) {
                String report = "There is " + String.valueOf(count) + " " + type + " appointment in " + monthCasing + ".";
                return report;
            }
            else {
                String report = "There are " + String.valueOf(count) + " " + type + " appointments in " + monthCasing + ".";
                return report;
            }
        };

        String report = customReport.createReport();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Reports.fxml"));
        loader.load();

        ReportsController reportsController = loader.getController();
        reportsController.sendReport(report);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }




    /**This FXML method generates the schedule for each contact using a lambda expression. The lambda expression returns a string, which allows three different reports to be created with different expressions using one interface.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionGenerateScheduleByContact(ActionEvent event) throws IOException {


        if (reportContactComboBox.getSelectionModel().getSelectedItem().equals(null)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ALERT");
            alert.setContentText("Merci d'entrer un nom d'utilisateur valide!");
            alert.show();
        }

        String selectedContact = reportContactComboBox.getSelectionModel().getSelectedItem();

        Report customReport = () -> {
            String report = "Appointments with " + reportContactComboBox.getSelectionModel().getSelectedItem() + ":\n";
            for (Appointment appointment : CollectionManagement.getAllAppointments()) {
                String contact = appointment.getContact();
                if (contact.equals(selectedContact)) {
                    int appointmentId = appointment.getAppointmentId();
                    String title = appointment.getTitle();
                    String description = appointment.getDescription();
                    LocalDateTime start = appointment.getStart();
                    LocalDateTime end = appointment.getEnd();
                    int customerId = appointment.getCustomerId();
                    String appointmentInfo = "Appointment ID: #" + appointmentId + "     Title: " + title + "     Description: " + description + "     Starts: " + start + "     Ends: " + end + "     Customer ID: #" + customerId;
                    report = report + "\n" + appointmentInfo;
                }
            }
            return report;
        };

        String report = customReport.createReport();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Reports.fxml"));
        loader.load();

        ReportsController reportsController = loader.getController();
        reportsController.sendReport(report);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }




    /**This FXML method generates a list of all contacts and customers using a lambda expression. The lambda expression returns a string, which allows three different reports to be created with different expressions using one interface.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionGenerateAllContactsAndCustomers(ActionEvent event) throws IOException {

        Report customReport = () -> {
            String report = "CUSTOMERS:\n";
            for (Customer customers : CollectionManagement.getAllCustomers()) {
                String name = customers.getName();
                report = report + "\n" + name;
            }
            report = report + "\n\n\n" + "CONTACTS:\n";
            for (String contact : CollectionManagement.getAllContacts()) {
                String name = contact;
                report = report + "\n" + name;
            }
            return report;
        };

        String report = customReport.createReport();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Reports.fxml"));
        loader.load();

        ReportsController reportsController = loader.getController();
        reportsController.sendReport(report);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();


    }









    /**This method initializes the main menu and contains a lambda expression to generate an alert. The lambda expression takes the login time of the user as a parameter to generate an alert relating to the time proximity of appointments.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userLbl.setText("Hello " + ActiveUser.getActiveUser());

        try {
            CollectionManagement.refreshAllCustomers();
            CollectionManagement.refreshAllAppointments();
            CollectionManagement.refreshAllTypes();
            CollectionManagement.refreshAllContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        customerTableView.setItems(CollectionManagement.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        appointmentTableView.setItems(CollectionManagement.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        reportTypeComboBox.setItems(CollectionManagement.getAllTypes());
        reportMonthComboBox.setItems(CollectionManagement.getAllMonths());
        reportContactComboBox.setItems(CollectionManagement.getAllContacts());
        monthComboBox.setItems(CollectionManagement.getAllMonths());

        AppointmentAlert upcomingAppointment = (login, limit) -> {

            try {
                String sql = "SELECT * FROM appointments WHERE User_ID = ?";
                PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
                ps.setInt(1, ActiveUser.getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    LocalDateTime startLDT = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDate startDate = startLDT.toLocalDate();
                    LocalTime startTime = startLDT.toLocalTime();

                    if (startDate.isEqual(LocalDate.now()) && startTime.isAfter(login) && login.until(startTime, ChronoUnit.MINUTES) <= limit) {
                        int id = rs.getInt("Appointment_ID");
                        long difference = login.until(startTime, ChronoUnit.MINUTES);
                        String report = "User #" + ActiveUser.getId() + ", your appointment (#" + id + ") is scheduled for today (" + startDate + ") at " + startTime + ", which is in " + difference + " minutes!" ;
                        return report;
                    }
                }
            }
            catch (Exception e) {
            }
            String report = "You do not have any upcoming appointments!";
            return report;
        };

        try {
        String report = upcomingAppointment.generateAlert(ActiveUser.getLoginTime(), 15);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("UPCOMING APPOINTMENTS");
        alert.setContentText(report);
        alert.showAndWait();
        }
        catch (SQLException e) {
        e.printStackTrace();
        }

    }
}
