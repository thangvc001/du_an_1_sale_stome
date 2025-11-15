/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaapplication8.model.SanPham_ThuocTinh;
import javaapplication8.until.DBConnect;

/**
 *
 * @author dungc
 */
public class SanPhamThuocTinhDao {

    private ArrayList<SanPham_ThuocTinh> list;
    private PreparedStatement ps = null;
    private Connection conn = null;
    private ResultSet rs = null;
    private String sql;

    public SanPhamThuocTinhDao() {
        conn = DBConnect.getConnection();

        list = new ArrayList<>();
    }

    public ArrayList<SanPham_ThuocTinh> getThuocTinh(String tableName) {
        ArrayList<SanPham_ThuocTinh> list = new ArrayList<>();
        sql = "SELECT id,ma, ten FROM " + tableName + " WHERE DA_XOA = 0";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SanPham_ThuocTinh(rs.getInt("id"), rs.getString("ma"), rs.getString("ten")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addThuocTinh(String tableName, String ma, String ten) {
        sql = "insert into " + tableName + "(MA, TEN) values (?,?)";
        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, ma);
            ps.setString(2, ten);

            return ps.executeUpdate() > 0;//thêm/sửa/xoá:executeUpđate()
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateThuocTinh(String tableName, String tenMoi, String ma) {
        sql = "UPDATE " + tableName + " SET ten = ? WHERE MA = ?";

        try {
            ps = conn.prepareStatement(sql);

            ps.setNString(1, tenMoi);
            ps.setNString(2, ma);

            return ps.executeUpdate() > 0;//thêm/sửa/xoá:executeUpđate()

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean kiemTraTenThuocTinhDaTonTai(String tableName, String tenThuocTinh) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE ten = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setNString(1, tenThuocTinh);
            rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Tên đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Tên chưa tồn tại
    }

    public boolean xoaThuocTinhSanPham(String tableName, int id) {

        String sql = "DELETE FROM SAN_PHAM_CHI_TIET WHERE ID_" + tableName + " = ?; "
                + "DELETE FROM " + tableName + " WHERE ID = ?;";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate(); // dùng executeUpdate

            return rowsAffected > 0; // nếu có dòng bị ảnh hưởng, xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    }
