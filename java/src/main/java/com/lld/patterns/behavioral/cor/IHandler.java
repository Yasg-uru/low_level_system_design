package com.lld.patterns.behavioral.cor;

/**
 * Interface defining operations for Chain of Responsibility handlers.
 */
public interface IHandler {
    
    /**
     * Sets the next handler in the execution chain.
     *
     * @param handler the successor handler
     * @return the successor handler to allow fluent builder chaining
     */
    IHandler setNext(IHandler handler);

    /**
     * Processes the support ticket request or forwards it to the successor.
     *
     * @param request the description of the support request
     * @return processing details or fallback message
     */
    String handle(String request);
}
