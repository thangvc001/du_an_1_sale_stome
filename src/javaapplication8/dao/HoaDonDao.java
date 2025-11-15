package javaapplication8.dao;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javaapplication8.model.HoaDonChiTiet_Model;
import javaapplication8.model.HoaDon_Model;
import javaapplication8.model.LichSuHoaDon;
import javaapplication8.until.DBConnect;

public class HoaDonDao {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<HoaDon_Model> list;

    public HoaDonDao() {
        conn = DBConnect.getConnection();

        list = new ArrayList<>();
    }

    //Tạo nhanh hóa đơn:
    public int taoNhanhHoaDon(String mahd, int idNV, String ngay) {
        String countSql = "SELECT COUNT(*) FROM Hoa_Don WHERE TRANG_THAI = 0";
        String insertSql = "INSERT INTO Hoa_Don(MA_HD, ID_NV, NGAY_TAO, TRANG_THAI) VALUES (?, ?, ?, 0)";
        int idHD = -1;

        try {
            // 1. Kiểm tra số lượng hóa đơn chưa thanh toán
            ps = conn.prepareStatement(countSql);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count >= 10) {
                    return -1; // Quá 10 hóa đơn chưa thanh toán
                }
            }

            // 2. Thêm hóa đơn mới và lấy ID tự tăng
            ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, mahd);
            ps.setInt(2, idNV);
            ps.setString(3, ngay);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idHD = rs.getInt(1); // Lấy ID hóa đơn vừa insert
                }
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

        return idHD; // Trả về ID nếu thành công, -1 nếu thất bại
    }

    public List<HoaDon_Model> danhSachHoaDon() {
        List<HoaDon_Model> list = new ArrayList<>();

        String sql = """
    SELECT 
        Hoa_Don.ID,
        Hoa_Don.ID_KH,
        Hoa_Don.ID_NV,
        Hoa_Don.ID_PGG,
        Hoa_Don.ID_THANH_TOAN,
        Hoa_Don.TONG_TIEN,
        Hoa_Don.MA_HD,
        Hoa_Don.NGAY_TAO,
        NhanVien.MA_NV AS MA_NV,
        Khach_Hang.TEN_KHACH_HANG AS TEN_KH,
        SUM(Hoa_Don_Chi_Tiet.SO_LUONG) AS TONG_SO_SAN_PHAM
    FROM Hoa_Don 
    LEFT JOIN Khach_Hang ON Khach_Hang.id = Hoa_Don.ID_KH
    LEFT JOIN NhanVien ON NhanVien.id = Hoa_Don.ID_NV
    LEFT JOIN Phieu_Giam_Gia ON Phieu_Giam_Gia.id = Hoa_Don.ID_PGG
    LEFT JOIN Hinh_Thuc_Thanh_Toan ON Hinh_Thuc_Thanh_Toan.id = Hoa_Don.ID_THANH_TOAN
    LEFT JOIN Hoa_Don_Chi_Tiet ON Hoa_Don_Chi_Tiet.ID_HD = Hoa_Don.ID
    WHERE Hoa_Don.TRANG_THAI = 0
    GROUP BY 
        Hoa_Don.ID,
        Hoa_Don.ID_KH,
        Hoa_Don.ID_NV,
        Hoa_Don.ID_PGG,
        Hoa_Don.ID_THANH_TOAN,
        Hoa_Don.TONG_TIEN,
        Hoa_Don.MA_HD,
        Hoa_Don.NGAY_TAO,
        NhanVien.MA_NV,
        Khach_Hang.TEN_KHACH_HANG
""";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon_Model model = new HoaDon_Model();
                model.setId(rs.getInt("ID"));
                model.setIdKH(rs.getInt("ID_KH"));
                model.setMaNV(rs.getString("MA_NV"));
                model.setIdNV(rs.getInt("ID_NV"));
                model.setIdPGG(rs.getInt("ID_PGG"));
                model.setIdTT(rs.getInt("ID_THANH_TOAN"));
                model.setTenKhachHang(rs.getString("TEN_KH"));
                model.setTongTien(rs.getString("TONG_TIEN"));
                model.setNgayTao(rs.getString("NGAY_TAO"));
                model.setMaHD(rs.getString("MA_HD"));
                model.setTongSP(rs.getInt("TONG_SO_SAN_PHAM"));
                list.add(model);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi truy vấn danh sách hóa đơn:");
            e.printStackTrace();
        }

        return list;
    }

    public List<HoaDonChiTiet_Model> layChiTietHoaDonTheoID(int idHoaDon) {
        List<HoaDonChiTiet_Model> dsGioHang = new ArrayList<>();
        String sql = """
                    SELECT 
                        hdct.ID_SPCT,
                        spct.MA_SPCT,
                        sp.TEN AS TEN_SP,
                        ms.TEN AS MAU_SAC,
                        cl.TEN AS CHAT_LIEU,
                        kt.TEN AS KICH_THUOC,
                        hdct.DON_GIA AS DON_GIA,
                        hdct.SO_LUONG,
                        (hdct.DON_GIA * hdct.SO_LUONG) AS THANH_TIEN
                    FROM Hoa_Don_Chi_Tiet hdct
                    JOIN San_Pham_Chi_Tiet spct ON hdct.ID_SPCT = spct.ID
                    JOIN San_Pham sp ON spct.ID_SAN_PHAM = sp.ID
                    JOIN Mau_Sac ms ON spct.ID_MAU_SAC = ms.ID
                    JOIN Chat_Lieu cl ON spct.ID_CHAT_LIEU = cl.ID
                    JOIN Kich_Thuoc kt ON spct.ID_KICH_THUOC = kt.ID
                    JOIN Hoa_Don h ON hdct.ID_HD = h.ID
                    WHERE hdct.ID_HD = ? ;

                    """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idHoaDon);
            rs = ps.executeQuery();

            while (rs.next()) {
                HoaDonChiTiet_Model hdct = new HoaDonChiTiet_Model();
                hdct.setMaSPCT(rs.getString("MA_SPCT"));
                hdct.setTenSP(rs.getString("TEN_SP"));
                hdct.setMauSac(rs.getString("MAU_SAC"));
                hdct.setChatLieu(rs.getString("CHAT_LIEU"));
                hdct.setKichThuoc(rs.getString("KICH_THUOC"));
                hdct.setDonGia(rs.getString("DON_GIA"));
                hdct.setSoLuong(rs.getInt("SO_LUONG"));
                hdct.setThanhTien(rs.getString("THANH_TIEN"));
                hdct.setIdSPCT(rs.getInt("ID_SPCT"));
                dsGioHang.add(hdct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsGioHang;
    }
    //Kiểm tra số lượng trong giỏ hàng

    public int laySoLuongTheoSPCT(int idHD, int idSPCT) {
        String sql = """
                     SELECT SO_LUONG FROM Hoa_Don_Chi_Tiet 
                     WHERE ID_HD = ? AND ID_SPCT = ?
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idHD);
            ps.setInt(2, idSPCT);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SO_LUONG");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Cập nhật số lượng khi đã có trong giỏ hàng
    public boolean capNhatSoLuong(int idHD, int IdSPCT, int soLuongMoi) {
        String sql = """
                     UPDATE Hoa_Don_Chi_Tiet SET SO_LUONG = ?
                     WHERE ID_HD = ? AND ID_SPCT = ?
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, soLuongMoi);
            ps.setInt(2, idHD);
            ps.setInt(3, IdSPCT);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean themSanPhamVaoHoaDon(int idHD, int idSPCT, int soLuong, BigDecimal donGia, String maHDCT) {
        System.out.println("So luong: " + soLuong);
        String sql = """
                     INSERT INTO Hoa_Don_Chi_Tiet (ID_HD, ID_SPCT, SO_LUONG, DON_GIA, MA_HDCT)
                     VALUES (?,?,?,?,?);
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idHD);
            ps.setInt(2, idSPCT);
            ps.setInt(3, soLuong);
            ps.setBigDecimal(4, donGia);
            ps.setString(5, maHDCT);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Xoa Sản phẩm khỏi hóa đơn:
    public boolean xoaSanPhamKhoiHoaDon(int idHD, int idSPCT) {
        String sql = """
                DELETE FROM Hoa_Don_Chi_Tiet WHERE ID_HD = ? AND ID_SPCT = ?
                """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idHD);
            ps.setInt(2, idSPCT);
            int rows = ps.executeUpdate();
            System.out.println("++++" + rows + " - " + idHD + " - " + idSPCT);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateHoaDon(String maHD, LocalDateTime thoiGianThanhToan, int idKH, BigDecimal tongTien, int idPhieuGG, int hinhTHucThanhToan, BigDecimal tongTienThucTra) {
        String sqlUpdate = """
        UPDATE Hoa_Don 
        SET ID_KH = ?, 
            ID_PGG = ?, 
            ID_THANH_TOAN = ?,
            TONG_TIEN = ?, 
            TONG_TIEN_KHI_GIAM = ?,
            NGAY_THANH_TOAN = ?, 
            TRANG_THAI = 1
        WHERE MA_HD = ?
    """;

        String sqlInsertLog = """
        INSERT INTO Hanh_Dong_Hoa_Don (ID_HD, ID_HANH_DONG, THOI_GIAN)
        VALUES (?, ?, ?)
    """;

        try {
            if (idPhieuGG == -1) {
                tongTienThucTra = tongTien;
            }

            // 1. Cập nhật hóa đơn
            ps = conn.prepareStatement(sqlUpdate);

            // Set các giá trị
            ps.setObject(1, idKH == -1 ? null : idKH, java.sql.Types.INTEGER);
            ps.setObject(2, idPhieuGG == -1 ? null : idPhieuGG, java.sql.Types.INTEGER);
            ps.setObject(3, hinhTHucThanhToan == -1 ? null : hinhTHucThanhToan, java.sql.Types.INTEGER);
            ps.setBigDecimal(4, tongTien);
            ps.setBigDecimal(5, tongTienThucTra);
            ps.setTimestamp(6, Timestamp.valueOf(thoiGianThanhToan)); // dùng thời gian hiện tại
            ps.setString(7, maHD);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                // 2. Lấy ID_HD từ MA_HD
                String getIdSql = "SELECT ID FROM Hoa_Don WHERE MA_HD = ?";
                PreparedStatement getIdStmt = conn.prepareStatement(getIdSql);
                getIdStmt.setString(1, maHD);
                ResultSet rs = getIdStmt.executeQuery();

                if (rs.next()) {
                    int idHD = rs.getInt("ID");

                    // 3. Ghi log vào bảng Hanh_Dong_Hoa_Don
                    PreparedStatement insertLog = conn.prepareStatement(sqlInsertLog);
                    insertLog.setInt(1, idHD);
                    insertLog.setInt(2, 5); // hành động: thanh toán
                    insertLog.setTimestamp(3, Timestamp.valueOf(thoiGianThanhToan));

                    insertLog.executeUpdate();
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean huyHoaDon(String maHD) {
        String sql = """
                     UPDATE HOA_DON SET TRANG_THAI = 2
                     WHERE MA_HD = ?
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maHD);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public HoaDon_Model layIdHoaDonTheoMa(String maHD) {
        String sql = """
                 SELECT hd.ID 
                 FROM Hoa_Don hd
                 WHERE hd.TRANG_THAI = 0 AND hd.Ma_hd = ?;
                 """;

        HoaDon_Model hd = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maHD);
            rs = ps.executeQuery();
            if (rs.next()) {
                hd = new HoaDon_Model();
                hd.setId(rs.getInt("ID"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }

    public HoaDon_Model layHoaDonTheoMa(String maHD) {
        String sql = """
                     SELECT hd.ID, hd.Ma_HD, hd.Ngay_Tao, hd.Ngay_Thanh_Toan,
                                         hd.Tong_Tien_Khi_Giam, nv.Ma_NV, kh.TEN_KHACH_HANG,
                                         kh.DIA_CHI, kh.SDT, hd.QR_CODE_PATH
                                         FROM Hoa_Don hd
                                         LEFT JOIN NhanVien nv ON hd.ID_NV = nv.ID
                                         LEFT JOIN Khach_Hang kh ON hd.ID_KH = kh.ID
                                         WHERE hd.TRANG_THAI = 1;
                     """;
        HoaDon_Model hd = new HoaDon_Model();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                hd.setId(rs.getInt("ID"));
                hd.setMaHD(rs.getString("Ma_HD"));
                hd.setNgayTao(rs.getString("Ngay_Tao"));
                hd.setNgayTT(rs.getString("NGAY_THANH_TOAN"));

// Format số tiền
                String tongTienStr = rs.getString("TONG_TIEN_KHI_GIAM");
                double tien = 0;
                try {
                    tien = Double.parseDouble(tongTienStr);
                } catch (NumberFormatException e) {
                    // Nếu dữ liệu lỗi, giữ 0
                }
                NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
                String formattedTien = currencyFormat.format(tien) + " VND";
                hd.setTongTienKhiGiam(formattedTien);

// Mã nhân viên
                hd.setMaNV(rs.getString("Ma_NV"));

// Xử lý thông tin khách hàng nếu null
                String tenKH = rs.getString("TEN_KHACH_HANG");
                if (tenKH == null || tenKH.trim().isEmpty()) {
                    hd.setTenKhachHang("Khách bán lẻ");
                    hd.setDiaChiKhachHang("");
                    hd.setSdtKH("");
                } else {
                    hd.setTenKhachHang(tenKH);
                    hd.setDiaChiKhachHang(rs.getString("DIA_CHI"));
                    hd.setSdtKH(rs.getString("SDT"));
                }
                hd.setQrHoaDon(rs.getString("QR_CODE_PATH"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }

    public List<HoaDon_Model> danhsachHoaDonDaThanhToan() {
        List<HoaDon_Model> list = new ArrayList<>();
        String sql = """
                    SELECT hd.ID, hd.Ma_HD, hd.Ngay_Tao, hd.Ngay_Thanh_Toan,
                    hd.Tong_Tien_Khi_Giam, nv.Ma_NV, kh.TEN_KHACH_HANG,
                    kh.DIA_CHI, kh.SDT
                    FROM Hoa_Don hd
                    LEFT JOIN NhanVien nv ON hd.ID_NV = nv.ID
                    LEFT JOIN Khach_Hang kh ON hd.ID_KH = kh.ID
                    WHERE hd.TRANG_THAI = 1;
                    """;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon_Model hd = new HoaDon_Model();
                hd.setId(rs.getInt("ID"));
                hd.setMaHD(rs.getString("Ma_HD"));
                hd.setNgayTao(rs.getString("Ngay_Tao"));
                hd.setNgayTT(rs.getString("NGAY_THANH_TOAN"));

// Format số tiền
                String tongTienStr = rs.getString("TONG_TIEN_KHI_GIAM");
                double tien = 0;
                try {
                    tien = Double.parseDouble(tongTienStr);
                } catch (NumberFormatException e) {
                    // Nếu dữ liệu lỗi, giữ 0
                }
                NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
                String formattedTien = currencyFormat.format(tien) + " VND";
                hd.setTongTienKhiGiam(formattedTien);

// Mã nhân viên
                hd.setMaNV(rs.getString("Ma_NV"));

// Xử lý thông tin khách hàng nếu null
                String tenKH = rs.getString("TEN_KHACH_HANG");
                if (tenKH == null || tenKH.trim().isEmpty()) {
                    hd.setTenKhachHang("Khách bán lẻ");
                    hd.setDiaChiKhachHang("");
                    hd.setSdtKH("");
                } else {
                    hd.setTenKhachHang(tenKH);
                    hd.setDiaChiKhachHang(rs.getString("DIA_CHI"));
                    hd.setSdtKH(rs.getString("SDT"));
                }

                list.add(hd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void hanhDong(int idHD, int hanhDong, LocalDateTime thoiGianInHD, int idNV) {

        System.out.println("idHDong: " + hanhDong);
        String sql = """
                     INSERT INTO Hanh_Dong_Hoa_Don(ID_HD, ID_HANH_DONG, THOI_GIAN, ID_NV)
                     VALUES (?,?,?,?)
                     """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idHD);
            ps.setInt(2, hanhDong);
            ps.setTimestamp(3, Timestamp.valueOf(thoiGianInHD));
            ps.setInt(4, idNV);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<LichSuHoaDon> lichSuHoaDonTheoID(int idHD) {
        List<LichSuHoaDon> lc = new ArrayList<>();
        String sql = """
                    SELECT hdhd.id_new, nv.MA_NV, CONVERT(date, hdhd.THOI_GIAN) AS Ngay,
                    FORMAT(hdhd.THOI_GIAN, 'HH:mm') AS GioPhut,
                    hanhdong.TEN_HANH_DONG 
                    FROM HANH_DONG_HOA_DON hdhd
                    JOIN NhanVien nv ON nv.id = hdhd.ID_NV
                    JOIN HANH_DONG hanhdong ON hanhdong.id = hdhd.id_HANH_DONG
                    WHERE hdhd.id_HD = ?
                     ORDER BY hdhd.id_new DESC
                    """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idHD);
            rs = ps.executeQuery();
            while (rs.next()) {
                LichSuHoaDon ls = new LichSuHoaDon();
                ls.setId(rs.getInt("id_new"));
                ls.setMaNV(rs.getString("MA_NV"));
                ls.setGio(rs.getString("GioPhut"));
                ls.setNgay(rs.getString("Ngay"));
                ls.setHanhDong(rs.getString("TEN_HANH_DONG"));
                lc.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lc;
    }

    public boolean taoVaLuuQR(String maHD) {
        try {
            String data = maHD;
            String path = "qr_hoadon/" + maHD + ".png"; // thư mục qr, nên tạo trước
            int width = 200, height = 200;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            Path filePath = Paths.get(path);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

            // Sau khi tạo xong, cập nhật đường dẫn vào CSDL
            String sql = "UPDATE Hoa_Don SET QR_CODE_PATH = ? WHERE MA_HD = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, path);
            ps.setString(2, maHD);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<HoaDon_Model> danhsachHoaDonTimTheoTuKhoa(String tuKhoa) {
        List<HoaDon_Model> list = new ArrayList<>();
        String sql = """
                    SELECT hd.ID, hd.Ma_HD, hd.Ngay_Tao, hd.Ngay_Thanh_Toan,
                    hd.Tong_Tien_Khi_Giam, nv.Ma_NV, kh.TEN_KHACH_HANG,
                    kh.DIA_CHI, kh.SDT
                    FROM Hoa_Don hd
                    LEFT JOIN NhanVien nv ON hd.ID_NV = nv.ID
                    LEFT JOIN Khach_Hang kh ON hd.ID_KH = kh.ID
                    WHERE hd.TRANG_THAI = 1 AND (hd.Ma_HD like ? OR kh.TEN_KHACH_HANG like ?);
                    """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + tuKhoa + "%");
            ps.setString(2, "%" + tuKhoa + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon_Model hd = new HoaDon_Model();
                hd.setId(rs.getInt("ID"));
                hd.setMaHD(rs.getString("Ma_HD"));
                hd.setNgayTao(rs.getString("Ngay_Tao"));
                hd.setNgayTT(rs.getString("NGAY_THANH_TOAN"));

// Format số tiền
                String tongTienStr = rs.getString("TONG_TIEN_KHI_GIAM");
                double tien = 0;
                try {
                    tien = Double.parseDouble(tongTienStr);
                } catch (NumberFormatException e) {
                    // Nếu dữ liệu lỗi, giữ 0
                }
                NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
                String formattedTien = currencyFormat.format(tien) + " VND";
                hd.setTongTienKhiGiam(formattedTien);

// Mã nhân viên
                hd.setMaNV(rs.getString("Ma_NV"));

// Xử lý thông tin khách hàng nếu null
                String tenKH = rs.getString("TEN_KHACH_HANG");
                if (tenKH == null || tenKH.trim().isEmpty()) {
                    hd.setTenKhachHang("Khách bán lẻ");
                    hd.setDiaChiKhachHang("");
                    hd.setSdtKH("");
                } else {
                    hd.setTenKhachHang(tenKH);
                    hd.setDiaChiKhachHang(rs.getString("DIA_CHI"));
                    hd.setSdtKH(rs.getString("SDT"));
                }

                list.add(hd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDon_Model> danhsachHoaDonTimTheoMa(String tuKhoa) {
        List<HoaDon_Model> list = new ArrayList<>();
        String sql = """
                    SELECT hd.ID, hd.Ma_HD, hd.Ngay_Tao, hd.Ngay_Thanh_Toan,
                    hd.Tong_Tien_Khi_Giam, nv.Ma_NV, kh.TEN_KHACH_HANG,
                    kh.DIA_CHI, kh.SDT
                    FROM Hoa_Don hd
                    LEFT JOIN NhanVien nv ON hd.ID_NV = nv.ID
                    LEFT JOIN Khach_Hang kh ON hd.ID_KH = kh.ID
                    WHERE hd.TRANG_THAI = 1 AND hd.Ma_HD = ? ;
                    """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tuKhoa);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon_Model hd = new HoaDon_Model();
                hd.setId(rs.getInt("ID"));
                hd.setMaHD(rs.getString("Ma_HD"));
                hd.setNgayTao(rs.getString("Ngay_Tao"));
                hd.setNgayTT(rs.getString("NGAY_THANH_TOAN"));

// Format số tiền
                String tongTienStr = rs.getString("TONG_TIEN_KHI_GIAM");
                double tien = 0;
                try {
                    tien = Double.parseDouble(tongTienStr);
                } catch (NumberFormatException e) {
                    // Nếu dữ liệu lỗi, giữ 0
                }
                NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
                String formattedTien = currencyFormat.format(tien) + " VND";
                hd.setTongTienKhiGiam(formattedTien);

// Mã nhân viên
                hd.setMaNV(rs.getString("Ma_NV"));

// Xử lý thông tin khách hàng nếu null
                String tenKH = rs.getString("TEN_KHACH_HANG");
                if (tenKH == null || tenKH.trim().isEmpty()) {
                    hd.setTenKhachHang("Khách bán lẻ");
                    hd.setDiaChiKhachHang("");
                    hd.setSdtKH("");
                } else {
                    hd.setTenKhachHang(tenKH);
                    hd.setDiaChiKhachHang(rs.getString("DIA_CHI"));
                    hd.setSdtKH(rs.getString("SDT"));
                }

                list.add(hd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
