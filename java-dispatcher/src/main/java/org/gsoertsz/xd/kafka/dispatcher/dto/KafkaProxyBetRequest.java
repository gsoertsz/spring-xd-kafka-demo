package org.gsoertsz.xd.kafka.dispatcher.dto;

import java.util.Collection;

/**
 * Created by gsoertsz on 1/6/17.
 */
public class KafkaProxyBetRequest {
    private Integer value_schema_id;
    private Collection<KafkaProxyBetContainer> records;

    public KafkaProxyBetRequest() {
        super();
    }

    private KafkaProxyBetRequest(Integer value_schema_id, Collection<KafkaProxyBetContainer> records) {
        this.value_schema_id = value_schema_id;
        this.records = records;
    }

    public static KafkaProxyBetRequest withValues(Integer valueSchemaId, Collection<KafkaProxyBetContainer> records) {
        return new KafkaProxyBetRequest(valueSchemaId, records);
    }

    public Integer getValue_schema_id() {
        return value_schema_id;
    }

    public void setValue_schema_id(Integer value_schema_id) {
        this.value_schema_id = value_schema_id;
    }

    public Collection<KafkaProxyBetContainer> getRecords() {
        return records;
    }

    public void setRecords(Collection<KafkaProxyBetContainer> records) {
        this.records = records;
    }
}
