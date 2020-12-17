public class Main {

    public static void main(String[] args) {
        CrazyMarket crazyMarket = new CrazyMarket(200);
        crazyMarket.customerEnter.start(); //thread ile çağırdık
        crazyMarket.customerRemove.start();

    }
}


