package org.gsoertsz.xd.kafka.gateway.api.dto;

/**
 * Created by gsoertsz on 31/5/17.
 * {
 "subject": "test",
 "version": 1,
 "id": 1,
 "schema": "{"type":"record","name":"test","fields":[{"name":"field1","type":"string"},{"name":"field2","type":"string"}]}"
 }
 */
public class SchemaResponse {
    private String subject;
    private Integer version;
    private String id;
    private String schema;

    public SchemaResponse() {
        super();
    }

    public SchemaResponse(String subject, Integer version, String id, String schema) {
        super();
        this.subject = subject;
        this.version = version;
        this.id = id;
        this.schema = schema;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
