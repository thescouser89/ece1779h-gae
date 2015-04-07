function rebindButtonEvent() {
 $(".btnSave").bind("click", Save);
 $(".btnDelete").bind("click", Delete);
 $(".btnEdit").bind("click", Edit);
 $("input").onEnter(Save);
}

function Add(){
	$("#tblData tbody").append(
		"<tr>"+
		"<td class='col-md-2'><span class='pie'>0/0</span></td>"+
		"<td class='col-md-4'><textarea rows='1' class='question form-control' placeholder='Question' /></td>"+
		"<td class='col-md-4'><textarea rows='1' class='answer form-control' placeholder='Answer'/></td>"+
		"<td class='col-md-2'><button class=\"btnSave btn btn-default\"><span class='glyphicon glyphicon-floppy-disk'></span></button><button class=\"btnDelete btn btn-default\"><span class='glyphicon glyphicon-remove'></span></button></td>"+
		"</tr>");
    rebindButtonEvent();
    $("span.pie").peity("pie");
};

function Save(){
	var par = $(this).parent().parent(); //tr

	var question = par.children("td:nth-child(2)");
	var answer = par.children("td:nth-child(3)");
	var tdButtons = par.children("td:nth-child(4)");

    var questionInputText = question.children("textarea");
    var answerInputText = answer.children("textarea");
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
	tdButtons.html("<button class=\"btnEdit btn btn-default\"><span class='glyphicon glyphicon-pencil'></span></button><button class=\"btnDelete btn btn-default\"><span class='glyphicon glyphicon-remove'></span></button>");

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
            var n = noty({text: 'Flashcard Updated!', timeout: 2000, layout: 'topRight', type: 'success'});
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


	question.html("<textarea class='form-control' rows='1'>"+question.html()+"</textarea>");
	answer.html("<textarea class='form-control' rows='1'>"+answer.html()+"</textarea>");

	tdButtons.html("<button class=\"btnSave btn btn-default\"><span class='glyphicon glyphicon-floppy-disk'></span></button>");

    rebindButtonEvent();
};

function Delete(){
	var par = $(this).parent().parent(); //tr
	
	var keyFlashVal = null;
	if (par.data("key") != null) {
	    keyFlashVal = par.data("key");
	}
	
	if (keyFlashVal == null) {
	    return;
	}
	
	par.remove();
	
	$.post( "/flashcardDelete", { keyFlash: keyFlashVal})
        .done(function( data ) {
            var n = noty({text: 'Flashcard deleted!', timeout: 2000, layout: 'topRight', type: 'information'});
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