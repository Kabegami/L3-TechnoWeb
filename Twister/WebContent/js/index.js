toto = {"id":1, "login":"toto"}
msg = new Message(1, toto, "blabla", Date(Date.now()));

function test(){
    return (msg.id + " " + msg.author.login + " " + msg.text + " " + msg.date);
}

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
	    + "\t \t <span class=\"link author\">" + this.author.login + "</span> \n"
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
	    + "\t \t <span class=\"link author\">" + this.author.login + "</span> \n"
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


/* --------------------------------------------------------
   ENVIRONNEMENT DE TEST ET PAGE PRINCIPALE
   ----------------------------------------------------------*/

function init() {
    env = new Object();
    env.noConnection = false;
    if (env.noConnection){
    	setVirtualMessages();
    }
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
    env.messages = [];
    env.minId = -1;
    env.maxId = -1;

      var mainCode = " \
    <nav class=\"navbar\"> \
    	<div class=\"nav-left\"> \
			 <a href=\"\"><img class=\"logo\" src=\"img/logo_black.png\"/></a> \
			 <div class=\"search\"> \
			  <form action=\"message/search\" method=\"get\"> \
				<textarea type=\"text\" placerholder=\"Search\" name=\"query\"></textarea> \
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
      		<div class=\"stats verticalLine\"> \
				<h4>Stats</h4> \
			</div> \
			<div class=\"messages\"> \
				<div class=\"message-box\"> \
				  <h4>New message</h4> \
				  <form action=\"message/new\" method=\"get\" > \
					<textarea name=\"message\" rows=\"5\" cols=\"40\"> </textarea><br /> \
					<input type=\"submit\" value=\"Submit\" /> \
				  </form>	 \
				</div> \
				<div class=\"message-list\"> \
				  <hr> \
				  <h4>Liste de messages ici</h4> \
				</div> \
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
    makeMainPanel(id, login, "");
}


/* --------------------------------------------------------
   DROPDOWN et CLOSE
   ----------------------------------------------------------*/

function makeHomePage(){
	var s = " \
	<nav class=\"navbar\"> \
			<div class=\"nav-left\"> \
				<div class=\"nav-logo\">\
					<a href=\"\"><img class=\"logo\" src=\"img/logo_black.png\"/></a>\
				</div>\
			</div>\
			<div class=\"nav-right\">\
				<div id=\"login-dropdown\">\
					Log in\
				</div>\
				<div id=\"login-content\">\
						<span class=\"close-toggle\">&times;</span>\
							<p>Welcome back.</p>\
							<form id=\"login-form\">\
								<input id=\"login\" type=\"text\" placeholder=\"Username\">\
								<input id=\"password\" type=\"password\" placeholder=\"Password\">\
								<input type=\"submit\" value=\"Log In\">\
							</form>\
				</div>\
				<div id=\"new-user\" onClick=\"makeRegistrationPanel()\">\
					Sign up\
				</div>\
				<div class=\"modal\">\
					<div id=\"new-user-content\">\
						<span class=\"close-modal\" onClick=\"\">&times;</span>\
							<p>Join us, it's free.</p>\
							</br>\
						<form>\
							<input type=\"text\" name=\"fname\" placeholder=\"First name\" required>\
							<input type=\"text\" name=\"lname\" placeholder=\"Last name\">\
							<input type=\"text\" name=\"login\" placeholder=\"Username\">\
							<input type=\"password\" name=\"pwd\" placeholder=\"Password\">\
							<input type=\"text\" name=\"mail\" placeholder=\"Email adress\">\
							<input type=\"submit\" value=\"Sign up\"></input>\
						</form>\
					</div>\
				</div>\
			</div>\
		</nav>\
		<div class=\"main-container\">\
			<div class=\"main-content\">\
				<h1>Welcome on board.</h1>\
				<p>\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\
				</p>\
				<p>\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\"\
				</p>  \
			</div>\
			<div class=\"main-separator\">\
				<hr>\
			</div>  \
			<footer>\
				<span class=\"footer-left\">\
					<a href=\"index.html\">English</a>\
					<a href=\"fr/index.html\">Français</a>\
				</span>\
				<span class=\"footer-right\">\
					<a href=\"apidoc/\">Documentation</a>\
				</span>\
			</footer>\
		</div>\
	";
 	$("body").css("display", "none");
	$("body").fadeIn(1000);
    $("body").html(s);
    $('link[href="css/style.css"]').attr('href','css/styleindex.css');
    init();
}

function makeRegistrationPanel(){
	if(! $("#login-content").is(":hidden")){
		$("#login-content").hide();
	}
    $(".modal").show();
    $("#new-user-content").show();
}

function closeDropdown(e){
    $(e).parent().slideToggle();
}

function closeModal(e){
    $(e).parent().hide();
    $(e).parent().parent().hide();
}

