package vn.cmc.du21.userservice.common;

import java.util.Random;

public class RandomOtpUtil {
    private RandomOtpUtil() {
        super();
    }
    public static String createOtp() {
        Random rand = new Random(); //instance of random class
        int upperbound = 10000;
        //generate random values from 0-9999
        int int_random = rand.nextInt(upperbound);

        return String.format("%04d", int_random);
    }
}
