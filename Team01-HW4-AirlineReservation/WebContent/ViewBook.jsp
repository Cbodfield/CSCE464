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
	<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
	<c:if test="${empty sessionScope.client}">
		<c:redirect url="Login.jsp"></c:redirect>
	</c:if>
</head>
<script>
	function selectFlight(){
		var cost = $("#cost").html();
		cost = cost.replace("$","");
		var seats = $("#seatcount").val();
		var flightid=$("#flightnumber").html();
		var c = $("#class").val();
		var stops = $("#stops").html();
		var newForm = jQuery('<form>', {
	        'action': 'ViewAndBook;jsessionid=${pageContext.session.id}',
	        'method':'POST',
	    }).append(jQuery('<input>', {
	        'name': 'flightid',
	        'value': flightid,
	        'type': 'hidden'
	    })).append(jQuery('<input>', {
	        'name': 'class',
	        'value': c,
	        'type': 'hidden'
	    })).append(jQuery('<input>', {
	        'name': 'cost',
	        'value': cost,
	        'type': 'hidden'
	    })).append(jQuery('<input>', {
	        'name': 'stops',
	        'value': stops,
	        'type': 'hidden'
	    })).append(jQuery('<input>', {
	        'name': 'seats',
	        'value': seats,
	        'type': 'hidden'
	    }));
	    
	    newForm.submit();
	}
	
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
	
	function addToCart(){
		$.post('ShoppingCart;jsessionid=${pageContext.session.id}',
				{"action":"add",
				 "flightID":$("#flightnumber").html(), 
				 "seats":$("#seatcount").val(), 
				 "cost":$("#cost").html(),
				 "class":$("#class").val()},
				function(data) { 
	                       if(data.indexOf("good")>-1){
	                    	   //it was added
	                    	   alert("Successfully added to shopping cart");
	                       } else {
	           					alert(data);
	                       }
	                                
	                    });
	}
</script>
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
						<select id="seatcount">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="9">9</option>
							<option value="10">10</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Class</td>
					<td>
						<select id="class" name="class">
						<option value="economy">Economy</option>
						<option value="business">Business</option>
						<option value="first">First Class</option>
					</select>
					</td>
				</tr>
				<tr>
					<td colspan='2'style="text-align:center">
					<button class='nav_button'  onclick="goToCart()">Check Out</button>
					</td>
				</tr>
				<tr>
					<td colspan='2' style="text-align:center">
					<button class='nav_button'  onclick="addToCart()">Add to Cart</button>
					</td>
				</tr>
				<tr>
					<td colspan='2' style="text-align:center">
					<button class='nav_button'  onclick="javascript:window.history.back()">Back to search results</button>
					</td>
				</tr>
				<tr>
					<td colspan='2' style="text-align:center">
					<button class='nav_button'  onclick="location.href='FlightSearchQuery.jsp;jsessionid=${pageContext.session.id}'">Search for new flights</button>
					</td>
				</tr>
			</table>
		
		</div>
	</td>
</tr>
</table>
	<script>
	var Name = '<c:out value="${sessionScope.client.user.name}" />';
	var Organization = '<c:out value="${sessionScope.client.organization.name}" />';
		ShowUsername(Name,Organization);
	
var details = '<c:out value="${sessionScope.details}"  escapeXml="false"/>';
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
