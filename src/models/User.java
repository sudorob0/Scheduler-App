package models;

/**
 * Customer user to create user objects. It also sets and gets the user attributes.
 */
public class User {

    private int userID = 0;
    private final String userName;
    private final String password;

    /**
     * Create user object
     * @param userID integer that sets the userID
     * @param userName string that sets the userName
     * @param password string that sets the users Password
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;

    }

    /**
     * Getter for userName,
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for user password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for userID
     * @return the userID
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Setter for userID
     * @param userID the userID to set to the User object
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Returns a string for userID
     * @return The string the combobox uses to display the user in the add/update appointment view
     */
    public String toString() {
        return String.valueOf(userID);
    }

}