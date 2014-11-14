<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="Resources/JS/jquery-1.11.1.min.js"></script>
<script src="Resources/JS/LoginAndRegistration"></script>
<LINK href="Resources/main.css" rel="stylesheet" type="text/css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
<%
String userName = null;

userName = (String)session.getAttribute("user");

if(userName != null) {
	//We are logged in, lets get them home!
	response.sendRedirect("FlightSearchQuery.jsp");
}
%>

<table id=global_table border=0>
<tr>
	<td id=navigation>
		<table border=0 class=navtable width="100%">
			<tr><td><div id=date></div></td></tr>
			<tr><td><div id=time></div></td></tr>
			<tr><td><hr/></td></tr>
			<tr><td>
				<div id=login_username>
				
					<input width="100%" type="text" placeholder="Username" id="username"/>
					<input width="100%" type="password" placeholder="Password" id="password"/>	
					<br><br>
					<a href="javascript:login();" class="" width="50%">Login</a>
					<a href="Register.jsp;" class="" width="50%">Register</a>
					
				</div>
					
			</td></tr>
			
			<tr><td><hr/></td></tr>
			<tr><td><button onclick="location.href='FlightSearchQuery.jsp';" class="nav_button">Flight Search</button></td></tr>
			<tr><td><button onclick="location.href='BookingHistory.jsp';"  class="nav_button">Booking History</button></td></tr>
				
		</table>
		
	</td>
	<td id=content valign="top" align="middle">
		<span id=welcome><h1>Welcome to FlightSearch.Com</h1></span>
	</td>
</tr>
</table>

<script>
var UserName= "<%=userName %>"
ShowUsername(UserName);
</script>
</body>
</html>