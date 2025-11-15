package javaapplication8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javaapplication8.until.DBConnect;

public class OTPDAO {
    private static final int OTP_EXPIRY_MINUTES = 5;

    public void luuOTP(String email, String otp) {
        String sql = """
            MERGE INTO OTP AS target
            USING (SELECT ? AS Email, ? AS OTPCode, ? AS ExpiryTime) AS source
            ON target.Email = source.Email
            WHEN MATCHED THEN
                UPDATE SET OTPCode = source.OTPCode, ExpiryTime = source.ExpiryTime
            WHEN NOT MATCHED THEN
                INSERT (Email, OTPCode, ExpiryTime) VALUES (source.Email, source.OTPCode, source.ExpiryTime);
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otp);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES)));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean kiemTraOTP(String email, String otp) {
        String sql = "SELECT OTPCode FROM OTP WHERE Email = ? AND ExpiryTime > ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return otp.equals(rs.getString("OTPCode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
