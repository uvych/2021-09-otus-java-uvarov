package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Optional<Message>> history = new TreeMap<>();

    @Override
    public void onUpdated(Message msg) {
        history.put(msg.getId(), Optional.of(msg.cloneMessage()));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return history.get(id);
    }
}
