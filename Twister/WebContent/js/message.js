function completeMessages(){
    if (! env.noConnection){
    	$.ajax({
    		type: "POST",
    		url: "message/list",
    		data: "key=" + env.key,
    		datatype: "text",
    		success: function(rep){
    			completeMessagesResponse(JSON.stringify(rep));
    		},
    		error: function(xhr, status, err){
    			func_error(status);
    		}
    	})
    }
    else {
		var tab = getFromLocalDb(-1, -1, -1, 10);
		completeMessagesResponse(JSON.stringify(tab));
		for (var i = 0; i < env.messages.length; i++){
			var msg = env.messages[env.messages.length - 1 - i];
			$(".message-list").append(msg.getHtml());
		}
    }
}

function completeMessagesResponse(rep){
    tab = JSON.parse(rep, revival);
    console.log(tab);
    for (var i = 0; i < tab.messages.length; i++){
		var m = tab.messages[i];
		env.messages.push(m);
    }
}

function getFromLocalDb(from, minId, maxId, nbMax){
     var tab = [];

    // page d'accueil
    if (from < 0){
    	var i = 0;
		while (i < nbMax){
			var msg = localdb[i];
			if (msg != undefined){
				tab.push(msg);
			}
			else {
				return tab;
			}
			i++;
		}
    }
    // utilisateur donné
    else {
		var followed = follows[from];
		for (let user of followed){
			
		}
    }
    return tab;
}

function compareMessages(a, b){
	return a.id - b.id;
}


function developpeMessage(id){
    var msg = env.messages[id];
    var el = $("#message_" + id + " .comments");
    for (var i = 0; i < msg.comments.length; i++){
        var com = msg.comments[i];
        $(com.getHtml()).appendTo(el).hide().slideToggle('fast');
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