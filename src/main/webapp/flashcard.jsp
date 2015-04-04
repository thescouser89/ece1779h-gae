<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="ca.utoronto.ece1779h.model.Flashcard" %>
<%@ page import="ca.utoronto.ece1779h.model.FlashcardHelper" %>
<%@ page import="ca.utoronto.ece1779h.model.Stackcard" %>
<%@ page import="ca.utoronto.ece1779h.model.StackcardHelper" %>
<%@ page import="java.util.List" %>
<%
    String keyString = request.getParameter("key");
    Key key = KeyFactory.stringToKey(keyString);
    Stackcard stack = StackcardHelper.getStackcard(key);
    List<Flashcard> flashcards = stack.getFlashcards();
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
  <a href="/train?key=<%= keyString %>" >haha</a>
    <span class="pie">1/3</span>
    <table id="tblData">
      <thead>
        <tr>
          <th>Success Rate</th>
          <th>Question</th>
          <th>Answer</th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
      <%
      for (Flashcard f : flashcards) {
      %>
      <tr>
      <td><span class="pie">0/0</span></td>
      <td data-key="<%= KeyFactory.keyToString(f.getKey()) %>"><%= f.getQuestion() %></td>
      <td><%= f.getAnswer() %></td>
      <td></td>
      </tr>
      <%
      }
      %>
      </tbody>
    </table>
    <button id="btnAdd">Add New Flashcard</button>
  </body>
</html>
