# Android App: Bài tập 01 

## Mô Tả

Ứng dụng Android này thực hiện các thao tác với mảng số nguyên và chuỗi ký tự. Người dùng có thể nhập mảng số để phân loại các số chẵn và lẻ, và cũng có thể nhập một chuỗi ký tự để đảo ngược và chuyển thành chữ hoa. Ứng dụng sử dụng các thành phần của Android Studio như `EditText`, `Button`, và `TextView` để giao tiếp với người dùng.

## Yêu Cầu

1. **Cài đặt môi trường làm việc** với Android Studio.
2. **Tạo Project Baitap01** bao gồm Activity chính với giao diện chứa các thành phần:
    - Hình ảnh đại diện cho sinh viên.
    - Thông tin sinh viên như tên và trường học.
3. **Tạo 2 yêu cầu chức năng chính**:
   - Xử lý và in ra các số chẵn và số lẻ từ mảng nhập vào.
   - Đảo ngược chuỗi ký tự và hiển thị kết quả.

## Các Yêu Cầu Chức Năng

### Yêu Cầu 4: Xử lý mảng số nguyên

- Nhập mảng số nguyên từ người dùng qua `EditText`.
- Phân loại và in ra các số chẵn và số lẻ vào `Logcat`.

### Yêu Cầu 5: Đảo ngược chuỗi ký tự

- Nhập chuỗi ký tự từ người dùng qua `EditText`.
- Hiển thị chuỗi đảo ngược, chuyển thành chữ hoa, và xuất ra `TextView` và `Toast`.

## Hướng Dẫn Sử Dụng

1. **Nhập mảng số nguyên** vào ô nhập liệu "Nhập mảng", sau đó nhấn nút "Xử lý mảng".
2. **Nhập chuỗi ký tự** vào ô nhập liệu "Nhập chuỗi bạn muốn đảo ngược", sau đó nhấn nút "Xử lý Chuỗi".
3. Kết quả sẽ được hiển thị trực tiếp trên giao diện và Logcat.

### Giao Diện Người Dùng

- **Hình ảnh sinh viên**: Được hiển thị ở đầu ứng dụng với một `CircleImageView`.
- **Thông tin sinh viên**: Tên và trường học của sinh viên hiển thị dưới ảnh đại diện.
- **Các nút**:
    - `Xử lý mảng`: Để xử lý mảng số nguyên.
    - `Xử lý Chuỗi`: Để xử lý chuỗi ký tự.
- **Kết quả**: Kết quả các thao tác sẽ được hiển thị dưới dạng `TextView` và `Toast`.

## Mẫu Giao Diện

<img width="559" height="901" alt="image" src="https://github.com/user-attachments/assets/aad79da0-09bb-4b80-a070-a05e596df335" />

### Kết quả Xử Lý Mảng
Khi nhập vào 1 mảng số nguyên bất kì và bấm button Xử lí mảng thì hệ thống sẽ chia mảng gốc thành 2 mảng số lẻ và số chẳn riêng biệt
<img width="552" height="957" alt="image" src="https://github.com/user-attachments/assets/ece33a1f-57b0-413c-b31c-94cdf447b857" />
<img width="470" height="120" alt="image" src="https://github.com/user-attachments/assets/093ea693-9d2d-4c50-b2f7-2f753632987e" />

### Kết quả Xử Lý Chuỗi
Khi nhập vào 1 chuỗi bất kì thì hệ thống sẽ đảo ngược các từ có trong chuỗi và hiển thị kết quả ra màn hình
<img width="553" height="964" alt="image" src="https://github.com/user-attachments/assets/fbcf3122-d2dd-47c2-9fd5-2b7849a9c99d" />
