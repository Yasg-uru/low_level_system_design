/**
 * ============================================================================
 * ABSTRACT FACTORY PATTERN
 * ============================================================================
 * * Purpose: Provides an interface for creating families of related or dependent 
 * objects (Repositories) without specifying their concrete classes.
 * * Components:
 * - Abstract Products:  UserRepository, OrderRepository
 * - Concrete Products:  Postgres/Mongo implementations
 * - Abstract Factory:   RepositoryFactory
 * - Concrete Factories: PostgresFactory, MongoFactory
 * - Client:             UserService
 */

// ============================================================================
// 1. ABSTRACT PRODUCTS (Interfaces)
// ============================================================================

export interface UserRepository {
  getUser(id: number): void;
}

export interface OrderRepository {
  getOrder(id: number): void;
}

// ============================================================================
// 2. CONCRETE PRODUCTS: POSTGRESQL IMPLEMENTATION
// ============================================================================

export class PostgresUserRepository implements UserRepository {
  public getUser(id: number): void {
    console.log(`[PostgreSQL] Fetching user with ID: ${id}`);
  }
}

export class PostgresOrderRepository implements OrderRepository {
  public getOrder(id: number): void {
    console.log(`[PostgreSQL] Fetching order with ID: ${id}`);
  }
}

// ============================================================================
// 3. CONCRETE PRODUCTS: MONGODB IMPLEMENTATION
// ============================================================================

export class MongoUserRepository implements UserRepository {
  public getUser(id: number): void {
    console.log(`[MongoDB] Fetching user with ID: ${id}`);
  }
}

export class MongoOrderRepository implements OrderRepository {
  public getOrder(id: number): void {
    console.log(`[MongoDB] Fetching order with ID: ${id}`);
  }
}

// ============================================================================
// 4. ABSTRACT FACTORY
// ============================================================================

export interface RepositoryFactory {
  createUserRepository(): UserRepository;
  createOrderRepository(): OrderRepository;
}

// ============================================================================
// 5. CONCRETE FACTORIES
// ============================================================================

export class PostgresFactory implements RepositoryFactory {
  public createUserRepository(): UserRepository {
    return new PostgresUserRepository();
  }

  public createOrderRepository(): OrderRepository {
    return new PostgresOrderRepository();
  }
}

export class MongoFactory implements RepositoryFactory {
  public createUserRepository(): UserRepository {
    return new MongoUserRepository();
  }

  public createOrderRepository(): OrderRepository {
    return new MongoOrderRepository();
  }
}

// ============================================================================
// 6. CLIENT SERVICE (Dependency Injection via Constructor)
// ============================================================================

export class UserService {
  // Using TypeScript's shorthand for parameter properties
  constructor(private readonly factory: RepositoryFactory) {}

  public getUserProfile(userId: number): void {
    const userRepo = this.factory.createUserRepository();
    const orderRepo = this.factory.createOrderRepository();

    userRepo.getUser(userId);
    orderRepo.getOrder(1001);
  }
}

// ============================================================================
// 7. BOOTSTRAP / CONFIGURATION
// ============================================================================

type DatabaseType = 'postgres' | 'mongo';

const CURRENT_DB: DatabaseType = 'postgres';

function bootstrapFactory(dbType: DatabaseType): RepositoryFactory {
  switch (dbType) {
    case 'postgres':
      return new PostgresFactory();
    case 'mongo':
      return new MongoFactory();
    default:
      throw new Error(`Unsupported database type: ${dbType}`);
  }
}

// Initialize application
const selectedFactory = bootstrapFactory(CURRENT_DB);
const userService = new UserService(selectedFactory);

// Execute business logic
console.log(`--- Application Started (Mode: ${CURRENT_DB.toUpperCase()}) ---`);
userService.getUserProfile(1);