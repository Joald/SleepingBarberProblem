import java.util.concurrent.Semaphore;

public class Barber implements Runnable {
    private BarberShop workplace;
    Barber(BarberShop shop) {
        workplace = shop;
    }

    @Override
    public void run() {
        System.out.println("The Barber begins.");
        while (true) {
            System.out.println("The Barber checks if there are customers waiting.");
            try {
                workplace.waitForCustomers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The Barber finished shaving a customer.");

        }
    }
}
