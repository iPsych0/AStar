import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int ITERATIONS = 10000;

    public static void main(String[] args) {
        List<Long> executionTimes = new ArrayList<>();

        for (int i = 0; i < ITERATIONS; i++) {
            long startTime = System.nanoTime();

            // Instantiate the grid and get a list of nodes to the goal
            AStarGrid aStarGrid = new AStarGrid(0, 0, 20, 20);
            List<Node> nodes = aStarGrid.findPath(0, 0, 19, 19);

            // Save the execution times
            executionTimes.add((System.nanoTime() - startTime));
        }

        printExecutionTimes(executionTimes);

    }

    private static void printExecutionTimes(List<Long> executionTimes) {
        // Sort the list
        executionTimes.sort(Long::compareTo);

        // Calculate mean execution times
        boolean evenNumber = executionTimes.size() % 2 == 0;
        long medianTime;
        if (evenNumber) {
            // Average of n / 2 - 1 and n / 2
            medianTime = (executionTimes.get(executionTimes.size() / 2 - 1) +
                    executionTimes.get(executionTimes.size() / 2)) /
                    2L;
        } else {
            medianTime = executionTimes.get(executionTimes.size() / 2);
        }

        // Get the execution times
        long totalTime = executionTimes.stream().mapToLong(Long::longValue).sum();
        long averageTime = totalTime / ITERATIONS;
        long minTime = executionTimes.get(0);
        long maxTime = executionTimes.get(executionTimes.size() - 1);


        System.out.println("--- Executions: " + ITERATIONS + " ---");
        System.out.println("Total execution time: " + totalTime + " ns");
        System.out.println("Average execution time: " + averageTime + " ns");
        System.out.println("Median execution time: " + medianTime + " ns");
        System.out.println("Min. execution time: " + minTime + " ns");
        System.out.println("Max. execution time: " + maxTime + " ns");
    }
}