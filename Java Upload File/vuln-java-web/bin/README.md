package com.example.vulnweb.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uploadPath = getServletContext().getRealPath("/") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File uploadedFile = new File(filePath);
                    item.write(uploadedFile);

                    // File details
                    request.setAttribute("fileName", fileName);
                    request.setAttribute("filePath", "uploads/" + fileName);
                    request.setAttribute("extension", fileName.substring(fileName.lastIndexOf('.') + 1));
                    request.setAttribute("size", uploadedFile.length());

                    // Read content (UTF-8)
                    StringBuilder content = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(new FileInputStream(uploadedFile), "UTF-8"))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                    } catch (Exception e) {
                        content.append("❌ Không thể đọc nội dung: ").append(e.getMessage());
                    }
                    request.setAttribute("content", content.toString());

                    break;
                }
            }

        } catch (Exception e) {
            request.setAttribute("error", "❌ Upload failed: " + e.getMessage());
        }

        // 🔽 Thêm: Danh sách file đã upload
        File[] fileList = new File(uploadPath).listFiles();
        List<Map<String, Object>> uploadedFiles = new ArrayList<>();

        if (fileList != null) {
            for (File file : fileList) {
                Map<String, Object> fileInfo = new HashMap<>();
                fileInfo.put("name", file.getName());
                fileInfo.put("size", file.length());
                fileInfo.put("url", "uploads/" + file.getName());
                uploadedFiles.add(fileInfo);
            }
        }

        request.setAttribute("uploadedFiles", uploadedFiles);

        // Quay về upload.jsp
        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }
}
