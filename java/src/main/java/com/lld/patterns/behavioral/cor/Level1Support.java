package com.lld.patterns.behavioral.cor;

/**
 * Concrete handler representing Support Tier 1.
 */
public class Level1Support extends BaseHandler {
    
    @Override
    public String handle(String request) {
        if ("password reset".equalsIgnoreCase(request)) {
            return "[Level 1 Support]: Successfully processed \"password reset\" request.";
        }
        return super.handle(request);
    }
}
