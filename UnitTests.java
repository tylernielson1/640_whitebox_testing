import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {
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
    void test_insert_single_money() {
        double expectedAmount = 0.05;
        ByteArrayInputStream in = new ByteArrayInputStream(("0.05\n").getBytes());
        System.setIn(in);
        vendingMachine.insertMoney();
        assertEquals(expectedAmount, vendingMachine.balance);
    }

    @Test
    void test_insert_multiple_monies() {
        double expectedAmount = 3.00;
        ByteArrayInputStream in = new ByteArrayInputStream(("1.00\n").getBytes());
        System.setIn(in);
        vendingMachine.insertMoney();
        ByteArrayInputStream in2 = new ByteArrayInputStream(("1.00\n").getBytes());
        System.setIn(in2);
        vendingMachine.insertMoney();
        ByteArrayInputStream in3 = new ByteArrayInputStream(("1.00\n").getBytes());
        System.setIn(in3);
        vendingMachine.insertMoney();
        assertEquals(expectedAmount, vendingMachine.balance);
    }

    @Test
    void test_dispense_change() {
        double expectedDispense = 0;
        vendingMachine.balance = 0.5;
        vendingMachine.dispenseChange();
        assertEquals(expectedDispense, vendingMachine.balance);
    }

    @Test
    void test_exit_status() {
        VendingMachine.State expectedState = VendingMachine.State.OFF;
        ByteArrayInputStream in = new ByteArrayInputStream(("e\n").getBytes());
        System.setIn(in);
        vendingMachine.start();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_insert_status() {
        VendingMachine.State expectedState = VendingMachine.State.INSERT;
        ByteArrayInputStream in = new ByteArrayInputStream(("testing insert status change\n").getBytes());
        System.setIn(in);
        vendingMachine.start();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_restock_status() {
        VendingMachine.State expectedState = VendingMachine.State.STOCK;
        ByteArrayInputStream in = new ByteArrayInputStream(("r\n").getBytes());
        System.setIn(in);
        vendingMachine.start();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_selection_refund() {
        VendingMachine.State expectedState = VendingMachine.State.CHANGE;
        ByteArrayInputStream in = new ByteArrayInputStream(("r\n").getBytes());
        System.setIn(in);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_restock_functionality() {
        VendingMachine.State expectedState = VendingMachine.State.START;
        ByteArrayInputStream in = new ByteArrayInputStream(("sampleStock\n").getBytes());
        System.setIn(in);
        vendingMachine.restock();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_display_functionality_in_select() {
        VendingMachine.State expectedState = VendingMachine.State.START;
        ByteArrayInputStream in = new ByteArrayInputStream(("d\n").getBytes());
        System.setIn(in);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_invalid_selection_in_select_catch() {
        VendingMachine.State expectedState = VendingMachine.State.START;
        ByteArrayInputStream in = new ByteArrayInputStream(("x\n").getBytes());
        System.setIn(in);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_choice_functionality_in_select() {
        VendingMachine.State expectedState = VendingMachine.State.DISPENSE;
        ByteArrayInputStream in = new ByteArrayInputStream(("1\n").getBytes());
        System.setIn(in);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_invalid_selection_in_select_try() {
        VendingMachine.State expectedState = VendingMachine.State.START;
        ByteArrayInputStream in = new ByteArrayInputStream(("8\n").getBytes());
        System.setIn(in);
        vendingMachine.select();
        assertEquals(expectedState, vendingMachine.status);
    }

    @Test
    void test_dispense_selection_exact_change() {
        double expectedChange = 0.0;
        vendingMachine.balance = 0.75;
        vendingMachine.selection = 3;
        vendingMachine.dispenseSelection();
        assertEquals(expectedChange, vendingMachine.balance);
    }

    @Test
    void test_dispense_selection_with_change() {
        double expectedChange = 0.75;
        vendingMachine.balance = 1.5;
        vendingMachine.selection = 3;
        vendingMachine.dispenseSelection();
        assertEquals(expectedChange, vendingMachine.balance);
    }
}
