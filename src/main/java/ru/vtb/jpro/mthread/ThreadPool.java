package ru.vtb.jpro.mthread;


import java.util.LinkedList;


public class ThreadPool {

    //Очередь задач
    private final LinkedList<Runnable> queue = new LinkedList<>();
    //Признак остановки
    private volatile boolean isShutDown = false;
    //Список потоков
    private final LinkedList<Thread> threads = new LinkedList<>();


    public ThreadPool(int threadsCount) {
        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(this::runNextTaskFromQueue);
            thread.start();
            threads.add(thread);
        }
    }

    private void runNextTaskFromQueue() {
        Runnable curTask;
        while (true) {
            synchronized(queue) {
                if (queue.isEmpty()) {
                    //Если остановка пула, пробуждаем потоки, чтобы они завершили свою работу
                    if (isShutDown && queue.isEmpty()){
                        queue.notifyAll();
                        break;
                    }
                    //Переводим поток в ожидание
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        isShutDown = true;
                        Thread.currentThread().interrupt();
                    }
                }
                curTask = queue.pollFirst();
            }
            //Выполняем задачу вне блока синхронизации
            curTask.run();
        }
    }

    public void execute(Runnable task){
        if (isShutDown){
            throw new  IllegalStateException("Пул потоков в процессе остановки");
        }
        //Добавляем задачу в очередь и оповещяем спящий поток, что пришла задача
        synchronized(queue) {
            queue.addLast(task);
            queue.notify();
        }
    }

    public void shutdown(){
        isShutDown = true;
    }

    public void awaitTermination() throws InterruptedException {
        //Джойним все работающие потоки
        for (Thread thread : threads){
            if (thread.isAlive())
                thread.join();
        }
    }

    public int countTasksInQueue(){
        synchronized(queue){
            return queue.size();
        }
    }
}

