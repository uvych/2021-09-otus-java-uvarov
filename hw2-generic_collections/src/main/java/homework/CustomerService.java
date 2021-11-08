package homework;


import java.util.*;
import java.util.Map.Entry;

public class CustomerService {
    private final NavigableMap<Customer, String> customerMap= new TreeMap<>(
        Comparator.comparing(Customer::getScores)
    );

    public Map.Entry<Customer, String> getSmallest() {
        Entry<Customer, String> entry = customerMap.firstEntry();
        return getImmutableEntry(entry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Entry<Customer, String> entry = customerMap.higherEntry(customer);
        return entry == null ? null : getImmutableEntry(entry);
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }

    private Map.Entry<Customer, String> getImmutableEntry(Entry<Customer, String> entry) {
        Customer customer = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
        return Map.entry(customer, entry.getValue());
    }
}
