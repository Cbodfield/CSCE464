<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="Resources/JS/jquery-1.11.1.min.js"></script>
<script src="Resources/JS/LoginAndRegistration"></script>
<LINK href="Resources/main.css" rel="stylesheet" type="text/css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
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
if(userName != null) {
	response.sendRedirect("FlightSearchQuery.jsp");
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
					<a href="register.jsp;" class="" width="50%">Register</a>
					
				</div>
			</td></tr>
			
			<tr><td><hr/></td></tr>
			<tr><td><button onclick="location.href='FlightSearchQuery.jsp';" class="nav_button">Flight Search</button></td></tr>
			<tr><td><button onclick="location.href='BookingHistory.jsp';"  class="nav_button">Booking History</button></td></tr>
				
		</table>
		
	</td>
	<td id=content valign="top">
		
		<table>
		<tr>
		<td align="center" colspan=2>
		<span id=registration><h1>Registration</h1></span>
		</td>
		</tr>
			<tr>
				<td align="right">
				First name:
				</td>
				<td>
				<input type="text" id="firstname"/>
				</td>
			</tr>
			<tr>
				<td align="right">
				Last name:
				</td>
				<td>
				<input type="text" id="lastname"/>
				</td>
			</tr>
			<tr>
			<td colspan=2>
			<hr>
			</td>
			</tr>
			<tr>
				<td align="right">
				Username:
				</td>
				<td>
				<input type="text" id="1username"/>
				</td>
			</tr>
			<tr>
				<td align="right">
				Retype Username:
				</td>
				<td>
				<input type="text" id="dusername"/>
				</td>
			</tr>
			<tr>
			<td colspan=2>
			<hr>
			</td>
			</tr>
			<tr>
				<td align="right">
				Password:
				</td>
				<td>
				<input type="text" id="1password"/>
				</td>
			</tr>
			<tr>
				<td align="right">
				Retype Password:
				</td>
				<td>
				<input type="text" id="dpassword"/>
				</td>
			</tr>
			<tr>
				<td colspan=2 align="right">
					<button onclick="javascript:register()">Register</button>
				</td>
			</tr>
			
		</table>
	</td>
</tr>
</table>
</body>
</html>