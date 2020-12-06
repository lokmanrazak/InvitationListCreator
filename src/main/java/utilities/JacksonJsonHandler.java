package com.lokmanrazak.main.java.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.lokmanrazak.main.java.interfaces.DistanceCalculator;
import com.lokmanrazak.main.java.models.Customer;

import java.io.*;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class JacksonJsonHandler {
    private DistanceCalculator distanceCalculator;

    public JacksonJsonHandler(DistanceCalculator distanceCalculator) {
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

    public void outputFile(String filePath, List<Customer> customers) throws Exception {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("outputFilter", SimpleBeanPropertyFilter.filterOutAllExcept("user_id", "name"));

        ObjectMapper om = new ObjectMapper();
        om.setFilterProvider(filterProvider);

        Path outputPath = Path.of(filePath).resolveSibling("output.txt");

        for (Customer customer : customers) {
            Files.writeString(
                    outputPath,
                    om.writeValueAsString(customer) + System.lineSeparator(),
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
        }
    }
}
