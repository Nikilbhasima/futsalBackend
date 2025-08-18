package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.utils.OptUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OptService {
    private final Map<String, OtpEntry> otpCache = new ConcurrentHashMap<>();

    public String generateOtp(String key) {
        String otp = OptUtils.generateOtp();
        otpCache.put(key, new OtpEntry(otp, LocalDateTime.now().plusMinutes(5))); // valid 5 min
        return otp;
    }

    public boolean validateOtp(String key, String otp) {
        OtpEntry entry = otpCache.get(key);
        if (entry == null) return false;

        if (entry.getExpiry().isBefore(LocalDateTime.now())) {
            otpCache.remove(key);
            return false; // expired
        }

        boolean isValid = entry.getOtp().equals(otp);
        if (isValid) otpCache.remove(key); // remove after successful use
        return isValid;
    }

    private static class OtpEntry {
        private final String otp;
        private final LocalDateTime expiry;

        public OtpEntry(String otp, LocalDateTime expiry) {
            this.otp = otp;
            this.expiry = expiry;
        }

        public String getOtp() { return otp; }
        public LocalDateTime getExpiry() { return expiry; }
    }
}
