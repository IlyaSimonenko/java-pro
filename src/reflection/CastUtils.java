package reflection;

public final class CastUtils {

    private CastUtils() {
        throw new RuntimeException();
    }

    public static Object castStringInObject(String value) {

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        } else if (value.matches("\\d+")) {
            return Integer.parseInt(value);
        } else {
            return value;
        }

    }
}
