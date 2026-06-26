package com.lld.patterns.behavioral.command;

/**
 * Interface that all concrete commands must implement.
 */
public interface ICommand {
    CommandResult execute() throws Exception;
    void undo() throws Exception;
    String getDescription();
    boolean canUndo();
}
