<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Content-Type Upload – Dark Mode</title>
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
            font-family: "Courier New", monospace;
            padding: 40px;
        }

        h2 {
            color: #00ffff;
        }

        label {
            font-weight: bold;
            color: #90caf9;
        }

        input[type="file"],
        input[type="submit"] {
            background-color: #1e1e1e;
            color: #ffffff;
            border: 1px solid #333;
            padding: 10px;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background-color: #333;
            cursor: pointer;
        }

        .msg {
            margin-top: 20px;
            padding: 15px;
            background-color: #263238;
            border-left: 4px solid #00e676;
            color: #a5d6a7;
        }

        .error {
            border-left-color: #ff5252;
            color: #ef9a9a;
        }

        code {
            background-color: #1e1e1e;
            padding: 2px 5px;
            border-radius: 4px;
            color: #ffcc80;
        }

        pre {
            background-color: #1e1e1e;
            padding: 10px;
            border-radius: 5px;
            overflow-x: auto;
            color: #cfcfcf;
        }

        hr {
            border-color: #444;
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <h2>🧪 Content-Type Upload Test – Dark Mode</h2>
    <p>⚠️ Chỉ cho phép upload file có <code>Content-Type</code> là <code>image/png</code> hoặc <code>image/jpeg</code>.<br>
    Không kiểm tra đuôi hoặc nội dung thật của file!</p>

    <form action="content-type-upload" method="post" enctype="multipart/form-data">
        <label>Chọn file để upload:</label><br>
        <input type="file" name="file" /><br><br>
        <input type="submit" value="Upload" />
    </form>

    <hr>

    <%
        String msg = (String) request.getAttribute("msg");
        if (msg != null) {
            boolean isError = msg.contains("❌");
    %>
        <div class="msg <%= isError ? "error" : "" %>">
            <%= msg %>
        </div>
    <%
        }

        String filename = (String) request.getAttribute("filename");
        String contentType = (String) request.getAttribute("contentType");
        Object sizeObj = request.getAttribute("fileSize");
        String fileContent = (String) request.getAttribute("fileContent");

        if (filename != null && contentType != null && sizeObj != null) {
            long fileSize = (sizeObj instanceof Long) ? (Long) sizeObj : Long.parseLong(sizeObj.toString());
    %>
        <div class="msg">
            <strong>📄 Tên file:</strong> <code><%= filename %></code><br>
            <strong>🧾 Content-Type:</strong> <code><%= contentType %></code><br>
            <strong>📦 Kích thước:</strong> <code><%= fileSize %> bytes</code><br>
            <strong>🔗 Đường dẫn:</strong> <a href="uploads/<%= filename %>" target="_blank">Mở file</a><br><br>

            <strong>📂 Nội dung file (xem trước):</strong><br>
            <pre><%= (fileContent != null && !fileContent.isEmpty()) ? fileContent : "(Không đọc được nội dung hoặc không phải text)" %></pre>
        </div>
    <%
        }
    %>
</body>
</html>
