package com.lokmanrazak.test.java;

import com.fasterxml.jackson.core.JsonParseException;
import com.lokmanrazak.main.java.interfaces.DistanceCalculator;
import com.lokmanrazak.main.java.models.Customer;
import com.lokmanrazak.main.java.utilities.JacksonJsonHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class JacksonJsonHandlerTests {
    @Test
    public void getCustomerList_givenValidFile_returnCorrectResult() throws Exception {
        String filePath = getClass().getClassLoader().getResource("customers.txt").getPath();

        JacksonJsonHandler jsonHandler = new JacksonJsonHandler(mock(DistanceCalculator.class));
        List<Customer> result = jsonHandler.getCustomerList(filePath);

        assertEquals(result.size(), 3);

        assertEquals(result.get(0).userId, 12);
        assertEquals(result.get(0).name, "Christina McArdle");
        assertEquals(result.get(0).latitude, 52.986375);
        assertEquals(result.get(0).longitude, -6.043701);

        assertEquals(result.get(1).userId, 1);
        assertEquals(result.get(1).name, "Alice Cahill");
        assertEquals(result.get(1).latitude, 51.92893);
        assertEquals(result.get(1).longitude, -10.27699);

        assertEquals(result.get(2).userId, 2);
        assertEquals(result.get(2).name, "Ian McArdle");
        assertEquals(result.get(2).latitude, 51.8856167);
        assertEquals(result.get(2).longitude, -10.4240951);
    }

    @Test
    public void getCustomerList_givenMissingFile_throwException() {
        JacksonJsonHandler jsonHandler = new JacksonJsonHandler(mock(DistanceCalculator.class));

        assertThrows(FileNotFoundException.class, () -> jsonHandler.getCustomerList("unknown.txt"));
    }

    @Test
    public void getCustomerList_givenWrongJson_throwException(@TempDir Path tempDir) throws IOException {
        Path tempPath = tempDir.resolve("wrong_json.txt");
        Files.writeString(tempPath, "{ name: John Doe, address: 12 Main Street }");

        JacksonJsonHandler jsonHandler = new JacksonJsonHandler(mock(DistanceCalculator.class));

        assertThrows(JsonParseException.class, () -> jsonHandler.getCustomerList(tempPath.toString()));
    }

    @Test
    public void outputFile_givenValidList_returnCorrectOutput(@TempDir Path tempDir) throws Exception {
        JacksonJsonHandler jsonHandler = new JacksonJsonHandler(mock(DistanceCalculator.class));

        List<Customer> customers = Arrays.asList(
                createCustomer(1, "John Green"),
                createCustomer(2, "Sarah Lee"));

        Path tempPath = tempDir.resolve("customers.txt");

        jsonHandler.outputFile(tempPath.toString(), customers);

        List<String> result = Files.readAllLines(tempPath.resolveSibling("output.txt"));

        assertEquals(result.get(0), "{\"user_id\":1,\"name\":\"John Green\"}");
        assertEquals(result.get(1), "{\"user_id\":2,\"name\":\"Sarah Lee\"}");
    }

    private Customer createCustomer(int userId, String name) {
        Customer customer = new Customer();
        customer.userId = userId;
        customer.name = name;
        return customer;
    }
}
