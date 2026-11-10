import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int workers = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(workers);
        ArrayList<Future<Long>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < 1000; i++) {
                Callable<Long> task = () -> {
                    long count = 0;
                    for (int number = 1; number <= 1_000_000; number++) {
                        count++;
                    }
                    return count;
                };
                futures.add(executor.submit(task));
            }
        } finally {
            executor.shutdown();
        }
        System.out.println("Project complete");
    }
}
