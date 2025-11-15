package javaapplication8.service;

import java.sql.SQLException;
import java.util.List;
import javaapplication8.model.Model_NhanVien;

public interface NhanVienService {
    
    Model_NhanVien kiemTraDangNhap(String tenDN, String mk);
    
    Model_NhanVien kiemTraEmail(String email);

    boolean resetPassword(String email, String newPassword);
    
    List<Model_NhanVien> getPage(int offset, int limit) throws SQLException;
    
    int countAll() throws SQLException;
    
    boolean themMoiNhanVien(Model_NhanVien nv);
    
    boolean capNhatNhanVien(Model_NhanVien nv);
    
    boolean xoaNhanVien(String maNV);
    
    List<Model_NhanVien> danhSachTimKiem(String timkiem, int offset, int limit);
}
