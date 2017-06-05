package org.gsoertsz.xd.kafka.gateway.api.controllers;

import org.gsoertsz.xd.kafka.gateway.api.dto.BusinessEvent;
import org.gsoertsz.xd.kafka.gateway.api.dto.BusinessEventAcceptedResponse;
import org.gsoertsz.xd.kafka.gateway.api.exceptions.BusinessEventException;
import org.gsoertsz.xd.kafka.gateway.api.services.MessageSink;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(value = "/business-event", description = "Post Business Events For Guaranteed Delivery")
@RestController
public class BusinessEventController {

    @Autowired
    private MessageSink sink;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    BusinessEventAcceptedResponse sendMessage(@RequestBody BusinessEvent ev) throws BusinessEventException {
        try {
            sink.store(ev);
            BusinessEventAcceptedResponse acceptedMessage = new BusinessEventAcceptedResponse();
            acceptedMessage.setAcceptedDate(new Date());
            return acceptedMessage;
        } catch (Exception e) {
            throw new BusinessEventException("Unable to store message", e);
        }
    }
    
}
