# SketchPoint
Đồ án Kĩ thuật đồ họa 2018

## Yêu cầu
1. **Hệ tọa độ**</br>
Cho phép người dùng chọn hệ tọa độ 2D, 3D và vẽ hệ tọa độ lên màn hình, mỗi đơn vị tương ứng 5 pixel.

2. **Vẽ trong hệ tọa độ 2D**
   - Tự động (hoặc cho người dùng chọn)  vẽ ít nhất 2 vật cơ bản
   - Mỗi vật được tạo bởi ít nhất 2 hình cơ sở khác nhau.
   - Có ít nhất 4 hình cơ sở được vẽ trong sản phẩm.
   Thực hiện các phép biến đổi (tịnh tiến, quay, đối xứng, thu phóng) trên các hình để tạo hiệu ứng chuyển động.  
   Hiển thị tọa độ của các hình cơ sở trên hệ tọa độ người dùng.
  
3. **Vẽ trong hệ tọa độ 3D**</br>
   - Cho phép người dùng chọn hình ảnh cơ bản cần vẽ: hình cầu, hình hộp chữ nhật, hình hộp vuông, hình trụ,… trong hệ tọa độ 3D.  
   - Dùng thuật toán Cavalier hoặc Cabinet để vẽ các đối tượng trong hệ tọa độ 3D dựa vào các thông số người dùng nhập vào.
   - Ví dụ: Hình cầu (tọa độ tâm, bán kính), hình hộp chữ nhật (tọa độ đỉnh dưới bên trái, chiều dài, chiều rộng, chiều cao), hình trụ (chiều cao, tâm đáy, bán kính đáy).

## Mô tả
### Chức năng chính
<ol>
<li>Hiển thị tọa độ trên 2D, 3D.</li>
<li>Xây dựng một số hình mẫu cho từng hệ tọa độ với 2 chức năng nhập là nhập thông số và click chuột.</li>
<li>Cho phép sử dụng các phép biến hình cơ bản (tịnh tiến, quay, đối xứng, thu phóng).</li>
<li>Tạo hiệu ứng chuyển động thông qua các phép biến hình.</li>
</ol>

### Chức năng bổ sung
<ol>
<li>Undo/Redo tác vụ.</li>
<li>Lưu/Mở ảnh.</li>
<li>Tô màu và hiệu ứng.</li>
<li>Ẩn hiện lưới pixel.</li>
</ol>

## Todos
- [x] Tạo lưới pixel và hiển thị tọa độ điểm (Bài tập 1).
- [x] Vẽ các hình hợp từ các hình cơ sở (Bài tập 2, 3).
- [x] Kĩ thuật xử lý nhiều nét vẽ trên một hình (Bài tập 4).
- [x] Hoàn thiện các phép biến hình (Bài tập 5, 6).
