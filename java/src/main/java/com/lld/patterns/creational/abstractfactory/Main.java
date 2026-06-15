package com.lld.patterns.creational.abstractfactory;

public class Main {
    public enum DatabaseType {
        POSTGRES,
        MONGO
    }

    private static final DatabaseType CURRENT_DB = DatabaseType.POSTGRES;

    private static RepositoryFactory bootstrapFactory(DatabaseType dbType) {
        switch (dbType) {
            case POSTGRES:
                return new PostgresFactory();
            case MONGO:
                return new MongoFactory();
            default:
                throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
    }

    public static void main(String[] args) {
        RepositoryFactory selectedFactory = bootstrapFactory(CURRENT_DB);
        UserService userService = new UserService(selectedFactory);

        System.out.println("--- Application Started (Mode: " + CURRENT_DB + ") ---");
        userService.getUserProfile(1);
    }
}
