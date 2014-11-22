<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Shopping Cart</title>
	<script src="Resources/JS/jquery-1.11.1.min.js"></script>
	<script src="Resources/JS/jquery-ui.js"></script>
	<script src="Resources/JS/LoginAndRegistration"></script>
	<link href="Resources/main.css" rel="stylesheet" type="text/css">
	<link href="Resources/jquery-ui.structure.css" rel="stylesheet" type="text/css">
	<link href="Resources/jquery-ui.theme.css" rel="stylesheet" type="text/css">
	
	<script>
	$( document ).ready(function() {
		
	});
	</script>
</head>
<body>
<%
String user = null;

user = (String)session.getAttribute("user");

if(user == null) {
	response.sendRedirect("Login.jsp");
}

String flights = String.valueOf(request.getAttribute("flights"));
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
		<span id=welcome><h1>Your shopping cart</h1></span>
		
	</td>
</tr>
</table>
	<script>
var UserName= "<%=user %>"
	ShowUsername(UserName);
	
var json = '<%= flights %>';
$( document ).ready(function() {
	var html = "<table class='resultsTable'><tr><th>Flight Number</th><th>Source</th><th>Destination</th><th>Departure Time</th><th>Arrival Time</th><th># Of Stops</th><th>Seats</th><th>Cost</th></tr>";
	var total= 0;
	for (var i in json) {
		  if (json.hasOwnProperty(i)) {
			    //alert(key + " -> " + JSONFlights[key]);
			    html+="<tr><td>" + json[i].flightID + "</td>";
			    html+="<td>" + json[i].departuretime + "</td>";
			    html+="<td>" + json[i].arrivaltime + "</td>";
			    html+="<td>" + json[i].stops + "</td>";
			    html+="<td style='color:green'><b>$" + json[i].cost + "</b></td>";
			    html+="<td align='center'><a href='javascript:ViewAndBook("+json[i].flightID+","+json[i].cost+","+json[i].stops+");'>Select</a></td></tr>";
			    count++;
		  }
		}
});
</script>
</body>
</html>