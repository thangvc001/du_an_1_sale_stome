
package javaapplication8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javaapplication8.model.HoaDon_Model;
import javaapplication8.model.PhuongThucThanhToan;
import javaapplication8.until.DBConnect;

public class PhuongThucThanhToanDao {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<PhuongThucThanhToan> list;

    public PhuongThucThanhToanDao() {
        conn = DBConnect.getConnection();
        list = new ArrayList<>();
    }
    
    public List<PhuongThucThanhToan> layPhuongThucThanhToan(){
        String sql ="""
                    select id, ten_phuong_thuc from hinh_thuc_thanh_toan
                    """;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                PhuongThucThanhToan pt = new  PhuongThucThanhToan();
                pt.setId(rs.getInt("ID"));
                pt.setTenPT(rs.getString("TEN_PHUONG_THUC"));
                list.add(pt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;       
    }

    public PhuongThucThanhToan layPhuongThucThanhToanTheoTen(String httt){
        String sql = """
                     select id from hinh_thuc_thanh_toan where ten_phuong_thuc = ?
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, httt);
            rs = ps.executeQuery();
            while (rs.next()) {                
                PhuongThucThanhToan pt = new PhuongThucThanhToan();
                pt.setId(rs.getInt("ID"));
                return pt;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
