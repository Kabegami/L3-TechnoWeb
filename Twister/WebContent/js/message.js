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

/* --------------------------------------------------------
   JSON String -> JSON Object
   ----------------------------------------------------------*/

function revival(key, value){
    if (key == "erreur" && value == 0){
		return value;
    }
    if (value.comments != undefined){
		return new Message(value.id, value.author, value.text, value.date, value.comments);
    }
    else if (value.text != undefined){
		return new Comment(value.id, value.author, value.text, value.date);
    }
    else if (key == "date"){
		return new Date(value);
    }
    else {
		return value;
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

function completeMessages(){
    if (! env.noConnection){
    	$.ajax({
    		type: "POST",
    		url: "message/list",
    		data: "key=" + env.key + "&from=" + env.fromId 
				+ "&id_max=" + env.minId + "&id_min=-1&nb=" + 10,
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
	// tableau trié par ordre décroissant des id
    var tab = JSON.parse(rep, revival);
    
	if (tab.messages.length != 0){
		var idMax = tab.messages[0].id;
		var idMin = tab.messages[tab.messages.length-1].id;
		for (var i = 0; i < tab.messages.length; i++){
			var m = tab.messages[i];
			env.messages.set(m.id, m);
		}
		for (var i = idMax; i >= idMin; i--){
			var msg = env.messages.get(i);
			if (msg != undefined){
				//$(".message-list").append(msg.getHtml());
				$(msg.getHtml()).appendTo(".message-list").hide().slideToggle();
			}
		}
		env.minId = tab.messages[tab.messages.length-1].id;
		env.maxId = Math.max.apply(null, env.messages.keys())
		// dernier message affiché pour déclencher le appear des 10 suivants
		$("#message_" + env.minId).appear();
	}
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
	var tab = JSON.parse(rep, revival);
	for (var i = tab.messages.length-1; i >= 0; i--){
		var msg = tab.messages[i];
		//$(".message-list").prepend(msg.getHtml());
        if (env.messages.get(msg.id) == undefined){
            $(msg.getHtml()).prependTo(".message-list").hide().slideToggle();
            env.messages.set(msg.id, msg);
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
    var msg = env.messages.get(id);
    //console.log(id);
    var el = $("#message_" + id + " .comments");
    for (var i = 0; i < msg.comments.length; i++){
        var com = msg.comments[i];
        $(com.getHtml()).appendTo(el).hide().slideToggle('fast');
    }
    
    el = $("#message_" + id + " .new-comment");
    var ncomment = " \
       <form id=\"new-comment-form\"> \
	    <textarea id=\"new-comment\" class=\"submitEnter\" type=\"text\" placerholder=\"Type here\"></textarea> </br> \
	    <input type=\"submit\" value=\"Post\" /> \
	  </form> \
    ";
    $(ncomment).appendTo(el).hide().slideToggle();
    var s = "<span class=\"reduce-comments\">&dash;</span>"
    $("#message_" + id +" .expand-comments").replaceWith(s);

}

function reduceMessage(id){
    var msg = env.messages.get(id);
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
				newCommentResponse(JSON.stringify(rep), id_message);
    		},
    		error: function(xhr, status, err){
    			func_error(status);
    		}
    	})
    }
    else {
		
    }
}

function newCommentResponse(rep, id_message){
	console.log("ajout commentaire au msg " + id_message);
	var comment = JSON.parse(rep, revival);
	var msg = env.messages.get(id_message);
	msg.comments.push(comment);
	el = $("#message_" + id_message + " .comments");
	var com = msg.comments[msg.comments.length-1];
	$(com.getHtml()).appendTo(el).hide().fadeIn(500);
	refreshMessages();
    //var el = $("#message_" + id_message + " .comments");
};


/* --------------------------------------------------------
   SEARCH
   ----------------------------------------------------------*/

function makeSearchPage(){
	var s = '<div class="back-button">&leftarrow; Go back</div>\
             	<div class="main-content-search">\
               		<div class="message-list">\
                        \
                    </div>\
                </div>\
            </div>\
        </div>\
    ';
    $(".main-container").css("display", "none");
	$(".main-container").fadeIn(500);
    $(".main-container").html(s);
	resetMessages();
}

function completeMessagesSearch(query){
	console.log("searching " + query);
	if (! env.noConnection){
    	$.ajax({
    		type: "POST",
    		url: "message/list",
    		data: "key=" + env.key + "&query=" + query + "&from=" + env.fromId 
				+ "&id_max=" + env.minId + "&id_min=-1&nb=" + 10,
    		datatype: "text",
    		success: function(rep){
    			completeMessagesSearchResponse(JSON.stringify(rep));
    		},
    		error: function(xhr, status, err){
    			func_error(status);
    		}
    	})
    }
}

function completeMessagesSearchResponse(rep){
	// tableau trié par ordre des id
    var tab = JSON.parse(rep, revival);
	
	if (tab.messages.length != 0){
		for (var i = 0; i < tab.messages.length; i++){
			var msg = tab.messages[i];
			env.messages.set(msg.id, msg);
			//$(".message-list").append(msg.getHtml());
			$(msg.getHtml()).appendTo(".message-list").hide().slideToggle();
		}
	}
}

