package com.lld.patterns.structural.facade;

import java.util.List;

public class InventoryService {
    private final IInventoryRepository repo;

    public InventoryService(IInventoryRepository repo) {
        this.repo = repo;
    }

    public static class ReservationResult {
        private final boolean success;
        private final String reason;

        public ReservationResult(boolean success, String reason) {
            this.success = success;
            this.reason = reason;
        }

        public boolean isSuccess() { return success; }
        public String getReason() { return reason; }
    }

    public ReservationResult reserveItems(List<CartItem> items) {
        for (CartItem item : items) {
            int stock = repo.getStock(item.getProductId());
            if (stock < item.getQuantity()) {
                return new ReservationResult(false, item.getProductId() + " out of stock");
            }
        }
        for (CartItem item : items) {
            repo.decrementStock(item.getProductId(), item.getQuantity());
        }
        return new ReservationResult(true, null);
    }

    public void releaseItems(List<CartItem> items) {
        for (CartItem item : items) {
            repo.incrementStock(item.getProductId(), item.getQuantity());
        }
    }
}
