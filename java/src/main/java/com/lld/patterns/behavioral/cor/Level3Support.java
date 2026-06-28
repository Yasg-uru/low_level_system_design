package com.lld.patterns.behavioral.cor;

/**
 * Concrete handler representing Support Tier 3.
 */
public class Level3Support extends BaseHandler {
    
    @Override
    public String handle(String request) {
        if ("billing issue".equalsIgnoreCase(request)) {
            return "[Level 3 Support]: Escalation resolved: \"billing issue\" processed.";
        }
        return super.handle(request);
    }
}
