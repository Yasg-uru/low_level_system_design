package com.lld.patterns.behavioral.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete command that represents a non-reversible operation (sending emails).
 */
public class SendMarketingEmailCommand implements ICommand {
    private final EmailService emailService;
    private final List<String> userIds;
    private final String campaignId;

    public SendMarketingEmailCommand(EmailService emailService, List<String> userIds, String campaignId) {
        this.emailService = emailService;
        this.userIds = userIds;
        this.campaignId = campaignId;
    }

    @Override
    public CommandResult execute() throws Exception {
        emailService.sendCampaign(userIds, campaignId);
        Map<String, Integer> payload = new HashMap<>();
        payload.put("sent", userIds.size());
        return CommandResult.ofSuccess(payload);
    }

    @Override
    public void undo() throws Exception {
        throw new UnsupportedOperationException("Cannot undo a sent email target operation");
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Send campaign " + campaignId + " to " + userIds.size() + " users";
    }
}
