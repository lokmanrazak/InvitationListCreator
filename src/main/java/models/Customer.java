package com.lokmanrazak.main.java.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonFilter("outputFilter")
@JsonPropertyOrder({"user_id", "name"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Customer {
    public int userId;
    public String name;
    public double latitude;
    public double longitude;
    public double distance;
}
