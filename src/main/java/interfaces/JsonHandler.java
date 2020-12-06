package com.lokmanrazak.main.java.interfaces;

import com.lokmanrazak.main.java.models.Customer;

import java.util.List;

public interface JsonHandler {
    List<Customer> getCustomerList(String filePath) throws Exception;

    void outputFile(String filePath, List<Customer> customers) throws Exception;
}
