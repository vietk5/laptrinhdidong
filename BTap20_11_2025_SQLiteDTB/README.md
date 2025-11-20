# 📝 Ứng dụng Ghi chú với SQLite (Android)

Ứng dụng demo CRUD (Create – Read – Update – Delete) ghi chú sử dụng **SQLite** trên Android.

Giao diện chính:

![Main UI](https://github.com/user-attachments/assets/a2cc3bc3-737a-4af4-beda-598a440400b7)

---

## 1. Chức năng chính

1. Chức năng chính
✅ 1. Thêm ghi chú (Add Note)

Người dùng nhấn vào menu trên Toolbar (icon +).

Ứng dụng mở Dialog cho phép nhập tên Note mới.

Nếu người dùng không nhập gì → hiển thị Toast “Vui lòng nhập tên Notes”.

Nếu hợp lệ → lưu vào SQLite bằng câu lệnh:

INSERT INTO Notes VALUES (NULL, 'Tên ghi chú')


Sau đó danh sách được tải lại và hiển thị Note mới.

✏️ 2. Chỉnh sửa ghi chú (Edit Note)

Người dùng nhấn vào icon cây bút trên từng dòng.

Mở dialog chỉnh sửa, phần EditText sẽ hiển thị sẵn nội dung cũ.

Người dùng sửa nội dung rồi nhấn Cập nhật.

Lúc này câu lệnh SQLite thực thi:

UPDATE Notes
SET NameNotes = 'Nội dung ghi chú mới'
WHERE Id = <id_note>


Thành công → hiển thị Toast “Đã cập nhật Notes thành công”.

Danh sách được reload để cập nhật giao diện.

🗑 3. Xóa ghi chú (Delete Note)

Người dùng nhấn vào icon thùng rác trên dòng cần xóa.

Một AlertDialog hiện ra để xác nhận:

Bạn có muốn xóa Notes "<tên note>" này không?

Nếu người dùng chọn Có, SQLite thực thi:

DELETE FROM Notes
WHERE Id = <id_note>


Thành công → hiển thị Toast “Đã xóa Notes thành công”.

Giao diện được reload để loại bỏ item đó.
