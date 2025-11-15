package javaapplication8.component;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.ActionListener;
import javaapplication8.main.Login;
import javaapplication8.main.Main;
import javaapplication8.model.Model_NhanVien;
import javaapplication8.service.NhanVienService;
import javaapplication8.service.serviceimpl.NhanVienServiceImpl;
import javaapplication8.swing.QuenMatKhau;
import javaapplication8.until.Text;
import javaapplication8.until.ValidationUtil;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class PanelCover extends javax.swing.JPanel {

    private final NhanVienService nvService = new NhanVienServiceImpl();

    public PanelCover() {
        initComponents();
        setOpaque(false);
        lbl_thongbaoten.setText("");
        lbl_thongbaoten1.setText("");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_tendangnhap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_matkhau = new javax.swing.JPasswordField();
        lbl_thongbaoten = new javax.swing.JLabel();
        lbl_thongbaoten1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Đăng nhập");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tên đăng nhập");

        txt_tendangnhap.setBorder(null);
        txt_tendangnhap.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_tendangnhapCaretUpdate(evt);
            }
        });
        txt_tendangnhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tendangnhapActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mật khẩu");

        txt_matkhau.setBorder(null);
        txt_matkhau.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_matkhauCaretUpdate(evt);
            }
        });

        lbl_thongbaoten.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_thongbaoten.setForeground(new java.awt.Color(255, 0, 51));
        lbl_thongbaoten.setText("jLabel4");

        lbl_thongbaoten1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_thongbaoten1.setForeground(new java.awt.Color(255, 0, 51));
        lbl_thongbaoten1.setText("jLabel4");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 255));
        jButton1.setText("Đăng nhập");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Quên mật khẩu");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(57, 57, 57)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addComponent(jLabel2)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(173, 173, 173)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(97, 97, 97)
                            .addComponent(jButton1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(txt_tendangnhap, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_thongbaoten1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_matkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_thongbaoten, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(156, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txt_tendangnhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_thongbaoten, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_matkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_thongbaoten1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(36, 36, 36))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String tenDN = txt_tendangnhap.getText().trim();
        String mk = new String(txt_matkhau.getPassword()).trim();

        lbl_thongbaoten.setText("");      // Clear thông báo cũ nếu có
        lbl_thongbaoten1.setText("");
        if (ValidationUtil.isEmpty(tenDN)) {
            lbl_thongbaoten.setText(Text.TRONG);
            return;
        }
        if (ValidationUtil.isEmpty(mk)) {
            lbl_thongbaoten1.setText(Text.TRONG);
            return;
        }

        Model_NhanVien nv = nvService.kiemTraDangNhap(tenDN, mk);
        if (nv == null) {
            lbl_thongbaoten1.setText(Text.TT_DN);
            return;
        }

        JOptionPane.showMessageDialog(this, Text.DNTC);
        this.setVisible(false);
        Window window = SwingUtilities.getWindowAncestor(this);
        if(window != null) {
            window.setVisible(false);
        }
        
        Main main = new Main(nv);
        main.setLocationRelativeTo(null);
        main.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_tendangnhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tendangnhapActionPerformed

    }//GEN-LAST:event_txt_tendangnhapActionPerformed

    private void txt_tendangnhapCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_tendangnhapCaretUpdate
        lbl_thongbaoten.setText("");
        lbl_thongbaoten1.setText("");
    }//GEN-LAST:event_txt_tendangnhapCaretUpdate

    private void txt_matkhauCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_matkhauCaretUpdate
        // TODO add your handling code here:
        lbl_thongbaoten.setText("");
        lbl_thongbaoten1.setText("");
    }//GEN-LAST:event_txt_matkhauCaretUpdate

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        Window window = SwingUtilities.getWindowAncestor(this);
        if(window != null) {
            window.setVisible(false);
        }
        
        QuenMatKhau quen = new QuenMatKhau();
        quen.setLocationRelativeTo(null);
        quen.setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gra = new GradientPaint(0, 0, new Color(70, 97, 245), 0, getHeight(), new Color(14, 37, 169));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbl_thongbaoten;
    private javax.swing.JLabel lbl_thongbaoten1;
    private javax.swing.JPasswordField txt_matkhau;
    private javax.swing.JTextField txt_tendangnhap;
    // End of variables declaration//GEN-END:variables
}
