package Utilities;

public class Validation {

    public static boolean checkStringWithFormat(String value, String pattern) {
        boolean result = false;
        if (value.matches(pattern)) {
            result = true;
        }
        return result;
    }

    public static boolean checkStringEmpty(String value) {
        boolean reuslt = true;
        if (value.isEmpty()) {
            reuslt = false;
        }
        return reuslt;
    }
}
