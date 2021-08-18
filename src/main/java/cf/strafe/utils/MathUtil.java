package cf.strafe.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtil {

    public double roundTo(double number, int decimals) {
        return Math.round(number * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public String getTimer(int number) {
        int seconds = number % 60;
        int minutes = Math.floorDiv(number, 60);
        if (seconds >= 10) {
            return minutes + ":" + seconds;
        } else {
            return minutes + ":0" + seconds;
        }
    }

}
