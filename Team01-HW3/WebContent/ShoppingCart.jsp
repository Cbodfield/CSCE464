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
	<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
	<c:if test="${empty sessionScope.client}">
		<c:redirect url="Login.jsp"></c:redirect>
	</c:if>
	<script>
	function goToCart(){
		//
		var newForm = jQuery('<form>', {
	        'action': 'ShoppingCart;jsessionid=${pageContext.session.id}',
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
			<tr><td><button onclick="location.href='FlightSearchQuery.jsp;jsessionid=${pageContext.session.id}';" class="nav_button">Flight Search</button></td></tr>
			<tr><td><button onclick="location.href='BookingHistory.jsp;jsessionid=${pageContext.session.id}';"  class="nav_button">Booking History</button></td></tr>
			<tr><td><button class='nav_button'  onclick="goToCart()">Shopping Cart</button></td></tr>
		</table>
		
	</td>
	<td id=content valign="top" align="middle">
		<span id=welcome><h1>Your shopping cart</h1></span><span id="totalcost"></span>
		<div id="htmlout">
			
		</div>
		<table>
		<tr>
			<td colspan='2'style="text-align:center">
				<button id="btnCheckOut" class='nav_button'  onclick="checkOut()">Check Out</button>
			</td>
		</tr>
				
			<tr>
				<td colspan='2' style="text-align:center">
					<button class='nav_button'  onclick="location.href='FlightSearchQuery.jsp;jsessionid=${pageContext.session.id}'">Continue Shopping</button>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
	<script>
	var Name = '<c:out value="${sessionScope.client.user.name}" />';
	var Organization = '<c:out value="${sessionScope.client.organization.name}" />';
		ShowUsername(Name,Organization);
	
var json = '<c:out value="${sessionScope.flights}" escapeXml="false"/>';
var originalJSON = json;
if(json != ""){
	json=JSON.parse(json);
}
$( document ).ready(function() {
	if(json !=""){
	var html = "<table class='resultsTable'><tr><th>Flight Number</th><th>Operator</th><th>Source</th><th>Destination</th><th>Departure Time</th><th>Arrival Time</th><th>Seats</th><th>Seat Type</th><th>Cost</th></tr>";
	var total= 0;
	
	for (var i in json) {
	//id,operator,source,destination,departure,arrival,arrival,seats,cost
		  if (json.hasOwnProperty(i)) {
			    //alert(key + " -> " + JSONFlights[key]);
			    html+="<tr><td>"+json[i].id+"</td>";
			    html+="<td>"+json[i].operator+"</td>";
			    html+="<td>"+json[i].source+"</td>";
			    html+="<td>"+json[i].destination+"</td>";
			    html+="<td>"+json[i].departure+"</td>";
			    html+="<td>"+json[i].arrival+"</td>";
			    html+="<td>"+json[i].seats+"</td>";
			    html+="<td>"+json[i].seatType+"</td>";
			    html+="<td>"+json[i].cost+"</td></tr>";
			    total+= parseInt(json[i].cost);
		  }
	
		}
	html+="</table>";
	$("#htmlout").html(html);
	$("#totalcost").html("Total Cost: " + String(total));
	} else {
		$("#btnCheckOut").hide();
		$("#totalcost").html("No flights have been added to your cart");
	}
	
	
});

function checkOut(){
	//
	var newForm = jQuery('<form>', {
        'action': 'ViewAndBook;jsessionid=${pageContext.session.id}',
        'method':'POST',
    }).append(jQuery('<input>', {
        'name': 'flightInfo',
        'value': originalJSON,
        'type': 'hidden'
    }));
    
    newForm.submit();
}
</script>
</body>
</html>