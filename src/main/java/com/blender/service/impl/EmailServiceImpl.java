package com.blender.service.impl;

import com.blender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
     @Autowired
     private JavaMailSender javaMailSender;
     @Override
     public void sendEmail(String to, String body, String sub) {
          SimpleMailMessage message = new SimpleMailMessage();
          message.setTo(to);
          message.setSubject(sub);
          message.setText(body);
          javaMailSender.send(message);

     }
}
