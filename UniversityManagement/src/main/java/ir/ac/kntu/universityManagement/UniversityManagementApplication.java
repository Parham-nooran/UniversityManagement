package ir.ac.kntu.universityManagement;


import ir.ac.kntu.universityManagement.controllers.LoginSmartPart;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class UniversityManagementApplication {


	public static void main(String[] args) {
		Application.launch(LoginSmartPart.class);
	}


}
