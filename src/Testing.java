import java.util.ArrayList;

public class Testing {
    public static void main(String[] args) {
        BarberShop barberShop = new BarberShop(10);
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Thread> customerThreads = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            customers.add(new Customer(i, barberShop));
            customerThreads.add(new Thread(customers.get(i)));
        }

        for (Thread thread : customerThreads) {
            thread.start();
        }


    }

}
