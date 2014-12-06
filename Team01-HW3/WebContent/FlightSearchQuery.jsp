<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Flight Search</title>
	<script src="Resources/JS/jquery-1.11.1.min.js"></script>
	<script src="Resources/JS/jquery-ui.js"></script>
	<script src="Resources/JS/LoginAndRegistration"></script>
	<link href="Resources/main.css" rel="stylesheet" type="text/css">
	<link href="Resources/jquery-ui.structure.css" rel="stylesheet" type="text/css">
	<link href="Resources/jquery-ui.theme.css" rel="stylesheet" type="text/css">
	
	<script>
	$( document ).ready(function() {
		var airports = ["ATL", "ANC", "AUS", "BWI", "BOS", "CLT", "MDW", "ORD", "CVG", "CLE", "CMH", "DFW", "DEN", "DTW", "FLL", "RSW", "BDL", "HNL", "IAH", "HOU", "IND", "MCI", "LAS", "LAX", "MEM", "MIA", "MSP", "BNA", "MSY", "JFK", "LGA", "EWR", "OAK", "ONT", "MCO", "PHL", "PHX", "PIT", "PDX", "RDU", "SMF", "SLC", "SAT", "SAN", "SFO", "SJC", "SNA", "SEA", "STL", "TPA", "IAD", "DCA"]; 
		jQuery.each(airports,function(key,val){
			$("#source").append($("<option></option>").attr("value",val).text(val));
			$("#destination").append($("<option></option>").attr("value",val).text(val));
			
			});
		$("#datepicker").datepicker();
		$("#datepicker").datepicker("option","dateFormat","yy-mm-dd");
			
		$("#seats").on('input', function() {
			var match = new RegExp(/^[0-9]*$/);
			var bool = match.test($("#seats").val());
			
			if (!bool){
				alert("Only numeric characters allowed");
			}
		});	
	});

	
	function goToCart(){
		//
		var newForm = jQuery('<form>', {
	        'action': 'ShoppingCart',
	        'method':'POST',
	    }).append(jQuery('<input>', {
	        'name': 'action',
	        'value': 'get',
	        'type': 'hidden'
	    }));
	    
	    newForm.submit();
	}
	
	
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
			<tr><td><button class='nav_button'  onclick="goToCart()">Shopping Cart</button></td></tr>	
		</table>
		
	</td>
	<td id=content valign="top" align="middle">
		<span id=welcome><h1>Welcome to FlightSearch.Com</h1></span>
		<div>
		<form action="FlightSearchQuery" method="POST">
			<table>
				<tr>
					<td>Source</td>
					<td><select id="source" name="source"></select>
					</td>
				</tr>
				<tr>
					<td>Destination</td>
					<td><select id="destination" name="destination"></select></td>
				</tr>
				<tr>
					<td>Departure Date</td>
					<td><input id="datepicker" type="text" name="date"></input></td>
				</tr>
				<tr>
					<td>Number of Seats</td>
					<td><input id="seats" type="text" name="seats"></input></td>
				</tr>
				<tr>
					<td>Class</td>
					<td>
					<select name="class">
						<option value="economy">Economy</option>
						<option value="business">Business</option>
						<option value="first class">First Class</option>
					</select>
					</td>
				</tr>
				<tr>
					<td colspan=2 style="text-align:center"><input type="submit" value="Search"/></td>
				</tr>
			</table>
			</form>	
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