<%@ page import="java.util.List" %>
<%@ page import="ca.utoronto.ece1779h.model.*" %>
<%@ page import="ca.utoronto.ece1779h.authentication.Authentication" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%
        User user = Authentication.authenticate();
        String loginBar = Authentication.getLoginBar();

        // Null user shouldn't be here. Just break for now.
        if (user == null){
            response.sendRedirect("/");
        } 

        //FOLLOWING COMMENTED BLOCK IS FOR DEBUG.
        //String questions = "\"Who is my dog?\"," + 
	    //   "\"Who is the PM?\"," + 
	    //   "\"Who is my girlfriend?\"," + 
	    //   "\"Is this an unnecessarlily long question?\"," + 
	    //   "\"Who am I?\"";

	    //String answers = "\"Rex\"," + 
	    //   "\"Popo\"," + 
	    //   "\"<3\"," +
	    //   "\"YES!\"," +
	    //   "\"Who are YOU?\"";

	    //String ids = "\"a111111\"," + 
	    //   "\"a11111A\"," + 
	    //   "\"a11111B\"," +
	    //   "\"a11111C\"," +
	    //   "\"a11111D\"";

	    //String stats_by_id = "a111111: {right:0, wrong:0}," + 
	    //   "a11111A: {right:0, wrong:0}," + 
	    //   "a11111B: {right:0, wrong:0}," +
	    //   "a11111C: {right:0, wrong:0}," +
	    //   "a11111D: {right:0, wrong:0}";

        // Get Key from URL and get cards for corresponding stack
	    String key = request.getParameter("key");
	    Stackcard stack = StackcardHelper.getStackcard(KeyFactory.stringToKey(key));
        
	    List<Flashcard> cards = stack.getFlashcards();
        
        // Create js object strings to pass stack info
        String questions = "";
	    String answers = "";
	    String stats_by_id = "";
	    String ids = "";
	    for (int i=0; i<cards.size(); i++){
	    	questions += "\"" + cards.get(i).getQuestion();
	    	answers   += "\"" + cards.get(i).getAnswer();
	    	ids       += "\"" + KeyFactory.keyToString(cards.get(i).getKey());
	    	stats_by_id += KeyFactory.keyToString(cards.get(i).getKey()) + ":{right:0,wrong:0}";
	    
	    	if (i == cards.size()-1){
	    		questions += "\"";
	    		answers   += "\"";
	        	ids       += "\"";
	    	} else {
	    		questions += "\",";
	    		answers   += "\",";
	    		ids       += "\",";
	    		stats_by_id += ",";
	    	}	
		}
%>

<!DOCTYPE html>
<html>
<head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="js/train.js"></script>

<link rel="stylesheet" href="stylesheets/train.css">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</head>

<body onload="onload();">

<div class="container">
<%= loginBar %>

<div class="jumbotron">
	<div class="flip"> 
	    <div class="card"> 
	        <div class="face front"> 
	            <h2 id="question">Start</h2>
	        </div> 
	        <div class="face back"> 
	            <h2 id="answer">Ready?</h2>
	        </div> 
	    </div> 
	</div> 
	<p class="text-center" id="progress"></p>
</div> 

<script>
	// Passed by jsp.
	var cards = {q:  [<%= questions %>],
				 a:  [<%= answers %>], 
				 id: [<%= ids %>]};
	var stats = {<%= stats_by_id %>};
	
	var index = -1;
	
	function onload(){
		// Set initial button visibility
		document.getElementById('prevButton').style.visibility = 'hidden ';
	    document.getElementById('wrongButton').style.visibility = 'hidden ';

	    // Initialize progress string
		progress_update();
	}

	function next() {
		// Mark current card as "right"
		if (index >= 0) stats[cards.id[index]].right++;

		// If next has been pressed, we know there's a previous. Set button to visible.
		document.getElementById('prevButton').style.visibility = 'visible ';

	    // Display quizz end card and stop.            
	    if (index+1 >= cards.q.length) {
	        document.getElementById('question').innerHTML = '<div>Quiz End, Thank you</div>'
	        document.getElementById('answer').innerHTML = '<div></div>'
	        document.getElementById('nextButton').style.visibility = 'hidden ';
	        document.getElementById('wrongButton').style.visibility = 'hidden ';
	        return false;
	    }

		index++;

		progress_update();
	
	    document.getElementById('question').innerHTML = cards.q[index];
	    document.getElementById('answer').innerHTML   = cards.a[index];
	}

	function previous() {
		// If previous has been pressed, we know there's a next. Set button to visible.
		document.getElementById('nextButton').style.visibility = 'visible ';
		document.getElementById('wrongButton').style.visibility = 'visible ';

	    // If this is the first question stop and hide "previous" button.            
	    if (index-1 < 0) {
	   		document.getElementById('prevButton').style.visibility = 'hidden ';
	   		return false;
	    }

		index--;

		progress_update();

	    document.getElementById('question').innerHTML = cards.q[index];
	    document.getElementById('answer').innerHTML   = cards.a[index];
	}

	// Wrong answer. Append at the end of array.
	function wrong() {
		cards.q.push(cards.q[index]);
		cards.a.push(cards.a[index]);
		cards.id.push(cards.id[index]);

		stats[cards.id[index]].wrong++;
	}

	// Genetates a card #x out of #y
	function progress_update(){
	    document.getElementById('progress').innerHTML =
	    	(index+1) + " out of " + cards.q.length;
	}

	function post_stats() {
		// ==========================================
    	// Do ajax call
    	// ==========================================
		$.post( "/flashcard/stats", stats)
        	.done(function( data ) {
            	// All is good
        	}).fail(function() {
            	sweetAlert("Oops...", "Something went wrong!", "error");
        });
	}

</script>

<div id="button_container" class="text-center">
	<input type="button" class="btn btn-info" value="Prev" id="prevButton" onclick="previous()">
	<input type="button" class="btn btn-info" value="Wrong" id="wrongButton" onclick="wrong()">
	<input type="button" class="btn btn-info" value="Next" id="nextButton" onclick="next()">
</div>

</div>

</body>
</html>