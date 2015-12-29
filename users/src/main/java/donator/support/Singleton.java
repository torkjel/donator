package donator.support;

import java.util.Optional;
import java.util.function.Supplier;

public class Singleton<T> {

    private T value;

    public synchronized T get(Supplier<T> supplier) {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }

    public Optional<T> get() {
        return Optional.ofNullable(value);
    }

    public void set(T override) {
        value = override;
    }

}
