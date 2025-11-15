package javaapplication8.form;

import java.math.BigDecimal;
import javaapplication8.service.HoaDonService;
import javaapplication8.service.SanPhamChiTietService;
import javaapplication8.service.serviceimpl.HoaDonServiceImpl;
import javaapplication8.service.serviceimpl.SanPhamChiTietServiceImpl;
import javaapplication8.until.CodeGeneratorUtil;
import javax.swing.JOptionPane;

public class NhapSoLuongMuonMua extends javax.swing.JFrame {

    private SanPhamChiTietService sanPhamChiTietService = new SanPhamChiTietServiceImpl();
    private HoaDonService hoaDonService = new HoaDonServiceImpl();
    private int IDSPCT;
    private int IDHD;
    private Runnable reloadCallback;

    public NhapSoLuongMuonMua(int idSPCT, int idHD, Runnable reloadCallback) {
        this.IDSPCT = idSPCT;
        this.IDHD = idHD;
        this.reloadCallback = reloadCallback;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_soluong = new javax.swing.JTextField();
        lbl_thongbao = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Nhập số lượng muốn mua:");

        txt_soluong.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_soluongCaretUpdate(evt);
            }
        });

        lbl_thongbao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_thongbao.setForeground(new java.awt.Color(255, 0, 51));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 255));
        jButton1.setText("Xác nhận");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 255));
        jButton2.setText("Hủy");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                        .addGap(39, 39, 39)
                        .addComponent(txt_soluong, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_thongbao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(17, 17, 17)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_thongbao, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_soluongCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_soluongCaretUpdate
        String input = txt_soluong.getText().trim();
        int soLuong;

        if (!input.isEmpty()) {
            try {
                soLuong = Integer.parseInt(input);
                int soLuongTrongKho = sanPhamChiTietService.kiemTraSanPhamChiTiet(IDSPCT);
                System.out.println(soLuongTrongKho);

                if (soLuong > soLuongTrongKho) {
                    lbl_thongbao.setText("Số lượng mua vượt quá số lượng trong kho");
                } else if (soLuong <= 0) {
                    lbl_thongbao.setText("Số lượng phải lớn hơn 0");
                } else {
                    lbl_thongbao.setText(""); // Không có lỗi
                }

            } catch (NumberFormatException e) {
                lbl_thongbao.setText("Vui lòng nhập số hợp lệ");
            }
        } else {
            lbl_thongbao.setText("Không được để trống số lượng");
        }

    }//GEN-LAST:event_txt_soluongCaretUpdate

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String input = txt_soluong.getText().trim();
        int soLuong;

        if (!input.isEmpty()) {
            try {
                soLuong = Integer.parseInt(input);
                int soLuongTrongKho = sanPhamChiTietService.kiemTraSanPhamChiTiet(IDSPCT);
                System.out.println(soLuongTrongKho);

                if (soLuong > soLuongTrongKho) {
                    return;
                } else if (soLuong <= 0) {
                    return;
                } else {
                    lbl_thongbao.setText(""); // Không có lỗi
                }

            } catch (NumberFormatException e) {
                return;
            }
        } else {
            return;
        }
        int soLuongNhap = Integer.valueOf(txt_soluong.getText().trim());
        System.out.println("So luong nhap: " + soLuongNhap);
        int soLuongCu = hoaDonService.laySoLuongTheoSPCT(IDHD, IDSPCT);
        boolean thanhCong;
        String maHDCT = CodeGeneratorUtil.generateHoaDonChiTiet();
        int soLuongMoi = soLuongNhap + soLuongCu;

        if (soLuongCu > 0) {
            //Cap nhat số lượng trong hóa đơn chi tiết
            thanhCong = hoaDonService.capNhatSoLuong(IDHD, IDSPCT, soLuongMoi);
        } else {
            //Them mới sản phẩm vào hóa đơn chi tiết
            BigDecimal donGia = sanPhamChiTietService.layDonGiaSPCT(IDSPCT);
            thanhCong = hoaDonService.themSanPhamVaoHoaDon(IDHD, IDSPCT, soLuongNhap, donGia, maHDCT);

        }

        if (thanhCong) {
            //Cập nhật tồn kho:
            sanPhamChiTietService.truSoLuongTon(IDSPCT, soLuongNhap);
            if(reloadCallback!=null){
                reloadCallback.run();
            }
            JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm vào giỏ hàng");
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int idSPCT = 0;
                int idHD = 0;
                Runnable reloadCallBack = null;
                new NhapSoLuongMuonMua(idSPCT, idHD, reloadCallBack).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_thongbao;
    private javax.swing.JTextField txt_soluong;
    // End of variables declaration//GEN-END:variables
}
