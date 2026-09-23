import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.Map;

class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public synchronized void transfer(int amount, String from, String to) {
        balance -= amount;
        System.out.println(from + " -> " + to + " : -" + amount + ", остаток=" + balance);
    }

    static void example() throws InterruptedException {
        BankAccount account = new BankAccount(1000);
        Thread t1 = new Thread(() -> account.transfer(100, "Алиса", "Боб"));
        Thread t2 = new Thread(() -> account.transfer(200, "Кэрол", "Боб"));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

class ParkingLot {
    private final Semaphore spots = new Semaphore(2);

    public void park(String car) {
        try {
            spots.acquire();
            System.out.println(car + " припарковался");
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            spots.release();
            System.out.println(car + " уехал");
        }
    }

    static void example() throws InterruptedException {
        ParkingLot lot = new ParkingLot();
        String[] cars = {"Машина1", "Машина2", "Машина3", "Машина4"};
        Thread[] threads = new Thread[cars.length];
        for (int i = 0; i < cars.length; i++) {
            String car = cars[i];
            threads[i] = new Thread(() -> lot.park(car));
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
    }
}

class SharedCache {
    private final Map<String, String> data = new HashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, String value) {
        lock.writeLock().lock();
        try {
            data.put(key, value);
            System.out.println("запись " + key + "=" + value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String get(String key) {
        lock.readLock().lock();
        try {
            String value = data.get(key);
            System.out.println("чтение " + key + "=" + value);
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

    static void example() throws InterruptedException {
        SharedCache cache = new SharedCache();
        cache.put("город", "Москва");
        Thread reader1 = new Thread(() -> cache.get("город"));
        Thread reader2 = new Thread(() -> cache.get("город"));
        Thread writer = new Thread(() -> cache.put("город", "Стокгольм"));
        reader1.start();
        reader2.start();
        writer.start();
        reader1.join();
        reader2.join();
        writer.join();
    }
}

class Checkpoint {
    static void checkpoint() throws InterruptedException {
        int runnersCount = 3;
        CyclicBarrier checkpoint = new CyclicBarrier(runnersCount,
                () -> System.out.println("Все участники достигли контрольной точки"));
        Thread[] runners = new Thread[runnersCount];
        for (int i = 0; i < runnersCount; i++) {
            int id = i + 1;
            runners[i] = new Thread(() -> {
                try {
                    System.out.println("Участник " + id + " движется к контрольной точке");
                    Thread.sleep(200L * id);
                    checkpoint.await();
                    System.out.println("Участник " + id + " продолжает после контрольной точки");
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        for (Thread r : runners) r.start();
        for (Thread r : runners) r.join();
    }
}

class OrderProcessor {
    static void process() {
        CompletableFuture<Integer> orderTotal = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return 3500;
        });

        CompletableFuture<Integer> withDiscount = orderTotal.thenApply(total -> total - 300);

        withDiscount.thenAccept(result ->
                System.out.println("Итоговая сумма заказа: " + result));

        System.out.println("Заказ отправлен на обработку");

        try {
            withDiscount.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

public class Task7 {

    public static void main(String[] args) throws InterruptedException {
        BankAccount.example();
        ParkingLot.example();
        SharedCache.example();
        Checkpoint.checkpoint();
        OrderProcessor.process();
    }
	}
