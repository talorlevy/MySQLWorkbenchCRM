package Main;

import Database.DBConnection;
import Model.CollectionManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

/**This class creates a program that handles a database.*/
public class Main extends Application {


    /**This is the start method for the program and initializes the GUI for the portal.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Portal.fxml"));
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();
    }




    /**This is the main method that serves as the entry point for the execution of the program.
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        DBConnection.openConnection();
        CollectionManagement.loadAllCountries();
        CollectionManagement.setAllMonths();
        launch(args);
        DBConnection.closeConnection();
    }
}
