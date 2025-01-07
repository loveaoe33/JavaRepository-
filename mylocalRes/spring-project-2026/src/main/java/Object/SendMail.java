package Object;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class SendMail {

    @Autowired
    private JavaMailSender  mailService;
    
    public SendMail(JavaMailSender  mailService) {
    	
    	this.mailService=mailService;
    }
	public void mailInit(String to, String subject, String body,String mailAddress) {
		
		
		try {
			SimpleMailMessage message=new SimpleMailMessage();
			message.setFrom(mailAddress);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			mailService.send(message);
		}catch(Exception  e ) {
			
	        System.err.println("郵件發送失敗: " + e.getMessage());

			
		}

	}
	
}
