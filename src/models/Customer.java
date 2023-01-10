package models;


/**
 * Customer class for customer objects.
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerZipCode;
    private String customerPhone;
    private String customerDivision;
    private int divisionID;
    private String customerCountry;


    /**
     * Creates a customer object
     * @param customerID sets the ID of the customer as an integer.
     *  @param customerName sets the name of the customer as a string.
     *  @param customerAddress sets the customer address.
     *  @param customerZipCode sets the customer postal code.
     *  @param customerPhone sets the customer phone number.
     *  @param customerDivision sets the customer division using the country selection.
     *  @param divisionID sets the customer division ID using the division selection as an integer.
     *  @param customerCountry sets the customer country. */
    public Customer(int customerID, String customerName, String customerAddress, String customerZipCode,
                    String customerPhone, String customerDivision, int divisionID, String customerCountry) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZipCode = customerZipCode;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
        this.divisionID = divisionID;
        this.customerCountry = customerCountry;
    }

    /**
     * Getter for customerID,
     * @return the ID of the customer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for customerID,
     * @param customerID Setter for the ID of the customer.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for customerName,
     * @return the name of the customer.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for customerName
     * @param customerName Setter for customerName.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for customerAddress,
     * @return the address of the customer.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Setter for customerAddress
     * @param customerAddress Setter for customerAddress.
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Getter for customerPostalCode,
     * @return the Zip code of the customer.
     */
    public String getCustomerZipCode() {
        return customerZipCode;
    }

    /**
     * Setter for customerZipCode
     * @param customerZipCode Setter for customerZipCode.
     */
    public void setCustomerZipCode(String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

    /**
     * Getter for customerPhone,
     * @return the phone number of the customer.
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Setter for customerPhone
     * @param customerPhone Setter for customerPhone.
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * Getter for divisionID,
     * @return the ID of the selected division.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for divisionID,
     * @param divisionID Setter for divisionID.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     *Getter for customerDivision,
     * @return the division of the selected customer.
     */
    public String getCustomerDivision() {
        return customerDivision;
    }

    /**
     * Setter for customerDivision
     * @param customerDivision Setter for customerDivision.
     */
    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }

    /**
     * Getter for customerCountry,
     * @return the country of the selected customer.
     */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**
     * Setter for customerCountry
     * @param customerCountry Setter for customerCountry.
     */
    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

}
