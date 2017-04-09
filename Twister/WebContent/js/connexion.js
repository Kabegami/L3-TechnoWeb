function connection(form){
    var login = form.login.value;
    var pass = form.password.value;
    var ok = check_form(login, pass);
    if (ok) {
		connect(login, pass);
    }
}

function check_form(login, pass){
    if (login.length == 0){
		func_error("Login obligatoire");
		return false;
    }
    if (login.length > 20){
		func_error("Taille maximale : 20 caractères");
		return false;
    }
    if (pass.length == 0){
		func_error("Mot de passe obligatoire");
		return false;
    }
    return true;
}

function func_error(msg){
    var s = "<div class=\"error-message\">" + msg + "</div>";
    var old_msg = $("#login-content .error-message");
    if (old_msg.length == 0){
		//$("#login-content").prepend(s);
		$(s).appendTo("#login-content").hide().fadeIn(500);
    }
    else {
		$(old_msg).fadeOut(500, function(){
        	$(this).replaceWith(s);
        	$('#login-content .error-message').hide().fadeIn(500);
        });
    }
}

function connect(login, pass){
	console.log("connecte " + login);
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "auth/login",
			data: "login=" + login + "&pwd=" + pass,
			datatype: "json",
			success: function(rep){
				if ('error_code' in rep){
					if (rep.error_code == 2){
						func_error("Mot de passe incorrect");
					}
				}
				else {
					responseConnection(rep);
				}
			},
			error: function(xhr, status, err){
				func_error(xhr.responseText);
			}
		});
	}
	else {
		var id_user = 1;
		var key = "thisisakey";
		responseConnection({"key": key, "id": id_user, "login": "toto", "follows": []});
	}
}

function responseConnection(rep){
	if (rep.error == undefined){
		env.key = rep.key;
		env.id = rep.id;
		env.login = rep.login;
		env.follows = [];
		console.log(env.key);
		for (var i = 0; i < rep.follows.length; i++){
			env.follows.push(rep.follows[i].id);
		}
		if (env.noConnection) {
			
		}
		pageUser(rep.id, rep.login);
	}
	else {
		func_error(rep.error);
	}
}

function logout(key){
	console.log("déconnecte " + key);
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "auth/logout",
			data: "key=" + key,
			datatype: "json",
			success: function(rep){
				responseLogout(rep);
			},
			error: function(xhr, status, err){
				func_error(xhr.responseText);
			}
		});
	}
	else {
		makeHomePage();
	}
}

function responseLogout(rep){
	if (rep.error == undefined){
		makeHomePage();
	}
	else {
		func_error(rep.error);
	}
}

function makeConnectionPanel(){
    $("#login-content").slideToggle();
}


/* MOT DE PASSE OUBLIE */

function makeRecoveryPanel(){
	closeDropdown($("#login-content .close-toggle"));
	$(".recovery-modal").fadeIn(500);
	$("#recovery-password").fadeIn(500);
}

function func_error_recovery(msg){
    var s = "<div class=\"notif-message\">" + msg + "</div>";
    var old_msg = $("#recovery-password .notif-message");
    if (old_msg.length == 0){
		//$("#login-content").prepend(s);
		$(s).appendTo("#recovery-password").hide().fadeIn('fast');
    }
    else {
		$(old_msg).fadeOut(500, function(){
        	$(this).replaceWith(s);
        	$('#recovery-password .notif-message').hide().fadeIn('fast');
        });
    }
}


function sendPassword(form){
	var mail = form.mail.value;
	if (mail.length == 0){
		func_error_recovery("Email address required");
	}
	else {
		sendPasswordRecovery(mail);
	}
}

function sendPasswordRecovery(mail){
	console.log("recovery " + mail);
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "user/pass",
			data: "mail=" + mail,
			datatype: "json",
			success: function(rep){
				responsePasswordRecovery(rep);
			},
			error: function(xhr, status, err){
				func_error(xhr.responseText);
			}
		});
	}
	else {
		
	}
}

function responsePasswordRecovery(rep){
	if (rep.error_code == 1){
		func_error_recovery("Email address not found in the database");
	}
	else {
		var s = "<div class=\"notif-message\">Password successfully sent!</div>";
		var old_msg = $("#recovery-password .notif-message");
		if (old_msg.length == 0){
			//$("#login-content").prepend(s);
			$(s).appendTo("#recovery-password").hide().fadeIn('fast');
		}
		else {
			$(old_msg).fadeOut(500, function(){
				$(this).replaceWith(s);
				$('#recovery-password .notif-message').hide().fadeIn('fast');
			});
		}
	}
}