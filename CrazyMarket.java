import java.util.Iterator;

public class CrazyMarket<T> implements MyQueue<Customer> {
    public String tekerleme;
    Customer head; //listenin başını tutacak
    Customer currentHead;
    int numberOfCustomer; //müşteri sayısı
    public static long currentTime;
    public static long processingTime; //bu değerler değişceği için static tanımladık
    CustomerRemove customerRemove; //süreye göre müşteri siler
    CustomerEnter customerEnter;  //süreyle müşteri ekler

    /**
     * numberOfCustumer ile verilen sayida musteri hizmet gorene kadar calismaya devam eder
     */

    public CrazyMarket(int numberOfCustomer) {
        this(numberOfCustomer, "O piti piti karamela sepeti "
                + "\nTerazi lastik jimnastik "
                + "\nBiz size geldik bitlendik Hamama gittik temizlendik.");
        //alttaki constructor çağırıldı ve tekerlemeyi default olarak yazdık
    }

    public CrazyMarket(int numberOfCustomer, String tekerleme) {
        this.currentTime = 0l;
        this.processingTime = 0l;
        this.head = new Customer(0l);
        this.currentHead = head;
        this.tekerleme = tekerleme;
        this.numberOfCustomer = numberOfCustomer;
        this.customerRemove = new CustomerRemove(); //classların nesnelerini oluşturduk
        this.customerEnter = new CustomerEnter();

    }

    /**
     * kuyrugun basindaki musteriyi yada tekerleme ile buldugu musteriyi return eder
     */
    public Customer chooseCustomer() {
        if (currentHead.arrivalTime - processingTime < 10) {
            return head;
        } else
            return dequeuWithCounting(tekerleme);
    }

    @Override
    public int size() {
        int size = 0;
        while (currentHead.nextCustomer != null) size++;
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size() > 0)
            return true;
        return false;
    }

    @Override
    public boolean enqueue(Customer item) { //kuyruğa eleman ekliycek
        Customer currentCustomer = currentHead;
        while (currentCustomer.nextCustomer != null) {
            currentCustomer = currentCustomer.nextCustomer;
        }
        currentCustomer.nextCustomer = item;
        return true;
    }

    @Override
    public Customer dequeuNext() {
        currentHead = currentHead.nextCustomer;
        currentHead.removalTime = processingTime;
        print(currentHead);
        return null;
    }

    @Override
    public Customer dequeuWithCounting(String tekerleme) {
        int numberOfVowels = 0;
        for (char c : tekerleme.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                numberOfVowels++;
            }
        }
        Customer currentCustomer = currentHead;
        for (int count = 0; count < numberOfVowels; count++) {
            if (currentCustomer.nextCustomer == null) {
                currentCustomer = currentHead;
            } else {
                currentCustomer = currentCustomer.nextCustomer;
            }
        }

        Customer previousCustomer = currentHead;
        while (previousCustomer.nextCustomer != currentCustomer || previousCustomer.nextCustomer == null) {
            previousCustomer = previousCustomer.nextCustomer;
        }
        previousCustomer.nextCustomer = currentCustomer.nextCustomer;
        currentCustomer.nextCustomer = currentHead.nextCustomer;
        currentHead.nextCustomer = currentCustomer;
        currentCustomer.removalTime = processingTime;
        print(currentCustomer);
        return null;

    }

    @Override
    public void print(Customer customer) { //en son süreleri yazdırdık.
        System.out.println(customer.item+". number customer waited for " + (long) (customer.removalTime - customer.arrivalTime) + " " + "arrived at " + customer.arrivalTime + " removed at " + customer.removalTime);
        customer.item++;
    }

    @Override
    public Iterator<Customer> iterator() {
        Iterator iterator = new Iterator<T>() {
            private T next;

            @Override
            public boolean hasNext() { //bir sonraki eleman null değilse true dönmeli
                return next() != null;
            }

            @Override
            public T next() { //bir sonraki elemanı döndürmeli
                T temp = next;
                next = null;
                return temp;
            }
        };
        return iterator;
    }

    class CustomerRemove extends Thread {
        @Override
        public void run() {
            while (currentHead.nextCustomer != null) {
                long time = (long) ((Math.random() * 2) + 1);
                CrazyMarket.processingTime += time;
                if (currentHead.arrivalTime - processingTime < 10) {
                    dequeuNext();
                } else {
                    dequeuWithCounting(tekerleme);
                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CustomerEnter extends Thread {
        @Override
        public void run() {
            for (int counter = 0; counter < numberOfCustomer; counter++) {
                long time = (long) (Math.random() * 2);
                CrazyMarket.currentTime += time;
                enqueue(new Customer(CrazyMarket.currentTime));
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}




