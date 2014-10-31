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
	<script>
	$( document ).ready(function() {
		$.post('BookingHistory',
		{},
		function(json) { 
                      if(json.indexOf("failed") >-1){
                      	//fail
                      	alert("Error");
                     	} else {
                     		json = JSON.parse(json);
                     		var wehaverowsbaby=false;
                     		var html = "";
                     		var count = 0;
                     		html+="<table class='resultsTable'><tr><th>Booking Date</th><th>Booking ID</th><th>Flight ID</th><th>Number of Seats</th><th>Total Cost</th></tr>";
                     		for (var i in json) {
                     			  if (json.hasOwnProperty(i)) {
                     				  wehaverowsbaby=true;
                     				    //alert(key + " -> " + JSONFlights[key]);
                     				    html+="<tr><td>" + json[i].date + "</td>";
                     				    html+="<td>" + json[i].id + "</td>";
                     				    html+="<td>" + json[i].flightid + "</td>";
                     				    html+="<td>" + json[i].seats + "</td>";
                     				    html+="<td style='color:green'><b>$" + json[i].cost + "</b></td>";
                     				   
                     				    count++;
                     			  }
                     			}
                     		html+="</table>";
                     		if(!wehaverowsbaby){
                     			html="<h5>No Flight History</h5>";
                     		}
                     		$("#count").html("Number of flights: " +count);
                     		$("#detailsOut").html(html);                   		
                      }
                               
                   });	
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
		<span id=welcome><h1>Booking History</h1></span>
		<span id="count"></span>
		<div id="detailsOut">
			
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