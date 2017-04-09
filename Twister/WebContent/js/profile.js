function makeProfile(){
    var s = '<div class="back-button">&leftarrow; Go back</div>\
            <div class="main-content-profile">\
                <div class="left-profile card">\
                    <div id="userpic">\
                    </div>\
                    <div class="user-details">\
                        <div id="username">\
                        </div>\
                        <div class="details">\
                            <div>\
                                Registered in \
                                <span id="register-date"></span>\
                            </div>\
                        </div>\
                    </div>\
                </div>\
                <div class="right-profile card">\
                    <div class="bar">\
                        <span class="follow-button">\
                            <span class="plus"> \
                                &plus; \
                            </span>\
                            Follow\
                        </span>\
                        <span class="star">&starf;</span>\
                        <span id="subscribers-count">101</span> subscribers\
                        <span class="heart">&hearts;</span>\
                        <span id="follow-count">101</span> followed\
                    </div>\
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

function getInfoUser(username){
    console.log("information " + username);
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "user/info",
			data: "key=" + env.key + "&user=" + username,
			datatype: "json",
			success: function(rep){
                makeProfile();
                responseInfoUser(rep);
			},
			error: function(xhr, status, err){
				func_error(xhr.responseText);
			}
		});
	}
}

function responseInfoUser(rep){
	profile = new Object();
	profile.id = rep.user.id;
	profile.login = rep.user.login;
	profile.registration = rep.registration;
	profile.subscribers = rep.subscribers;
	profile.follows = rep.follows;
	
    $("#username").text(capitalizeFirstLetter(profile.login));
    d = new Date(profile.registration);
    $("#register-date").text(d.getFullYear());
    $("#subscribers-count").text(profile.subscribers.length);
    $("#follow-count").text(profile.follows.length);
    checkFollowStatus(profile.id);
    completeMessagesProfile();
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function updateInfoUser(){
	console.log("update " + profile.login);
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "user/info",
			data: "key=" + env.key + "&user=" + profile.login,
			datatype: "json",
			success: function(rep){
                responseUpdateInfoUser(rep);
			},
			error: function(xhr, status, err){
				func_error(xhr.responseText);
			}
		});
	}
}

function responseUpdateInfoUser(rep){
	profile.subscribers = rep.subscribers;
	profile.follows = rep.follows;
    $("#subscribers-count").text(profile.subscribers.length).hide().fadeIn(500);
    $("#follow-count").text(profile.follows.length).hide().fadeIn(500);
    checkFollowStatus(profile.id);
}

function checkFollowStatus(){
	// l'utilisateur regarde son propre profil
	if (profile.id == env.id){
		$(".follow-button").replaceWith('');
		$(".right-profile .bar").css('line-height', '40px')
	}
	// si on suit cet utilisateur
	else if (env.follows.indexOf(profile.id) > -1){
		var s = '<span class="unfollow-button">\
					<span class="minus"> \
                 		&minus; \
                 	</span>\
                	Unfollow\
               	 </span>';
         $(".follow-button").fadeOut(500, function(){
        	$(this).replaceWith(s);
        	$(".unfollow-button").hide().fadeIn(500);
        });
	}
	// si on suit pas l'utilisateur
	else {
		var s = '<span class="follow-button">\
                 	<span class="plus"> \
                    	&plus; \
                    </span>\
                    Follow\
                 </span>';
        $(".unfollow-button").fadeOut(500, function(){
        	$(this).replaceWith(s);
        	$(".follow-button").hide().fadeIn(500);
        });
	}
}

function followUser(){
	console.log("suit " + profile.login);
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "follow/add",
			data: "key=" + env.key + "&id_follow=" + profile.id,
			datatype: "json",
			success: function(rep){
				responseFollowUser(rep);
			},
			error: function(xhr, status, err){
				func_error(xhr.responseText);
			}
		});
	}
}

function responseFollowUser(rep){
	env.follows.push(profile.id);
	updateInfoUser(profile.login);
}

function unfollowUser(){
	console.log("ne suit plus " + profile.login);
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "follow/remove",
			data: "key=" + env.key + "&id_follow=" + profile.id,
			datatype: "json",
			success: function(rep){
				responseUnfollowUser(rep);
			},
			error: function(xhr, status, err){
				func_error(xhr.responseText);
			}
		});
	}
}

function responseUnfollowUser(rep){
	env.follows.splice(env.follows.indexOf(profile.id), 1);
	updateInfoUser(profile.login);
}

function completeMessagesProfile(){
    if (! env.noConnection){
    	$.ajax({
    		type: "POST",
    		url: "message/user",
    		data: "key=" + env.key + "&from=" + profile.id 
				+ "&id_max=" + env.minId + "&id_min=-1&nb=" + 10,
    		datatype: "text",
    		success: function(rep){
    			completeMessagesProfileResponse(JSON.stringify(rep));
    		},
    		error: function(xhr, status, err){
    			func_error(status);
    		}
    	})
    }
    else {

    }
}

function completeMessagesProfileResponse(rep){
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
				$("#message_" + i).removeClass('message');
				$("#message_" + i).addClass('profile-message');
			}
		}
		env.minId = tab.messages[tab.messages.length-1].id;
		env.maxId = Math.max.apply(null, env.messages.keys())
		// dernier message affiché pour déclencher le appear des 10 suivants
		$("#message_" + env.minId).appear();
	}
}
