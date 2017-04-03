/* --------------------------------------------------------
   CONSTRUCTEURS
   ----------------------------------------------------------*/

function Message (id, author, text, date, comments){
    this.id = id;
    this.author = author;
    this.text = text;
    this.date = date;
    if (comments == undefined){
		comments = [];
    }
    this.comments = comments;
}

function Comment (id, author, text, date){
    this.id = id;
    this.author = author;
    this.text = text;
    this.date = date;
}

Message.prototype.getHtml = function() {
	return ("<div id=\"message_" + this.id + "\" class=\"message\"> \n "
	    + "\t <div class=\"info-message\"> \n "
	    + "\t \t <span class=\"link author\">" + this.author.username + "</span> \n"
	    + "\t \t - <span class=\"date\">" + this.date + "</span> \n"
	    + "\t </div> \n"
	    + "\t <div class=\"text-message\"> \n \t \t" + this.text + "\n"
	    + "\t </div> \n "
	    + "\t <div class=\"bottom-message\"> <span class=\"expand-comments\">&plus;</span></div> \n"
	    + "\t <div class=\"comments\"> \n"
	    + "\t </div> \n"
	    + "\t <div class=\"new-comment\"> \n"
	    + "\t </div> \n"
	 
	+ "</div>"
	);
}

Comment.prototype.getHtml = function() {
    return (
	"<div id=\"comment_" + this.id + "\" class=\"comment\"> \n "
	    + "\t <div class=\"info-message\"> \n "
	    + "\t \t <span class=\"link author\">" + this.author.username + "</span> \n"
	    + "\t \t - <span class=\"date\">" + this.date + "</span> \n"
	    + "\t </div> \n"
	    + "\t <div class=\"text-message\"> \n \t \t" + this.text + "\n"
	    + "\t </div> \n "
	+ "</div>"
    );
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

function completeMessages(){
    if (! env.noConnection){
    	$.ajax({
    		type: "POST",
    		url: "message/list",
    		data: "key=" + env.key + "&from=" + env.fromId 
				+ "&id_max=" + env.maxId + "&id_min=" + env.minId + "&nb=" + 10,
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

    }
}

function completeMessagesResponse(rep){
    var tab = JSON.parse(rep, revival);
    for (var i = 0; i < tab.messages.length; i++){
		var m = tab.messages[i];
		env.messages[m.id] = m;
    }
    for (var i = 0; i < env.messages.length; i++){
		var msg = env.messages[env.messages.length - 1 - i];
		if (msg != undefined){
			//$(".message-list").append(msg.getHtml()).fadeIn('normal');
			$(msg.getHtml()).appendTo(".message-list").hide().slideToggle();

		}
    }
    env.minId = tab.messages[0].id;
    env.maxId = env.messages.length;
}

function refreshMessages(){
	if (env.query != undefined){
		return ;
	}
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "message/list",
			data: "key=" + env.key + "&from=" + env.fromId 
				+ "&id_max=-1&id_min=" + env.minId + "&nb=" + 10,
			datatype: "text",
			success: function(rep){
    			refreshMessagesResponse(JSON.stringify(rep));
    		},
    		error: function(xhr, status, err){
    			func_error(status);
    		}
		});
	}
}

function refreshMessagesResponse(rep){
	tab = JSON.parse(rep, revival);
	for (var i = tab.messages.length-1; i >= 0; i--){
		var msg = tab.messages[i];
		//$(".message-list").prepend(msg.getHtml());
        if (env.messages[msg.id] == undefined){
            $(msg.getHtml()).prependTo(".message-list").hide().slideToggle();
            env.messages[msg.id] = msg;
            if (msg.id > env.maxId){
                env.maxId = msg.id;
            }
            if (env.minId < 0 || msg.id < env.minId){
                env.minId = msg.id;
            }
        }
	}
}

function newMessage(text){
    if (! env.noConnection){
    	$.ajax({
    		type: "POST",
    		url: "message/new",
    		data: "key=" + env.key + "&text=" + text,
    		datatype: "text",
    		success: function(rep){
    			refreshMessages();
    		},
    		error: function(xhr, status, err){
    			func_error(status);
    		}
    	});
    }
}



/* --------------------------------------------------------
   COMMENTAIRES
   ----------------------------------------------------------*/

function developpeMessage(id){
    var msg = env.messages[id];
    //console.log(id);
    var el = $("#message_" + id + " .comments");
    for (var i = 0; i < msg.comments.length; i++){
        var com = msg.comments[i];
        $(com.getHtml()).appendTo(el).hide().slideToggle('fast');
    }
    
    el = $("#message_" + id + " .new-comment");
    var ncomment = " \
       <form id=\"new-comment-form\"> \
	    <textarea id=\"new-comment\" type=\"text\" placerholder=\"Type here\"></textarea> </br> \
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

function newComment(id_message, text){
    if (! env.noConnection){
    	$.ajax({
    		type: "POST",
    		url: "comment/new",
    		data: "key=" + env.key + "&id_message=" + id_message + "&text=" + text,
    		datatype: "text",
    		success: function(rep){

    		},
    		error: function(xhr, status, err){
    			func_error(status);
    		}
    	})
    }
    else {
		
    }
}

function newCommentResponse(id_message){
	
}