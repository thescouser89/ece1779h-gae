//
// For train.jsp
// jQuery to handle the card flip and keyboard actions.
//

$(document).ready(function() {
    $('.front').click(function(){
        $(document).find('.card').addClass('flipped')
    });
    $('.back').click(function(){
        $(document).find('.card').removeClass('flipped')
    });

    $("body").keydown(function(e) {
        if(e.keyCode == 16) { // shift
            wrong();
        }
        if(e.keyCode == 32) { // spacebar
            wrong();
        }
        if(e.keyCode == 37) { // left
            previous();
        }
        else if(e.keyCode == 39) { // right
            right(); // As in "the answer was right".
        }
        else if(e.keyCode == 38) { // up
            $('.flip').find('.card').removeClass('flipped')
        }
        else if(e.keyCode == 40) { // down
            $('.flip').find('.card').addClass('flipped')
        }
    });
});
