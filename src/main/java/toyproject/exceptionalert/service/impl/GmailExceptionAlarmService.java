package toyproject.exceptionalert.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import toyproject.exceptionalert.service.ExceptionAlarmService;
import toyproject.exceptionalert.service.MemberService;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Qualifier("gmailExceptionAlarmService")
public class GmailExceptionAlarmService implements ExceptionAlarmService {
    private final JavaMailSender javaMailSender;
    @Qualifier("dummyMemberService")
    private final MemberService memberService;

    @Override
    public MimeMessage makeAlarm(String receiver, String methodExceptionOccur, String exceptionMessage) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, receiver);//보내는 대상
        message.setSubject("[에러 발생 알림]");

        StringBuilder msgg = new StringBuilder();
        msgg.append("<div style='margin:100px;'>");
        msgg.append("<h1> [Exception 발생 Alarm] </h1>");
        msgg.append("<br>");
        msgg.append("<p>아래의 메소드 호출에서 에러가 발생했습니다. 즉시 조치 바랍니다.<p>");
        msgg.append("<br>");
        msgg.append("<p>"+methodExceptionOccur+"<p>");
        msgg.append("<br>");
        msgg.append("<div align='center' style='border:1px solid black;>");
        msgg.append("<h3 style='color:red;'>"+exceptionMessage+"</h3>");
        msgg.append("</div>");

        message.setText(msgg.toString(), "utf-8", "html");
        message.setFrom(new InternetAddress("email.properties","Exception 관리자"));

        return message;
    }
    @Override
    public void sendAlarm(String methodExceptionOccur, String exceptionMessage) throws MessagingException, UnsupportedEncodingException {
        List<String> emailList = memberService.getEmailList();
        for(String email : emailList) {
            MimeMessage message = makeAlarm(email, methodExceptionOccur, exceptionMessage);
            try {
                javaMailSender.send(message);
            } catch(MailException e) {
                e.printStackTrace();
                throw new IllegalArgumentException();
            }
        }
    }
}
