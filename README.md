# 8-Puzzle Solver - AI Search Algorithm

Dự án trực quan hóa thuật toán **A* (A-Star)** để giải bài toán sắp xếp 8 ô số. Sử dụng **Manhattan Distance** làm hàm Heuristic để tối ưu hóa đường đi.

## Tính năng
- **Thuật toán A*:** Tìm kiếm lời giải tối ưu với số bước di chuyển ít nhất.
- **Heuristic:** Áp dụng khoảng cách Manhattan để giảm không gian trạng thái cần duyệt.
- **Visualizer:** Trực quan hóa từng bước dịch chuyển của ô trống trên giao diện web.
- **Responsive UI:** Giao diện đơn giản, hiện đại, hỗ trợ khóa nút (disable) khi đang chạy.
- **Tối ưu UX:**
  - Dự án được chú trọng đặc biệt vào việc xử lý các tình huống thực tế nhằm đảm bảo tính ổn định và mượt mà:
  - Data Sanitization (Làm sạch dữ liệu): Sử dụng cơ chế trim() và parseInt() để xử lý dữ liệu đầu vào. Điều này giúp loại bỏ khoảng trắng thừa và ép kiểu dữ liệu về dạng số nguyên, ngăn chặn lỗi "cộng chuỗi" (String Concatenation) kinh điển của JavaScript.
  - State Locking (Khóa trạng thái): Triển khai biến cờ hiệu (isSolving) để tạo cơ chế chặn spam click. Khi thuật toán đang thực thi hoặc đang diễn hoạt (Animation), mọi yêu cầu gửi thêm đều bị chặn đứng từ lớp Logic nhằm bảo vệ tài nguyên Server.
  - Race Condition Prevention (Chống xung đột luồng): Quản lý chặt chẽ setInterval bằng việc luôn clearInterval trước khi bắt đầu một luồng mới. Điều này đảm bảo giao diện không bị hiện tượng "nhảy số" khi người dùng thực hiện Reset và Solve liên tục.
  - Modern Visual Feedback: Trạng thái hệ thống được cập nhật thời gian thực trên giao diện thông qua status label và các nút bấm được vô hiệu hóa trực quan, giúp người dùng nắm bắt được tiến độ xử lý của thuật toán.

## Công nghệ sử dụng
- **Backend:** Java 21, Spring Boot.
- **Frontend:** HTML, CSS, JavaScript (Vanilla).
- **DevOps:** Docker.

## Cách chạy dự án
Yêu cầu máy đã cài đặt **Docker**.
1. Clone dự án: `git clone [link-repo]`
2. Chạy lệnh: `docker-compose up --build`
3. Truy cập: `http://localhost:8080`


