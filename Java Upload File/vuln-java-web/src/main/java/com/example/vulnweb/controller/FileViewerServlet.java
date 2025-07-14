package com.example.vulnweb.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileViewerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filename = request.getParameter("filename");

        if (filename != null && !filename.trim().isEmpty()) {
            // Kiểm tra tên file không hợp lệ
           
                // Lấy đường dẫn vật lý đến thư mục document trong servlet context
                String relativePath = "/WEB-INF/classes/document/" + filename;
                String absolutePath = getServletContext().getRealPath(relativePath);

                try {
                    // String content = readFileContentFromPath(absolutePath);
                    // String content = readFileContentFromPath("/WEB-INF/classes/document/"+"../../../../../../../../../../etc/passwd");
                    String content = readFileContentFromPath("/WEB-INF/classes/document/../../../../../../../../../../etc/passwd");
                    // String content = readFileContentFromPath("/etc/hosts");
                    request.setAttribute("content", content);
                } catch (IOException e) {
                    request.setAttribute("error", "❌ " + e.getMessage());
                    e.printStackTrace();
                }
                request.setAttribute("filename", filename);
            
        } else {
            request.setAttribute("error", "❌ Vui lòng cung cấp tên file.");
        }

        // Forward tới JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/read.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Đọc nội dung file từ đường dẫn tuyệt đối.
     */
    public static String readFileContentFromPath(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("File không tồn tại: " + filePath);
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }

    /**
     * Hàm main để test đọc file ngoài servlet.
     * Ví dụ: java FileViewerServlet src/main/document/a.txt
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("❗ Vui lòng truyền đường dẫn file làm tham số.");
            return;
        }

        try {
            String content = readFileContentFromPath("src/main/document/a.txt");
            System.out.println("📄 Nội dung file:\n" + content);
        } catch (IOException e) {
            System.out.println("❌ " + e.getMessage());
            e.printStackTrace();
        }
    }
}
