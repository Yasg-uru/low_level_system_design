package com.lld.patterns.behavioral.command;

/**
 * Encapsulates the execution response of a Command.
 */
public class CommandResult {
    private final boolean success;
    private final Object data;
    private final String error;

    public CommandResult(boolean success, Object data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static CommandResult ofSuccess(Object data) {
        return new CommandResult(true, data, null);
    }

    public static CommandResult ofSuccess() {
        return new CommandResult(true, null, null);
    }

    public static CommandResult ofFailure(String error) {
        return new CommandResult(false, null, error);
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
