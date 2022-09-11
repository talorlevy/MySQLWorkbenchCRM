package Controller;

import Utilities.ActiveUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


/**This class controls the portal.*/
public class PortalController implements Initializable {

    /**This variable creates the stage for the GUI of the portal.*/
    Stage stage;

    /**This variable creates the stage for the GUI of the portal.*/
    Parent scene;

    /**This variable creates the resource bundle for the portal.*/
    public static ResourceBundle rb;

    /**This variable creates the France locale.*/
    public static Locale france = new Locale("fr", "FR");



    /**This FXML variable handles the custom time zone label.*/
    @FXML
    private Label customTimeZoneLbl;

    /**This FXML variable handles the login button.*/
    @FXML
    private Button logInBtn;

    /**This FXML variable handles the password label.*/
    @FXML
    private Label passwordLbl;

    /**This FXML variable handles the password text field.*/
    @FXML
    private PasswordField passwordTxt;

    /**This FXML variable handles the time zone label.*/
    @FXML
    private Label timeZoneLbl;

    /**This FXML variable handles the user ID label.*/
    @FXML
    private Label usernameLbl;

    /**This FXML variable handles the user ID text field.*/
    @FXML
    private TextField usernameTxt;



    /**This FXML method logs in a user based on credentials and opens the main menu.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionLogIn(ActionEvent event) throws IOException, SQLException {

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if (username.equals("1") && password.equals("test")) {
            ActiveUser.setActiveUser("Test");
            ActiveUser.setId(1);
            String userName = usernameTxt.getText();
            LocalTime login = LocalTime.now();
            ActiveUser.setLoginTime(login);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            ZonedDateTime time = ZonedDateTime.now();
            String filename = "activitylog.txt";
            FileWriter fWriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fWriter);
            outputFile.println("Username '" + userName + "' successfully logged in at " + time);
            outputFile.close();
        }
        if (username.equals("2") && password.equals("admin")) {
            ActiveUser.setActiveUser("Admin");
            ActiveUser.setId(2);
            String userName = usernameTxt.getText();
            LocalTime login = LocalTime.now();
            ActiveUser.setLoginTime(login);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            ZonedDateTime time = ZonedDateTime.now();
            String filename = "activitylog.txt";
            FileWriter fWriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fWriter);
            outputFile.println("Username '" + userName + "' successfully logged in at " + time);
            outputFile.close();
        }
        if (!username.equals("1") && !username.equals("2")) {
            ZonedDateTime time = ZonedDateTime.now();
            String userName = usernameTxt.getText();
            String filename = "activitylog.txt";
            FileWriter fWriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fWriter);
            outputFile.println("A failed log in attempt occurred with the invalid username '" + userName + "' at " + time);
            outputFile.close();
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("NOM D'UTILISATEUR INVALIDE");
                alert.setContentText("Merci d'entrer un nom d'utilisateur valide!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("INVALID USERNAME");
                alert.setContentText("Please enter a valid username!");
                alert.showAndWait();
            }
        }
        if (username.equals("1") && !password.equals("test")) {
            ZonedDateTime time = ZonedDateTime.now();
            String userName = usernameTxt.getText();
            String filename = "activitylog.txt";
            FileWriter fWriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fWriter);
            outputFile.println("User '" + userName + "' failed to login with an incorrect password at " + time);
            outputFile.close();
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("MOT DE PASSE INCORRECT");
                alert.setContentText("Veuillez saisir le bon mot de passe!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("INVALID PASSWORD");
                alert.setContentText("Please enter the correct password!");
                alert.showAndWait();
            }
        }
        if (username.equals("2") && !password.equals("admin")) {
            ZonedDateTime time = ZonedDateTime.now();
            String userName = usernameTxt.getText();
            String filename = "activitylog.txt";
            FileWriter fWriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fWriter);
            outputFile.println("User '" + userName + "' failed to login with an incorrect password at " + time);
            outputFile.close();
            if(Locale.getDefault().getLanguage().equals("fr")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("MOT DE PASSE INCORRECT");
                alert.setContentText("Veuillez saisir le bon mot de passe!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("INVALID PASSWORD");
                alert.setContentText("Please enter the correct password!");
                alert.showAndWait();
            }
        }
    }



    /**This method initializes the portal.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (Locale.getDefault().getLanguage().equals("fr")) {
            Locale.setDefault(france);
            rb = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());
            usernameLbl.setText("Identifiant d'utilisateur");
            passwordLbl.setText(rb.getString(passwordLbl.getText()));
            logInBtn.setText("Connexion");
            timeZoneLbl.setText("Fuseau Horaire");
        }

        ZoneId timeZone = ZoneId.systemDefault();
        customTimeZoneLbl.setText(timeZone.toString());

    }
}
