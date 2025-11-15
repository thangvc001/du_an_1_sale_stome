package javaapplication8.dao;

import java.math.BigDecimal;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaapplication8.model.SanPham_ChiTiet;
import javaapplication8.until.DBConnect;

public class SanPhamChiTietDao {

    private ArrayList<SanPham_ChiTiet> list;
    private PreparedStatement ps = null;
    private Connection conn = null;
    private ResultSet rs = null;
    private String sql;

    public SanPhamChiTietDao() {
        conn = DBConnect.getConnection();

        list = new ArrayList<>();
    }

    public ArrayList<SanPham_ChiTiet> getSanPhamChiTiet() {
        ArrayList<SanPham_ChiTiet> list = new ArrayList();

        sql = """
              SELECT San_Pham_Chi_Tiet.ID, San_Pham_Chi_Tiet.MA_SPCT, San_Pham.TEN, Mau_Sac.TEN, Chat_Lieu.TEN, Kich_Thuoc.TEN, FORMAT(San_Pham_Chi_Tiet.Don_Gia, 'N0') + ' VNĐ' , San_Pham_Chi_Tiet.SO_LUONG
              FROM San_Pham_Chi_Tiet 
              JOIN San_Pham ON San_Pham.ID = San_Pham_Chi_Tiet.ID_SAN_PHAM
              JOIN Mau_Sac ON Mau_Sac.ID = San_Pham_Chi_Tiet.ID_MAU_SAC
              JOIN Chat_Lieu ON Chat_Lieu.ID = San_Pham_Chi_Tiet.ID_CHAT_LIEU
              JOIN Kich_Thuoc ON Kich_Thuoc.ID = San_Pham_Chi_Tiet.ID_KICH_THUOC
              WHERE San_Pham_Chi_Tiet.DA_XOA = 0 AND San_Pham.DA_XOA = 0 AND Mau_Sac.DA_XOA = 0 AND Chat_Lieu.DA_XOA = 0 AND Kich_Thuoc.DA_XOA = 0 
              ORDER BY San_Pham_Chi_Tiet.ID DESC
              """;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                int id;
                String maSpct;
                String sp;
                String mauSac;
                String kichThuoc;
                String chatLieu;
                String donGia;
                int soLuong;

                id = rs.getInt(1);
                maSpct = rs.getString(2);
                sp = rs.getString(3);
                kichThuoc = rs.getString(6);
                mauSac = rs.getString(4);
                chatLieu = rs.getString(5);
                donGia = rs.getString(7);
                soLuong = rs.getInt(8);

                list.add(new SanPham_ChiTiet(id, maSpct, sp, kichThuoc, mauSac, chatLieu, donGia, soLuong));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addSanPhamChiTiet(String ma, int idsp, int idms, int idcl, int idkt, String donGia, int soLuong) {
        sql = """
              insert into San_Pham_Chi_Tiet 
               ( MA_SPCT, ID_SAN_PHAM, ID_MAU_SAC, ID_CHAT_LIEU, ID_KICH_THUOC, DON_GIA, SO_LUONG) 
              values ( ?, ?, ?, ?, ?, ?, ?)
              """;
        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, ma);
            ps.setInt(2, idsp);
            ps.setInt(3, idms);
            ps.setInt(4, idcl);
            ps.setInt(5, idkt);
            ps.setString(6, donGia);
            ps.setInt(7, soLuong);

            return ps.executeUpdate() > 0;//thêm/sửa/xoá:executeUpđate()
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean kiemTraMaSPCTDaTonTai(String ma) {
        String sql = "SELECT COUNT(*) FROM San_Pham_Chi_Tiet WHERE MA_SPCT = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setNString(1, ma);
            rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Tên đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Tên chưa tồn tại
    }


    public boolean updateSanPhamChiTiet(String ma, int idsp, int idms, int idcl, int idkt, String donGia, int soLuong) {
        sql = "UPDATE San_Pham_Chi_Tiet set  ID_SAN_PHAM = ? , ID_MAU_SAC = ? , ID_CHAT_LIEU = ? , ID_KICH_THUOC = ? , DON_GIA = ? , SO_LUONG = ?  where MA_SPCT = ? ";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, idsp);
            ps.setInt(2, idms);
            ps.setInt(3, idcl);
            ps.setInt(4, idkt);
            ps.setString(5, donGia);
            ps.setInt(6, soLuong);
            ps.setString(7, ma);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean xoaSPCT(String ma) {
        sql = """
              update San_Pham_Chi_Tiet 
              set DA_XOA =1 
              where MA_SPCT = ?
              """;

        try {
            ps = conn.prepareStatement(sql);

            ps.setNString(1, ma);

            return ps.executeUpdate() > 0;//thêm/sửa/xoá:executeUpđate()

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<SanPham_ChiTiet> getSanPhamChiTietDaXoa() {
        ArrayList<SanPham_ChiTiet> list = new ArrayList();

        sql = """
              select San_Pham_Chi_Tiet.MA_SPCT, San_Pham_Chi_Tiet.TEN_SP, San_Pham.Ten,
                  Kich_Thuoc.Ten, Mau_Sac.Ten, Chat_Lieu.Ten, FORMAT(San_Pham_Chi_Tiet.Don_Gia, 'N0') + ' VNĐ' , San_Pham_Chi_Tiet.So_Luong
              from San_Pham_Chi_Tiet
              join San_Pham on San_Pham_Chi_Tiet.ID_SAN_PHAM = San_Pham.ID
              join Kich_Thuoc on San_Pham_Chi_Tiet.ID_Kich_Thuoc = Kich_Thuoc.ID
              join Mau_Sac on San_Pham_Chi_Tiet.ID_Mau_Sac = Mau_Sac.ID
              join Chat_Lieu on San_Pham_Chi_Tiet.ID_Chat_Lieu = Chat_Lieu.ID
              
               WHERE San_Pham_Chi_Tiet.DA_XOA = 1 OR (San_Pham.DA_XOA = 1 OR Kich_Thuoc.DA_XOA = 1 OR Mau_Sac.DA_XOA = 1 OR Chat_Lieu.DA_XOA = 1 )
               
              """;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                String maSp;
                String tenSp;
                String loaiSp;
                String kichThuoc;
                String mauSac;
                String chatLieu;
                String donGia;
                int soLuong;

                maSp = rs.getString(1);
                tenSp = rs.getString(2);
                loaiSp = rs.getString(3);
                kichThuoc = rs.getString(4);
                mauSac = rs.getString(5);
                chatLieu = rs.getString(6);
                donGia = rs.getString(7);
                soLuong = rs.getInt(8);

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean khoiPhucDaXoaSPCT(String ma, int idsp, int idms, int idkt, int idcl) {
        String sql1 = "UPDATE San_Pham SET DA_XOA = 0 WHERE San_Pham.ID = ?";
        String sql2 = "UPDATE Mau_Sac SET DA_XOA = 0 WHERE Mau_Sac.ID = ?";
        String sql3 = "UPDATE Kich_Thuoc SET DA_XOA = 0 WHERE Kich_Thuoc.ID = ?";
        String sql4 = "UPDATE Chat_Lieu SET DA_XOA = 0 WHERE Chat_Lieu.ID = ?";
        String sql5 = "UPDATE San_Pham_Chi_Tiet SET DA_XOA = 0 WHERE MA_SPCT = ?";

        try {
            conn.setAutoCommit(false); // Tắt auto commit để kiểm soát transaction

            // Thực hiện các câu lệnh update

            try (PreparedStatement ps = conn.prepareStatement(sql5)) {
                ps.setString(1, ma);
                System.out.println(ma);
                ps.executeUpdate();
            }


            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setInt(1, idsp);
                System.out.println(idsp);
                ps.executeUpdate();
            }


            try (PreparedStatement ps = conn.prepareStatement(sql2)) {
                ps.setInt(1, idms);
                System.out.println(idms);
                ps.executeUpdate();
            }


            try (PreparedStatement ps = conn.prepareStatement(sql3)) {
                ps.setInt(1, idkt);
                System.out.println(idkt);
                ps.executeUpdate();
            }


            try (PreparedStatement ps = conn.prepareStatement(sql4)) {
                ps.setInt(1, idcl);
                System.out.println(idcl);
                ps.executeUpdate();
            }

            conn.commit(); // Commit nếu tất cả đều thành công
            return true; // Thành công

        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<SanPham_ChiTiet> loadTableTimKiemTuongDoi(String keyword) {
        ArrayList<SanPham_ChiTiet> list = new ArrayList<SanPham_ChiTiet>();

        String sql = """
                  select San_Pham_Chi_Tiet.MA_SPCT, San_Pham_Chi_Tiet.TEN_SP, San_Pham.Ten,
                                   Kich_Thuoc.Ten, Mau_Sac.Ten, Chat_Lieu.Ten, FORMAT(San_Pham_Chi_Tiet.Don_Gia, 'N0') + ' VNĐ' , San_Pham_Chi_Tiet.So_Luong
                               from San_Pham_Chi_Tiet
                               join San_Pham on San_Pham_Chi_Tiet.ID_SAN_PHAM = San_Pham.ID
                               join Kich_Thuoc on San_Pham_Chi_Tiet.ID_Kich_Thuoc = Kich_Thuoc.ID
                               join Mau_Sac on San_Pham_Chi_Tiet.ID_Mau_Sac = Mau_Sac.ID
                               join Chat_Lieu on San_Pham_Chi_Tiet.ID_Chat_Lieu = Chat_Lieu.ID
                                WHERE San_Pham_Chi_Tiet.DA_XOA = 0 AND San_Pham.DA_XOA = 0 AND Kich_Thuoc.DA_XOA = 0 AND Mau_Sac.DA_XOA = 0 AND Chat_Lieu.DA_XOA = 0 AND San_Pham_Chi_Tiet.TEN_SP LIKE ?
                               
                 """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%"); // LIKE tìm kiếm
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String maSp;
                String tenSp;
                String loaiSp;
                String kichThuoc;
                String mauSac;
                String chatLieu;
                String donGia;
                int soLuong;

                maSp = rs.getString(1);
                tenSp = rs.getString(2);
                loaiSp = rs.getString(3);
                kichThuoc = rs.getString(4);
                mauSac = rs.getString(5);
                chatLieu = rs.getString(6);
                donGia = rs.getString(7);
                soLuong = rs.getInt(8);


           }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Kiểm tra số lượng spct trong kho
    public int kiemTraSanPhamChiTiet(int idSPCT) {
        int soLuong = 0;
        String sql = "SELECT SO_LUONG FROM San_Pham_Chi_Tiet WHERE ID = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idSPCT);
            rs = ps.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt("SO_LUONG");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return soLuong;
    }

    //Lấy đơn giá spct:
    public BigDecimal layDonGiaSPCT(int idSPCT) {
        String sql = """
                     SELECT DON_GIA FROM San_Pham_Chi_Tiet WHERE ID = ?
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idSPCT);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("DON_GIA");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    //Trừ số lượng tồn kho:
    public boolean truSoLuongTon(int idSPCT, int soLuong) {
        System.out.println("So luong" + soLuong);
        String sql = """
        UPDATE San_Pham_Chi_Tiet 
        SET SO_LUONG = SO_LUONG - ? 
        WHERE ID = ? AND SO_LUONG >= ?
    """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setInt(2, idSPCT);
            ps.setInt(3, soLuong); // kiểm tra đủ số lượng tồn mới trừ
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Cộng số lượng tồn:
    public boolean congSoLuongTon(int idSPCT, int soLuong) {
        System.out.println(">> Bắt đầu cộng số lượng: ID_SPCT = " + idSPCT + ", SoLuong = " + soLuong);
        String sql = """
                 UPDATE San_Pham_Chi_Tiet 
                 SET So_Luong = ISNULL(So_Luong, 0) + ? 
                 WHERE ID = ?
                 """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setInt(2, idSPCT);
            int result = ps.executeUpdate();
            System.out.println(">> Rows affected: " + result);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean taoVaLuuQR(String maSPCT) {
        try {
            String data = maSPCT;
            String path = "qr/" + maSPCT + ".png"; // thư mục qr, nên tạo trước
            int width = 200, height = 200;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            Path filePath = Paths.get(path);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

            // Sau khi tạo xong, cập nhật đường dẫn vào CSDL
            String sql = "UPDATE San_Pham_Chi_Tiet SET QR_CODE_PATH = ? WHERE MA_SPCT = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, path);
            ps.setString(2, maSPCT);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getQRPathByMaSPCT(String maSPCT) {
        String qrPath = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DBConnect.getConnection(); // Mở kết nối lại nếu bị đóng
            }

            String sql = "SELECT QR_CODE_PATH FROM San_Pham_Chi_Tiet WHERE MA_SPCT = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maSPCT);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                qrPath = rs.getString("QR_CODE_PATH");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qrPath;
    }

    public SanPham_ChiTiet getByMaSPCT(String maSPCT) {
        Connection conn = DBConnect.getConnection();
        if (conn == null) {
            System.out.println("❌ Kết nối cơ sở dữ liệu thất bại");
            return null;
        }

        String sql = """
         SELECT sp.TEN, spt.ID_MAU_SAC, spt.ID_KICH_THUOC, spt.ID_CHAT_LIEU, spt.DON_GIA, spt.SO_LUONG, spt.QR_CODE_PATH
            FROM San_Pham_Chi_Tiet spt
            JOIN San_Pham sp ON spt.ID_SAN_PHAM = sp.ID
            WHERE spt.MA_SPCT = ?;
    """;

        try {
            System.out.println("📥 Đang truy vấn với mã: '" + maSPCT + "'");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maSPCT.trim());

            ResultSet rs = ps.executeQuery();
            System.out.println("🧪 Đã thực thi truy vấn.");

            if (rs.next()) {
                System.out.println("✅ Dữ liệu được tìm thấy trong CSDL");

                SanPham_ChiTiet spct = new SanPham_ChiTiet();
                spct.setMaSPCT(maSPCT.trim());

                spct.setTenSp(rs.getString("TEN")); // Lấy tên sản phẩm
                spct.setMauSac(rs.getString("ID_MAU_SAC"));
                spct.setChatLieu(rs.getString("ID_CHAT_LIEU"));
                spct.setKichThuoc(rs.getString("ID_KICH_THUOC"));
                spct.setDonGia(rs.getString("DON_GIA"));
                spct.setSoLuong(rs.getInt("SO_LUONG"));
                spct.setQrCode(rs.getString("QR_CODE_PATH"));

                System.out.println("✅ Truy vấn thành công với mã SPCT: " + maSPCT);
                return spct;
            } else {
                System.out.println("❌ Không tìm thấy dữ liệu với mã: '" + maSPCT + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public SanPham_ChiTiet timSanPhamChiTietTheoMa(String maSPCT){
        String sql = """
                     SELECT * FROM San_Pham_Chi_Tiet WHERE ma_SPCT = ?
                     """;
        SanPham_ChiTiet spct = new SanPham_ChiTiet();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maSPCT);
            rs = ps.executeQuery();
            while(rs.next()){
                spct.setSoLuong(rs.getInt("SO_LUONG"));
                spct.setId(rs.getInt("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spct;
    }

    public List<SanPham_ChiTiet> timKiemSanPhamChiTiet(String timKiem) {
        List<SanPham_ChiTiet> list = new ArrayList();

        sql = """
              SELECT San_Pham_Chi_Tiet.ID, San_Pham_Chi_Tiet.MA_SPCT, San_Pham.TEN, Mau_Sac.TEN, Chat_Lieu.TEN, Kich_Thuoc.TEN, FORMAT(San_Pham_Chi_Tiet.Don_Gia, 'N0') + ' VNĐ' , San_Pham_Chi_Tiet.SO_LUONG
              FROM San_Pham_Chi_Tiet 
              JOIN San_Pham ON San_Pham.ID = San_Pham_Chi_Tiet.ID_SAN_PHAM
              JOIN Mau_Sac ON Mau_Sac.ID = San_Pham_Chi_Tiet.ID_MAU_SAC
              JOIN Chat_Lieu ON Chat_Lieu.ID = San_Pham_Chi_Tiet.ID_CHAT_LIEU
              JOIN Kich_Thuoc ON Kich_Thuoc.ID = San_Pham_Chi_Tiet.ID_KICH_THUOC
              WHERE San_Pham_Chi_Tiet.DA_XOA = 0 AND San_Pham.DA_XOA = 0 AND Mau_Sac.DA_XOA = 0 AND Chat_Lieu.DA_XOA = 0 AND Kich_Thuoc.DA_XOA = 0 
              AND (San_Pham.TEN like ? OR San_Pham_Chi_Tiet.MA_SPCT like ? )
              ORDER BY San_Pham_Chi_Tiet.ID DESC
              """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + timKiem + "%");
            ps.setString(2, "%" + timKiem + "%");
            rs = ps.executeQuery();
            while (rs.next()) {

                int id;
                String maSpct;
                String sp;
                String mauSac;
                String kichThuoc;
                String chatLieu;
                String donGia;
                int soLuong;

                id = rs.getInt(1);
                maSpct = rs.getString(2);
                sp = rs.getString(3);
                kichThuoc = rs.getString(6);
                mauSac = rs.getString(4);
                chatLieu = rs.getString(5);
                donGia = rs.getString(7);
                soLuong = rs.getInt(8);

                list.add(new SanPham_ChiTiet(id, maSpct, sp, kichThuoc, mauSac, chatLieu, donGia, soLuong));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
