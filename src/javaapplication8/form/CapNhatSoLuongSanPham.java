package javaapplication8.form;

import java.util.function.Consumer;
import javaapplication8.service.HoaDonService;
import javaapplication8.service.SanPhamChiTietService;
import javaapplication8.service.serviceimpl.HoaDonServiceImpl;
import javaapplication8.service.serviceimpl.SanPhamChiTietServiceImpl;
import javax.swing.JOptionPane;

public class CapNhatSoLuongSanPham extends javax.swing.JFrame {

    private final SanPhamChiTietService sanPhamChiTietService = new SanPhamChiTietServiceImpl();
    private final HoaDonService hoaDonService = new HoaDonServiceImpl();

    private int IDSPCT;
    private int IDHD;
    private Consumer<Integer> callback;
    private int soLuongCu;

    public CapNhatSoLuongSanPham(int idHD, int idSPCT, int soLuongCu, Consumer<Integer> callback) {
        initComponents();
        this.IDHD = idHD;
        this.IDSPCT = idSPCT;
        this.callback = callback;
        this.soLuongCu = soLuongCu;

        txt_sanPham.setText(String.valueOf(soLuongCu));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_sanPham = new javax.swing.JTextField();
        lbl_sanpham = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btn_huy = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Cập nhật số lượng sản phẩm:");

        txt_sanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sanPhamActionPerformed(evt);
            }
        });

        lbl_sanpham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_sanpham.setForeground(new java.awt.Color(255, 0, 51));

        jButton1.setBackground(new java.awt.Color(204, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 204));
        jButton1.setText("Xác nhận");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_huy.setBackground(new java.awt.Color(255, 0, 51));
        btn_huy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_huy.setForeground(new java.awt.Color(255, 255, 255));
        btn_huy.setText("Hủy");
        btn_huy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_huyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_sanPham))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_huy)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(45, 45, 45))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 70, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btn_huy))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_huyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_huyActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String input = txt_sanPham.getText().trim();
        int soLuongMoi;

        if (input.isEmpty()) {
            lbl_sanpham.setText("Không được để trống số lượng");
            return;
        }

        try {
            soLuongMoi = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            lbl_sanpham.setText("Vui lòng nhập số hợp lệ");
            return;
        }

        if (soLuongMoi < 0) {
            lbl_sanpham.setText("Số lượng phải lớn hơn hoặc bằng 0");
            return;
        }

        int soLuongTonKho = sanPhamChiTietService.kiemTraSanPhamChiTiet(IDSPCT);
        System.out.println("Soluongtonkho: " + soLuongTonKho);

        int chenhLech = soLuongMoi - soLuongCu;

// Kiểm tra số lượng tồn kho trước khi thực hiện thao tác
        if (chenhLech > 0) {
            if (chenhLech > soLuongTonKho) {
                lbl_sanpham.setText("Không đủ hàng trong kho (còn " + soLuongTonKho + ")");
                return;
            }
        }

// Nếu có thay đổi số lượng (chenhLech != 0), thực hiện các thao tác
        boolean thanhCong = false;

        if (soLuongMoi == 0) {
            // Xóa sản phẩm khỏi hóa đơn và hoàn lại số lượng vào kho
            boolean thanhCongXoa = hoaDonService.xoaSanPhamKhoiHoaDon(IDHD, IDSPCT);
            boolean thanhCongCongKho = sanPhamChiTietService.congSoLuongTon(IDSPCT, soLuongCu);

            System.out.println("Thành công xóa sản phẩm: " + thanhCongXoa);
            System.out.println("Thành công cộng số lượng vào kho: " + thanhCongCongKho);

            thanhCong = thanhCongXoa && thanhCongCongKho; // Cập nhật trạng thái thành công
        } else {
            // Cập nhật số lượng mới vào hóa đơn
            thanhCong = hoaDonService.capNhatSoLuong(IDHD, IDSPCT, soLuongMoi);

            // Cập nhật số lượng tồn kho
            if (chenhLech > 0) {
                // Nếu số lượng tăng thì trừ đi trong kho
                thanhCong = thanhCong && sanPhamChiTietService.truSoLuongTon(IDSPCT, chenhLech);
            } else if (chenhLech < 0) {
                // Nếu số lượng giảm thì cộng lại vào kho
                thanhCong = thanhCong && sanPhamChiTietService.congSoLuongTon(IDSPCT, -chenhLech);
            }
        }

// Kiểm tra kết quả và gọi callback nếu thành công
        if (thanhCong) {
            callback.accept(soLuongMoi);
            JOptionPane.showMessageDialog(this, "Cập nhật số lượng thành công!");
            this.dispose();
        } else {
            lbl_sanpham.setText("Không thể cập nhật do lỗi tồn kho");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_sanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sanPhamActionPerformed


    }//GEN-LAST:event_txt_sanPhamActionPerformed

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
            java.util.logging.Logger.getLogger(CapNhatSoLuongSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapNhatSoLuongSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapNhatSoLuongSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapNhatSoLuongSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapNhatSoLuongSanPham(1, 1, 5, soLuongMoi -> {
                    System.out.println("Số lượng mới: " + soLuongMoi);
                }).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_huy;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_sanpham;
    private javax.swing.JTextField txt_sanPham;
    // End of variables declaration//GEN-END:variables
}
