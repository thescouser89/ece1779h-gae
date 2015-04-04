<%@ page import="ca.utoronto.ece1779h.model.Stackcard" %>
<%@ page import="ca.utoronto.ece1779h.model.StackcardHelper" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="ca.utoronto.ece1779h.authentication.Authentication" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="java.util.List" %>

<%
	Key key;

	// only the owner can delete stackcards
	User user = Authentication.authenticate();
	if (user == null) response.sendRedirect("mystacks");
	
	String keystring = (String) request.getParameter("key");
	if (keystring == null) response.sendRedirect("mystacks");
	else {
		key = KeyFactory.stringToKey(keystring);
		List<Stackcard> stackcards = Stackcard.getStacks(user.getUserId());
		
		for (Stackcard s : stackcards){
			if (s.getKey().equals(key)){
				out.println("DELETED");
				// s.delete();
				StackcardHelper.deleteStackcard(key);
			}
		}
	}

	//out.println("FAILED");
	response.sendRedirect("mystacks");
%>