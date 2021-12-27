package ru.otus.processor.homework;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.util.data.DateTimeProvider;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class EvenSecondExProcessor implements Processor {
    private DateTimeProvider dateTimeProvider;

    @Override
    public Message process(Message message) {
        if (isEvenSecond(Objects.requireNonNullElseGet(dateTimeProvider, () -> LocalDateTime::now))) {
            throw new RuntimeException("Even second");
        }
        return message;
    }

    private boolean isEvenSecond(DateTimeProvider dateTimeProvider) {
        LocalDateTime time = dateTimeProvider.getDate();
        return time.getSecond() % 2 == 0;
    }
}
