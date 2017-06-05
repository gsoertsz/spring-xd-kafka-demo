package org.gsoertsz.common.messaging.dto;

import java.util.Collection;

/**
 * Created by gsoertsz on 26/5/17.
 */
public class BetTransactionEvent {

    private Collection<Body> body;

    public BetTransactionEvent() {
    }

    public Collection<Body> getBody() {
        return body;
    }

    public void setBody(Collection<Body> body) {
        this.body = body;
    }
}
