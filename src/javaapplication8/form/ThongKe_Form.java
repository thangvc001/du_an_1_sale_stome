package javaapplication8.form;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javaapplication8.model.ThongKeDoanhThu;
import javaapplication8.model.ThongKeDoanhThuNgay;
import javaapplication8.model.Top10SP;
import javaapplication8.service.ThongKeService;
import javaapplication8.service.serviceimpl.ThongKeServiceImpl;
import javaapplication8.until.EmailUtil;
import javaapplication8.until.FileExcelTop10SP;
import javaapplication8.until.STT;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ThongKe_Form extends javax.swing.JPanel {

    private ThongKeService tkservice = new ThongKeServiceImpl();

    Date today = new Date();

    public String getTongTien() {
        return lbl_tongtien.getText();
    }

    public String getTongSP() {
        return lbl_tongsp.getText();
    }

    public String getTongHoaDon() {
        return lbl_tonghoadon.getText();
    }

    public Date getNgayBD() {
        return ngayBD.getDate();
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc.getDate();
    }

    public ThongKe_Form() {
        initComponents();

        ngayBD.setDate(today);
        ngayKetThuc.setDate(today);

        Date tuNgay = ngayBD.getDate();
        Date denNgay = ngayKetThuc.getDate();

        fillTableTop10SP(tkservice.layTop10SP(tuNgay, denNgay));

        fillCacLabelThongKe(tkservice.thongKeDoanhThu(tuNgay, denNgay));

        hienThiBieuDoTheoNgay(tkservice.getDoanhThuTheoNgay(tuNgay, denNgay));

        //nam:
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        cbo_nam.removeAllItems();
        cbo_nam.addItem("Tất cả");
        for (int i = currentYear - 4; i <= currentYear; i++) {
            cbo_nam.addItem(String.valueOf(i));
        }

        cbo_thang.removeAllItems();
        cbo_thang.addItem("Tất cả");
        for (int i = 1; i <= 12; i++) {
            cbo_thang.addItem(String.valueOf(i));
        }

    }

    private void hienThiBieuDoTheoNgay(List<ThongKeDoanhThuNgay> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ThongKeDoanhThuNgay tk : list) {
            dataset.addValue(tk.getTongTien(), "Doanh thu", tk.getNhomThoiGian());
        }

        JFreeChart chart = ChartFactory.createAreaChart("Biểu đồ doanh thu theo ngày",
                "Ngày", "Doanh thu (VND)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);

        panelThongKe.removeAll();
        panelThongKe.setLayout(new java.awt.BorderLayout());
        panelThongKe.add(chartPanel, BorderLayout.CENTER);
        panelThongKe.revalidate();
        panelThongKe.repaint();

    }

    private void fillTableTop10SP(List<Top10SP> list) {
        DefaultTableModel model = (DefaultTableModel) tableSP.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

// Chỉ gắn listener 1 lần nếu chưa có
        if (model.getTableModelListeners().length == 0) {
            model.addTableModelListener(e -> STT.updateSTT(model, 1));
        }

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        for (Top10SP sp : list) {
            BigDecimal tongTien = sp.getTongTien();
            String tienFormatted;

            if (tongTien != null) {
                tienFormatted = formatter.format(tongTien) + " VND";
            } else {
                tienFormatted = "0 VND"; // Hoặc "N/A" tùy bạn muốn
            }

            model.addTableModelListener(e -> STT.updateSTT(model, 1));
            model.addRow(new Object[]{
                null,
                sp.getTenSP(),
                sp.getSoLuong(),
                tienFormatted
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_tongtien = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_tongsp = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbl_tonghoadon = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ngayBD = new com.toedter.calendar.JDateChooser();
        ngayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        btn_guibaocao = new javax.swing.JButton();
        btn_taiexcel = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSP = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbo_nam = new javax.swing.JComboBox<>();
        cbo_thang = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        panelThongKe = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1027, 689));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(51, 0, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("TỔNG TIỀN");

        lbl_tongtien.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_tongtien.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tongtien.setText("TỔNG TIỀN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tongtien)
                    .addComponent(jLabel2))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lbl_tongtien)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 0, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TỔNG SẢN PHẨM");

        lbl_tongsp.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_tongsp.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tongsp.setText("TỔNG SẢN PHẨM");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tongsp)
                    .addComponent(jLabel1))
                .addGap(57, 57, 57))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lbl_tongsp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 0));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TỔNG HÓA ĐƠN");

        lbl_tonghoadon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_tonghoadon.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tonghoadon.setText("TỔNG HÓA ĐƠN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tonghoadon)
                    .addComponent(jLabel3))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lbl_tonghoadon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(51, 51, 51)));
        jPanel5.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("Lọc theo khoảng thời gian: Từ");

        ngayBD.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ngayBDPropertyChange(evt);
            }
        });

        ngayKetThuc.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ngayKetThucPropertyChange(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Đến");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ngayBD, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ngayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ngayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(2, 2, 2))
                    .addComponent(ngayBD, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_guibaocao.setBackground(new java.awt.Color(0, 0, 153));
        btn_guibaocao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_guibaocao.setForeground(new java.awt.Color(255, 255, 255));
        btn_guibaocao.setText("Gửi báo cáo");
        btn_guibaocao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guibaocaoActionPerformed(evt);
            }
        });

        btn_taiexcel.setBackground(new java.awt.Color(153, 51, 0));
        btn_taiexcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_taiexcel.setForeground(new java.awt.Color(255, 255, 255));
        btn_taiexcel.setText("Tải EXCEL");
        btn_taiexcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taiexcelActionPerformed(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setText("TOP 10 Sản phẩm bán chạy");

        tableSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Tên sản phẩm", "Số lượng bán", "Tổng tiền"
            }
        ));
        jScrollPane1.setViewportView(tableSP);
        if (tableSP.getColumnModel().getColumnCount() > 0) {
            tableSP.getColumnModel().getColumn(0).setMinWidth(50);
            tableSP.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableSP.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2052, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản phẩm đã bán", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Năm");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Tháng");

        cbo_nam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_namActionPerformed(evt);
            }
        });
        cbo_nam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbo_namPropertyChange(evt);
            }
        });

        cbo_thang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_thang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_thangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(cbo_nam, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbo_thang, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_nam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_thang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 51));
        jLabel11.setText("Biểu đồ tổng");

        panelThongKe.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelThongKeLayout = new javax.swing.GroupLayout(panelThongKe);
        panelThongKe.setLayout(panelThongKeLayout);
        panelThongKeLayout.setHorizontalGroup(
            panelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        panelThongKeLayout.setVerticalGroup(
            panelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(581, 581, 581)
                        .addComponent(jLabel11))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(panelThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2091, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(panelThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Biểu đồ", jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_guibaocao, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_taiexcel)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_guibaocao)
                        .addComponent(btn_taiexcel)))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ngayBDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ngayBDPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            kiemtratop10();
        }
    }//GEN-LAST:event_ngayBDPropertyChange

    private void ngayKetThucPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ngayKetThucPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            kiemtratop10();
        }
    }//GEN-LAST:event_ngayKetThucPropertyChange

    private void cbo_namPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbo_namPropertyChange

    }//GEN-LAST:event_cbo_namPropertyChange

    private void cbo_namActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_namActionPerformed
        capNhatBieuDoTheoNamThang();
    }//GEN-LAST:event_cbo_namActionPerformed

    private void cbo_thangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_thangActionPerformed
        capNhatBieuDoTheoNamThang();
    }//GEN-LAST:event_cbo_thangActionPerformed

    private void btn_guibaocaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guibaocaoActionPerformed
        String email = JOptionPane.showInputDialog(null, "Nhập địa chỉ email:");
        if (email != null && !email.trim().isEmpty()) {
            guiEmailBaoCao(email);
        }
    }//GEN-LAST:event_btn_guibaocaoActionPerformed

    private void btn_taiexcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taiexcelActionPerformed
        Date tuNgay = ngayBD.getDate();
        Date denNgay = ngayKetThuc.getDate();

        List<Top10SP> list = tkservice.layTop10SP(tuNgay, denNgay);
        FileExcelTop10SP.exportTop10SPToExcel(list);
    }//GEN-LAST:event_btn_taiexcelActionPerformed

    private void guiEmailBaoCao(String email) {
        String tieuDe = "Báo cáo doanh thu";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String tuNgay = sdf.format(ngayBD.getDate());
        String denNgay = sdf.format(ngayKetThuc.getDate());

        String noiDung = """
                         
                         Báo cáo doanh thu từ %s đến %s:
                                 
                                 - Tổng doanh thu: %s
                                 - Tổng sản phẩm đã bán: %s
                                 - Tổng số hóa đơn: %s
                                 
                                 Trân trọng,
                                 Hệ thống quản lý bán hàng.
                                 """.formatted(tuNgay, denNgay,
                lbl_tongtien.getText(),
                lbl_tongsp.getText(),
                lbl_tonghoadon.getText());

        try {
            EmailUtil.sendEmail(email, tieuDe, noiDung);
            JOptionPane.showMessageDialog(null, "Đã gửi báo cáo tới " + email);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gửi thất bại: " + e.getMessage());
        }
    }

    private void capNhatBieuDoTheoNamThang() {
        String namStr = (String) cbo_nam.getSelectedItem();
        String thangStr = (String) cbo_thang.getSelectedItem();

        if (namStr == null || thangStr == null) {
            return;
        }
        List<ThongKeDoanhThuNgay> list = new ArrayList<>();

        if ("Tất cả".equals(namStr)) {
            list = tkservice.getDoanhThuTheo5NamGanNhat();
            hienThiBieuDoTheoNam(list);
        } else if (!"Tất cả".equals(namStr) && "Tất cả".equals(thangStr)) {
            int nam = Integer.parseInt(namStr);
            list = tkservice.getDoanhThuTheoThang(nam);
            hienThiBieuDoTheoThang(list, nam);
        } else if (!"Tất cả".equals(namStr) && !"Tất cả".equals(thangStr)) {
            int nam = Integer.parseInt(namStr);
            int thang = Integer.parseInt(thangStr);
            list = tkservice.getDoanhThuTheoNgayTrongThang(nam, thang);
            hienThiBieuDoTheoNgay(list);
        }
    }

    private void hienThiBieuDoTheoThang(List<ThongKeDoanhThuNgay> list, int nam) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ThongKeDoanhThuNgay tk : list) {
            dataset.addValue(tk.getTongTien(), "Doanh thu", tk.getNhomThoiGian());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh thu theo tháng năm " + nam,
                "Tháng", "Doanh thu (VND)",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        hienThiBieuDo(chart);
    }

    private void hienThiBieuDoTheoNam(List<ThongKeDoanhThuNgay> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ThongKeDoanhThuNgay tk : list) {
            dataset.addValue(tk.getTongTien(), "Doanh thu", tk.getNhomThoiGian());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Doanh thu 5 năm gần nhất",
                "Năm", "Doanh thu (VND)",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        hienThiBieuDo(chart);
    }

    private void hienThiBieuDo(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        panelThongKe.removeAll();
        panelThongKe.setLayout(new java.awt.BorderLayout());
        panelThongKe.add(chartPanel, BorderLayout.CENTER);
        panelThongKe.revalidate();
        panelThongKe.repaint();
    }

    private void kiemtratop10() {
        Date tuNgay = ngayBD.getDate();
        Date denNgay = ngayKetThuc.getDate();
        if (tuNgay != null && denNgay != null) {
            fillTableTop10SP(tkservice.layTop10SP(tuNgay, denNgay));
            fillCacLabelThongKe(tkservice.thongKeDoanhThu(tuNgay, denNgay));
            hienThiBieuDoTheoNgay(tkservice.getDoanhThuTheoNgay(tuNgay, denNgay));
        }
    }

    private void fillCacLabelThongKe(ThongKeDoanhThu thongKeDoanhThu) {
        if (thongKeDoanhThu == null) {
            lbl_tongtien.setText("0 VND");
            lbl_tongsp.setText("0 sản phẩm");
            lbl_tonghoadon.setText("0 hóa đơn");
            return;
        }

        BigDecimal tongTien = thongKeDoanhThu.getTongTien();

        // Debug in ra kiểm tra
        System.out.println("tongTien = " + tongTien + " | class = " + (tongTien != null ? tongTien.getClass() : "null"));

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        String tienFormatted = "0 VND";
        try {
            if (tongTien != null) {
                tienFormatted = formatter.format(tongTien) + " VND";
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi format tiền: " + e.getMessage());
        }

        lbl_tongtien.setText(tienFormatted);
        lbl_tongsp.setText(thongKeDoanhThu.getTongSP() + " sản phẩm");
        lbl_tonghoadon.setText(thongKeDoanhThu.getTongHD() + " hóa đơn");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_guibaocao;
    private javax.swing.JButton btn_taiexcel;
    private javax.swing.JComboBox<String> cbo_nam;
    private javax.swing.JComboBox<String> cbo_thang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_tonghoadon;
    private javax.swing.JLabel lbl_tongsp;
    private javax.swing.JLabel lbl_tongtien;
    private com.toedter.calendar.JDateChooser ngayBD;
    private com.toedter.calendar.JDateChooser ngayKetThuc;
    private javax.swing.JPanel panelThongKe;
    private javax.swing.JTable tableSP;
    // End of variables declaration//GEN-END:variables

}
