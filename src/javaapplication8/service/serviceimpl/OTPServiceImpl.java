package javaapplication8.service.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javaapplication8.service.OTPService;
import javaapplication8.until.EmailUtil;

public class OTPServiceImpl implements OTPService {

    private final Map<String, String> otpStorage = new HashMap<>();

    @Override
    public String generateOTP(String email) {
        String otp = EmailUtil.sendOTP(email);

        if (otp != null) {
            otpStorage.put(email, otp);
            return otp;
        }

        return null;
    }

    @Override
    public boolean verifyOTP(String email, String inputOtp) {
        String storedOtp = otpStorage.get(email);

        if (storedOtp != null && storedOtp.equals(inputOtp)) {
            otpStorage.remove(email); // Xóa OTP sau khi xác thực thành công
            return true;
        }
        return false;
    }

}
