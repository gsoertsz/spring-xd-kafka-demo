package org.gsoertsz.xd.kafka.dispatcher.rabbit.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gsoertsz.common.messaging.dto.BetTransactionEvent;
import org.gsoertsz.common.messaging.dto.WrappedBetTransaction;
import org.gsoertsz.xd.kafka.dispatcher.dto.KafkaProxyBetContainer;
import org.gsoertsz.xd.kafka.dispatcher.dto.KafkaProxyBetRequest;
import org.gsoertsz.xd.kafka.dispatcher.dto.KafkaProxyBetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class Consumer {

    @Autowired
    RestTemplate restTemplate;

    @Value("${kafka.rest.proxy.url:http://kafka-rest-proxy:8083/}")
    private String restProxyUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    public void receive(String message) throws Exception {
            LOGGER.info("received message from proxy queue: " + message);
            System.out.println("Received message from proxy queue: " + message);

    //         take the message from the queue and send it to the rest proxy!
            WrappedBetTransaction bet = new ObjectMapper().readValue(message, WrappedBetTransaction.class);
            System.out.println("Parsed message as avro bet!");
            KafkaProxyBetRequest payload = deriveKafkaProxyBetRequestFromEnrichedBet(bet);
            System.out.println("Constructed request for kafka proxy: " + new ObjectMapper().writeValueAsString(payload));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/vnd.kafka.json.v2+json");
            HttpEntity<KafkaProxyBetRequest> entity = new HttpEntity<>(payload, headers);
            System.out.println("Submitted request: " + entity.toString());

            ResponseEntity<KafkaProxyBetResponse> response
                    = restTemplate.exchange(urlForTopic(restProxyUrl, bet.getTopic()), HttpMethod.POST, entity, KafkaProxyBetResponse.class);

            System.out.println("Submitted message...|" + response.toString() + "|");

    }

    private KafkaProxyBetRequest deriveKafkaProxyBetRequestFromEnrichedBet(WrappedBetTransaction bet) throws Exception {
        Integer schemaId = bet.getValue_schema_id();
        ObjectMapper om = new ObjectMapper();

        Collection<KafkaProxyBetContainer> containers
                = bet.getRecords().stream()
                    .map(
                            (BetTransactionEvent e) -> new KafkaProxyBetContainer(e)
                    ).collect(Collectors.toList());

        KafkaProxyBetRequest requestObj = KafkaProxyBetRequest.withValues(schemaId, containers);
        return requestObj;
    }

    private String urlForTopic(String baseUrl, String topic) {
        return baseUrl + "/topics/" + topic;
    }
}
