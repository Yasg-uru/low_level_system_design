# Interpreter Design Pattern - Java

## What is it?

The **Interpreter Pattern** represents a grammar as a set of objects and evaluates sentences by interpreting those objects. It is useful for small languages, rule engines, and declarative business checks.

In this package, order eligibility rules are modeled as a tree of expressions such as status checks, total comparisons, and logical combinators.

## Why use it?

- **Rule Encapsulation**: Each rule is isolated in its own class.
- **Composable Expressions**: Small checks can be combined with `And`, `Or`, and `Not`.
- **Readable Decision Logic**: The code reflects the business language more clearly than nested conditionals.
- **Easy Extension**: New expressions can be added without rewriting the existing tree.

## Implementation Details

- **`OrderContext`**: Holds the data being interpreted.
- **`IExpression`**: The common contract for all expressions.
- **Terminal Expressions**: `StatusEqualsExpression`, `TotalGreaterThanExpression`, `TypeEqualsExpression`.
- **Non-Terminal Expressions**: `AndExpression`, `OrExpression`, `NotExpression`.
- **`Main`**: Demonstrates a rule tree for discount eligibility.

## Related Concepts

- **Composite Pattern**: Both patterns assemble tree-like structures of objects.
- **Strategy Pattern**: Strategy selects one algorithm at runtime, while Interpreter evaluates a grammar of expressions.