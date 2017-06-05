package org.gsoertsz.xd.kafka.xd.transform;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.xd.tuple.Tuple;
import org.springframework.xd.tuple.TupleBuilder;

/**
 * Created by gsoertsz on 23/5/17.
 */
@MessageEndpoint
public class BusinessEventTransformer {

    @Transformer(inputChannel = "input", outputChannel = "output")
    public Message<Tuple> convertToTuple(Message<String> be) {
        Tuple t = TupleBuilder.fromString(be.getPayload());
        Message<Tuple> m = MessageBuilder.withPayload(t).build();
        return m;
    }
}
