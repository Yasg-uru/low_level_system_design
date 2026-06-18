package com.lld.patterns.structural.facade;

import java.util.List;

public class AdminOrderController {
    private final CheckoutFacade facade;

    public AdminOrderController(CheckoutFacade facade) {
        this.facade = facade;
    }

    @SuppressWarnings("unchecked")
    public void placeOrderForCustomer(Request req, Response res) {
        String customerId = (String) req.getBody().get("customerId");
        List<CartItem> items = (List<CartItem>) req.getBody().get("items");

        CheckoutResult result = facade.checkout(new CheckoutRequest(customerId, items));
        res.json(result);
    }
}
