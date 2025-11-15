package javaapplication8.service;

public interface OTPService {
    String generateOTP(String email);
    
    boolean verifyOTP(String email, String otp);
}
