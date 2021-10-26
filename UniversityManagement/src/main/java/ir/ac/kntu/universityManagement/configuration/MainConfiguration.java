package ir.ac.kntu.universityManagement.configuration;

import ir.ac.kntu.universityManagement.controllers.InstructorController;
import ir.ac.kntu.universityManagement.controllers.StudentController;
import ir.ac.kntu.universityManagement.controllers.StudentsController;
import ir.ac.kntu.universityManagement.models.repositories.InstructorRepository;
import ir.ac.kntu.universityManagement.models.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, InstructorRepository instructorRepository){
        return args -> {
            StudentController.studentRepository = studentRepository;
            InstructorController.instructorRepository = instructorRepository;
            StudentsController.studentRepository = studentRepository;
        };
    }

}
