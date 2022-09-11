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


/**This class controls the create appointment menu.*/
public class CreateAppointmentController implements Initializable {

    /**
     * This variable creates the stage for the GUI of the create appointment menu.
     */
    Stage stage;

    /**
     * This variable creates the scene for the GUI of the create appointment menu.
     */
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

    /**This FXML variable handles the id text field.*/
    @FXML
    private TextField appointmentIdTxt;

    /**This FXML variable handles the location text field.*/
    @FXML
    private TextField appointmentLocationTxt;

    /**This FXML variable handles the start combo box*/
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




    /**This FXML method allows the cancel button to send the user back to the main menu.
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




    /**This FXML method filters the end time options based on the selected start time.
     * @param event
     */
    @FXML
    void onActionFilterEndTime(ActionEvent event) {
        CollectionManagement.refreshAvailableEndTimes();
        CollectionManagement.refreshFilteredEndTimes(appointmentStartTimeComboBox.getSelectionModel().getSelectedItem());
        appointmentEndTimeComboBox.setItems(CollectionManagement.getFilteredEndTimes());
    }






    /**This FXML method saves the appointment details into the database.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException, IOException {

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

            if (start.equals(conflictStart) || start.isAfter(conflictStart) && start.isBefore(conflictEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("SCHEDULING CONFLICT");
                alert.setContentText("There is an appointment already scheduled for this customer during this time!");
                alert.show();
                return;
            }
            if (end.isAfter(conflictStart) && end.isBefore(conflictEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("SCHEDULING CONFLICT");
                alert.setContentText("There is an appointment already scheduled for this time!");
                alert.show();
                return;
            }
        }

        Appointment appointment = new Appointment(1, title, description, location, type, start, end, customerId, userId, contact);
        DBAppointments.insert(appointment);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }






    /**
     * This method initializes the create appointment menu.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentIdTxt.setText("Auto Gen- disabled");
        appointmentIdTxt.setDisable(true);

        CollectionManagement.refreshAllCustomerIds();
        CollectionManagement.refreshAllContacts();
        CollectionManagement.refreshAllUsers();
        CollectionManagement.setAvailableStartTimes();
        CollectionManagement.refreshAvailableEndTimes();
        CollectionManagement.refreshAllTypes();

        appointmentContactComboBox.setItems(CollectionManagement.getAllContacts());
        appointmentCustomerComboBox.setItems(CollectionManagement.getAllCustomerIds());
        appointmentUserComboBox.setItems(CollectionManagement.getAllUsers());
        appointmentStartTimeComboBox.setItems(CollectionManagement.getAvailableStartTimes());
        appointmentEndTimeComboBox.setItems(CollectionManagement.getAvailableEndTimes());
        appointmentTypeComboBox.setItems(CollectionManagement.getAllTypes());

    }



}
