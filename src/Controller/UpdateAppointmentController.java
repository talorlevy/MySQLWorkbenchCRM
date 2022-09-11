package Controller;

import Database.DBAppointments;
import Database.DBConnection;
import Model.Appointment;
import Model.CollectionManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;



/**This class controls the update appointment menu.*/
public class UpdateAppointmentController implements Initializable {

    /**This variable creates the stage for the GUI of the update appointment menu.*/
    Stage stage;

    /**This variable creates the stage for the GUI of the update appointment menu.*/
    Parent scene;





    /**This FXML variable handles the contact combo box.*/
    @FXML
    private ComboBox<String> appointmentContactComboBox;

    /**This FXML variable handles the customer combo box.*/
    @FXML
    private ComboBox<Integer> appointmentCustomerComboBox;

    /**This FXML variable handles the date picker.*/
    @FXML
    private DatePicker appointmentDatePicker;

    /**This FXML variable handles the description text field.*/
    @FXML
    private TextField appointmentDescriptionTxt;

    /**This FXML variable handles the end time combo box.*/
    @FXML
    private ComboBox<LocalTime> appointmentEndTimeComboBox;

    /**This FXML variable handles the appointment id text field.*/
    @FXML
    private TextField appointmentIdTxt;

    /**This FXML variable handles the location text field.*/
    @FXML
    private TextField appointmentLocationTxt;

    /**This FXML variable handles the start time combo box.*/
    @FXML
    private ComboBox<LocalTime> appointmentStartTimeComboBox;

    /**This FXML variable handles the title text field.*/
    @FXML
    private TextField appointmentTitleTxt;

    /**This FXML variable handles the type combo box.*/
    @FXML
    private ComboBox<String> appointmentTypeComboBox;

    /**This FXML variable handles the user combo box.*/
    @FXML
    private ComboBox<Integer> appointmentUserComboBox;





    /**This FXML method cancels the update and displays the main menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }




    /**This FXML method filters end times based on the selected start time.
     * @param event
     */
    @FXML
    void onActionFilterEndTime(ActionEvent event) {
        CollectionManagement.refreshAvailableEndTimes();
        CollectionManagement.refreshFilteredEndTimes(appointmentStartTimeComboBox.getSelectionModel().getSelectedItem());
        appointmentEndTimeComboBox.setItems(CollectionManagement.getFilteredEndTimes());
    }




    /**This FXML method updates the appointment and displays the main menu.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws SQLException, IOException {

        int appointmentId = Integer.parseInt(appointmentIdTxt.getText());
        String title = appointmentTitleTxt.getText();
        String description = appointmentDescriptionTxt.getText();
        String location = appointmentLocationTxt.getText();
        String type = appointmentTypeComboBox.getSelectionModel().getSelectedItem();

        LocalTime startTime = appointmentStartTimeComboBox.getSelectionModel().getSelectedItem();
        LocalTime endTime = appointmentEndTimeComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = appointmentDatePicker.getValue();

        LocalDateTime start = LocalDateTime.of(date, startTime);
        LocalDateTime end = LocalDateTime.of(date, endTime);

        int customerId = appointmentCustomerComboBox.getSelectionModel().getSelectedItem();
        int userId = appointmentUserComboBox.getSelectionModel().getSelectedItem();
        String contact = appointmentContactComboBox.getSelectionModel().getSelectedItem();

        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LocalDateTime conflictStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime conflictEnd = rs.getTimestamp("End").toLocalDateTime();
            int idClarify = rs.getInt("Appointment_ID");

            if (start.equals(conflictStart) || start.isAfter(conflictStart) && start.isBefore(conflictEnd)) {
                if (idClarify != appointmentId) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("SCHEDULING CONFLICT");
                    alert.setContentText("There is an appointment already scheduled for this customer during this time!");
                    alert.show();
                    return;
                }
            }
            if (end.isAfter(conflictStart) && end.isBefore(conflictEnd)) {
                if (idClarify != appointmentId) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("SCHEDULING CONFLICT");
                    alert.setContentText("There is an appointment already scheduled for this customer during this time!");
                    alert.show();
                    return;
                }
            }
        }

        Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contact);
        DBAppointments.update(appointment);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }




    /**This method populates the menu with an appointment selected from the main menu.
     * @param appointment
     */
    public void sendAppointment (Appointment appointment) {
        appointmentIdTxt.setText(String.valueOf(appointment.getAppointmentId()));
        appointmentTitleTxt.setText(appointment.getTitle());
        appointmentDescriptionTxt.setText(appointment.getDescription());
        appointmentLocationTxt.setText(appointment.getLocation());
        appointmentTypeComboBox.getSelectionModel().select(appointment.getType());
        appointmentContactComboBox.getSelectionModel().select(appointment.getContact());
        appointmentCustomerComboBox.getSelectionModel().select(appointment.getCustomerId() - 1);
        appointmentUserComboBox.getSelectionModel().select(appointment.getUserId() - 1);
        LocalDate date = appointment.getStart().toLocalDate();
        LocalTime start = appointment.getStart().toLocalTime();
        LocalTime end = appointment.getEnd().toLocalTime();
        appointmentDatePicker.setValue(date);
        appointmentStartTimeComboBox.getSelectionModel().select(start);
        appointmentEndTimeComboBox.getSelectionModel().select(end);
    }






    /**This method initializes the update appointment menu.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentIdTxt.setDisable(true);

        CollectionManagement.refreshAllCustomerIds();
        CollectionManagement.refreshAllContacts();
        CollectionManagement.refreshAllUsers();
        CollectionManagement.setAvailableStartTimes();
        CollectionManagement.refreshAvailableEndTimes();

        appointmentContactComboBox.setItems(CollectionManagement.getAllContacts());
        appointmentCustomerComboBox.setItems(CollectionManagement.getAllCustomerIds());
        appointmentUserComboBox.setItems(CollectionManagement.getAllUsers());
        appointmentStartTimeComboBox.setItems(CollectionManagement.getAvailableStartTimes());
        appointmentEndTimeComboBox.setItems(CollectionManagement.getAvailableEndTimes());
        appointmentTypeComboBox.setItems(CollectionManagement.getAllTypes());

    }
}
