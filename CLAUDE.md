# LLD Practice Repository - Development Guide

## Repository Purpose

This is a **Low-Level Design practice repository** for learning and teaching:
- Object-Oriented Programming (OOPS) fundamentals
- SOLID design principles
- Design patterns (Creational, Structural, Behavioral)

Target audience: Students, junior developers, interview candidates, and anyone wanting to master software design.

## Code Organization

### Directory Structure
```
src/00-oops/        → Foundation OOP concepts
src/01-solid/       → SOLID principles with bad/good comparisons
src/02-patterns/    → Design patterns organized by type
docs/               → Theory and documentation
exercises/          → Practice problems
config/             → TypeScript, Jest, ESLint configs
```

### File Naming
- **Classes**: PascalCase (`BankAccount`, `UserService`)
- **Interfaces**: PascalCase with I prefix (`IRepository`, `INotification`)
- **Files**: kebab-case (`bank-account.ts`, `payment-processor.ts`)
- **Constants**: UPPER_SNAKE_CASE (`MAX_RETRIES`, `DEFAULT_TIMEOUT`)

## Adding Content

### Adding a New OOPS Concept

1. Create folder: `src/00-oops/05-new-concept/`
2. Create files:
   - `basic.ts` - Simple implementation
   - `example.ts` - Real-world usage
   - `README.md` - Explanation (see template below)
   - `index.test.ts` - Tests

### Adding a New SOLID Principle

1. Create folder: `src/01-solid/6-new-principle/`
2. Create subfolders: `bad/` and `good/`
3. In `bad/`: Anti-pattern implementation + README
4. In `good/`: Proper implementation + README
5. Create root README explaining principle

### Adding a Design Pattern

1. Create folder: `src/02-patterns/type/pattern-name/`
2. Create files:
   - `basic.ts` - Core pattern
   - `example.ts` - Practical usage
   - `README.md` - Pattern explanation
   - `index.test.ts` - Tests

## Documentation Template

### README.md Template

```markdown
# [Concept Name]

## What is it?
One paragraph explanation of the concept.

## Why use it?
- Benefit 1
- Benefit 2
- Benefit 3

## When to use?
Real-world scenarios where this applies.

## Common Mistakes

❌ **Bad approach**
\`\`\`typescript
// Anti-pattern code
\`\`\`

✅ **Good approach**
\`\`\`typescript
// Proper implementation
\`\`\`

## Key Points
- Point 1
- Point 2
- Point 3

## Related Concepts
- **[Concept A](../link/)**: How it relates
- **[Concept B](../link/)**: How it relates
```

## Code Quality Standards

### Mandatory

1. **No mixed language**: English only (was: English + Hindi mixed)
2. **Strict TypeScript**: Use strict mode, all types explicit
3. **Descriptive names**: No single-letter variables
4. **No commented-out code**: Remove or delete
5. **Proper encapsulation**: Private fields with controlled access

### TypeScript Rules

```typescript
// ✅ Good
class BankAccount {
  private balance: number;
  
  withdraw(amount: number): boolean {
    if (amount > this.balance) return false;
    this.balance -= amount;
    return true;
  }
}

// ❌ Bad
class BankAccount {
  balance: number; // Public, no validation
  
  withdraw(amount) { // No types
    this.balance -= amount; // No checks
  }
}
```

### Comments

Use comments only for non-obvious logic:

```typescript
// ✅ Good: Explains why
class Logger {
  private static instance: Logger | null = null;
  
  static getInstance(): Logger {
    // Lazy initialization - only create when first needed
    if (Logger.instance === null) {
      Logger.instance = new Logger();
    }
    return Logger.instance;
  }
}

// ❌ Bad: Restates code
class Logger {
  // Get the logger instance
  static getInstance(): Logger {
    // Check if instance is null
    if (Logger.instance === null) {
      // Create new instance
      Logger.instance = new Logger();
    }
    // Return the instance
    return Logger.instance;
  }
}
```

## Testing Standards

Every implementation should have tests:

```typescript
describe('BankAccount', () => {
  describe('withdraw', () => {
    it('should decrease balance when sufficient funds', () => {
      const account = new BankAccount('123', 1000);
      const success = account.withdraw(100);
      expect(success).toBe(true);
      expect(account.getBalance()).toBe(900);
    });

    it('should fail when insufficient funds', () => {
      const account = new BankAccount('123', 100);
      const success = account.withdraw(200);
      expect(success).toBe(false);
    });
  });
});
```

## Common Patterns to Follow

### Dependency Injection
```typescript
// ✅ Good: Dependencies injected
class UserService {
  constructor(
    private database: IDatabase,
    private emailService: IEmailService
  ) {}
}

// ❌ Bad: Hard-coded dependencies
class UserService {
  private database = new MySQLDatabase();
  private emailService = new EmailService();
}
```

### Interfaces for Abstraction
```typescript
// ✅ Good: Depend on interface
class PaymentService {
  constructor(private processor: IPaymentProcessor) {}
}

// ❌ Bad: Depend on concrete class
class PaymentService {
  constructor(private processor: StripePayment) {}
}
```

### Validation in Setters
```typescript
// ✅ Good: Validate data
class User {
  private age: number;
  
  setAge(age: number): void {
    if (age < 0 || age > 150) {
      throw new Error('Invalid age');
    }
    this.age = age;
  }
}

// ❌ Bad: No validation
class User {
  age: number; // Can be set to -5 or 500!
}
```

## Refactoring Completed

### What Changed

1. **Separated theory from code**
   - Theory/notes in `/docs/` (not yet created)
   - Clean implementations in `/src/`
   - Exercises in `/exercises/` (not yet created)

2. **Organized by difficulty**
   - Foundation: OOPS concepts
   - Intermediate: SOLID principles
   - Advanced: Design patterns

3. **Bad vs Good comparisons**
   - Shows anti-patterns in `/bad/`
   - Shows solutions in `/good/`
   - Explains the difference

4. **Production-grade code**
   - Strict TypeScript
   - Proper types everywhere
   - English only
   - No shortcuts

5. **Clear documentation**
   - README for each concept
   - Explains what, why, when, how
   - Real-world examples
   - Related concepts linked

### What Stayed the Same

- Your original code quality (now improved)
- Core concepts and examples
- The learning structure

### What Was Removed

- Mixed Hindi/English comments
- Scattered theory in code
- Inconsistent file organization
- God classes and violations (moved to bad/)

## Before Committing

1. **Run tests**: `npm test`
2. **Check types**: `npm run build` (should have no errors)
3. **Lint code**: `npm run lint:fix`
4. **Verify**: Code follows patterns above

## For Contributors

When adding new content:

1. Follow the file structure above
2. Match code quality standards
3. Include comprehensive README
4. Write tests for implementations
5. Link to related concepts
6. Use English only

## File Size Guidelines

- Implementation: 100-200 lines max
- Example: 50-150 lines max
- README: Keep to 1-2 screens of reading
- Test: Match implementation size

## Common Issues Fixed

### Issue: Mixed Languages
❌ Was: Hindi + English in comments
✅ Now: English only

### Issue: Theory + Code Mixed
❌ Was: 20 lines of explanation in code file
✅ Now: README.md contains explanation

### Issue: No Structure
❌ Was: Files scattered, no organization
✅ Now: Numbered, organized folders (00-, 01-, 02-)

### Issue: Unclear Code
❌ Was: Unclear variable names, complex logic
✅ Now: Clear names, documented logic

### Issue: Hard to Compare
❌ Was: Bad patterns buried with good
✅ Now: Bad/good side by side in SOLID section

## Questions About Design?

This repository emphasizes:
- **Clarity**: Code should be obvious
- **Correctness**: Proper types, validation
- **Consistency**: Same style throughout
- **Completeness**: Tests, docs, examples

Every concept should teach something useful for real projects.

---

**Repository Maintainer**: Yash Choudhary
**Purpose**: Advanced LLD Learning
**Quality Level**: Production-Ready
