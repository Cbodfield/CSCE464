<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Transaction Confirmation</title>
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
String details = String.valueOf(request.getAttribute("details"));
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
		<span id=welcome><h1>Transaction</h1></span>
		<div id="detailsOut">
			<table>
				<tr>
					<td>Flight Number</td>
					<td id="flightid">ABC12345</td>
				</tr>
				<tr>
					<td>Departure Time</td>
					<td id="departure">08:00</td>
				</tr>
				<tr>
					<td>Arrival Time</td>
					<td id="arrival">17:00</td>
				</tr>
				<tr>
					<td>Total Cost</td>
					<td id="cost">$1,434.87</td>
				</tr>
				<tr>
					<td colspan=2><b>Your purchase has been confirmed!</b></td>
				</tr>
				<tr>
					<td colspan='2' style="text-align:center"><button class="nav_button" onclick="location.href='FlightSearchQuery.jsp'">Return Home</button></td>
				</tr>
				</table>
		</div>
		<div id="passengerdetails">
		</div>
		<br>
		<center><A HREF="javascript:window.print()">Click to Print This Page</A></center>
	</td>
</tr>
</table>
<script>
var UserName= "<%=user %>"
	ShowUsername(UserName);
	
var json = '<%=details %>';
$( document ).ready(function() {
	details = JSON.parse(json);
	var numberOfSeats  =0 ;
	if(!details.bSuccess){
		$("#detailsOut").html(details.sMessage);
	} else {
		
		$("#flightid").html(details.id);
		//$("#operator").html(details.operator);
		//$("#source").html(details.source);
		//$("#destination").html(details.destination);
		$("#departure").html(details.departure);
		$("#arrival").html(details.arrival);
		$("#cost").html(details.cost);
		numberOfSeats = parseInt(details.seats);
	}
	var html ="<table><tr><th>Name</th><th>Age</th><th>Sex</th></tr>";
	for(var i =0;i<numberOfSeats;i++){
		html+="<tr><td><input type='text' ></input></td><td><input type='text'></input></td><td><input type='text'></input></td></tr>";
	}
	html+="</table>";
	$("#passengerdetails").html(html);
	
	
});
</script>
</body>
</html>