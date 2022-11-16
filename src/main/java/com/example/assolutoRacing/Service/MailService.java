package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest; 


/**
 * 
 * @author nakagawa.so
 * メールサービス.
 */

@Service
public class MailService {
	
	@Autowired
	MailSender mailSender;
	
	static final String CONFIGSET = "ConfigSet";
	/**
	 * メールを送信する.
	 * @param to 送信先
	 * @param from 送信元
	 * @param subject 件名
	 * @param textbody　本文
	 */
	public void send(String to,String from,String subject,String textbody) {
		
		try {
		      AmazonSimpleEmailService client = 
		              AmazonSimpleEmailServiceClientBuilder.standard()
		                .withRegion(Regions.AP_NORTHEAST_1).build();
		      SendEmailRequest request = new SendEmailRequest()
		              .withDestination(
		                      new Destination().withToAddresses(to))
		              .withMessage(new Message()
		            		  .withBody(new Body()
		            		    .withHtml(new Content().withCharset("UTF-8").withData(textbody)))
		            		  .withSubject(new Content()
		            				  .withCharset("UTF-8").withData(subject)))
		              .withSource(from);
//		              .withConfigurationSetName(CONFIGSET);
		      client.sendEmail(request);
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * メール認証用のテキストボディを生成する.
	 * @param url 認証用URL
	 * @return 本文
	 */
	public String createMailAuthTextbody(String url) {
		String textBody = "<p>以下のリンクからユーザー認証を行ってください。</p>"
				+ "<br>"
				+ "<br>"
				+ "<p><a href='"
				+ url
				+ "'>"
				+ url
				+ "</a></p>";
		return textBody;
	}
}
