<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload File - Dark Mode</title>
    <style>
        body {
            background-color: #121212;
            color: #f1f1f1;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 800px;
            margin: auto;
            background-color: #1e1e1e;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(255, 255, 255, 0.05);
        }

        h2 {
            color: #4fc3f7;
        }

        input[type="file"],
        input[type="submit"] {
            margin-top: 15px;
            padding: 10px;
            width: 100%;
            border-radius: 6px;
            border: none;
            font-size: 15px;
        }

        input[type="file"] {
            background-color: #2e2e2e;
            color: #f1f1f1;
            border: 1px solid #555;
        }

        input[type="submit"] {
            background-color: #2196f3;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #1976d2;
        }

        .result, .file-list {
            margin-top: 30px;
            background-color: #2a2a2a;
            padding: 15px;
            border-radius: 8px;
        }

        .file-list ul {
            list-style: none;
            padding-left: 0;
        }

        .file-list a {
            color: #4fc3f7;
            text-decoration: none;
        }

        .file-list a:hover {
            text-decoration: underline;
        }

        pre {
            background-color: #111;
            padding: 10px;
            overflow-x: auto;
            color: #c7f1ff;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>📁 Upload File</h2>
        <form action="upload" method="post" enctype="multipart/form-data">
            <input type="file" name="file" required>
            <input type="submit" value="Upload">
        </form>

        <%
            String fileName = (String) request.getAttribute("fileName");
            String filePath = (String) request.getAttribute("filePath");
            String extension = (String) request.getAttribute("extension");
            Long size = (Long) request.getAttribute("size");
            String content = (String) request.getAttribute("content");
            String error = (String) request.getAttribute("error");
        %>

        <% if (fileName != null) { %>
            <div class="result">
                <h3>✅ File đã upload:</h3>
                <p><strong>Tên:</strong> <%= fileName %></p>
                <p><strong>Đường dẫn:</strong> <a href="<%= filePath %>" target="_blank"><%= filePath %></a></p>
                <p><strong>Kích thước:</strong> <%= size %> bytes</p>
                <p><strong>Đuôi file:</strong> <%= extension %></p>
                <% if (content != null) { %>
                    <h4>Nội dung file:</h4>
                    <pre><%= content %></pre>
                <% } %>
            </div>
        <% } else if (error != null) { %>
            <div class="result" style="color: #f44336;">
                <strong>❌ Lỗi:</strong> <%= error %>
            </div>
        <% } %>

        <%-- Danh sách file đã upload --%>
        <div class="file-list">
            <h3>📂 Danh sách file đã upload:</h3>
            <ul>
                <%
                    List<Map<String, Object>> files = (List<Map<String, Object>>) request.getAttribute("uploadedFiles");
                    if (files != null && !files.isEmpty()) {
                        for (Map<String, Object> f : files) {
                %>
                    <li>
                        <a href="<%= f.get("url") %>" target="_blank"><%= f.get("name") %></a> (<%= f.get("size") %> bytes)
                    </li>
                <%
                        }
                    } else {
                %>
                    <li><em>Không có file nào được upload.</em></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</body>
</html>
