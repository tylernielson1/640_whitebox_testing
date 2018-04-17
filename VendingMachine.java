import java.io.*;
import java.util.*;

public class VendingMachine {
    HashMap<Integer, StockItem> stock;
    double balance = 0;
    double maxCost = 0;
    int selection = 0;

    enum State {
        START, INSERT, SELECT, DISPENSE, CHANGE, OFF, STOCK
    }

    State status = State.OFF;

    public VendingMachine() {
        stock = new HashMap<Integer, StockItem>();
        loadStock();
        status = State.START;
    }

    public void restock() {
        System.out.println("Restocking...");
        ArrayList<String> items = readFile();
        for (int i = 0; i < items.size(); i++) {
            String[] item = items.get(i).split(",");
            stock.put(i, new StockItem(item[0],
                    Double.parseDouble(item[1]),
                    Integer.parseInt(item[2])));
            if (stock.get(i).cost > maxCost){
                maxCost = stock.get(i).cost;
            }
        }
        status = State.START;
    }

    public void loadStock() {
        System.out.println("Loading initial stock...");
        ArrayList<String> items = readFile();
        for (int i = 0; i < items.size(); i++) {
            String[] item = items.get(i).split(",");
            stock.put(i, new StockItem(item[0],
                    Double.parseDouble(item[1]),
                    Integer.parseInt(item[2])));
            if (stock.get(i).cost > maxCost){
                maxCost = stock.get(i).cost;
            }
        }
    }

    public ArrayList<String> readFile() {
        ArrayList<String> result = new ArrayList<>();
        File file = new File(getFileName());

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                result.add(st);
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String getFileName() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the stock's full file path: ");
        return s.nextLine();
    }

    public void insertMoney() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter money (.05, .10, .25, 1): ");
        balance += Double.parseDouble(s.nextLine());
        System.out.println("Total: $" + balance);
        if (balance >= maxCost){
            status = State.SELECT;
        }

    }

    public void select() {
        Scanner s = new Scanner(System.in);
        System.out.println("\nPlease select a drink, or (r)efund or (d)isplay choices: ");
        String input = s.nextLine();
        int choice = 0;
        try {
            choice = Integer.parseInt(input);
            if (stock.get(choice).count > 0) {
                status = State.DISPENSE;
                selection = choice;
                return;
            }
            System.out.println("Invalid selection");
        } catch (Exception e) {
            if (input.charAt(0) == 'r') {
                status = State.CHANGE;
            } else if (input.charAt(0) == 'd') {
                display();
            } else {
                System.out.println("Invalid selection");
            }
        }
        selection = -1;
    }

    private void display() {
        for (int i = 0; i < stock.size(); i++) {
            System.out.println(i + ": " + stock.get(i).name + "("+stock.get(i).count+") @$"+stock.get(i).cost);
        }
        System.out.println();
    }

    public void start() {
        System.out.println("\n\nWelcome!\n");
        display();
        Scanner s = new Scanner(System.in);
        System.out.print("Press (e)xit, (r)estock, or anything else to continue: ");
        String input = s.nextLine();
        if (input.charAt(0) == 'r') {
            status = State.STOCK;
        } else if (input.charAt(0) == 'e') {
            status = State.OFF;
        } else {
            status = State.INSERT;
        }
    }

    public void dispenseSelection() {
        System.out.println("Dispensing your " + stock.get(selection).name);
        balance -= stock.get(selection).cost;
        stock.get(selection).count--;
        if (balance == 0) {
            status = State.CHANGE;
        } else {
            status = State.START;
        }
    }

    public void dispenseChange() {
        System.out.println("Here is your change back!");
        System.out.println("Dispensed: $"+balance);
        balance = 0;
        status = State.START;
    }
}
