package toyproject.exceptionalert.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.exceptionalert.annotation.ExceptionAlert;

@Slf4j
@RestController
public class TmpController {
    private static int err = 0;
    @ExceptionAlert
    @GetMapping("/")
    public String main() {
        if(err++ % 5 != 0) { return "ok"; }
        else throw new IllegalStateException("Exception occurs");
    }
}
