/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.swing;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author phamd
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerifyOTPDialog extends JDialog {
    private JTextField txtOTP;
    private JButton btnVerify;
    private String expectedOTP;
    private String email;

    public VerifyOTPDialog(Frame parent, String otp, String email) {
        super(parent, "Xác nhận OTP", true);
        this.expectedOTP = otp;
        this.email = email;

        // Cấu hình dialog
        setSize(350, 180);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Panel chứa nội dung
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Nhập mã OTP:", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        txtOTP = new JTextField();
        txtOTP.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(lblTitle);
        panel.add(txtOTP);

        // Nút xác nhận
        btnVerify = new JButton("Xác nhận");
        btnVerify.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerify.setBackground(new Color(66, 135, 245));
        btnVerify.setForeground(Color.WHITE);
        btnVerify.setFocusPainted(false);

        // Xử lý sự kiện xác nhận OTP
        btnVerify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtOTP.getText().trim().equals(expectedOTP)) {
                    JOptionPane.showMessageDialog(VerifyOTPDialog.this, "✅ Xác nhận thành công!");
                    dispose();
                    new DatLaiMatKhauDialog(parent, email).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(VerifyOTPDialog.this, "❌ OTP không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Thêm các thành phần vào dialog
        add(panel, BorderLayout.CENTER);
        add(btnVerify, BorderLayout.SOUTH);
    }
}

