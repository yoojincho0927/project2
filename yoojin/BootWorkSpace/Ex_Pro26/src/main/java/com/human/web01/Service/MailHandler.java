package com.human.web01.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailHandler {
	private JavaMailSender 		javaMailSender;  // 메일보내기 객체
	private MimeMessage    		message;		 // 메일 내용을 담을 객체 
	private MimeMessageHelper 	messageHelper;	 // 첨부파일을 가능하도록 해주는 객체
	
	// 생성자
	public MailHandler(JavaMailSender javaMailSender) throws MessagingException {
		this.javaMailSender = javaMailSender; 
		message = this.javaMailSender.createMimeMessage();
		messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		// 첫번째 인수는 MimeMessage객체 두번째는 다수의 사람에게 보내기 가능여부 세번째는 인코딩
	}
	// 보내는 사람 지정
	public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
		messageHelper.setFrom(email, name);
	}
	// 받는사람 지정
	public void setTo(String email) throws MessagingException {
		messageHelper.setTo(email);
	}
	// 제목 지정
	public void setSubject(String text) throws MessagingException {
		messageHelper.setSubject(text);
	}
	// 내용 지정
	public void setText(String text) throws MessagingException {
		messageHelper.setText(text, true); // 두번째 인수가 HTML가능여부
	}
	// 내용에 이미지를 추가하는 기능
	public void addInline(String contentId, Resource resource) throws MessagingException {
		messageHelper.addInline(contentId, resource);
	}
	public void addInline(String contentId, File file) throws MessagingException {
		messageHelper.addInline(contentId, file);
	}
	public void addInline(String contentId, DataSource dataSource) throws MessagingException {
		messageHelper.addInline(contentId, dataSource);
	}
	
	// 실제로 메일을 보내는 메서드
	public void send() {
		try {
			javaMailSender.send(message);
			log.info("메일발송 성공!!!===================================================");
		}catch (Exception e) {
			log.info("메일발송 실패!!!===================================================");
			e.printStackTrace();
		}
	}
}
