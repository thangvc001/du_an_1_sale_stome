
package javaapplication8.service;

import java.util.List;
import javaapplication8.model.KhachHangModel;

public interface KhachHangService {
    
    List<KhachHangModel> danhSachKhachHang();
    
    boolean addKhachHang(KhachHangModel kh);
    
    KhachHangModel layKhachHangTheoMa(String makh);
    
    List<KhachHangModel> timKiem(String timKiem);
}
