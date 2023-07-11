package data;

/**
 * Enumeration with city climate constants.
 */
public enum Climate {
    RAIN_FOREST,
    OCEANIC,
    MEDITERRANIAN,
    TUNDRA;

    /**
     * Generates a list of enum string values.
     * @return String with all enum values.
     */
    public static String sortClimate() {
        String AllClimate = "";
        for (Climate climate : values()) {
            AllClimate += climate.name() + ", ";
        }
        return AllClimate.substring(0, AllClimate.length() - 2);
    }

}