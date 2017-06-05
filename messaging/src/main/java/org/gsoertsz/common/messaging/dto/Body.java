package org.gsoertsz.common.messaging.dto;

import java.util.Collection;


public class Body {
    private Collection<Transaction> transaction;

    public Body() {
    }

    public Collection<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(Collection<Transaction> transaction) {
        this.transaction = transaction;
    }
}
