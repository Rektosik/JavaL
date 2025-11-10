package ua.tests;

import org.junit.jupiter.api.Test;
import ua.util.InvalidDataException;
import ua.util.Utils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTests {

    @Test
    void testFileNotFound() {
        assertThrows(InvalidDataException.class,
                () -> Utils.readDataFromFile("no_file.txt"));
    }

    @Test
    void testParserReadsFileCorrectly() throws Exception {
        String path = "students_test.txt";
        Utils.createExampleFile(path);

        List<String[]> data = Utils.readDataFromFile(path);

        assertEquals(3, data.size());        // в тестовому файлі 3 записи
        assertEquals("Alice", data.get(0)[0]);
    }
}
