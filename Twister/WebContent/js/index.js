/* --------------------------------------------------------
   ENVIRONNEMENT DE TEST ET PAGE PRINCIPALE
   ----------------------------------------------------------*/

function init() {
    env = new Object();
    env.noConnection = false;

    // back and forward
    /*$(window).on('popstate', function (e){
		var state = e.originalEvent.state;
		if (state != null){
			document.title = state.title;
		}
    });*/

    // chargement progressif des messages
    $(document.body).on('appear', '.message', function(e, $affected){
    	completeMessages();
    	$.clear_appear();
    	console.log("déclenché");
    });
    $(document.body).on('appear', '.profile-message', function(e, $affected){
    	completeMessagesProfile();
    	$.clear_appear();
    	console.log("déclenché");
    });

    // page d'accueil
    // login
    $(document).on('submit', '#login-form', function(e) {
		e.preventDefault();
	    connection(this);
	    //window.history.pushState({page: "home"}, null, 'home');
	});
	$(document).on('click', '#login-dropdown', function(e){
        e.stopPropagation();
		makeConnectionPanel();
	});
	$(document).on('click', "#forgot-password", function(){
		makeRecoveryPanel();
	})
	$(document).on('submit', '#forgot-form', function(e){
		e.preventDefault();
		sendPassword(this);
	})

	// enregistrement
	$(document).on('click', '#new-user', function(){
	   makeRegistrationPanel();
	});
	$(document).on('submit', '#new-user-form', function(e) {
		e.preventDefault();
	    register(this);
	    //window.history.pushState({page: "home"}, null, 'home');
	});
	$(document).on('click', '.close-toggle', function(){
	   closeDropdown(this);
	});
	$(document).on('click', '.close-modal', function(){
		closeModal(this);
	});

	// page utilisateur
	// messages et commentaires
	$(document).on('click', '.expand-comments', function(){
	    var id = $(this).parent().parent().get(0).id;
		developpeMessage(parseInt(id.substring(id.indexOf('_') + 1)));
	});
	$(document).on('click', '.reduce-comments', function(){
		var id = $(this).parent().parent().get(0).id;
		reduceMessage(parseInt(id.substring(id.indexOf('_') + 1)));
	});
	$(document).on('submit', '#new-message-form', function(e){
		e.preventDefault();
	    newMessage($('#new-message').val());
	  	$('#new-message').fadeOut('normal', function() {
			$(this).val('');
			$(this).fadeIn('normal');
		});
	});
	$(document).on('submit', '#new-comment-form', function(e){
	    e.preventDefault();
	    var id_msg = $(this).parent().parent().get(0).id;
	    var id = parseInt(id_msg.substring(id_msg.indexOf('_') + 1));
	    newComment(id, $('#new-comment').val());
	    $('#message_' + id + ' #new-comment').fadeOut('normal', function() {
			$(this).val('');
			$(this).fadeIn('normal');
		});
	});

	// search
	$(document).on('keypress', '.submitEnter', function(e){
		if (e.which == 13 && !e.shiftKey){
			$(this.form).submit();
			e.preventDefault();
		}
	});
	$(document).on('submit', '#search-form', function(e){
		e.preventDefault();
		//console.log($('#search-query').val());
		makeSearchPage();
		completeMessagesSearch($('#search-query').val());
		$('#search-query').val('');
	});



	// divers
	// chargement page profil
    $(document).on('click', '.link', function(){
		var user = $(this).get(0).innerText;
		getInfoUser(user);
		var url = '?user=' + user
		//window.history.pushState({page:url, title: capitalizeFirstLetter(user)}, user, url);
    });
    $(document).on('click', '.logout', function() {
	   	logout(env.key);
	   	//window.history.pushState({page: 'index', title: 'Homepage'}, null, 'index');
	});
    $(document).on('click', '.back-button', function(){
		pageBack();
		//window.history.pushState({page: "home", title: 'Home'}, null, 'home');
    });
    $(document).on('click', '.follow-button', function() {
	   	followUser();
	});
    $(document).on('click', '.unfollow-button', function() {
	   	unfollowUser();
	});
}

function setVirtualMessages() {
    localdb = [];
    follows = [];
    var user1 = {"id": 1, "login": "toto"};
    var user2 = {"id": 2, "login": "toto2"};
    var user3 = {"id": 3, "login": "toto3"};

    follows[1] = new Set();
    follows[1].add(2);
    follows[1].add(3);

    var com1 = new Comment(2, user2, "haha il l'a dans le fillon", Date(Date.now()));
    var com2 = new Comment(3, user3, "ça rime en plus", Date(Date.now()));
    var mes1 = new Message(0, user1, "Fillon détourne des fonds", new Date(2017, 02, 22, 15, 05, 42), [com1, com2]);

    var com3 = new Comment(2, user2, "Bonjour", new Date("2017-03-22"));
    var mes2 = new Message(1, user1, "deuxieme message", new Date(2017, 02, 22, 16, 35, 22), [com3]);

    var mes3 = new Message(2, user2, "Les casseroles de légende", new Date("2017-03-24"));
    var mes4 = new Message(3, user3, "Billy : le retour", new Date("2017-03-25"));

    localdb.push(mes1);
    localdb.push(mes2);
    localdb.push(mes3);
    localdb.push(mes4);
}

function makeMainPanel(fromId, fromLogin, query){
    /* Page d'accueil */
    if (fromId == undefined){
		fromId = -1;
    }
    env.fromId = fromId;
    env.fromLogin = fromLogin;
    env.query = query;
    env.messages = new Map();
    env.minId = -1;
    env.maxId = -1;

      var mainCode = " \
    <nav class=\"navbar\"> \
    	<div class=\"nav-left\"> \
			 <a href=\"\"><img class=\"logo\" src=\"img/logo_black.png\"/></a> \
			 <div class=\"search\"> \
			  <form id=\"search-form\"> \
				<textarea id=\"search-query\" class=\"submitEnter\" type=\"text\" placeholder=\"Search\"></textarea> \
				<input type=\"submit\" value=\"Search\" /> \
			  </form> \
			  </div> \
		</div> \
		<div class=\"nav-right\"> \
		  <div class=\"logout\"> \
		 	Logout\
		  </div> \
		</div> \
    </nav> \
  	<div class=\"main-container\"> \
		<div class=\"main-content\"> \
			<div class=\"main-row\">\
				<div class=\"stats-container card\"> \
					<div class=\"card-title\">\
						<h4>Stats</h4> \
					</div>\
					<div class=\"stats\">\
					</div>\
				</div> \
				<div class=\"new-message card\"> \
					<div class=\"card-title\">\
						<h4>New message</h4> \
					</div>\
					<div class=\"message-box\">\
						<form id=\"new-message-form\" > \
							<textarea id=\"new-message\" class=\"submitEnter\" rows=\"5\" cols=\"40\"> </textarea><br /> \
							<input type=\"submit\" value=\"Submit\" /> \
						</form>	 \
					</div>\
				</div> \
			</div> \
			<div class=\"card\"> \
					<div class=\"card-title\">\
				  		<h4>Latest messages</h4> \
				  	</div>\
				  	<div class=\"message-list\">\
				  	</div>\
			</div> \
		</div> \
	</div> \
    ";
    $("body").css("display", "none");
	$("body").fadeIn(1000);
    $("body").html(mainCode);
    $('link[href="css/styleindex.css"]').attr('href','css/style.css');
    completeMessages();
}

function pageUser(id, login){
    makeMainPanel(id, login);
}

function pageBack(){
    var mainCode = " \
		<div class=\"main-content\"> \
			<div class=\"main-row\">\
				<div class=\"stats-container card\"> \
					<div class=\"card-title\">\
						<h4>Stats</h4> \
					</div>\
					<div class=\"stats\">\
					</div>\
				</div> \
				<div class=\"new-message card\"> \
					<div class=\"card-title\">\
						<h4>New message</h4> \
					</div>\
					<div class=\"message-box\">\
						<form id=\"new-message-form\" > \
							<textarea id=\"new-message\" class=\"submitEnter\" rows=\"5\" cols=\"40\"> </textarea><br /> \
							<input type=\"submit\" value=\"Submit\" /> \
						</form>	 \
					</div>\
				</div> \
			</div> \
			<div class=\"card\"> \
					<div class=\"card-title\">\
				  		<h4>Latest messages</h4> \
				  	</div>\
				  	<div class=\"message-list\">\
				  	</div>\
			</div> \
		</div> \
    ";
    $(".main-container").css("display", "none");
	$(".main-container").fadeIn(500);
    $(".main-container").html(mainCode);
 	resetMessages();
    profile = undefined;
    completeMessages();
}



/* --------------------------------------------------------
   DROPDOWN et CLOSE
   ----------------------------------------------------------*/

function makeHomePage(){
	var s = ' \
<nav class="navbar">\
			<div class="nav-left">\
				<div class="nav-logo">\
					<a href=""><img class="logo" src="img/logo_black.png"/></a>\
				</div>\
			</div>\
			<div class="nav-right">\
				<div id="login-dropdown">\
					Log in\
				</div>\
				<div id="login-content">\
						<span class="close-toggle">&times;</span>\
							<h3>Welcome back.</h3>\
							<form id="login-form">\
								<input name="login" type="text" placeholder="Username">\
								<input name="password" type="password" placeholder="Password">\
								<input type="submit" value="Log In">\
							</form>\
							</br>\
							<span id="forgot-password">Forgot your password?</span>\
				</div>\
				<div id="new-user" onClick="makeRegistrationPanel()">\
					Sign up\
				</div>\
				<div class="modal new-user-modal">\
					<div id="new-user-content">\
						<span class="close-modal">&times;</span>\
							<h3>Join us, it\'s free.</h3>\
							</br>\
						<form id="new-user-form">\
							<input type="text" name="fname" placeholder="First name">\
							<input type="text" name="lname" placeholder="Last name">\
							<input type="text" name="login" placeholder="Username">\
							<input type="password" name="password" placeholder="Password">\
							<input type="password" name="passwordCheck" placeholder="Password check">\
							<input type="text" name="mail" placeholder="Email address">\
							<input type="text" name="mailCheck" placeholder="Email address check">\
							<input type="submit" value="Sign up">\
						</form>\
					</div>\
				</div>\
			</div>\
		</nav>\
		<div class="main-container">\
			<div class="main-content">\
				<div class="modal recovery-modal">\
					<div id="recovery-password">\
						<span class="close-modal">&times;</span>\
						</br>\
						<p>Please input your email address to recover your password.</p>\
						<form id="forgot-form">\
							<input type="text" name="mail" placeholder="Email address" required>\
							<input type="submit" value="Submit">\
						</form>\
					</div>\
				</div>\
				<h1>Welcome on board.</h1>\
				<p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."\
				</p>\
				<p>"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"\
				</p>  \
			</div>\
			<div class="main-separator">\
				<hr>\
			</div>  \
			<footer>\
				<span class="footer-left">\
					<a href="index.html">English</a>\
					<a href="fr/index.html">Français</a>\
				</span>\
				<span class="footer-right">\
					<a href="apidoc/">Documentation</a>\
				</span>\
			</footer>\
		</div>';
 	$("body").css("display", "none");
	$("body").fadeIn(500);
    $("body").html(s);
    $('link[href="css/style.css"]').attr('href','css/styleindex.css');
}

function resetMessages(){
	env.minId = -1;
	env.maxId = -1;
	env.messages = new Map();
}

function closeDropdown(e){
    $(e).parent().slideToggle();
}

function closeModal(e){
    $(e).parent().hide();
    $(e).parent().parent().fadeOut('normal');
}

