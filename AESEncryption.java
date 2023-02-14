package application;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryption {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = 
        new byte[] { 'O', 'b', 'j', 'e', 'c', 't', 'O',
                     'r', 'i', 'e', 'n','t', 'e', 'd', 'P', 'r' };

    public static String encrypt(String valueToEnc) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        return Base64.getEncoder().encodeToString(encValue);
    }

    public static String decrypt(String encryptedValue) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.getDecoder().decode(encryptedValue.getBytes());
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGORITHM);
    }
}
