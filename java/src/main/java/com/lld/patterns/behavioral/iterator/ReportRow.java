package com.lld.patterns.behavioral.iterator;

import java.time.LocalDateTime;

/**
 * Represents a single row of compiled data inside ReportData.
 */
public class ReportRow {
    private final String id;
    private final double total;
    private final LocalDateTime date;

    public ReportRow(String id, double total, LocalDateTime date) {
        this.id = id;
        this.total = total;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ReportRow{" +
                "id='" + id + '\'' +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}
