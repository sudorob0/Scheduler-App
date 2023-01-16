package models;

/**
 * Class for the Division object
 */
public class Division {

    private final int divisionID;
    private final String division;
    private final int countryID;

    /**
     * Constructor for first level division object
     * @param divisionID sets the divisionID
     * @param division sets a string as the division name
     * @param countryID sets an integer as countryID
     */
    public Division(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Getter for divisionID
     * @return the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Getter for division Name
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Getter for countryID
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }


    /**
     * Getter to return a string of the division Name
     * @return The string the combo box uses to display the division in the list
     */
    public String toString() {
        return division;
    }
}


