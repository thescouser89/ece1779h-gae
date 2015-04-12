package ca.utoronto.ece1779h;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Dump any constants here
 */
public class Constants {
    public static final String APPLICATION_NAME = "stacky";

    public static String getAppId() {
        return SystemProperty.applicationId.get();
    }
}
