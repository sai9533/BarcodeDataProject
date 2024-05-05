package com.example.demo.Util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Component
public class EmailUtils {
	
	@Autowired
    private JavaMailSender mailSender;
    
   public boolean sendEmail(String subject,String body,String to)
    {
         // Creating a mime message
        MimeMessage mimeMessage
            = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
         
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body,true);
 
            
 
            // Sending the mail
            mailSender.send(mimeMessageHelper.getMimeMessage());
            return true;
        }
 
        // Catch block to handle MessagingException
        catch (MessagingException e) {
 
            // Display message when exception occurred
            return false;
        
    }
    }
	

}
