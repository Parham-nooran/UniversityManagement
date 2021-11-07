package ir.ac.kntu.universityManagement;

import ir.ac.kntu.universityManagement.controllers.LoginController;
import ir.ac.kntu.universityManagement.controllers.StudentController;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversityManagementApplication {
	public static void main(String[] args) {
		Application.launch(LoginController.class);
	}
}
