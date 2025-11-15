package javaapplication8.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javaapplication8.model.HoaDon_Model;
import javaapplication8.model.ThongKeDoanhThu;
import javaapplication8.model.ThongKeDoanhThuNgay;
import javaapplication8.model.Top10SP;
import javaapplication8.until.DBConnect;

public class ThongKeDao {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ThongKeDao() {
        conn = DBConnect.getConnection();
    }

    public List<Top10SP> thongKeTop10SanPham(Date ngayBD, Date ngayKT) {
        List<Top10SP> list = new ArrayList<>();

        // Tăng thêm 1 ngày cho ngày kết thúc để bao trọn cả ngày cuối
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngayKT);
        cal.add(Calendar.DATE, 1); // Cộng thêm 1 ngày

        java.sql.Date sqlTuNgay = new java.sql.Date(ngayBD.getTime());
        java.sql.Date sqlDenNgay = new java.sql.Date(cal.getTimeInMillis()); // ngàyKT + 1

        String sql = """
                 SELECT TOP 10 
                     sp.TEN AS TenSanPham,
                     SUM(hdct.SO_LUONG) AS TongSoLuongBan,
                     SUM(hdct.SO_LUONG * hdct.DON_GIA) AS TongTienThuDuoc
                 FROM 
                     Hoa_Don hd
                 LEFT JOIN 
                     Hoa_Don_Chi_Tiet hdct ON hd.ID = hdct.ID_HD
                 LEFT JOIN 
                     San_Pham_Chi_Tiet spct ON hdct.ID_SPCT = spct.ID
                 LEFT JOIN 
                     San_Pham sp ON spct.ID_SAN_PHAM = sp.ID
                 WHERE 
                     hd.NGAY_THANH_TOAN BETWEEN ? AND ?
                     AND hd.TRANG_THAI = 1
                 GROUP BY 
                     sp.TEN
                 ORDER BY 
                     TongSoLuongBan DESC;
                 """;

        try {
            ps = conn.prepareStatement(sql);
            ps.setDate(1, sqlTuNgay);
            ps.setDate(2, sqlDenNgay);
            rs = ps.executeQuery();

            while (rs.next()) {
                Top10SP top = new Top10SP();
                top.setTenSP(rs.getString("TenSanPham"));
                top.setSoLuong(rs.getInt("TongSoLuongBan"));
                top.setTongTien(rs.getBigDecimal("TongTienThuDuoc"));
                list.add(top);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ThongKeDoanhThu thongKeDoanhThu(Date ngayBD, Date ngayKT) {
        ThongKeDoanhThu tk = new ThongKeDoanhThu();
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngayKT);
        cal.add(Calendar.DATE, 1); // Cộng thêm 1 ngày

        java.sql.Date sqlTuNgay = new java.sql.Date(ngayBD.getTime());
        java.sql.Date sqlDenNgay = new java.sql.Date(ngayKT.getTime());
        String sql = """
                     SELECT 
                             COUNT(DISTINCT hd.ID) AS TongSoHoaDon,
                             SUM(hdct.SO_LUONG) AS TongSanPhamBan,
                             SUM(DISTINCT hd.TONG_TIEN_KHI_GIAM) AS TongTienThuDuoc
                         FROM 
                             Hoa_Don hd
                         LEFT JOIN 
                             Hoa_Don_Chi_Tiet hdct ON hd.ID = hdct.ID_HD
                         WHERE 
                             hd.NGAY_THANH_TOAN BETWEEN ? AND ?
                             AND hd.TRANG_THAI = 1;
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setDate(1, sqlTuNgay);
            ps.setDate(2, sqlDenNgay);
            rs = ps.executeQuery();
            while (rs.next()) {
                tk.setTongTien(rs.getBigDecimal("TongTienThuDuoc"));
                tk.setTongSP(rs.getInt("TongSanPhamBan"));
                tk.setTongHD(rs.getInt("TongSoHoaDon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }

    public List<ThongKeDoanhThuNgay> getDoanhThuTheoNgay(Date tuNgay, Date denNgay) {
        List<ThongKeDoanhThuNgay> list = new ArrayList<>();
        String sql = """
        SELECT FORMAT(NGAY_THANH_TOAN, 'dd/MM') AS nhom, SUM(tong_Tien_khi_giam) AS tong
        FROM Hoa_Don
        WHERE NGAY_THANH_TOAN BETWEEN ? AND ?
        GROUP BY FORMAT(NGAY_THANH_TOAN, 'dd/MM'), CAST(NGAY_THANH_TOAN AS DATE)
        ORDER BY CAST(NGAY_THANH_TOAN AS DATE)
    """;

        try {

            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(tuNgay.getTime()));
            ps.setDate(2, new java.sql.Date(denNgay.getTime()));

            rs = ps.executeQuery();
            while (rs.next()) {
                String nhom = rs.getString("nhom");
                BigDecimal tong = rs.getBigDecimal("tong");
                list.add(new ThongKeDoanhThuNgay(nhom, tong));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ThongKeDoanhThuNgay> getDoanhThuTheoThang(int nam) {
        List<ThongKeDoanhThuNgay> list = new ArrayList<>();
        String sql = """
        SELECT FORMAT(NGAY_THANH_TOAN, 'MM') AS nhom, SUM(tong_Tien_khi_giam) AS tong
        FROM Hoa_Don
        WHERE YEAR(NGAY_THANH_TOAN) = ?
        GROUP BY FORMAT(NGAY_THANH_TOAN, 'MM'), MONTH(NGAY_THANH_TOAN)
        ORDER BY MONTH(NGAY_THANH_TOAN)
    """;

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, nam);
            rs = ps.executeQuery();

            while (rs.next()) {
                String nhom = rs.getString("nhom"); // "01", "02", ...
                BigDecimal tong = rs.getBigDecimal("tong");
                list.add(new ThongKeDoanhThuNgay(nhom, tong));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ThongKeDoanhThuNgay> getDoanhThuTheoNam() {
        List<ThongKeDoanhThuNgay> list = new ArrayList<>();
        String sql = """
        SELECT YEAR(NGAY_THANH_TOAN) AS nhom, SUM(tong_Tien_khi_giam) AS tong
        FROM Hoa_Don
        GROUP BY YEAR(NGAY_THANH_TOAN)
        ORDER BY YEAR(NGAY_THANH_TOAN)
    """;

        try {

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String nhom = rs.getString("nhom"); // năm như: "2023", "2024"
                BigDecimal tong = rs.getBigDecimal("tong");
                list.add(new ThongKeDoanhThuNgay(nhom, tong));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ThongKeDoanhThuNgay> getDoanhThuTheo5NamGanNhat() {
        List<ThongKeDoanhThuNgay> list = new ArrayList<>();

        // Lấy năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Chuẩn bị câu lệnh SQL để lấy doanh thu của 5 năm gần nhất
        String sql = """
        SELECT YEAR(NGAY_THANH_TOAN) AS nam, SUM(tong_Tien_khi_giam) AS tong
        FROM Hoa_Don
        WHERE YEAR(NGAY_THANH_TOAN) BETWEEN ? AND ?
        GROUP BY YEAR(NGAY_THANH_TOAN)
        ORDER BY YEAR(NGAY_THANH_TOAN) DESC
    """;

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, currentYear - 4);
            ps.setInt(2, currentYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                int nam = rs.getInt("nam");
                BigDecimal tong = rs.getBigDecimal("tong");
                list.add(new ThongKeDoanhThuNgay(String.valueOf(nam), tong));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ThongKeDoanhThuNgay> getDoanhThuTheoNgayTrongThang(int nam, int thang) {
        List<ThongKeDoanhThuNgay> list = new ArrayList<>();
        String sql = """
        SELECT FORMAT(NGAY_THANH_TOAN, 'dd/MM') AS nhom, SUM(tong_Tien_khi_giam) AS tong
        FROM Hoa_Don
        WHERE YEAR(NGAY_THANH_TOAN) = ? AND MONTH(NGAY_THANH_TOAN) = ?
        GROUP BY FORMAT(NGAY_THANH_TOAN, 'dd/MM')
        ORDER BY MIN(NGAY_THANH_TOAN)
    """;

        try {
            ps = conn.prepareStatement(sql);

            ps.setInt(1, nam);
            ps.setInt(2, thang);

            rs = ps.executeQuery();
            while (rs.next()) {
                String nhom = rs.getString("nhom"); // ngày định dạng dd/MM
                BigDecimal tong = rs.getBigDecimal("tong");
                list.add(new ThongKeDoanhThuNgay(nhom, tong));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
