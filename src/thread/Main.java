package thread;

import thread.pool.ThreadPool;

public class Main {

    public static void main(String[] args) {

        ThreadPool pool = new ThreadPool(2);
        pool.execute(() -> System.out.println("Hello from ThreadPool!"));
        pool.execute(() -> System.out.println("World from ThreadPool!"));
        pool.shutdown();
        while (!pool.isThreadStopped()) {
            System.out.println("Все потоки пока не остановлены");
        }
        System.out.println("Все потоки остановлены");
        pool.execute(() -> System.out.println("Hello from ThreadPool!"));

    }
}
