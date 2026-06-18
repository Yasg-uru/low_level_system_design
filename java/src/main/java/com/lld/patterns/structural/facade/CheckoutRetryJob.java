package com.lld.patterns.structural.facade;

import java.util.List;

public class CheckoutRetryJob {
    private final CheckoutFacade facade;

    public CheckoutRetryJob(CheckoutFacade facade) {
        this.facade = facade;
    }

    public interface QueueDriver {
        List<QueueItem> getAll();
        void remove(String id);
    }

    public static class QueueItem extends CheckoutRequest {
        private final String id;

        public QueueItem(String id, String userId, List<CartItem> items) {
            super(userId, items);
            this.id = id;
        }

        public String getId() { return id; }
    }

    public void run(QueueDriver queueDriver) {
        List<QueueItem> pendingCheckouts = queueDriver.getAll();

        for (QueueItem checkout : pendingCheckouts) {
            CheckoutResult result = facade.checkout(checkout);
            if (result.isSuccess()) {
                queueDriver.remove(checkout.getId());
            }
        }
    }
}
