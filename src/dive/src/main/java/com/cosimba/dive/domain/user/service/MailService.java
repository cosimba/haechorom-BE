package com.cosimba.dive.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService { // 이메일 발송

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("비밀번호 재설정 요청");
        message.setText("비밀번호를 재설정하려면 다음 링크를 클릭하세요:\n" + resetLink);
        javaMailSender.send(message);
    }
}

