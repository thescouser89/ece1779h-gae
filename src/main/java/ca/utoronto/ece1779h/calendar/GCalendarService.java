package ca.utoronto.ece1779h.calendar;

import ca.utoronto.ece1779h.Constants;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GCalendarService {
    public static List<String> getCalendars() throws IOException {

        List<String> calendarNames = new LinkedList<String>();
        Calendar client = Utils.loadCalendarClient();
        com.google.api.services.calendar.Calendar.CalendarList.List listRequest =
                client.calendarList().list();

        listRequest.setFields("items(id,summary)");
        CalendarList feed = listRequest.execute();

        if (feed.getItems() != null) {
            for (CalendarListEntry entry : feed.getItems()) {
                calendarNames.add(entry.getSummary());
            }
        }
        return calendarNames;
    }
    
    public static String getURLForAuth(final String redirectTo) 
            throws IOException {
        String appId = Constants.getAppId(); 
        
        String url = Utils.newFlow()
                .newAuthorizationUrl()
                .setState(redirectTo)
                .setRedirectUri("http://" + appId + ".appspot.com/oauth2callback")
                .build();
        
        return url;
    }
}


