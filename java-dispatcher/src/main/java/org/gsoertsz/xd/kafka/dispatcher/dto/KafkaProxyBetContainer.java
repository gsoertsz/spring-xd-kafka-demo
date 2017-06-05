package org.gsoertsz.xd.kafka.dispatcher.dto;


import org.gsoertsz.common.messaging.dto.BetTransactionEvent;

/**
 * Created by gsoertsz on 2/6/17.
 */
public class KafkaProxyBetContainer {
    private BetTransactionEvent value;

    public KafkaProxyBetContainer(BetTransactionEvent value) {
        this.value = value;
    }

    public KafkaProxyBetContainer() {
        super();
    }

    public BetTransactionEvent getValue() {
        return value;
    }

    public void setValue(BetTransactionEvent value) {
        this.value = value;
    }
}
