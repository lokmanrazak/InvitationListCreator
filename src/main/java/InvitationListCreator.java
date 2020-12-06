package com.lokmanrazak.main.java;

import com.lokmanrazak.main.java.interfaces.JsonHandler;
import com.lokmanrazak.main.java.models.Customer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InvitationListCreator {
    private JsonHandler jsonHandler;

    public InvitationListCreator(JsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    public void getList(String filePath, double distance) {
        List<Customer> customers = jsonHandler.getCustomerList(filePath);

        List<Customer> filteredCustomers =
                customers
                        .stream()
                        .filter(customer -> customer.distance <= distance)
                        .sorted(Comparator.comparing(customer -> customer.userId))
                        .collect(Collectors.toList());

        jsonHandler.outputFile(filePath, filteredCustomers);
    }
}
