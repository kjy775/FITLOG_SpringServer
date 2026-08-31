package com.fastcam.spserver.service;

import com.fastcam.spserver.verification.VerificationCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender JMSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    private final HashMap<String, VerificationCode> codeList = new HashMap<>();

    public void sendEmail(String email) {
        int number = (int)(Math.random() * (900000)) + 100000;
        MimeMessage message = JMSender.createMimeMessage();
        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("FITLOG 이메일 인증");
            String body = "";
            body += "<h3>" + "FITLOG에 요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        JMSender.send(message);
        codeList.put(email, new VerificationCode(number));
    }


    public String confirmCode(String userNumber, String email) {
        String msg = "";
        if(!codeList.containsKey(email))
            msg = "먼저 인증번호를 발급받으세요.";
        else {
            VerificationCode vc = codeList.get(email);
            if (vc.isExpired()) {
                msg = "만료된 인증번호입니다. 인증번호를 새로 발급받으세요.";
                codeList.remove(email);
            }
            else {
                String code = String.valueOf(vc.getCode());
                if (code.equals(userNumber)) {
                    msg = "ok";
                    codeList.remove(email);
                } else
                    msg = "잘못된 인증번호입니다.";
            }
        }
        return msg;
    }
}
