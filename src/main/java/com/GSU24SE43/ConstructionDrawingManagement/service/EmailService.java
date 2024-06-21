package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.EmailRequest;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class EmailService {
    final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String username;

    public void sendEmail(EmailRequest request){
        try {
            boolean hasSubject = request.getSubject() != null && !request.getSubject().trim().isEmpty();
            boolean hasBody = request.getBody() != null && !request.getBody().trim().isEmpty();
            boolean hasAttachments = request.getAttachments() != null && !request.getAttachments().isEmpty();

            if (!hasSubject && !hasBody && !hasAttachments) {
                throw new AppException(ErrorCode.EMAIL_CONTENT_NOT_BLANK);
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(username);
            helper.setText(request.getBody());
            helper.setSubject(request.getSubject());

            if(request.getAttachments() != null) {
                List<MultipartFile> attachments = request.getAttachments();
                for (MultipartFile attachment : attachments) {
                    if (attachment.isEmpty()) {
                        System.out.println("Empty attachment: " + attachment.getOriginalFilename());
                        continue;
                    }
                    String attachmentName = attachment.getOriginalFilename();
                    if (attachmentName == null || attachmentName.isBlank()) {
                        attachmentName = attachmentName +"_"+ System.currentTimeMillis();
                    }
                    helper.addAttachment(attachmentName, attachment);
                }
            }

            for (String toEmail : request.getEmails()) {
                helper.setTo(toEmail);
                mailSender.send(message);
            }
            System.out.println("All Mails Sent Successfully");
        }
        catch (MailException e) {
            throw new RuntimeException("Failed to send email", e);
        }
        catch (MessagingException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
