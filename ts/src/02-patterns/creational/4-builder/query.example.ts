class QueryBuilder {
    private _columns: string[] = [];
    private _table: string = "";
    private _condition: string = "";
    private _isAscending: boolean = true;
    private _count: number = 0;
    select(columns: string[]): QueryBuilder {
        this._columns.push(...columns);
        return this;
    }
    from(table: string): QueryBuilder {
        this._table = table;
        return this;
    }
    where(condition: string): QueryBuilder {
        this._condition = condition;
        return this;
    }
    orderBy(isAscending: boolean): QueryBuilder {
        this._isAscending = isAscending;
        return this;
    }
    limit(count: number): QueryBuilder {
        if (count < 0) throw new Error("Limit count must be non-negative");
        this._count = count;
        return this;
    }
    build(): string {
        if (!this._table) throw new Error("Table name is required to build a query");
        if (this._columns.length === 0) this._columns.push("*"); // Default to all columns if none specified 

        const columns = this._columns.length > 0 ? this._columns.join(", ") : "*";
        const order = this._isAscending ? "ASC" : "DESC";
        if (this._condition) {
            return `SELECT ${columns} FROM ${this._table} WHERE ${this._condition} ORDER BY ${order} LIMIT ${this._count}`;
        }
        return `SELECT ${columns} FROM ${this._table} ORDER BY ${order} LIMIT ${this._count}`;
    }


}

const query = new QueryBuilder().select(["id", "name", "email"])
    .from("users")
    .where("age > 18")
    .orderBy(true)
    .limit(10)
    .build();

console.log(query); // Output: SELECT id, name, email FROM users WHERE age > 18 ORDER BY ASC LIMIT 10

