package pl.bmstefanski.tools.util;

public class SafeUtils {

    private SafeUtils() {

    }

    private static void reportUnsafe(Throwable throwable) {
        System.out.println(throwable.toString());
    }

    public static <T> T safeInit(SafeInitializer<T> initializer) {
        try {
            return initializer.initialize();
        } catch (Exception ex) {
            reportUnsafe(ex);
            return null;
        }
    }

    @FunctionalInterface
    public interface SafeInitializer<T> {
        T initialize() throws Exception;
    }
}
