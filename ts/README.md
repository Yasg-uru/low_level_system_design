# LLD Practice - TypeScript Version

Learn Low-Level Design with **TypeScript**. This folder is self-contained with all TypeScript implementations, tests, and documentation.

## 🎯 Quick Start

### Installation
```bash
npm install
```

### Running Tests
```bash
npm test
npm test:watch
```

### Building
```bash
npm run build
npm run dev
```

### Linting
```bash
npm run lint
npm run lint:fix
```

## 📁 Structure

```
ts/
├── src/                     # TypeScript source code
│   ├── 00-oops/            # OOPS Fundamentals
│   │   ├── 01-abstraction/
│   │   ├── 02-encapsulation/
│   │   ├── 03-inheritance/
│   │   └── 04-polymorphism/
│   │
│   ├── 01-solid/           # SOLID Principles
│   │   ├── 1-srp/
│   │   ├── 2-ocp/
│   │   ├── 3-lsp/
│   │   ├── 4-isp/
│   │   └── 5-dip/
│   │
│   ├── 02-patterns/        # Design Patterns
│   │   ├── creational/
│   │   │   ├── 1-singleton/
│   │   │   ├── 2-factory-method/
│   │   │   └── 3-abstract-factory/
│   │   ├── structural/
│   │   │   ├── 1-adapter/
│   │   │   ├── 2-decorator/
│   │   │   ├── 3-facade/
│   │   │   └── 4-proxy/
│   │   └── behavioral/
│   │       ├── 1-observer/
│   │       ├── 2-strategy/
│   │       ├── 3-command/
│   │       └── 4-state/
│   │
│   └── shared/             # Common utilities
│
├── docs/                    # Theory and documentation
│   ├── 00-oops/
│   ├── 01-solid/
│   └── 02-patterns/
│
├── exercises/              # Practice problems
│   ├── 00-oops/
│   └── 01-solid/
│
├── package.json            # Dependencies
├── tsconfig.json           # TypeScript config
├── jest.config.js          # Test configuration
├── .eslintrc.json          # Linting rules
└── README.md              # This file
```

## 📚 Learning Path

### 1. OOPS Fundamentals (00-oops/)
Start with understanding basic OOP concepts:
- **Abstraction**: `01-abstraction/README.md`
- **Encapsulation**: `02-encapsulation/README.md`
- **Inheritance**: `03-inheritance/README.md`
- **Polymorphism**: `04-polymorphism/README.md`

### 2. SOLID Principles (01-solid/)
Learn design principles for maintainable code:
- **SRP**: `1-srp/README.md` (Single Responsibility)
- **OCP**: `2-ocp/README.md` (Open-Closed)
- **LSP**: `3-lsp/README.md` (Liskov Substitution)
- **ISP**: `4-isp/README.md` (Interface Segregation)
- **DIP**: `5-dip/README.md` (Dependency Inversion)

### 3. Design Patterns (02-patterns/)
Learn proven solutions to design problems:

#### Creational Patterns
- **Singleton**: `creational/1-singleton/README.md`
- **Factory Method**: `creational/2-factory-method/README.md`
- **Abstract Factory**: `creational/3-abstract-factory/README.md`

#### Structural Patterns
- **Adapter**: `structural/1-adapter/README.md`
- **Decorator**: `structural/2-decorator/README.md`
- **Facade**: `structural/3-facade/README.md`
- **Proxy**: `structural/4-proxy/README.md`

#### Behavioral Patterns
- **Observer**: `behavioral/1-observer/README.md`
- **Strategy**: `behavioral/2-strategy/README.md`
- **Command**: `behavioral/3-command/README.md`
- **State**: `behavioral/4-state/README.md`

## 🎓 How to Use Each Concept

Each concept folder contains:

```
concept/
├── README.md              # Theory and explanation
├── basic.ts              # Simple implementation
├── [variation].ts        # Alternative approaches
├── [example].ts          # Real-world usage
├── index.test.ts         # Tests and usage patterns
└── bad/                  # Anti-patterns (SOLID only)
```

### Study Steps
1. Open `README.md` - understand the concept
2. Read `basic.ts` - see simple implementation
3. Study `[example].ts` - practical usage
4. Check `index.test.ts` - see how to use it
5. Review `bad/` - understand what NOT to do (SOLID)

## 🔧 npm Scripts

```bash
npm install         # Install dependencies
npm run build      # Compile TypeScript
npm run dev        # Run with ts-node
npm test           # Run all tests
npm test:watch     # Watch mode
npm test:coverage  # Generate coverage
npm run lint       # Check code quality
npm run lint:fix   # Auto-fix issues
```

## 📖 Key Files

| File | Purpose |
|------|---------|
| `src/00-oops/` | Foundation OOP concepts |
| `src/01-solid/` | Design principles (bad/good) |
| `src/02-patterns/` | Design patterns |
| `docs/` | Theory and learning materials |
| `exercises/` | Practice problems by difficulty |

## ✨ Features

✅ **TypeScript Strict Mode**: Full type safety
✅ **Comprehensive Documentation**: Each concept has README
✅ **Real-World Examples**: Logger, BankAccount, PaymentProcessor
✅ **Bad vs Good Patterns**: Learn what NOT to do
✅ **Tests Included**: Validate understanding
✅ **Production-Grade Code**: Professional quality

## 🎯 Interview Preparation

This TypeScript version helps prepare for:
- System design interviews
- Code design questions
- Pattern implementation questions
- TypeScript-specific design discussions

## 🔗 Parallel Learning

Also available: **Java version** in `../java/`

Learn both languages to:
- Understand design patterns language-agnostically
- Compare different approaches to same problems
- Become versatile engineer

See `../PARALLEL_LEARNING_GUIDE.md` for how to learn both.

## 📝 Code Quality

- **TypeScript 5.0+**: Strict mode enabled
- **ESLint**: Code quality enforcement
- **Jest**: Comprehensive testing
- **Naming Conventions**:
  - Classes: `PascalCase`
  - Interfaces: `PascalCase` with `I` prefix
  - Methods: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`

## 🚀 Getting Started

1. **Install dependencies**:
   ```bash
   npm install
   ```

2. **Run tests**:
   ```bash
   npm test
   ```

3. **Pick a concept**:
   Open `src/00-oops/01-abstraction/README.md`

4. **Study the code**:
   Read implementations and run examples

5. **Run tests to validate**:
   ```bash
   npm test -- abstraction
   ```

## 💡 Pro Tips

- Read the concept README first
- Don't just read code, run it
- Modify examples and see what breaks
- Write your own test cases
- Build small projects using these concepts

## 📚 Further Learning

- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- Design Patterns: Gang of Four
- Clean Code by Robert C. Martin
- Refactoring by Martin Fowler

## 🤝 Contributing

To add more concepts:
1. Create folder following `NN-name` pattern
2. Create `README.md` with explanation
3. Create implementation files
4. Add tests
5. Follow existing code style

---

**Happy Learning with TypeScript!** 🚀

Start with OOPS → Progress to SOLID → Master Patterns
