package javaapplication8.main;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import javaapplication8.event.EventMenuSelected;
import javaapplication8.form.DoiMatKhau_Form;
import javaapplication8.form.Form_Home;
import javaapplication8.form.HoaDon_Form;
import javaapplication8.form.KhachHangForm;
import javaapplication8.form.KhuyenMai_Form;
import javaapplication8.form.LichSu_Form;
import javaapplication8.form.NhanVien_Form;
import javaapplication8.form.SanPham_Form;
import javaapplication8.form.ThongKe_Form;
import javaapplication8.model.Model_NhanVien;
import javaapplication8.until.EmailUtil;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends javax.swing.JFrame {

    private Form_Home home;
    private SanPham_Form sanPham_Form;
    private ThongKe_Form thongKe_Form;
    private NhanVien_Form nhanVien_Form;
    private HoaDon_Form hoaDon_Form;
    private KhachHangForm khachHangForm;
    private LichSu_Form lichSu_Form;
    private KhuyenMai_Form khuyenMai_Form;
    private DoiMatKhau_Form doiMatKhau_Form;

    public Main(Model_NhanVien nv) {
        initComponents();
        setBackground(new Color(0, 0, 0));
        home = new Form_Home();
        sanPham_Form = new SanPham_Form();
        thongKe_Form = new ThongKe_Form();
        nhanVien_Form = new NhanVien_Form();

        khachHangForm = new KhachHangForm();
        lichSu_Form = new LichSu_Form();
        lichSu_Form.setNhanVien(nv);
        hoaDon_Form = new HoaDon_Form(nv, lichSu_Form);
        hoaDon_Form.setNhanVien(nv);
        khuyenMai_Form = new KhuyenMai_Form();
        doiMatKhau_Form = new DoiMatKhau_Form();
        header1.setNhanVien(nv);

        menu1.initMoving(Main.this);
        menu1.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                System.out.println("Menu clicked, index: " + index);
                if (index == 0) {
                    setForm(thongKe_Form);
                } else if (index == 1) {
                    setForm(sanPham_Form);
                } else if (index == 2) {
                    setForm(nhanVien_Form);
                } else if (index == 3) {
                    setForm(hoaDon_Form);
                } else if (index == 4) {
                    setForm(khachHangForm);
                } else if (index == 5) {
                    setForm(lichSu_Form);
                } else if (index == 6) {
                    setForm(khuyenMai_Form);
                } else if (index == 7) {
                    setForm(doiMatKhau_Form);
                } else if (index == 9) {
                    int xacNhan = JOptionPane.showConfirmDialog(Main.this, "Bạn có muốn đăng xuất không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (xacNhan == JOptionPane.YES_OPTION) {
                        if (nv.isVaiTro()) {
                            // Nếu là quản lý → thoát và về màn hình đăng nhập
                            Main.this.dispose(); // Đóng cửa sổ hiện tại
                            Login lg = new Login();
                            lg.setVisible(true); // Hiển thị form đăng nhập
                        } else {
                            // Nếu là nhân viên → yêu cầu gửi báo cáo trước khi thoát
                            JOptionPane.showMessageDialog(null, "Vui lòng gửi báo cáo trước khi đăng xuất!");

                            // Hiển thị dialog nhập email báo cáo
                            String email = JOptionPane.showInputDialog(null, "Nhập địa chỉ email:");
                            if (email != null && !email.trim().isEmpty()) {
                                guiEmailBaoCao(email);
                            }
                            if (email != null && !email.trim().isEmpty()) {
                                // Ở đây bạn có thể xử lý gửi báo cáo qua email, hoặc lưu lại log nếu cần
                                JOptionPane.showMessageDialog(null, "Báo cáo đã được ghi nhận. Đăng xuất!");

                                Main.this.dispose(); // Đóng cửa sổ
                                Login lg = new Login();
                                lg.setVisible(true);// Quay lại form đăng nhập
                            } else {
                                JOptionPane.showMessageDialog(null, "Báo cáo chưa được gửi. Hủy đăng xuất.");
                            }
                        }
                    }
                }
            }

            private void guiEmailBaoCao(String email) {
                
                Date ngayBD = thongKe_Form.getNgayBD();
                Date ngayKetThuc = thongKe_Form.getNgayKetThuc();
                
                String tieuDe = "Báo cáo doanh thu";

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String tuNgay = sdf.format(ngayBD);
                String denNgay = sdf.format(ngayKetThuc);

                String noiDung = """
                         
                         Báo cáo doanh thu từ %s đến %s:
                                 
                                 - Tổng doanh thu: %s
                                 - Tổng sản phẩm đã bán: %s
                                 - Tổng số hóa đơn: %s
                                 
                                 Trân trọng,
                                 Hệ thống quản lý bán hàng.
                                 """.formatted(tuNgay, denNgay,
                        thongKe_Form.getTongTien(),
                        thongKe_Form.getTongSP(),
                        thongKe_Form.getTongHoaDon());

                try {
                    EmailUtil.sendEmail(email, tieuDe, noiDung);
                    JOptionPane.showMessageDialog(null, "Đã gửi báo cáo tới " + email);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Gửi thất bại: " + e.getMessage());
                }
            }

        }
        );
        setForm(
                new JPanel());
    }

    private void setForm(JComponent com) {
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new javaapplication8.swing.PanelBorder();
        menu1 = new javaapplication8.component.Menu();
        header1 = new javaapplication8.component.Header();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        header1.setBackground(new java.awt.Color(102, 102, 255));

        mainPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 793, Short.MAX_VALUE)
                        .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Model_NhanVien nv = new Model_NhanVien();
                new Main(nv).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapplication8.component.Header header1;
    private javax.swing.JPanel mainPanel;
    private javaapplication8.component.Menu menu1;
    private javaapplication8.swing.PanelBorder panelBorder1;
    // End of variables declaration//GEN-END:variables
}
