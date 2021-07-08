package nl.cryonic.utils;

public class MathUtil {

    public static double roundTo(double number, int decimals) {
        return Math.round(number * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

}
