<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="ca.utoronto.ece1779h.model.Flashcard" %>
<%@ page import="ca.utoronto.ece1779h.model.FlashcardHelper" %>
<%@ page import="ca.utoronto.ece1779h.model.Stackcard" %>
<%@ page import="ca.utoronto.ece1779h.model.StackcardHelper" %>
<%@ page import="ca.utoronto.ece1779h.authentication.Authentication" %>
<%@ page import="java.util.List" %>
<%
    String keyString = request.getParameter("key");
    Key key = KeyFactory.stringToKey(keyString);
    Stackcard stack = StackcardHelper.getStackcard(key);
    List<Flashcard> flashcards = stack.getFlashcards();

    User user = Authentication.authenticate();
    String loginBar = Authentication.getLoginBar();

    // Null user shouldn't be here. Redirect.
    if (user == null){
        response.sendRedirect("/");
    } 
%>
<html>
  <head>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/flashcard-view.js"></script>
    <script type="text/javascript" src="js/sweet-alert.min.js"></script>
    <script type="text/javascript" src="js/jquery.peity.min.js"></script>
    <link href="../stylesheets/sweet-alert.css" rel="stylesheet">
  </head>
  <body>
  <p><%= loginBar %></p>
  <h1><%= stack.getName() %></h1>
  <a href="/train?key=<%= keyString %>" >TRAIN</a>
    <table id="tblData" data-keystack="<%= keyString %>">
      <thead>
        <tr>
          <th>Success Rate</th>
          <th>Question</th>
          <th>Answer</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
      <%
      for (Flashcard f : flashcards) {
      %>
      <tr data-key="<%= KeyFactory.keyToString(f.getKey()) %>">
      <td><span class="pie"><%= f.getNumberRights() %>/<%= f.getNumberRights() + f.getNumberWrongs() %></span></td>
      <td><%= f.getQuestion() %></td>
      <td><%= f.getAnswer() %></td>
	  <td><button class='btnDelete'>delete</button><button class='btnEdit'>edit</button></td>
      </tr>
      <%
      }
      %>
      </tbody>
    </table>
    <button id="btnAdd">Add New Flashcard</button>
  </body>
</html>
