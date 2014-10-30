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
	<script>
	function ViewAndBook(flightid,cost,stops){
		
		//round about hacky way to implement a submit form....
		
		
		    var newForm = jQuery('<form>', {
		        'action': 'FlightSearchResults',
		        'method':'POST'
		    }).append(jQuery('<input>', {
		        'name': 'f',
		        'value': flightid,
		        'type': 'hidden'
		    })).append(jQuery('<input>', {
		        'name': 'c',
		        'value': cost,
		        'type': 'hidden'
		    })).append(jQuery('<input>', {
		        'name': 's',
		        'value': stops,
		        'type': 'hidden'
		    }));
		    newForm.submit();
	}
	</script>
</head>
<body>
<%@ page import="org.json.JSONObject,classes.Flight,java.util.ArrayList,org.json.*" %>
<%
String user = null;

user = (String)session.getAttribute("user");

if(user == null) {
	response.sendRedirect("Login.jsp");
}

String sJSONFlights = String.valueOf(request.getAttribute("flights"));
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
		<span id=welcome><h1>Flight Search Results</h1></span><div id="count"></div><br>
		
		<div id="results">
			
		</div>
	</td>
</tr>
</table>
	<script>
var UserName= "<%=user %>";
	ShowUsername(UserName);
	
var json = '<%=sJSONFlights %>';
json=JSON.parse(json);
$( document ).ready(function() {
	//$("#results").html(JSONFlights);
	var wehaverowsbaby=false;
	var html = "";
	var count = 0;
	html+="<table class='resultsTable'><tr><th>Flight Number</th><th>Departure Time</th><th>Arrival Time</th><th># Of Stops</th><th>Cost</th><th>View and Book</th></tr>";
	for (var i in json) {
		  if (json.hasOwnProperty(i)) {
			  wehaverowsbaby=true;
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
	html+="</table>";
	if(!wehaverowsbaby){
		html="<h5>No Flights match your criteria</h5>";
	}
	$("#count").html("Number of flights: " +count);
	$("#results").html(html);
});
</script>
</body>
</html>