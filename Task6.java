import java.util.concurrent.atomic.AtomicInteger;

/*
Код из примера содержит 2 проблемы:
    - результаты операций кэшируются на ядрах процессора. Решение - сделать поле volatile;
    - операция инкремент не атомарна. Решение - использовать AtomicInteger, который работает через CAS.
      Дополнительно volatile указывать не надо, так как поле, хранящее значение в классе AtomicInteger,
      уже помечено volatile.
 */
class RaceConditionExample {
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        int numberOfThreads = 10;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    counter.incrementAndGet();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Final counter value: " + counter);
    }
}

/*
Поток 1 захватил lock1 --- Поток 1 спит 50ms --- Поток 1 пытается захватить lock2, lock2 захвачен, встаёт в очередь \
                                                                                                                     -> deadlock
Поток 2 захватил lock2 --- Поток 2 спит 50ms --- Поток 2 пытается захватить lock1, lock1 захвачен, встаёт в очередь /

Решение: одинаковый порядок захвата локов для обоих потоков.
 */
class DeadlockExample {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1 acquired lock1");

                try { Thread.sleep(50); }
                catch (InterruptedException e) { e.printStackTrace(); }

                synchronized (lock2) {
                    System.out.println("Thread 1 acquired lock2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 2 acquired lock1");

                try { Thread.sleep(50); }
                catch (InterruptedException e) { e.printStackTrace(); }

                synchronized (lock2) {
                    System.out.println("Thread 2 acquired lock2");
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished");
    }
}

public class Task6 {
}
