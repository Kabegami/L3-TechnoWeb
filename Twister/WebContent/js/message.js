function developpeMessage(id){
    var msg = env.messages[id];
    var el = $("#message_" + id + " .comments");
    for (var i = 0; i < msg.comments.length; i++){
        var com = msg.comments[i];
        $(com.getHtml()).appendTo(el).hide().slideToggle();
    }
    
    el = $("#message_" + id + " .new-comment");
    var ncomment = " \
       <form> \
	    <textarea type=\"text\" placerholder=\"Type here\"></textarea> </br> \
	    <input type=\"submit\" value=\"Post\" /> \
	  </form> \
    ";
    $(ncomment).appendTo(el).hide().slideToggle();
    var s = "<span class=\"reduce-comments\">&dash;</span>"
    $("#message_" + id +" .expand-comments").replaceWith(s);

}

function reduceMessage(id){
    var msg = env.messages[id];
    var el = $("#message_" + id + " .comments");
    el.slideToggle("normal", function(){
        $(this).empty();
        $(this).css("display", "block");
        //$("#message_" + id + " .new-comment").empty();
    });
    el = $("#message_" + id + " .new-comment");
    el.slideToggle("normal", function(){
    	$(this).empty();
        $(this).css("display", "block");
    })
    var s = "<span class=\"expand-comments\">&plus;</span>"
    $("#message_" + id +" .reduce-comments").replaceWith(s);
}