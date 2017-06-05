package org.gsoertsz.xd.kafka.gateway.api.services;

/**
 * Created by gsoertsz on 31/5/17.
 */
public interface SchemaService {
    public String getSchemaIdForTopicAtVersion(String targetTopic, String version) throws Exception;
}
