# LLD Practice - Dual Language Repository

Master **Low-Level Design** in both **TypeScript** and **Java**!

This repository contains **identical learning structures** for both languages, allowing you to learn design concepts language-agnostically.

## 🎯 Choose Your Language

### 📘 TypeScript Version
Go to `/ts/` for TypeScript implementation
- More concise syntax
- Modern JavaScript features
- Great for learning core concepts

**Start**: `cd ts && npm install && npm test`

### ☕ Java Version
Go to `/java/` for Java implementation
- Strict type system
- Enterprise standard
- Production-ready patterns

**Start**: `cd java && mvn clean install && mvn test`

### 🔄 Both Together
Learn both simultaneously! See `PARALLEL_LEARNING_GUIDE.md` for how to compare implementations.

---

## 📁 Directory Structure

```
lld-practice/
│
├── /ts/                      ← TypeScript (INDEPENDENT)
│   ├── src/
│   │   ├── 00-oops/         (Abstraction, Encapsulation, Inheritance, Polymorphism)
│   │   ├── 01-solid/        (SRP, OCP, LSP, ISP, DIP with bad/good)
│   │   ├── 02-patterns/     (Singleton, Factory, Abstract Factory)
│   │   └── shared/
│   ├── docs/                (Theory and learning materials)
│   ├── exercises/           (Practice problems)
│   ├── package.json
│   ├── tsconfig.json
│   └── README.md           ← TypeScript Guide
│
├── /java/                    ← Java (INDEPENDENT)
│   ├── src/main/java/com/lld/
│   │   ├── oops/            (Same concepts as TypeScript)
│   │   ├── solid/           (Same principles as TypeScript)
│   │   └── patterns/        (Same patterns as TypeScript)
│   ├── src/test/java/       (JUnit 5 tests)
│   ├── docs/                (Theory and learning materials)
│   ├── exercises/           (Practice problems)
│   ├── pom.xml
│   └── README.md           ← Java Guide
│
└── Root Documentation
    ├── README.md            (This file)
    ├── PARALLEL_LEARNING_GUIDE.md  (How to learn both)
    ├── ARCHITECTURE.md      (Project structure)
    ├── CLAUDE.md           (Development guidelines)
    └── REFACTORING_SUMMARY.md
```

---

## 📚 What's Included

### OOPS Fundamentals (4 concepts)
Both languages include:
- **Abstraction**: Hiding complexity
- **Encapsulation**: Data hiding with controlled access
- **Inheritance**: Creating class hierarchies
- **Polymorphism**: Flexible, extensible code

### SOLID Principles (5 principles)
Both languages include structure for:
- **SRP** - Single Responsibility ✅ (good example in Java)
- **OCP** - Open-Closed
- **LSP** - Liskov Substitution
- **ISP** - Interface Segregation
- **DIP** - Dependency Inversion

### Design Patterns (Creational)
Both languages include:
- **Singleton** ✅ (eager + lazy initialization in Java)
- **Factory Method** (structure ready)
- **Abstract Factory** (structure ready)

---

## 🚀 Quick Start

### Option 1: TypeScript Only
```bash
cd ts
npm install
npm test
# Read ts/README.md for next steps
```

### Option 2: Java Only
```bash
cd java
mvn clean install
mvn test
# Read java/README.md for next steps
```

### Option 3: Both Languages (Recommended)
```bash
# Setup TypeScript
cd ts
npm install

# Setup Java (in another terminal)
cd java
mvn clean install

# Learn PARALLEL_LEARNING_GUIDE.md
cat PARALLEL_LEARNING_GUIDE.md
```

---

## 📖 Learning Paths

### Path 1: TypeScript First (Easier)
1. Start with `/ts/README.md`
2. Learn OOPS concepts (simpler syntax)
3. Progress to SOLID principles
4. Master design patterns
5. *Optional*: Repeat in Java for deeper understanding

### Path 2: Java First (Stricter)
1. Start with `/java/README.md`
2. Learn OOPS concepts (strict types)
3. Progress to SOLID principles
4. Master design patterns
5. Compare with TypeScript for flexibility perspective

### Path 3: Parallel Learning (Best)
1. Read `PARALLEL_LEARNING_GUIDE.md`
2. Pick a concept
3. Read both TypeScript and Java READMEs
4. Compare implementations
5. Understand language-agnostic design

---

## 🎓 Learning Each Concept

Both languages have identical organization. Each concept includes:

```
concept/
├── README.md               # What, Why, When, How
├── Implementation files    # Core code
├── Example files          # Real-world usage
├── Test files            # Usage patterns
└── bad/ + good/          # Anti-patterns (SOLID)
```

### Study Steps
1. **Open README.md** - Understand the concept
2. **Read implementations** - See how it's done
3. **Study examples** - Practical usage
4. **Review tests** - See how to use it
5. **Compare bad/good** - Understand mistakes

---

## 🔄 TypeScript vs Java

| Aspect | TypeScript | Java |
|--------|-----------|------|
| **Syntax** | Concise, modern | Explicit, verbose |
| **Types** | Optional (but strict mode enforced) | Mandatory |
| **Access Control** | public, private, protected | public, private, protected |
| **Abstraction** | interface, abstract class | interface, abstract class |
| **Learning Curve** | Easier for beginners | Steeper but more thorough |
| **Enterprise Use** | Growing | Industry standard |

---

## 🎯 Interview Preparation

Learning both versions prepares you for:
- ✅ System design interviews
- ✅ Code design questions
- ✅ Language-specific pattern questions
- ✅ Demonstrating versatility
- ✅ Understanding trade-offs

---

## ✨ Key Features

✅ **Independent Folders**: Each language is self-contained
✅ **Same Structure**: Identical organization (00-oops, 01-solid, 02-patterns)
✅ **Complete Documentation**: READMEs in each concept folder
✅ **Real-World Examples**: Logger, BankAccount, Payment systems
✅ **Bad vs Good**: Learn anti-patterns in SOLID section
✅ **Comprehensive Tests**: All implementations tested
✅ **Production-Grade Code**: Professional quality standards

---

## 📝 Documentation Files

| File | Purpose |
|------|---------|
| `ts/README.md` | TypeScript guide - start here for TS |
| `java/README.md` | Java guide - start here for Java |
| `PARALLEL_LEARNING_GUIDE.md` | How to learn both together |
| `ARCHITECTURE.md` | Overall project structure |
| `CLAUDE.md` | Development guidelines |
| `REFACTORING_SUMMARY.md` | What was refactored |

---

## 🔥 For Parallel Learning

If learning both languages:

1. **Read** `PARALLEL_LEARNING_GUIDE.md` first
2. **Setup both** TypeScript and Java
3. **Pick a concept** (e.g., Abstraction)
4. **Read** both TypeScript and Java implementations
5. **Compare**: Notice differences and similarities
6. **Understand**: Why each language made different choices

This approach helps you:
- Learn design patterns language-agnostically
- Appreciate language-specific strengths
- Become a versatile engineer
- Make better language choices

---

## 💡 Pro Tips

1. **Each folder is independent** - You don't need both
2. **Follow the same structure** - Same concepts in both
3. **Read READMEs first** - Each concept documented
4. **Run tests to validate** - See code in action
5. **Modify code** - Break it, fix it, understand it
6. **Compare implementations** - See language differences

---

## 🚀 Next Steps

### For TypeScript
```bash
cd ts
npm install
npm test
cat README.md      # Read TypeScript guide
```

### For Java
```bash
cd java
mvn clean install
mvn test
cat README.md      # Read Java guide
```

### For Both
```bash
cat PARALLEL_LEARNING_GUIDE.md  # Learn how to compare
cd ts && npm install            # Setup TypeScript
cd java && mvn install          # Setup Java
# Then learn same concept in both languages
```

---

## 📚 What You'll Master

By the end, you'll understand:
- ✅ OOPS fundamentals (in both languages)
- ✅ SOLID design principles
- ✅ Creational design patterns
- ✅ How patterns work language-agnostically
- ✅ When to use each pattern
- ✅ Language-specific trade-offs

---

## 🎯 Start Your Journey

**TypeScript?** → Go to `/ts/README.md`  
**Java?** → Go to `/java/README.md`  
**Both?** → Read `PARALLEL_LEARNING_GUIDE.md`

---

**Choose your language(s) and start learning!** 🚀

```
LLD Practice (Low-Level Design)
├── TypeScript (Easier, Modern)
└── Java (Stricter, Enterprise)

Both teach same concepts, different languages!
```

---

**Happy Learning!** 🎓

