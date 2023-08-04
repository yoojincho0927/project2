package com.human.web01.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void mailSend(String from, String to, String subject, String content) {
		log.info("메일보내기를 시작합니다.....");
		MailHandler MailHandler = null;
		try {
			MailHandler = new MailHandler(javaMailSender);
			MailHandler.setFrom(from, "웹마스터");
			MailHandler.setTo(to);
			MailHandler.setSubject(subject);
			MailHandler.setText(content);
			MailHandler.send();
			log.info(from +"가 " + to +"에게 메일을 성공적으로 보냈습니다.");
		}catch (Exception e) {
			log.info(from +"가 " + to +"에게 메일 보내기를 실패했습니다.");
			e.printStackTrace();
		}
	}
	public void mailSend2(String from, String to, String subject, String content, String cid) {
		log.info("메일보내기를 시작합니다.....");
		MailHandler MailHandler = null;
		try {
			MailHandler = new MailHandler(javaMailSender);
			MailHandler.setFrom(from, "웹마스터");
			MailHandler.setTo(to);
			MailHandler.setSubject(subject);
			MailHandler.setText(content);
			MailHandler.addInline(cid, new ClassPathResource("static/images/linux-icon2.png"));
			MailHandler.send();
			log.info(from +"가 " + to +"에게 메일을 성공적으로 보냈습니다.");
		}catch (Exception e) {
			log.info(from +"가 " + to +"에게 메일 보내기를 실패했습니다.");
			e.printStackTrace();
		}
	}
	public void mailSend3(String from, String to, String subject, String content, String[] cids, String[] filenames) {
		log.info("메일보내기를 시작합니다.....");
		MailHandler MailHandler = null;
		try {
			MailHandler = new MailHandler(javaMailSender);
			MailHandler.setFrom(from, "웹마스터");
			MailHandler.setTo(to);
			MailHandler.setSubject(subject);
			MailHandler.setText(content);
			// 그림의 개수만큼 반복
			for(int i=0;i<cids.length;i++) {
				MailHandler.addInline(cids[i], new ClassPathResource("static/images/"+filenames[i]));
			}
			MailHandler.send();
			log.info(from +"가 " + to +"에게 메일을 성공적으로 보냈습니다.");
		}catch (Exception e) {
			log.info(from +"가 " + to +"에게 메일 보내기를 실패했습니다.");
			e.printStackTrace();
		}
	}
}
