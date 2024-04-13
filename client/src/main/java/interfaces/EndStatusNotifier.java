package interfaces;

@FunctionalInterface
public interface EndStatusNotifier<T> {
    void setEndStatusListener(EndStatusListener<T> listener);
}
