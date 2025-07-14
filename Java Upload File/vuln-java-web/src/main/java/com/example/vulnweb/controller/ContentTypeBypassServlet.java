package com.example.vulnweb.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class ContentTypeBypassServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("content-type-upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
        String contentType = filePart.getContentType();

        // Kiểm tra Content-Type
        if (!"image/png".equals(contentType) && !"image/jpeg".equals(contentType)) {
            request.setAttribute("msg", "❌ Từ chối: Content-Type không hợp lệ → " + contentType);
            request.getRequestDispatcher("content-type-upload.jsp").forward(request, response);
            return;
        }

        // Tạo thư mục uploads nếu chưa có
        String uploadDir = getServletContext().getRealPath("/") + "uploads";
        File dir = new File(uploadDir);
        if (!dir.exists())
            dir.mkdirs();

        // Ghi file ra ổ đĩa
        File file = new File(uploadDir + File.separator + fileName);
        try (InputStream input = filePart.getInputStream();
                FileOutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        // Đọc nội dung file vừa lưu (tối đa 4KB)
        StringBuilder content = new StringBuilder(); // Tạo đối tượng StringBuilder để chứa nội dung file. 
        // lý do dùng StringBuilder thay vì dùng String để tối ưu hóa hiệu năng bộ nhớ và tốc độ xử lý trong Java 
        // String là immutable (bất biến) --> Tự động tạo đối tượng mới và bỏ cái cũ 
        // Đọc nội dung từ một file văn bản (file) và lưu nó vào StringBuilder content. 
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            char[] charBuffer = new char[4096];
            int charsRead = reader.read(charBuffer);
            if (charsRead > 0) {
                content.append(charBuffer, 0, charsRead);
            }
        }

        // Gửi thông tin lên JSP
        String link = "uploads/" + fileName;
        request.setAttribute("msg",
                "✅ File đã upload thành công: <a href='" + link + "' target='_blank'>" + fileName + "</a>");
        request.setAttribute("filename", fileName);
        request.setAttribute("fileSize", filePart.getSize());
        request.setAttribute("contentType", contentType);
        request.setAttribute("fileContent", content.toString());

        request.getRequestDispatcher("content-type-upload.jsp").forward(request, response);
    }

    // Hàm lấy tên file từ phần header Content-Disposition
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        // Content-Disposition: form-data; name="file"; filename="shell.jsp;.jpg"
        // contentDisp = "form-data; name=\"file\"; filename=\"shell.jsp\"";
        // Dấu / chỉ dùng để không làm rối cú pháp để escape dấu " --> java xử lý để hiển thị chuỗi theo cách an toàn 
        for (String piece : contentDisp.split(";")) { // tách chuỗi header thành từng phần nhỏ theo dấu ;
            if (piece.trim().startsWith("filename")) {
                return piece.substring(piece.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "unknown";
    }
}
