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
	repo = rep;
    $("#username").text(capitalizeFirstLetter(rep.login));
    d = new Date(rep.registration)
    $("#register-date").text(d.getFullYear());
    $("#subscribers-count").text(rep.subscribers.length);
    $("#follow-count").text(rep.follows.length);
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}