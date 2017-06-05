package org.gsoertsz.common.messaging;

import java.io.Serializable;

/**
 * Created by gsoertsz on 23/5/17.
 */
public class ReportableBusinessEvent implements Serializable {
    private String messageBody;

    public ReportableBusinessEvent() {
        super();
    }

    private ReportableBusinessEvent(String messageBody) {
        this.messageBody = messageBody;
    }

    private static ReportableBusinessEvent withBody(String messageBody) {
        return new ReportableBusinessEvent(messageBody);
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
