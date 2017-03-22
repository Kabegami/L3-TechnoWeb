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
    setVirtualMessages();
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
    var mes1 = new Message(0, user1, "Fillon détourne des fonds", Date(Date.now()-1000), [com1, com2]);

    var com3 = new Comment(2, user2, "Bonjour", Date(Date.now()));
    var mes2 = new Message(1, user1, "deuxieme message", Date(Date.now()), [com3]);
    localdb.push(mes1);
    localdb.push(mes2);
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
    <header> \
    </header> \
    <nav class=\"navbar\"> \
     <a href=\"\"><img class=\"logo\" src=\"img/logo_black.png\"/></a> \
     <div class=\"search\"> \
	  <form action=\"message/search\" method=\"get\"> \
	    <textarea type=\"text\" placerholder=\"Search\" name=\"query\"></textarea> \
	    <input type=\"submit\" value=\"Search\" /> \
	  </form> \
      </div> \
      <div class=\"logout\"> \
	 Logout\
      </div> \
    </nav> \
 \
     <div class=\"main-separator\"> \
      <hr> \
    </div> \
     \
    \
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
 \
	<div class=\"message-list\"> \
	  <hr> \
	  <h4>Liste de messages ici</h4> \
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

function completeMessages(){
    if (! env.noConnection){
	// communication serveur
    }
    else {
		var tab = getFromLocalDb();
		completeMessagesResponse(JSON.stringify(tab));
		for (var i = 0; i < env.messages.length; i++){
			$(".message-list").append(env.messages[i].getHtml());
		}
    }
}

function completeMessagesResponse(rep){
    var tab = JSON.parse(rep, revival);
    for (var i = 0; i < tab.length; i++){
		var m = tab[i];
		//alert(m.getHtml());
		env.messages.push(m);
    }
}

function getFromLocalDb(from, minId, maxId, nbMax){
    var tab = [];

    // page d'accueil
    if (from < 0){
	
    }
    return localdb;
}



/* --------------------------------------------------------
   DROPDOWN et CLOSE
   ----------------------------------------------------------*/

function makeRegistrationPanel(){
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

