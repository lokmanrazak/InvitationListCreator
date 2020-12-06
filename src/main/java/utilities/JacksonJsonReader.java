package com.lokmanrazak.main.java.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokmanrazak.main.java.interfaces.DistanceCalculator;
import com.lokmanrazak.main.java.models.Customer;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JacksonJsonReader {
    private DistanceCalculator distanceCalculator;

    public JacksonJsonReader(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public List<Customer> getCustomerList(String filePath) throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            List<Customer> customers = new ArrayList<>();
            ObjectMapper om = new ObjectMapper();

            String text;
            while ((text = br.readLine()) != null) {
                Customer customer = om.readValue(text, Customer.class);
                customer.distance = distanceCalculator.getDistance(customer.latitude, customer.longitude);
                customers.add(customer);
            }

            return customers;
        }
    }
}
