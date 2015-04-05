//
// For train.jsp
// jQuery to handle the card flip and keyboard actions.
//

$(document).ready(function() {
    $('.flip').click(function(){
        $(this).find('.card').addClass('flipped').mouseleave(function(){
            $(this).removeClass('flipped');
        });
        return false;
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
