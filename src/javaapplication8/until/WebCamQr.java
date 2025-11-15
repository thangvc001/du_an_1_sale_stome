/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaapplication8.until;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;
import javaapplication8.dao.SanPhamChiTietDao;
import javaapplication8.form.SanPham_Form;
import javaapplication8.form.ThongTinSanPhamChiTiet;
import javaapplication8.model.SanPham_ChiTiet;
import javaapplication8.service.SanPhamChiTietService;
import javaapplication8.service.serviceimpl.SanPhamChiTietServiceImpl;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author dungc
 */
public class WebCamQr extends javax.swing.JFrame implements Runnable {

    private Webcam webcam;
    private boolean running = true;
    private SanPhamChiTietService service_spct = new SanPhamChiTietServiceImpl();
    private SanPham_Form parentFrame;// để lưu form cha
    private WebcamPanel webcamPanel;
    private JPanel pnl_cam;

    public WebCamQr() {
        this(null); // gọi lại constructor chính và truyền null
        this.setLocationRelativeTo(null);

    }

    public WebCamQr(SanPham_Form parentFrame) {
        this.parentFrame = parentFrame;

        this.setTitle("Quét mã QR");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pnl_cam = new JPanel(new BorderLayout());
        this.add(pnl_cam, BorderLayout.CENTER); // Gắn webcam panel vào frame chính
        this.setLocationRelativeTo(null);

    }

    public void startCameraAndScan() {
        openCamera();            // Mở camera
        new Thread(this).start(); // Bắt đầu luồng quét QR
    }

    private void openCamera() {
        // Lấy webcam mặc định
        webcam = Webcam.getDefault();
        if (webcam != null) {
            // Đảm bảo webcam được đóng trước khi thay đổi độ phân giải (nếu cần)
            if (webcam.isOpen()) {
                webcam.close(); // Đóng webcam nếu nó đang mở
            }

            // Đặt độ phân giải cho webcam
            webcam.setViewSize(WebcamResolution.VGA.getSize());

            // Mở webcam
            webcam.open();

            // Tạo WebcamPanel và thêm vào container
            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setPreferredSize(WebcamResolution.VGA.getSize()); // Đặt kích thước cho webcamPanel

            // Thêm webcamPanel vào pnl_cam (container)
            pnl_cam.setLayout(new BorderLayout());
            pnl_cam.add(webcamPanel, BorderLayout.CENTER);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy webcam!");
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                BufferedImage image = webcam.getImage();
                if (image != null) {
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);

                    if (result != null) {
                        String maSPCT = result.getText().trim();

                        // Gọi service để lấy thông tin sản phẩm
                        SanPham_ChiTiet spct = service_spct.getByMaSPCT(maSPCT);
                        System.out.println("spct: " + spct); // Debug

                        if (spct != null) {
                            SwingUtilities.invokeLater(() -> {
                                // 🛠️ Truyền đúng parentFrame (SanPham_Form)
                                ThongTinSanPhamChiTiet thongtinForm = new ThongTinSanPhamChiTiet(
                                        parentFrame, // ✅ TRUYỀN FORM CHA VÀO
                                        null, // Callback hoặc Runnable nếu có, nếu không để null
                                        spct.getMaSPCT(),
                                        spct.getTenSp(),
                                        spct.getMauSac(),
                                        spct.getChatLieu(),
                                        spct.getKichThuoc(),
                                        spct.getSoLuong(),
                                        Double.parseDouble(spct.getDonGia()),
                                        spct.getQrCode()
                                );

                                thongtinForm.setVisible(true);
                                dispose(); // Đóng form webcam sau khi quét thành công
                            });

                            running = false;
                        } else {
                            System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSPCT);
                        }
                    }
                }

                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace(); // Hiển thị lỗi nếu có
            }
        }
    }

    @Override
    public void dispose() {
        running = false;
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
        super.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_webcam = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout pnl_webcamLayout = new javax.swing.GroupLayout(pnl_webcam);
        pnl_webcam.setLayout(pnl_webcamLayout);
        pnl_webcamLayout.setHorizontalGroup(
            pnl_webcamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        pnl_webcamLayout.setVerticalGroup(
            pnl_webcamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_webcam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_webcam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(WebCamQr.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WebCamQr.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WebCamQr.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WebCamQr.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new WebCamQr().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnl_webcam;
    // End of variables declaration//GEN-END:variables
    }
