<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Booking History</title>
	<script src="Resources/JS/jquery-1.11.1.min.js"></script>
	<script src="Resources/JS/LoginAndRegistration"></script>
	<link href="Resources/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<%
String user = null;

user = (String)session.getAttribute("user");

if(user == null) {
	response.sendRedirect("Login.jsp");
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
		<div>
			<table class="bottomBorder">
				<thead>
					<tr>
						<td>Flight Date</td>
						<td>Departure Time</td>
						<td>Arrival Time</td>
						<td>Number of Stops</td>
						<td>Cost</td>
						<td>Ticket Number</td>
					</tr>
				</thead>		
				<tr>
					<td>08/19/2014</td>
					<td>13:00</td>
					<td>15:30</td>
					<td>1</td>
					<td>$200.00</td>
					<td>A1W3WS</td>
				</tr>
				<tr>
					<td>02/23/2015</td>
					<td>07:00</td>
					<td>11:15</td>
					<td>3</td>
					<td>$415.35</td>
					<td>JMOD9861</td>
				</tr>
				<tr>
					<td>06/02/2015</td>
					<td>17:00</td>
					<td>20:50</td>
					<td>2</td>
					<td>$307.94.00</td>
					<td>9534A13R</td>
				</tr>
				<tr>
					<td>12/11/2013</td>
					<td>11:00</td>
					<td>18:320</td>
					<td>5</td>
					<td>$1098.11</td>
					<td>9AG48AZN</td>
				</tr>			
			</table> 
		</div>
	</td>
</tr>
</table>
<script>
var UserName= "<%=user %>"
	ShowUsername(UserName);
</script>
</body>
</html>