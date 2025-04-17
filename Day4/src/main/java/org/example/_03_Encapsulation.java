package org.example;

class Student {
    // can not be accessed directly from outside the class
    private String name;
    private int age;
    private String rollNumber;

    // Default access modifier
    // Can be accessed by classes in the same package
    String value;

    // Protected Method or Field
    // Can be accessed by subclasses and classes in the same package
    protected String getStudentInfo() {
        return "Name: " + name + ", Age: " + age + ", Roll Number: " + rollNumber;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getRollNumber() {
        return rollNumber;
    }
}

public class _03_Encapsulation {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("Mateen");
        student.setAge(20);
        student.setRollNumber("2021-CS-190");

        // Accessing private field directly will cause an error
        // System.out.println(student.name); // Error: name has private access in Student
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Roll Number: " + student.getRollNumber());

        // Accessing protected method
        System.out.println(student.getStudentInfo());

        // Accessing default field
        student.value = "Default Value";
    }
}