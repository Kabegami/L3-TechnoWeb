package services;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONObject;

public class MailRecovery {
	static final String fromEmail = "noreply.twister06@gmail.com";
	static final String password = "gr2vu2017";
	
	public static JSONObject sendPassword(String toEmail){
		if (toEmail == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		System.out.println("Sending email");
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", 465);
		props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session = Session.getDefaultInstance(props, auth);
		System.out.println("Session created");
		
		String pass = AuthTools.getPasswordFromMail(toEmail);
		if (pass != null){
			String body = "<h3>Password recovery</h3>"
					+ "<p>You have requested your password : "
					+ "<b>" + pass + "</b></p>"
					+ "<i>This is an automated email.</i>";
			sendEmail(session, toEmail, "Password recovery", body);
			return new JSONObject();
		}
		else {
			System.out.println("Email not found in the database");
			return ErrorJSON.serviceRefused("Email address not found", 1);
		}
		
	}
	
	private static void sendEmail(Session session, String toEmail, String subject, String body){
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		    msg.addHeader("format", "flowed");
		    msg.addHeader("Content-Transfer-Encoding", "8bit");
		    
		    msg.setSentDate(new Date());
		    msg.setFrom(new InternetAddress(fromEmail));
		    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
		    msg.setSubject(subject);
		    msg.setText(body, "utf-8", "html");
		    Transport.send(msg);
		    
		    System.out.println("Message sent");

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
