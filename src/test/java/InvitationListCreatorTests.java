package com.lokmanrazak.test.java;

import com.lokmanrazak.main.java.InvitationListCreator;
import com.lokmanrazak.main.java.interfaces.JsonHandler;
import com.lokmanrazak.main.java.models.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class InvitationListCreatorTests {
    @Test
    public void outputList_validArguments_returnCorrectResult() throws Exception {
        ArgumentCaptor<List<Customer>> captor = ArgumentCaptor.forClass((Class) List.class);

        JsonHandler jsonHandlerMock = mock(JsonHandler.class);
        InvitationListCreator ilc = new InvitationListCreator(jsonHandlerMock);

        List<Customer> customers = Arrays.asList(
                createCustomer(2, "Michael Jordan", 150),
                createCustomer(3, "Terry Ford", 65),
                createCustomer(1, "Laura Big", 27)
        );

        when(jsonHandlerMock.getCustomerList(anyString())).thenReturn(customers);

        ilc.outputList("customers.txt", 100);

        verify(jsonHandlerMock).getCustomerList(anyString());
        verify(jsonHandlerMock).outputFile(anyString(), captor.capture());

        List<Customer> value = captor.getValue();

        assertEquals(value.size(), 2);

        assertEquals(value.get(0).userId, 1);
        assertEquals(value.get(0).name, "Laura Big");

        assertEquals(value.get(1).userId, 3);
        assertEquals(value.get(1).name, "Terry Ford");
    }

    @Test
    public void endToEnd_givenValidFile_returnCorrectOutput() throws Exception {
        String customersFileName = "customers.txt";
        String outputFileName = "output.txt";

        Path customerPath = Path.of(getClass().getClassLoader().getResource(customersFileName).toURI());
        String content = new String(Files.readAllBytes(customerPath));

        URL url = InvitationListCreator.class.getProtectionDomain().getCodeSource().getLocation();
        Path customerNewPath = Path.of(url.toURI().resolve(customersFileName));
        Files.write(customerNewPath, content.getBytes());

        InvitationListCreator.main(new String[]{customersFileName});

        Path outputPath = Path.of(url.toURI().resolve(outputFileName));
        List<String> result = Files.readAllLines(outputPath);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), "{\"user_id\":12,\"name\":\"Christina McArdle\"}");

        Files.deleteIfExists(customerNewPath);
        Files.deleteIfExists(outputPath);
    }

    private Customer createCustomer(int userId, String name, double distance) {
        Customer customer = new Customer();
        customer.userId = userId;
        customer.name = name;
        customer.distance = distance;
        return customer;
    }
}
