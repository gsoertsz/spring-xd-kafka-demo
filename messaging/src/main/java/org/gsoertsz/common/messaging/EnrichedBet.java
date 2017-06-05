package org.gsoertsz.common.messaging;


import org.gsoertsz.common.messaging.bet.avro.Bet;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gsoertsz on 31/5/17.
 */
public class EnrichedBet {
    private String value_schema_id;
    private String topic;
    private Collection<Bet> records;

    public EnrichedBet() {
        super();
    }

    private EnrichedBet(String value_schema_id, String topic, Collection<Bet> records) {
        super();
        this.value_schema_id = value_schema_id;
        this.topic = topic;
        this.records = records;
    }

    public static EnrichedBet wrappingSingleBetWith(String schema_id, String topic, Bet theBet) {
        return new EnrichedBet(schema_id, topic, Stream.of(theBet).collect(Collectors.toList()));
    }

    public String getValue_schema_id() {
        return value_schema_id;
    }

    public void setValue_schema_id(String value_schema_id) {
        this.value_schema_id = value_schema_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Collection<Bet> getRecords() {
        return records;
    }

    public void setRecords(Collection<Bet> records) {
        this.records = records;
    }
}
