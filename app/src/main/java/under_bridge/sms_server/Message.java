package under_bridge.sms_server;

import java.util.Date;

/**
 * Created by Илья Кокорин on 28.09.2017.
 */

public class Message {
    private String text;

    public Message(String text) {
        this.text = text;
    }

    public String getMessage() {
        return this.text;
    }

    public void setMessage(String text) {
        this.text = text;
    }
}
