package org.gsoertsz.xd.kafka.gateway.api.controllers;

import org.gsoertsz.xd.kafka.gateway.api.dto.BusinessEventAcceptedResponse;
import org.gsoertsz.xd.kafka.gateway.api.exceptions.BusinessEventException;
import org.gsoertsz.xd.kafka.gateway.api.services.MessageSink;
import io.swagger.annotations.Api;
import org.gsoertsz.common.messaging.dto.BetTransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by gsoertsz on 26/5/17.
 */

@Api("/business-event/bet")
@RestController(value = "/business-event")
public class BetTransactionController {

    @Autowired
    private MessageSink messageSink;

    @RequestMapping(value = "/bet", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    BusinessEventAcceptedResponse acceptBetTransaction(@RequestBody BetTransactionEvent event) throws BusinessEventException {
        try {
            messageSink.storeBet(event);
            return BusinessEventAcceptedResponse.withAcceptedDateTime(new Date());
        } catch (Exception e) {
            throw new BusinessEventException("Unable to store bet!", e);
        }
    }
}
