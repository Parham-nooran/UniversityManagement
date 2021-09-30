package ir.ac.kntu.libraryManagement;


import ir.ac.kntu.libraryManagement.controllers.StudentSmartPart;
import ir.ac.kntu.libraryManagement.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class LibraryManagementApplication {


	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}



}
