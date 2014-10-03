<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Flight Search</title>
	<script src="Resources/JS/jquery-1.11.1.min.js"></script>
	<script src="Resources/JS/LoginAndRegistration"></script>
	<link href="Resources/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
String userName = null;
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
    if(cookie.getName().equals("user")) {
    	userName = cookie.getValue();
    }
}
}
if(userName == null) {
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
			<table>
				<tr>
					<td>Source</td>
					<td><input type="text"></input></td>
				</tr>
				<tr>
					<td>Destination</td>
					<td><input type="text"></input></td>
				</tr>
				<tr>
					<td>Travel Date</td>
					<td><input type="text"></input></td>
				</tr>
				<tr>
					<td>Number of Seats</td>
					<td><input type="text"></input></td>
				</tr>
				<tr>
					<td>Class</td>
					<td><select name="class">
						<option value="economy">Economy</option>
						<option value="economy">Business</option>
						<option value="economy">First Class</option>
					</select></td>
				</tr>
				<tr>
					<td colspan=2 style="text-align:center"><button onclick="location.href='${pageContext.request.contextPath}/FlightSearchResults.jsp'">Search</button></td>
				</tr>
			</table>	
		</div>
	</td>
</tr>
</table>
	<script>
var UserName= "<%=userName %>"
	ShowUsername(UserName);
</script>
</body>
</html>