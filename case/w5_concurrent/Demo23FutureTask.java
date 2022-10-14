import java.util.concurrent.*;

public class Demo23FutureTask {
    public static void main(String[] args) {
        Task23 task = new Task23();

        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(integerFutureTask);

        try {
            System.out.println("task运行结果：" + integerFutureTask.get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Task23 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("子线程正在计算");
        Thread.sleep(3000);

        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}
//学号 G20221148010123