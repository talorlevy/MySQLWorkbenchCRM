package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;


/**This class controls the reports page.*/
public class ReportsController {

    /**This variable creates the stage for the GUI of the reports page.*/
    Stage stage;

    /**This variable creates the stage for the GUI of the reports page.*/
    Parent scene;



    /**This FXML variable handles the data text area.*/
    @FXML
    private TextArea dataTextArea;



    /**This FXML method displays the main menu.
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



    /**This method populates the data text area with the report.
     * @param report
     */
    public void sendReport (String report) {
        dataTextArea.setText(report);
    }

}


