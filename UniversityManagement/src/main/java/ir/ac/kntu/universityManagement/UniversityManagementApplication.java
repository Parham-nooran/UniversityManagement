package ir.ac.kntu.universityManagement;

import ir.ac.kntu.universityManagement.controllers.LoginController;
AdelMirsharji
import ir.ac.kntu.universityManagement.controllers.StudentController;
=======
master
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversityManagementApplication {
	public static void main(String[] args) {
		Application.launch(LoginController.class);
	}
}
