package ir.ac.kntu.universityManagement.configuration;

import ir.ac.kntu.universityManagement.controllers.InstructorSmartPart;
import ir.ac.kntu.universityManagement.controllers.StudentSmartPart;
import ir.ac.kntu.universityManagement.repositories.InstructorRepository;
import ir.ac.kntu.universityManagement.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, InstructorRepository instructorRepository){
        return args -> {
            StudentSmartPart.studentRepository = studentRepository;
            InstructorSmartPart.instructorRepository = instructorRepository;
        };
    }

}
