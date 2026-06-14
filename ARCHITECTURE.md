# Repository Architecture

## Overview

This repository is organized as a progressive learning path from fundamental OOP concepts through design patterns. The structure supports both learning and reference use.

## Design Principles

### 1. Clear Separation of Concerns
- **docs/**: Theory and explanations (no code)
- **src/**: Clean, runnable implementations
- **exercises/**: Practice problems with solutions
- **tests/**: Unit tests for each concept

### 2. Consistent Structure
Every concept follows the same pattern:
```
concept/
├── README.md           # Theory and explanation
├── basic.ts            # Simple implementation
├── [variant].ts        # Alternative approaches  
├── [example].ts        # Real-world usage
└── index.test.ts       # Tests and usage patterns
```

### 3. Bad vs Good Comparisons
SOLID principles folder includes:
```
principle/
├── bad/                # Anti-pattern examples
│   ├── [violation].ts
│   └── README.md
├── good/               # Proper implementation
│   ├── [solution].ts
│   └── README.md
└── README.md           # Principle explanation
```

### 4. Progressive Complexity
- **00-oops/**: Foundation (basic concepts)
- **01-solid/**: Intermediate (applying concepts)
- **02-patterns/**: Advanced (solutions to problems)

## File Naming Conventions

### TypeScript Files
- **Classes**: PascalCase
- **Interfaces**: PascalCase with `I` prefix
- **Files**: kebab-case

Examples:
- `class BankAccount` in `bank-account.ts`
- `interface IPaymentStrategy` in `payment-strategy.ts`

### Documentation Files
- **README.md**: One per concept
- **ARCHITECTURE.md**: Project structure
- **CLAUDE.md**: Contributor guidelines

## Module Organization

### OOPS Section (`src/00-oops/`)
**Purpose**: Teach fundamental OOP concepts

| Concept | Files | Goal |
|---------|-------|------|
| Abstraction | `abstract-class.ts`, `interface.ts` | Hide details, expose essentials |
| Encapsulation | `bank-account.ts` | Data hiding, controlled access |
| Inheritance | `employee-hierarchy.ts` | Class hierarchies, code reuse |
| Polymorphism | `method-overriding.ts` | Flexible, extensible code |

**Learning Approach**:
1. Read README to understand concept
2. Study implementation examples
3. Understand practical applications
4. Review test cases

### SOLID Section (`src/01-solid/`)
**Purpose**: Learn design best practices

| Principle | Bad/Good | Focus |
|-----------|----------|-------|
| SRP | god-class.ts / separated-concerns.ts | Single responsibility |
| OCP | switch-statement.ts / strategy-pattern.ts | Extensibility |
| LSP | rectangle-square.ts / proper-hierarchy.ts | Inheritance contracts |
| ISP | fat-interface.ts / segregated-interfaces.ts | Interface design |
| DIP | tight-coupling.ts / dependency-injection.ts | Loose coupling |

**Learning Approach**:
1. Study the bad pattern (understand the problem)
2. See how it violates the principle
3. Review the good solution
4. Understand the improvements

### Design Patterns Section (`src/02-patterns/`)
**Purpose**: Learn common solution patterns

#### Creational Patterns
- **Singleton**: Control instance creation
- **Factory Method**: Create objects via inheritance
- **Abstract Factory**: Create object families

**Structure**:
```
pattern/
├── basic.ts              # Core pattern
├── [real-world].example.ts # Practical usage
└── README.md             # Pattern explanation
```

## Code Quality Standards

### TypeScript Configuration
```json
{
  "strict": true,
  "noImplicitAny": true,
  "strictNullChecks": true,
  "noUnusedLocals": true,
  "noImplicitReturns": true
}
```

### Naming Conventions

**Classes**
```typescript
class UserService { }          // PascalCase
class DatabasePool { }         // Descriptive
class PaymentProcessor { }     // One word if possible
```

**Interfaces**
```typescript
interface IRepository { }      // I prefix
interface IPaymentStrategy { } // Descriptive, specific
interface INotification { }    // Singular
```

**Methods**
```typescript
getUserById(id: string)        // camelCase, verb
calculateTotalPrice()          // Descriptive
isValid()                       // Boolean with is/has/can
```

**Constants**
```typescript
const MAX_RETRIES = 3;         // UPPER_SNAKE_CASE
const DEFAULT_TIMEOUT = 5000;
const API_ENDPOINT = '...';
```

### Documentation Standards

**Every Implementation File**:
1. Header comment explaining purpose
2. Clear variable names (no x, y, temp)
3. Inline comments only for non-obvious logic
4. Export statement at end

**Every README.md**:
```markdown
# [Concept Name]

## What is it?
Brief, clear definition

## Why use it?
Problems it solves

## When to use?
Real-world scenarios

## Common Mistakes
Anti-patterns to avoid

## Key Points
Bullet points of essentials

## Related Concepts
Links to related topics
```

## Testing Strategy

### Test Organization
```typescript
describe('[Concept Name]', () => {
  describe('Feature 1', () => {
    it('should do something', () => {
      // Test implementation
    });
  });
});
```

### Test Coverage
- Happy path (normal usage)
- Edge cases (boundaries)
- Error cases (invalid input)
- Integration (multiple components)

## Import/Export Strategy

### Avoid Circular Dependencies
```typescript
// ❌ Bad
import { ServiceA } from './a';
// In a.ts: import { ServiceB } from './b';
// In b.ts: import { ServiceA } from './a';

// ✅ Good
// Extract common interface
import { IService } from './interfaces';
```

### Module Exports
```typescript
// Export interfaces separately
export interface IUser { }
export interface IRepository { }

// Export implementations
export class User { }
export class Repository { }
```

## Adding New Concepts

### For OOPS Concept
```
src/00-oops/05-new-concept/
├── implementation.ts
├── example.ts
├── index.test.ts
└── README.md
```

### For SOLID Principle
```
src/01-solid/6-new-principle/
├── bad/
│   ├── violation.ts
│   └── README.md
├── good/
│   ├── solution.ts
│   └── README.md
├── compare.ts
├── index.test.ts
└── README.md
```

### For Design Pattern
```
src/02-patterns/type/pattern-name/
├── basic.ts
├── [example].example.ts
├── index.test.ts
└── README.md
```

## Documentation Links

### Within README.md
Use markdown links to related concepts:
```markdown
## Related Concepts
- **[Abstraction](../01-abstraction/)**: Hiding implementation
- **[Polymorphism](../04-polymorphism/)**: Using abstractions
```

### Cross-Concept Links
```markdown
This relates to SRP because...
See [DIP](../../01-solid/5-dip/) for how to achieve this.
```

## Configuration Files

### tsconfig.json
- Strict mode enabled
- ES2020 target
- Source maps included
- Declaration files generated

### jest.config.js
- ts-jest preset
- Test pattern: `*.test.ts`
- Source root: `src/`
- Coverage tracking enabled

### .eslintrc.json
- TypeScript parser
- Naming conventions enforced
- Return types required
- Unused variable detection

## Maintenance

### Regular Reviews
1. Update examples with latest TypeScript features
2. Keep dependencies current
3. Review test coverage
4. Verify documentation accuracy

### Common Changes
- Adding new pattern: Create folder structure, implement, document
- Updating pattern: Maintain backward compatibility, update README
- Fixing errors: Update all related documentation

## Performance Considerations

- TypeScript compilation speed: Incremental builds
- Test execution: Fast by design (no external dependencies)
- Documentation build: Static files only

## Extensibility

### Adding Custom Patterns
Repository supports custom pattern additions without modifying core structure.

### Plugin Architecture (Future)
- Support for additional pattern categories
- Custom learning paths
- Integration with online platforms

## Standards Compliance

- **TypeScript 5.0+**: Strict mode
- **ECMAScript 2020**: Modern syntax
- **Jest**: Testing framework
- **ESLint**: Code quality

## Backward Compatibility

- No breaking changes within a concept (maintain version)
- New concepts added with new numbering
- Documentation preserved for deprecated approaches

---

**Last Updated**: 2024
**Repository Version**: 1.0.0
**TypeScript Version**: 5.0+
