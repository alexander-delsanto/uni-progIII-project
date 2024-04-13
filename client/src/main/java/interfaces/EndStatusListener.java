package interfaces;

@FunctionalInterface
public interface EndStatusListener<T> {
    void useEndStatus(T result);
}
