package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class FlightSearchResults_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("\t<title>Search Results</title>\r\n");
      out.write("\t<script src=\"Resources/JS/jquery-1.11.1.min.js\"></script>\r\n");
      out.write("\t<script src=\"Resources/JS/LoginAndRegistration\"></script>\r\n");
      out.write("\t<link href=\"Resources/main.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");

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

      out.write("\r\n");
      out.write("<table id=global_table border=0>\r\n");
      out.write("<tr>\r\n");
      out.write("\t<td id=navigation>\r\n");
      out.write("\t\t<table border=0 class=navtable width=\"100%\">\r\n");
      out.write("\t\t\t<tr><td><div id=date></div></td></tr>\r\n");
      out.write("\t\t\t<tr><td><div id=time></div></td></tr>\r\n");
      out.write("\t\t\t<tr><td><hr/></td></tr>\r\n");
      out.write("\t\t\t<tr><td>\r\n");
      out.write("\t\t\t\t<div id=login_username>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<input width=\"100%\" type=\"text\" placeholder=\"Username\" id=\"username\"/>\r\n");
      out.write("\t\t\t\t\t<input width=\"100%\" type=\"password\" placeholder=\"Password\" id=\"password\"/>\t\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<a href=\"javascript:login();\" class=\"\" width=\"50%\">Login</a>\r\n");
      out.write("\t\t\t\t\t<a href=\"Register.jsp;\" class=\"\" width=\"50%\">Register</a>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</td></tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr><td><hr/></td></tr>\r\n");
      out.write("\t\t\t<tr><td><button onclick=\"location.href='FlightSearchQuery.jsp';\" class=\"nav_button\">Flight Search</button></td></tr>\r\n");
      out.write("\t\t\t<tr><td><button onclick=\"location.href='BookingHistory.jsp';\"  class=\"nav_button\">Booking History</button></td></tr>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t\r\n");
      out.write("\t</td>\r\n");
      out.write("\t<td id=content valign=\"top\" align=\"middle\">\r\n");
      out.write("\t\t<span id=welcome><h1>Welcome to FlightSearch.Com</h1></span>\r\n");
      out.write("\t\t<div>\r\n");
      out.write("\t\t\t<table class=\"bottomBorder\">\r\n");
      out.write("\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>Flight Number</td>\r\n");
      out.write("\t\t\t\t\t\t<td>Flight Date</td>\r\n");
      out.write("\t\t\t\t\t\t<td>Departure Time</td>\r\n");
      out.write("\t\t\t\t\t\t<td>Arrival Time</td>\r\n");
      out.write("\t\t\t\t\t\t<td>Number of Stops</td>\r\n");
      out.write("\t\t\t\t\t\t<td>Cost</td>\r\n");
      out.write("\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>A123BS</td>\r\n");
      out.write("\t\t\t\t\t<td>08/19/2014</td>\r\n");
      out.write("\t\t\t\t\t<td>13:00</td>\r\n");
      out.write("\t\t\t\t\t<td>15:30</td>\r\n");
      out.write("\t\t\t\t\t<td>1</td>\r\n");
      out.write("\t\t\t\t\t<td>$200.00</td>\r\n");
      out.write("\t\t\t\t\t<td><button onclick=\"location.href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/ViewBook.jsp'\" >View and Book</button></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>BINA9865</td>\r\n");
      out.write("\t\t\t\t\t<td>01/13/2015</td>\r\n");
      out.write("\t\t\t\t\t<td>19:00</td>\r\n");
      out.write("\t\t\t\t\t<td>22:30</td>\r\n");
      out.write("\t\t\t\t\t<td>1</td>\r\n");
      out.write("\t\t\t\t\t<td>$150.00</td>\r\n");
      out.write("\t\t\t\t\t<td><button onclick=\"location.href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/ViewBook.jsp'\">View and Book</button></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>98GA689A1</td>\r\n");
      out.write("\t\t\t\t\t<td>03/14/2015</td>\r\n");
      out.write("\t\t\t\t\t<td>08:00</td>\r\n");
      out.write("\t\t\t\t\t<td>14:45</td>\r\n");
      out.write("\t\t\t\t\t<td>2</td>\r\n");
      out.write("\t\t\t\t\t<td>$457.13</td>\r\n");
      out.write("\t\t\t\t\t<td><button onclick=\"location.href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/ViewBook.jsp'\">View and Book</button></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</td>\r\n");
      out.write("</tr>\r\n");
      out.write("</table>\r\n");
      out.write("\t<script>\r\n");
      out.write("var UserName= \"");
      out.print(userName );
      out.write("\"\r\n");
      out.write("\tShowUsername(UserName);\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
