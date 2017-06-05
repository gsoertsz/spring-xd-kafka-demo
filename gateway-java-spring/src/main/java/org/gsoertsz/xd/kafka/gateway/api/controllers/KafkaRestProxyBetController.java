package org.gsoertsz.xd.kafka.gateway.api.controllers;

import org.gsoertsz.xd.kafka.gateway.api.dto.BusinessEventAcceptedResponse;
import org.gsoertsz.xd.kafka.gateway.api.exceptions.BusinessEventException;
import org.gsoertsz.xd.kafka.gateway.api.services.MessageSink;
import io.swagger.annotations.Api;
import org.gsoertsz.common.messaging.dto.BetTransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by gsoertsz on 31/5/17.
 */
@Api(value = "/alternate/bet", description = "Post Business Events For Guaranteed Delivery")
@RestController
public class KafkaRestProxyBetController {

    @Autowired
    private MessageSink messageSink;

    @Value("${kafka.rest.proxy.topic}")
    private String targetTopic;

    @RequestMapping(value = "/alternate/bet/{version}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody BusinessEventAcceptedResponse process(
            @PathVariable("version") String version,
            @RequestBody BetTransactionEvent e) throws BusinessEventException {

        try {
            messageSink.enrichAndSend(e, version, targetTopic);
            BusinessEventAcceptedResponse acceptedMessage = new BusinessEventAcceptedResponse();
            acceptedMessage.setAcceptedDate(new Date());
            return acceptedMessage;
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new BusinessEventException("Unable to process bet message", ex);
        }
    }

}
