package com.futsalBooking.advanceJavaProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendBookingConfirmationEmail(String to, String playing_date) {
        SimpleMailMessage msg = new SimpleMailMessage();

        StringBuilder string = new StringBuilder();
        string.append("Your booking has been confirmed from\n");

        msg.setTo(to);
        msg.setSubject("Your booking has been confirmed for date: "+playing_date);
        msg.setText(string.toString());

        mailSender.send(msg);
    }

    public void sendOtp(String to, String otp) {
        SimpleMailMessage msg = new SimpleMailMessage();

        StringBuilder string = new StringBuilder();
        string.append("Your OTP to reset your password is:\n").append(otp + "\n")
                .append("Please do not share it with anyone\n").append("It is only valid for 5 minutes");

        msg.setTo(to);
        msg.setSubject("KickStart OTP");
        msg.setText(string.toString());

        mailSender.send(msg);
    }
}
