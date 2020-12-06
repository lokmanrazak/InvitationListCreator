package com.lokmanrazak.main.java;

import com.lokmanrazak.main.java.interfaces.DistanceCalculator;
import com.lokmanrazak.main.java.interfaces.JsonHandler;
import com.lokmanrazak.main.java.models.Customer;
import com.lokmanrazak.main.java.utilities.JacksonJsonHandler;
import com.lokmanrazak.main.java.utilities.SimpleDistanceCalculator;

import java.net.URL;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InvitationListCreator {
    private static final double START_LATITUDE = 53.339428;
    private static final double START_LONGITUDE = -6.257664;
    private static final double MEAN_EARTH_RADIUS = 6371.009;
    private static final double DISTANCE = 100;

    private final JsonHandler jsonHandler;

    public InvitationListCreator(JsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    public static void main(String[] args) {
        try {
            URL url = InvitationListCreator.class.getProtectionDomain().getCodeSource().getLocation();
            Path filePath = Path.of(url.toURI().resolve(args[0]));

            DistanceCalculator distanceCalculator = new SimpleDistanceCalculator(START_LATITUDE, START_LONGITUDE, MEAN_EARTH_RADIUS);
            JsonHandler jsonHandler = new JacksonJsonHandler(distanceCalculator);
            InvitationListCreator invitationListCreator = new InvitationListCreator(jsonHandler);

            invitationListCreator.outputList(filePath.toString(), DISTANCE);

            System.out.println("Successfully completed!");

        } catch (Exception e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }

    public void outputList(String filePath, double distance) throws Exception {
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
