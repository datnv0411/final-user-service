package vn.cmc.du21.userservice.common;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsSender {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    public static boolean sendOtp(String phoneNumber, String Otp)  {
        Twilio.init("AC8e9cb695e41ebe1ce521956979647ca0", "f368f8376f71ac4ff0967f2b2dbed8f8");
        try {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("+84"+phoneNumber),
                            new com.twilio.type.PhoneNumber("+19033213365"),
                            "Your verification code: " + Otp)
                    .create();

            System.out.println(message.getSid());
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
