package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdOut;
import java.awt.event.KeyEvent;

/**
 * The {@code Utility} class provides utility methods which
 * can be useful anywhere in the game.
 */
public final class Utility {
    private Utility() {}

    /**
     * Checks whether the game is run in debug mode
     * @return  {@code true} if the game was started in debug mode, else {@code
     *          false}
     */
    public static boolean isInDebugMode() {
        return Config.getGlobalConfig().getInt("debugMode") != 0;
    }

    /**
     * Prints the {@code message} only if debug mode is enabled ({@link
     * #isInDebugMode() isInDebugMode} returns {@code true})
     * @param message   the message to print to standard out
     */
    public static void debugPrintLine(String message) {
        if (Utility.isInDebugMode()) {
            StdOut.println(message);
        }
    }

    /**
     * "Normalizes" the double {@code value} in a range between {@code min-max}
     * to a value between {@code 0-1}. {@code value} is linearly scaled to be
     * the same distance from 0 as it is to {@code min} and the same distance
     * from 1 as it is from {@code max}.
     * @param value the value to normalize
     * @param min   the minimum value in the range of values
     * @param max   the maximum value in the range of values
     * @return      the normalized value
     */
    public static double normalize(double value, double min, double max)
        throws ArithmeticException {
        if (max < min) {
            throw new ArithmeticException("min can't be greater than max");
        }
        if (value > max || value < min) {
            throw new ArithmeticException(
                "value must be in range from min to max");
        }
        return (value - min) / (max - min);
    }

    /**
     * "Normalizes" the int {@code value} in a range between {@code min-max} to
     * a value between {@code 0-1}. {@code value} is linearly scaled to be the
     * same distance from 0 as it is to {@code min} and the same distance from 1
     * as it is from {@code max}.
     * @param value the value to normalize
     * @param min   the minimum value in the range of values
     * @param max   the maximum value in the range of values
     * @return      the normalized value
     */
    public static double normalize(int value, int min, int max)
        throws ArithmeticException {
        if (max < min) {
            throw new ArithmeticException("min can't be greater than max");
        }
        if (value > max || value < min) {
            throw new ArithmeticException(
                "value must be in range from min to max");
        }
        return (double)(value - min) / (double)(max - min);
    }

    /**
     * "Normalizes" the long {@code value} in a range between {@code min-max} to
     * a value between {@code 0-1}. {@code value} is linearly scaled to be the
     * same distance from 0 as it is to {@code min} and the same distance from 1
     * as it is from {@code max}.
     * @param value the value to normalize
     * @param min   the minimum value in the range
     * @param max   the maximum value in the range
     * @return      the normalized value
     */
    public static double normalize(long value, long min, long max)
        throws ArithmeticException {
        if (max < min) {
            throw new ArithmeticException("min can't be greater than max");
        }
        if (value > max || value < min) {
            throw new ArithmeticException(
                "value must be in range from min to max");
        }
        return (double)(value - min) / (double)(max - min);
    }

    /**
     * Clamps the long {@code value} to the bounds {@code lower} and {@code
     * lower}. If {@code value} is greater than {@code upper} then {@code upper}
     * is returned else if {@code value} is smaller than {@code lower} then
     * lower is returned else {@code value} is returned as is.
     * @param value the value to clamp
     * @param lower the minimum value of the bounds
     * @param upper the maximum value of the bounds
     * @return      the clamped value
     */
    public static double clamp(double value, double lower, double upper)
        throws ArithmeticException {
        if (lower > upper) {
            throw new ArithmeticException("lower can't be greater than upper");
        }
        return Math.max(lower, Math.min(value, upper));
    }

    /**
     * Clamps the int {@code value} to the bounds {@code lower} and {@code
     * lower}. If {@code value} is greater than {@code upper} then {@code upper}
     * is returned else if {@code value} is smaller than {@code lower} then
     * lower is returned else {@code value} is returned as is.
     * @param value the value to clamp
     * @param lower the minimum value of the bounds
     * @param upper the maximum value of the bounds
     * @return      the clamped value
     */
    public static int clamp(int value, int lower, int upper) {
        if (lower > upper) {
            throw new ArithmeticException("lower can't be greater than upper");
        }
        return Math.max(lower, Math.min(value, upper));
    }

    /**
     * Clamps the long {@code value} to the bounds {@code lower} and {@code
     * lower}. If {@code value} is greater than {@code upper} then {@code upper}
     * is returned else if {@code value} is smaller than {@code lower} then
     * lower is returned else {@code value} is returned unchanged.
     * @param value the value to clamp
     * @param lower the minimum value of the bounds
     * @param upper the maximum value of the bounds
     * @return      the clamped value
     */
    public static long clamp(long value, long lower, long upper) {
        if (lower > upper) {
            throw new ArithmeticException("lower can't be greater than upper");
        }
        return Math.max(lower, Math.min(value, upper));
    }

    /**
     * Attempts to get a {@code KeyCode} from a given String
     * @param key   the key to parse. Special characters should be enclosed in
     *              square brackets ([])
     * @return      the corrosponsing {@code KeyCode} if {@code key} was
     *              successfully parsed, else 0
     */
    public static int getKeyCode(String key) {
        if (key.length() == 1) {
            return KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
        } else if (key.startsWith("[") && key.endsWith("]")) {
            String substring = key.substring(1, key.length() - 1);
            if (substring.equals("space")) {
                return KeyEvent.VK_SPACE;
            }
        }

        return 0;
    }
}
