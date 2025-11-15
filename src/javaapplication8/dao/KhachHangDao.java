package javaapplication8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javaapplication8.model.KhachHangModel;
import javaapplication8.until.DBConnect;

public class KhachHangDao {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<KhachHangModel> list;

    public KhachHangDao() {
        conn = DBConnect.getConnection();
        list = new ArrayList<>();
    }

    public List<KhachHangModel> danhSachKhachHang() {
        list.clear(); 
        String sql = """
                     SELECT ID, TEN_KHACH_HANG,DIA_CHI,
                            SDT, GIOI_TINH, MA_KH
                     FROM Khach_Hang 
                     WHERE Da_Xoa = 0;
                     """;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                KhachHangModel kh = new KhachHangModel();
                kh.setId(rs.getInt("ID"));
                kh.setHoTen(rs.getString("TEN_KHACH_HANG"));
                kh.setDiaChi(rs.getString("DIA_CHI"));
                kh.setSdt(rs.getString("SDT"));
                kh.setGioiTinh(rs.getBoolean("GIOI_TINH"));
                kh.setMaKH(rs.getString("MA_KH"));
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(""+ list.size());
        return list;
    }

    public boolean addKhachHang(KhachHangModel kh) {
        String sql = """
    INSERT INTO khach_hang(TEN_KHACH_HANG, DIA_CHI, SDT, GIOI_TINH, DA_XOA, MA_KH)
    VALUES (?, ?, ?, ?, 0, ?);
""";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getDiaChi());
            ps.setString(3, kh.getSdt());
            ps.setBoolean(4, kh.isGioiTinh()); // Hoặc ps.setInt(4, kh.isGioiTinh() ? 1 : 0); nếu cột là int
            ps.setString(5, kh.getMaKH());     // Không phải getMaHD nếu đây là mã khách hàng

            return ps.executeUpdate() > 0; // đừng quên thực thi câu lệnh
        } catch (Exception e) {
            e.printStackTrace(); // để debug lỗi nếu có
        }
        return false;
    }
    
    public KhachHangModel layKhachHangTheoMa(String makh){
        String sql ="""
                    SELECT * FROM Khach_Hang WHERE MA_KH = ?
                    """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, makh);
            rs = ps.executeQuery();
            while (rs.next()) {                
                KhachHangModel kh = new KhachHangModel();
                kh.setId(rs.getInt("ID"));
                kh.setHoTen(rs.getString("Ten_Khach_Hang"));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<KhachHangModel> timKiem(String timKiem) {
        list.clear(); 
        String sql = """
                     SELECT ID, TEN_KHACH_HANG,DIA_CHI,
                            SDT, GIOI_TINH, MA_KH
                     FROM Khach_Hang 
                     WHERE Da_Xoa = 0 AND (TEN_KHACH_HANG LIKE ? OR MA_KH LIKE ?);
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + timKiem + "%");
            ps.setString(2, "%" + timKiem + "%");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                KhachHangModel kh = new KhachHangModel();
                kh.setId(rs.getInt("ID"));
                kh.setHoTen(rs.getString("TEN_KHACH_HANG"));
                kh.setDiaChi(rs.getString("DIA_CHI"));
                kh.setSdt(rs.getString("SDT"));
                kh.setGioiTinh(rs.getBoolean("GIOI_TINH"));
                kh.setMaKH(rs.getString("MA_KH"));
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(""+ list.size());
        return list;
    }

}
