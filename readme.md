# Exception Alarm

**스프링 AOP를 실습하기 위해 제작된 토이 프로젝트**

@ExceptionAlert 애노테이션을 메소드에 붙이면, 해당 메소드에서 Exception 발생 시 개발자가 구현한 방법을 통해 알람이 전파된다.

예를 들어, 아래와 같이 Controller에 애노테이션을 붙여서 사용할 수 있다.

```java
@RestController
public class TmpController {
    private static int err = 0;
    
    @ExceptionAlert // 해당 애노테이션을 추가하면, Exception 발생 시 알림을 보낸다.
    @GetMapping("/")
    public String main() {
        if(err++ % 5 != 0) { return "ok"; }
        else throw new IllegalStateException("Exception occurs in TmpController");
    }
}
```

개발자는 아래의 인터페이스만 구현하면 된다. 아래의 인터페이스를 구현하여 어떤 방법으로 Exception Alarm을 전달할지 구체화한다.

```java
public interface ExceptionAlarmService {
    MimeMessage makeAlarm(String receiver, String methodExceptionOccur, String exceptionMessage) throws MessagingException, UnsupportedEncodingException;
    void sendAlarm(String methodExceptionOccur, String exceptionMessage) throws MessagingException, UnsupportedEncodingException;
}
```

+ makeAlarm : 알림 메세지를 생성한다.
+ sendAlarm : 알림 메세지를 보낸다.

이 프로젝트에서는 구글 이메일을 통해 알림이 전송되는 것을 기본으로 한다.

만약, 구글 이메일 대신 다른 방법을 통해 알림을 전송하고 싶다면, 

ExceptionAlarmService 인터페이스를 구현한 후 ExceptionAlertAspect 클래스에서 새롭게 구현한 ExceptionAlarmService를 주입한다.

```java
@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class ExceptionAlertAspect {
    @Qualifier("gmailExceptionAlarmService") // <- 이 부분만 변경하여 다른 Bean을 주입하면 된다.
    private final ExceptionAlarmService exceptionAlarmService;

    @Around("@annotation(toyproject.exceptionalert.annotation.ExceptionAlert)")
    public Object alertException(ProceedingJoinPoint joinPoint) throws Throwable {
      ...
    }
}
```

실시간으로 에러 모니터링에 유용하게 사용될 것으로 기대된다.
