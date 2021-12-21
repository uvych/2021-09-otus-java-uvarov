package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.homework.EvenSecondExProcessor;
import ru.otus.processor.homework.SwapProcessor;

import java.util.List;

public class HomeWork {

    public static void main(String[] args) {
        var processors = List.of(new SwapProcessor(),
            new LoggerProcessor(new EvenSecondExProcessor()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
            System.out.println(ex.getMessage());
        });
        var listenerHistory = new HistoryListener();
        complexProcessor.addListener(listenerHistory);
        var message = new Message.Builder(1L)
            .field1("field1")
            .field10("field10")
            .field11("filed11")
            .field12("filed12")
            .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);
        System.out.println("field12: " + result.getField12());
        System.out.println("field11: " + listenerHistory.findMessageById(1L).get().getField11());;
        complexProcessor.removeListener(listenerHistory);
    }
}
