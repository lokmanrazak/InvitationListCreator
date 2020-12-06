package com.lokmanrazak.main.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
    @JsonProperty("user_id")
    public int userId;
    public String name;
    public double latitude;
    public double longitude;
    public double distance;
}
