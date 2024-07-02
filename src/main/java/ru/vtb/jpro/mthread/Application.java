package ru.vtb.jpro.mthread;


public class Application {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(4);
        Thread.sleep(1000);
        for (int i = 0; i < 40; i++) {
            Integer n = i;
            pool.execute(()->{
                System.out.println("Задача "+n+" запущена в потоке "+Thread.currentThread().getName());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        boolean exceptionThrowsed = false;
        pool.shutdown();
        try {
            pool.execute(()-> System.out.println("Задача после выполнения shutdown"));
        } catch (IllegalStateException e){
            System.out.println("Выброшено исключение IllegalStateException при попытке добавить задачу после выполнения shutdown");
            exceptionThrowsed = true;
        }

        if (!exceptionThrowsed){
            throw new RuntimeException("!!!Не выброшено исключение");
        }
        pool.awaitTermination();

        System.out.println(pool.countTasksInQueue()+" задач в очереди после остановки");
    }
}
