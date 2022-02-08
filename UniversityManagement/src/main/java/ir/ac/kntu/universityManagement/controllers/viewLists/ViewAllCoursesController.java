package ir.ac.kntu.universityManagement.controllers.viewLists;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.managers.CourseManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.CourseController;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.scheduling.Schedule;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

@NoArgsConstructor
public class ViewAllCoursesController extends CourseController implements Initializable {

    //----------------------------------------------------------------Constructor
    public ViewAllCoursesController(Scene scene){super("ViewAllCourses" ,scene);}
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

    //----------------------------------------------------------------Table view of Prerequisites
    @FXML private TableView<Course> prerequisitesTableView;
    @FXML private TableColumn<Course, String> prerequisitesCourseId;
    @FXML private TableColumn<Course, String> prerequisitesCourseName;
    @FXML private ComboBox<String> prerequisitesSearchBy;
    @FXML private TextField prerequisitesSearchBox;
    //----------------------------------------------------------------

    //----------------------------------------------------------------Table view of Corequisites
    @FXML private TableView<Course> corequisitesTableView;
    @FXML private TableColumn<Course, String> corequisitesCourseId;
    @FXML private TableColumn<Course, String> corequisitesCourseName;
    @FXML private ComboBox<String> corequisitesSearchBy;
    @FXML private TextField corequisitesSearchBox;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToCourseManagement;
    @FXML private Button goToEditCourse;
    @FXML private Button goToDeleteCourse;
    @FXML private Button gotoViewAllCourses;
    @FXML private Button selected;
    @FXML private Button refreshTablesButton;

    @FXML Label prerequisitesLabel;
    @FXML Label corequisitesLabel;

    @FXML private Label courseManagementHeader;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static boolean isUpTODate = true;
    public static Course course;
    private ObservableList<Course> prerequisites;
    private ObservableList<Course> corequisites;
    //----------------------------------------------------------------


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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


        prerequisites = FXCollections.observableArrayList();
        corequisites = FXCollections.observableArrayList();

        prerequisitesTableView.setItems(prerequisites);
        corequisitesTableView.setItems(corequisites);
        //---------------------------------------------
        courseTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                course = courseTableView.getSelectionModel().getSelectedItem();
                prerequisites.clear();
                corequisites.clear();
                prerequisites.addAll(course.getPrerequisites());
                corequisites.addAll(course.getCorequisites());
            }
        });
        fillRequisitesSearch(prerequisitesSearchBox, prerequisitesSearchBy, prerequisitesTableView,
                new ArrayList<>(prerequisitesTableView.getItems()));
        fillRequisitesSearch(corequisitesSearchBox, corequisitesSearchBy, corequisitesTableView,
                new ArrayList<>(corequisitesTableView.getItems()));
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

        fxmlID.add("prerequisitesSearchBy");
        fxmlID.add("prerequisitesCourseId");
        fxmlID.add("prerequisitesCourseName");

        fxmlID.add("corequisitesSearchBy");
        fxmlID.add("corequisitesCourseId");
        fxmlID.add("corequisitesCourseName");

        fxmlID.add("prerequisitesLabel");
        fxmlID.add("corequisitesLabel");

        fxmlID.add("courseManagementHeader");
        fxmlID.add("universityManagementHeader");


        new LanguageConfiguration(fxmlID,text,"CourseManagement");

        goBackToHomePage.setText(text.get(0));
        goToCourseManagement.setText(text.get(1));
        goToEditCourse.setText(text.get(2));
        goToDeleteCourse.setText(text.get(3));
        selected.setText(text.get(4));
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

        prerequisitesSearchBy.setPromptText(text.get(17));
        prerequisitesCourseId.setText(text.get(18));
        prerequisitesCourseName.setText(text.get(19));

        corequisitesSearchBy.setPromptText(text.get(20));
        corequisitesCourseId.setText(text.get(21));
        corequisitesCourseName.setText(text.get(22));

        prerequisitesLabel.setText(text.get(23));
        corequisitesLabel.setText(text.get(24));

        courseManagementHeader.setText(text.get(25));
        universityManagementHeader.setText(text.get(26));
    }
}


