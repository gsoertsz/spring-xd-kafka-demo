package org.gsoertsz.xd.kafka.gateway.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gsoertsz.xd.kafka.gateway.api.dto.BusinessEvent;
import org.gsoertsz.xd.kafka.gateway.api.exceptions.BusinessEventStorageException;

import org.gsoertsz.common.messaging.dto.BetTransactionEvent;
import org.gsoertsz.common.messaging.dto.WrappedBetTransaction;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by gsoertsz on 22/5/17.
 */
@Component
public class MessageSinkImpl implements MessageSink {

    @Autowired
    RabbitTemplate jmsTemplate;

    @Autowired
    SchemaService schemaService;

    @Value("${jms.queue.destination}")
    String destinationQueue;

    @Value("${jms.queue.proxy.destination}")
    String proxyDestinationQueue;

    @Override
    public void store(BusinessEvent be) throws BusinessEventStorageException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String body = mapper.writeValueAsString(be);
            Message m = jmsTemplate.getMessageConverter().toMessage(body, null);
            jmsTemplate.send(destinationQueue, m);
        } catch (Exception e) {
            throw new BusinessEventStorageException("Unable to store message", e);
        }
    }

    @Override
    public void storeBet(BetTransactionEvent bte) throws BusinessEventStorageException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String body = mapper.writeValueAsString(bte);
            Message m = jmsTemplate.getMessageConverter().toMessage(body, null);
            jmsTemplate.send(destinationQueue, m);
        } catch (Exception e) {
            throw new BusinessEventStorageException("Unable to store message", e);
        }
    }

    private void storeEnrichedBetAtDestination(WrappedBetTransaction enrichedBet, String destination)
            throws BusinessEventStorageException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String body = mapper.writeValueAsString(enrichedBet);
            Message m = jmsTemplate.getMessageConverter().toMessage(body, null);
            jmsTemplate.send(destination, m);
        } catch (Exception e) {
            throw new BusinessEventStorageException("Unable to store message", e);
        }
    }

    @Override
    public void enrichAndSend(BetTransactionEvent e, String version, String targetTopic) throws BusinessEventStorageException {

        try {
            String schemaId = schemaService.getSchemaIdForTopicAtVersion(targetTopic, version);
            WrappedBetTransaction enrichedBet = WrappedBetTransaction.wrappingSingleBetWith(Integer.parseInt(schemaId), targetTopic, e);
            storeEnrichedBetAtDestination(enrichedBet, proxyDestinationQueue);
        } catch (Exception ex) {
            throw new BusinessEventStorageException("Unable to send the event", ex);
        }
    }
}
