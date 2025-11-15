package javaapplication8.form;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.function.Consumer;
import javaapplication8.model.KhachHangModel;
import javaapplication8.service.KhachHangService;
import javaapplication8.service.serviceimpl.KhachHangServiceImpl;
import javaapplication8.until.CodeGeneratorUtil;
import javaapplication8.until.STT;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class ChonKhachHangForm extends javax.swing.JFrame {

    private Consumer<KhachHangModel> callback;
    private KhachHangService khService = new KhachHangServiceImpl();

    public ChonKhachHangForm(Consumer<KhachHangModel> callback) {
        this.callback = callback;

        this.setLocationRelativeTo(null);

        initComponents();
        loadData();
        
        txt_tiemKiem.setText("Nhập mã, tên để tìm kiếm");
         txt_tiemKiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_tiemKiem.getText().equals("Nhập mã, tên để tìm kiếm")) {
                    txt_tiemKiem.setText("");
                    txt_tiemKiem.setForeground(Color.BLACK); // chuyển về màu chữ bình thường
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_tiemKiem.getText().isEmpty()) {
                    txt_tiemKiem.setText("Nhập mã, tên để tìm kiếm");
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
    String timKiem = txt_tiemKiem.getText().trim().toLowerCase();
    List<KhachHangModel> danhSach = khService.timKiem(timKiem);
    DefaultTableModel model = (DefaultTableModel) tbl_bangkhachhang.getModel();
    model.setRowCount(0);
    model.addTableModelListener(e -> STT.updateSTT(model,1));

    for (KhachHangModel khachHangModel : danhSach) {
        model.addRow(new Object[]{
            null,
            khachHangModel.getMaKH(),
            khachHangModel.getHoTen(),
            khachHangModel.getSdt(),
            khachHangModel.isGioiTinh() ? "Nam" : "Nữ",
            khachHangModel.getDiaChi(),
            khachHangModel.getId()
        });
    }
}


    private ChonKhachHangForm() {
    }

    private void loadData() {
        List<KhachHangModel> list = khService.danhSachKhachHang();
        DefaultTableModel model = (DefaultTableModel) tbl_bangkhachhang.getModel();
        model.setRowCount(0);
        model.addTableModelListener(e -> STT.updateSTT(model, 1));

        for (KhachHangModel khachHangModel : list) {
            model.addRow(new Object[]{
                null,
                khachHangModel.getMaKH(),
                khachHangModel.getHoTen(),
                khachHangModel.getSdt(),
                khachHangModel.isGioiTinh() ? "Nam" : "Nữ",
                khachHangModel.getDiaChi(),
                khachHangModel.getId()});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bangkhachhang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_tiemKiem = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_tenKhachHang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_SoDT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_diaChi = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbl_bangkhachhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Mã KH", "Tên KH", "SDT", "Giới tính", "Địa chỉ", "idKH"
            }
        ));
        tbl_bangkhachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangkhachhangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_bangkhachhang);
        if (tbl_bangkhachhang.getColumnModel().getColumnCount() > 0) {
            tbl_bangkhachhang.getColumnModel().getColumn(0).setMinWidth(30);
            tbl_bangkhachhang.getColumnModel().getColumn(0).setPreferredWidth(30);
            tbl_bangkhachhang.getColumnModel().getColumn(0).setMaxWidth(30);
            tbl_bangkhachhang.getColumnModel().getColumn(6).setMinWidth(0);
            tbl_bangkhachhang.getColumnModel().getColumn(6).setPreferredWidth(0);
            tbl_bangkhachhang.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("Tìm kiếm");

        txt_tiemKiem.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txt_tiemKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_tiemKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_tiemKiemFocusLost(evt);
            }
        });
        txt_tiemKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tiemKiemActionPerformed(evt);
            }
        });

        jButton1.setText("Chọn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Quay lại");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_tiemKiem))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(txt_tiemKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách khách hàng", jPanel1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 204));
        jLabel2.setText("Tên khách hàng");

        txt_tenKhachHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("Số điện thoại");

        txt_SoDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setText("Giới tính");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 204));
        jLabel5.setText("Địa chỉ");

        txt_diaChi.setColumns(20);
        txt_diaChi.setRows(5);
        jScrollPane2.setViewportView(txt_diaChi);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 204));
        jButton2.setText("Thêm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(204, 0, 0));
        jButton4.setText("Hủy");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_tenKhachHang)
                    .addComponent(txt_SoDT)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(140, 140, 140))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_SoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addGap(0, 28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thiết lập thông tin khách hàng", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int row = tbl_bangkhachhang.getSelectedRow();
        if (row >= 0) {
            String maKH = tbl_bangkhachhang.getValueAt(row, 1).toString();
            String tenKH = tbl_bangkhachhang.getValueAt(row, 2).toString();
            int id = Integer.parseInt(tbl_bangkhachhang.getValueAt(row, 6).toString());
            KhachHangModel kh = new KhachHangModel(maKH, tenKH, id);

            if (callback != null) {
                callback.accept(kh);
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Bạn vui lòng chọn khách hàng trước!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbl_bangkhachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangkhachhangMouseClicked
    }//GEN-LAST:event_tbl_bangkhachhangMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        KhachHangModel kh = new KhachHangModel();

        String maKH = CodeGeneratorUtil.generateKhachHang();
        String tenKhachHang = txt_tenKhachHang.getText().trim();
        String sdt = txt_SoDT.getText().trim();
        boolean gioiTinh = rdoNam.isSelected();
        String diaChi = txt_diaChi.getText().trim();
        kh.setMaKH(maKH);
        kh.setHoTen(tenKhachHang);
        kh.setSdt(sdt);
        kh.setGioiTinh(gioiTinh);
        kh.setDiaChi(diaChi);

        int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khách hàng không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.YES_OPTION) {
            boolean them = khService.addKhachHang(kh);
            if (them) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại");
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_tiemKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tiemKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tiemKiemActionPerformed

    private void txt_tiemKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_tiemKiemFocusLost
        String timKiem = txt_tiemKiem.getText().trim().toLowerCase();
        List<KhachHangModel> danhSach = khService.timKiem(timKiem);
        DefaultTableModel model = (DefaultTableModel) tbl_bangkhachhang.getModel();
        model.setRowCount(0);
        model.addTableModelListener(e -> STT.updateSTT(model, 1));

        for (KhachHangModel khachHangModel : danhSach) {
            model.addRow(new Object[]{
                null,
                khachHangModel.getMaKH(),
                khachHangModel.getHoTen(),
                khachHangModel.getSdt(),
                khachHangModel.isGioiTinh() ? "Nam" : "Nữ",
                khachHangModel.getDiaChi(),
                khachHangModel.getId()});
        }
    }//GEN-LAST:event_txt_tiemKiemFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChonKhachHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChonKhachHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChonKhachHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChonKhachHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChonKhachHangForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tbl_bangkhachhang;
    private javax.swing.JTextField txt_SoDT;
    private javax.swing.JTextArea txt_diaChi;
    private javax.swing.JTextField txt_tenKhachHang;
    private javax.swing.JTextField txt_tiemKiem;
    // End of variables declaration//GEN-END:variables
}
