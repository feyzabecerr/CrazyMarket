public class Customer {

    //datafield tiplerini degistirebilirsiniz
    public long arrivalTime; //musteri gelis zamani-
    //bekleme zamanini hesaplamada kullanabilirsiniz
    public long removalTime;  //daha büyük veri tutabilmesi için long tanımladım
    public Customer nextCustomer;
    static int item=1;
    Customer(int item){
    this.item=item;
    }

    @Override
    public String toString() {
        return "Customer" +
                ", item=";
    }

    public Customer(long arrivalTime){
        this.arrivalTime=arrivalTime;
    }

}
