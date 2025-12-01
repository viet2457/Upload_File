# Nghiên cứu & Minh họa Các Lỗ hổng Bảo mật Ứng dụng Web và Phương pháp Phòng thủ

## Giới thiệu
Dự án này là một ứng dụng web Java (JSP/Servlet) được xây dựng để nghiên cứu bảo mật. Nó mô phỏng các lỗ hổng bảo mật phổ biến liên quan đến tính năng upload file trong các ứng dụng web, cho phép người dùng thực hành các kỹ thuật khai thác và tìm hiểu cách phòng chống.

## Cấu trúc Dự án
Thư mục chính `vuln-java-web` chứa mã nguồn của dự án Maven.

Các thành phần chính trong `src/main/java/com/example/vulnweb/controller`:
- **UploadServlet.java**: Servlet xử lý upload cơ bản.
- **ContentTypeBypassServlet.java**: Minh họa lỗ hổng khi chỉ kiểm tra `Content-Type` mà không kiểm tra kỹ nội dung hoặc đuôi file.
- **ContentInspectionBypassServlet.java**: Minh họa việc bypass các cơ chế kiểm tra nội dung file.
- **FileViewerServlet.java**: Servlet hỗ trợ xem file (có thể kết hợp để khai thác).

## Các Kỹ thuật & Lỗ hổng
Dựa trên tài liệu nội bộ, dự án hỗ trợ thực hành các kỹ thuật:
- **Extension Bypass**: Sử dụng các đuôi file thay thế hoặc kỹ thuật đặt tên đặc biệt (ví dụ: `shell.jsp;.jpg`, `shell.jsp%00.jpg`).
- **Content-Type Bypass**: Thay đổi Header Content-Type để đánh lừa bộ lọc.
- **Null Byte Injection**: Kỹ thuật chèn ký tự null để ngắt chuỗi tên file (đặc biệt trên các phiên bản Java cũ hoặc cấu hình sai).
- **RCE (Remote Code Execution)**: Tải lên Web Shell (JSP) để thực thi mã từ xa.

## Công nghệ Sử dụng
- **Ngôn ngữ**: Java 8
- **Framework/Library**:
  - Java Servlet API 4.0.1
  - Apache Commons FileUpload 1.4
  - Apache Commons IO 2.11.0
- **Build Tool**: Maven

## Hướng dẫn Cài đặt & Chạy

### Yêu cầu
- Java JDK 8 trở lên
- Apache Maven
- Apache Tomcat (hoặc Web Server hỗ trợ Servlet/JSP tương đương)

### Các bước Build
1. Mở terminal tại thư mục `vuln-java-web`.
2. Chạy lệnh Maven để đóng gói dự án:
   ```bash
   mvn clean package
   ```
3. Sau khi build thành công, file `.war` sẽ được tạo ra trong thư mục `target` (ví dụ: `ROOT.war` hoặc `vuln-java-web-1.0.0.war`).

### Triển khai (Deploy)
1. Copy file `.war` vừa tạo vào thư mục `webapps` của Apache Tomcat.
2. Khởi động Tomcat (`bin/startup.bat` hoặc `bin/startup.sh`).
3. Truy cập ứng dụng qua trình duyệt (thường là `http://localhost:8080`).

## Tài liệu tham khảo
Xem thêm chi tiết về các payload và kỹ thuật khai thác trong file `vuln-java-web/README.md`.
