import java.util.*;
import java.util.concurrent.Semaphore;

public class BarberShop {
    private int waitingRoom;
    private Semaphore barberReady = new Semaphore(1);
    private Semaphore accessToChairs = new Semaphore(1);
    private Semaphore readyCustomers;
    private Queue<Customer> customers;
    private Barber barber;
    private Thread barberThread;
    private int numberOfChairs;
    BarberShop(int numberOfChairs) {
        waitingRoom = 0;
        this.numberOfChairs = numberOfChairs;
        barber = new Barber(this);
        barberThread = new Thread(barber);
        barberThread.setDaemon(true);
        barberThread.start();
        customers = new LinkedList<>();
        barberReady.drainPermits();
        readyCustomers = new Semaphore(numberOfChairs);
        readyCustomers.drainPermits();
    }

    public void comeIntoTheWaitingRoom(Customer customer) throws InterruptedException {
        int number = customer.getNumber();
        System.out.println("Customer no. " + number + " is checking if there are any chairs");
        accessToChairs.acquire();
        if (thereAreEmptyChairs()) {
            System.out.println("Customer no. " + number + " found an empty chair and proceeds to sit in it.");
            customers.add(customer);
            readyCustomers.release();
            accessToChairs.release();
            System.out.println("Customer no. " + number + " signalizes to The Barber that they are ready for shaving.");
            barberReady.acquire();
        } else {
            System.out.println("Customer no. " + number + " didn't find an empty chair.");
            accessToChairs.release();
        }

    }

    public void waitForCustomers() throws InterruptedException {
        readyCustomers.acquire();
        System.out.println("The Barber found a customer waiting and proceeds to shave them.");
        accessToChairs.acquire();
        barberReady.release();
        accessToChairs.release();
        forwardCustomer().shave();
    }

    public Customer forwardCustomer() {
        readyCustomers.release();
        return customers.remove();
    }

    public boolean thereAreEmptyChairs() {
        return customers.size() < numberOfChairs;
    }
}
