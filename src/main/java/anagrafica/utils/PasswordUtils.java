package anagrafica.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordUtils {

    final static Logger logger = LogManager.getLogger(PasswordUtils.class);


    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+<>?";
    private static final String ALL = UPPER + LOWER + DIGITS + SYMBOLS;
    private static final SecureRandom RANDOM = new SecureRandom();


    public static String randomPassword() {
        int length = 8; // lunghezza della password
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALL.length());
            password.append(ALL.charAt(index));
        }

        return password.toString();
    }

    public static String  encodePassword(String password) {
        String algorithm = "SHA";
        byte[] plainText = password.getBytes();

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.reset();
            md.update(plainText);
            byte[] encodedPassword = md.digest();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < encodedPassword.length; ++i) {
                if ((encodedPassword[i] & 255) < 16) {
                    sb.append("0");
                }

                sb.append(Long.toString((long)(encodedPassword[i] & 255), 16));
            }

            return sb.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
