package ca.utoronto.ece1779h.authentication;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Authentication {

	public static User authenticate(){

		UserService userService = UserServiceFactory.getUserService();

        return userService.getCurrentUser();
	}

	public static String getLoginBar(){
		String loginBar;

		UserService userService = UserServiceFactory.getUserService();
        User user =  userService.getCurrentUser();

		if (user != null) {
            loginBar = "<p>Welcome, " + user.getNickname() + "! You can <a href=\"" +
                     userService.createLogoutURL("/") +
                     "\">sign out</a>.</p>";
        } else {
            loginBar = "<p>Welcome! <a href=\"" + userService.createLoginURL("/") +
                     "\">Sign in or register</a> to customize.</p>";
        }

        return loginBar;
	}
}
