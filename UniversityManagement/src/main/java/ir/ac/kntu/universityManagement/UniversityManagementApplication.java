package ir.ac.kntu.universityManagement;


import ir.ac.kntu.universityManagement.controllers.LoginSmartPart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class UniversityManagementApplication {


	public static void main(String[] args) {
		new Thread(() -> LoginSmartPart.main(new String[0])).start();
		SpringApplication.run(UniversityManagementApplication.class, args);
	}


}
