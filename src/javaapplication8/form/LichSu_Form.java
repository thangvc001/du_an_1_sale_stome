package javaapplication8.form;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javaapplication8.model.HoaDon;
import javaapplication8.model.HoaDonChiTiet_Model;
import javaapplication8.model.HoaDon_Model;
import javaapplication8.model.KhachHangModel;
import javaapplication8.model.LichSuHoaDon;
import javaapplication8.model.Model_NhanVien;
import javaapplication8.service.HoaDonService;
import javaapplication8.service.serviceimpl.HoaDonServiceImpl;
import javaapplication8.until.HoaDonPDFExporter;
import javaapplication8.until.STT;
import javaapplication8.until.WebCamQrHD;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class LichSu_Form extends javax.swing.JPanel {

    private HoaDonService hoaDonService = new HoaDonServiceImpl();
    private Model_NhanVien nhanvien = new Model_NhanVien();

    private int row = -1;
//    int DongHoa

    public LichSu_Form() {
        initComponents();

        //fillTable
        fillTableHoaDonDaThanhToan(hoaDonService.danhsachHoaDonDaThanhToan());

        txt_tiemKiem.setText("Nhập mã hóa đơn, tên khách hàng để tìm kiếm");
        txt_tiemKiem.setForeground(Color.GRAY);
        txt_tiemKiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_tiemKiem.getText().equals("Nhập mã hóa đơn, tên khách hàng để tìm kiếm")) {
                    txt_tiemKiem.setText("");
                    txt_tiemKiem.setForeground(Color.BLACK); // chuyển về màu chữ bình thường
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_tiemKiem.getText().isEmpty()) {
                    txt_tiemKiem.setText("Nhập mã hóa đơn, tên khách hàng để tìm kiếm");
                    txt_tiemKiem.setForeground(Color.GRAY);
                }
            }
        });

        txt_tiemKiem.getDocument().addDocumentListener(new DocumentListener() {
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

    }

    private void timKiemNgay() {
        String tuKhoa = txt_tiemKiem.getText().trim();

        if (tuKhoa.equals("Nhập mã hóa đơn, tên khách hàng để tìm kiếm")) {
            // Có thể reload danh sách gốc ở đây
            fillTableHoaDonDaThanhToan(hoaDonService.danhsachHoaDonDaThanhToan());
            return;
        }
        String timKiem = txt_tiemKiem.getText().trim().toLowerCase();
        List<HoaDon_Model> danhSach = hoaDonService.danhsachHoaDonTimTheoTuKhoa(timKiem);
        DefaultTableModel model = (DefaultTableModel) tbl_hoaDonChiTiet.getModel();
        model.setRowCount(0);
        model.addTableModelListener(e -> STT.updateSTT(model, 1));

        for (HoaDon_Model hd : danhSach) {

            model.addRow(new Object[]{
                null,
                hd.getMaHD(),
                hd.getNgayTao(),
                hd.getNgayTT(),
                hd.getTongTienKhiGiam(),
                hd.getMaNV(),
                hd.getTenKhachHang(),
                hd.getDiaChiKhachHang(),
                hd.getSdtKH(),
                "Đã thanh toán",
                hd.getId()});
        }
    }

    //fillTableHoaDonDaThanhToan
    public void fillTableHoaDonDaThanhToan(List<HoaDon_Model> list) {
        System.out.println("dfakjds" + list.size());
        DefaultTableModel model = (DefaultTableModel) tbl_hoaDonChiTiet.getModel();
        model.setRowCount(0);
        model.addTableModelListener(e -> STT.updateSTT(model, 1));
        for (HoaDon_Model hd : list) {

            model.addRow(new Object[]{
                null,
                hd.getMaHD(),
                hd.getNgayTao(),
                hd.getNgayTT(),
                hd.getTongTienKhiGiam(),
                hd.getMaNV(),
                hd.getTenKhachHang(),
                hd.getDiaChiKhachHang(),
                hd.getSdtKH(),
                "Đã thanh toán",
                hd.getId()});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_tiemKiem = new javax.swing.JTextField();
        tbl_quetOR = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_hoaDonChiTiet = new javax.swing.JTable();
        lbl_trang = new javax.swing.JLabel();
        btn_Sau = new javax.swing.JButton();
        btn_cuoi = new javax.swing.JButton();
        btn_truoc = new javax.swing.JButton();
        btn_dau = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableHoaDonChiTiet = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableLichSu = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Hóa Đơn");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setText("Hóa đơn");

        jPanel2.setOpaque(false);

        jLabel3.setText("Tìm kiếm hóa đơn:");

        txt_tiemKiem.setText("jTextField1");

        tbl_quetOR.setText("Quét QR");
        tbl_quetOR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_quetORActionPerformed(evt);
            }
        });

        tbl_hoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày tạo", "Ngày thanh toán", "Tổng tiền", "Mã nhân viên", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Trạng thái", "idHD"
            }
        ));
        tbl_hoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_hoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_hoaDonChiTiet);
        if (tbl_hoaDonChiTiet.getColumnModel().getColumnCount() > 0) {
            tbl_hoaDonChiTiet.getColumnModel().getColumn(0).setMinWidth(50);
            tbl_hoaDonChiTiet.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl_hoaDonChiTiet.getColumnModel().getColumn(0).setMaxWidth(50);
            tbl_hoaDonChiTiet.getColumnModel().getColumn(10).setMinWidth(0);
            tbl_hoaDonChiTiet.getColumnModel().getColumn(10).setPreferredWidth(0);
            tbl_hoaDonChiTiet.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        lbl_trang.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        lbl_trang.setForeground(new java.awt.Color(0, 204, 255));
        lbl_trang.setText("jLabel6");

        btn_Sau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/next.png"))); // NOI18N
        btn_Sau.setBorder(null);

        btn_cuoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/last.png"))); // NOI18N
        btn_cuoi.setBorder(null);

        btn_truoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/before.png"))); // NOI18N
        btn_truoc.setBorder(null);

        btn_dau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/first.png"))); // NOI18N
        btn_dau.setBorder(null);

        jButton5.setText("In hóa đơn");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tiemKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(tbl_quetOR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(36, 36, 36))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(411, 411, 411)
                .addComponent(btn_dau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_truoc)
                .addGap(18, 18, 18)
                .addComponent(lbl_trang)
                .addGap(18, 18, 18)
                .addComponent(btn_Sau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_cuoi)
                .addContainerGap(514, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_tiemKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbl_quetOR)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_trang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_truoc, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_dau, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_Sau, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cuoi, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        tableHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SPCT", "Tên sản phẩm", "Số lượng", "Tổng tiền"
            }
        ));
        jScrollPane2.setViewportView(tableHoaDonChiTiet);
        if (tableHoaDonChiTiet.getColumnModel().getColumnCount() > 0) {
            tableHoaDonChiTiet.getColumnModel().getColumn(0).setMinWidth(50);
            tableHoaDonChiTiet.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableHoaDonChiTiet.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("Hóa đơn chi tiết");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 51));
        jLabel9.setText("Lịch sử hóa đơn");

        tableLichSu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Người tác động ", "Giờ", "Ngày cập nhật", "Hành động"
            }
        ));
        jScrollPane3.setViewportView(tableLichSu);
        if (tableLichSu.getColumnModel().getColumnCount() > 0) {
            tableLichSu.getColumnModel().getColumn(0).setMinWidth(50);
            tableLichSu.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableLichSu.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(511, 511, 511)
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(179, 179, 179)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(214, 214, 214)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_hoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_hoaDonChiTietMouseClicked
        row = tbl_hoaDonChiTiet.getSelectedRow();
        int idHD = Integer.parseInt(tbl_hoaDonChiTiet.getValueAt(row, 10).toString());
        DefaultTableModel model = (DefaultTableModel) tableHoaDonChiTiet.getModel();
        model.setRowCount(0);

        List<HoaDonChiTiet_Model> hoaDonCT = hoaDonService.layChiTietHoaDonTheoId(idHD);

        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        currencyFormat.setMaximumFractionDigits(0);
        for (HoaDonChiTiet_Model hd : hoaDonCT) {
            String thanhTienFormatted = "";
            try {
                String raw = hd.getThanhTien().replace("VND", "").replace(",", "").trim();
                BigDecimal thanhTien = new BigDecimal(raw);
                thanhTienFormatted = currencyFormat.format(thanhTien) + " VND";
            } catch (Exception ex) {
                thanhTienFormatted = hd.getThanhTien(); // fallback nếu lỗi
            }
            model.addTableModelListener(e -> STT.updateSTT(model, 1));
            model.addRow(new Object[]{
                null,
                hd.getMaSPCT(),
                hd.getTenSP(),
                hd.getSoLuong(),
                thanhTienFormatted});
        }

        DefaultTableModel model2 = (DefaultTableModel) tableLichSu.getModel();
        model2.setRowCount(0);

        List<LichSuHoaDon> lichSu = hoaDonService.lichSuHoaDonTheoID(idHD);
        for (LichSuHoaDon lichSuHoaDon : lichSu) {

            model2.addTableModelListener(e -> STT.updateSTT(model2, 1));
            model2.addRow(new Object[]{
                null,
                lichSuHoaDon.getMaNV(),
                lichSuHoaDon.getGio(),
                lichSuHoaDon.getNgay(),
                lichSuHoaDon.getHanhDong()});
        }

    }//GEN-LAST:event_tbl_hoaDonChiTietMouseClicked

    public void fillHaiBang() {
        int hang = 0;
        int idHD = Integer.parseInt(tbl_hoaDonChiTiet.getValueAt(0, 10).toString());
        DefaultTableModel model = (DefaultTableModel) tableHoaDonChiTiet.getModel();
        model.setRowCount(0);

        List<HoaDonChiTiet_Model> hoaDonCT = hoaDonService.layChiTietHoaDonTheoId(idHD);

        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        currencyFormat.setMaximumFractionDigits(0);
        for (HoaDonChiTiet_Model hd : hoaDonCT) {
            String thanhTienFormatted = "";
            try {
                String raw = hd.getThanhTien().replace("VND", "").replace(",", "").trim();
                BigDecimal thanhTien = new BigDecimal(raw);
                thanhTienFormatted = currencyFormat.format(thanhTien) + " VND";
            } catch (Exception ex) {
                thanhTienFormatted = hd.getThanhTien(); // fallback nếu lỗi
            }

            model.addRow(new Object[]{
                null,
                hd.getMaSPCT(),
                hd.getTenSP(),
                hd.getSoLuong(),
                thanhTienFormatted});
        }

        DefaultTableModel model2 = (DefaultTableModel) tableLichSu.getModel();
        model2.setRowCount(0);

        int hanhDong = 8;
        LocalDateTime thoiGianInHD = LocalDateTime.now();
        int idNV = nhanvien.getId();

        hoaDonService.hanhDong(idHD, hanhDong, thoiGianInHD, idNV);

        List<LichSuHoaDon> lichSu = hoaDonService.lichSuHoaDonTheoID(idHD);
        for (LichSuHoaDon lichSuHoaDon : lichSu) {

            model2.addTableModelListener(e -> STT.updateSTT(model2, 1));
            model2.addRow(new Object[]{
                null,
                lichSuHoaDon.getMaNV(),
                lichSuHoaDon.getGio(),
                lichSuHoaDon.getNgay(),
                lichSuHoaDon.getHanhDong()});
        }

    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        row = tbl_hoaDonChiTiet.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn để in");
            return;
        }

        HoaDon_Model hd = new HoaDon_Model();
        String maHD = tbl_hoaDonChiTiet.getValueAt(row, 1).toString();
        hd = hoaDonService.layHoaDonTheoMa(maHD);
        List<HoaDonChiTiet_Model> listHDCT = hoaDonService.layChiTietHoaDonTheoId(hd.getId());
        int inHoaDon = JOptionPane.showConfirmDialog(null, "Bạn có muốn in Hóa Đơn không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (inHoaDon == JOptionPane.NO_OPTION) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu hóa đơn");
        fileChooser.setSelectedFile(new File("hoa_don_" + hd.getMaHD() + ".pdf"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();

            if (!path.endsWith(".pdf")) {
                path += ".pdf"; // đảm bảo đuôi .pdf
            }

            int idHD = hd.getId();
            int hanhDong = 6;
            LocalDateTime thoiGianInHD = LocalDateTime.now();
            int idNV = nhanvien.getId();
            try {
                HoaDonPDFExporter.inHoaDonPDF(hd, listHDCT, path);
                JOptionPane.showMessageDialog(this, "In hóa đơn thành công tại:\n" + path);
                hoaDonService.hanhDong(idHD, hanhDong, thoiGianInHD, idNV);

                DefaultTableModel model2 = (DefaultTableModel) tableLichSu.getModel();
                model2.setRowCount(0);

                List<LichSuHoaDon> lichSu = hoaDonService.lichSuHoaDonTheoID(idHD);
                for (LichSuHoaDon lichSuHoaDon : lichSu) {

                    model2.addTableModelListener(e -> STT.updateSTT(model2, 1));
                    model2.addRow(new Object[]{
                        null,
                        lichSuHoaDon.getMaNV(),
                        lichSuHoaDon.getGio(),
                        lichSuHoaDon.getNgay(),
                        lichSuHoaDon.getHanhDong()});
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất hóa đơn!");
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tbl_quetORActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_quetORActionPerformed

        WebCamQrHD qr = new WebCamQrHD(this);
        qr.setVisible(true);
        qr.startCameraAndScan();


    }//GEN-LAST:event_tbl_quetORActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Sau;
    private javax.swing.JButton btn_cuoi;
    private javax.swing.JButton btn_dau;
    private javax.swing.JButton btn_truoc;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_trang;
    private javax.swing.JTable tableHoaDonChiTiet;
    private javax.swing.JTable tableLichSu;
    private javax.swing.JTable tbl_hoaDonChiTiet;
    private javax.swing.JButton tbl_quetOR;
    private javax.swing.JTextField txt_tiemKiem;
    // End of variables declaration//GEN-END:variables

    public void setNhanVien(Model_NhanVien nv) {
        this.nhanvien = nv;
    }
}
