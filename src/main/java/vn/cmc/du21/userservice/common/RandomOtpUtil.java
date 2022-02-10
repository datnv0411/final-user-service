package vn.cmc.du21.userservice.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RandomOtpUtil {
    private static Random rand;

    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private RandomOtpUtil() {
        super();
    }

    public static String createOtp() {
        int upperbound = 10000;
        //generate random values from 0-9999
        int intRandom = rand.nextInt(upperbound);

        return String.format("%04d", intRandom);
    }
}
