package toyproject.exceptionalert.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import toyproject.exceptionalert.controller.TmpController;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j

@SpringBootTest
@Import(ExceptionAlertAspect.class)
@ExtendWith(MockitoExtension.class)
class ExceptionAlertAspectTest {
    @Autowired
    TmpController tmpController;
    @Test
    void exceptTest() {
        Assertions.assertThrows(IllegalStateException.class, () -> tmpController.main());
        Assertions.assertDoesNotThrow(() -> tmpController.main());
    }
}