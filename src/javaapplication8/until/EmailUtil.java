/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication8.until;

import java.awt.Transparency;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

/**
 *
 * @author phamd
 */
public class EmailUtil {
    private static final String EMAIL_SENDER = "phamductoan030222@gmail.com";
    private static final String PASSWORD = "yezl ortz lezi tiwr";
    
    public static String sendOTP(String emailReceiver) {
        String otp = generateOTP();
        String subject = "Mã xác nhận đặt lại mật khẩu";
        String message = "Mã xác nhận của bạn là: " + otp 
                         + ". Vui lòng không chia sẻ mã này với ai.";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, PASSWORD);
            }
        });
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(EMAIL_SENDER));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiver));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            System.err.println("OTP đã gửi đến: " + emailReceiver);
            return otp;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void sendEmail(String to, String subject, String body) throws Exception {
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_SENDER, PASSWORD);
                }
            });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_SENDER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
    
    private static String generateOTP() {
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000);
        return String.valueOf(otp);
    }
}
