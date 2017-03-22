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
    var s = "<div id=\"error-message\">" + msg + "</div>";
    var old_msg = $("#error-message");
    if (old_msg.length == 0){
		$("#login-content").prepend(s);
    }
    else {
		old_msg.replaceWith(s);
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
				responseConnection(rep);
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
		env.follows = new Set();
		console.log(env.key);
		for (var i = 0; i < rep.follows.length; i++){
			env.follows.add(rep.follows[i]);
		}
		if (env.noConnection) {
			
		}
		pageUser(rep.id, rep.login);
	}
	else {
		func_error(rep.error);
	}
}

function makeConnectionPanel(){
    $("#login-content").slideToggle();
}
