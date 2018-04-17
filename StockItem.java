public class StockItem {
    public double cost;
    public int count = 0;
    public String name = "default";
    public StockItem(String name, double cost, int count){
        this.cost = cost;
        this.count = count;
        this.name = name;
    }
}
