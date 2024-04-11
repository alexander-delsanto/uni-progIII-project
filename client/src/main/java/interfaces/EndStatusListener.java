package interfaces;

import java.io.IOException;

@FunctionalInterface
public interface EndStatusListener<T> {
    void useEndStatus(T result);
}
