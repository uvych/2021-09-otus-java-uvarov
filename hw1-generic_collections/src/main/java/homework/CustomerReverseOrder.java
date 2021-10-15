package homework;


import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {
    private final Deque<Customer> customerList = new LinkedList<>();

    public void add(Customer customer) {
        customerList.add(customer);
    }

    public Customer take() {
        return customerList.pollLast();
    }
}
