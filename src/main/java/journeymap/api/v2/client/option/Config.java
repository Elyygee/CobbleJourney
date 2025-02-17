package journeymap.api.v2.client.option;

/**
 * Minimal stub for Config interface required for compilation.
 */
public interface Config<T> {
    T get();
    void set(T value);
}
