package org.gsoertsz.xd.kafka.gateway.api.dto;

import java.util.Date;

/**
 * Created by gsoertsz on 22/5/17.
 */
public class BusinessEventAcceptedResponse {

    private Date acceptedDate;

    public BusinessEventAcceptedResponse() {
    }

    public static BusinessEventAcceptedResponse withAcceptedDateTime(Date d) {
        BusinessEventAcceptedResponse response = new BusinessEventAcceptedResponse();
        response.setAcceptedDate(d);
        return response;
    }

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }
}
