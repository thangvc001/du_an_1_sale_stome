package javaapplication8.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import javaapplication8.model.Model_NhanVien;
import javaapplication8.model.PhieuGiamGiaModel;
import javaapplication8.service.PhieuGiamGiaService;
import javaapplication8.service.serviceimpl.PhieuGiamGiaServiceImpl;
import javaapplication8.until.STT;
import javax.swing.table.DefaultTableModel;

public class KhuyenMai_Form extends javax.swing.JPanel {

    private PhieuGiamGiaService pgggService = new PhieuGiamGiaServiceImpl();
    private String selected;
    int trangThai = 0;

    public KhuyenMai_Form() {
        initComponents();

        cbo_trangthai.removeAllItems();
        cbo_trangthai.addItem("Tất cả");
        cbo_trangthai.addItem("Sắp diễn ra");
        cbo_trangthai.addItem("Đang diễn ra");
        cbo_trangthai.addItem("Đã diễn ra");

        selected = cbo_trangthai.getSelectedItem().toString();
        switch (selected) {
            case "Sắp diễn ra":
                trangThai = 0;
                break;
            case "Đang diễn ra":
                trangThai = 1;
                break;
            case "Đã diễn ra":
                trangThai = 2;
                break;
            default:
                trangThai = -1;
                break;
        }
        fillTablePhieuGiamGiaTheoTrangThai(trangThai);
        // Thêm sự kiện khi chọn một item trong ComboBox
        cbo_trangthai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = cbo_trangthai.getSelectedItem().toString();
                switch (selected) {
                    case "Sắp diễn ra":
                        trangThai = 0;
                        break;
                    case "Đang diễn ra":
                        trangThai = 1;
                        break;
                    case "Đã diễn ra":
                        trangThai = 2;
                        break;
                    default:
                        trangThai = -1;
                        break;
                }

                // Gọi lại phương thức để fill bảng dữ liệu khi trạng thái thay đổi
                fillTablePhieuGiamGiaTheoTrangThai(trangThai);
            }
        });

    }

    private void fillTablePhieuGiamGiaTheoTrangThai(int trangThai) {
        List<PhieuGiamGiaModel> list;

        if (trangThai == -1) {
            list = pgggService.getAll();
        } else {
            list = pgggService.getByTrangThai(trangThai);
        }

        DefaultTableModel model = (DefaultTableModel) tblPhieuGiamGia.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        DecimalFormat df = new DecimalFormat("#,###");
        for (PhieuGiamGiaModel p : list) {
            model.addTableModelListener(e -> STT.updateSTT(model, 1));
            Object[] row = {
                null,
                p.getMaPGG(),
                p.getTenPGG(),
                p.getNgayDB(),
                p.getNgayKT(),
                p.getSoLuong(),
                df.format(p.getHDToiThieu()) + " VNĐ",
                p.getPhantramgiam() + " %",
                df.format(p.getGiamToiDa()) + " VNĐ",
                p.getNgayTao(),
                p.getMaNV(),
                p.getTrangThai() == 0 ? "Sắp diễn ra"
                : p.getTrangThai() == 1 ? "Đang diễn ra"
                : "Đã diễn ra"
            };
            model.addRow(row);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txt_makm = new javax.swing.JTextField();
        txt_tenkm = new javax.swing.JTextField();
        lbl_hoadontt = new javax.swing.JLabel();
        lbl_ngaybatdau = new javax.swing.JLabel();
        txt_soluongduocphepsudung = new javax.swing.JTextField();
        lbl_tenkm1 = new javax.swing.JLabel();
        txt_hoadontt = new javax.swing.JTextField();
        lbl_sotiengiamtoida = new javax.swing.JLabel();
        lbl_ngayketthuc = new javax.swing.JLabel();
        txt_phantramgiam = new javax.swing.JTextField();
        lbl_soluong2 = new javax.swing.JLabel();
        txt_sotiengiamtoida = new javax.swing.JTextField();
        lbl_phantramgiam1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbo_trangthai = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuGiamGia = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("PHIẾU GIẢM GIÁ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 204, 255));
        jLabel2.setText("Mã khuyến mại");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 204, 255));
        jLabel3.setText("Tên khuyến mại");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 204, 255));
        jLabel4.setText("Ngày bắt đầu");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 204, 255));
        jLabel5.setText("Hóa đơn tối thiểu");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 204, 255));
        jLabel6.setText("Ngày kết thúc");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 204, 255));
        jLabel7.setText("Số lượng được phép sử dụng");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 204, 255));
        jLabel8.setText("Phần trăm giảm");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 204, 255));
        jLabel9.setText("Số tiền được giảm tối đa");

        jButton1.setBackground(new java.awt.Color(0, 0, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thêm");

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Hủy");

        jButton3.setBackground(new java.awt.Color(0, 153, 153));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Làm mới");

        txt_makm.setEditable(false);
        txt_makm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_makm.setForeground(new java.awt.Color(0, 0, 255));
        txt_makm.setText("###");

        txt_tenkm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_tenkm.setForeground(new java.awt.Color(0, 0, 255));
        txt_tenkm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenkmActionPerformed(evt);
            }
        });

        lbl_hoadontt.setForeground(new java.awt.Color(255, 0, 51));

        lbl_ngaybatdau.setForeground(new java.awt.Color(255, 0, 51));

        txt_soluongduocphepsudung.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_soluongduocphepsudung.setForeground(new java.awt.Color(0, 0, 255));
        txt_soluongduocphepsudung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_soluongduocphepsudungActionPerformed(evt);
            }
        });

        lbl_tenkm1.setForeground(new java.awt.Color(255, 0, 51));

        txt_hoadontt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_hoadontt.setForeground(new java.awt.Color(0, 0, 255));
        txt_hoadontt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hoadonttActionPerformed(evt);
            }
        });

        lbl_sotiengiamtoida.setForeground(new java.awt.Color(255, 0, 51));

        lbl_ngayketthuc.setForeground(new java.awt.Color(255, 0, 51));

        txt_phantramgiam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_phantramgiam.setForeground(new java.awt.Color(0, 0, 255));
        txt_phantramgiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_phantramgiamActionPerformed(evt);
            }
        });

        lbl_soluong2.setForeground(new java.awt.Color(255, 0, 51));

        txt_sotiengiamtoida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_sotiengiamtoida.setForeground(new java.awt.Color(0, 0, 255));
        txt_sotiengiamtoida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sotiengiamtoidaActionPerformed(evt);
            }
        });

        lbl_phantramgiam1.setForeground(new java.awt.Color(255, 0, 51));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Trạng thái");

        cbo_trangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_trangthai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbo_trangthaiMouseClicked(evt);
            }
        });

        tblPhieuGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KM", "Tên KM", "Ngày BD", "Ngày KT", "Số lượng", "HD tối thiểu", "Số % giảm", "Giảm tối đa", "Ngày tạo", "Người tạo", "Trạng thái"
            }
        ));
        jScrollPane1.setViewportView(tblPhieuGiamGia);
        if (tblPhieuGiamGia.getColumnModel().getColumnCount() > 0) {
            tblPhieuGiamGia.getColumnModel().getColumn(0).setMinWidth(50);
            tblPhieuGiamGia.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblPhieuGiamGia.getColumnModel().getColumn(0).setMaxWidth(50);
            tblPhieuGiamGia.getColumnModel().getColumn(5).setMinWidth(70);
            tblPhieuGiamGia.getColumnModel().getColumn(5).setPreferredWidth(70);
            tblPhieuGiamGia.getColumnModel().getColumn(5).setMaxWidth(70);
            tblPhieuGiamGia.getColumnModel().getColumn(7).setMinWidth(70);
            tblPhieuGiamGia.getColumnModel().getColumn(7).setPreferredWidth(70);
            tblPhieuGiamGia.getColumnModel().getColumn(7).setMaxWidth(70);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(txt_makm)
                            .addComponent(txt_tenkm, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(lbl_tenkm1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_ngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(28, 28, 28)
                                .addComponent(cbo_trangthai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(154, 154, 154)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7)
                                    .addComponent(txt_hoadontt, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                    .addComponent(lbl_hoadontt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_ngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(txt_soluongduocphepsudung)
                                    .addComponent(lbl_soluong2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(131, 131, 131)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lbl_phantramgiam1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txt_phantramgiam, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jButton2)
                                                .addGap(50, 50, 50))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_sotiengiamtoida, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9)
                                            .addComponent(lbl_sotiengiamtoida, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(492, 492, 492)
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)))
                .addComponent(jButton3)
                .addGap(52, 52, 52))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_makm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_soluongduocphepsudung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_phantramgiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_soluong2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tenkm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_hoadontt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sotiengiamtoida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_phantramgiam1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_sotiengiamtoida, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_hoadontt, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_ngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_ngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbl_tenkm1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbo_trangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tenkmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenkmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenkmActionPerformed

    private void txt_soluongduocphepsudungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_soluongduocphepsudungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_soluongduocphepsudungActionPerformed

    private void txt_hoadonttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hoadonttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hoadonttActionPerformed

    private void txt_phantramgiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_phantramgiamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_phantramgiamActionPerformed

    private void txt_sotiengiamtoidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sotiengiamtoidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sotiengiamtoidaActionPerformed

    private void cbo_trangthaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_trangthaiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_trangthaiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbo_trangthai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_hoadontt;
    private javax.swing.JLabel lbl_ngaybatdau;
    private javax.swing.JLabel lbl_ngayketthuc;
    private javax.swing.JLabel lbl_phantramgiam1;
    private javax.swing.JLabel lbl_soluong2;
    private javax.swing.JLabel lbl_sotiengiamtoida;
    private javax.swing.JLabel lbl_tenkm1;
    private javax.swing.JTable tblPhieuGiamGia;
    private javax.swing.JTextField txt_hoadontt;
    private javax.swing.JTextField txt_makm;
    private javax.swing.JTextField txt_phantramgiam;
    private javax.swing.JTextField txt_soluongduocphepsudung;
    private javax.swing.JTextField txt_sotiengiamtoida;
    private javax.swing.JTextField txt_tenkm;
    // End of variables declaration//GEN-END:variables

}
