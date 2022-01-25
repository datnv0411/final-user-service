package vn.cmc.du21.userservice.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodePasswordUtil {
    private EncodePasswordUtil() {
        super();
    }

    public static String getEncodePassword(String password, String algorithm) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing Algorithm
        MessageDigest md = MessageDigest.getInstance(algorithm);

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
