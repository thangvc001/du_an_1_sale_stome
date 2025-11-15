/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.swing;

/**
 *
 * @author phamd
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javaapplication8.main.Login;
import javaapplication8.service.NhanVienService;
import javaapplication8.service.serviceimpl.NhanVienServiceImpl;

public class DatLaiMatKhauDialog extends JDialog {
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnSubmit;
    private JCheckBox chkShowPassword; // Thêm checkbox hiển thị mật khẩu
    private String email; 
    private NhanVienService  nhanVienService;

    public DatLaiMatKhauDialog(Frame parent, String email) {
        super(parent, "Đặt lại mật khẩu", true);
        this.email = email;
        this.nhanVienService = new NhanVienServiceImpl();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Label và Field Mật khẩu mới
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Mật khẩu mới:"), gbc);

        txtNewPassword = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtNewPassword, gbc);

        // Label và Field Xác nhận mật khẩu
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Xác nhận mật khẩu:"), gbc);

        txtConfirmPassword = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtConfirmPassword, gbc);

        // Checkbox hiển thị mật khẩu
        chkShowPassword = new JCheckBox("Hiển thị mật khẩu");
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        add(chkShowPassword, gbc);

        // Nút xác nhận
        btnSubmit = new JButton("Xác nhận");
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
        add(btnSubmit, gbc);

        // Xử lý sự kiện ẩn/hiện mật khẩu
        chkShowPassword.addActionListener(e -> togglePasswordVisibility(txtNewPassword, txtConfirmPassword));

        // Xử lý xác nhận thay đổi mật khẩu
        btnSubmit.addActionListener(e -> handleChangePassword());

        setSize(450, 250);
        setLocationRelativeTo(parent);
    }

    // Phương thức để ẩn/hiện mật khẩu khi nhấn nút 👁
    private void togglePasswordVisibility(JPasswordField passwordField) {
        if (passwordField.getEchoChar() == '\u2022') {
            passwordField.setEchoChar((char) 0); // Hiển thị mật khẩu
        } else {
            passwordField.setEchoChar('\u2022'); // Ẩn mật khẩu
        }
    }

    // Phương thức để thay đổi trạng thái hiển thị khi chọn checkbox
    private void togglePasswordVisibility(JPasswordField... fields) {
        boolean show = chkShowPassword.isSelected();
        for (JPasswordField field : fields) {
            field.setEchoChar(show ? (char) 0 : '\u2022');
        }
    }

    // Xử lý cập nhật mật khẩu
    private void handleChangePassword() {
        String newPassword = new String(txtNewPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đổi mật khẩu?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean updated = nhanVienService.resetPassword(email, newPassword);
            if (updated) {
                JOptionPane.showMessageDialog(this, "Mật khẩu đã được cập nhật thành công!");
                dispose();
                new Login().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

