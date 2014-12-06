<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Confirm Booking</title>
	<script src="Resources/JS/jquery-1.11.1.min.js"></script>
	<script src="Resources/JS/LoginAndRegistration"></script>
	<link href="Resources/main.css" rel="stylesheet" type="text/css">
	<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
	<c:if test="${empty sessionScope.client}">
		<c:redirect url="Login.jsp"></c:redirect>
	</c:if>
	<script>
	var globalFlightArray;
	function good(t){
		return !(t == null) && !(t.trim() == "");
	}
	function confirm_function(){
		var accountnumber = $("#accountnumber").val();
		var routingnumber = $("#routingnumber").val();
		var cost = $("#realcost").val();
		var pin = $("#pin").val();
		if(good(cost) && good(routingnumber) && good(accountnumber)){
		
		
		$.ajax
        ({
            type: "POST",
            url: '../../Team01-Banking/Bank;jsessionid=${pageContext.session.id}',
            dataType: 'json',
            data:{"accountnumber":accountnumber,
            	"routingnumber":routingnumber,
            	"cost":cost,
            	"pin":pin},
            success: function (data) {
        		if(!data.bSuccess){
        			alert(data.sMessage);
        		} else {
        			$("#succesessOut").html("<h2><b><span style='color:green'>Transaction Was Successful</span></b></h3>");
        			$("#print").show();
        			$(".passengers").show();
        			$("#confirm").hide();
        			update_history_function(accountnumber);
        		}
            },
            error: function(xhr, ajaxOptions, thrownError){
                context.ErrorOut("AJAX Error Please Try Again");
            }
        });
		} else {
			alert("Need all inputs");
		}
	}
	function updateHistoryAjax(accountnumber,flightid,seats,cost){
		$.ajax
        ({
            type: "POST",
            url: 'UpdateHistory;jsessionid=${pageContext.session.id}',
            dataType: 'json',
            data:{"accountnumber":accountnumber,
				 "flightid":flightid,
				 "seats":seats,
				 "cost":cost},
            success: function (data) {
        		if(!data.bSuccess){
        			alert(data.sMessage);
        		} 
            },
            error: function(xhr, ajaxOptions, thrownError){
                context.ErrorOut("AJAX Error Please Try Again");
            }
        });
	}
	function update_history_function(acc){
		//accountnumber,flightid,seats,cost
		globalFlightArray = JSON.parse(globalFlightArray);
		for (var i in globalFlightArray) {
			//id,operator,source,destination,departure,arrival,arrival,seats,cost
				  if (globalFlightArray.hasOwnProperty(i)) {
					  updateHistoryAjax(acc,globalFlightArray[i].id,globalFlightArray[i].seats, globalFlightArray[i].cost);
				  }
		}
		
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
		<span id=welcome><h1>Confirm Transaction</h1></span>
		<div id="succesessOut"></div>
		<div id="detailsOut">
			
		</div>
		<span id="messageOut"></span><br>
		<b><span style="color:green" id="totalCost"></span></b>
			<table>
			<tr>
					<td align='middle' colspan='2'>Payment Information</td>
				</tr>
				<tr>
					<td>Bank Routing Number</td>
					<td><input id="routingnumber" type="text"></td>
				</tr>
				<tr>
					<td>Account Number</td>
					<td><input id="accountnumber" type="text"></td>
				</tr>
				<tr>
					<td>PIN</td>
					<td><input id="pin" type="text"></td>
				</tr>
				<tr>
					<td colspan='2'style="text-align:center"><button  class="nav_button" id="confirm" onclick="javascript:confirm_function();">Confirm</button></td>
				</tr>
							<tr>
					<td colspan='2' style="text-align:center"><button  class="nav_button"  onclick="location.href='FlightSearchQuery.jsp;jsessionid=${pageContext.session.id}'">Cancel</button></td>
				</tr>
				 
			</table>
			
			<center><A id="print" HREF="javascript:window.print()">Click to Print This Page</A></center>
			<input type="hidden" id="realcost"/>
	</td>
</tr>
</table>
	<script>
	var Name = '<c:out value="${sessionScope.client.user.name}" />';
	var Organization = '<c:out value="${sessionScope.client.organization.name}" />';
		ShowUsername(Name,Organization);
	
var json = '<c:out value="${sessionScope.flights}"  escapeXml="false"/>';

function populatePage(flightArray){
	var bigWrapper = ""; 
	var total= 0;
	var count = 0;
	for (var i in flightArray) {
	//id,operator,source,destination,departure,arrival,arrival,seats,cost
		  if (flightArray.hasOwnProperty(i)) {
			    count++;
			    var html = "<div class='blackwrapper' style=''>";
			    html+="<p>Flight #"+ count+"</p>";
			    html+="<table class='confirmflightstables'><tr><th>Flight Number</th><th>Operator</th><th>Source</th><th>Destination</th><th>Departure Time</th><th>Arrival Time</th><th>Seats</th><th>Seat Type</th><th>Cost</th></tr>";
			    html+="<tr><td>"+flightArray[i].id+"</td>";
			    html+="<td>"+flightArray[i].operator+"</td>";
			    html+="<td>"+flightArray[i].source+"</td>";
			    html+="<td>"+flightArray[i].destination+"</td>";
			    html+="<td>"+flightArray[i].departure+"</td>";
			    html+="<td>"+flightArray[i].arrival+"</td>";
			    html+="<td>"+flightArray[i].seats+"</td>";
			    html+="<td>"+flightArray[i].seatType+"</td>";
			    html+="<td>"+flightArray[i].cost+"</td></tr>";
			    html+="</table>";
			    
			    html+="<table style='visibility:gone' class='passengers'>";
			    for(var j=0;j<parseInt(flightArray[i].seats);j++){
			    	html+="<tr><td>";
			    	html+="Passenger " + (j+1) +" Information: </td>";
			    	html+="<td >Name <input type='text'/></td>";
			    	html+="<td >Age <input type='text'/></td>";
			    	html+="<td >Sex <input type='text'/></td>";
			    	html+="</tr>";
			    }
			    html+="</table></div>";
			    bigWrapper +=html;
			    total+= parseInt(flightArray[i].cost);
		  }
	
		}
	$("#realcost").val(total);
	$("#totalCost").html("Your Total Cost: $"+total);
	$("#detailsOut").html(bigWrapper);
	$(".passengers").hide();
	$("#print").hide();
	
}

$( document ).ready(function() {
	if(json!=""){
		json = JSON.parse(json);
		}
	if(json!=""){
		var bSuccess = json.bSuccess;
		var sMessage = json.sMessage;
		var flightArray = json.flightArray;
		globalFlightArray = flightArray;
		if(bSuccess){
			flightArray = JSON.parse(flightArray);
			if(sMessage ==""){
				//all flights were available
				populatePage(flightArray);
			} else {
				//some flights weren't available
				$("#messageOut").html("The following flights with IDs: " + sMessage + " did not have available seats");
				populatePage(flightArray);
			}
		} else {
			$("#messageOut").html(sMessage);
			//failed, list out reason........
		}
	} //error internall........
});

</script>
</body>
</html>