package com.lld.patterns.behavioral.iterator;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the final generated report containing compiled rows and execution metadata.
 */
public class ReportData {
    private final List<ReportRow> rows;
    private final LocalDateTime generatedAt;

    public ReportData(List<ReportRow> rows, LocalDateTime generatedAt) {
        this.rows = rows;
        this.generatedAt = generatedAt;
    }

    public List<ReportRow> getRows() {
        return rows;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "rowsCount=" + rows.size() +
                ", generatedAt=" + generatedAt +
                '}';
    }
}
