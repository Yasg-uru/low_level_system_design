# SRP - Good Implementation (Java)

## Single Responsibility Principle

Each class has **one reason to change**.

## Classes and Responsibilities

| Class | Responsibility |
|-------|-----------------|
| `User` | User data |
| `IPasswordHasher` | Password hashing |
| `IEmailService` | Email sending |
| `IValidator` | Validation |
| `ILogger` | Logging |
| `UserCreationService` | Orchestration |

## Benefits

✅ Easy to test each responsibility independently
✅ Change password algorithm without affecting User
✅ Change email provider without affecting User
✅ Each class has one reason to change

## Key Patterns

### Dependency Injection
```java
public UserCreationService(
    IUserRepository userRepository,
    IPasswordHasher passwordHasher,
    // ... other dependencies
) { }
```

### Separation of Concerns
- User class only knows about user data
- EmailService only knows about sending emails
- PasswordHasher only knows about hashing

## Testing

Each responsibility can be tested independently with mocks:
```java
@Test
void testUserCreation() {
    IUserRepository mockRepo = new MockRepository();
    IPasswordHasher mockHasher = new MockHasher();
    IEmailService mockEmail = new MockEmailService();
    
    UserCreationService service = new UserCreationService(
        mockRepo, mockHasher, mockEmail, ...
    );
    
    service.createUser("user1", "user@example.com", "password123");
}
```
