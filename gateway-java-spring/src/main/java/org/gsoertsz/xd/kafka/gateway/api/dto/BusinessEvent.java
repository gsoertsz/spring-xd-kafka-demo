package org.gsoertsz.xd.kafka.gateway.api.dto;

/**
 * Created by gsoertsz on 19/5/17.
 */
public class BusinessEvent {
    private String title;
    private String body;

    public BusinessEvent() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
