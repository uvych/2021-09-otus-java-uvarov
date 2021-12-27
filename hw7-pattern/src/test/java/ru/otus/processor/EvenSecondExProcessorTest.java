package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.EvenSecondExProcessor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EvenSecondExProcessorTest {

    @Test
    void evenSecondExProcessor() {
        var message = new Message.Builder(1L).field7("field7").build();
        var processor = new EvenSecondExProcessor(() -> LocalDateTime.of(1, 1, 1, 1, 1, 12, 12));
        assertThrows(RuntimeException.class, () -> processor.process(message));
    }
}
