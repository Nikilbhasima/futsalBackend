package com.futsalBooking.advanceJavaProject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futsalBooking.advanceJavaProject.config.EsewaConfig;
import com.futsalBooking.advanceJavaProject.dto.PaymentRequest;
import com.futsalBooking.advanceJavaProject.dto.PaymentResponse;
import com.futsalBooking.advanceJavaProject.enumFile.PaymentStatus;
import com.futsalBooking.advanceJavaProject.enumFile.PaymentType;
import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import com.futsalBooking.advanceJavaProject.model.Transaction;
import com.futsalBooking.advanceJavaProject.repository.FutsalBookingServiceeRepository;
import com.futsalBooking.advanceJavaProject.repository.TransactionRepository;
import com.futsalBooking.advanceJavaProject.utils.HmacSignatureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EsewaService {


    @Autowired
    private  EsewaConfig esewaConfig;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private HmacSignatureUtil hmacSignatureUtil;

    @Autowired
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private FutsalBookingServiceeRepository serviceeRepository;

    public PaymentResponse initiatePayment(PaymentRequest request,int bookingId) {
        try{
            Futsal_Booking futsal_booking=serviceeRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found"));
            String transactionUuid = hmacSignatureUtil.generateTransactionUuid();

            String totalAmount = String.format("%.2f", request.getTotalAmount());
            String productAmount = String.format("%.2f",
                    request.getTotalAmount() - request.getTaxAmount() -
                            request.getServiceCharge() - request.getDeliveryCharge());

            // Generate signature
            String signatureString = hmacSignatureUtil.generateSignatureString(
                    totalAmount, transactionUuid, request.getProductCode());
            String signature = hmacSignatureUtil.generateHmacSha256Signature(
                    signatureString, esewaConfig.getSecretKey());

            Transaction transaction = new Transaction();
            transaction.setTransactionUuid(transactionUuid);
            transaction.setProductCode(request.getProductCode());
            transaction.setProductName(request.getProductName());
            transaction.setTotalAmount(request.getTotalAmount());
            transaction.setTaxAmount(request.getTaxAmount());
            transaction.setServiceCharge(request.getServiceCharge());
            transaction.setDeliveryCharge(request.getDeliveryCharge());
            transaction.setStatus("PENDING");
            transaction.setCustomerEmail(request.getCustomerEmail());
            transaction.setSignature(signature);
            transaction.setBooking(futsal_booking);

            transactionRepository.save(transaction);

            Map<String, String> formData = new HashMap<>();
            formData.put("amount", productAmount);
            formData.put("tax_amount", String.format("%.2f", request.getTaxAmount()));
            formData.put("total_amount", totalAmount);
            formData.put("transaction_uuid", transactionUuid);
            formData.put("product_code", request.getProductCode());
            formData.put("product_service_charge", String.format("%.2f", request.getServiceCharge()));
            formData.put("product_delivery_charge", String.format("%.2f", request.getDeliveryCharge()));
            formData.put("success_url", esewaConfig.getSuccessUrl());
            formData.put("failure_url", esewaConfig.getFailureUrl());
            formData.put("signed_field_names", "total_amount,transaction_uuid,product_code");
            formData.put("signature", signature);

            return new PaymentResponse(
                    esewaConfig.getPaymentUrl(),
                    transactionUuid,
                    signature,
                    "SUCCESS",
                    "Payment form data generated successfully",
                    formData
            );
        }catch(Exception e){
            log.error("Error initiating payment: ", e);
            return new PaymentResponse(null, null, null, "ERROR",
                    "Failed to initiate payment", null);
        }
    }

    public boolean verifyPayment(String transactionUuid) {
        try {

            Optional<Transaction> transactionOpt = transactionRepository
                    .findByTransactionUuid(transactionUuid);

            if (!transactionOpt.isPresent()) {
                log.error("Transaction not found for UUID: {}", transactionUuid);
                return false;
            }

            Transaction transaction = transactionOpt.get();
            Futsal_Booking futsal_booking = transaction.getBooking();
           
            futsal_booking.setPaymentStatus(PaymentStatus.PAID);
            futsal_booking.setMatchPaymentType(PaymentType.ONLINE);
            serviceeRepository.save(futsal_booking);

            // Build correct verification URL
            String verificationUrl = esewaConfig.getVerificationUrl() +
                    "?product_code=" + esewaConfig.getMerchantCode() +
                    "&total_amount=" + transaction.getTotalAmount() +
                    "&transaction_uuid=" + transactionUuid;

            log.info("Verification URL: {}", verificationUrl);

            HttpGet request = new HttpGet(verificationUrl);
            request.setHeader("Accept", "application/json");

            CloseableHttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            log.info("eSewa verification response: {}", responseBody);

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(responseBody);

            String status = jsonResponse.get("status").asText();

            if ("COMPLETE".equalsIgnoreCase(status)) {
                // Update transaction status
                transaction.setStatus("SUCCESS");
                if (jsonResponse.has("refId")) {
                    transaction.setEsewaTransactionCode(jsonResponse.get("refId").asText());
                }
                transactionRepository.save(transaction);
                return true;
            }
            return false;

        } catch (Exception e) {
            log.error("Error verifying payment: ", e);
            return false;
        }
    }
}
