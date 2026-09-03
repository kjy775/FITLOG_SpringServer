package com.fastcam.spserver.service;

import com.fastcam.spserver.verification.VerificationCode;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SMSService {

    private final DefaultMessageService messageService;

    @Value("${coolsms.sender-phone}")
    private String senderPhone;

    private final HashMap<String, VerificationCode> codeList = new HashMap<>();


    public void sendSMS(String toPhone) {
        toPhone = toPhone.replace("-", "");

        int number = (int)(Math.random() * (900000)) + 100000;

        Message message = new Message();
        message.setFrom(senderPhone);
        message.setTo(toPhone);
        message.setText("[FITLOG] 본인확인 인증번호 안내\n\n"
                + "안녕하세요, FITLOG입니다.\n\n"
                + "요청하신 인증번호는 아래와 같습니다.\n\n"
                + "[ " + number + " ]\n\n"
                + "본 인증번호는 5분간 유효합니다.\n"
                + "타인에게 절대 공유하지 마세요.\n\n"
                + "본인이 요청하지 않은 경우 이 메시지를 무시하세요.");
        SingleMessageSentResponse response =
                this.messageService.sendOne(new SingleMessageSendingRequest(message));

        codeList.put(toPhone,new VerificationCode(number));
    }

    public String confirmSMSCode(String userNumber, String phone) {
        phone = phone.replace("-", "");
        String msg = "";
        if(!codeList.containsKey(phone))
            msg = "먼저 인증번호를 발급받으세요.";
        else {
            VerificationCode vc = codeList.get(phone);
            if (vc.isExpired()) {
                msg = "만료된 인증번호입니다. 인증번호를 새로 발급받으세요.";
                codeList.remove(phone);
            }
            else {
                String code = String.valueOf(vc.getCode());
                if (code.equals(userNumber)) {
                    msg = "ok";
                    codeList.remove(phone);
                } else
                    msg = "잘못된 인증번호입니다.";
            }
        }
        return msg;
    }
}
