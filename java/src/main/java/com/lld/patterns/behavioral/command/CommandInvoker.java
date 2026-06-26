package com.lld.patterns.behavioral.command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Invoker class that coordinates execution, rollback, redo, and batch queuing.
 */
public class CommandInvoker {
    private final Stack<ICommand> history = new Stack<>();
    private final Stack<ICommand> redoStack = new Stack<>();
    private final Queue<ICommand> queue = new LinkedList<>();

    public CommandResult execute(ICommand command) throws Exception {
        CommandResult result = command.execute();

        if (result.isSuccess()) {
            history.push(command);
            redoStack.clear(); // Clear redo on new actions

            AuditLogger.log(new AuditLogEntry(
                    command.getDescription(),
                    LocalDateTime.now(),
                    true
            ));
        } else {
            AuditLogger.log(new AuditLogEntry(
                    command.getDescription(),
                    LocalDateTime.now(),
                    false,
                    result.getError()
            ));
        }

        return result;
    }

    public void undo() throws Exception {
        if (history.isEmpty()) {
            throw new IllegalStateException("Nothing to undo");
        }

        ICommand command = history.pop();
        if (!command.canUndo()) {
            throw new IllegalStateException(command.getDescription() + " cannot be undone");
        }

        command.undo();
        redoStack.push(command);

        AuditLogger.log(new AuditLogEntry(
                "UNDO: " + command.getDescription(),
                LocalDateTime.now(),
                true
        ));
    }

    public void redo() throws Exception {
        if (redoStack.isEmpty()) {
            throw new IllegalStateException("Nothing to redo");
        }

        ICommand command = redoStack.pop();
        CommandResult result = command.execute();
        if (result.isSuccess()) {
            history.push(command);
        }
    }

    public void enqueue(ICommand command) {
        queue.offer(command);
    }

    public List<CommandResult> flushQueue() throws Exception {
        List<CommandResult> results = new ArrayList<>();
        while (!queue.isEmpty()) {
            ICommand command = queue.poll();
            results.add(execute(command));
        }
        return results;
    }

    public List<String> getHistory() {
        List<String> list = new ArrayList<>();
        for (ICommand c : history) {
            list.add(c.getDescription());
        }
        return list;
    }

    public boolean canUndo() {
        if (history.isEmpty()) {
            return false;
        }
        return history.peek().canUndo();
    }
}
