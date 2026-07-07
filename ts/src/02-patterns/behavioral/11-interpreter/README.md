# Interpreter Design Pattern

## What is it?

The **Interpreter Pattern** defines a representation for a grammar and provides an interpreter that evaluates expressions written in that grammar. It is useful when you need to model a small language or rule set directly in code.

In this example, order rules are represented as a tree of expressions such as `StatusEquals`, `TotalGreaterThan`, `And`, `Or`, and `Not`.

## Why use it?

- **Encapsulates Rules**: Each rule becomes its own class instead of being buried inside conditionals.
- **Composable Logic**: You can combine small expressions into larger rule trees.
- **Readable Business Rules**: The structure mirrors the domain language more clearly than nested `if-else` statements.
- **Extensible Evaluation**: New expressions can be added without changing existing ones.

## When to use?

- When you have a small, well-defined grammar or rule engine.
- When business logic is best expressed as reusable expressions.
- When you want to combine simple predicates into larger decision trees.

## Common Mistakes

❌ **Bad approach**
Hardcoding all rule checks in one service makes the logic difficult to extend.
```typescript
function isEligible(order: { status: string; total: number; type: string }): boolean {
  return (order.total > 1000 && order.status === "confirmed") || order.type === "premium";
}
```

✅ **Good approach**
Split each rule into a separate expression and combine them into an expression tree.
```typescript
const highValueConfirmed = new AndExpression(
  new TotalGreaterThanExpression(1000),
  new StatusEqualsExpression("confirmed")
);

const eligibleForDiscount = new OrExpression(
  highValueConfirmed,
  new TypeEqualsExpression("premium")
);
```

## Key Points

- **Context**: Holds the data being interpreted.
- **Terminal Expressions**: Evaluate simple checks directly against the context.
- **Non-Terminal Expressions**: Combine other expressions with logical operators.
- **AST-like Structure**: The final rule set behaves like a small abstract syntax tree.

## Related Concepts

- **Composite Pattern**: Both patterns build tree-like structures of objects.
- **Strategy Pattern**: Strategy swaps algorithms, while Interpreter models a grammar of expressions.