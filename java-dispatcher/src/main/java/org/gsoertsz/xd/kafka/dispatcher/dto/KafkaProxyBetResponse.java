package org.gsoertsz.xd.kafka.dispatcher.dto;

import java.util.Collection;

/**
 * Created by gsoertsz on 1/6/17.
 * {
 "key_schema_id": null,
 "value_schema_id": 32,
 "offsets": [
     {
     "partition": 2,
     "offset": 103
     },
     {
     "partition": 1,
     "offset": 104
     }
 ]
 }
 */
public class KafkaProxyBetResponse {
    private Integer key_schema_id;
    private Integer value_schema_id;
    private Collection<KafkaProxyOffset> offsets;

    public KafkaProxyBetResponse() {
        super();
    }

    public Integer getKey_schema_id() {
        return key_schema_id;
    }

    public void setKey_schema_id(Integer key_schema_id) {
        this.key_schema_id = key_schema_id;
    }

    public Integer getValue_schema_id() {
        return value_schema_id;
    }

    public void setValue_schema_id(Integer value_schema_id) {
        this.value_schema_id = value_schema_id;
    }

    public Collection<KafkaProxyOffset> getOffsets() {
        return offsets;
    }

    public void setOffsets(Collection<KafkaProxyOffset> offsets) {
        this.offsets = offsets;
    }
}
