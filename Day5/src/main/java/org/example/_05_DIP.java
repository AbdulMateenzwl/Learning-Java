package org.example;

interface MessageSender {
    void sendMessage(String message);
}

class EmailSender implements MessageSender {
    public void sendMessage(String message) {
        System.out.println("Sending Email: " + message);
    }
}

class SMSSender implements MessageSender {
    public void sendMessage(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class NotificationService {
    private MessageSender sender;

    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String message) {
        sender.sendMessage(message);
    }
}

// This is a simple example of the Dependency Inversion Principle (DIP).
// The NotificationService class depends on the MessageSender interface, not on concrete implementations.
// This allows us to easily switch between different message sending implementations (like EmailSender and SMSSender) without modifying the NotificationService class.
// The main method demonstrates how to use these classes.
// The Dependency Inversion Principle (DIP) states that high-level modules should not depend on low-level modules. Both should depend on abstractions.
// Additionally, abstractions should not depend on details. Details should depend on abstractions.
public class _05_DIP {
    public static void main(String[] args) {
        MessageSender email = new EmailSender();
        NotificationService emailService = new NotificationService(email);
        emailService.notifyUser("Hello via Email!");

        MessageSender sms = new SMSSender();
        NotificationService smsService = new NotificationService(sms);
        smsService.notifyUser("Hello via SMS!");
    }
}
