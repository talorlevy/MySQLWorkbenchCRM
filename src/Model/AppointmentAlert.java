package Model;

import java.sql.SQLException;
import java.time.LocalTime;


/**This interface controls appointment alerts.*/
public interface AppointmentAlert {

    /**This method generates an alert. Justification for the lambda expression can be found initialize method in MainMenController on line 499.
     * @param login
     * @param limit
     * @throws SQLException
     */
    String generateAlert (LocalTime login, long limit) throws SQLException;

}
