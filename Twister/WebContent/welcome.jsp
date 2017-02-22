<!DOCTYPE html>
<html>
  <head>
    <title>Homepage</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans|Raleway|Source+Sans+Pro" rel="stylesheet">
  </head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <body>
    <header>
      <!-- TODO -->
    </header>
    <nav class="navbar">
      <div class="nav-left">
	<div class="search">
	  <form action="message/search" method="get">
	    <input type="text" placerholder="Search" name="query">
	    <input type="submit" value="Search" />
	  </form>
	</div>
      </div>
      <div class="nav-right">
	<div class="logout">
	  <a href="auth/logout">Logout</a>
	</div>
    </nav>

    
    <% String user = session.getAttribute("user").toString();%>
    <h3>Hello ${user}.</h3>
    
    <aside class="stats verticalLine">
      Stats
    </aside>

    <div class="main">
      <section class="message">
	<h4>New message</h4>
	<form action="message/new" method="post" >
  	  <textarea name="message" rows="5" cols="40"> </textarea><br />
	  <input type="submit" value="Submit" />
	</form>	
      </section>

      <section class="message-list">
	<hr>
	<h4>Liste de messages ici</h4>
      </section>
    </div>
  </body>
</html>
