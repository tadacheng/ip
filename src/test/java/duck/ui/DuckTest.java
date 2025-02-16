package duck.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DuckTest {
    private final Path testFilePath = Paths.get("data", "duck_test.txt");
    private Duck duck;
    @BeforeEach
    void setUp() {
        // Initialize Duck
        duck = new Duck(testFilePath.toString());
    }

    @Test
    void testDuckInitialization() {
        assertNotNull(duck, "Duck should be initialized properly.");
    }

    @Test
    void testGetResponse_validCommand() {
        String response = duck.getResponse("todo Read a book");
        assertTrue(response.contains("added"), "Response should confirm task addition.");
    }

    @Test
    void testGetResponse_invalidCommand() {
        String response = duck.getResponse("invalid command");
        assertTrue(response.contains("Unknown command"), "Response should indicate an invalid command.");
    }
}

