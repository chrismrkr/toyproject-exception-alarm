package toyproject.exceptionalert.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import toyproject.exceptionalert.service.ExceptionAlarmService;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class ExceptionAlertAspect {
    @Qualifier("gmailExceptionAlarmService")
    private final ExceptionAlarmService exceptionAlarmService;

    @Around("@annotation(toyproject.exceptionalert.annotation.ExceptionAlert)")
    public Object alertException(ProceedingJoinPoint joinPoint) throws Throwable {
        Exception exceptionHolder = null;
        try {
            Object proceed = joinPoint.proceed();
            return proceed;
        } catch(Exception e) { // exception 발생 시, alarm 발생
            exceptionAlarmService.sendAlarm(joinPoint.getSignature().toString(), e.getMessage());
            exceptionHolder = e;
        }
        throw exceptionHolder;
    }
}
