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
            next();
        }
        if(e.keyCode == 32) { // spacebar
            wrong();
            next();
        }
        if(e.keyCode == 37) { // left
            previous();
        }
        else if(e.keyCode == 39) { // right
            next();
        }
        else if(e.keyCode == 38) { // up
            $('.flip').find('.card').removeClass('flipped')
            //$(this).hide();
        }
        else if(e.keyCode == 40) { // down
            $('.flip').find('.card').addClass('flipped')
            //$(this).hide();
        }
    });
});
