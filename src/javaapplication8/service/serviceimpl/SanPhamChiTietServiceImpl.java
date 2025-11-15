/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.service.serviceimpl;

import java.math.BigDecimal;
import java.util.List;
import javaapplication8.dao.SanPhamChiTietDao;
import javaapplication8.model.SanPham_ChiTiet;
import javaapplication8.model.SanPham_ThuocTinh;
import javaapplication8.service.SanPhamChiTietService;

/**
 *
 * @author dungc
 */
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    private final SanPhamChiTietDao dao = new SanPhamChiTietDao();

    @Override
    public List<SanPham_ChiTiet> getAllSanPhamChiTiet() {
        return dao.getSanPhamChiTiet();
    }

    @Override
    public boolean addSanPhamChiTiet(String ma, int idsp, int idms, int idcl, int idkt, String donGia, int soLuong) {
        return dao.addSanPhamChiTiet(ma, idsp, idms, idcl, idkt, donGia, soLuong);
    }

    @Override
    public int kiemTraSanPhamChiTiet(int idSPCT) {
        return dao.kiemTraSanPhamChiTiet(idSPCT);
    }

    @Override
    public BigDecimal layDonGiaSPCT(int idSPCT) {
        return dao.layDonGiaSPCT(idSPCT);
    }

    @Override
    public boolean truSoLuongTon(int idSPCT, int soLuong) {
        return dao.truSoLuongTon(idSPCT, soLuong);
    }

    @Override
    public boolean congSoLuongTon(int idSPCT, int soLuong) {
        return dao.congSoLuongTon(idSPCT, soLuong);
    }

    @Override
    public SanPham_ChiTiet timSanPhamChiTietTheoMa(String maSPCT) {
        return dao.timSanPhamChiTietTheoMa(maSPCT);
    }

    public boolean updateSanPhamChiTiet(String ma, int idsp, int idms, int idcl, int idkt, String donGia, int soLuong) {
        return dao.updateSanPhamChiTiet(ma, idsp, idms, idcl, idkt, donGia, soLuong);
    }

    @Override
    public boolean taoVaLuuQR(String maSPCT) {
        return dao.taoVaLuuQR(maSPCT);
    }

    @Override
    public String getQRPathByMaSPCT(String maSPCT) {
        return dao.getQRPathByMaSPCT(maSPCT);
    }

    @Override
    public SanPham_ChiTiet getByMaSPCT(String maSPCT) {
        return dao.getByMaSPCT(maSPCT);
    }

    @Override
    public List<SanPham_ChiTiet> timKiemSanPhamChiTiet(String timKiem) {
        return dao.timKiemSanPhamChiTiet(timKiem);
    }

}
