/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package javaapplication8.service;

import java.util.List;
import javaapplication8.model.SanPham_ThuocTinh;

import java.sql.SQLException;

/**
 *
 * @author dungc
 */
public interface SanPhamThuocTinhService {

    List<SanPham_ThuocTinh> layDanhSachThuocTinh(String tableName);

    List<SanPham_ThuocTinh> getLoaiSanPham();

    List<SanPham_ThuocTinh> getLoaiMauSac();

    List<SanPham_ThuocTinh> getLoaiKichThuoc();

    List<SanPham_ThuocTinh> getLoaiChatLieu();

    boolean kiemTraTenThuocTinhDaTonTai(String tableName, String tenThuocTinh);

    boolean addThuocTinh(String tableName, String ma, String ten);

    boolean updateThuocTinh(String tableName, String tenMoi, String ma);

    boolean xoaThuocTinhSanPham(String tableName, int id);

    }
