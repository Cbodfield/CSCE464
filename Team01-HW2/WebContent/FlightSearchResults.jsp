<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search Results</title>
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
						<td>Flight Number</td>
						<td>Flight Date</td>
						<td>Departure Time</td>
						<td>Arrival Time</td>
						<td>Number of Stops</td>
						<td>Cost</td>
						<td></td>
					</tr>
				</thead>
				<tr>
					<td>A123BS</td>
					<td>08/19/2014</td>
					<td>13:00</td>
					<td>15:30</td>
					<td>1</td>
					<td>$200.00</td>
					<td><button onclick="location.href='${pageContext.request.contextPath}/ViewBook.jsp'" >View and Book</button></td>
				</tr>
				<tr>
					<td>BINA9865</td>
					<td>01/13/2015</td>
					<td>19:00</td>
					<td>22:30</td>
					<td>1</td>
					<td>$150.00</td>
					<td><button onclick="location.href='${pageContext.request.contextPath}/ViewBook.jsp'">View and Book</button></td>
				</tr>
				<tr>
					<td>98GA689A1</td>
					<td>03/14/2015</td>
					<td>08:00</td>
					<td>14:45</td>
					<td>2</td>
					<td>$457.13</td>
					<td><button onclick="location.href='${pageContext.request.contextPath}/ViewBook.jsp'">View and Book</button></td>
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