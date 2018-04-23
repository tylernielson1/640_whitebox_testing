import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {
    private VendingMachine vendingMachine;

    @BeforeAll
    static void setUpClass() {
        System.out.println("\nSetup Class.");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("\nTear Down Class.");
    }

    @BeforeEach
    void setUp() {
        System.out.println("\nSetup Test.");
        ByteArrayInputStream in = new ByteArrayInputStream(("sampleStock\n").getBytes());
        System.setIn(in);
        vendingMachine = new VendingMachine();
    }

    @AfterEach
    void tearDown() {
        System.out.println("\nTear down test.");
    }

    @Test
    void test_insert_money_and_dispense() {
        double expectedBalance = 2;
        VendingMachine.State expectedState = VendingMachine.State.DISPENSE;
        ByteArrayInputStream in = new ByteArrayInputStream(("1\n").getBytes());
        System.setIn(in);
        vendingMachine.insertMoney();
        ByteArrayInputStream in2 = new ByteArrayInputStream(("1\n").getBytes());
        System.setIn(in2);
        vendingMachine.insertMoney();
        assertEquals(expectedBalance, vendingMachine.balance);

        ByteArrayInputStream in3 = new ByteArrayInputStream(("2\n").getBytes());
        System.setIn(in3);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);
    }
}