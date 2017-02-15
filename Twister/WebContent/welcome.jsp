<HTML>
	<HEAD><TITLE>Homepage</TITLE></HEAD>
		<BODY>
			<H1>Index</H1>
			<% String user = session.getAttribute("user").toString(); %>
			Hello ${user}.
			
			<form action="message/search" method="get">
			         <input type="text" name="query">
			         <input type="submit" value="Search" />
			</form>
			
			<form action="message/new" method="post" >
			 	 Message:<br />
  					<textarea name="message" rows="5" cols="30"> </textarea><br />
					<input type="submit" value="Submit" />
			</form>
			<!-- ><p><a href="en/message.html">New message</a></p> --
			
			<!-- Liste messages -->
			<p><a href="message/list">Messages list</a></p>
			<p><a href="friend/list">Friends list</a></p>
			<p><a href="auth/logout">Logout</a></p>
		
		</BODY>
</HTML>