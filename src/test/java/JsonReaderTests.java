package com.lokmanrazak.test.java;

import com.fasterxml.jackson.core.JsonParseException;
import com.lokmanrazak.main.java.Customer;
import com.lokmanrazak.main.java.JsonReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTests {
    @Test
    public void getCustomerList_givenValidFile_returnCorrectResult() throws Exception {
        String filePath = getClass().getClassLoader().getResource("customers.txt").getPath();

        JsonReader jsonReader = new JsonReader();
        List<Customer> result = jsonReader.getCustomerList(filePath);

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
        JsonReader jsonReader = new JsonReader();

        assertThrows(FileNotFoundException.class, () -> jsonReader.getCustomerList("unknown.txt"));
    }

    @Test
    public void getCustomerList_givenWrongJson_throwException(@TempDir Path tempDir) throws IOException {
        Path tempPath = tempDir.resolve("wrong_json.txt");
        Files.writeString(tempPath, "{ name: John Doe, address: 12 Main Street }");

        JsonReader jsonReader = new JsonReader();

        assertThrows(JsonParseException.class, () -> jsonReader.getCustomerList(tempPath.toString()));
    }
}