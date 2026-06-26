package com.lld.patterns.behavioral.command;

import java.util.List;

/**
 * Mock business service to send campaign emails.
 */
public class EmailService {
    public void sendCampaign(List<String> userIds, String campaignId) {
        System.out.println("[EmailService] Dispatched campaign \"" + campaignId + "\" to " + userIds.size() + " targets.");
    }
}
