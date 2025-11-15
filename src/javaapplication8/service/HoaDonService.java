package javaapplication8.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javaapplication8.model.HoaDonChiTiet_Model;
import javaapplication8.model.HoaDon_Model;
import javaapplication8.model.LichSuHoaDon;

public interface HoaDonService {
    
    int taoNhanhHoaDonS(String mahd, int idNV, String ngay);
    
    List<HoaDon_Model> danhSachHoaDon();
    
    List<HoaDonChiTiet_Model> layChiTietHoaDonTheoId(int idHoaDon);
    
    int laySoLuongTheoSPCT(int idHD, int idSPCT);
    
    boolean capNhatSoLuong(int idHD, int IdSPCT, int soLuongMoi);
    
    boolean themSanPhamVaoHoaDon(int idHD, int idSPCT, int soLuong, BigDecimal donGia, String maHDCT);
    
    boolean xoaSanPhamKhoiHoaDon(int idHD,int idSPCT);

    boolean updateHoaDon(String maHD, LocalDateTime ngayThanhToan, int idKH, BigDecimal tongTien, int idPhieuGG, int hinhTHucThanhToan, BigDecimal tongTienThucTra );

    boolean huyHoaDon(String maHD);
    
    HoaDon_Model layIdHoaDonTheoMa(String ma);
    
    List<HoaDon_Model> danhsachHoaDonDaThanhToan();
    
    void hanhDong(int idHD,int hanhDong, LocalDateTime thoiGianInHD, int idNV) ;
    
    List<LichSuHoaDon> lichSuHoaDonTheoID(int idHD);
    
    boolean taoVaLuuQR(String maHD);
    
    HoaDon_Model layHoaDonTheoMa(String maHD);
    
    List<HoaDon_Model> danhsachHoaDonTimTheoTuKhoa(String tuKhoa);
    
    List<HoaDon_Model> danhsachHoaDonTimTheoMa(String tuKhoa);

}
