function rebindButtonEvent() {
 $(".btnSave").bind("click", Save);
 $(".btnDelete").bind("click", Delete);
 $(".btnEdit").bind("click", Edit);
 $("input").onEnter(Save);
}

function Add(){
	$("#tblData tbody").append(
		"<tr>"+
		"<td><span class='pie'>0/0</span></td>"+
		"<td><input type='text' class='question' /></td>"+
		"<td><input type='text' class='answer' /></td>"+
		"<td><button class=\"btnSave btn btn-default btn-xs\">save</button><button class=\"btnDelete btn btn-default btn-xs\">delete</button></td>"+
		"</tr>");
    rebindButtonEvent();
    $("span.pie").peity("pie");
};

function Save(){
	var par = $(this).parent().parent(); //tr

	var question = par.children("td:nth-child(2)");
	var answer = par.children("td:nth-child(3)");
	var tdButtons = par.children("td:nth-child(4)");

    var questionInputText = question.children("input[type=text]");
    var answerInputText = answer.children("input[type=text]");
	if (questionInputText.val().trim() == "") {
		sweetAlert("Oops...", "Question is empty!", "error");
		questionInputText.focus();
		return;
	}
	if (answerInputText.val().trim() == "") {
		sweetAlert("Oops...", "Answer is empty!", "error");
		answerInputText.focus();
		return;
	}
	question.html(questionInputText.val());
	answer.html(answerInputText.val());
	tdButtons.html("<button class=\"btnDelete btn btn-default btn-xs\">delete</button><button class=\"btnEdit btn btn-default btn-xs\">edit</button>");

	var keyFlashVal = null;
	if (par.data("key") != null) {
	    keyFlashVal = par.data("key");
	}
	
	var keyStack = $('#tblData').data("keystack");
	var data_to_send = {question: questionInputText.val(),
	                    answer: answerInputText.val(),
	                    keyStack: keyStack,
	                    keyFlash: keyFlashVal};
	$.post( "/flashcardSave", data_to_send).done(function(data) {
            par.data("key", data["keyFlash"]);
    }).fail(function() {
        sweetAlert("Oops...", "Something went wrong!", "error");
    });
        
    rebindButtonEvent();
    $("span.pie").peity("pie");
};

function Edit(){
	var par = $(this).parent().parent(); //tr

	var question = par.children("td:nth-child(2)");
	var answer = par.children("td:nth-child(3)");
	var tdButtons = par.children("td:nth-child(4)");


	question.html("<input type='text' id='txtName' value='"+question.html()+"'/>");
	answer.html("<input type='text' id='txtPhone' value='"+answer.html()+"'/>");

	tdButtons.html("<button class=\"btnSave btn btn-default btn-xs\">save</button>");

    rebindButtonEvent();
};

function Delete(){
	var par = $(this).parent().parent(); //tr
	par.remove();
	
	var keyFlashVal = null;
	if (par.data("key") != null) {
	    keyFlashVal = par.data("key");
	}
	
	if (keyFlashVal == null) {
	    return;
	}
	$.post( "/flashcardDelete", { keyFlash: keyFlashVal})
        .done(function( data ) {
            sweetAlert("Flashcard deleted!");
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