package Model;

import java.io.IOException;


/**This interface controls reports.*/
public interface Report {

    /**This method creates a report. Justification for the lambda expression can be in the onActionGenerateNumOfAppointments, onActionGenerateScheduleByContact, andonActionGenerateAllContactsAndCustomers methods in the MainMenuController.
     * @throws IOException
     */
    String createReport() throws IOException;

}
