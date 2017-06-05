package org.gsoertsz.common.messaging.dto;

import java.lang.Integer;import java.lang.String;import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gsoertsz on 1/6/17.
 */
public class WrappedBetTransaction {
    private Integer value_schema_id;
    private String topic;
    private Collection<BetTransactionEvent> records;

    public WrappedBetTransaction() {
        super();
    }

    private WrappedBetTransaction(Integer value_schema_id, String topic, Collection<BetTransactionEvent> records) {
        super();
        this.value_schema_id = value_schema_id;
        this.topic = topic;
        this.records = records;
    }

    public static WrappedBetTransaction wrappingSingleBetWith(Integer schemaId, String targetTopic, BetTransactionEvent e) {
        return new WrappedBetTransaction(schemaId, targetTopic, Stream.of(e).collect(Collectors.toList()));
    }

    public Integer getValue_schema_id() {
        return value_schema_id;
    }

    public void setValue_schema_id(Integer value_schema_id) {
        this.value_schema_id = value_schema_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Collection<BetTransactionEvent> getRecords() {
        return records;
    }

    public void setRecords(Collection<BetTransactionEvent> records) {
        this.records = records;
    }
}
