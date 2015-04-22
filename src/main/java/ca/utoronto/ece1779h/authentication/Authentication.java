package ca.utoronto.ece1779h.authentication;

import ca.utoronto.ece1779h.model.*;

import java.util.List;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.KeyFactory;

public class Authentication {

    // Get current user.
	public static User authenticate(){

		UserService userService = UserServiceFactory.getUserService();

        return userService.getCurrentUser();
	}

    // Returns regular, text-based, menu.
	public static String getLoginBar(){
		String loginBar;

		UserService userService = UserServiceFactory.getUserService();
        User user =  userService.getCurrentUser();

		if (user != null) {
            loginBar = "Welcome, " + user.getNickname() + "! You can <a href=\"" +
                     userService.createLogoutURL("/") +
                     "\">sign out</a>.";
        } else {
            loginBar = "Welcome! <a href=\"" + userService.createLoginURL("/") +
                     "\">Sign in or register</a> to customize.";
        }

        return loginBar;
	}

    // Return HTML for menu bar. Dropdown menus for each stacks, user menu with options to logout, etc.
    public static String getMenu(String section){
        String menuBar, stackActive, cardActive, trainActive;

        UserService userService = UserServiceFactory.getUserService();
        User user =  userService.getCurrentUser();

        // Highlight menu links depending on the current page.
        if (section.equals("stacks")){
            stackActive = "<li class=\"active\">";
            cardActive = "<li class=\"dropdown\">";
            trainActive = "<li class=\"dropdown\">";
        } else if (section.equals("cards")){
            stackActive = "<li>";
            cardActive = "<li class=\"dropdown active\">";
            trainActive = "<li class=\"dropdown\">";
        } else if (section.equals("train")){
            stackActive = "<li>";
            cardActive = "<li class=\"dropdown\">";
            trainActive = "<li class=\"dropdown active\">";
        } else {
            stackActive = "<li>";
            cardActive = "<li class=\"dropdown\">";
            trainActive = "<li class=\"dropdown\">";
        }

        if (user != null) {
            menuBar =
                  "<nav class=\"navbar navbar-default\">"
                + "  <div class=\"container-fluid\">"
                + "    <!-- Brand and toggle get grouped for better mobile display -->"
                + "    <div class=\"navbar-header\">"
                + "      <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\">"
                + "        <span class=\"sr-only\">Toggle navigation</span>"
                + "        <span class=\"icon-bar\"></span>"
                + "        <span class=\"icon-bar\"></span>"
                + "        <span class=\"icon-bar\"></span>"
                + "      </button>"
                + "      <a class=\"navbar-brand\" href=\"/\">Stacky</a>"
                + "    </div>"
                + ""
                + "    <!-- Collect the nav links, forms, and other content for toggling -->"
                + "    <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">"
                + "      <ul class=\"nav navbar-nav\">"
                + "        " + stackActive + "<a href=\"/mystacks\">Home <span class=\"sr-only\">(current)</span></a></li>"
                + "        " + cardActive
                + "          <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">My Stacks <span class=\"caret\"></span></a>"
                + "          <ul class=\"dropdown-menu\" role=\"menu\">";

            // Create link in dropdown for each stack...
            String user_id = user.getUserId();
            List<Stackcard> stackcards = Stackcard.getStacks(user_id);
            
            if (stackcards.size() == 0){
                menuBar += "          <li><a href=\"#\">No stacks here.</a></li>";
            }
            
            for (Stackcard s : stackcards)
            {
                menuBar += "          <li><a href=\"/flashcard?key=" + KeyFactory.keyToString(s.getKey()) + "\">" + s.getName() + "</a></li>";
            }

            menuBar +=
                  "          </ul>"
                + "        </li>"
                + "        " + trainActive
                + "          <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">Train <span class=\"caret\"></span></a>"
                + "          <ul class=\"dropdown-menu\" role=\"menu\">";

            // Create link in dropdown menu for eack stack...
            if (stackcards.size() == 0){
                menuBar += "          <li><a href=\"#\">No stacks here.</a></li>";
            }
            
            for (Stackcard s : stackcards)
            {
                menuBar += "          <li><a href=\"/train?key=" + KeyFactory.keyToString(s.getKey()) + "\">" + s.getName() + "</a></li>";
            }

            // Menu name customized to the user's google nickname.
            menuBar +=
                  "          </ul>"
                + "        </li>"
                + "      </ul>"
                + "      <ul class=\"nav navbar-nav navbar-right\">"
                + "        <li class=\"dropdown\">"
                + "          <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">" + user.getNickname() + " <span class=\"caret\"></span></a>"
                + "          <ul class=\"dropdown-menu\" role=\"menu\">"
                + "            <li><a href=\"" + userService.createLogoutURL("/") + "\">Sign Out</a></li>"
                + "          </ul>"
                + "        </li>"
                + "      </ul>"
                + "    </div><!-- /.navbar-collapse -->"
                + "  </div><!-- /.container-fluid -->"
                + "</nav>";
        } else {
            menuBar = "Welcome! <a href=\"" + userService.createLoginURL("/") +
                     "\">Sign in or register</a> to customize.";
        }

        return menuBar;
    }

}
