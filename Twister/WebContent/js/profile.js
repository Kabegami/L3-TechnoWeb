function makeProfile(){
    var s = '<div class="back-button">&leftarrow; Go back</div>\
            <div class="main-content">\
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
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function updateInfoUser(){
	console.log("update " + username);
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