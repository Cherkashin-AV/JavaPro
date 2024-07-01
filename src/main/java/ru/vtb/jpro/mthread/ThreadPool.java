package ru.vtb.jpro.mthread;


import java.util.LinkedList;


public class ThreadPool {

    //Очередь задач
    private final LinkedList<Runnable> queue = new LinkedList<>();
    //Признак остановки
    private volatile boolean isShutDown = false;
    //Timeout
    private int timeout = 200;

    public void setTimeout(int timeout) {
        if (timeout<100){
            throw new IllegalArgumentException("Значение timeout должно быть больше 100 мс");
        }
        this.timeout = timeout;
    }

    public ThreadPool(int threadsCount) {
        for (int i = 0; i <= threadsCount; i++) {
            new Thread(this::runNextTaskFromQueue).start();
        }
    }

    private void runNextTaskFromQueue() {
        Runnable curTask;
        while (true) {
            synchronized(this) {
                while (queue.isEmpty()) {
                    try {
                        Thread.sleep(timeout);
                        if (isShutDown) {
                            return;
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                synchronized(queue) {
                    curTask = queue.pollFirst();
                }
            }
            curTask.run();
        }
    }

    public void execute(Runnable task){
        if (isShutDown){
            throw new  IllegalStateException("Пул потоков в процессе остановки");
        }
        synchronized(queue) {
            queue.addLast(task);
        }
    }

    public void shutdown(){
        isShutDown = true;
    }

    public void awaitTermination() throws InterruptedException {
        while (!queue.isEmpty()) {
            Thread.sleep(timeout);
        }
    }

    public int countTasksInQueue(){
        synchronized(queue){
            return queue.size();
        }
    }
}

