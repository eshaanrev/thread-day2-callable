import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        // Reuse a limited set of worker threads for 1,000 counting tasks.
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

            // Submit all tasks first, then wait for and combine their results.
            long total = 0;
            for (Future<Long> future : futures) {
                total += future.get();
            }
            double seconds = (System.nanoTime() - startTime) / 1_000_000_000.0;
            System.out.println("Count: " + total);
            System.out.println("Time: " + seconds + " seconds");
        } finally {
            executor.shutdown();
        }
        System.out.println("Project complete: counting and timer merged");
    }
}
