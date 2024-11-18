package net.engineeringdigest.journalApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	private final static Logger logger = LoggerFactory.getLogger(JournalEntryService.class);
	  public void sendEmail(String to, String subject, String body) {
	        try {
	            SimpleMailMessage mail = new SimpleMailMessage();
	            mail.setTo(to);
	            mail.setSubject(subject);
	            mail.setText(body);
	            javaMailSender.send(mail);
	        } catch (Exception e) {
	        	logger.error("Exception while sendEmail ", e);
	        }
	    }
	
}
