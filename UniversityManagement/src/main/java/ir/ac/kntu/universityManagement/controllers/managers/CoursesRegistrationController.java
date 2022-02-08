package ir.ac.kntu.universityManagement.controllers.managers;

import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.CourseManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.TakeCourseController;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Grade;
import ir.ac.kntu.universityManagement.models.repositories.universityEntites.GradeRepository;
import ir.ac.kntu.universityManagement.models.scheduling.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

@NoArgsConstructor
public class CoursesRegistrationController extends TakeCourseController implements Initializable {

    public static GradeRepository gradeRepository;
    public static ObservableList<Grade> grades;
    public static boolean isUpTODate = true;

    //----------------------------------------------------------------Constructor
    public CoursesRegistrationController(Scene scene) {
        super("CoursesRegistration", scene);
    }
    //----------------------------------------------------------------

    //-------------------------------------------------------------
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem logOut;
    @FXML
    private Button goBackToHomePage;
    @FXML
    private Button goToAddCourse;
    @FXML
    private Button goToViewCourses;
    //--------------------------------------------------------------

    @FXML
    private TableView<Course> courseTableView;
    @FXML
    private TableColumn<Course, String> courseCourseId;
    @FXML
    private TableColumn<Course, String> courseCourseName;
    @FXML
    private TableColumn<Course, Instructor> courseInstructor;
    @FXML
    private TableColumn<Course, Schedule> courseSchedule;
    @FXML
    private TableColumn<Course, LocalDate> courseFinalExam;
    @FXML
    private TableColumn<Course, Faculty> courseFaculty;
    @FXML
    private TableColumn<Course, Integer> courseCapacity;
    @FXML
    private TableColumn<Course, Integer> enListed;
    @FXML
    private TableColumn<Course, Integer> courseCredit;

    //-----------------------------

    @FXML
    private TableView<Course> takenCourseTableView;
    @FXML
    private TableColumn<Grade, Course> takenCourseCourseId;
    @FXML
    private TableColumn<Grade, Instructor> takenCourseName;
    @FXML
    private TableColumn<Grade, Faculty> takenCourseSchedule;
    @FXML
    private TableColumn<Course, LocalDate> takenCourseFinalExam;
    @FXML
    private ComboBox<String> takenCourseSearchBy;
    @FXML
    private TextField takenCourseSearchBox;

    //-----------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillCourseTableView(courseCourseId, courseCourseName, courseCapacity, courseSchedule
                , courseInstructor, courseFinalExam, courseFaculty, courseCredit, enListed);

        courseCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        refreshCourse(CourseManagementController.courses, courseTableView);
        takenCourseCourseId.setCellValueFactory(new PropertyValueFactory<>("course"));
        courseCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        courseSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        courseInstructor.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        courseFinalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        courseFaculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        courseCredit.setCellValueFactory(new PropertyValueFactory<>("units"));
        enListed.setCellValueFactory(new PropertyValueFactory<>("enListed"));
        //fillCourseTableView(takenCourseCourseId, takenCourseName, takenCourseSchedule, takenCourseFinalExam);
        fillUserInterface(goBackToHomePage, menuButton, logOut);
    }

    public static void refillCourses(){
        Thread thread = new Thread(() -> {
            CourseManagementController.courses = FXCollections.observableArrayList(CourseManagementController.courseRepository.findAll());
            CourseManagementController.isUpTODate = true;
        });
        thread.setPriority(7);
        thread.start();
    }

    public void add(MouseEvent mouseEvent) {

        UserInfo userInfo = HomePageController.user;

        List<Student> students = StudentManagementController.studentRepository.findByUserInfoId(userInfo.getId());

        if(students.size() == 1){
            HashSet<Grade> gradeHashSet = new HashSet<>();
            Course registeredCourse = courseTableView.getSelectionModel().getSelectedItem();
            gradeHashSet.add(new Grade(registeredCourse, students.get(0), null));
            students.get(0).setGradeSet(gradeHashSet);
            takenCourseTableView.getItems().add(registeredCourse);
            //CourseManagementController.courses.stream().filter(course -> course.getInstructor().getId().
            // equals(instructor.getId())).collect(Collectors.toList());

        }


    }
}
