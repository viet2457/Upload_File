package com.example.vulnweb.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class ContentInspectionBypassServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khi người dùng truy cập servlet bằng GET, redirect về trang JSP
        response.sendRedirect("content-inspection-bypass.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String contentType = filePart.getContentType();
        String contentDisposition = filePart.getHeader("content-disposition");

        // Tạo thư mục uploads nếu chưa tồn tại
        String uploadPath = getServletContext().getRealPath("/") + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();

        // Đọc nội dung file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(filePart.getInputStream()))) {
            char[] buffer = new char[4096];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                content.append(buffer, 0, len);
            }
        }

        // Kiểm tra nội dung nguy hiểm
        String data = content.toString().toLowerCase();
        // if (data.contains("<%") || data.contains("runtime") || data.contains("script")) {
        if (data.contains("runtime") || data.contains("script")) {
            response.sendRedirect("content-inspection-bypass.jsp?msg=fail");
            return;
        }
        // Bypass file test.jsp --> có thể RCE 
        // Lưu file nếu hợp lệ
        File uploadedFile = new File(uploadDir, fileName);
        try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
            fos.write(content.toString().getBytes());
        }

        // Redirect về giao diện kèm thông tin file
        response.sendRedirect("content-inspection-bypass.jsp?msg=success"
                + "&file=" + URLEncoder.encode(fileName, "UTF-8")
                + "&type=" + URLEncoder.encode(contentType, "UTF-8")
                + "&disp=" + URLEncoder.encode(contentDisposition, "UTF-8"));
    }
}
