public class Main {

    public static void main(String[] args){
        run();
    }
	private static void run(){
		VendingMachine vend = new VendingMachine();
        while(vend.status != VendingMachine.State.OFF){
            if (vend.status == VendingMachine.State.START){
                vend.start();
            }
            if (vend.status == VendingMachine.State.INSERT){
                vend.insertMoney();
            }
            if (vend.status == VendingMachine.State.SELECT){
                vend.select();
            }
            if (vend.status == VendingMachine.State.DISPENSE){
                vend.dispenseSelection();
            }
            if (vend.status == VendingMachine.State.CHANGE){
                vend.dispenseChange();
            }
            if (vend.status == VendingMachine.State.STOCK){
                vend.restock();
            }
        }
	}
}
