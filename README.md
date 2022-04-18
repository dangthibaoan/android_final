Xây dựng được ứng dụng giúp nhân viên order làm việc nhanh chóng, hiệu quả hơn, rút ngắn thời gian gọi món và chờ đợi, giảm thiểu lao động thủ công.

a) Tổng quan

Ứng dụng giúp cho việc order đồ ăn trong nhà hàng thuận tiện và dễ dàng hơn.

Nhân viên quán ăn thay vì sử dụng giấy để ghi chép có thể order ngay trên giao diện phần mềm cài trên máy tính bảng.

Sau khi khách order, các món ăn sẽ hiển thị lần lượt theo Queue ở giao diện máy tính bảng của nhà bếp. Đầu bếp sẽ căn cứ vào đó để chuẩn bị các món ăn/uống theo nhu cầu của khách hàng. Sau khi thực hiện xong món ăn, đầu bếp có thể xóa món ăn khỏi list để làm món tiếp theo.

b) Các yêu cầu đặt ra

Về Hệ thống Back-end: Sử dụng một CSDL và một phần mềm backend bất kỳ (ví dụ MySQL, Sql Server, Python, NodeJS…). Nhóm lựa chọn sử dụng cơ sở dữ liệu Cloud Firestore của Firebase, Google.

Về giao diện: Yêu cầu giao diện thân thiện, dễ sử dụng và có thể thao tác nhanh phù hợp với môi trường quán ăn, cần phục vụ nhanh.

Về tính năng: Cần phải có các module tối thiểu như sau:

Module quản lý món ăn: Để quản lý tất cả các món ăn trong menu, yêu cầu quản lý tối thiểu các thông tin như: Tên món ăn, đơn vị (chiếc, đĩa, gói…), giá tiền và hình ảnh minh họa. Đây là chức năng dành cho quản lý.

Module quản lý nhân sự: Thêm mới, sửa, xóa các user được quyền login vào hệ thống. Một user trong hệ thống có thể là nhân viên hoặc quản lý. Các module dành cho quản lý cần ngăn chặn nhân viên truy cập.

Module order món ăn: Giao diện cho phép nhân viên chọn món khách hàng order kèm theo số lượng và các ghi chú thêm (ví dụ: ít cay, nhiều hành,…). Chú ý cần hiển thị danh sách các bàn sao cho nhân viên lựa chọn thuận tiện khi order. Khi khách hàng đã thanh toán xong, nhân viên có thể chọn bàn và nhấn Thanh toán để xóa các món ăn đã order cho bàn đó và sẵn sàng tiếp khách mới.

Module hiển thị danh sách món ăn cho đầu bếp: Giao diện đảm bảo dễ dàng, thân thiện để đầu bếp có thể dễ dàng thao tác khi đang nấu ăn. Đầu bếp sẽ nhìn thấy các món ăn được sắp xếp theo thời gian đặt và sau khi thực hiện xong món nào thì có thể xóa món đó khỏi list.
