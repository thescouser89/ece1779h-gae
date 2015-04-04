<%@ page import="ca.utoronto.ece1779h.model.Stackcard" %>
<%@ page import="ca.utoronto.ece1779h.model.StackcardHelper" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="ca.utoronto.ece1779h.authentication.Authentication" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="java.util.List" %>

<%
	Key key;

	// only an owner can create stackcards
	User user = Authentication.authenticate();
	if (user == null) response.sendRedirect("mystacks");
	
	String name = (String) request.getParameter("name");
	if (name == null) response.sendRedirect("mystacks");
	else {
		StackcardHelper.createStackcard(name, user.getUserId());
	}

	response.sendRedirect("mystacks");
%>