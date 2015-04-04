<!-- 
stackcards_index.jsp
 -show list of all stackcards
 -create, delete, rename stackcards
 mvn appengine:devserver
-->

<%@ page import="ca.utoronto.ece1779h.model.Stackcard" %>
<%@ page import="ca.utoronto.ece1779h.model.StackcardHelper" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="ca.utoronto.ece1779h.authentication.Authentication" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="java.util.List" %>

<html>
	<head>
		<script>
			function rename(key){
				
				var xmlhttp;
				xmlhttp=new XMLHttpRequest();
				
				xmlhttp.onreadystatechange=function(){
					if (xmlhttp.readyState==4 && xmlhttp.status==200){
						//
					}
				}
				
				xmlhttp.open("GET","/renamestack?key="+
									encodeURIComponent(key)+
									"&name="+encodeURIComponent(document.getElementById("id_sc_input_"+key).value),true);
				xmlhttp.send();
			}
		</script>
		<style>
			input{
				border:1px solid white;
			}
			
			input:hover, input:focus{
				border:1px solid black;
			}
		</style>
	</head>
	<body>
	
	<h1> My Card Stacks </h1>
	
		<ul>
			<%
				User user = Authentication.authenticate();
				
				if (user != null){
				
				String user_id = user.getUserId();
				List<Stackcard> stackcards = Stackcard.getStacks(user_id);
				
				if (stackcards.size() == 0){
					out.println("You have no stacks! Create one below.");
				}
				
				for (Stackcard s : stackcards)
				{
			%>
				<div id="id_sc_div_<%= KeyFactory.keyToString(s.getKey()) %>">
					<input 	type="text" value="<%= s.getName() %>" 
							onKeyUp="document.getElementById('id_sc_savebutton_<%= KeyFactory.keyToString(s.getKey()) %>').style.display = 'inline';" 
							id="id_sc_input_<%= KeyFactory.keyToString(s.getKey()) %>" />
					<a 	href="javascript:void(0);" style="display:none;" 
						onClick="rename('<%= KeyFactory.keyToString(s.getKey()) %>');this.style.display='none';"
						id="id_sc_savebutton_<%= KeyFactory.keyToString(s.getKey()) %>">Save</a>
					<a href="/viewstack?key=<%= KeyFactory.keyToString(s.getKey()) %>">View</a>
					<a href="/deletestack?key=<%= KeyFactory.keyToString(s.getKey()) %>">Delete</a>
				</div>
			<%
				}
				} else {
					response.sendRedirect("/_ah/login?continue=%2Fmystacks");
				}
			%>
			
				<div id="id_sc_div_newstack">
					<input 	type="text" placeholder="new stack..." 
							onKeyUp="if (this.value.length > 0) document.getElementById('id_sc_savebutton_newstack').style.display = 'inline';" 
							id="id_sc_input_newstack" />
					<a 	href="javascript:void(0);" style="display:none;" 
						onClick="this.href='/createstack?name='+encodeURIComponent(document.getElementById('id_sc_input_newstack').value);"
						id="id_sc_savebutton_newstack">Save</a>
				</div>
		</ul>
	</body>
</html>