/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.service.serviceimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaapplication8.dao.SanPhamThuocTinhDao;
import javaapplication8.model.SanPham_ThuocTinh;
import javaapplication8.service.SanPhamThuocTinhService;

/**
 *
 * @author dungc
 */
public class SanPhamThuocTinhServiceImpl implements SanPhamThuocTinhService {

    private final SanPhamThuocTinhDao dao = new SanPhamThuocTinhDao();

    @Override
    public List<SanPham_ThuocTinh> layDanhSachThuocTinh(String tableName) {
        return dao.getThuocTinh(tableName);
    }

    @Override
    public boolean kiemTraTenThuocTinhDaTonTai(String tableName, String tenThuocTinh) {
        return dao.kiemTraTenThuocTinhDaTonTai(tableName, tenThuocTinh);
    }

    @Override
    public boolean addThuocTinh(String tableName, String ma, String ten) {
        return dao.addThuocTinh(tableName, ma, ten);
    }

    @Override
    public List<SanPham_ThuocTinh> getLoaiSanPham() {
        return dao.getThuocTinh("San_Pham");
    }

    @Override
    public List<SanPham_ThuocTinh> getLoaiMauSac() {
        return dao.getThuocTinh("Mau_Sac");
    }

    @Override
    public List<SanPham_ThuocTinh> getLoaiKichThuoc() {
        return dao.getThuocTinh("Kich_Thuoc");
    }

    @Override
    public List<SanPham_ThuocTinh> getLoaiChatLieu() {
        return dao.getThuocTinh("Chat_Lieu");
    }

    @Override
    public boolean updateThuocTinh(String tableName, String tenMoi, String ma) {
        return dao.updateThuocTinh(tableName, tenMoi, ma);
    }

    @Override
    public boolean xoaThuocTinhSanPham(String tableName, int id) {
        return dao.xoaThuocTinhSanPham(tableName, id);
    }

    }
