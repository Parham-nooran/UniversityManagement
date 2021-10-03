package ir.ac.kntu.universityManagement.configuration;

import ir.ac.kntu.universityManagement.controllers.StudentSmartPart;
import ir.ac.kntu.universityManagement.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        StudentSmartPart.studentRepository = studentRepository;
        return args -> StudentSmartPart.main(new String[0]);
    }
}
