package javaapplication8.form;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import javaapplication8.model.HoaDonChiTiet_Model;
import javaapplication8.model.HoaDon_Model;
import javaapplication8.model.KhachHangModel;
import javaapplication8.model.Model_NhanVien;
import javaapplication8.model.Model_SanPham;
import javaapplication8.model.PhieuGiamGiaModel;
import javaapplication8.model.PhuongThucThanhToan;
import javaapplication8.model.SanPham_ChiTiet;
import javaapplication8.model.SanPham_ThuocTinh;
import javaapplication8.service.HoaDonService;
import javaapplication8.service.KhachHangService;
import javaapplication8.service.PhieuGiamGiaService;
import javaapplication8.service.PhuongThucThanhToanService;
import javaapplication8.service.SanPhamChiTietService;
import javaapplication8.service.SanPhamService;
import javaapplication8.service.SanPhamThuocTinhService;
import javaapplication8.service.serviceimpl.HoaDonServiceImpl;
import javaapplication8.service.serviceimpl.KhachHangServiceImpl;
import javaapplication8.service.serviceimpl.PhieuGiamGiaServiceImpl;
import javaapplication8.service.serviceimpl.PhuongThucThanhToanServiceImp;
import javaapplication8.service.serviceimpl.SanPhamChiTietServiceImpl;
import javaapplication8.service.serviceimpl.SanPhamServiceImpl;
import javaapplication8.service.serviceimpl.SanPhamThuocTinhServiceImpl;
import javaapplication8.until.CodeGeneratorUtil;
import javaapplication8.until.PhanTrang;
import javaapplication8.until.QRCodeGenerator;
import javaapplication8.until.STT;
import javaapplication8.until.Text;
import javaapplication8.until.ValidationUtil;
import javaapplication8.until.WebCamChonSL;
import javaapplication8.until.WebCamQr;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class HoaDon_Form extends javax.swing.JPanel {

    private SanPhamChiTietService snctService = new SanPhamChiTietServiceImpl();
    private Model_NhanVien nhanVien;
    private HoaDonService hoaDonService = new HoaDonServiceImpl();
    private PhuongThucThanhToanService ptttService = new PhuongThucThanhToanServiceImp();
    private PhieuGiamGiaService pggService = new PhieuGiamGiaServiceImpl();
    private String currentMaHD;
    private int currentIdHD = -1;
    private KhachHangService khachHangService = new KhachHangServiceImpl();
    private int dongHoaDon = -1;
    private final SanPhamThuocTinhService service_spthuoctinh = new SanPhamThuocTinhServiceImpl();
    final SanPhamChiTietService service_spChiTiet = new SanPhamChiTietServiceImpl();

    private HashMap<String, Integer> sanPhamDangBanMap = new HashMap<>();
    private HashMap<String, Integer> sanPhamNgungBanMap = new HashMap<>();
    private HashMap<String, Integer> mauSacMap = new HashMap<>();
    private HashMap<String, Integer> kichThuocMap = new HashMap<>();
    private HashMap<String, Integer> chatLieuMap = new HashMap<>();

    private HashMap<String, Integer> idMauSacTheoMa = new HashMap<>();
    private HashMap<String, Integer> idKichThuocTheoMa = new HashMap<>();
    private HashMap<String, Integer> idChatLieuTheoMa = new HashMap<>();

    private HashMap<String, Integer> idSanPhamTheoMa = new HashMap<>();

    private LichSu_Form lichSu;

    private final SanPhamService service_sp = new SanPhamServiceImpl();

    private PhanTrang phanTrang;  // Đối tượng phân trang cho sản phẩm
    int currentPage = 1;  // Trang mặc định là 1
    int limit = 8;        // Số sản phẩm mỗi trang
    int offset = (currentPage - 1) * limit; // Tính toán offset

    public void setNhanVien(Model_NhanVien nv) {
        this.nhanVien = nv;
    }

    public HoaDon_Form(Model_NhanVien nv, LichSu_Form lichSu_Form) {
        initComponents();

        this.lichSu = lichSu_Form;

        //fill bảng sản phẩm chi tiết
        fillSanPhamChiTiet(snctService.getAllSanPhamChiTiet());
        fillBangHoaDon(hoaDonService.danhSachHoaDon());

        //ẩn cot id cua bang hoa don
        tableHoaDon.getColumnModel().getColumn(6).setMinWidth(0);
        tableHoaDon.getColumnModel().getColumn(6).setMaxWidth(0);
        tableHoaDon.getColumnModel().getColumn(6).setWidth(0);

        cbo_httt.removeAllItems();
        List<PhuongThucThanhToan> pt = ptttService.layPhuongThucThanhToan();
        cbo_httt.addItem("Cả 2");
        for (PhuongThucThanhToan phuongThucThanhToan : pt) {
            cbo_httt.addItem(phuongThucThanhToan.getTenPT());
        }

        cbo_phieugiamgia.removeAllItems();
        List<PhieuGiamGiaModel> phieuGiamGia = pggService.getByTrangThai(1);
        cbo_phieugiamgia.addItem("Không chọn");
        for (PhieuGiamGiaModel phieuGiamGiaModel : phieuGiamGia) {
            cbo_phieugiamgia.addItem(phieuGiamGiaModel.getTenPGG());
        }

        txt_thongtinkhachhangten.setText("Khách bán lẻ");

        txt_timkiem.setText("Nhập mã, tên để tìm kiếm");
        txt_timkiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_timkiem.getText().equals("Nhập mã, tên để tìm kiếm")) {
                    txt_timkiem.setText("");
                    txt_timkiem.setForeground(Color.BLACK); // chuyển về màu chữ bình thường
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_timkiem.getText().isEmpty()) {
                    txt_timkiem.setText("Nhập mã, tên để tìm kiếm");
                    txt_timkiem.setForeground(Color.GRAY);
                }
            }
        });

        txt_timkiem.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                timKiemNgay();
            }

            public void removeUpdate(DocumentEvent e) {
                timKiemNgay();
            }

            public void changedUpdate(DocumentEvent e) {
                timKiemNgay();
            }
        });

        loadComboboxLoaiSanPham();
        loadComboboxLoaiMauSac();
        loadComboboxLoaiKichThuoc();
        loadComboboxLoaiChatlieu();

        cbo_sanpham.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                apDungBoLoc();
            }
        });
        cbo_mausac.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                apDungBoLoc();
            }
        });
        cbo_chatlieu.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                apDungBoLoc();
            }
        });
        cbo_kichthuoc.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                apDungBoLoc();
            }
        });

    }

    private void apDungBoLoc() {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>((DefaultTableModel) tbl_bangsanphamchitiet.getModel());
        tbl_bangsanphamchitiet.setRowSorter(rowSorter);

        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        String sanPham = cbo_sanpham.getSelectedItem().toString();
        if (!sanPham.equals("Sản phẩm")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(sanPham), 2)); // Tên sản phẩm (cột 2)
        }

        String mauSac = cbo_mausac.getSelectedItem().toString();
        if (!mauSac.equals("Màu sắc")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(mauSac), 3)); // Màu sắc (cột 3)
        }

        String chatLieu = cbo_chatlieu.getSelectedItem().toString();
        if (!chatLieu.equals("Chất liệu")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(chatLieu), 4)); // Chất liệu (cột 4)
        }

        String kichThuoc = cbo_kichthuoc.getSelectedItem().toString();
        if (!kichThuoc.equals("Kích thước")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(kichThuoc), 5)); // Kích thước (cột 5)
        }

        rowSorter.setRowFilter(RowFilter.andFilter(filters));

    }

    void loadComboboxLoaiSanPham() {

        cbo_sanpham.removeAllItems();
        sanPhamDangBanMap.clear();
        sanPhamNgungBanMap.clear();

        cbo_sanpham.addItem("Sản phẩm"); // ✅ Thêm dòng mặc định

        // Lấy sản phẩm đang bán và ngừng bán với phân trang
        List<Model_SanPham> dsSanPhamDangBan = service_sp.getPage(offset, limit, 0); // 0 là trạng thái "đang bán"
        List<Model_SanPham> dsSanPhamNgungBan = service_sp.getPage(offset, limit, 1); // 1 là trạng thái "ngừng bán"

        for (Model_SanPham ms : dsSanPhamDangBan) {
            cbo_sanpham.addItem(ms.getTen());
            sanPhamDangBanMap.put(ms.getTen(), ms.getId());
        }

        for (Model_SanPham ms : dsSanPhamNgungBan) {
            cbo_sanpham.addItem(ms.getTen());
            sanPhamNgungBanMap.put(ms.getTen(), ms.getId());
        }

        cbo_sanpham.setSelectedItem("Sản phẩm"); // ✅ Chọn mặc định
    }

    void loadComboboxLoaiMauSac() {
        cbo_mausac.removeAllItems();
        mauSacMap.clear();

        cbo_mausac.addItem("Màu sắc"); // ✅ Thêm dòng mặc định

        List<SanPham_ThuocTinh> dsMauSac = service_spthuoctinh.getLoaiMauSac();
        for (SanPham_ThuocTinh ms : dsMauSac) {
            cbo_mausac.addItem(ms.getTen());
            mauSacMap.put(ms.getTen(), ms.getId());
        }

        cbo_mausac.setSelectedItem("Màu sắc"); // ✅ Chọn mặc định
    }

    void loadComboboxLoaiKichThuoc() {
        cbo_kichthuoc.removeAllItems();
        kichThuocMap.clear();
        cbo_kichthuoc.addItem("Kích thước"); // ✅ Thêm dòng mặc định

        List<SanPham_ThuocTinh> dsKichThuoc = service_spthuoctinh.getLoaiKichThuoc();
        for (SanPham_ThuocTinh ms : dsKichThuoc) {
            cbo_kichthuoc.addItem(ms.getTen());
            kichThuocMap.put(ms.getTen(), ms.getId());
        }

        cbo_kichthuoc.setSelectedItem("Kích thước"); // ✅ Chọn mặc định
    }

    void loadComboboxLoaiChatlieu() {
        cbo_chatlieu.removeAllItems();
        chatLieuMap.clear();

        cbo_chatlieu.addItem("Chất liệu"); // ✅ Thêm dòng mặc định

        List<SanPham_ThuocTinh> dsChatLieu = service_spthuoctinh.getLoaiChatLieu();
        for (SanPham_ThuocTinh ms : dsChatLieu) {
            cbo_chatlieu.addItem(ms.getTen());
            chatLieuMap.put(ms.getTen(), ms.getId());
        }

        cbo_chatlieu.setSelectedItem("Chất liệu"); // ✅ Chọn mặc định
    }

    public HashMap<String, Integer> getSanPhamDangBanMap() {
        return sanPhamDangBanMap;
    }

    public HashMap<String, Integer> getSanPhamNgungBanMap() {
        return sanPhamNgungBanMap;
    }

    public HashMap<String, Integer> getMauSacMap() {
        return mauSacMap;
    }

    public HashMap<String, Integer> getKichThuocMap() {
        return kichThuocMap;
    }

    public HashMap<String, Integer> getChatLieuMap() {
        return chatLieuMap;
    }

    public void timKiemNgay() {
        String timKiem = txt_timkiem.getText().trim().toLowerCase();
        List<SanPham_ChiTiet> list = service_spChiTiet.timKiemSanPhamChiTiet(timKiem);
        DefaultTableModel model = (DefaultTableModel) tbl_bangsanphamchitiet.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (SanPham_ChiTiet sp : list) {
            model.addTableModelListener(e -> STT.updateSTT(model, 1));
            model.addRow(new Object[]{
                null,
                sp.getMaSp(),
                sp.getTenSp(),
                sp.getMauSac(),
                sp.getChatLieu(),
                sp.getKichThuoc(),
                sp.getSoLuong(),
                sp.getDonGia(),
                sp.getId()});
            System.out.println("" + sp.getId());
        }
    }

    //Phương thức fill san phẩm chi tiết:
    public void fillSanPhamChiTiet(List<SanPham_ChiTiet> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_bangsanphamchitiet.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (SanPham_ChiTiet sp : list) {
            model.addTableModelListener(e -> STT.updateSTT(model, 1));
            model.addRow(new Object[]{
                null,
                sp.getMaSp(),
                sp.getTenSp(),
                sp.getMauSac(),
                sp.getChatLieu(),
                sp.getKichThuoc(),
                sp.getSoLuong(),
                sp.getDonGia(),
                sp.getId()});
            System.out.println("" + sp.getId());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btn_taohoad = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableGioHang = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_bangsanphamchitiet = new javax.swing.JTable();
        txt_timkiem = new javax.swing.JTextField();
        cbo_sanpham = new javax.swing.JComboBox<>();
        cbo_mausac = new javax.swing.JComboBox<>();
        cbo_chatlieu = new javax.swing.JComboBox<>();
        cbo_kichthuoc = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt_makhthongtinkhachhang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_thongtinkhachhangten = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txt_ngaytao = new javax.swing.JTextField();
        txt_ngaythanhtoan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txt_manv = new javax.swing.JTextField();
        txt_mahd = new javax.swing.JTextField();
        txt_tongtien = new javax.swing.JTextField();
        txt_tenkhachhang = new javax.swing.JTextField();
        cbo_phieugiamgia = new javax.swing.JComboBox<>();
        cbo_httt = new javax.swing.JComboBox<>();
        txt_tienkhachdua = new javax.swing.JTextField();
        txt_tienkhachchuyenkhoan = new javax.swing.JTextField();
        txt_tienthua = new javax.swing.JTextField();
        lbl_tongtienthuc = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        lbl_tienkhachdua = new javax.swing.JLabel();
        lbl_tongtienthuc1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("BÁN HÀNG");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Hóa đơn");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(153, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 204));
        jButton1.setText("Quét mã SP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_taohoad.setBackground(new java.awt.Color(153, 255, 255));
        btn_taohoad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_taohoad.setForeground(new java.awt.Color(0, 0, 204));
        btn_taohoad.setText("Tạo hóa đơn");
        btn_taohoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taohoadActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(153, 255, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 204));
        jButton3.setText("Làm mới");

        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Mã hóa đơn", "Ngày tạo", "Nhân viên", "Tổng SP", "Trạng thái", "ID"
            }
        ));
        tableHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableHoaDon);
        if (tableHoaDon.getColumnModel().getColumnCount() > 0) {
            tableHoaDon.getColumnModel().getColumn(0).setMinWidth(30);
            tableHoaDon.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableHoaDon.getColumnModel().getColumn(0).setMaxWidth(30);
            tableHoaDon.getColumnModel().getColumn(1).setResizable(false);
            tableHoaDon.getColumnModel().getColumn(6).setMinWidth(0);
            tableHoaDon.getColumnModel().getColumn(6).setPreferredWidth(0);
            tableHoaDon.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btn_taohoad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btn_taohoad)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Giỏ hàng");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tableGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Mã SPCT", "Tên SP", "Màu sắc", "Chất liệu", "Kích thước", "Giá bán", "Số lượng", "Thành tiền", "idSPCT"
            }
        ));
        tableGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableGioHang);
        if (tableGioHang.getColumnModel().getColumnCount() > 0) {
            tableGioHang.getColumnModel().getColumn(0).setMinWidth(30);
            tableGioHang.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableGioHang.getColumnModel().getColumn(0).setMaxWidth(30);
            tableGioHang.getColumnModel().getColumn(1).setResizable(false);
            tableGioHang.getColumnModel().getColumn(7).setMinWidth(70);
            tableGioHang.getColumnModel().getColumn(7).setPreferredWidth(70);
            tableGioHang.getColumnModel().getColumn(7).setMaxWidth(70);
            tableGioHang.getColumnModel().getColumn(8).setMinWidth(150);
            tableGioHang.getColumnModel().getColumn(8).setPreferredWidth(150);
            tableGioHang.getColumnModel().getColumn(8).setMaxWidth(150);
            tableGioHang.getColumnModel().getColumn(9).setMinWidth(0);
            tableGioHang.getColumnModel().getColumn(9).setPreferredWidth(0);
            tableGioHang.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Danh sách sản phẩm");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tbl_bangsanphamchitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Mã SPCT", "Tên SP", "Màu sắc", "Chất liệu", "Kích thước", "SL tồn", "Đơn giá", "idSPCT"
            }
        ));
        tbl_bangsanphamchitiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangsanphamchitietMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_bangsanphamchitiet);
        if (tbl_bangsanphamchitiet.getColumnModel().getColumnCount() > 0) {
            tbl_bangsanphamchitiet.getColumnModel().getColumn(0).setMinWidth(30);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(0).setPreferredWidth(30);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(0).setMaxWidth(30);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(1).setResizable(false);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(6).setMinWidth(50);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(6).setPreferredWidth(50);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(6).setMaxWidth(50);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(8).setMinWidth(0);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(8).setPreferredWidth(0);
            tbl_bangsanphamchitiet.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        txt_timkiem.setText("jTextField1");

        cbo_sanpham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_mausac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_chatlieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_kichthuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(cbo_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(cbo_mausac, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(cbo_chatlieu, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(cbo_kichthuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_mausac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_chatlieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_kichthuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Đơn hàng");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 255));
        jLabel6.setText("Thông tin khách hàng");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Mã KH");

        txt_makhthongtinkhachhang.setEditable(false);
        txt_makhthongtinkhachhang.setText("###");
        txt_makhthongtinkhachhang.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)), null));

        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Tên KH");

        txt_thongtinkhachhangten.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)), null));

        jButton4.setBackground(new java.awt.Color(153, 255, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 255));
        jButton4.setText("Chọn");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 255, 255));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 0, 255));
        jButton7.setText("Hủy ");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_makhthongtinkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4))
                    .addComponent(jLabel8)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_thongtinkhachhangten, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_makhthongtinkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 35, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(txt_thongtinkhachhangten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 255));
        jLabel9.setText("Thông tin hóa đơn");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton5.setBackground(new java.awt.Color(153, 255, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 255));
        jButton5.setText("Hủy HD");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel12.setText("Mã hóa đơn");

        txt_ngaytao.setEditable(false);
        txt_ngaytao.setText("###");
        txt_ngaytao.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)), null));
        txt_ngaytao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ngaytaoActionPerformed(evt);
            }
        });

        txt_ngaythanhtoan.setEditable(false);
        txt_ngaythanhtoan.setText("###");
        txt_ngaythanhtoan.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)), null));
        txt_ngaythanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ngaythanhtoanActionPerformed(evt);
            }
        });

        jLabel13.setText("Ngày thanh toán");

        jLabel14.setText("Mã nhân viên");

        jLabel15.setText("Tên khách hàng");

        jLabel16.setText("Tổng tiền");

        jLabel17.setText("Ngày tạo");

        jLabel18.setText("Phiếu giảm giá");

        jLabel19.setText("HT thanh toán");

        jLabel20.setText("Tiền khách đưa");

        jLabel21.setText("Tiền khách CK");

        jLabel22.setText("Tiền thừa");

        txt_manv.setEditable(false);
        txt_manv.setText("###");
        txt_manv.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)), null));
        txt_manv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_manvActionPerformed(evt);
            }
        });

        txt_mahd.setEditable(false);
        txt_mahd.setText("###");
        txt_mahd.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)), null));
        txt_mahd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_mahdActionPerformed(evt);
            }
        });

        txt_tongtien.setEditable(false);
        txt_tongtien.setBackground(new java.awt.Color(255, 255, 255));
        txt_tongtien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_tongtien.setForeground(new java.awt.Color(255, 0, 51));
        txt_tongtien.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)), null));
        txt_tongtien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tongtienActionPerformed(evt);
            }
        });

        txt_tenkhachhang.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)), null));
        txt_tenkhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenkhachhangActionPerformed(evt);
            }
        });

        cbo_phieugiamgia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_phieugiamgia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_phieugiamgiaActionPerformed(evt);
            }
        });

        cbo_httt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_httt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_htttActionPerformed(evt);
            }
        });

        txt_tienkhachdua.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_tienkhachduaCaretUpdate(evt);
            }
        });
        txt_tienkhachdua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tienkhachduaActionPerformed(evt);
            }
        });
        txt_tienkhachdua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_tienkhachduaKeyReleased(evt);
            }
        });

        txt_tienthua.setEditable(false);
        txt_tienthua.setBorder(null);

        lbl_tongtienthuc.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_tongtienthuc.setForeground(new java.awt.Color(255, 0, 0));

        jButton6.setBackground(new java.awt.Color(153, 255, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 0, 255));
        jButton6.setText("Thanh toán");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        lbl_tienkhachdua.setForeground(new java.awt.Color(255, 0, 51));

        lbl_tongtienthuc1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_tongtienthuc1.setForeground(new java.awt.Color(255, 0, 0));
        lbl_tongtienthuc1.setText("Tổng: ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_ngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(txt_ngaythanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_manv, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_mahd, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_phieugiamgia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tongtien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_tenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(57, 57, 57)
                        .addComponent(txt_tienthua))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(33, 33, 33)
                        .addComponent(txt_tienkhachchuyenkhoan))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_httt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_tienkhachdua)
                            .addComponent(lbl_tienkhachdua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lbl_tongtienthuc1)
                        .addGap(30, 30, 30)
                        .addComponent(lbl_tongtienthuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_mahd, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_ngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ngaythanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_manv, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_tenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cbo_phieugiamgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cbo_httt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txt_tienkhachdua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_tienkhachdua, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txt_tienkhachchuyenkhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txt_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_tongtienthuc1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_tongtienthuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(380, 380, 380)
                        .addComponent(jLabel1))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_ngaytaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ngaytaoActionPerformed
    }//GEN-LAST:event_txt_ngaytaoActionPerformed

    private void txt_ngaythanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ngaythanhtoanActionPerformed
    }//GEN-LAST:event_txt_ngaythanhtoanActionPerformed

    private void txt_manvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_manvActionPerformed
    }//GEN-LAST:event_txt_manvActionPerformed

    private void txt_mahdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_mahdActionPerformed
    }//GEN-LAST:event_txt_mahdActionPerformed

    private void txt_tongtienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tongtienActionPerformed
    }//GEN-LAST:event_txt_tongtienActionPerformed

    private void txt_tenkhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenkhachhangActionPerformed
    }//GEN-LAST:event_txt_tenkhachhangActionPerformed

    private void btn_taohoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taohoadActionPerformed
        String maHD = CodeGeneratorUtil.generateHoaDon();
        Date today = new Date();
        String formatted = new SimpleDateFormat("yyyy-MM-dd").format(today);
        String maNV = nhanVien.getMaNV();
        int idNV = nhanVien.getId();
        String trangThai = Text.CHUA_THANH_TOAN;

        int xacNhanTaoHoaDon = JOptionPane.showConfirmDialog(this, Text.TAO_NHANH_HOA_DON, Text.XAC_NHAN, JOptionPane.YES_NO_OPTION);

        if (xacNhanTaoHoaDon == JOptionPane.YES_OPTION) {
            int taoHoaDon = hoaDonService.taoNhanhHoaDonS(maHD, idNV, formatted);

            boolean taovaLuuQR = hoaDonService.taoVaLuuQR(maHD);
            fillBangHoaDon(hoaDonService.danhSachHoaDon());
        } else {
            JOptionPane.showMessageDialog(this, Text.SO_LUONG_HOA_DON, Text.CANH_BAO, JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_taohoadActionPerformed

    private void tableHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonMouseClicked

        dongHoaDon = tableHoaDon.getSelectedRow();
        if (dongHoaDon >= 0) {
            String maHD = tableHoaDon.getValueAt(dongHoaDon, 1).toString();
            int idHD = Integer.parseInt(tableHoaDon.getValueAt(dongHoaDon, 6).toString());

            this.currentMaHD = maHD;
            this.currentIdHD = idHD;

            loadGioHang(idHD);

            txt_mahd.setText(maHD);
            txt_ngaytao.setText(tableHoaDon.getValueAt(dongHoaDon, 2).toString());

            Date ngayThanhToan = new Date(); // Ngày hiện tại
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày
            txt_ngaythanhtoan.setText(sdf.format(ngayThanhToan));
            txt_manv.setText(nhanVien.getMaNV());

            cbo_phieugiamgia.setSelectedIndex(0);
            cbo_httt.setSelectedIndex(0);
            tinhTongTienTuGioHang();
            txt_tenkhachhang.setText(txt_thongtinkhachhangten.getText());

        }


    }//GEN-LAST:event_tableHoaDonMouseClicked


    private void tbl_bangsanphamchitietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangsanphamchitietMouseClicked
        int sanPhamChiTiet = tbl_bangsanphamchitiet.getSelectedRow();
        System.out.println("crrr" + currentIdHD);
        String maHD = txt_mahd.getText().trim();
        if (currentIdHD < 0 || maHD.isEmpty() || maHD.equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idSPCT = (int) tbl_bangsanphamchitiet.getValueAt(sanPhamChiTiet, 8);
        if (sanPhamChiTiet >= 0) {
            NhapSoLuongMuonMua nhap = new NhapSoLuongMuonMua(idSPCT, currentIdHD, () -> {
                fillBangHoaDon(hoaDonService.danhSachHoaDon());
                fillBangGioHang(hoaDonService.layChiTietHoaDonTheoId(currentIdHD));
                fillSanPhamChiTiet(snctService.getAllSanPhamChiTiet());
                tinhTongTienTuGioHang();

            });
            nhap.setVisible(true);
        }

    }//GEN-LAST:event_tbl_bangsanphamchitietMouseClicked

    private void tableGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGioHangMouseClicked
        int row = tableGioHang.getSelectedRow();
        if (row < 0) {
            return;
        }

        int idSPCT = Integer.parseInt(tableGioHang.getValueAt(row, 9).toString());
        System.out.println("idSPCT===" + idSPCT);
        int soLuongCu = Integer.parseInt(tableGioHang.getValueAt(row, 7).toString());

        new CapNhatSoLuongSanPham(currentIdHD, idSPCT, soLuongCu, soLuongMoi -> {
            int chenhLech = soLuongMoi - soLuongCu;

//            if (soLuongMoi == 0) {
//                hoaDonService.xoaSanPhamKhoiHoaDon(currentIdHD, idSPCT);
//                snctService.congSoLuongTon(idSPCT, soLuongCu); // trả lại toàn bộ tồn kho
//            } else {
//                hoaDonService.capNhatSoLuong(currentIdHD, idSPCT, soLuongMoi);
//                if (chenhLech > 0) {
//                    snctService.truSoLuongTon(idSPCT, chenhLech); // mua thêm → trừ tồn kho
//                } else if (chenhLech < 0) {
//                    snctService.congSoLuongTon(idSPCT, -chenhLech); // giảm bớt → cộng lại
//                }
//            }
            // Refresh lại bảng
            fillBangGioHang(hoaDonService.layChiTietHoaDonTheoId(currentIdHD));
            fillSanPhamChiTiet(snctService.getAllSanPhamChiTiet());
            fillBangHoaDon(hoaDonService.danhSachHoaDon());
            tinhTongTienTuGioHang();
        }).setVisible(true);

    }//GEN-LAST:event_tableGioHangMouseClicked

    public int getIdHoaDonDangChon() {
        System.out.println("hoa don dang duoc hon:" + currentIdHD);
        return this.currentIdHD;
    }

    public void reloadTatCaSauKhiThemSP() {
        fillBangHoaDon(hoaDonService.danhSachHoaDon());
        fillBangGioHang(hoaDonService.layChiTietHoaDonTheoId(currentIdHD));
        fillSanPhamChiTiet(snctService.getAllSanPhamChiTiet());
        tinhTongTienTuGioHang();
    }

    private void txt_tienkhachduaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tienkhachduaKeyReleased

    }//GEN-LAST:event_txt_tienkhachduaKeyReleased

    private void txt_tienkhachduaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_tienkhachduaCaretUpdate
        try {
            String tienKhachDuaStr = txt_tienkhachdua.getText().trim()
                    .replace("VND", "").replace(",", "").replace(" ", "");
            String tongTienStr = lbl_tongtienthuc.getText().trim()
                    .replace("VND", "").replace(",", "").replace(" ", "");

            if (tienKhachDuaStr.isEmpty() || tongTienStr.isEmpty()) {
                txt_tienkhachchuyenkhoan.setText("0 VND");
                txt_tienthua.setText("0 VND");
                return;
            }

            int tienKhachDua = Integer.parseInt(tienKhachDuaStr);
            int tongTien = Integer.parseInt(tongTienStr);

            int tienChuyenKhoan = 0;
            int tienThua = 0;

            if (tienKhachDua >= tongTien) {
                tienThua = tienKhachDua - tongTien;
                tienChuyenKhoan = 0;
            } else {
                tienChuyenKhoan = tongTien - tienKhachDua;
                tienThua = 0;
            }

            DecimalFormat df = new DecimalFormat("#,###");
            txt_tienkhachchuyenkhoan.setText(df.format(tienChuyenKhoan) + " VND");
            txt_tienthua.setText(df.format(tienThua) + " VND");

            // ✅ Nếu hợp lệ thì xóa thông báo lỗi
            lbl_tienkhachdua.setText("");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            lbl_tienkhachdua.setText("Vui lòng nhập số tiền hợp lệ!");
            txt_tienkhachchuyenkhoan.setText("0 VND");
            txt_tienthua.setText("0 VND");
        }


    }//GEN-LAST:event_txt_tienkhachduaCaretUpdate

    private void txt_tienkhachduaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tienkhachduaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tienkhachduaActionPerformed

    private void cbo_htttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_htttActionPerformed
        // TODO add your handling code here:
        Object selected = cbo_httt.getSelectedItem();
        if (selected == null) {
            return; // tránh lỗi khi combo chưa sẵn sàng
        }
        String phuongThuc = selected.toString();

        switch (phuongThuc) {
            case "Tiền mặt":
                txt_tienkhachdua.setEnabled(true);
                txt_tienkhachchuyenkhoan.setEnabled(false);
                txt_tienkhachchuyenkhoan.setText("0 VND");
                break;
            case "Chuyển khoản":
                txt_tenkhachhang.setEnabled(false);
                txt_tienkhachdua.setText("0 VND");
                txt_tienkhachchuyenkhoan.setEnabled(true);
                break;
            case "Cả 2":
                txt_tienkhachdua.setEnabled(true);
                txt_tienkhachchuyenkhoan.setEnabled(true);
                break;
            default:
                // Có thể set mặc định hoặc bỏ qua
                break;
        }
        tinhTien();
    }//GEN-LAST:event_cbo_htttActionPerformed

    private void cbo_phieugiamgiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_phieugiamgiaActionPerformed
        String maHoaDon = txt_mahd.getText().trim();

        // Kiểm tra nếu chưa có mã hóa đơn
        if (maHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hoặc tạo hóa đơn trước!");
            cbo_phieugiamgia.setSelectedIndex(0); // Đặt lại lựa chọn về "Không chọn"
            return;
        }

        // Lấy giá trị đã chọn từ ComboBox
        Object selected = cbo_phieugiamgia.getSelectedItem();
        if (selected == null || selected.toString().equalsIgnoreCase("Không chọn")) {
            lbl_tongtienthuc.setText(txt_tongtien.getText()); // Không giảm giá, hiển thị tổng tiền gốc
            return;
        }

        // Kiểm tra nếu tổng tiền không hợp lệ
        if (txt_tongtien.getText().trim().isEmpty()) {
            lbl_tongtienthuc.setText("");  // Không có tổng tiền, xóa thông tin tổng tiền thực
            return;
        }

        String phieuGiamGia = selected.toString().trim();
        String tongTienStr = txt_tongtien.getText().trim()
                .replace("VND", "")
                .replace(",", "")
                .replace(" ", "");

        try {
            // Chuyển đổi tổng tiền sang BigDecimal
            BigDecimal tongTien = new BigDecimal(tongTienStr);
            PhieuGiamGiaModel phieuGiamGiaModel = pggService.layPhieuGiamGiaTheoTen(phieuGiamGia);

            if (phieuGiamGiaModel == null) {
                JOptionPane.showMessageDialog(null, "Phiếu giảm giá không tồn tại!");
                return;
            }

            BigDecimal hoaDonToiThieu = phieuGiamGiaModel.getHDToiThieu();
            if (hoaDonToiThieu == null) {
                return; // Nếu giá trị tối thiểu không được thiết lập, thoát khỏi hàm.
            }

            // Kiểm tra điều kiện áp dụng phiếu giảm giá
            if (tongTien.compareTo(hoaDonToiThieu) < 0) {
                JOptionPane.showMessageDialog(null, "Hóa đơn chưa đủ điều kiện để áp dụng mã giảm giá!");
                cbo_phieugiamgia.setSelectedIndex(0); // Đặt lại ComboBox về "Không chọn"
                return;
            }

            // Tính toán giảm giá
            BigDecimal phanTramGiam = BigDecimal.valueOf(phieuGiamGiaModel.getPhantramgiam());
            BigDecimal giam = tongTien.multiply(phanTramGiam).divide(BigDecimal.valueOf(100));

            // Kiểm tra giá trị giảm tối đa
            if (giam.compareTo(phieuGiamGiaModel.getGiamToiDa()) > 0) {
                giam = phieuGiamGiaModel.getGiamToiDa();
                JOptionPane.showMessageDialog(null,
                        "Giá trị giảm vượt mức tối đa!\n"
                        + "Hóa đơn chỉ được giảm tối đa: " + formatCurrency(giam));
            }

            // Tính tổng tiền sau giảm giá
            BigDecimal tongTienSauGiam = tongTien.subtract(giam);
            lbl_tongtienthuc.setText(formatCurrency(tongTienSauGiam));  // Cập nhật lại tổng tiền thực tế sau khi giảm

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tổng tiền không hợp lệ!");
        }
    }//GEN-LAST:event_cbo_phieugiamgiaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ChonKhachHangForm chon = new ChonKhachHangForm(this::chonKhachHang);
        chon.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String maHD = txt_mahd.getText().trim();
        if (ValidationUtil.isEmpty(maHD) || maHD.equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để thanh toán");
            return;
        }
        System.out.println("Ma Hoa Dơn: " + maHD);
        int idHD = hoaDonService.layIdHoaDonTheoMa(maHD).getId();
        System.out.println("+++ idHD" + idHD);

// Lấy thông tin cơ bản
        String ngayTao = txt_ngaytao.getText().trim();
        LocalDateTime ngayThanhToan = LocalDateTime.now();
        String maNV = txt_manv.getText().trim();
        String tenKH = txt_tenkhachhang.getText().trim();
        String maKH = txt_makhthongtinkhachhang.getText().trim();

// Xử lý khách hàng
        Integer idKH = -1;
        if (!maKH.equalsIgnoreCase("###")) {
            KhachHangModel kh = khachHangService.layKhachHangTheoMa(maKH);
            if (kh != null) {
                idKH = kh.getId();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với mã: " + maKH);
                return;
            }
        }

// Tổng tiền
        String tongTienVND = txt_tongtien.getText().trim().replace("VND", "").replace(",", "").trim();
        BigDecimal tongTien = new BigDecimal(tongTienVND);

// Mã phiếu giảm giá
        String tenPhieu = cbo_phieugiamgia.getSelectedItem().toString();
        Integer idPhieu = -1;
        if (!tenPhieu.equalsIgnoreCase("Không chọn")) {
            PhieuGiamGiaModel pgg = pggService.layPhieuGiamGiaTheoTen(tenPhieu);
            if (pgg != null) {
                idPhieu = pgg.getId();
            }
        } else {
            // Không chọn phiếu => gán tổng tiền thực bằng tổng tiền gốc
            lbl_tongtienthuc.setText(txt_tongtien.getText());
        }

// Hình thức thanh toán
        String hinhTT = cbo_httt.getSelectedItem().toString();
        Integer idHTTT = -1;
        if (!hinhTT.equalsIgnoreCase("Cả 2")) {
            PhuongThucThanhToan httt = ptttService.layIDTheoTen(hinhTT);
            if (httt != null) {
                idHTTT = httt.getId();
            }
        }

// Tổng tiền thực tế
        String tongTienThucVND = lbl_tongtienthuc.getText().replace("VND", "").replace(",", "").trim();
        BigDecimal tongTienThuc = new BigDecimal(tongTienThucVND);

// Kiểm tra tiền khách đưa
        String tienKhachStr = txt_tienkhachdua.getText().trim();
        if (ValidationUtil.isEmpty(tienKhachStr)) {
            lbl_tienkhachdua.setText("Nhập số tiền thanh toán");
            return;
        }

        BigDecimal tienKhachDua;
        try {
            tienKhachDua = new BigDecimal(tienKhachStr.replace(",", "").replace("VND", "").trim());
            if (!hinhTT.equalsIgnoreCase("Cả 2") && !hinhTT.equalsIgnoreCase("Chuyển khoản")) {
                if (tienKhachDua.compareTo(tongTienThuc) < 0) {
                    lbl_tienkhachdua.setText("Số tiền khách đưa không đủ");
                    return;
                }
            }
        } catch (NumberFormatException e) {
            lbl_tienkhachdua.setText("Tiền không hợp lệ");
            return;
        }

// Xác nhận và thực hiện thanh toán
        int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thanh toán không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.YES_OPTION) {
            boolean thanhToan = hoaDonService.updateHoaDon(maHD, ngayThanhToan, idKH, tongTien, idPhieu, idHTTT, tongTienThuc);
            if (thanhToan) {
                // Cập nhật lại số lượng phiếu giảm giá:
                pggService.capNhatSoLuongPhieuGiamGia(idPhieu);

                BigDecimal tienThua = tienKhachDua.subtract(tongTienThuc);

                JOptionPane.showMessageDialog(this, "Thanh toán thành công.");
                fillBangHoaDon(hoaDonService.danhSachHoaDon());
                fillBangGioHang(hoaDonService.layChiTietHoaDonTheoId(0));
                lichSu.fillTableHoaDonDaThanhToan(hoaDonService.danhsachHoaDonDaThanhToan());
                hoaDonService.hanhDong(idHD, 5, ngayThanhToan, nhanVien.getId());
                cbo_phieugiamgia.removeAllItems();
                List<PhieuGiamGiaModel> phieuGiamGia = pggService.getByTrangThai(1);
                cbo_phieugiamgia.addItem("Không chọn");
                for (PhieuGiamGiaModel phieuGiamGiaModel : phieuGiamGia) {
                    cbo_phieugiamgia.addItem(phieuGiamGiaModel.getTenPGG());
                }
                loadForm();
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại");
            }
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int chonHoaDon = (dongHoaDon >= 0) ? dongHoaDon : -1;

        if (chonHoaDon == -1 || txt_mahd.getText().equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn trước.");
            return;
        }

        String maHD = (String) tableHoaDon.getValueAt(chonHoaDon, 1);
        int idHD = hoaDonService.layIdHoaDonTheoMa(maHD).getId();

        int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy hóa đơn không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.YES_OPTION) {
            if (hoaDonService.huyHoaDon(maHD)) {

                fillBangHoaDon(hoaDonService.danhSachHoaDon());
                fillBangGioHang(hoaDonService.layChiTietHoaDonTheoId(0));

                List<HoaDonChiTiet_Model> dsSanPhamTrongHoaDon = hoaDonService.layChiTietHoaDonTheoId(idHD);
                System.out.println("So luong sp " + dsSanPhamTrongHoaDon.size());
                for (HoaDonChiTiet_Model hoaDonChiTiet_Model : dsSanPhamTrongHoaDon) {
                    String maSP = hoaDonChiTiet_Model.getMaSPCT();
                    int soLuongMua = hoaDonChiTiet_Model.getSoLuong();

                    SanPham_ChiTiet spct = snctService.timSanPhamChiTietTheoMa(maSP);
                    System.out.println(spct.getDonGia() + "dfdsf");
                    if (spct != null) {
                        int soLuongHienTai = spct.getSoLuong();
                        int soLuongMoi = soLuongHienTai + soLuongMua;
                        spct.setSoLuong(soLuongMoi);
                        snctService.congSoLuongTon(spct.getId(), soLuongMua); // gọi cập nhật DB nếu có
                    }
                }

                fillSanPhamChiTiet(snctService.getAllSanPhamChiTiet());
                JOptionPane.showMessageDialog(this, "Hủy thành công.");
                loadForm();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy thất bại", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        txt_makhthongtinkhachhang.setText("###");
        txt_thongtinkhachhangten.setText("Khách bán lẻ");
        txt_mahd.setText("###");
        txt_tenkhachhang.setText("Khách lẻ");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int hoaDon = tableHoaDon.getSelectedRow();
        if (hoaDon < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước.");
            return;
        }

        WebCamChonSL webcamForm = new WebCamChonSL(this, Integer.parseInt(tableHoaDon.getValueAt(hoaDon, 6).toString()));
        webcamForm.setVisible(true);
        webcamForm.startCameraAndScan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loadForm() {
        txt_makhthongtinkhachhang.setText("###");
        txt_thongtinkhachhangten.setText("Khách bán lẻ");
        txt_mahd.setText("###");
        txt_ngaytao.setText("");
        txt_manv.setText(nhanVien.getMaNV());
        txt_tenkhachhang.setText("Khách lẻ");
        txt_tongtien.setText("");
        cbo_phieugiamgia.setSelectedIndex(0);
        cbo_httt.setSelectedIndex(0);
        txt_tienkhachdua.setText("");
        txt_tienkhachchuyenkhoan.setText("");
        lbl_tongtienthuc.setText("0 VND");
    }

    private void chonKhachHang(KhachHangModel khachHang) {
        txt_tenkhachhang.setText(khachHang.getHoTen());
        txt_makhthongtinkhachhang.setText(khachHang.getMaKH());
        txt_thongtinkhachhangten.setText(khachHang.getHoTen());
    }

    private String formatCurrency(BigDecimal amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " VND";
    }

    public void tinhTien() {
        try {
            String tienMatStr = txt_tienkhachdua.getText().trim().replace("VND", "").replace(",", "").replace(" ", "");
            String chuyenKhoanStr = txt_tienkhachchuyenkhoan.getText().trim().replace("VND", "").replace(",", "").replace(" ", "");
            String tongTienStr = txt_tongtien.getText().trim().replace("VND", "").replace(",", "").replace(" ", "");

            if (tongTienStr.isEmpty()) {
                return;
            }

            long tienMat = tienMatStr.isEmpty() ? 0 : Long.parseLong(tienMatStr);
            long chuyenKhoan = chuyenKhoanStr.isEmpty() ? 0 : Long.parseLong(chuyenKhoanStr);
            long tongTien = Long.parseLong(tongTienStr);

            long tongKhachTra = tienMat + chuyenKhoan;
            long tienThua = Math.max(0, tongKhachTra - tongTien);

            DecimalFormat df = new DecimalFormat("#,###");
            txt_tienthua.setText(df.format(tienThua) + " VND");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //fill bang gio hang
    private void loadGioHang(int idHD) {
        List<HoaDonChiTiet_Model> listHDCT = hoaDonService.layChiTietHoaDonTheoId(idHD);
        if (listHDCT.size() == 0) {
            JOptionPane.showMessageDialog(this, "Hóa đơn chưa có sản phẩm!");
            fillBangGioHang(listHDCT);
            return;
        } else {
            fillBangGioHang(listHDCT);
        }
    }

    //fill Bang Gio Hang
    public void fillBangGioHang(List<HoaDonChiTiet_Model> listHDCT) {
        DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        System.out.println("Số lượng sp: " + listHDCT.size());

        for (HoaDonChiTiet_Model hd : listHDCT) {
            model.addRow(new Object[]{
                null, // STT
                hd.getMaSPCT(), // Mã hóa đơn
                hd.getTenSP(), // Ngày tạo
                hd.getMauSac(),
                hd.getChatLieu(),
                hd.getKichThuoc(),
                hd.getDonGia(),
                hd.getSoLuong(),
                hd.getThanhTien(),
                hd.getIdSPCT()
            });
        }

        // Cập nhật STT sau khi đổ dữ liệu xong
        STT.updateSTT(model, 1);
    }

    //fill Bang Hoa Don
    public void fillBangHoaDon(List<HoaDon_Model> listHD) {
        DefaultTableModel model = (DefaultTableModel) tableHoaDon.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        System.out.println("Số lượng hóa đơn: " + listHD.size());

        for (HoaDon_Model hd : listHD) {
            model.addRow(new Object[]{
                null, // STT
                hd.getMaHD(), // Mã hóa đơn
                hd.getNgayTao(), // Ngày tạo
                hd.getMaNV(), // Nhân viên
                hd.getTongSP(), // Tổng SP
                "Chưa thanh toán", // Trạng thái (nếu có thể hiển thị rõ ràng hơn thì map thành chuỗi)
                hd.getId()
            });
        }

        // Cập nhật STT sau khi đổ dữ liệu xong
        STT.updateSTT(model, 1);
    }

    public void tinhTongTienTuGioHang() {
        double tongTien = 0;
        DecimalFormat df = new DecimalFormat("#,###");

        for (int i = 0; i < tableGioHang.getRowCount(); i++) {
            Object cellValue = tableGioHang.getValueAt(i, 8); // Cột 'Thành tiền'
            if (cellValue == null) {
                continue; // Bỏ qua nếu ô bị null
            }

            String thanhTienStr = cellValue.toString().replace("VND", "").replace(",", "").trim();

            try {
                double thanhTien = Double.parseDouble(thanhTienStr);
                tongTien += thanhTien;
            } catch (NumberFormatException e) {
                System.err.println("Lỗi parse thành tiền ở dòng " + i + ": " + thanhTienStr);

            }
        }

        txt_tongtien.setText(df.format(tongTien) + " VND");
        lbl_tongtienthuc.setText(df.format(tongTien) + " VND");
        System.out.println(">>> Tổng tiền đã tính: " + tongTien);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_taohoad;
    private javax.swing.JComboBox<String> cbo_chatlieu;
    private javax.swing.JComboBox<String> cbo_httt;
    private javax.swing.JComboBox<String> cbo_kichthuoc;
    private javax.swing.JComboBox<String> cbo_mausac;
    private javax.swing.JComboBox<String> cbo_phieugiamgia;
    private javax.swing.JComboBox<String> cbo_sanpham;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_tienkhachdua;
    private javax.swing.JLabel lbl_tongtienthuc;
    private javax.swing.JLabel lbl_tongtienthuc1;
    private javax.swing.JTable tableGioHang;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTable tbl_bangsanphamchitiet;
    private javax.swing.JTextField txt_mahd;
    private javax.swing.JTextField txt_makhthongtinkhachhang;
    private javax.swing.JTextField txt_manv;
    private javax.swing.JTextField txt_ngaytao;
    private javax.swing.JTextField txt_ngaythanhtoan;
    private javax.swing.JTextField txt_tenkhachhang;
    private javax.swing.JTextField txt_thongtinkhachhangten;
    private javax.swing.JTextField txt_tienkhachchuyenkhoan;
    private javax.swing.JTextField txt_tienkhachdua;
    private javax.swing.JTextField txt_tienthua;
    private javax.swing.JTextField txt_timkiem;
    private javax.swing.JTextField txt_tongtien;
    // End of variables declaration//GEN-END:variables
}
