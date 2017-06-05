package org.gsoertsz.xd.kafka.gateway.api.services;

import org.gsoertsz.xd.kafka.gateway.api.dto.BusinessEvent;
import org.gsoertsz.xd.kafka.gateway.api.exceptions.BusinessEventStorageException;
import org.gsoertsz.common.messaging.dto.BetTransactionEvent;

/**
 * Created by gsoertsz on 22/5/17.
 */
public interface MessageSink {
    void store(BusinessEvent be) throws BusinessEventStorageException;
    void storeBet(BetTransactionEvent bte) throws BusinessEventStorageException;
    void enrichAndSend(BetTransactionEvent e, String version, String targetTopic) throws BusinessEventStorageException;
}
