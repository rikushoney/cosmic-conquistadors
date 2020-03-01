package cosmic_conquistadors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncryptionTest {
    @Test
    public void encryptsString() {
        String data = "Raw string data to be encrypted";
        String key = "Super secure key";
        String encryptedData = Encryption.encrypt(data, key);
        String decryptedData = Encryption.decrypt(encryptedData, key);
        assertEquals(data, decryptedData);
    }
}
