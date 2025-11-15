
package javaapplication8.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javaapplication8.model.ThongKeDoanhThu;
import javaapplication8.model.ThongKeDoanhThuNgay;
import javaapplication8.model.Top10SP;

public interface ThongKeService {
    
    List<Top10SP> layTop10SP(Date ngayBD, Date ngayKT);
    
    ThongKeDoanhThu thongKeDoanhThu(Date ngayBD, Date ngayKT);
    
    List<ThongKeDoanhThuNgay> getDoanhThuTheoNgay(Date tuNgay, Date denNgay);
    
    List<ThongKeDoanhThuNgay> getDoanhThuTheoThang(int nam);
    
    List<ThongKeDoanhThuNgay> getDoanhThuTheoNam();
    
    List<ThongKeDoanhThuNgay> getDoanhThuTheo5NamGanNhat();
    
    List<ThongKeDoanhThuNgay> getDoanhThuTheoNgayTrongThang(int nam, int thang) ;
}
