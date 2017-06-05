package org.gsoertsz.common.messaging;

import java.io.Serializable;

/**
 * Created by gsoertsz on 23/5/17.
 */
public class FailedMessage implements Serializable {
    private String failedMessageBody;

    public FailedMessage() {
        super();
    }

    private FailedMessage(String failedMessageBody) {
        this.failedMessageBody = failedMessageBody;
    }

    public static FailedMessage withMessageBody(String body) {
        return new FailedMessage(body);
    }

    public String getFailedMessageBody() {
        return failedMessageBody;
    }

    public void setFailedMessageBody(String failedMessageBody) {
        this.failedMessageBody = failedMessageBody;
    }
}
