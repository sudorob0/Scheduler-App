package models;

/**
 * Appointment class to create country objects. It also sets and gets country attributes.
 */
public class Country {
    private final int countryID;
    private final String country;

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * getter for countryID
     * @return countryID int
     */
    public int getCountryID() {
    return countryID;
    }

    /**
     * getter for country name
     * @return country name
     */
    public String getCountry() {
        return country;
    }
}
