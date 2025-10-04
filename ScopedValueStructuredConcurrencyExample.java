import java.util.concurrent.StructuredTaskScope;

public class ScopedValueStructuredConcurrencyExample {
    private static final ScopedValue<String> USERNAME = ScopedValue.newInstance();

    public static void doSomething() {
        try (var scope = StructuredTaskScope.open()) {
            StructuredTaskScope.Subtask<String> task1 = scope.fork(() -> USERNAME.get() + " from task 1");
            StructuredTaskScope.Subtask<String> task2 = scope.fork(() -> USERNAME.get() + " from task 2");
            scope.join();
            var result1 = task1.get();
            var result2 = task2.get();
            System.out.println(result1);
            System.out.println(result2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ScopedValue.where(USERNAME, "Bazlur").run(ScopedValueStructuredConcurrencyExample::doSomething);
    }
}
