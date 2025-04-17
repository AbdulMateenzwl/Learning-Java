# Learning Java

Welcome to the **Learning Java** repository! This repository is structured to help you learn Java step by step, with practical examples and exercises.

## Structure

The repository is organized into multiple folders, each representing a day of learning:

- **Day1/**: Introduction to Java basics, including Hello World, classes, methods, and loops.
- **Day2/**: Understanding primitive data types, variable types, modifiers, exception handling, and more.
- **Day3/**: Exploring collections like List and Map, and working with Streams.
- **Day4/**: Diving into Object-Oriented Programming concepts such as Polymorphism, Inheritance, Encapsulation, and Abstraction.
- **Project-Day_1-4/**: A consolidated project to apply the concepts learned in Days 1-4.

Each folder contains:
- `pom.xml`: Maven configuration file.
- `src/`: Source code organized into `main` and `test` directories.
- `target/`: Compiled classes and other build artifacts.

## Resources

### Book
This learning journey is based on the book [**Head First Java**](https://github.com/AbdulMateenzwl/Books/blob/master/Languages/Head%20First%20Java%203rd%20Edition%20by%20Sierra%2C%20Kathy%20%20Bates%2C%20Bert%20%20Gee%2C%20Trisha.pdf) by Kathy Sierra and Bert Bates. It is an excellent resource for beginners to understand Java concepts in an engaging and interactive way.

### Additional Materials
- `Java Roadmap (Compact).pdf`: A compact roadmap to guide your Java learning process.

## How to Use

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the desired day's folder to explore the examples and exercises.
3. Use Maven to build and run the projects:
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="org.example.MainClass"
   ```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven
- An IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code

## Contributing

Feel free to contribute by adding more examples, improving documentation, or suggesting new topics to cover. Fork the repository and create a pull request with your changes.

## License

This repository is for educational purposes only. Please ensure you have the necessary permissions to use the referenced book and materials.