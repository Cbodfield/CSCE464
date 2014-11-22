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

userName = (String)session.getAttribute("user");

if(userName != null) {
	//We are logged in, lets get them home!
	response.sendRedirect("FlightSearchQuery.jsp");
}
Cookie cookie = null;
Cookie[] cookies = null;
// Get an array of Cookies associated with this domain
cookies = request.getCookies();

String email ="";
String password="";
if( cookies != null ){
   for (int i = 0; i < cookies.length; i++){
          cookie = cookies[i];
          if(cookie.getName().equalsIgnoreCase("email")){
        	  email=cookie.getValue();
          }
          if(cookie.getName().equalsIgnoreCase("password")){
        	  password=cookie.getValue();
          }
   }
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
					<br><br>
					<input type="checkbox" id="remember"/>Remember Me<br><br>
					<a href="javascript:login();" class="" width="50%">Login</a>
					<a href="Register.jsp;" class="" width="50%">Register</a><br>
					
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
				<input type="password" id="1password"/>
				</td>
			</tr>
			<tr>
				<td align="right">
				Retype Password:
				</td>
				<td>
				<input type="password" id="dpassword"/>
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
<script>
var UserName= "<%=userName %>"
ShowUsername(UserName);
var email= "<%=email %>"
var password= "<%=password %>"
if(password !="" &&email !=""){
	$("#remember").click();
	$("#username").val(email);
	$("#password").val(password);
}
</script>
</body>
</html>