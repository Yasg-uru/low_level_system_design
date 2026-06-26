# LLD Practice - Java Version

Learn Low-Level Design with **Java**. This folder is self-contained with all Java implementations, tests, and documentation.

## 🎯 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.8.1 or higher

### Installation
```bash
mvn clean install
```

### Running Tests
```bash
mvn test
```

### Building
```bash
mvn clean package
```

### Running Specific Test
```bash
mvn test -Dtest=AbstractionTest
```

## 📁 Structure

```
java/
├── src/main/java/com/lld/    # Java source code
│   ├── oops/                 # OOPS Fundamentals
│   │   ├── abstraction/
│   │   ├── encapsulation/
│   │   ├── inheritance/
│   │   └── polymorphism/
│   │
│   ├── solid/                # SOLID Principles
│   │   ├── srp/
│   │   │   ├── bad/
│   │   │   └── good/
│   │   ├── ocp/
│   │   ├── lsp/
│   │   ├── isp/
│   │   └── dip/
│   │
│   └── patterns/             # Design Patterns
│       ├── creational/
│       │   ├── singleton/
│       │   ├── factorymethod/
│       │   └── abstractfactory/
│       ├── structural/
│       │   ├── adapter/
│       │   ├── decorator/
│       │   ├── facade/
│       │   └── proxy/
│       └── behavioral/
│           ├── observer/
│           ├── strategy/
│           └── command/
│
├── src/test/java/com/lld/   # JUnit 5 tests
│   ├── oops/
│   ├── solid/
│   └── patterns/
│
├── docs/                     # Theory and documentation
│   ├── 00-oops/
│   ├── 01-solid/
│   └── 02-patterns/
│
├── exercises/               # Practice problems
│   ├── 00-oops/
│   └── 01-solid/
│
├── pom.xml                 # Maven configuration
└── README.md              # This file
```

## 📚 Learning Path

### 1. OOPS Fundamentals (oops/)
Start with understanding basic OOP concepts:
- **Abstraction**: `abstraction/` (Vehicle, IPaymentProcessor)
- **Encapsulation**: `encapsulation/` (BankAccount)
- **Inheritance**: `inheritance/` (Employee, Manager, Developer)
- **Polymorphism**: `polymorphism/` (Shape interface implementations)

### 2. SOLID Principles (solid/)
Learn design principles for maintainable code:
- **SRP**: `srp/` (Single Responsibility - good example included)
- **OCP**: `ocp/` (Open-Closed - structure ready)
- **LSP**: `lsp/` (Liskov Substitution - structure ready)
- **ISP**: `isp/` (Interface Segregation - structure ready)
- **DIP**: `dip/` (Dependency Inversion - structure ready)

### 3. Design Patterns (patterns/)
Learn proven solutions to design problems:

#### Creational Patterns (patterns/creational/)
- **Singleton**: `creational/singleton/` (eager + lazy initialization, Logger example)
- **Factory Method**: `creational/factorymethod/` (structure ready)
- **Abstract Factory**: `creational/abstractfactory/` (structure ready)

#### Structural Patterns (patterns/structural/)
- **Adapter**: `structural/adapter/`
- **Decorator**: `structural/decorator/`
- **Facade**: `structural/facade/`
- **Proxy**: `structural/proxy/`

#### Behavioral Patterns (patterns/behavioral/)
- **Observer**: `behavioral/observer/`
- **Strategy**: `behavioral/strategy/`
- **Command**: `behavioral/command/`

## 🎓 How to Use Each Concept

Each concept folder contains:

```
concept/
├── README.md                    # Theory and explanation
├── MainClass.java              # Core implementation
├── [ConcreteClass].java        # Concrete implementations
├── [Interface].java            # Interface definitions
├── examples/
│   └── [Example].java          # Real-world usage
└── [Concept]Test.java          # JUnit 5 tests
```

### Study Steps
1. Open `README.md` - understand the concept
2. Read main class and concrete implementations
3. Look at examples - see practical usage
4. Check test file - understand how to use
5. Review `bad/good` - learn anti-patterns (SOLID)

## 🔧 Maven Commands

```bash
mvn clean install              # Full build
mvn clean package             # Create JAR
mvn test                      # Run all tests
mvn test -Dtest=TestName      # Run specific test
mvn test -Dtest=*Solid*       # Run matching tests
mvn clean test jacoco:report  # Coverage report
```

## 📖 Key Files

| File | Purpose |
|------|---------|
| `src/main/java/com/lld/oops/` | Foundation OOP concepts |
| `src/main/java/com/lld/solid/` | Design principles (bad/good) |
| `src/main/java/com/lld/patterns/` | Design patterns |
| `src/test/java/` | JUnit 5 tests |
| `docs/` | Theory and learning materials |
| `exercises/` | Practice problems by difficulty |

## ✨ Features

✅ **Java 17+**: Modern Java features
✅ **Maven Build System**: Standard Java project structure
✅ **JUnit 5**: Comprehensive testing framework
✅ **Proper Packages**: `com.lld.*` organization
✅ **Documentation**: Each concept has README
✅ **Real-World Examples**: Logger, BankAccount, PaymentProcessor
✅ **Bad vs Good Patterns**: Learn anti-patterns (SOLID)
✅ **Production-Grade Code**: Professional quality

## 🎯 Interview Preparation

This Java version helps prepare for:
- System design interviews
- Java-specific design questions
- Pattern implementation in Java
- Enterprise code design discussions

## 🔗 Parallel Learning

Also available: **TypeScript version** in `../ts/`

Learn both languages to:
- Understand design patterns language-agnostically
- Compare different approaches to same problems
- Become versatile engineer

See `../PARALLEL_LEARNING_GUIDE.md` for how to learn both.

## 📝 Java Conventions

Following standard Java conventions:

- **Packages**: `com.lld.oops.abstraction`
- **Classes**: `PascalCase` (Vehicle, BankAccount)
- **Interfaces**: `PascalCase` with `I` prefix (IPaymentProcessor)
- **Methods**: `camelCase` (calculateBonus, getBalance)
- **Constants**: `UPPER_SNAKE_CASE` (MAX_BALANCE, DEFAULT_TIMEOUT)
- **Access Modifiers**: `public`, `protected`, `private`
- **Annotations**: `@Override`, `@Test`, `@DisplayName`

## 🚀 Getting Started

1. **Verify Java version**:
   ```bash
   java -version      # Should be 17 or higher
   ```

2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

3. **Run tests**:
   ```bash
   mvn test
   ```

4. **Pick a concept**:
   Start with `src/main/java/com/lld/oops/abstraction/`

5. **Study the code**:
   Read classes and run tests

6. **Run specific test**:
   ```bash
   mvn test -Dtest=AbstractionTest
   ```

## 💡 Pro Tips

- Always read the README in each concept folder
- Study the interface/abstract class first
- Then read concrete implementations
- Look at test cases to understand usage
- Modify code and run tests to validate
- Write your own test cases

## 🔥 Java-Specific Features Used

- **Abstract Classes**: For defining contracts
- **Interfaces**: For pure abstraction
- **Access Modifiers**: Explicit access control
- **Generics**: For reusable code
- **Records** (Java 16+): For immutable data
- **Sealed Classes** (Java 17+): For restricted hierarchies
- **@Override Annotation**: Validate correct overriding
- **JUnit 5**: Modern testing framework

## 📚 Further Learning

- [Official Java Documentation](https://docs.oracle.com/en/java/)
- Design Patterns: Gang of Four
- Clean Code by Robert C. Martin
- Effective Java by Joshua Bloch

## 🤝 Contributing

To add more concepts:
1. Create folder in appropriate package
2. Create abstract class/interface
3. Create concrete implementations
4. Add test file with @Test methods
5. Create README.md with explanation
6. Follow existing code style

---

**Happy Learning with Java!** 🚀

Start with OOPS → Progress to SOLID → Master Patterns
