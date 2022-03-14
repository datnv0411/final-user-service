package vn.cmc.du21.userservice.common;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsSender {
    private SmsSender()
    {
        super();
    }
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    public static void sendOtp(String phoneNumber, String otp)  {
        log.info("Mapped sendOtp method");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        try {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("+84"+phoneNumber),
                            new com.twilio.type.PhoneNumber("+18565531074"),
                            "Your verification code: " + otp)
                    .create();
            System.out.println(message.getSid());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
}
