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
    List<Flashcard> flashcards = FlashcardHelper.getFlashcardsFromStack(stack);

    User user = Authentication.authenticate();
    String loginBar = Authentication.getMenu("cards");

    // Null user shouldn't be here. Redirect.
    if (user == null){
        response.sendRedirect("/");
    } 
%>
<html>
  <head>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.noty.packaged.min.js"></script>
    <script type="text/javascript" src="js/sweet-alert.min.js"></script>
    <script type="text/javascript" src="js/jquery.peity.min.js"></script>
    <script type="text/javascript" src="js/flashcard-view.js"></script>
    <link href="stylesheets/sweet-alert.css" rel="stylesheet">
    <link href="stylesheets/flashcard.css" rel="stylesheet">
    <!-- NEXT 2 LINES FOR BOOTSTRAP -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  </head>
    <body>
      <div class="container">
        <p class="text-right"><%= loginBar %></p>
        <h1><%= stack.getName() %></h1>
        <ul>
        <a href="/train?key=<%= keyString %>" class="btn btn-primary">TRAIN</a>
        <br>
        <table id="tblData" data-keystack="<%= keyString %>" class="table table-hover">
          <thead>
            <tr>
              <th>Success Rate</th>
              <th>Question</th>
              <th>Answer</th>
              <th>Commands</th>
           </tr>
          </thead>
          <tbody>
        <%
        for (Flashcard f : flashcards) {
        %>
        <tr data-key="<%= KeyFactory.keyToString(f.getKey()) %>">
        <td class='col-md-2'><span class="pie"><%= f.getNumberRights() %>/<%= f.getNumberRights() + f.getNumberWrongs() %></span></td>
        <td class='col-md-4'><%= f.getQuestion() %></td>
        <td class='col-md-4'><%= f.getAnswer() %></td>
	      <td class='col-md-2'><button class="btnEdit btn btn-default"><span class='glyphicon glyphicon-pencil'></span></button><button class="btnDelete btn btn-default"><span class='glyphicon glyphicon-remove'></span></button></td>
        </tr>
        <%
        }
        %>
      </tbody>
    </table>
    <button id="btnAdd" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>  Add New Flashcard</button>
    <ul>
    </div>
  </body>
</html>
