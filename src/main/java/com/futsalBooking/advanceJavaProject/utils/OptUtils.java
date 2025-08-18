package com.futsalBooking.advanceJavaProject.utils;

import java.security.SecureRandom;

public class OptUtils {
    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    public static String generateOtp() {
        int number = random.nextInt(900000) + 100000; // ensures 6 digits
        return String.valueOf(number);
    }
}
