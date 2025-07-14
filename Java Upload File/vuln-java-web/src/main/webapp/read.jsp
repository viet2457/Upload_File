<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<html>
<head>
    <title>🗂️ Xem nội dung file</title>
    <style>
        body {
            background-color: #121212;
            color: #f1f1f1;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            padding: 20px;
        }

        input[type="text"] {
            width: 500px;
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #444;
            background-color: #1e1e1e;
            color: #fff;
        }

        input[type="submit"] {
            padding: 8px 16px;
            background-color: #4fc3f7;
            border: none;
            border-radius: 4px;
            color: #000;
            cursor: pointer;
        }

        pre {
            background-color: #1e1e1e;
            padding: 10px;
            border-radius: 5px;
            overflow-x: auto;
            white-space: pre-wrap;
            word-wrap: break-word;
            border: 1px solid #555;
        }

        .error {
            color: #ff5252;
            font-weight: bold;
        }
    </style>
</head>

<body>
    <h2>🔍 Nhập tên file để xem nội dung</h2>
    <form method="get" action="read">
        File name: 
        <input type="text" name="filename"
               value="<%= request.getAttribute("filename") != null ? request.getAttribute("filename") : "" %>"/>
        <input type="submit" value="Xem"/>
    </form>
    <hr/>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error">❌ <%= request.getAttribute("error") %></p>
    <% } else if (request.getAttribute("content") != null) { %>
        <h3>📄 Nội dung file:</h3>
        <pre><%= StringEscapeUtils.escapeHtml4((String) request.getAttribute("content")) %></pre>
    <% } %>
</body>
</html>
