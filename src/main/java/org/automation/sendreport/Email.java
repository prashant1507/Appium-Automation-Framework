package org.automation.sendreport;

import java.util.Base64;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.automation.constants.GlobalVars;
import org.automation.enums.ConfigMap;
import org.automation.setpath.ReportPath;
import org.automation.utils.PropertyUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Email class is responsible for setting up the mail server. Its also responsible for sending the generated Test Report via email.
 * 
 * April 07, 2021
 * @author User1
 * @version 1.0
 * 
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Email {
	static String encodedPassword;
	static String receiversEmailID;
	static String emailId;
	static String report;
	static String reportName;

	static {
		encodedPassword = PropertyUtils.get(ConfigMap.EMAILPASSWORD);
		receiversEmailID = PropertyUtils.get(ConfigMap.RECEIVERSID);
		emailId = PropertyUtils.get(ConfigMap.SENDERSID);
		report = ReportPath.getReportPath();
		reportName = report.split("/")[report.split("/").length - 1];

	}

	public static void sendMail() {
		try {
			if (PropertyUtils.get(ConfigMap.SENDMAILAFTEREXECUTION)
					.equalsIgnoreCase(GlobalVars.getYes())) {

				Properties props = System.getProperties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.ssl.checkserveridentity", true);

				if (PropertyUtils.get(ConfigMap.SENDMAILUSING).equalsIgnoreCase("Gmail")) {
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.port", "465");
				} else if (PropertyUtils.get(ConfigMap.SENDMAILUSING).equalsIgnoreCase("Outlook")) {
					props.put("mail.smtp.host", "smtp.live.com");
					props.put("mail.smtp.port", "587");
				}

				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						try {
							String decodedPassword = new String(Base64.getDecoder().decode(encodedPassword.getBytes()));
							return new PasswordAuthentication(emailId, decodedPassword);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}
				});
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(emailId));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiversEmailID));
				message.setSubject("Test Execution Report - " + reportName);

				BodyPart body = new MimeBodyPart();
				body.setText("Hi,\nPlease find attached QA test report for "
						+ PropertyUtils.get(ConfigMap.ENVIRONMENT) + ".\nThanks & regards\n"
						+ PropertyUtils.get(ConfigMap.TESTERNAME));
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(report);
				mimeBodyPart.setDataHandler(new DataHandler(source));
				mimeBodyPart.setFileName(reportName);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(body);
				multipart.addBodyPart(mimeBodyPart);
				message.setContent(multipart);
				Transport.send(message);
				System.out.println("\n\n");
				System.out.println("----------------------------");
				System.out.println("|                          |");
				System.out.println("| Report sent successfully |");
				System.out.println("|                          |");
				System.out.println("----------------------------");
				System.out.println("\n\n");
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}