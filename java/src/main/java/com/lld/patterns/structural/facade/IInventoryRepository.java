package com.lld.patterns.structural.facade;

public interface IInventoryRepository {
    int getStock(String productId);
    void decrementStock(String productId, int quantity);
    void incrementStock(String productId, int quantity);
}
