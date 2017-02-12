<HTML>
	<HEAD><TITLE>Homepage</TITLE></HEAD>
		<BODY>
			<H1>Index</H1>
			<% String user = session.getAttribute("user").toString(); %>
			Hello ${user}.
			<p><a href="en/message.html">New message</a></p>
		</BODY>
</HTML>