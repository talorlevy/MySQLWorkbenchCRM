package Database;

import Model.Customer;
import Utilities.ActiveUser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**This class controls the database connection to customers.*/
public class DBCustomers {


    /**This method inserts a customer into the database.
     * @param customer
     * @throws SQLException
     */
    public static int insert(Customer customer) throws SQLException {

        String name = customer.getName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();
        LocalDateTime createDate = LocalDateTime.now();
        String createdBy = ActiveUser.getActiveUser();
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = ActiveUser.getActiveUser();

        String division = customer.getDivision();
        int divisionId = customer.getDivisionId(division);

        String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(createDate));
        ps.setString(6, createdBy);
        ps.setTimestamp(7, Timestamp.valueOf(lastUpdate));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }




    /**This method updates a customer in the database.
     * @param customer
     * @throws SQLException
     */
    public static int update(Customer customer) throws SQLException {

        String name = customer.getName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = ActiveUser.getActiveUser();

        String division = customer.getDivision();
        int divisionId = customer.getDivisionId(division);

        int customerId = customer.getId();

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(lastUpdate));
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, customerId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }





    /**This method deletes a customer in the database.
     * @param id
     * @throws SQLException
     */
    public static int delete(int id) throws SQLException {

        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


}
