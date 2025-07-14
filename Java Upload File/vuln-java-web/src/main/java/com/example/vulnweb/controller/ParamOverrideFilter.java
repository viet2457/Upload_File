package com.example.vulnweb.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@WebFilter("/uploads/*")
public class ParamOverrideFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public String getParameter(String name) {
                if ("cmd".equals(name)) {
                    String raw = super.getParameter(name);
                    if (raw == null)
                        return null;

                    try {
                        System.out.println("⚠️ CMD = " + raw);
                        System.out.println("📏 Byte length = " + raw.getBytes("UTF-8").length);
                        // ✅ Bước 1: Replace ../ trước tiên (tránh xử lý sau khi append/cut)
                        // raw = raw.replaceAll("\\.\\./", "");

                        // // ✅ Bước 2: Append .txt trước khi cắt
                        // if (!raw.endsWith(".txt")) {
                        //     raw += ".txt";
                        // }

                        // ✅ Bước 3: In ra chuỗi sau khi append
                        System.out.println("📎 After append .txt = " + raw);
                        System.out.println("📏 Byte length = " + raw.getBytes("UTF-8").length);

                        // ✅ Bước 4: Giới hạn 1024 bytes
                        // byte[] cmdBytes = raw.getBytes("UTF-8");
                        // if (cmdBytes.length > 1024) {
                        //     int byteCount = 0, charIndex = 0;
                        //     while (charIndex < raw.length() && byteCount < 1024) {
                        //         byteCount += String.valueOf(raw.charAt(charIndex)).getBytes("UTF-8").length;
                        //         if (byteCount <= 1024)
                        //             charIndex++;
                        //     }
                        //     raw = raw.substring(0, charIndex);
                        //     System.out.println("✂️ After cut: " + raw);
                        //     System.out.println("📏 Length After Trim = " + raw.getBytes("UTF-8").length);
                        // }

                        // ✅ Bước 5: Nếu vẫn chứa .. thì chặn
                        // if (raw.contains("..")) {
                        //     return "echo ❌ Blocking because contain ../";
                        // }

                        // // ✅ Bước 6: Ép thành đường dẫn uploads/
                        // String filePath = "uploads/" + raw;

                        // // ✅ Bước 7: Kiểm tra file tồn tại
                        // java.io.File f = new java.io.File(filePath);
                        // if (!f.exists()) {
                        //     return "echo ❌ File not found: " + filePath;
                        // }

                        // // ✅ Bước 8: Cho phép thực thi
                        // return "cat " + filePath;

                    } catch (UnsupportedEncodingException e) {
                        return "echo ❌ Error encoding UTF-8!";
                    }
                }

                return super.getParameter(name);
            }
        };

        // ✅ Cho phép request tiếp tục đến JSP sau khi override parameter
        chain.doFilter(wrappedRequest, response);
    }
}
