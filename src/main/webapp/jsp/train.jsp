<%@ page import="ca.utoronto.ece1779h.model.*" %>
<%@ page import="ca.utoronto.ece1779h.authentication.Authentication" %>
<%@ page import="com.google.appengine.api.users.User" %>

<%
        User user = Authentication.authenticate();
        String loginBar = Authentication.getLoginBar();

        // Null user shouldn't be here. Just break for now.
        if (user == null){
            response.sendRedirect("/");
        } 

        // List all stacks for user.
        String questions = "\"Who is my dog?\"," + 
	       "\"Who is the PM?\"," + 
	       "\"Who is my girlfriend?\"," + 
	       "\"Is this an unnecessarlily long question?\"," + 
	       "\"Who am I?\"";

	    String answers = "\"Rex\"," + 
	       "\"Popo\"," + 
	       "\"<3\"," +
	       "\"YES!\"," +
	       "\"Who are YOU?\"";

	    String ids = "\"111111\"," + 
	       "\"11111A\"," + 
	       "\"11111B\"," +
	       "\"11111C\"," +
	       "\"11111D\"";

	    /* UNCOMMENT ONCE A WAY TO CREATE STACKS WAS IMPLEMENTED */
	    //List<Stackcard> stacks = Stackcard.getStacks(user.getUserId());
	    //stack = stacks.get(0);
        //
	    //List<Flashcard> cards = stack.getFlashcards();
        //
        //String questions = "";
	    //String answers = "";
	    //for (i=0; i<cards.size(); i++){
	    //	questions += "\"" + cards.get(i).getQuestion();
	    //	answers   += "\"" + cards.get(i).getAnswer();
	    //	ids       += "\"" + cards.get(i).getKey();
	    //
	    //	if (i == cards.size()-1){
	    //		questions += "\"";
	    //		answers   += "\"";
	    //      ids       += "\"";
	    //	} else {
	    //		questions += "\",";
	    //		answers   += "\",";
	    //		ids       += "\",";
	    //	}
	    //	
		//}
%>

<!DOCTYPE html>
<html>
<head>

<%= loginBar %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="js/train.js"></script>

<link rel="stylesheet" href="stylesheets/train.css">

</head>

<body>

<div class="flip"> 
    <div class="card"> 
        <div class="face front"> 
            <h1 id="question">Start</h1>
        </div> 
        <div class="face back"> 
            <h1 id="answer">Ready?</h1>
        </div> 
    </div> 
</div> 

<script>
	// Passed by jsp.
	var cards = {q:  [<%= questions %>],
				 a:  [<%= answers %>], 
				 id: [<%= ids %>]};
	var qArray = [<%= questions %>];
	var aArray = [<%= answers %>];
	
	var index = -1;
	
	function next() {
		index++;

		// If next has been pressed, we know there's a previous. Set button to visible.
		document.getElementById('prevButton').style.visibility = 'visible ';

	    // Display quizz end card.            
	    if (index >= cards.q.length) {
	        document.getElementById('question').innerHTML = '<div>Quiz End, Thank you</div>'
	        document.getElementById('nextButton').style.visibility = 'hidden ';
	        document.getElementById('wrongButton').style.visibility = 'hidden ';
	        index = cards.q.length;
	        return false;
	    }
	
	    document.getElementById('question').innerHTML = cards.q[index];
	    document.getElementById('answer').innerHTML   = cards.a[index];
	}

	function previous() {
		index--;

		// If previous has been pressed, we know there's a next. Set button to visible.
		document.getElementById('nextButton').style.visibility = 'visible ';

	    // If this is the first question stop and hide "previous" button.            
	    if (index < 0) {
	   		document.getElementById('prevButton').style.visibility = 'hidden ';
	        index = 0;
	    }
	
	    document.getElementById('question').innerHTML = cards.q[index];
	    document.getElementById('answer').innerHTML   = cards.a[index];
	}

	// Wrong answer. Append at the end of array.
	function wrong() {
		cards.q.push(cards.q[index]);
		cards.a.push(cards.a[index]);
		cards.id.push(cards.id[index]);

		// ==========================================
    	// Do ajax call
    	// ==========================================
		//$.post( "/flashcard/delete", { question: question.val()})
        //	.done(function( data ) {
        //    	alert( "Data deleted: " + data );
        //	}).fail(function() {
        //    	sweetAlert("Oops...", "Something went wrong!", "error");
        //	});
	}

</script>

<input type="button" value="Wrong" id="wrongButton" onclick="wrong()"><br>
<input type="button" value="Prev" id="prevButton" onclick="previous()">
<input type="button" value="Next" id="nextButton" onclick="next()">

</body>
</html>