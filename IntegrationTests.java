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
    void test_insert_money_and_select() {
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

    @Test
    void test_select_and_dispense() {
        VendingMachine.State expectedState = VendingMachine.State.DISPENSE;
        double expectedCount = 6;
        vendingMachine.balance = 0.75;
        ByteArrayInputStream in = new ByteArrayInputStream(("3\n").getBytes());
        System.setIn(in);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);

        vendingMachine.dispenseSelection();
        assertEquals(expectedCount, vendingMachine.stock.get(3).count);
    }

    @Test
    void test_dispense_selection_and_dispense_change() {
        VendingMachine.State expectedState = VendingMachine.State.DISPENSE;
        VendingMachine.State expectedState2 = VendingMachine.State.CHANGE;
        double expectedBalance = 0;
        vendingMachine.balance = 3;
        ByteArrayInputStream in = new ByteArrayInputStream(("3\n").getBytes());
        System.setIn(in);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);

        vendingMachine.dispenseSelection();
        assertEquals(expectedState2, vendingMachine.status);

        vendingMachine.dispenseChange();
        assertEquals(expectedBalance, vendingMachine.balance);
    }

    @Test
    void test_start_and_insert_money() {
        VendingMachine.State expectedState = VendingMachine.State.INSERT;
        double expectedBalance = 1;
        ByteArrayInputStream in = new ByteArrayInputStream(("1\n").getBytes());
        System.setIn(in);
        vendingMachine.start();
        assertEquals(expectedState, vendingMachine.status);

        ByteArrayInputStream in2 = new ByteArrayInputStream(("1\n").getBytes());
        System.setIn(in2);
        vendingMachine.insertMoney();
        assertEquals(expectedBalance, vendingMachine.balance);
    }

    @Test
    void test_start_and_restock() {
        VendingMachine.State expectedState = VendingMachine.State.STOCK;
        double expectedMaxCost = 2;
        ByteArrayInputStream in = new ByteArrayInputStream(("r\n").getBytes());
        System.setIn(in);
        vendingMachine.start();
        assertEquals(expectedState, vendingMachine.status);

        ByteArrayInputStream in2 = new ByteArrayInputStream(("sampleStock\n").getBytes());
        System.setIn(in2);
        vendingMachine.restock();
        assertEquals(expectedMaxCost, vendingMachine.maxCost);
    }

    @Test
    void test_select_and_refund() {
        VendingMachine.State expectedState = VendingMachine.State.CHANGE;
        double expectedBalance = 0;
        vendingMachine.balance = 0.75;
        ByteArrayInputStream in = new ByteArrayInputStream(("r\n").getBytes());
        System.setIn(in);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);

        vendingMachine.dispenseChange();
        assertEquals(expectedBalance, vendingMachine.balance);
    }
}