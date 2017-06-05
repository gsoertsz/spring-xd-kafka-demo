package org.gsoertsz.xd.kafka.dispatcher.dto;

/**
 * Created by gsoertsz on 1/6/17.
 */
public class KafkaProxyOffset {
    private int partition;
    private int offset;

    public KafkaProxyOffset() {
        super();
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
