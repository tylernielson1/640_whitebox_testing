import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {
    private VendingMachine vendingMachine;

    @BeforeAll
    static void setUpClass() {
        System.out.println("Setup Class.");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("Tear Down Class.");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Setup Test.");
        ByteArrayInputStream in = new ByteArrayInputStream(("sampleStock\n").getBytes());
        System.setIn(in);
        vendingMachine = new VendingMachine();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tear down test.");
    }

    @Test
    void test_insert_single_money() {
        double expectedAmount = 0.05;
        ByteArrayInputStream in = new ByteArrayInputStream(("0.05\n").getBytes());
        System.setIn(in);
        vendingMachine.insertMoney();
        assertEquals(expectedAmount, vendingMachine.balance);
    }
}
