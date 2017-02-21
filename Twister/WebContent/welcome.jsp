<html>
	<head>
		<TITLE>Homepage</title>
		<link rel="stylesheet" type="text/css" href="./css/style.css">
	</head>
		<body>
			<h1>Twister</h1>
			<% String user = session.getAttribute("user").toString();%>
			Hello ${user}.
			
			<form action="message/search" method="get">
			         <input type="text" name="query">
			         <input type="submit" value="Search" />
			</form>
			
			<div class="menu">
				<!-- Liste messages -->
			<ul>
				<li><a href="message/list">Messages list</a></li>
				<li><a href="friend/list">Friends list</a></li>
				<li><a href="auth/logout">Logout</a></li>
			</ul>
			</div>
			
			<div class="main">
			<form action="message/new" method="post" >
			 	 Message <br />
  					<textarea name="message" rows="5" cols="40"> </textarea><br />
					<input type="submit" value="Submit" />
			</form>	
			</div>
		</body>
</html>