package com.lld.patterns.behavioral.cor;

/**
 * Concrete handler representing Support Tier 2.
 */
public class Level2Support extends BaseHandler {
    
    @Override
    public String handle(String request) {
        if ("bug report".equalsIgnoreCase(request)) {
            return "[Level 2 Support]: Logged and processed \"bug report\" lifecycle request.";
        }
        return super.handle(request);
    }
}
