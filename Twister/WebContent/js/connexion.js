function connection(form){
    var login = form.login.value;
    var pass = form.password.value;
    var ok = check_form(login, pass);
    if (ok) {
	connecte(login, pass);
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
	$("form").prepend(s);
    }
    else {
	old_msg.replaceWith(s);
    }	
}

function makeConnectionPanel(){
    $("#login-content").slideToggle();
}
