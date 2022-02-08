package ir.ac.kntu.universityManagement.configuration;

import ir.ac.kntu.universityManagement.auth.modules._LoginModule;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.general.PersonalInfoController;
import ir.ac.kntu.universityManagement.controllers.managers.*;
import ir.ac.kntu.universityManagement.models.repositories.*;
import ir.ac.kntu.universityManagement.models.repositories.calendar.CalendarEntryRepository;
import ir.ac.kntu.universityManagement.models.repositories.individuals.InstructorRepository;
import ir.ac.kntu.universityManagement.models.repositories.individuals.ManagerRepository;
import ir.ac.kntu.universityManagement.models.repositories.individuals.StudentRepository;
import ir.ac.kntu.universityManagement.models.repositories.universityEntites.CourseRepository;
import ir.ac.kntu.universityManagement.models.repositories.universityEntites.FacultyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration{

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, InstructorRepository instructorRepository,
                                        UserInfoRepository userInfoRepository, CourseRepository courseRepository,
                                        FacultyRepository facultyRepository, ManagerRepository managerRepository,
                                        CalendarEntryRepository calendarEntryRepository,
                                        ApplicationContext applicationContext){
        return args -> {
            _LoginModule.userInfoRepository = userInfoRepository;

            FacultyManagementController.facultyRepository = facultyRepository;
            UserManagementController.userInfoRepository = userInfoRepository;
            StudentManagementController.studentRepository = studentRepository;
            CourseManagementController.courseRepository = courseRepository;
            InstructorManagementController.instructorRepository = instructorRepository;
            UniversityCalendarController.calendarEntryRepository = calendarEntryRepository;

            HomePageController.applicationContext = applicationContext;
            PersonalInfoController.managerRepository = managerRepository;
        };
    }

}
