package Model;

import Database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**This class controls customer objects.*/
public class Customer {

    /**This variable creates the id.*/
    private int id;

    /**This variable creates the name.*/
    private String name;

    /**This variable creates the phone.*/
    private String phone;

    /**This variable creates the address.*/
    private String address;

    /**This variable creates the division.*/
    private String division;

    /**This variable creates the country.*/
    private String country;

    /**This variable creates the postal code.*/
    private String postalCode;




    /**This method creates a customer.
     * @param id
     * @param name
     * @param phone
     * @param address
     * @param division
     * @param country
     * @param postalCode
     */
    public Customer(int id, String name, String phone, String address, String division, String country, String postalCode) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.division = division;
        this.country = country;
        this.postalCode = postalCode;
    }

    /**This method gets the ID.*/
    public int getId() {
        return id;
    }

    /**This method sets the ID.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**This method gets the name.*/
    public String getName() {
        return name;
    }

    /**This method sets the name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**This method gets the phone.*/
    public String getPhone() {
        return phone;
    }

    /**This method sets the phone.
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**This method sets the address.*/
    public String getAddress() {
        return address;
    }

    /**This method gets the address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**This method gets the division.*/
    public String getDivision() {
        return division;
    }

    /**This method sets the division.
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**This method gets the country.*/
    public String getCountry() {
        return country;
    }

    /**This method sets the country.
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**This method gets the postal code.*/
    public String getPostalCode() {
        return postalCode;
    }

    /**This method sets the postal code.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }




    /**This method gets the division ID.
     * @param name
     * @throws SQLException
     */
    public int getDivisionId(String name) throws SQLException {

        try {
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Division_ID");
                return id;
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


}
