package javaapplication8.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javaapplication8.model.Model_SanPham;
import javaapplication8.model.SanPham_ChiTiet;
import javaapplication8.model.SanPham_ThuocTinh;
import javaapplication8.service.SanPhamChiTietService;
import javaapplication8.service.SanPhamService;
import javaapplication8.service.SanPhamThuocTinhService;
import javaapplication8.service.serviceimpl.SanPhamChiTietServiceImpl;
import javaapplication8.service.serviceimpl.SanPhamServiceImpl;
import javaapplication8.service.serviceimpl.SanPhamThuocTinhServiceImpl;
import javaapplication8.until.CodeGeneratorUtil;
import javaapplication8.until.PhanTrang;
import javaapplication8.until.STT;
import javaapplication8.until.ValidationUtil;
import javaapplication8.until.WebCamQr;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SanPham_Form extends javax.swing.JPanel {

    private final SanPhamService service_sp = new SanPhamServiceImpl();

    private PhanTrang phanTrang;  // Đối tượng phân trang cho sản phẩm
    int currentPage = 1;  // Trang mặc định là 1
    int limit = 8;        // Số sản phẩm mỗi trang
    int offset = (currentPage - 1) * limit; // Tính toán offset

    private final SanPhamThuocTinhService service_spthuoctinh = new SanPhamThuocTinhServiceImpl();
    final SanPhamChiTietService service_spChiTiet = new SanPhamChiTietServiceImpl();

    private TableRowSorter<DefaultTableModel> rowSorter;

    private HashMap<String, Integer> sanPhamDangBanMap = new HashMap<>();
    private HashMap<String, Integer> sanPhamNgungBanMap = new HashMap<>();
    private HashMap<String, Integer> mauSacMap = new HashMap<>();
    private HashMap<String, Integer> kichThuocMap = new HashMap<>();
    private HashMap<String, Integer> chatLieuMap = new HashMap<>();

    private HashMap<String, Integer> idMauSacTheoMa = new HashMap<>();
    private HashMap<String, Integer> idKichThuocTheoMa = new HashMap<>();
    private HashMap<String, Integer> idChatLieuTheoMa = new HashMap<>();

    private HashMap<String, Integer> idSanPhamTheoMa = new HashMap<>();

    private WebCamQr webCamQr = new WebCamQr();

    public SanPham_Form() {
        initComponents();
        customizeTabblePane();

        // Tắt khả năng chỉnh sửa
        tbl_sanpham.setDefaultEditor(Object.class, null);
        tbl_sanphamchitiet.setDefaultEditor(Object.class, null);
        tbl_thuoctinh.setDefaultEditor(Object.class, null);

        cbo_khoanggia.removeAllItems();
        cbo_khoanggia.addItem("Mặc định");
        cbo_khoanggia.addItem("Thấp đến Cao");
        cbo_khoanggia.addItem("Cao đến Thấp");

        int status = rdo_sanphamdangban.isSelected() ? 0 : 1;

        // Lấy tổng số sản phẩm theo trạng thái
        int totalCountSanPham = service_sp.countAll(status); // Lấy tổng số sản phẩm từ service

        // Khởi tạo đối tượng PhanTrang
        phanTrang = new PhanTrang(limit); // Khởi tạo đối tượng phân trang với limit là số sản phẩm mỗi trang

        // Thiết lập tổng số sản phẩm (items) vào đối tượng phân trang
        phanTrang.setTotalItems(totalCountSanPham); // Cập nhật tổng số sản phẩm vào đối tượng phân trang

        // Cập nhật lại trang hiện tại và tính toán offset
        int currentPage = phanTrang.getCurrentPage(); // Lấy trang hiện tại từ đối tượng phân trang
        int offset = phanTrang.getOffset(); // Tính toán offset từ đối tượng phân trang

        // Lấy danh sách sản phẩm phân trang từ service
        List<Model_SanPham> sanPhamList = service_sp.getPage(offset, limit, status);

        // Hiển thị sản phẩm vào bảng
        fillTable_SP(sanPhamList);

        fillTable_SanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Áp dụng cho tất cả các cột
        for (int i = 0; i < tbl_sanpham.getColumnCount(); i++) {
            tbl_sanpham.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        for (int i = 0; i < tbl_thuoctinh.getColumnCount(); i++) {
            tbl_thuoctinh.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        for (int i = 0; i < tbl_sanphamchitiet.getColumnCount(); i++) {
            tbl_sanphamchitiet.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Lấy renderer mặc định của header
        JTableHeader header = tbl_sanpham.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        

        rdo_mausac.setSelected(true);
        xuLyChonRadioButton();

        //fill cbobox 
        loadComboboxLoaiSanPham();
        loadComboboxLoaiMauSac();
        loadComboboxLoaiKichThuoc();
        loadComboboxLoaiChatlieu();

        lbl_danhsachthuoctinh.setText("DANH SÁCH THUỘC TÍNH MÀU SẮC");
        rdo_sanphamdangban.setSelected(true);

        txt_timkiemsanphamtheoten.setText("Nhập tên cần tìm kiếm");
        txt_timkiemsanphamtheoten.setForeground(Color.GRAY);

        txt_timkiemsanphamtheoten.requestFocusInWindow();

        txt_timkiemsanphamtheoten.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchSanPhamTuongDoi(); // Gọi phương thức tìm kiếm khi người dùng nhập vào
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchSanPhamTuongDoi(); // Gọi phương thức tìm kiếm khi người dùng xóa văn bản
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchSanPhamTuongDoi(); // Gọi phương thức tìm kiếm khi văn bản thay đổi (hiếm khi xảy ra)
            }
        });

        btn_khoiphucsanpham.setVisible(false);
        setupRadioButtons();

        cbo_sanpham.addItem("Tất cả");
        cbo_mausac.addItem("Tất cả");
        cbo_chatlieu.addItem("Tất cả");
        cbo_kichthuoc.addItem("Tất cả");

        cbo_sanpham.setSelectedItem("Tất cả");
        cbo_mausac.setSelectedItem("Tất cả");
        cbo_chatlieu.setSelectedItem("Tất cả");
        cbo_kichthuoc.setSelectedItem("Tất cả");

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
        cbo_khoanggia.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                apDungBoLoc();
            }
        });

        suKienCacNut();
        loadDataSP();

    }

    void fillTable_SanPhamChiTiet(List<SanPham_ChiTiet> allSanPhamChiTiet) {
        DefaultTableModel model = (DefaultTableModel) tbl_sanphamchitiet.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (SanPham_ChiTiet sp : allSanPhamChiTiet) {
            model.addTableModelListener(e -> STT.updateSTT(model,1));
            model.addRow(new Object[]{
                null,
                sp.getMaSp(),
                sp.getTenSp(),
                sp.getMauSac(),
                sp.getChatLieu(),
                sp.getKichThuoc(),
                sp.getDonGia(),
                sp.getSoLuong(),
                sp.getSoLuong() > 0 ? "Còn hàng" : "Hết hàng"
            });

        }

        tbl_sanphamchitiet.setRowSorter(rowSorter); // Gắn vào bảng
    }

    private void apDungBoLoc() {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>((DefaultTableModel) tbl_sanphamchitiet.getModel());
        tbl_sanphamchitiet.setRowSorter(rowSorter);

        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        String sanPham = cbo_sanpham.getSelectedItem().toString();
        if (!sanPham.equals("Tất cả")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(sanPham), 2)); // Tên sản phẩm (cột 2)
        }

        String mauSac = cbo_mausac.getSelectedItem().toString();
        if (!mauSac.equals("Tất cả")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(mauSac), 3)); // Màu sắc (cột 3)
        }

        String chatLieu = cbo_chatlieu.getSelectedItem().toString();
        if (!chatLieu.equals("Tất cả")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(chatLieu), 4)); // Chất liệu (cột 4)
        }

        String kichThuoc = cbo_kichthuoc.getSelectedItem().toString();
        if (!kichThuoc.equals("Tất cả")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(kichThuoc), 5)); // Kích thước (cột 5)
        }

        rowSorter.setRowFilter(RowFilter.andFilter(filters));

        // Sắp xếp theo đơn giá
        String sapXep = cbo_khoanggia.getSelectedItem().toString();
        if (sapXep.equals("Thấp đến Cao")) {
            rowSorter.setSortKeys(List.of(new RowSorter.SortKey(6, SortOrder.ASCENDING)));
        } else if (sapXep.equals("Cao đến Thấp")) {
            rowSorter.setSortKeys(List.of(new RowSorter.SortKey(6, SortOrder.DESCENDING)));
        } else if (sapXep.equals("Mặc định")) {
            rowSorter.setSortKeys(null);
        }
    }

    void capNhatBangSanPhamChiTiet(List<SanPham_ChiTiet> danhSach) {
        fillTable_SanPhamChiTiet(danhSach);

        DefaultTableModel model = (DefaultTableModel) tbl_sanphamchitiet.getModel();
        rowSorter = new TableRowSorter<>(model);
        tbl_sanphamchitiet.setRowSorter(rowSorter);
    }

    void fillTable_SP(List<Model_SanPham> ds) {
        DefaultTableModel model = (DefaultTableModel) tbl_sanpham.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (Model_SanPham sp : ds) {
            model.addTableModelListener(e -> STT.updateSTT(model,1));
            model.addRow(new Object[]{
                null,
                sp.getMaSp(),
                sp.getTen(),
                sp.getMoTa(),
                sp.getSoLuong(),
                sp.getSoLuong() > 0 ? "Còn hàng" : "Hết hàng"
            });
        }

        // Hiển thị số trang hiện tại / tổng số trang (có thể sử dụng phanTrang từ toàn cục)
        lbl_trang.setText(phanTrang.getCurrentPage() + " / " + phanTrang.getTotalPages());
    }

    private void fill_SanPhamDauTien(List<Model_SanPham> ds, String maSpMoi) {
        DefaultTableModel model = (DefaultTableModel) tbl_sanpham.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        // Đưa sản phẩm mới lên đầu danh sách nếu có
        if (maSpMoi != null) {
            for (int i = 0; i < ds.size(); i++) {
                String maSp = ds.get(i).getMaSp();
                if (maSp != null && maSp.equals(maSpMoi)) {
                    Model_SanPham spMoi = ds.remove(i);
                    ds.add(0, spMoi);
                    break;
                }
            }
        }

        // Thêm dữ liệu
        for (Model_SanPham sp : ds) {
            model.addRow(new Object[]{
                null,
                sp.getMaSp(),
                sp.getTen(),
                sp.getMoTa(),
                sp.getSoLuong(),
                sp.getSoLuong() > 0 ? "Còn hàng" : "Hết hàng"
            });
        }

        // Chỉ thêm listener 1 lần!
        if (model.getTableModelListeners().length == 0) {
            model.addTableModelListener(e -> STT.updateSTT(model,1));
        }

        // Chọn dòng đầu tiên nếu có mã mới
        if (maSpMoi != null && tbl_sanpham.getRowCount() > 0) {
            tbl_sanpham.setRowSelectionInterval(0, 0);
            tbl_sanpham.scrollRectToVisible(tbl_sanpham.getCellRect(0, 0, true));
        }
    }

    void xuLyChonRadioButton() {
        if (rdo_mausac.isSelected()) {
            capNhatBangThuocTinh("Mau_Sac");
        } else if (rdo_kichthuoc.isSelected()) {
            capNhatBangThuocTinh("Kich_Thuoc");
        } else if (rdo_chatlieu.isSelected()) {
            capNhatBangThuocTinh("Chat_Lieu");
        }
    }

    private String getSelectedTableName() {
        if (rdo_mausac.isSelected()) {
            return "Mau_Sac";
        } else if (rdo_kichthuoc.isSelected()) {
            return "Kich_Thuoc";
        } else if (rdo_chatlieu.isSelected()) {
            return "Chat_Lieu";
        }
        return null;
    }

    private void capNhatBangThuocTinh(String tableName) {
        List<SanPham_ThuocTinh> danhSach = service_spthuoctinh.layDanhSachThuocTinh(tableName);
        fillTable_SPThuocTinh(danhSach);
    }

    private void fillTable_SPThuocTinh(List<SanPham_ThuocTinh> danhSach) {
        DefaultTableModel model = (DefaultTableModel) tbl_thuoctinh.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (SanPham_ThuocTinh sp : danhSach) {
            model.addTableModelListener(e -> STT.updateSTT(model,1));
            model.addRow(new Object[]{
                null,
                sp.getMa(),
                sp.getTen()});
        }
    }

    private void fillTable_SPThuocTinhDauTien(List<SanPham_ThuocTinh> danhSach, String maMoi) {
        DefaultTableModel model = (DefaultTableModel) tbl_thuoctinh.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        // Đưa thuộc tính mới lên đầu danh sách nếu có
        if (maMoi != null) {
            for (int i = 0; i < danhSach.size(); i++) {
                String ma = danhSach.get(i).getMa();
                if (ma != null && ma.equals(maMoi)) {
                    SanPham_ThuocTinh moi = danhSach.remove(i);
                    danhSach.add(0, moi);
                    break;
                }
            }
        }

        // Thêm dữ liệu
        for (SanPham_ThuocTinh sp : danhSach) {
            model.addRow(new Object[]{
                null,
                sp.getMa(),
                sp.getTen()
            });
        }

        // Chỉ thêm listener 1 lần
        if (model.getTableModelListeners().length == 0) {
            model.addTableModelListener(e -> STT.updateSTT(model,1));
        }

        // Chọn dòng đầu tiên nếu có mã mới
        if (maMoi != null && tbl_thuoctinh.getRowCount() > 0) {
            tbl_thuoctinh.setRowSelectionInterval(0, 0);
            tbl_thuoctinh.scrollRectToVisible(tbl_thuoctinh.getCellRect(0, 0, true));
        }
    }

    private void searchSanPhamTuongDoi() {
        // Lấy từ khóa tìm kiếm, chuyển về chữ thường để tìm kiếm không phân biệt hoa thường
        String keyword = txt_timkiemsanphamtheoten.getText().trim().toLowerCase();

        // ✅ Nếu từ khóa trống hoặc đang là placeholder thì không tìm
        if (keyword.isEmpty() || keyword.equals("nhập tên cần tìm kiếm".toLowerCase())) {
            return;
        }

        // Xóa dữ liệu cũ trong bảng
        DefaultTableModel model = (DefaultTableModel) tbl_sanpham.getModel();
        model.setRowCount(0);

        // Lấy trạng thái của radio button
        int status = rdo_sanphamdangban.isSelected() ? 0 : 1;

        // Lấy danh sách sản phẩm từ service (tìm kiếm tương đối)
        List<Model_SanPham> list = service_sp.loadTableTimKiemTuongDoi(keyword, status);

        // Kiểm tra nếu danh sách có sản phẩm
        if (list != null && !list.isEmpty()) {
            for (Model_SanPham sp : list) {
                // So sánh tên sản phẩm với từ khóa tìm kiếm (tìm kiếm không phân biệt chữ hoa chữ thường)
                if (sp.getTen().toLowerCase().contains(keyword)) {
                    // Nếu có khớp, hiển thị vào bảng
                    model.addRow(new Object[]{
                        null,
                        sp.getMaSp(),
                        sp.getTen(),
                        sp.getMoTa(),
                        sp.getSoLuong(),
                        sp.getSoLuong() > 0 ? "Còn hàng" : "Hết hàng"
                    });
                }
            }
        }
    }

    // Thiết lập các radio button
    private void setupRadioButtons() {
        rdo_sanphamdangban.addActionListener(e -> searchSanPhamTuongDoi());
        rdo_sanphamngungban.addActionListener(e -> searchSanPhamTuongDoi());
    }

    void macDinhCboSPCT() {
        cbo_sanpham.setSelectedItem("Tất cả");
        cbo_mausac.setSelectedItem("Tất cả");
        cbo_chatlieu.setSelectedItem("Tất cả");
        cbo_kichthuoc.setSelectedItem("Tất cả");
        cbo_khoanggia.setSelectedItem("Mặc định");
    }

    void loadDataSP() {
        int status = rdo_sanphamdangban.isSelected() ? 0 : 1;

        // Cập nhật tổng số sản phẩm theo trạng thái
        int total = service_sp.countAll(status);
        phanTrang.setTotalItems(total);

        // Lấy danh sách sản phẩm theo offset & limit
        List<Model_SanPham> ds = service_sp.getPage(phanTrang.getOffset(), phanTrang.getPageSize(), status);

        fillTable_SP(ds);
        lbl_trang.setText(phanTrang.getCurrentPage() + " / " + phanTrang.getTotalPages());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        table1 = new javaapplication8.swing.Table();
        table2 = new javaapplication8.swing.Table();
        table3 = new javaapplication8.swing.Table();
        buttonGroup2 = new javax.swing.ButtonGroup();
        btn_dau2 = new javax.swing.JButton();
        btn_truoc2 = new javax.swing.JButton();
        lbl_trang2 = new javax.swing.JLabel();
        btn_sau1 = new javax.swing.JButton();
        btn_cuoi2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cbo_sanpham = new javax.swing.JComboBox<>();
        cbo_mausac = new javax.swing.JComboBox<>();
        cbo_kichthuoc = new javax.swing.JComboBox<>();
        cbo_chatlieu = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        cbo_khoanggia = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btn_xuatexcel = new javax.swing.JButton();
        btn_themmoispct = new javax.swing.JButton();
        btn_quetqr = new javax.swing.JButton();
        taiQR = new javax.swing.JButton();
        btn_lammoispct = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_sanphamchitiet = new javax.swing.JTable();
        btn_cuoi4 = new javax.swing.JButton();
        btn_sau3 = new javax.swing.JButton();
        lbl_trang4 = new javax.swing.JLabel();
        btn_truoc4 = new javax.swing.JButton();
        btn_dau4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_mathuoctinh = new javax.swing.JTextField();
        txt_tenthuoctinh = new javax.swing.JTextField();
        lbl_thongbaothuoctinh = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        rdo_mausac = new javax.swing.JRadioButton();
        rdo_kichthuoc = new javax.swing.JRadioButton();
        rdo_chatlieu = new javax.swing.JRadioButton();
        btn_themthuoctinh = new javax.swing.JButton();
        btn_capnhatthuoctinh = new javax.swing.JButton();
        btn_xoathuoctinh = new javax.swing.JButton();
        btn_lammoithuoctinh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_thuoctinh = new javax.swing.JTable();
        lbl_danhsachthuoctinh = new javax.swing.JLabel();
        btn_dau3 = new javax.swing.JButton();
        btn_truoc3 = new javax.swing.JButton();
        lbl_trang3 = new javax.swing.JLabel();
        btn_sau2 = new javax.swing.JButton();
        btn_cuoi3 = new javax.swing.JButton();
        panel_danhsachsanpham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_masanpham = new javax.swing.JTextField();
        txt_tensanpham = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_motasanpham = new javax.swing.JTextArea();
        btn_themsanpham = new javax.swing.JButton();
        btn_capnhatsanpham = new javax.swing.JButton();
        btn_xoasanpham = new javax.swing.JButton();
        btn_lamoisanpham = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        rdo_sanphamngungban = new javax.swing.JRadioButton();
        rdo_sanphamdangban = new javax.swing.JRadioButton();
        txt_timkiemsanphamtheoten = new org.jdesktop.swingx.JXTextField();
        btn_dau = new javax.swing.JButton();
        btn_truoc = new javax.swing.JButton();
        lbl_trang = new javax.swing.JLabel();
        btn_sau = new javax.swing.JButton();
        btn_cuoi = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_sanpham = new javaapplication8.swing.Table();
        lbl_danhsachsanpham = new javax.swing.JLabel();
        lbl_thongbaotensp = new javax.swing.JLabel();
        btn_khoiphucsanpham = new javax.swing.JButton();

        btn_dau2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/first.png"))); // NOI18N
        btn_dau2.setBorder(null);

        btn_truoc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/before.png"))); // NOI18N
        btn_truoc2.setBorder(null);

        lbl_trang2.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        lbl_trang2.setForeground(new java.awt.Color(0, 204, 255));
        lbl_trang2.setText("0/0");
        lbl_trang2.setPreferredSize(new java.awt.Dimension(30, 20));

        btn_sau1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/next.png"))); // NOI18N
        btn_sau1.setBorder(null);

        btn_cuoi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/last.png"))); // NOI18N
        btn_cuoi2.setBorder(null);
        btn_cuoi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cuoi2ActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1137, 633));
        setMinimumSize(new java.awt.Dimension(1137, 633));
        setPreferredSize(new java.awt.Dimension(1137, 633));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1125, 635));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 255));
        jLabel7.setText("THÔNG TIN SẢN PHẨM CHI TIẾT");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbo_sanpham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_sanpham.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
        cbo_sanpham.setPreferredSize(new java.awt.Dimension(100, 22));

        cbo_mausac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_mausac.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
        cbo_mausac.setPreferredSize(new java.awt.Dimension(100, 22));

        cbo_kichthuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_kichthuoc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
        cbo_kichthuoc.setMinimumSize(new java.awt.Dimension(100, 22));
        cbo_kichthuoc.setPreferredSize(new java.awt.Dimension(100, 22));

        cbo_chatlieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_chatlieu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
        cbo_chatlieu.setPreferredSize(new java.awt.Dimension(100, 22));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Sản phẩm");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Màu sắc");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Kích thước");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Chất liệu");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Khoảng giá");

        jTextField1.setBorder(null);

        jTextField2.setBorder(null);

        cbo_khoanggia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_khoanggia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
        cbo_khoanggia.setPreferredSize(new java.awt.Dimension(100, 22));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(cbo_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(cbo_mausac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(cbo_kichthuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(cbo_chatlieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbo_khoanggia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_mausac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_kichthuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_chatlieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_khoanggia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Bộ lọc");

        btn_xuatexcel.setText("Xuất excel");
        btn_xuatexcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xuatexcelActionPerformed(evt);
            }
        });

        btn_themmoispct.setBackground(new java.awt.Color(204, 255, 255));
        btn_themmoispct.setText("Thêm mới");
        btn_themmoispct.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_themmoispct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themmoispctActionPerformed(evt);
            }
        });

        btn_quetqr.setText("Quét QR");
        btn_quetqr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quetqrActionPerformed(evt);
            }
        });

        taiQR.setText("Tải mã QR");
        taiQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taiQRActionPerformed(evt);
            }
        });

        btn_lammoispct.setText("Làm mới");
        btn_lammoispct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoispctActionPerformed(evt);
            }
        });

        jScrollPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane4MouseClicked(evt);
            }
        });

        tbl_sanphamchitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SPCT", "Sản phẩm", "Màu sắc", "Chất liệu", "Kích thước", "Giá ", "Số lượng", "Trạng thái"
            }
        ));
        tbl_sanphamchitiet.setRowHeight(30);
        tbl_sanphamchitiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sanphamchitietMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_sanphamchitiet);
        if (tbl_sanphamchitiet.getColumnModel().getColumnCount() > 0) {
            tbl_sanphamchitiet.getColumnModel().getColumn(0).setMinWidth(50);
            tbl_sanphamchitiet.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl_sanphamchitiet.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        btn_cuoi4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/last.png"))); // NOI18N
        btn_cuoi4.setBorder(null);
        btn_cuoi4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cuoi4ActionPerformed(evt);
            }
        });

        btn_sau3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/next.png"))); // NOI18N
        btn_sau3.setBorder(null);

        lbl_trang4.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        lbl_trang4.setForeground(new java.awt.Color(0, 204, 255));
        lbl_trang4.setText("0/0");
        lbl_trang4.setPreferredSize(new java.awt.Dimension(30, 20));

        btn_truoc4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/before.png"))); // NOI18N
        btn_truoc4.setBorder(null);

        btn_dau4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/first.png"))); // NOI18N
        btn_dau4.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_xuatexcel)
                        .addGap(374, 374, 374)
                        .addComponent(btn_themmoispct)
                        .addGap(26, 26, 26)
                        .addComponent(btn_quetqr)
                        .addGap(34, 34, 34)
                        .addComponent(taiQR)
                        .addGap(51, 51, 51)
                        .addComponent(btn_lammoispct)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 478, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(356, 356, 356))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_dau4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_truoc4)
                .addGap(24, 24, 24)
                .addComponent(lbl_trang4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_sau3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cuoi4)
                .addGap(450, 450, 450))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_xuatexcel)
                    .addComponent(btn_themmoispct)
                    .addComponent(btn_quetqr)
                    .addComponent(taiQR)
                    .addComponent(btn_lammoispct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_dau4)
                    .addComponent(btn_truoc4)
                    .addComponent(lbl_trang4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sau3)
                    .addComponent(btn_cuoi4))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Chi tiết sản phẩm", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setText("Mã thuộc tính");

        jLabel19.setText("Tên thuộc tính");

        txt_mathuoctinh.setEditable(false);
        txt_mathuoctinh.setBackground(new java.awt.Color(204, 204, 204));
        txt_mathuoctinh.setText("###");
        txt_mathuoctinh.setBorder(null);

        txt_tenthuoctinh.setBorder(null);

        lbl_thongbaothuoctinh.setForeground(new java.awt.Color(255, 51, 51));
        lbl_thongbaothuoctinh.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(lbl_thongbaothuoctinh, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tenthuoctinh, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addComponent(txt_mathuoctinh))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_mathuoctinh, txt_tenthuoctinh});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(5, 5, 5)
                .addComponent(txt_mathuoctinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_tenthuoctinh, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_thongbaothuoctinh, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_mathuoctinh, txt_tenthuoctinh});

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 255));
        jLabel20.setText("THIẾT LẬP THUỘC TÍNH");

        buttonGroup2.add(rdo_mausac);
        rdo_mausac.setText("Màu Sắc");
        rdo_mausac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_mausacActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdo_kichthuoc);
        rdo_kichthuoc.setText("Kích Thước");
        rdo_kichthuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_kichthuocActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdo_chatlieu);
        rdo_chatlieu.setText("Chất Liệu");
        rdo_chatlieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_chatlieuActionPerformed(evt);
            }
        });

        btn_themthuoctinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/material-symbols--add-circle-outline.png"))); // NOI18N
        btn_themthuoctinh.setText("Thêm");
        btn_themthuoctinh.setBorder(null);
        btn_themthuoctinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themthuoctinhActionPerformed(evt);
            }
        });

        btn_capnhatthuoctinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/capnhat.png"))); // NOI18N
        btn_capnhatthuoctinh.setText("Cập nhật");
        btn_capnhatthuoctinh.setBorder(null);
        btn_capnhatthuoctinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capnhatthuoctinhActionPerformed(evt);
            }
        });

        btn_xoathuoctinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/delete.png"))); // NOI18N
        btn_xoathuoctinh.setText("Xóa");
        btn_xoathuoctinh.setBorder(null);
        btn_xoathuoctinh.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btn_xoathuoctinhMouseMoved(evt);
            }
        });
        btn_xoathuoctinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoathuoctinhActionPerformed(evt);
            }
        });

        btn_lammoithuoctinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/lammoi.png"))); // NOI18N
        btn_lammoithuoctinh.setText("Làm mới");
        btn_lammoithuoctinh.setBorder(null);
        btn_lammoithuoctinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoithuoctinhActionPerformed(evt);
            }
        });

        tbl_thuoctinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính"
            }
        ));
        tbl_thuoctinh.setRowHeight(30);
        tbl_thuoctinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_thuoctinhMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_thuoctinh);
        if (tbl_thuoctinh.getColumnModel().getColumnCount() > 0) {
            tbl_thuoctinh.getColumnModel().getColumn(0).setMinWidth(50);
            tbl_thuoctinh.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl_thuoctinh.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        lbl_danhsachthuoctinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_danhsachthuoctinh.setForeground(new java.awt.Color(51, 0, 255));
        lbl_danhsachthuoctinh.setText(" ");

        btn_dau3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/first.png"))); // NOI18N
        btn_dau3.setBorder(null);

        btn_truoc3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/before.png"))); // NOI18N
        btn_truoc3.setBorder(null);

        lbl_trang3.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        lbl_trang3.setForeground(new java.awt.Color(0, 204, 255));
        lbl_trang3.setText("0/0");
        lbl_trang3.setPreferredSize(new java.awt.Dimension(30, 20));

        btn_sau2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/next.png"))); // NOI18N
        btn_sau2.setBorder(null);

        btn_cuoi3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/last.png"))); // NOI18N
        btn_cuoi3.setBorder(null);
        btn_cuoi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cuoi3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdo_kichthuoc)
                            .addComponent(rdo_chatlieu))
                        .addGap(137, 137, 137)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_themthuoctinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_xoathuoctinh, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_capnhatthuoctinh, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(btn_lammoithuoctinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(93, 93, 93))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(rdo_mausac)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_danhsachthuoctinh, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(393, 393, 393)
                        .addComponent(jLabel20)))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_dau3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_truoc3)
                .addGap(24, 24, 24)
                .addComponent(lbl_trang3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_sau2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cuoi3)
                .addGap(450, 450, 450))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_capnhatthuoctinh, btn_lammoithuoctinh, btn_themthuoctinh, btn_xoathuoctinh});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_xoathuoctinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_lammoithuoctinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rdo_mausac)
                                .addGap(41, 41, 41)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdo_kichthuoc)
                                    .addComponent(btn_themthuoctinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_capnhatthuoctinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdo_chatlieu))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(lbl_danhsachthuoctinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_dau3)
                    .addComponent(btn_truoc3)
                    .addComponent(lbl_trang3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sau2)
                    .addComponent(btn_cuoi3))
                .addGap(16, 16, 16))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_capnhatthuoctinh, btn_lammoithuoctinh, btn_themthuoctinh, btn_xoathuoctinh});

        jTabbedPane1.addTab("Thuộc tính", jPanel3);

        panel_danhsachsanpham.setBackground(new java.awt.Color(255, 255, 255));
        panel_danhsachsanpham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel_danhsachsanpham.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Mã sản phẩm");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tên sản phẩm");

        txt_masanpham.setEditable(false);
        txt_masanpham.setBackground(new java.awt.Color(204, 204, 204));
        txt_masanpham.setText("###");
        txt_masanpham.setBorder(null);
        txt_masanpham.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("THÔNG TIN SẢN PHẨM");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Mô tả sản phẩm");

        txt_motasanpham.setColumns(20);
        txt_motasanpham.setRows(5);
        jScrollPane1.setViewportView(txt_motasanpham);

        btn_themsanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/material-symbols--add-circle-outline.png"))); // NOI18N
        btn_themsanpham.setText("Thêm");
        btn_themsanpham.setBorder(null);
        btn_themsanpham.setMaximumSize(new java.awt.Dimension(84, 32));
        btn_themsanpham.setMinimumSize(new java.awt.Dimension(84, 32));
        btn_themsanpham.setPreferredSize(new java.awt.Dimension(84, 32));
        btn_themsanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themsanphamActionPerformed(evt);
            }
        });

        btn_capnhatsanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/capnhat.png"))); // NOI18N
        btn_capnhatsanpham.setText("Cập nhật");
        btn_capnhatsanpham.setBorder(null);
        btn_capnhatsanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capnhatsanphamActionPerformed(evt);
            }
        });

        btn_xoasanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/delete.png"))); // NOI18N
        btn_xoasanpham.setText("Xóa");
        btn_xoasanpham.setBorder(null);
        btn_xoasanpham.setMaximumSize(new java.awt.Dimension(84, 32));
        btn_xoasanpham.setMinimumSize(new java.awt.Dimension(84, 32));
        btn_xoasanpham.setPreferredSize(new java.awt.Dimension(84, 32));
        btn_xoasanpham.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btn_xoasanphamMouseMoved(evt);
            }
        });
        btn_xoasanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoasanphamActionPerformed(evt);
            }
        });

        btn_lamoisanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/lammoi.png"))); // NOI18N
        btn_lamoisanpham.setText("Làm mới");
        btn_lamoisanpham.setBorder(null);
        btn_lamoisanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lamoisanphamActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Trạng thái");

        buttonGroup1.add(rdo_sanphamngungban);
        rdo_sanphamngungban.setText("Ngừng bán");
        rdo_sanphamngungban.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdo_sanphamngungbanMouseClicked(evt);
            }
        });
        rdo_sanphamngungban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_sanphamngungbanActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_sanphamdangban);
        rdo_sanphamdangban.setText("Đang bán");
        rdo_sanphamdangban.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdo_sanphamdangbanMouseClicked(evt);
            }
        });
        rdo_sanphamdangban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_sanphamdangbanActionPerformed(evt);
            }
        });

        txt_timkiemsanphamtheoten.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 255)));
        txt_timkiemsanphamtheoten.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_timkiemsanphamtheotenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_timkiemsanphamtheotenFocusLost(evt);
            }
        });
        txt_timkiemsanphamtheoten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timkiemsanphamtheotenActionPerformed(evt);
            }
        });

        btn_dau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/first.png"))); // NOI18N
        btn_dau.setBorder(null);

        btn_truoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/before.png"))); // NOI18N
        btn_truoc.setBorder(null);

        lbl_trang.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        lbl_trang.setForeground(new java.awt.Color(0, 204, 255));
        lbl_trang.setText("0/0");
        lbl_trang.setPreferredSize(new java.awt.Dimension(30, 20));

        btn_sau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/next.png"))); // NOI18N
        btn_sau.setBorder(null);

        btn_cuoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/last.png"))); // NOI18N
        btn_cuoi.setBorder(null);
        btn_cuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cuoiActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(null);
        jScrollPane3.setOpaque(false);

        tbl_sanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Mô tả", "Số lượng", "Trạng thái"
            }
        ));
        tbl_sanpham.setGridColor(new java.awt.Color(255, 255, 255));
        tbl_sanpham.setRowHeight(30);
        tbl_sanpham.getTableHeader().setResizingAllowed(false);
        tbl_sanpham.getTableHeader().setReorderingAllowed(false);
        tbl_sanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sanphamMouseClicked(evt);
            }
        });
        tbl_sanpham.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbl_sanphamPropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_sanpham);
        if (tbl_sanpham.getColumnModel().getColumnCount() > 0) {
            tbl_sanpham.getColumnModel().getColumn(0).setMinWidth(40);
            tbl_sanpham.getColumnModel().getColumn(0).setPreferredWidth(40);
            tbl_sanpham.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl_sanpham.getColumnModel().getColumn(1).setMinWidth(100);
            tbl_sanpham.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbl_sanpham.getColumnModel().getColumn(1).setMaxWidth(100);
            tbl_sanpham.getColumnModel().getColumn(2).setMinWidth(150);
            tbl_sanpham.getColumnModel().getColumn(2).setPreferredWidth(150);
            tbl_sanpham.getColumnModel().getColumn(2).setMaxWidth(150);
            tbl_sanpham.getColumnModel().getColumn(4).setMinWidth(70);
            tbl_sanpham.getColumnModel().getColumn(4).setPreferredWidth(70);
            tbl_sanpham.getColumnModel().getColumn(4).setMaxWidth(70);
            tbl_sanpham.getColumnModel().getColumn(5).setMinWidth(120);
            tbl_sanpham.getColumnModel().getColumn(5).setPreferredWidth(120);
            tbl_sanpham.getColumnModel().getColumn(5).setMaxWidth(120);
        }

        lbl_danhsachsanpham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_danhsachsanpham.setForeground(new java.awt.Color(51, 51, 255));
        lbl_danhsachsanpham.setText("Sản Phẩm Đang Bán");

        lbl_thongbaotensp.setForeground(new java.awt.Color(255, 0, 0));

        btn_khoiphucsanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/lammoi.png"))); // NOI18N
        btn_khoiphucsanpham.setText("Khôi phục");
        btn_khoiphucsanpham.setBorder(null);
        btn_khoiphucsanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_khoiphucsanphamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_danhsachsanphamLayout = new javax.swing.GroupLayout(panel_danhsachsanpham);
        panel_danhsachsanpham.setLayout(panel_danhsachsanphamLayout);
        panel_danhsachsanphamLayout.setHorizontalGroup(
            panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(jLabel3)
                .addContainerGap(803, Short.MAX_VALUE))
            .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_thongbaotensp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_masanpham, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(txt_tensanpham, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_themsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_xoasanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_capnhatsanpham, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(btn_lamoisanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42))
            .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1091, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                        .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(100, 100, 100)
                                .addComponent(rdo_sanphamdangban))
                            .addComponent(lbl_danhsachsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_khoiphucsanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rdo_sanphamngungban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_timkiemsanphamtheoten, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_danhsachsanphamLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_dau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_truoc)
                .addGap(24, 24, 24)
                .addComponent(lbl_trang, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_sau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cuoi)
                .addGap(443, 443, 443))
        );

        panel_danhsachsanphamLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_capnhatsanpham, btn_lamoisanpham, btn_themsanpham, btn_xoasanpham});

        panel_danhsachsanphamLayout.setVerticalGroup(
            panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                        .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_themsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_capnhatsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_xoasanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_lamoisanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                        .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                                .addComponent(txt_masanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_tensanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_thongbaotensp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_danhsachsanphamLayout.createSequentialGroup()
                        .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(rdo_sanphamdangban)
                            .addComponent(rdo_sanphamngungban)
                            .addComponent(txt_timkiemsanphamtheoten, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(lbl_danhsachsanpham))
                    .addComponent(btn_khoiphucsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel_danhsachsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_dau)
                    .addComponent(btn_truoc)
                    .addComponent(lbl_trang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sau)
                    .addComponent(btn_cuoi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_danhsachsanphamLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_capnhatsanpham, btn_lamoisanpham, btn_themsanpham, btn_xoasanpham});

        jTabbedPane1.addTab("Sản phẩm", panel_danhsachsanpham);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_xoasanphamMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_xoasanphamMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_xoasanphamMouseMoved

    private void txt_timkiemsanphamtheotenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_timkiemsanphamtheotenFocusGained
        if (txt_timkiemsanphamtheoten.getText().trim().equals("Nhập tên cần tìm kiếm")) {
            txt_timkiemsanphamtheoten.setText("");  // Xóa placeholder khi focus vào
            txt_timkiemsanphamtheoten.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txt_timkiemsanphamtheotenFocusGained

    private void txt_timkiemsanphamtheotenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_timkiemsanphamtheotenFocusLost
        String keyword = txt_timkiemsanphamtheoten.getText().trim();

        // Đặt lại placeholder nếu ô tìm kiếm trống
        if (keyword.isEmpty()) {
            txt_timkiemsanphamtheoten.setText("Nhập tên cần tìm kiếm");
            txt_timkiemsanphamtheoten.setForeground(Color.GRAY);

        } else if (!keyword.equalsIgnoreCase("Nhập tên cần tìm kiếm")) {
            // Nếu ô tìm kiếm có nội dung, thực hiện tìm kiếm
            searchSanPhamTuongDoi();
        }
    }//GEN-LAST:event_txt_timkiemsanphamtheotenFocusLost

    private void rdo_sanphamngungbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_sanphamngungbanActionPerformed
//        List<Model_SanPham> dsNgungBan = service_sp.layDanhSachSanPhamNgungBan();
//        fillTable_SP(dsNgungBan);
        // Trạng thái sản phẩm: ngừng bán (status = 1)
        int status = 1;

        // Cập nhật lại tổng số sản phẩm và tính toán phân trang
        int total = service_sp.countAll(status);  // Lấy tổng số sản phẩm theo trạng thái
        phanTrang.setTotalItems(total);  // Cập nhật số lượng sản phẩm vào đối tượng phân trang

        // Nếu trang hiện tại vượt quá tổng số trang, đặt lại về trang đầu tiên
        if (phanTrang.getCurrentPage() > phanTrang.getTotalPages()) {
            phanTrang.firstPage();  // Đặt lại trang về trang đầu tiên
        }
        // Lấy danh sách sản phẩm ngừng bán với phân trang
        List<Model_SanPham> dsNgungBan = service_sp.getPage(phanTrang.getOffset(), limit, status);
        // Cập nhật bảng với danh sách sản phẩm ngừng bán
        fillTable_SP(dsNgungBan);

        // Cập nhật nhãn số trang
        lbl_trang.setText(phanTrang.getCurrentPage() + " / " + phanTrang.getTotalPages());

        lbl_danhsachsanpham.setText("Danh sách sản phẩm ngưng bán");
        lbl_danhsachsanpham.setForeground(Color.red);
        txt_masanpham.setText("###");
        txt_tensanpham.setText("");
        txt_motasanpham.setText("");
        btn_khoiphucsanpham.setVisible(true);

        btn_themsanpham.setVisible(false);
        btn_xoasanpham.setVisible(false);
        btn_capnhatsanpham.setVisible(false);
    }//GEN-LAST:event_rdo_sanphamngungbanActionPerformed

    private void rdo_sanphamdangbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_sanphamdangbanActionPerformed
        int status = 0;

        // Cập nhật lại tổng số sản phẩm và tính toán phân trang
        int total = service_sp.countAll(status);  // Lấy tổng số sản phẩm theo trạng thái
        phanTrang.setTotalItems(total);  // Cập nhật số lượng sản phẩm vào đối tượng phân trang

        // Nếu trang hiện tại vượt quá tổng số trang, đặt lại về trang đầu tiên
        if (phanTrang.getCurrentPage() > phanTrang.getTotalPages()) {
            phanTrang.firstPage();  // Đặt lại trang về trang đầu tiên
        }

        // Lấy danh sách sản phẩm đang bán với phân trang
        List<Model_SanPham> dsDangBan = service_sp.getPage(phanTrang.getOffset(), limit, status);

        // Cập nhật bảng với danh sách sản phẩm đang bán
        fillTable_SP(dsDangBan);

        // Cập nhật nhãn số trang
        lbl_trang.setText(phanTrang.getCurrentPage() + " / " + phanTrang.getTotalPages());

        lbl_danhsachsanpham.setText("Danh sách sản phẩm đang bán");
        txt_masanpham.setText("###");
        txt_tensanpham.setText("");
        txt_motasanpham.setText("");

        btn_khoiphucsanpham.setVisible(false);
        btn_themsanpham.setVisible(true);
        btn_xoasanpham.setVisible(true);
        btn_capnhatsanpham.setVisible(true);
    }//GEN-LAST:event_rdo_sanphamdangbanActionPerformed

    private void btn_themsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themsanphamActionPerformed
        String ma = CodeGeneratorUtil.generateSanPham();
        String ten = txt_tensanpham.getText().trim();
        String moTa = txt_motasanpham.getText().trim();
        int soluong = 0;

        // Kiểm tra tên không được để trống
        if (ValidationUtil.isEmpty(ten)) {
            lbl_thongbaotensp.setText("Tên sản phẩm không được để trống");
            return;
        }

        // Kiểm tra tên thuộc tính đã tồn tại chưa
        if (service_sp.kiemTraTenSanPhamDaTonTai(ten)) {
            lbl_thongbaotensp.setText("Tên sản phẩm đã tồn tại!");
            return;
        }

        // Xác nhận thêm mới
        int luachon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm sản phẩm không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (luachon == JOptionPane.YES_OPTION) {
            boolean them = service_sp.addSanPham(ma, ten, moTa);
            if (them) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công.");

                // Cập nhật lại bảng với phân trang
                List<Model_SanPham> sanPhamList = service_sp.getPage(offset, limit, rdo_sanphamdangban.isSelected() ? 0 : 1);
                fillTable_SP(sanPhamList);  // Hiển thị lại bảng sản phẩm

                capNhatBangSanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
                loadComboboxLoaiSanPham();
                lbl_thongbaotensp.setText("");
                txt_masanpham.setText("###");
                txt_tensanpham.setText("");
                txt_motasanpham.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại.");
            }
        }
    }//GEN-LAST:event_btn_themsanphamActionPerformed

    private void btn_capnhatsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatsanphamActionPerformed

        int chon = tbl_sanpham.getSelectedRow();

        if (chon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = txt_masanpham.getText().trim();
        String ten = txt_tensanpham.getText().trim();
        String moTa = txt_motasanpham.getText().trim();

        // Lấy dữ liệu cũ từ bảng
        String tenCu = tbl_sanpham.getValueAt(chon, 2).toString().trim(); // cột 2 là tên sản phẩm
        String moTaCu = tbl_sanpham.getValueAt(chon, 3).toString().trim(); // cột 3 là mô tả (sửa nếu khác)

        if (ValidationUtil.isEmpty(ten)) {
            lbl_thongbaotensp.setText("Tên sản phẩm không được để trống!");
            return;
        }

        // Nếu không thay đổi gì
        if (ten.equals(tenCu) && moTa.equals(moTaCu)) {
            JOptionPane.showMessageDialog(this, "Bạn chưa thay đổi gì cả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Chỉ kiểm tra trùng nếu có thay đổi tên
        if (!ten.equalsIgnoreCase(tenCu)) {
            if (service_sp.kiemTraTenSanPhamDaTonTai(ten)) {
                lbl_thongbaotensp.setText("Tên sản phẩm đã tồn tại!");
                return;
            }
        }

        // Xác nhận cập nhật
        int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật tên sản phẩm?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.YES_OPTION) {
            boolean capNhatThanhCong = service_sp.capNhatSanPham(ma, ten, moTa);

            if (capNhatThanhCong) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");

                List<Model_SanPham> sanPhamList = service_sp.getPage(offset, limit, rdo_sanphamdangban.isSelected() ? 0 : 1); // Giả sử 'status' là trạng thái sản phẩm (đang bán hay ngừng bán)
                fillTable_SP(sanPhamList);

                for (int i = 0; i < tbl_sanpham.getRowCount(); i++) {
                    Object maTrongBang = tbl_sanpham.getValueAt(i, 1);
                    if (maTrongBang != null && ma.equals(maTrongBang.toString())) {
                        tbl_sanpham.setRowSelectionInterval(i, i);
                        tbl_sanpham.scrollRectToVisible(tbl_thuoctinh.getCellRect(i, 0, true));
                        break;
                    }
                }
                lbl_thongbaotensp.setText("");

                txt_timkiemsanphamtheoten.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btn_capnhatsanphamActionPerformed

    private void tbl_sanphamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanphamMouseClicked
        int selectedRow = tbl_sanpham.getSelectedRow(); // Lấy chỉ số hàng được chọn
        if (selectedRow != -1) { // Kiểm tra xem có hàng nào được chọn không
            System.out.println("Giá trị ở dòng " + selectedRow + ": " + tbl_sanpham.getValueAt(selectedRow, 0));

            txt_tensanpham.setText(tbl_sanpham.getValueAt(selectedRow, 2).toString());// Lấy dữ liệu từ cột 
            txt_motasanpham.setText(tbl_sanpham.getValueAt(selectedRow, 3).toString());
            txt_masanpham.setText(tbl_sanpham.getValueAt(selectedRow, 1).toString());
        }
        lbl_thongbaotensp.setText("");


    }//GEN-LAST:event_tbl_sanphamMouseClicked

    private void btn_lamoisanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lamoisanphamActionPerformed
        txt_masanpham.setText("###");
        txt_tensanpham.setText("");
        txt_motasanpham.setText("");
        lbl_thongbaotensp.setText("");
        rdo_sanphamdangban.setSelected(true);
    }//GEN-LAST:event_btn_lamoisanphamActionPerformed

    private void btn_xoasanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoasanphamActionPerformed

        int chon = tbl_sanpham.getSelectedRow();

        if (chon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy tên sản phẩm từ cột thứ 2 (index = 2)
        String tenSanPham = tbl_sanpham.getValueAt(chon, 2).toString();

        // Tìm ID sản phẩm từ tên (giả sử bạn có Map tên → ID)
        Integer idSanPham = sanPhamDangBanMap.get(tenSanPham);

        // Nếu không có map thì cần gọi service/DAO để lấy ID theo tên:
        // Integer idSanPham = service_sp.getIdSanPhamTheoTen(tenSanPham);
        if (idSanPham == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy ID sản phẩm tương ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hỏi xác nhận trước khi xóa
        int luachon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm '" + tenSanPham + "'?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (luachon == JOptionPane.YES_OPTION) {
            boolean xoaThanhCong = service_sp.updateDaXoaSanPham(idSanPham); // gọi DAO ở trên bạn viết

            if (xoaThanhCong) {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");

                // Xóa khỏi Map sanPhamDangBanMap và chuyển sang sanPhamNgungBanMap
                sanPhamDangBanMap.remove(tenSanPham);  // Xóa sản phẩm khỏi danh sách đang bán
                sanPhamNgungBanMap.put(tenSanPham, idSanPham); // Thêm vào danh sách sản phẩm ngừng bán
                xuLyChonRadioButton(); // Refresh bảng
                List<Model_SanPham> sanPhamList = service_sp.getPage(offset, limit, rdo_sanphamdangban.isSelected() ? 0 : 1); // Giả sử 'status' là trạng thái sản phẩm (đang bán hay ngừng bán)
                fillTable_SP(sanPhamList);

                //   fillTable_SanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
                capNhatBangSanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
                lbl_thongbaotensp.setText("");
                txt_masanpham.setText("###");
                txt_tensanpham.setText("");
                txt_motasanpham.setText("");
                txt_timkiemsanphamtheoten.setText("");

            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_xoasanphamActionPerformed

    private void btn_themthuoctinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themthuoctinhActionPerformed
        String ma = "";
        if (rdo_mausac.isSelected()) {
            ma = CodeGeneratorUtil.generateMauSac();
        } else if (rdo_chatlieu.isSelected()) {
            ma = CodeGeneratorUtil.generateChatLieu();
        } else if (rdo_kichthuoc.isSelected()) {
            ma = CodeGeneratorUtil.generateKichThuoc();
        }

        String ten = txt_tenthuoctinh.getText().trim();
        String tableName = getSelectedTableName();

        // Kiểm tra tên không được để trống
        if (ValidationUtil.isEmpty(ten)) {
            lbl_thongbaothuoctinh.setText("Tên thuộc tính không được để trống");
            return;
        }

        // Kiểm tra tên thuộc tính đã tồn tại chưa
        if (service_spthuoctinh.kiemTraTenThuocTinhDaTonTai(tableName, ten)) {
            JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận thêm mới
        int luachon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm thuộc tính không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (luachon == JOptionPane.YES_OPTION) {
            boolean them = service_spthuoctinh.addThuocTinh(tableName, ma, ten);
            if (them) {
                loadComboboxLoaiMauSac();
                loadComboboxLoaiKichThuoc();
                loadComboboxLoaiChatlieu();
                JOptionPane.showMessageDialog(this, "Thêm thuộc tính thành công.");
                xuLyChonRadioButton(); // Cập nhật lại bảng sau khi thêm

                lbl_thongbaothuoctinh.setText("");
                txt_mathuoctinh.setText("###");
                txt_tenthuoctinh.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại.");
            }
        }
    }//GEN-LAST:event_btn_themthuoctinhActionPerformed

    private void btn_capnhatthuoctinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatthuoctinhActionPerformed
        int chon = tbl_thuoctinh.getSelectedRow();

        if (chon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuộc tính để cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = tbl_thuoctinh.getValueAt(chon, 1).toString(); // Lấy mã từ bảng
        String tenMoi = txt_tenthuoctinh.getText().trim().toString(); // Lấy tên mới từ text field
        String tenCu = tbl_thuoctinh.getValueAt(chon, 2).toString();
        String tableName = getSelectedTableName(); // Lấy bảng hiện tại

        if (ValidationUtil.isEmpty(tenMoi)) {
            lbl_thongbaothuoctinh.setText("Tên thuộc tính không được để trống!");
            return;
        }

        // Kiểm tra xem có thay đổi tên không
        if (tenMoi.equalsIgnoreCase(tenCu)) {
            lbl_thongbaothuoctinh.setText("Tên mới không có sự thay đổi!");
            return;
        }

        // Kiểm tra xem tên mới đã tồn tại chưa
        if (service_spthuoctinh.kiemTraTenThuocTinhDaTonTai(tableName, tenMoi)) {
            lbl_thongbaothuoctinh.setText("Tên thuộc tính đã tồn tại! Vui lòng nhập tên khác.");
            return;
        }

        // Xác nhận cập nhật
        int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật tên thuộc tính?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.YES_OPTION) {
            boolean capNhatThanhCong = service_spthuoctinh.updateThuocTinh(tableName, tenMoi, ma);

            if (capNhatThanhCong) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");

                xuLyChonRadioButton(); // Load lại bảng sau khi cập nhật

                // Chọn lại dòng theo mã vừa cập nhật
                for (int i = 0; i < tbl_thuoctinh.getRowCount(); i++) {
                    Object maTrongBang = tbl_thuoctinh.getValueAt(i, 1);
                    if (maTrongBang != null && ma.equals(maTrongBang.toString())) {
                        tbl_thuoctinh.setRowSelectionInterval(i, i);
                        tbl_thuoctinh.scrollRectToVisible(tbl_thuoctinh.getCellRect(i, 0, true));
                        break;
                    }
                }
                lbl_thongbaothuoctinh.setText("");
                txt_mathuoctinh.setText("###");
                txt_tenthuoctinh.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_capnhatthuoctinhActionPerformed

    private void btn_xoathuoctinhMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_xoathuoctinhMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_xoathuoctinhMouseMoved

    private void btn_xoathuoctinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoathuoctinhActionPerformed
        int chon = tbl_thuoctinh.getSelectedRow();

        if (chon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuộc tính để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tenThuocTinh = tbl_thuoctinh.getValueAt(chon, 2).toString(); // Lấy TÊN thuộc tính
        String tableName = getSelectedTableName(); // Lấy tên bảng tương ứng với radio

        if (tableName == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại thuộc tính cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác định ID dựa trên tên và bảng được chọn
        Integer id = null;
        switch (tableName) {
            case "Mau_Sac":
                id = mauSacMap.get(tenThuocTinh);
                break;
            case "Kich_Thuoc":
                id = kichThuocMap.get(tenThuocTinh);
                break;
            case "Chat_Lieu":
                id = chatLieuMap.get(tenThuocTinh);
                break;
        }

        if (id == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy ID tương ứng với tên thuộc tính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int luachon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa '" + tenThuocTinh + "'?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (luachon == JOptionPane.YES_OPTION) {
            boolean xoaThanhCong = service_spthuoctinh.xoaThuocTinhSanPham(tableName, id); // ← Gọi theo ID

            if (xoaThanhCong) {
                JOptionPane.showMessageDialog(this, "Xóa thuộc tính thành công!");
                xuLyChonRadioButton();  // Cập nhật lại bảng sau khi xóa
                loadComboboxLoaiSanPham();
                loadComboboxLoaiMauSac();
                loadComboboxLoaiKichThuoc();
                loadComboboxLoaiChatlieu();
                fillTable_SanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
                lbl_thongbaothuoctinh.setText("");
                txt_mathuoctinh.setText("###");
                txt_tenthuoctinh.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_xoathuoctinhActionPerformed

    private void btn_lammoithuoctinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoithuoctinhActionPerformed
        txt_mathuoctinh.setText("###");
        txt_tenthuoctinh.setText("");
        lbl_thongbaothuoctinh.setText("");
        rdo_mausac.setSelected(true);
    }//GEN-LAST:event_btn_lammoithuoctinhActionPerformed

    private void rdo_mausacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_mausacActionPerformed
        xuLyChonRadioButton();

        lbl_danhsachthuoctinh.setText("DANH SÁCH THUỘC TÍNH MÀU SẮC");
        txt_mathuoctinh.setText("###");
        txt_tenthuoctinh.setText("");
    }//GEN-LAST:event_rdo_mausacActionPerformed

    private void rdo_kichthuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_kichthuocActionPerformed
        xuLyChonRadioButton();
        lbl_danhsachthuoctinh.setText("DANH SÁCH THUỘC TÍNH KÍCH THƯỚC");
        txt_mathuoctinh.setText("###");
        txt_tenthuoctinh.setText("");
    }//GEN-LAST:event_rdo_kichthuocActionPerformed

    private void rdo_chatlieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_chatlieuActionPerformed
        xuLyChonRadioButton();
        lbl_danhsachthuoctinh.setText("DANH SÁCH THUỘC TÍNH CHẤT LIỆU");
        txt_mathuoctinh.setText("###");
        txt_tenthuoctinh.setText("");
    }//GEN-LAST:event_rdo_chatlieuActionPerformed

    private void btn_xuatexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xuatexcelActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Nếu chưa có đuôi .xlsx thì thêm vào
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            // Gọi phương thức export
            exportSanPhamChiTietToExcel(tbl_sanphamchitiet, filePath);
        }
    }//GEN-LAST:event_btn_xuatexcelActionPerformed

    private void btn_themmoispctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themmoispctActionPerformed
        ThemSanPhamChiTiet themSPCT = new ThemSanPhamChiTiet(this, new Runnable() {
            @Override
            public void run() {
                fillTable_SanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
            }
        });

        themSPCT.setVisible(true); // ✅ Hiển thị đúng đối tượng có callback
    }//GEN-LAST:event_btn_themmoispctActionPerformed

    private void btn_quetqrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quetqrActionPerformed
        WebCamQr webcamForm = new WebCamQr(this);
        webcamForm.setVisible(true);
        webcamForm.startCameraAndScan();
    }//GEN-LAST:event_btn_quetqrActionPerformed

    private void taiQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taiQRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_taiQRActionPerformed

    private void btn_lammoispctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoispctActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_lammoispctActionPerformed

    private void tbl_sanphamPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbl_sanphamPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_sanphamPropertyChange

    private void tbl_thuoctinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_thuoctinhMouseClicked
        int selectedRow = tbl_thuoctinh.getSelectedRow(); // Lấy chỉ số hàng được chọn
        if (selectedRow != -1) { // Kiểm tra xem có hàng nào được chọn không
            String maThuocTinh = tbl_thuoctinh.getValueAt(selectedRow, 1).toString();
            String tenThuocTinh = tbl_thuoctinh.getValueAt(selectedRow, 2).toString(); // Lấy dữ liệu từ cột 2
            txt_tenthuoctinh.setText(tenThuocTinh); // Hiển thị vào ô nhập liệu
            txt_mathuoctinh.setText(maThuocTinh);
        }
    }//GEN-LAST:event_tbl_thuoctinhMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

//        fillTable_SP(service_sp.layDanhSachSanPhamDangBan());
        List<Model_SanPham> sanPhamList = service_sp.getPage(offset, limit, rdo_sanphamdangban.isSelected() ? 0 : 1); // Giả sử 'status' là trạng thái sản phẩm (đang bán hay ngừng bán)
        fillTable_SP(sanPhamList);

        xuLyChonRadioButton();
        //fillTable_SanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
        loadComboboxLoaiSanPham();
        loadComboboxLoaiMauSac();
        loadComboboxLoaiKichThuoc();
        loadComboboxLoaiChatlieu();
        rdo_sanphamdangban.setSelected(true);
        capNhatBangSanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
        macDinhCboSPCT();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void btn_khoiphucsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_khoiphucsanphamActionPerformed
        int chon = tbl_sanpham.getSelectedRow();

        if (chon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để khôi phục!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tenSanPham = txt_tensanpham.getText().toString().trim();

        // Tìm ID sản phẩm từ tên (giả sử bạn có Map tên → ID)
        Integer idSanPham = sanPhamNgungBanMap.get(tenSanPham); // sanPhamMap: Map<String, Integer>

        if (idSanPham == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy ID sản phẩm tương ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int luachon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn khôi phục sản phẩm '" + tenSanPham + "'?",
                "Xác nhận", JOptionPane.YES_NO_OPTION
        );

        if (luachon == JOptionPane.YES_OPTION) {
            boolean thanhCong = service_sp.khoiPhucSanPhamDaXoa(idSanPham);

            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Khôi phục sản phẩm thành công!");

                sanPhamNgungBanMap.remove(tenSanPham);  // Xóa sản phẩm khỏi danh sách ngưng bán
                sanPhamDangBanMap.put(tenSanPham, idSanPham); // Thêm vào danh sách sản phẩm đang bán
                xuLyChonRadioButton(); // Ví dụ: lọc lại danh sách đang chọn
                List<Model_SanPham> sanPhamList = service_sp.getPage(offset, limit, rdo_sanphamngungban.isSelected() ? 1 : 0); // Giả sử 'status' là trạng thái sản phẩm (đang bán hay ngừng bán)
                fillTable_SP(sanPhamList);

                fillTable_SanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
                txt_timkiemsanphamtheoten.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Khôi phục thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btn_khoiphucsanphamActionPerformed

    private void txt_timkiemsanphamtheotenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timkiemsanphamtheotenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_timkiemsanphamtheotenActionPerformed

    private void rdo_sanphamdangbanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdo_sanphamdangbanMouseClicked

    }//GEN-LAST:event_rdo_sanphamdangbanMouseClicked

    private void rdo_sanphamngungbanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdo_sanphamngungbanMouseClicked

    }//GEN-LAST:event_rdo_sanphamngungbanMouseClicked


    private void btn_cuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cuoiActionPerformed

    private void btn_cuoi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuoi2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cuoi2ActionPerformed

    private void btn_cuoi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuoi3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cuoi3ActionPerformed

    private void btn_cuoi4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuoi4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cuoi4ActionPerformed

    private void jScrollPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane4MouseClicked

    }//GEN-LAST:event_jScrollPane4MouseClicked

    private void tbl_sanphamchitietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanphamchitietMouseClicked
        int row = tbl_sanphamchitiet.getSelectedRow();
        if (row >= 0) {
            // Lấy dữ liệu từ dòng được click trong bảng
            String maSPCT = tbl_sanphamchitiet.getValueAt(row, 1).toString();
            String tenSP = tbl_sanphamchitiet.getValueAt(row, 2).toString(); // Tên sản phẩm
            String mauSac = tbl_sanphamchitiet.getValueAt(row, 3).toString(); // Màu sắc
            String chatLieu = tbl_sanphamchitiet.getValueAt(row, 4).toString(); // Chất liệu
            String kichThuoc = tbl_sanphamchitiet.getValueAt(row, 5).toString(); // Kích thước
            int soLuong = Integer.parseInt(tbl_sanphamchitiet.getValueAt(row, 7).toString()); // Số lượng

            // Lấy và chuyển đổi đơn giá, loại bỏ " VND" và dấu phẩy nếu có
            // Lấy đơn giá từ table, xử lý chuỗi an toàn
            String donGiaText = tbl_sanphamchitiet.getValueAt(row, 6).toString();

// Loại bỏ tất cả ký tự không phải số hoặc dấu chấm
            donGiaText = donGiaText.replaceAll("[^\\d.]", "").trim();

// Kiểm tra nếu rỗng thì gán 0, tránh crash
            double donGia = donGiaText.isEmpty() ? 0.0 : Double.parseDouble(donGiaText);
            // Lấy đường dẫn QR code từ CSDL
            String qrPath = service_spChiTiet.getQRPathByMaSPCT(maSPCT);

            // Mở form chi tiết và truyền dữ liệu
            ThongTinSanPhamChiTiet thongtinspct = new ThongTinSanPhamChiTiet(
                    this, // form cha
                    new Runnable() {
                @Override
                public void run() {
                    // Đoạn callback khi form con đóng, có thể refresh bảng
                    fillTable_SanPhamChiTiet(service_spChiTiet.getAllSanPhamChiTiet());
                }
            },
                    maSPCT, tenSP, mauSac, chatLieu, kichThuoc, soLuong, donGia, qrPath // Truyền thông tin sản phẩm vào form con
            );
            thongtinspct.setVisible(true);
        }
    }//GEN-LAST:event_tbl_sanphamchitietMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_capnhatsanpham;
    private javax.swing.JButton btn_capnhatthuoctinh;
    private javax.swing.JButton btn_cuoi;
    private javax.swing.JButton btn_cuoi2;
    private javax.swing.JButton btn_cuoi3;
    private javax.swing.JButton btn_cuoi4;
    private javax.swing.JButton btn_dau;
    private javax.swing.JButton btn_dau2;
    private javax.swing.JButton btn_dau3;
    private javax.swing.JButton btn_dau4;
    private javax.swing.JButton btn_khoiphucsanpham;
    private javax.swing.JButton btn_lammoispct;
    private javax.swing.JButton btn_lammoithuoctinh;
    private javax.swing.JButton btn_lamoisanpham;
    private javax.swing.JButton btn_quetqr;
    private javax.swing.JButton btn_sau;
    private javax.swing.JButton btn_sau1;
    private javax.swing.JButton btn_sau2;
    private javax.swing.JButton btn_sau3;
    private javax.swing.JButton btn_themmoispct;
    private javax.swing.JButton btn_themsanpham;
    private javax.swing.JButton btn_themthuoctinh;
    private javax.swing.JButton btn_truoc;
    private javax.swing.JButton btn_truoc2;
    private javax.swing.JButton btn_truoc3;
    private javax.swing.JButton btn_truoc4;
    private javax.swing.JButton btn_xoasanpham;
    private javax.swing.JButton btn_xoathuoctinh;
    private javax.swing.JButton btn_xuatexcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbo_chatlieu;
    private javax.swing.JComboBox<String> cbo_khoanggia;
    private javax.swing.JComboBox<String> cbo_kichthuoc;
    private javax.swing.JComboBox<String> cbo_mausac;
    private javax.swing.JComboBox<String> cbo_sanpham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lbl_danhsachsanpham;
    private javax.swing.JLabel lbl_danhsachthuoctinh;
    private javax.swing.JLabel lbl_thongbaotensp;
    private javax.swing.JLabel lbl_thongbaothuoctinh;
    private javax.swing.JLabel lbl_trang;
    private javax.swing.JLabel lbl_trang2;
    private javax.swing.JLabel lbl_trang3;
    private javax.swing.JLabel lbl_trang4;
    private javax.swing.JPanel panel_danhsachsanpham;
    private javax.swing.JRadioButton rdo_chatlieu;
    private javax.swing.JRadioButton rdo_kichthuoc;
    private javax.swing.JRadioButton rdo_mausac;
    private javax.swing.JRadioButton rdo_sanphamdangban;
    private javax.swing.JRadioButton rdo_sanphamngungban;
    private javaapplication8.swing.Table table1;
    private javaapplication8.swing.Table table2;
    private javaapplication8.swing.Table table3;
    private javax.swing.JButton taiQR;
    private javaapplication8.swing.Table tbl_sanpham;
    private javax.swing.JTable tbl_sanphamchitiet;
    private javax.swing.JTable tbl_thuoctinh;
    private javax.swing.JTextField txt_masanpham;
    private javax.swing.JTextField txt_mathuoctinh;
    private javax.swing.JTextArea txt_motasanpham;
    private javax.swing.JTextField txt_tensanpham;
    private javax.swing.JTextField txt_tenthuoctinh;
    private org.jdesktop.swingx.JXTextField txt_timkiemsanphamtheoten;
    // End of variables declaration//GEN-END:variables

    private void customizeTabblePane() {
        jTabbedPane1.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                if (isSelected) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x + 5, y + h - 3, w - 10, 3);
                }
            }

        });
    }

    void loadComboboxLoaiSanPham() {

        cbo_sanpham.removeAllItems();
        sanPhamDangBanMap.clear();
        sanPhamNgungBanMap.clear();

        cbo_sanpham.addItem("Tất cả"); // ✅ Thêm dòng mặc định

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

        cbo_sanpham.setSelectedItem("Tất cả"); // ✅ Chọn mặc định
    }

    void loadComboboxLoaiMauSac() {
        cbo_mausac.removeAllItems();
        mauSacMap.clear();

        cbo_mausac.addItem("Tất cả"); // ✅ Thêm dòng mặc định

        List<SanPham_ThuocTinh> dsMauSac = service_spthuoctinh.getLoaiMauSac();
        for (SanPham_ThuocTinh ms : dsMauSac) {
            cbo_mausac.addItem(ms.getTen());
            mauSacMap.put(ms.getTen(), ms.getId());
        }

        cbo_mausac.setSelectedItem("Tất cả"); // ✅ Chọn mặc định
    }

    void loadComboboxLoaiKichThuoc() {
        cbo_kichthuoc.removeAllItems();
        kichThuocMap.clear();
        cbo_kichthuoc.addItem("Tất cả"); // ✅ Thêm dòng mặc định

        List<SanPham_ThuocTinh> dsKichThuoc = service_spthuoctinh.getLoaiKichThuoc();
        for (SanPham_ThuocTinh ms : dsKichThuoc) {
            cbo_kichthuoc.addItem(ms.getTen());
            kichThuocMap.put(ms.getTen(), ms.getId());
        }

        cbo_kichthuoc.setSelectedItem("Tất cả"); // ✅ Chọn mặc định
    }

    void loadComboboxLoaiChatlieu() {
        cbo_chatlieu.removeAllItems();
        chatLieuMap.clear();

        cbo_chatlieu.addItem("Tất cả"); // ✅ Thêm dòng mặc định

        List<SanPham_ThuocTinh> dsChatLieu = service_spthuoctinh.getLoaiChatLieu();
        for (SanPham_ThuocTinh ms : dsChatLieu) {
            cbo_chatlieu.addItem(ms.getTen());
            chatLieuMap.put(ms.getTen(), ms.getId());
        }

        cbo_chatlieu.setSelectedItem("Tất cả"); // ✅ Chọn mặc định
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

    private void suKienCacNut() {

        btn_dau.addActionListener(e -> {
            phanTrang.firstPage();
            loadDataSP();
        });

        btn_truoc.addActionListener(e -> {
            if (!phanTrang.isFirstPage()) {
                phanTrang.prevPage();
                loadDataSP();
            }
        });

        btn_sau.addActionListener(e -> {
            if (!phanTrang.isLastPage()) {
                phanTrang.nextPage();
                loadDataSP();
            }
        });

        btn_cuoi.addActionListener(e -> {
            phanTrang.lastPage();
            loadDataSP();
        });

    }

    // Phương thức xuất dữ liệu bảng ra file Excel
    public void exportSanPhamChiTietToExcel(JTable table, String filePath) {
        System.out.println(org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream.class.getProtectionDomain().getCodeSource());

        XSSFWorkbook workbook = new XSSFWorkbook();  // Tạo Workbook mới cho file Excel
        Sheet sheet = workbook.createSheet("SanPhamChiTiet");

        TableModel model = table.getModel();  // Lấy model từ JTable

        // Tạo header (dòng tiêu đề)
        Row headerRow = sheet.createRow(0);  // Dòng tiêu đề sẽ là dòng đầu tiên
        for (int col = 0; col < model.getColumnCount(); col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(model.getColumnName(col));  // Set tên cột vào header
        }

        // Ghi dữ liệu từ JTable vào Excel
        for (int row = 0; row < model.getRowCount(); row++) {
            Row excelRow = sheet.createRow(row + 1);  // Dòng tiếp theo sau header
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = excelRow.createCell(col);
                Object value = model.getValueAt(row, col);

                // Kiểm tra kiểu dữ liệu và ghi vào cell tương ứng
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else {
                    cell.setCellValue(value != null ? value.toString() : "");
                }
            }
        }

        // Tự động điều chỉnh độ rộng cột
        for (int col = 0; col < model.getColumnCount(); col++) {
            sheet.autoSizeColumn(col);
        }

        // Ghi file ra đĩa
        try ( FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            workbook.close();  // Đóng workbook
            JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");  // Hiển thị thông báo thành công
        } catch (IOException e) {
            e.printStackTrace();  // In lỗi nếu có
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file: " + e.getMessage());  // Hiển thị lỗi
        }
    }
}
