package ir.ac.kntu.universityManagement.controllers.deleters;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.CourseController;
import ir.ac.kntu.universityManagement.controllers.managers.CourseManagementController;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.scheduling.Schedule;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;


@NoArgsConstructor
@Setter
@Getter
public class DeleteCourseController extends CourseController implements Initializable {

    //----------------------------------------------------------------Constructor
    public DeleteCourseController(Scene scene) {
        super("DeleteCourse", scene);
    }
    //----------------------------------------------------------------


    //----------------------------------------------------------------Table view of Courses
    @FXML private TableView<Course> courseTableView;
    @FXML private TableColumn<Course, String> courseId;
    @FXML private TableColumn<Course, String> courseName;
    @FXML private TableColumn<Course, Integer> courseCapacity;
    @FXML private TableColumn<Course, Instructor> courseInstructor;
    @FXML private TableColumn<Course, String> courseSemester;
    @FXML private TableColumn<Course, LocalDate> courseStartDate;
    @FXML private TableColumn<Course, LocalDate> courseFinalExam;
    @FXML private TableColumn<Course, Integer> courseUnits;
    @FXML private TableColumn<Course, Set<Schedule>> courseSchedules;
    @FXML private TableColumn<Course, Faculty> courseFaculty;
    @FXML private TextField courseSearchBox;
    @FXML private ComboBox<String> courseSearchBy;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToCourseManagement;
    @FXML private Button goToEditCourse;
    @FXML private Button goToDeleteCourse;
    @FXML private Button selected;
    @FXML private Button gotoViewAllCourses;

    @FXML private Button refreshTablesButton;


    @FXML Label youSureLabel;
    @FXML Button yes;
    @FXML Button no;
    @FXML Button delete;

    @FXML private Label courseManagementHeader;
    @FXML private AnchorPane areYouSure;

    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static boolean isUpTODate = true;
    public static Course course;
    //----------------------------------------------------------------



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillCourseTableView(courseId, courseName, courseCapacity, courseSchedules, courseInstructor, courseUnits, courseSemester, courseStartDate, courseFinalExam);
        fillCourseSearch(courseSearchBox, courseSearchBy, courseTableView);
        fillUserInterface(goBackToHomePage, goToCourseManagement, goToEditCourse, goToDeleteCourse, gotoViewAllCourses,
                menuButton, logOut);
        refreshCourse(CourseManagementController.courses, courseTableView);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------

        courseTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                course = courseTableView.getSelectionModel().getSelectedItem();
            }
        });
    }

    public void deleteCourse() {
        new Thread(() -> CourseManagementController.courseRepository.deleteById(course.getId())).start();
        CourseManagementController.courses.remove(course);
        areYouSure.setVisible(false);
    }

    public void delete(){
        if(course != null) {
            areYouSure.setVisible(true);
        }
    }

    public void no(){
        areYouSure.setVisible(false);
    }

    public void setRefreshTables(){
        refreshTables(courseTableView,null,null,null,null);
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){
        fxmlID.add("homePageButton");
        fxmlID.add("addCourseButton");
        fxmlID.add("editCourseButton");
        fxmlID.add("deleteCourseButton");
        fxmlID.add("ViewAllCoursesButton");
        fxmlID.add("refreshTablesButton");

        fxmlID.add("courseSearchBy");
        fxmlID.add("courseId");
        fxmlID.add("courseName");
        fxmlID.add("courseCapacity");
        fxmlID.add("courseInstructor");
        fxmlID.add("courseSemester");
        fxmlID.add("courseStartDate");
        fxmlID.add("courseFinalExam");
        fxmlID.add("courseUnits");
        fxmlID.add("courseSchedules");
        fxmlID.add("courseFaculty");

        fxmlID.add("youSureLabel");
        fxmlID.add("yes");
        fxmlID.add("no");
        fxmlID.add("delete");

        fxmlID.add("courseManagementHeader");
        fxmlID.add("universityManagementHeader");



        new LanguageConfiguration(fxmlID,text,"CourseManagement");

        goBackToHomePage.setText(text.get(0));
        goToCourseManagement.setText(text.get(1));
        goToEditCourse.setText(text.get(2));
        selected.setText(text.get(3));
        gotoViewAllCourses.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));

        courseSearchBy.setPromptText(text.get(6));
        courseId.setText(text.get(7));
        courseName.setText(text.get(8));
        courseCapacity.setText(text.get(9));
        courseInstructor.setText(text.get(10));
        courseSemester.setText(text.get(11));
        courseStartDate.setText(text.get(12));
        courseFinalExam.setText(text.get(13));
        courseUnits.setText(text.get(14));
        courseSchedules.setText(text.get(15));
        courseFaculty.setText(text.get(16));


        youSureLabel.setText(text.get(17));
        yes.setText(text.get(18));
        no.setText(text.get(19));
        delete.setText(text.get(20));

        courseManagementHeader.setText(text.get(21));
        universityManagementHeader.setText(text.get(22));

    }
}
