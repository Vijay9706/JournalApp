package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

	@Autowired
	EmailService emailService;
	
	@Test
	 void testSendMail() {	
		 emailService.sendEmail("hegishtevijay97@gmail.com", "Testing java Sender", "Hi, aap kaise hai");
	 }
	
}
