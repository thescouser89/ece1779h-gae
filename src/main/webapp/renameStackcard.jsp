<%@ page import="ca.utoronto.ece1779h.model.Stackcard" %>
<%@ page import="ca.utoronto.ece1779h.model.StackcardHelper" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="ca.utoronto.ece1779h.authentication.Authentication" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="java.util.List" %>

<%
	Key key;

	// only the owner can rename stackcards
	User user = Authentication.authenticate();
	if (user != null){
		String name = (String) request.getParameter("name");
		String keystring = (String) request.getParameter("key");
		
		if (keystring != null) {
			key = KeyFactory.stringToKey(keystring);
			
			List<Stackcard> stackcards = Stackcard.getStacks(user.getUserId());
			
			for (Stackcard s : stackcards){
				if (s.getKey().equals(key)){
					// s.delete();
					s.setName(name);
					s.save();
					out.println("RENAMED");
				}
			}
		}
	}
%>