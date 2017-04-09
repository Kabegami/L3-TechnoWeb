function register(form){
	var fname = form.fname.value;
    var lname = form.lname.value;
    var login = form.login.value;
    var pass = form.password.value;
    var passCheck = form.passwordCheck.value;
    var mail = form.mail.value;
    var mailCheck = form.mailCheck.value;
    var ok = check_form_register(login, pass, passCheck, mail, mailCheck);
    if (ok){
    	registerUser(login, pass, lname, fname, mail);
    }
    
}

function check_form_register(login, pass, passCheck, mail, mailCheck){
    if (login.length == 0){
		func_error_register("Login obligatoire");
		return false;
    }
    if (login.length > 20){
		func_error_register("Taille maximale : 20 caractères");
		return false;
    }
    if (pass.length == 0){
		func_error_register("Mot de passe obligatoire");
		return false;
    }
    if (pass !== passCheck){
    	func_error_register("Mot de passe non identique");
    	return false;
    }
    if (mail !== mailCheck){
    	func_error_register("Adresse mail non identique");
    	return false;
    }
    return true;
}

function func_error_register(msg){
    var s = "<div class=\"error-message\">" + msg + "</div>";
    var old_msg = $("#new-user-content .error-message");
    if (old_msg.length == 0){
		//$("#new-user-content").append(s);
		$(s).appendTo("#new-user-content").hide().fadeIn(500);
    }
    else {
		$(old_msg).fadeOut(500, function(){
        	$(this).replaceWith(s);
        	$("#new-user-content .error-message").hide().fadeIn('fast');
        });
    }
}

function registerUser(login, pass, lname, fname, mail){
	console.log("enregistre " + login);
	if (! env.noConnection){
		$.ajax({
			type: "POST",
			url: "user/create",
			data: "login=" + login + "&pwd=" + pass + "&lname=" + lname
					+ "&fname=" + fname + "&mail=" + mail,
			datatype: "json",
			success: function(rep){
				if ('error_code' in rep){
					console.log("error");
				}
				else {
					console.log("ok");
					responseRegister(rep);
				}
			},
			error: function(xhr, status, err){
				func_error(xhr.responseText);
			}
		});
	}
	else {
		responseRegister({});
	}
}

function responseRegister(rep){
	if (rep.error == undefined){
		makeSuccessPanel();
	}
	else {
		func_error_register(rep.error);
	}
}

function makeSuccessPanel(){
	closeModal($(".new-user-modal .close-modal"));
	var s = '<div class="modal success-modal">\
				<div class="success">\
					<span class="close-modal">&times;</span>\
					</br>\
					<p>Successfully registered!</p>\
					<p>Enjoy your time with us.</p>\
				</div>\
			</div>';
	var el = $(".success-modal")
	if (el.length == 0){
		$(".main-content").prepend(s);
		$(".success-modal").fadeIn(500);
	}
	else {
		el.replaceWith(s);
		$(".success-modal").fadeIn(500);
	}

}

function makeRegistrationPanel(){
	if(! $("#login-content").is(":hidden")){
		$("#login-content").hide();
	}
    $(".new-user-modal").fadeIn('normal');
    $("#new-user-content").fadeIn('normal');
}