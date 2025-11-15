package javaapplication8.form;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javaapplication8.dao.NhanVienDao;
import javaapplication8.model.Model_NhanVien;
import javaapplication8.service.NhanVienService;
import javaapplication8.service.serviceimpl.NhanVienServiceImpl;
import javaapplication8.until.CodeGeneratorUtil;
import javaapplication8.until.PhanTrang;
import javaapplication8.until.STT;
import javaapplication8.until.Text;
import javaapplication8.until.ValidationUtil;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class NhanVien_Form extends javax.swing.JPanel {

    private NhanVienService nhanVienService;
    private PhanTrang phanTrang;

    public NhanVien_Form() {
        initComponents();
        nhanVienService = new NhanVienServiceImpl();
        phanTrang = new PhanTrang(5);

        txt_timkiemnhanvien.setText("Nhập mã, tên để tìm kiếm");

        txt_timkiemnhanvien.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_timkiemnhanvien.getText().equals("Nhập mã, tên để tìm kiếm")) {
                    txt_timkiemnhanvien.setText("");
                    txt_timkiemnhanvien.setForeground(Color.BLACK); // chuyển về màu chữ bình thường
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_timkiemnhanvien.getText().isEmpty()) {
                    
                    timKiemNgay();
                }
            }
        });

        txt_timkiemnhanvien.getDocument().addDocumentListener(new DocumentListener() {
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

        //Phan trang
        loadData();
        suKienCacNut();
    }

    public void timKiemNgay() {
        try {
            String timKiem = txt_timkiemnhanvien.getText().trim().toLowerCase();
            // Lấy tổng số bản ghi từ Service (hoặc DAO)
            int total = nhanVienService.countAll();
            phanTrang.setTotalItems(total);

            int startSTT = (phanTrang.getCurrentPage() - 1) * phanTrang.getPageSize() + 1;

            if (timKiem.equals("Nhập mã, tên để tìm kiếm")) {
                // Có thể reload danh sách gốc ở đây
                loadData();
                return;
            }

            // Lấy danh sách nhân viên theo trang hiện tại
            List<Model_NhanVien> ds = nhanVienService.danhSachTimKiem(timKiem, phanTrang.getOffset(), phanTrang.getPageSize());

            // Lấy model của bảng để nạp dữ liệu
            DefaultTableModel model = (DefaultTableModel) nhanVienTableModel.getModel();
            model.setRowCount(0); // Xoá dữ liệu cũ

            for (Model_NhanVien nv : ds) {
                model.addRow(new Object[]{
                    null,
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getEmail(),
                    nv.getSodienThoai(),
                    nv.getNgaySinh(),
                    nv.isGioiTinh() ? "Nam" : "Nữ",
                    nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                    nv.getDiaChi(),
                    nv.getTenDN(),
                    nv.getMk(),
                    nv.isTrangThai() ? "Nghỉ việc" : "Đang làm",
                    nv.getId()
                });
            }
            STT.updateSTT(model, startSTT);

            // Hiển thị số trang hiện tại / tổng số trang
            lbl_trang.setText(phanTrang.getCurrentPage() + " / " + phanTrang.getTotalPages());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            // Lấy tổng số bản ghi từ Service (hoặc DAO)
            int total = nhanVienService.countAll();
            phanTrang.setTotalItems(total);

            int startSTT = (phanTrang.getCurrentPage() - 1) * phanTrang.getPageSize() + 1;

            // Lấy danh sách nhân viên theo trang hiện tại
            List<Model_NhanVien> ds = nhanVienService.getPage(phanTrang.getOffset(), phanTrang.getPageSize());

            // Lấy model của bảng để nạp dữ liệu
            DefaultTableModel model = (DefaultTableModel) nhanVienTableModel.getModel();
            model.setRowCount(0); // Xoá dữ liệu cũ

            for (Model_NhanVien nv : ds) {
                model.addTableModelListener(e -> STT.updateSTT(model, startSTT));
                model.addRow(new Object[]{
                    null,
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getEmail(),
                    nv.getSodienThoai(),
                    nv.getNgaySinh(),
                    nv.isGioiTinh() ? "Nam" : "Nữ",
                    nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                    nv.getDiaChi(),
                    nv.getTenDN(),
                    nv.getMk(),
                    nv.isTrangThai() ? "Nghỉ việc" : "Đang làm",
                    nv.getId()
                });
            }

            // Hiển thị số trang hiện tại / tổng số trang
            lbl_trang.setText(phanTrang.getCurrentPage() + " / " + phanTrang.getTotalPages());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_manv = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_NhanVien = new javax.swing.JTextField();
        lbl_hovaten = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_diachi = new javax.swing.JTextArea();
        rdonam = new javax.swing.JRadioButton();
        rdonu = new javax.swing.JRadioButton();
        rdoquanly = new javax.swing.JRadioButton();
        rdonhanvien = new javax.swing.JRadioButton();
        txt_tendangnhap = new javax.swing.JTextField();
        txt_mk = new javax.swing.JPasswordField();
        txt_xacnhanmk = new javax.swing.JPasswordField();
        lbl_email = new javax.swing.JLabel();
        lbl_sodt = new javax.swing.JLabel();
        lbl_tendangnhap = new javax.swing.JLabel();
        lbl_matkhau = new javax.swing.JLabel();
        lbl_nhaplaimatkhau = new javax.swing.JLabel();
        btn_themsanpham = new javax.swing.JButton();
        btn_capnhatsanpham = new javax.swing.JButton();
        btn_xoasanpham = new javax.swing.JButton();
        btn_lammoi = new javax.swing.JButton();
        lbl_ngaysinh = new javax.swing.JLabel();
        txt_NgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        chkTrangThai = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        nhanVienTableModel = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txt_timkiemnhanvien = new javax.swing.JTextField();
        btn_dau = new javax.swing.JButton();
        btn_truoc = new javax.swing.JButton();
        lbl_trang = new javax.swing.JLabel();
        btn_Sau = new javax.swing.JButton();
        btn_cuoi = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Nhân viên");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mã Nhân Viên");

        txt_manv.setEditable(false);
        txt_manv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_manv.setForeground(new java.awt.Color(0, 0, 255));
        txt_manv.setText("###");
        txt_manv.setBorder(null);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Họ và tên:");

        txt_NhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_NhanVien.setForeground(new java.awt.Color(0, 0, 255));
        txt_NhanVien.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_NhanVienCaretUpdate(evt);
            }
        });
        txt_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NhanVienActionPerformed(evt);
            }
        });

        lbl_hovaten.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txt_manv, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_hovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_manv, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txt_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lbl_hovaten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel5.setText("Email");

        jLabel6.setText("Số điện thoại");

        jLabel7.setText("Ngày sinh");

        jLabel8.setText("Giới tính");

        jLabel9.setText("Tên đăng nhập");

        jLabel10.setText("Mật khẩu");

        jLabel11.setText("Vai trò");

        jLabel12.setText("Địa chỉ");

        txt_email.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_emailCaretUpdate(evt);
            }
        });
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });

        jLabel13.setText("Nhập lại mật khẩu");

        txt_sdt.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_sdtCaretUpdate(evt);
            }
        });

        txt_diachi.setColumns(20);
        txt_diachi.setRows(5);
        jScrollPane1.setViewportView(txt_diachi);

        buttonGroup1.add(rdonam);
        rdonam.setText("Nam");

        buttonGroup1.add(rdonu);
        rdonu.setText("Nữ");

        buttonGroup2.add(rdoquanly);
        rdoquanly.setText("Quản lý");

        buttonGroup2.add(rdonhanvien);
        rdonhanvien.setText("Nhân viên");

        txt_tendangnhap.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_tendangnhapCaretUpdate(evt);
            }
        });

        txt_mk.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_mkCaretUpdate(evt);
            }
        });

        txt_xacnhanmk.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_xacnhanmkCaretUpdate(evt);
            }
        });

        lbl_email.setForeground(new java.awt.Color(255, 0, 0));

        lbl_sodt.setForeground(new java.awt.Color(255, 0, 0));

        lbl_tendangnhap.setForeground(new java.awt.Color(255, 0, 0));

        lbl_matkhau.setForeground(new java.awt.Color(255, 0, 0));

        lbl_nhaplaimatkhau.setForeground(new java.awt.Color(255, 0, 0));

        btn_themsanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/material-symbols--add-circle-outline.png"))); // NOI18N
        btn_themsanpham.setText("Thêm");
        btn_themsanpham.setBorder(null);
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

        btn_lammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/lammoi.png"))); // NOI18N
        btn_lammoi.setText("Làm mới");
        btn_lammoi.setBorder(null);
        btn_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiActionPerformed(evt);
            }
        });

        lbl_ngaysinh.setForeground(new java.awt.Color(255, 0, 0));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Trạng thái");

        chkTrangThai.setText("Đang làm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_email)
                            .addComponent(lbl_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_sodt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdoquanly)
                                    .addComponent(rdonam))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdonu)
                                    .addComponent(rdonhanvien)))
                            .addComponent(txt_sdt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_ngaysinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(txt_NgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel8)
                    .addComponent(jLabel11))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_nhaplaimatkhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_tendangnhap)
                    .addComponent(txt_xacnhanmk)
                    .addComponent(txt_mk)
                    .addComponent(lbl_tendangnhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_matkhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(chkTrangThai)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                                .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_xoasanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_themsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(79, 79, 79)
                        .addComponent(btn_capnhatsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(64, 64, 64))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel5)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tendangnhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbl_email, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                                .addComponent(lbl_tendangnhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(chkTrangThai))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txt_mk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_matkhau, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                    .addComponent(lbl_sodt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_xacnhanmk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                    .addComponent(txt_NgaySinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_nhaplaimatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_ngaysinh, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_themsanpham)
                            .addComponent(btn_capnhatsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(rdonam)
                                .addComponent(rdonu)
                                .addComponent(jLabel12))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(rdoquanly)
                                .addComponent(rdonhanvien)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_xoasanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        nhanVienTableModel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Họ tên", "Email", "Số ĐT", "Ngày sinh", "Giới tính", "Vai trò", "Địa chỉ", "Tên đăng nhập", "Mật khẩu", "Trạng thái"
            }
        ));
        nhanVienTableModel.setRowHeight(30);
        nhanVienTableModel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienTableModelMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(nhanVienTableModel);
        if (nhanVienTableModel.getColumnModel().getColumnCount() > 0) {
            nhanVienTableModel.getColumnModel().getColumn(0).setMinWidth(50);
            nhanVienTableModel.getColumnModel().getColumn(0).setPreferredWidth(50);
            nhanVienTableModel.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("Thông tin nhân viên");

        txt_timkiemnhanvien.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txt_timkiemnhanvien.setForeground(new java.awt.Color(51, 51, 255));
        txt_timkiemnhanvien.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(153, 153, 153), null));
        txt_timkiemnhanvien.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_timkiemnhanvienCaretUpdate(evt);
            }
        });
        txt_timkiemnhanvien.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_timkiemnhanvienFocusLost(evt);
            }
        });

        btn_dau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/first.png"))); // NOI18N
        btn_dau.setBorder(null);

        btn_truoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/before.png"))); // NOI18N
        btn_truoc.setBorder(null);

        lbl_trang.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        lbl_trang.setForeground(new java.awt.Color(0, 204, 255));
        lbl_trang.setText("jLabel6");

        btn_Sau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/next.png"))); // NOI18N
        btn_Sau.setBorder(null);

        btn_cuoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/icon/last.png"))); // NOI18N
        btn_cuoi.setBorder(null);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel15.setText("Tìm kiếm theo mã, tên NV:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(31, 31, 31)
                .addComponent(txt_timkiemnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(222, 222, 222)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(466, 466, 466)
                        .addComponent(btn_dau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_truoc)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_trang)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Sau)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cuoi)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timkiemnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_truoc, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cuoi, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_dau, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_trang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Sau, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themsanphamActionPerformed

        Model_NhanVien nv = new Model_NhanVien();

        String maNV = CodeGeneratorUtil.generateNhanVien();
        nv.setMaNV(maNV);
        String hoten = txt_NhanVien.getText().trim();
        if (ValidationUtil.isEmpty(hoten)) {
            lbl_hovaten.setText(Text.TRONG);
            return;
        }
        nv.setTenNV(hoten);
        boolean trangThai = chkTrangThai.isSelected();
        nv.setTrangThai(!trangThai);
        String email = txt_email.getText().trim();
        if (ValidationUtil.isEmpty(email)) {
            lbl_email.setText(Text.TRONG);
            return;
        }

        if (nhanVienService.kiemTraEmail(email) != null) {
            lbl_email.setText("Email đã tồn tại trong hệ thống");
        }
        nv.setEmail(email);

        String sdt = txt_sdt.getText().trim();
        if (ValidationUtil.isEmpty(sdt)) {
            lbl_sodt.setText(Text.TRONG);
            return;
        }
        nv.setSodienThoai(sdt);
        Date ngaySinhDate = txt_NgaySinh.getDate();
        if (ngaySinhDate == null) {
            lbl_ngaysinh.setText(Text.NGAY_SINH);
            return;
        }
        String ngaySinh = new SimpleDateFormat("yyyy-MM-dd").format(ngaySinhDate);
        nv.setNgaySinh(ngaySinh);
        boolean gioiTinh = rdonam.isSelected();
        nv.setGioiTinh(gioiTinh);
        boolean vaiTro = rdoquanly.isSelected();
        nv.setVaiTro(vaiTro);
        String tenDN = txt_tendangnhap.getText().trim();
        nv.setTenDN(tenDN);
        String mk = new String(txt_mk.getPassword());
        nv.setMk(mk);
        String nhapLai = new String(txt_xacnhanmk.getPassword());
        String diaChi = txt_diachi.getText().trim();
        nv.setDiaChi(diaChi);

        if (ValidationUtil.isEmpty(tenDN)) {
            lbl_tendangnhap.setText(Text.TRONG);
            return;
        }
        if (ValidationUtil.isEmpty(mk)) {
            lbl_matkhau.setText(Text.TRONG);
            return;
        }
        if (ValidationUtil.isEmpty(nhapLai)) {
            lbl_nhaplaimatkhau.setText(Text.TRONG);
            return;
        }
        if (!nhapLai.equals(mk)) {
            lbl_nhaplaimatkhau.setText(Text.SAI_MK);
            return;
        }

        int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm nhân viên không?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.NO_OPTION) {
            return;
        }
        boolean themNV = nhanVienService.themMoiNhanVien(nv);
        if (themNV) {
            JOptionPane.showMessageDialog(this, "Thêm mới thành công");
            loadData();
        }

        lammoi();

    }//GEN-LAST:event_btn_themsanphamActionPerformed

    private void btn_capnhatsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatsanphamActionPerformed

        int chonDong = nhanVienTableModel.getSelectedRow();
        if (chonDong < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để chỉnh sửa");
            return;
        }

        Model_NhanVien nv = new Model_NhanVien();

        String maNV = txt_manv.getText().trim();
        nv.setMaNV(maNV);
        String hoten = txt_NhanVien.getText().trim();
        if (ValidationUtil.isEmpty(hoten)) {
            lbl_hovaten.setText(Text.TRONG);
            return;
        }
        nv.setTenNV(hoten);
        boolean trangThai = chkTrangThai.isSelected();
        nv.setTrangThai(!trangThai);
        String email = txt_email.getText().trim();
        if (ValidationUtil.isEmpty(email)) {
            lbl_email.setText(Text.TRONG);
            return;
        }

        nv.setEmail(email);

        String sdt = txt_sdt.getText().trim();
        if (ValidationUtil.isEmpty(sdt)) {
            lbl_sodt.setText(Text.TRONG);
            return;
        }
        nv.setSodienThoai(sdt);
        Date ngaySinhDate = txt_NgaySinh.getDate();
        if (ngaySinhDate == null) {
            lbl_ngaysinh.setText(Text.NGAY_SINH);
            return;
        }
        String ngaySinh = new SimpleDateFormat("yyyy-MM-dd").format(ngaySinhDate);
        nv.setNgaySinh(ngaySinh);
        boolean gioiTinh = rdonam.isSelected();
        nv.setGioiTinh(gioiTinh);
        boolean vaiTro = rdoquanly.isSelected();
        nv.setVaiTro(vaiTro);
        String tenDN = txt_tendangnhap.getText().trim();
        nv.setTenDN(tenDN);
        String mk = new String(txt_mk.getPassword());
        nv.setMk(mk);
        String nhapLai = new String(txt_xacnhanmk.getPassword());
        String diaChi = txt_diachi.getText().trim();
        nv.setDiaChi(diaChi);

        if (ValidationUtil.isEmpty(tenDN)) {
            lbl_tendangnhap.setText(Text.TRONG);
            return;
        }
        if (ValidationUtil.isEmpty(mk)) {
            lbl_matkhau.setText(Text.TRONG);
            return;
        }
        if (ValidationUtil.isEmpty(nhapLai)) {
            lbl_nhaplaimatkhau.setText(Text.TRONG);
            return;
        }
        if (!nhapLai.equals(mk)) {
            lbl_nhaplaimatkhau.setText(Text.SAI_MK);
            return;
        }

        int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn cập nhật nhân viên không?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.NO_OPTION) {
            return;
        }
        boolean themNV = nhanVienService.capNhatNhanVien(nv);
        if (themNV) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadData();
        }

        lammoi();
    }//GEN-LAST:event_btn_capnhatsanphamActionPerformed

    private void btn_xoasanphamMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_xoasanphamMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_xoasanphamMouseMoved

    private void btn_xoasanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoasanphamActionPerformed

        int chonDong = nhanVienTableModel.getSelectedRow();
        if (chonDong < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa");
            return;
        }

        String maNV = txt_manv.getText().trim();

        int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên không?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.NO_OPTION) {
            return;
        }
        boolean themNV = nhanVienService.xoaNhanVien(maNV);
        if (themNV) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            loadData();
        }

        lammoi();

    }//GEN-LAST:event_btn_xoasanphamActionPerformed

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed

        lammoi();
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void lammoi() {
        txt_NhanVien.setText("");
        txt_tendangnhap.setText("");
        txt_email.setText("");
        txt_diachi.setText("");
        txt_manv.setText("###");
        txt_mk.setText("");
        txt_xacnhanmk.setText("");
        txt_sdt.setText("");
        rdonam.isSelected();
        rdoquanly.isSelected();
        chkTrangThai.isSelected();
    }
    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_NhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NhanVienActionPerformed

    private void txt_NhanVienCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_NhanVienCaretUpdate
        lbl_email.setText("");
        lbl_hovaten.setText("");
        lbl_matkhau.setText("");
        lbl_ngaysinh.setText("");
        lbl_nhaplaimatkhau.setText("");
        lbl_sodt.setText("");
        lbl_tendangnhap.setText("");

    }//GEN-LAST:event_txt_NhanVienCaretUpdate

    private void txt_emailCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_emailCaretUpdate
        // TODO add your handling code here:
        lbl_email.setText("");
        lbl_hovaten.setText("");
        lbl_matkhau.setText("");
        lbl_ngaysinh.setText("");
        lbl_nhaplaimatkhau.setText("");
        lbl_sodt.setText("");
        lbl_tendangnhap.setText("");
    }//GEN-LAST:event_txt_emailCaretUpdate

    private void txt_sdtCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_sdtCaretUpdate
        // TODO add your handling code here:
        lbl_email.setText("");
        lbl_hovaten.setText("");
        lbl_matkhau.setText("");
        lbl_ngaysinh.setText("");
        lbl_nhaplaimatkhau.setText("");
        lbl_sodt.setText("");
        lbl_tendangnhap.setText("");
    }//GEN-LAST:event_txt_sdtCaretUpdate

    private void txt_tendangnhapCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_tendangnhapCaretUpdate
        // TODO add your handling code here:
        lbl_email.setText("");
        lbl_hovaten.setText("");
        lbl_matkhau.setText("");
        lbl_ngaysinh.setText("");
        lbl_nhaplaimatkhau.setText("");
        lbl_sodt.setText("");
        lbl_tendangnhap.setText("");
    }//GEN-LAST:event_txt_tendangnhapCaretUpdate

    private void txt_mkCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_mkCaretUpdate
        // TODO add your handling code here:
        lbl_email.setText("");
        lbl_hovaten.setText("");
        lbl_matkhau.setText("");
        lbl_ngaysinh.setText("");
        lbl_nhaplaimatkhau.setText("");
        lbl_sodt.setText("");
        lbl_tendangnhap.setText("");
    }//GEN-LAST:event_txt_mkCaretUpdate

    private void txt_xacnhanmkCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_xacnhanmkCaretUpdate
        // TODO add your handling code here:
        lbl_email.setText("");
        lbl_hovaten.setText("");
        lbl_matkhau.setText("");
        lbl_ngaysinh.setText("");
        lbl_nhaplaimatkhau.setText("");
        lbl_sodt.setText("");
        lbl_tendangnhap.setText("");
    }//GEN-LAST:event_txt_xacnhanmkCaretUpdate

    private void nhanVienTableModelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienTableModelMouseClicked

        int chonDong = nhanVienTableModel.getSelectedRow();
        txt_manv.setText(nhanVienTableModel.getValueAt(chonDong, 1).toString());
        txt_NhanVien.setText(nhanVienTableModel.getValueAt(chonDong, 2).toString());
        txt_email.setText(nhanVienTableModel.getValueAt(chonDong, 3).toString());
        String ngaySinhStr = nhanVienTableModel.getValueAt(chonDong, 5).toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Hoặc "dd/MM/yyyy" tùy format thực tế

        try {
            Date ngaySinh = sdf.parse(ngaySinhStr);
            txt_NgaySinh.setDate(ngaySinh); // JDateChooser dùng setDate
        } catch (ParseException e) {
            e.printStackTrace(); // hoặc show message lỗi
        }
        txt_sdt.setText(nhanVienTableModel.getValueAt(chonDong, 4).toString());

        if (nhanVienTableModel.getValueAt(chonDong, 6).toString().equals("Nam")) {
            rdonam.setSelected(true);
        } else {
            rdonu.setSelected(true);
        }

        if (nhanVienTableModel.getValueAt(chonDong, 7).toString().equals("Quản lý")) {
            rdoquanly.setSelected(true);
        } else {
            rdonhanvien.setSelected(true);
        }

        txt_tendangnhap.setText(nhanVienTableModel.getValueAt(chonDong, 9).toString());

        txt_mk.setText(nhanVienTableModel.getValueAt(chonDong, 10).toString());

        txt_xacnhanmk.setText(nhanVienTableModel.getValueAt(chonDong, 10).toString());

        txt_diachi.setText(nhanVienTableModel.getValueAt(chonDong, 8).toString());

        if (nhanVienTableModel.getValueAt(chonDong, 11).toString().equals("Đang làm")) {
            chkTrangThai.setSelected(true);
        }


    }//GEN-LAST:event_nhanVienTableModelMouseClicked

    private void txt_timkiemnhanvienCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_timkiemnhanvienCaretUpdate
    }//GEN-LAST:event_txt_timkiemnhanvienCaretUpdate

    private void txt_timkiemnhanvienFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_timkiemnhanvienFocusLost
        String timkiem = txt_timkiemnhanvien.getText().trim();
        timKiemNhanVien(timkiem);
    }//GEN-LAST:event_txt_timkiemnhanvienFocusLost

    private void timKiemNhanVien(String timkiem) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Sau;
    private javax.swing.JButton btn_capnhatsanpham;
    private javax.swing.JButton btn_cuoi;
    private javax.swing.JButton btn_dau;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JButton btn_themsanpham;
    private javax.swing.JButton btn_truoc;
    private javax.swing.JButton btn_xoasanpham;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox chkTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_hovaten;
    private javax.swing.JLabel lbl_matkhau;
    private javax.swing.JLabel lbl_ngaysinh;
    private javax.swing.JLabel lbl_nhaplaimatkhau;
    private javax.swing.JLabel lbl_sodt;
    private javax.swing.JLabel lbl_tendangnhap;
    private javax.swing.JLabel lbl_trang;
    private javax.swing.JTable nhanVienTableModel;
    private javax.swing.JRadioButton rdonam;
    private javax.swing.JRadioButton rdonhanvien;
    private javax.swing.JRadioButton rdonu;
    private javax.swing.JRadioButton rdoquanly;
    private com.toedter.calendar.JDateChooser txt_NgaySinh;
    private javax.swing.JTextField txt_NhanVien;
    private javax.swing.JTextArea txt_diachi;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_manv;
    private javax.swing.JPasswordField txt_mk;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_tendangnhap;
    private javax.swing.JTextField txt_timkiemnhanvien;
    private javax.swing.JPasswordField txt_xacnhanmk;
    // End of variables declaration//GEN-END:variables

    private void suKienCacNut() {

        btn_dau.addActionListener(e -> {
            phanTrang.firstPage();
            loadData();
        });

        btn_truoc.addActionListener(e -> {
            if (!phanTrang.isFirstPage()) {
                phanTrang.prevPage();
                loadData();
            }
        });

        btn_Sau.addActionListener(e -> {
            if (!phanTrang.isLastPage()) {
                phanTrang.nextPage();
                loadData();
            }
        });

        btn_cuoi.addActionListener(e -> {
            phanTrang.lastPage();
            loadData();
        });

    }
}
