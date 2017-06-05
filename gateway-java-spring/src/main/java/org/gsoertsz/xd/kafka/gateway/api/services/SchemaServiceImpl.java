package org.gsoertsz.xd.kafka.gateway.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gsoertsz.xd.kafka.gateway.api.dto.SchemaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by gsoertsz on 31/5/17.
 */
@Component
public class SchemaServiceImpl implements SchemaService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${kafka.schema.registry.url}")
    private String registryUrl;

    @Override
    public String getSchemaIdForTopicAtVersion(String targetTopic, String version) throws Exception {
        String constructedUrl = constructUrl(registryUrl, targetTopic, version);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.schemaregistry.v1+json");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        //headers.setAccept(Stream.of(MediaType.valueOf("application/vnd.schemaregistry.v1+json")).collect(Collectors.toList()));

        ResponseEntity<String> response = restTemplate.getForEntity(constructedUrl, String.class);
        String schemaResponse = response.getBody();
        SchemaResponse sr = new ObjectMapper().readValue(schemaResponse, SchemaResponse.class);
        System.out.println("Received schema version: " + sr.getId());

        return sr.getId();
    }

    private String constructUrl(String registryUrl, String targetTopic, String version) {
        return registryUrl + "/subjects/" + targetTopic + "/versions/" + version;
    }
}
