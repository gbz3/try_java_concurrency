import module java.base;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.concurrent.StructuredTaskScope.open;

public class MemoryConsistencyDemo {
    private String configuration = "default";
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public void demonstrateMemoryConsistency() throws InterruptedException {
        configuration = "production-config";
        cache.put("database-url", "prod.example.com");
        cache.put("api-key", "secret-key-123");
        System.out.println("Owner thread prepared: " + configuration);
        try (var scope = open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
            var configTask = scope.fork(() ->  {
                System.out.println("Subtask sees: " + configuration);
                return "Config: " + configuration;
            });

            var cacheTask = scope.fork(() -> {
                String url = cache.get("database-url");
                System.out.println("Subtask found URL: " + url);
                return "Connected to: " + url;
            });

            var results = scope.join()
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();
            System.out.println("Owner received: " + results);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var obj = new MemoryConsistencyDemo();
        obj.demonstrateMemoryConsistency();
    }
}
