package cosmic_conquistadors;

/**
 * The {@code Encryption} class provides basic XOR encryption and decryption
 * methods. Not the most secure method of encryption possible, but good enough
 * to prevent the average end-user to access or edit data we don't want them to
 * have access to.
 * <p> As an example, consider the following:
 * <pre>{@code
 * String data = "Encrypt me!";
 * String key = "very secret key";
 *
 * String encrypted = Encryption.encrypt(data, key);
 * // encrypted = "000000330000000B000000110000000B0000005900000003000000110..."
 *
 * String decrypted = Encryption.decrypt(encrypted, key);
 * // decrypted = "Encrypt me!"
 * }</pre>
 *
 * @author Rikus Honey
 * @see <a href="https://github.com/rikushoney/cosmic-conquistadors">Cosmic
 *      Conquistadors</a>
 */
public final class Encryption {
    private Encryption() {}

    /**
     * Encrypts data with a key using an XOR algorithm. Each character in {@code
     * data} is XOR'ed with a character in {@code key}. {@code key} wraps around
     * if it is shorter than {@code data}.
     * @param data  the data to be encrypted
     * @param key   the key used to encrypt the data
     * @return      a string that contains the encrypted data in hexadecimal
     *              format. Each value will be 8 characters in length with
     *              leading zero's where necessary.
     */
    public static String encrypt(String data, String key) {
        String result = "";
        for (int i = 0; i < data.length(); i++) {
            int encrypted = data.charAt(i) ^ key.charAt(i % key.length());
            result += String.format("%08X", encrypted);
        }

        return result;
    }

    /**
     * Decrypts data using a key. The same algorithm is used as that of {@link
     * #encrypt(String, String)}.
     * @param data  the data to be decrypted. Each value should be 8 characters
     *              in length.
     * @param key   the key used to decrypt the data. Should be the same as the
     *              key used to encrypt the data.
     * @return      a string with the decrypted data
     */
    public static String decrypt(String data, String key) {
        if (data.length() % 8 != 0) {
            Utility.debugPrintLine(
                "data's length is not a multiple of 8 - data is most likely corrupt");
        }
        String result = "";
        for (int i = 0; i < data.length() / 8; i++) {
            String chars = data.substring(i * 8, i * 8 + 8);
            int decrypted =
                Integer.parseInt(chars, 16) ^ key.charAt(i % key.length());
            result += (char)decrypted;
        }

        return result;
    }
}
