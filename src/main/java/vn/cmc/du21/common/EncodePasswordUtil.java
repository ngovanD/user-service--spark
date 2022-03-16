package vn.cmc.du21.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodePasswordUtil {
    private EncodePasswordUtil() {
        super();
    }

    public static String getEncodePassword(String password, String Algorithm) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing MD5
        MessageDigest md = MessageDigest.getInstance(Algorithm);

        // digest() method is called to calculate message digest
        //  of an input digest() return array of byte
        byte[] messageDigest = md.digest(password.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        password = no.toString(16);

        return password;
    }
}
