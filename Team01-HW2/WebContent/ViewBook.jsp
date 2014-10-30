<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Booking</title>
	<script src="Resources/JS/jquery-1.11.1.min.js"></script>
	<script src="Resources/JS/LoginAndRegistration"></script>
	<link href="Resources/main.css" rel="stylesheet" type="text/css">
</head>
<script>
	function selectFlight(){
		
		location.href='Transaction.jsp';
	}
</script>
<body>
<%
String user = null;
String details = "";
user = (String)session.getAttribute("user");

if(user == null) {
	response.sendRedirect("Login.jsp");
}

details = String.valueOf(request.getAttribute("details"));
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
		<span id=welcome><h1>View and Book</h1></span>
		<div>
			<table>
				<tr>
					<td>Flight Number</td>
					<td id="flightnumber">ABC12345</td>
				</tr>
				<tr>
					<td>Flight Date</td>
					<td id="flightdate">09/28/2014</td>
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
					<td>Number of Stops</td>
					<td id="stops" >2</td>
				</tr>
				<tr>
					<td>Cost</td>
					<td id="cost">$1,434.87</td>
				</tr>
				<tr>
					<td>Seats</td>
					<td>
						<select>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan='2'style="text-align:center">
					<button class='nav_button'  onclick="selectFlight()">Select</button>
					</td>
				</tr>
				<tr>
					<td colspan='2' style="text-align:center">
					<button class='nav_button'  onclick="javascript:window.history.back()">Return</button>
					</td>
				</tr>
			</table>
		</div>
	</td>
</tr>
</table>
	<script>
var UserName= "<%=user %>"
	ShowUsername(UserName);
	
var details = '<%=details %>';
var json = JSON.parse(details);
$( document ).ready(function() {
	//$("#results").html(JSONFlights);


	$("#flightnumber").html(json[0].id);
	
	var dep = json[0].departure;
	var arrival = json[0].arrival;
	var dep_date = dep.split(" ");
	var arrival_date = arrival.split(" ");
	if(dep_date[0] == arrival_date[0]){
		$("#flightdate").html(dep_date[0]);
	} else {
		$("#flightdate").html(dep_date[0] + "-" + arrival_date[0]);
	}
	
	$("#departure").html(dep_date[1]);
	$("#arrival").html(arrival_date[1]);
	$("#stops").html(json[0].stops);
	$("#cost").html("$" + json[0].cost);

});
</script>
</body>
</html>
