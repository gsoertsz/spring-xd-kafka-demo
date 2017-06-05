package org.gsoertsz.xd.kafka.consumer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;
import org.gsoertsz.common.messaging.bet.avro.Bet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "${kafka.topics.record}")
    public void receive(byte[] message) throws Exception {
        System.out.println("received message=|" + new String(message) + "|");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(message);

        Schema schema = Bet.getClassSchema();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);

        DataFileStream<GenericRecord> dataStreamReader = new DataFileStream<GenericRecord>(inputStream, reader);
        JsonEncoder jsonEncoder = EncoderFactory.get().jsonEncoder(schema, outputStream, true);

        for (GenericRecord record : dataStreamReader) {
            writer.write(record, jsonEncoder);
        }

        jsonEncoder.flush();
        outputStream.flush();
        outputStream.close();

        String jsonMessage = new String(outputStream.toByteArray());
        LOGGER.info("Received Message=|{}|", jsonMessage);
        ObjectMapper om = new ObjectMapper();
        Bet b = om.readValue(jsonMessage.getBytes(), Bet.class);
    }

    @KafkaListener(topics = "${kafka.topics.proxy}")
    public void receiveFromProxy(byte[] jsonBytes) throws Exception {
        System.out.println("received message=|" + new String(jsonBytes) + "|");
        ObjectMapper om = new ObjectMapper();
        Bet b = om.readValue(jsonBytes, Bet.class);
    }


}
