import java.util.concurrent.Semaphore;

public class Customer implements Runnable {
    private BarberShop favoriteBarberShop;
    private int number;
    private boolean hasBeenShaved = false;
    private Semaphore sleepingCheck = new Semaphore(1);

    public Customer(int number, BarberShop shop) {
        favoriteBarberShop = shop;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void shave() {
        System.out.println("Customer no. " + number + " is being shaved.");
        hasBeenShaved = true;
    }

    @Override
    public void run() {
        System.out.println("Customer no. " + number + " has begun their visit to the shop.");
        try {
            favoriteBarberShop.comeIntoTheWaitingRoom(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        System.out.println("Customer no. " +
                number + " is exiting the shop " +
                (hasBeenShaved ? "after" : "without") + " being shaved.");
    }
}
