package entities;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class SmsSender {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC2a5e70720060db0983ddf33440e6288d";
    public static final String AUTH_TOKEN = "e77e01ec1e57c8e8dc19a47740ceb2c7";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21652971719"),
                        new com.twilio.type.PhoneNumber("+18507504735"),
                "bienvenue chez thakafa")
                .create();

        System.out.println(message.getSid());
    }
}