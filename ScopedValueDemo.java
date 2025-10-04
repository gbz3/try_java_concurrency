public class ScopedValueDemo {
    public static void main(String[] args) {
        ScopedValue<String> NAME = ScopedValue.newInstance();
        Runnable task = () -> {
            if (NAME.isBound()) {
                System.out.println("Name is bound: " + NAME.get());
            } else {
                System.out.println("Name is not bound");
            }
        };
        ScopedValue.where(NAME, "Bazlur").run(task);
        task.run();
    }
}
