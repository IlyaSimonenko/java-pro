package thread.pool;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadPool {
    private final LinkedList<Runnable> taskQueue;
    private List<WorkerThread> workerThreads;

    private boolean isShutdown;

    public ThreadPool(int capacity) {
        this.taskQueue = new LinkedList<>();
        this.isShutdown = false;
        formingWorkerThreads(capacity);
    }

    public boolean isThreadStopped() {
        for (WorkerThread workerThread : workerThreads) {
            if (workerThread.isAlive()) {
                return false;
            }
        }
        return true;
    }


    public synchronized void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool has already been turned off and further work with it is not possible");
        }
        taskQueue.addLast(task);
        notify();
    }

    public synchronized void shutdown() {
        isShutdown = true;
        workerThreads.forEach(Thread::interrupt);
    }

    private void formingWorkerThreads(int capacity){
        this.workerThreads = IntStream.range(0, capacity)
                .mapToObj(i -> {
                    WorkerThread workerThread = new WorkerThread();
                    workerThread.start();
                    return workerThread;
                })
                .collect(Collectors.toList());
    }

    private class WorkerThread extends Thread {
        public void run() {
            Runnable task;
            while (true) {
                synchronized (ThreadPool.this) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            ThreadPool.this.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    if (isShutdown && taskQueue.isEmpty()) {
                        return;
                    }
                    task = taskQueue.removeFirst();
                }
                executeTask(task);
            }
        }

        private void executeTask(Runnable task) {
            try {
                task.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
