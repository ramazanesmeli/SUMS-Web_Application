/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
/**
 *
 * @author Ifeoluwa
 */
import java.util.Properties;
import javax.mail.Authenticator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailController {

			    public void sendEmail(){
    
    final String username = "daraviace@gmail.com";
    final String password = "AURE-lia1";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                props.put("mail.smtp.ssl.trust","smtp.gmail.com");

		Session session = Session.getInstance(props, new GmailAuthenticator( username,password)); 
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("daraviace@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("up815039@myport.ac.ukcom"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
     class GmailAuthenticator extends Authenticator {
         String user;
         String pw;
         public GmailAuthenticator (String username, String password)
         {
             super();
             this.user = username;
             this.pw = password;
                      }
         @Override
         public PasswordAuthentication getPasswordAuthentication (){
         return new PasswordAuthentication(user,pw);
         }
                 
     }

}


  
    

