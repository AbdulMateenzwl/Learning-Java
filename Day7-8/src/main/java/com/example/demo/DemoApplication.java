package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		ParentChildService service = new ParentChildService();

		// Create and store a parent with children
		service.createAndStoreParentWithChildren("Parent 1", List.of("Child 1", "Child 2", "Child 3"));

		// Retrieve and display all parents
		System.out.println("All Parents:");
		service.getAllParents().forEach(parent -> {
			System.out.println("Parent Name: " + parent.getName());
			System.out.println("Children:");
			parent.getChildren().forEach(child -> System.out.println(" - " + child.getName()));
		});

		// Retrieve and display children by parent ID
		System.out.println("Children of Parent with ID 1:");
		service.getChildrenByParentId(1L).forEach(child -> System.out.println(child.getName()));
	}

}
