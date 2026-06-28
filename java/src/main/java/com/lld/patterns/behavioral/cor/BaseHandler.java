package com.lld.patterns.behavioral.cor;

/**
 * Abstract base class containing boilerplate chaining code for concrete support handlers.
 */
public abstract class BaseHandler implements IHandler {
    private IHandler nextHandler = null;

    @Override
    public IHandler setNext(IHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    @Override
    public String handle(String request) {
        if (this.nextHandler != null) {
            return this.nextHandler.handle(request);
        }
        return "End of chain reached. Nobody could handle: \"" + request + "\"";
    }
}
