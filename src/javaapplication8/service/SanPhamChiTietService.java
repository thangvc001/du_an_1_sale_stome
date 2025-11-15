/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package javaapplication8.service;

import java.math.BigDecimal;
import java.util.List;
import javaapplication8.model.SanPham_ChiTiet;
import javaapplication8.model.SanPham_ThuocTinh;

/**
 *
 * @author dungc
 */
public interface SanPhamChiTietService {

    List<SanPham_ChiTiet> getAllSanPhamChiTiet();

    boolean addSanPhamChiTiet(String ma, int idsp, int idms, int idcl, int idkt, String donGia, int soLuong);

    int kiemTraSanPhamChiTiet(int idSPCT);

    BigDecimal layDonGiaSPCT(int idSPCT);

    boolean truSoLuongTon(int idSPCT, int soLuong);

    boolean congSoLuongTon(int idSPCT, int soLuong);

    SanPham_ChiTiet timSanPhamChiTietTheoMa(String maSPCT);

    boolean updateSanPhamChiTiet(String ma, int idsp, int idms, int idcl, int idkt, String donGia, int soLuong);

    boolean taoVaLuuQR(String maSPCT);

    String getQRPathByMaSPCT(String maSPCT);

    SanPham_ChiTiet getByMaSPCT(String maSPCT);

    List<SanPham_ChiTiet> timKiemSanPhamChiTiet(String timKiem);

}
