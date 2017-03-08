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
    return (
	"<div id=\"message_" + this.id + "\" class=\"message\"> \n "
	    + "\t <div class=\"text-message\"> \n \t \t" + this.text + "\n"
	    + "\t </div> \n "
	    + "\t <div class=\"info-message\"> \n "
	    + "\t \t <span>Posté par : <span class=\"link\">" + this.author.login + "</span> \n"
	    + "\t \t le " + this.date + "</span> \n"
	    + "\t </div> \n"
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
	    + "\t <div class=\"text-message\"> \n \t \t" + this.text + "\n"
	    + "\t </div> \n "
	    + "\t <div class=\"info-message\"> \n "
	    + "\t \t <span>Posté par : <span class=\"link\">" + this.author.login + "</span> \n"
	    + "\t \t le " + this.date + "</span> \n"
	    + "\t </div> \n"
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
    env.noConnection = true;
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

    var com1 = new Comment(2, user2, "hello", Date(Date.now()));
    var com2 = new Comment(3, user3, "femaofkaeof", Date(Date.now()));
    var mes1 = new Message(1, user1, "yo", Date(Date.now()-1000), [com1, com2]);
    return mes1;
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
}

function completeMessages(){


}
