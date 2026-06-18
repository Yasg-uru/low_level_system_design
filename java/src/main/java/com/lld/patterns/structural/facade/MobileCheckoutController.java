package com.lld.patterns.structural.facade;

import java.util.List;

public class MobileCheckoutController {
    private final CheckoutFacade facade;

    public MobileCheckoutController(CheckoutFacade facade) {
        this.facade = facade;
    }

    @SuppressWarnings("unchecked")
    public void checkout(Request req, Response res) {
        String userId = req.getUser().getId();
        List<CartItem> cart = (List<CartItem>) req.getBody().get("cart");

        CheckoutResult result = facade.checkout(new CheckoutRequest(userId, cart));
        res.json(result);
    }
}
