package com.lld.patterns.creational.builder.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {
    private final List<String> columns = new ArrayList<>();
    private String table = "";
    private String condition = "";
    private boolean isAscending = true;
    private int count = 0;

    public QueryBuilder select(String[] cols) {
        this.columns.addAll(Arrays.asList(cols));
        return this;
    }

    public QueryBuilder select(List<String> cols) {
        this.columns.addAll(cols);
        return this;
    }

    public QueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    public QueryBuilder where(String condition) {
        this.condition = condition;
        return this;
    }

    public QueryBuilder orderBy(boolean isAscending) {
        this.isAscending = isAscending;
        return this;
    }

    public QueryBuilder limit(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Limit count must be non-negative");
        }
        this.count = count;
        return this;
    }

    public String build() {
        String colsStr = "*";
        if (!columns.isEmpty()) {
            colsStr = String.join(", ", columns);
        }
        String order = isAscending ? "ASC" : "DESC";
        return String.format("SELECT %s FROM %s WHERE %s ORDER BY %s LIMIT %d", colsStr, table, condition, order, count);
    }

    public static void main(String[] args) {
        String query = new QueryBuilder()
                .select(new String[]{"id", "name", "email"})
                .from("users")
                .where("age > 18")
                .orderBy(true)
                .limit(10)
                .build();

        System.out.println(query); // Output: SELECT id, name, email FROM users WHERE age > 18 ORDER BY ASC LIMIT 10
    }
}
