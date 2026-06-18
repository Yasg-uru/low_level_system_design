package com.lld.patterns.structural.facade;

import java.util.List;
import java.util.Map;

public class CheckoutController {
    private final CheckoutFacade facade;

    public CheckoutController(CheckoutFacade facade) {
        this.facade = facade;
    }

    @SuppressWarnings("unchecked")
    public void handleCheckout(Request req, Response res) {
        String userId = req.getUser().getId();
        List<CartItem> items = (List<CartItem>) req.getBody().get("items");

        CheckoutResult result = facade.checkout(new CheckoutRequest(userId, items));

        if (!result.isSuccess()) {
            res.status(400).json(Map.of("error", result.getError()));
            return;
        }
        res.json(Map.of("order", result.getOrder()));
    }

    public void handleCancel(Request req, Response res) {
        String orderId = req.getParams().get("orderId");
        CheckoutResult result = facade.cancelOrder(orderId);
        if (!result.isSuccess()) {
            res.status(400).json(Map.of("error", result.getError()));
        } else {
            res.json(result);
        }
    }
}
