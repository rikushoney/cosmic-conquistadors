/*
    Written by Rikus Honey for cosmic-conquistadors
    https://github.com/rikushoney/cosmic-conquistadors
*/

public class Encryption {
    public static String encrypt(String value, String key) {
        String result = "";
        for (int i = 0; i < value.length(); i++) {
            int encrypted = value.charAt(i) ^ key.charAt(i % key.length());
            result += String.format("%08X", encrypted);
        }

        return result;
    }

    public static String decrypt(String value, String key) {
        String result = "";
        for (int i = 0; i < value.length() / 8; i++) {
            String chars = value.substring(i * 8, i * 8 + 8);
            int decrypted = Integer.parseInt(chars, 16) ^ key.charAt(i % key.length());
            result += (char)decrypted;
        }

        return result;
    }
}
