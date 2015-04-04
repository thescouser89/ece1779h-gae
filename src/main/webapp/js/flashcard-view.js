function rebindButtonEvent() {
 $(".btnSave").bind("click", Save);
 $(".btnDelete").bind("click", Delete);
 $(".btnEdit").bind("click", Edit);
 $("input").onEnter(Save);
}

function Add(){
	$("#tblData tbody").append(
		"<tr>"+
		"<td>N/A</td>"+
		"<td><input type='text' class='question' /></td>"+
		"<td><input type='text' class='answer' /></td>"+
		"<td><button class='btnSave'>save</button><button class='btnDelete'>delete</buton></td>"+
		"</tr>");
    rebindButtonEvent();

};

function Save(){
	var par = $(this).parent().parent(); //tr

	var question = par.children("td:nth-child(2)");
	var answer = par.children("td:nth-child(3)");
	var tdButtons = par.children("td:nth-child(4)");

    var questionInputText = question.children("input[type=text]");
    var answerInputText = answer.children("input[type=text]");
	if (questionInputText.val().trim() == "") {
		alert("question is empty!");
		questionInputText.focus();
		return;
	}
	if (answerInputText.val().trim() == "") {
		alert("answer is empty!");
		answerInputText.focus();
		return;
	}
	question.html(questionInputText.val());
	answer.html(answerInputText.val());
	tdButtons.html("<button class='btnDelete'>delete</button><button class='btnEdit'>edit</button>");

	alert("I am being saved!");
	alert(questionInputText.data("key"));
	$.post( "/flashcardSave", { question: questionInputText.val(), answer: answerInputText.val(), keyStack: questionInputText.data("key")})
        .done(function( data ) {
            alert( "Data Loaded: " + data );
        }).fail(function() {
            sweetAlert("Oops...", "Something went wrong!", "error");
        });
        
    rebindButtonEvent();
    // =================================================
	// DO AJAX CALL
    // =================================================
};

function Edit(){
	var par = $(this).parent().parent(); //tr

	var question = par.children("td:nth-child(2)");
	var answer = par.children("td:nth-child(3)");
	var tdButtons = par.children("td:nth-child(4)");


	question.html("<input type='text' id='txtName' value='"+question.html()+"'/>");
	answer.html("<input type='text' id='txtPhone' value='"+answer.html()+"'/>");

	tdButtons.html("<button class='btnSave'>save</button>");

    rebindButtonEvent();
};

function Delete(){
	var par = $(this).parent().parent(); //tr
	par.remove();
	var question = par.children("td:nth-child(2)");
	alert(question);
    // ==========================================
    // Do ajax call
    // ==========================================
	$.post( "/flashcard/delete", { question: question.val()})
        .done(function( data ) {
            alert( "Data deleted: " + data );
        }).fail(function() {
            sweetAlert("Oops...", "Something went wrong!", "error");
        });
};


(function($) {
    $.fn.onEnter = function(func) {
        this.bind('keypress', function(e) {
            if (e.keyCode == 13) func.apply(this, [e]);
        });
        return this;
    };
})(jQuery);

$(function(){
    // get pretty pie charts
    $("span.pie").peity("pie");
	//Add, Save, Edit and Delete functions code
	// $(".btnEdit").bind("click", Edit);
	// $(".btnDelete").bind("click", Delete);
	$("#btnAdd").bind("click", Add);
    rebindButtonEvent();
});