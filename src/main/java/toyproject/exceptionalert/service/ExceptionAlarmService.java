package toyproject.exceptionalert.service;

import org.aspectj.lang.ProceedingJoinPoint;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public interface ExceptionAlarmService {
    MimeMessage makeAlarm(String receiver, String methodExceptionOccur, String exceptionMessage) throws MessagingException, UnsupportedEncodingException;
    void sendAlarm(String methodExceptionOccur, String exceptionMessage) throws MessagingException, UnsupportedEncodingException;
}
