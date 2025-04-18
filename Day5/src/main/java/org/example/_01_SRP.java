package org.example;

class User implements IUser {
    @Override
    public boolean Login(String username, String password) {
        // Implement login logic here
        return true;
    }

    @Override
    public boolean Register(String user, String password, String email) {
        // Implement registration logic here
        return true;
    }
}

class Logger implements ILogger {
    @Override
    public void LogError(String error) {
        // Implement error logging logic here
        System.out.println("Error: " + error);
    }
}

class Email implements IEmail {
    @Override
    public void SendEmail(String email, String subject, String body) {
        // Implement email sending logic here
        System.out.println("Sending email to " + email + " with subject: " + subject);
    }
}

interface IUser {
    boolean Login(String username, String password);

    boolean Register(String user, String password, String email);
}

interface ILogger {
    void LogError(String error);
}

interface IEmail {
    void SendEmail(String email, String subject, String body);
}
// This is an example of the Single Responsibility Principle (SRP) in Java.
// This makes the code more maintainable and easier to understand.
// The main method demonstrates how to use these classes.
// The User class implements the IUser interface, which defines methods for user operations.
// The Logger class implements the ILogger interface, which defines methods for logging.
// The Email class implements the IEmail interface, which defines methods for email operations.
// This separation of concerns allows for better organization and flexibility in the codebase.

public class _01_SRP {
    public static void main(String[] args) {
        User user = new User();
        Logger logger = new Logger();
        Email email = new Email();
        // Example usage
        user.Login("testUser", "testPassword");
        logger.LogError("This is a test error");
        email.SendEmail("mail@mail.com", "body", "Email");
    }
}