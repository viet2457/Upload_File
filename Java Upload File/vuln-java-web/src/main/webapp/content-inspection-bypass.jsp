<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Content Inspection Bypass Upload</title>
    <style>
        body {
            background-color: #1e1e1e;
            color: #cccccc;
            font-family: Consolas, monospace;
            padding: 40px;
        }
        h1 {
            color: #00e6e6;
        }
        input[type="file"] {
            background-color: #2e2e2e;
            color: #ffffff;
            border: 1px solid #555;
            padding: 6px;
        }
        input[type="submit"] {
            background-color: #00e6e6;
            color: black;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            font-weight: bold;
        }
        input[type="submit"]:hover {
            background-color: #00cccc;
        }
        .msg {
            margin-top: 20px;
            font-weight: bold;
        }
        .success {
            color: #00ff99;
        }
        .fail {
            color: #ff4444;
        }
        a {
            color: #00e6e6;
        }
        pre {
            background-color: #2a2a2a;
            color: #00ffcc;
            padding: 10px;
            border-left: 4px solid #00e6e6;
            overflow-x: auto;
        }
    </style>
</head>
<body>
    <h1>🧪 Content Inspection Bypass Upload</h1>
    <form action="content-inspection-bypass" method="POST" enctype="multipart/form-data">
        <input type="file" name="file" required /><br><br>
        <input type="submit" value="Upload" />
    </form>

    <%
        String msg = request.getParameter("msg");
        String file = request.getParameter("file");
        String type = request.getParameter("type");
        String disp = request.getParameter("disp");

        if ("success".equals(msg) && file != null) {
    %>
        <div class="msg success">✅ Upload thành công!</div>
        <p><strong>📄 Tên file:</strong> <%= file %></p>
        <p><strong>📦 Loại file (Content-Type):</strong> <%= type %></p>
        <p><strong>📝 Content-Disposition:</strong> <%= disp %></p>
        <p><strong>🔗 Xem file:</strong> <a href="uploads/<%= file %>" target="_blank">uploads/<%= file %></a></p>
    <%
        } else if ("fail".equals(msg)) {
    %>
        <div class="msg fail">❌ Từ chối upload: Phát hiện nội dung nguy hiểm.</div>
    <%
        }
    %>
</body>
</html>
