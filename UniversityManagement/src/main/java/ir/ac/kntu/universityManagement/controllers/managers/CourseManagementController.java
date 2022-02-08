package ir.ac.kntu.universityManagement.controllers.managers;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.CourseController;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Semester;
import ir.ac.kntu.universityManagement.models.repositories.universityEntites.CourseRepository;
import ir.ac.kntu.universityManagement.models.scheduling.Schedule;
import ir.ac.kntu.universityManagement.models.scheduling.TimeInterval;
import ir.ac.kntu.universityManagement.models.scheduling.WeekDay;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lombok.NoArgsConstructor;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class CourseManagementController extends CourseController implements Initializable {

    //----------------------------------------------------------------Constructor
    public CourseManagementController(Scene scene) {super("CourseManagement", scene);}
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


    //----------------------------------------------------------------Table view of Instructors
    @FXML private TableView<Instructor> instructorTableView;
    @FXML private TableColumn<Instructor, String> instructorId;
    @FXML private TableColumn<Instructor, String> instructorFirstName;
    @FXML private TableColumn<Instructor, String> instructorLastName;
    @FXML private TextField instructorTableSearchBox;
    @FXML private ComboBox<String> instructorTableSearchBy;
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


    //----------------------------------------------------------------Adding instructor field attributes
    @FXML private TextField addName;
    @FXML private TextField addCapacity;
    @FXML private TextField addInstructorId;
    @FXML private DatePicker addFinalExam;
    @FXML private AnchorPane addLoginError;
    @FXML private TextArea addErrorMessage;
    @FXML private ComboBox<String> addUnits;
    @FXML private ComboBox<String> addDayOne;
    @FXML private ComboBox<String> addDayTwo;
    @FXML private ComboBox<String> addTimeInterval;
    @FXML private DatePicker addStartDate;
    @FXML private ComboBox<String> addSemester;
    @FXML private TextField addFaculty;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToCourseManagement;
    @FXML private Button selected;
    @FXML private Button goToEditCourse;
    @FXML private Button goToDeleteCourse;
    @FXML private Button gotoViewAllCourses;
    @FXML private Button refreshTablesButton;

    @FXML private Label addCourseHeaderLabel;
    @FXML private Label addNameLabel;
    @FXML private Label addCapacityLabel;
    @FXML private Label addInstructorIdLabel;
    @FXML private Label addUnitsLabel;
    @FXML private Label addScheduleLabel;
    @FXML private Label addDayOneLabel;
    @FXML private Label addDayTwoLabel;
    @FXML private Label addTimeIntervalLabel;
    @FXML private Label addFacultyLabel;
    @FXML private Label addStartDateLabel;
    @FXML private Label addFinalExamLabel;
    @FXML private Label addSemesterLabel;

    @FXML private Button save;
    @FXML private Button addToPrerequisites;
    @FXML private Button addToCorequisites;
    @FXML private Button deletePrerequisites;
    @FXML private Button deleteCorequisites;

    @FXML private Label courseManagementHeader;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static CourseRepository courseRepository;
    public static ObservableList<Course> courses;
    private ObservableList<Course> prerequisites;
    private ObservableList<Course> corequisites;

    public static boolean isUpTODate = true;
    //----------------------------------------------------------------


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillCourseTableView(courseId, courseName, courseCapacity, courseSchedules, courseInstructor, courseUnits,
                courseSemester, courseStartDate, courseFinalExam);
        refreshCourse(CourseManagementController.courses, courseTableView);

        fillInstructorTableView(instructorId, instructorFirstName, instructorLastName);
        refreshInstructors(InstructorManagementController.instructors, instructorTableView);

        fillCourseSearch(courseSearchBox, courseSearchBy, courseTableView);
        fillInstructorSearch(instructorTableSearchBox, instructorTableSearchBy, instructorTableView);

        fillUserInterface(goBackToHomePage, goToCourseManagement, goToEditCourse, goToDeleteCourse, gotoViewAllCourses,
                menuButton, logOut);

        fillScheduling(addDayOne, addDayTwo, addTimeInterval, addUnits);
        changeEditable(false, addDayOne, addDayTwo, addTimeInterval);

        fillRequisitesTableView(prerequisitesCourseId, prerequisitesCourseName);
        fillRequisitesTableView(corequisitesCourseId, corequisitesCourseName);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------

        prerequisites = FXCollections.observableArrayList();
        corequisites = FXCollections.observableArrayList();

        prerequisitesTableView.setItems(prerequisites);
        corequisitesTableView.setItems(corequisites);

        addSemester.getItems().addAll(Semester.getValues());
        changeEditable(true, addTimeInterval, addDayOne, addDayTwo);

        addUnits.setOnAction((ActionEvent) -> {
            if(addUnits.getValue() != null) {
                changeEditable(false, addTimeInterval, addDayOne);
                changeEditable(Integer.parseInt(addUnits.getValue()) < 3, addDayTwo);
            } else{
                changeEditable(true, addDayOne, addDayTwo, addTimeInterval);
            }
        });
        fillRequisitesSearch(prerequisitesSearchBox, prerequisitesSearchBy, prerequisitesTableView,
                new ArrayList<>((prerequisitesTableView.getItems())));
        fillRequisitesSearch(corequisitesSearchBox, corequisitesSearchBy, corequisitesTableView,
                new ArrayList<>(corequisitesTableView.getItems()));

    }




    public static void refillCourses(){
        Thread thread = new Thread(() -> {
            courses = FXCollections.observableArrayList(courseRepository.findAll());
            isUpTODate = true;
        });
        thread.setPriority(7);
        thread.start();
    }

    public void save() throws Exception {
        List<Instructor> instructors = InstructorManagementController.instructors.stream().
                filter(instructor -> instructor.getInstructorId() != null &&
                        instructor.getInstructorId().equals(addInstructorId.getText().trim())).
                collect(Collectors.toList());
        List<Faculty> foundFaculties = FacultyManagementController.faculties.stream().
                filter(faculty -> faculty.getName() != null &&
                        faculty.getName().equals(addFaculty.getText().trim())).collect(Collectors.toList());
        if(addName.getText().isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Course name can not be empty!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام درس نمی تواند خالی باشد!");
            }
        } else if (addCapacity.getText().isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Course capacity can not be empty!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("ظرفیت درس نمی تواند خالی باشد!");
            }
        } else if(isNotNumeric(addCapacity.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Capacity value is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("ظرفیت درس قابل قبول نیست!");
            }
        } else if (addInstructorId.getText().isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Course instructor ID can not be empty!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("آی دی استاد درس نمی تواند خالی باشد!");
            }
        } else if (instructors.isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("There is no instructor with this ID!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage(" استاد با این آی دی وجود ندارد!");
            }
        } else if (instructors.size() > 1){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("There is more than one instructor with this ID!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("بیش از یک استاد با این آی دی وجود دارد!");
            }
        } else if (addUnits.getValue() == null) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Course units can not be empty!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("تعداد واحد نمی تواند خالی باشد!");
            }
        } else if (addFaculty.getText().isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Course faculty can not be empty!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("دانشکده نمی تواند خالی باشد!");
            }
        } else if (foundFaculties.isEmpty()) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("There is no faculty with this name!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("دانشکده ایی با این نام وجود ندارد!");
            }
        } else if (foundFaculties.size() > 1) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("There is more than one\n faculty with this name!\nPlease inform the admin!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("بیش از یک دانشکده با این نام وجود دارد!");
            }
        } else {
            Course course = new Course(addName.getText(),  Integer.parseInt(addCapacity.getText()),
                    makeSchedules(addDayOne, addDayTwo, addUnits, addTimeInterval), instructors.get(0),
                    Integer.parseInt(addUnits.getValue()), addSemester.getValue() != null ?
                    Semester.valueOf("_"+addSemester.getValue().toUpperCase()) : null,
                    addStartDate.getValue(), new HashSet<>(prerequisites), new HashSet<>(corequisites),
                    foundFaculties.get(0), addFinalExam.getValue());
            new Thread(() -> CourseManagementController.courseRepository.save(course)).start();
            CourseManagementController.courses.add(course);
            clear();
            new CourseManagementController(courseTableView.getScene()).start();
            Image successNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/successNot.png"))));
            if (BaseController.getLanguage().equals("English")) {
                Notifications notification = Notifications.create()
                        .title("Successfully Edited ! ")
                        .text("You can see it now !!!!")
                        .graphic(new ImageView(successNot))
                        .hideAfter(Duration.seconds(10))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            } else if (BaseController.getLanguage().equals("Persian")) {
                Notifications notification = Notifications.create()
                        .title("با موفقیت ثبت شد")
                        .text("می توانید تغییرات را ببینید!!!!")
                        .graphic(new ImageView(successNot))
                        .hideAfter(Duration.seconds(10))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        }
    }
    public Set<Schedule> makeSchedules(ComboBox<String> dayOne, ComboBox<String> dayTwo, ComboBox<String> units,
                                       ComboBox<String> timeInterval){
        Set<Schedule> schedules = new HashSet<>();
        if(dayOne.getValue() != null && dayTwo.getValue() != null && dayOne.getValue().equals(dayTwo.getValue())){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Schedules could not be on the same day");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("زمان بندی ها نمی توانند در یک روز باشند!");
            }
        }
        if(dayOne.getValue() != null) {
            schedules.add(new Schedule(TimeInterval.getValueOf(timeInterval.getValue()),
                    WeekDay.valueOf(dayOne.getValue().toUpperCase())));
        }
        if(dayTwo.getValue() != null && Integer.parseInt(units.getValue()) > 2) {
            schedules.add(new Schedule(TimeInterval.getValueOf(timeInterval.getValue().toUpperCase()),
                    WeekDay.valueOf(dayTwo.getValue().toUpperCase())));
        }
        return schedules;
    }


    public void addToPrerequisites() {
        if(courseTableView.getSelectionModel().getSelectedItem() != null) {
            prerequisites.add(courseTableView.getSelectionModel().getSelectedItem());
            HashSet<Course> temp = new HashSet<>(prerequisites);
            prerequisites.clear();
            prerequisites.addAll(temp);
        }
    }

    public void addToCorequisites() {
        if(courseTableView.getSelectionModel().getSelectedItem() != null) {
            corequisites.add(courseTableView.getSelectionModel().getSelectedItem());
            HashSet<Course> temp = new HashSet<>(corequisites);
            corequisites.clear();
            corequisites.addAll(temp);
        }
    }

    public void deleteFromPrerequisites() {
        if(prerequisitesTableView.getSelectionModel().getSelectedItem() != null) {
            prerequisites.remove(prerequisitesTableView.getSelectionModel().getSelectedItem());
        }
    }


    public void deleteFromCorequisites() {
        if(corequisitesTableView.getSelectionModel().getSelectedItem() != null){
            corequisites.remove(corequisitesTableView.getSelectionModel().getSelectedItem());
        }
    }



    private void clear(){
        clearTextFields(addName, addCapacity, addInstructorId);
        clearComboBoxes(addUnits, addDayOne, addDayTwo, addTimeInterval);

        addFinalExam.setValue(null);
    }

    private static final Image ErrorNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/ErrorNot.png"))));
    private void makeErrorMassage(String message){
        Notifications notification = Notifications.create()
                .title(" Error ! ")
                .text(message)
                .graphic(new ImageView(ErrorNot))
                .hideAfter(Duration.seconds(10))
                .position(Pos.CENTER);
        notification.show();
    }

    public void errorOk(){
        addLoginError.setVisible(false);
    }

    public static long getCoursesSize(){
        return courseRepository.count();
    }

    public void setRefreshTables(){
        refreshTables(courseTableView,instructorTableView,null,null,null);
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){
        fxmlID.add("homePageButton");
        fxmlID.add("addCourseButton");
        fxmlID.add("editCourseButton");
        fxmlID.add("deleteCourseButton");
        fxmlID.add("ViewAllCoursesButton");
        fxmlID.add("refreshTablesButton");

        fxmlID.add("courseSearchBy");
        fxmlID.add("instructorTableSearchBy");
        fxmlID.add("prerequisitesSearchBy");
        fxmlID.add("corequisitesSearchBy");

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

        fxmlID.add("instructorId");
        fxmlID.add("instructorFirstName");
        fxmlID.add("instructorLastName");

        fxmlID.add("prerequisitesCourseId");
        fxmlID.add("prerequisitesCourseName");

        fxmlID.add("corequisitesCourseId");
        fxmlID.add("corequisitesCourseName");

        fxmlID.add("addCourseHeaderLabel");
        fxmlID.add("addNameLabel");
        fxmlID.add("addCapacityLabel");
        fxmlID.add("addInstructorIdLabel");
        fxmlID.add("addUnitsLabel");
        fxmlID.add("addScheduleLabel");
        fxmlID.add("addDayOneLabel");
        fxmlID.add("addDayTwoLabel");
        fxmlID.add("addTimeIntervalLabel");
        fxmlID.add("addFacultyLabel");
        fxmlID.add("addStartDateLabel");
        fxmlID.add("addFinalExamLabel");
        fxmlID.add("addSemesterLabel");

        fxmlID.add("save");
        fxmlID.add("addToPrerequisites");
        fxmlID.add("addToCorequisites");
        fxmlID.add("deletePrerequisites");
        fxmlID.add("deleteCorequisites");

        fxmlID.add("courseManagementHeader");
        fxmlID.add("universityManagementHeader");






        new LanguageConfiguration(fxmlID,text,"CourseManagement");

        goBackToHomePage.setText(text.get(0));
        selected.setText(text.get(1));
        goToEditCourse.setText(text.get(2));
        goToDeleteCourse.setText(text.get(3));
        gotoViewAllCourses.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));

        courseSearchBy.setPromptText(text.get(6));
        instructorTableSearchBy.setPromptText(text.get(7));
        prerequisitesSearchBy.setPromptText(text.get(8));
        corequisitesSearchBy.setPromptText(text.get(9));


        courseId.setText(text.get(10));
        courseName.setText(text.get(11));
        courseCapacity.setText(text.get(12));
        courseInstructor.setText(text.get(13));
        courseSemester.setText(text.get(14));
        courseStartDate.setText(text.get(15));
        courseFinalExam.setText(text.get(16));
        courseUnits.setText(text.get(17));
        courseSchedules.setText(text.get(18));
        courseFaculty.setText(text.get(19));

        instructorId.setText(text.get(20));
        instructorFirstName.setText(text.get(21));
        instructorLastName.setText(text.get(22));

        prerequisitesCourseId.setText(text.get(23));
        prerequisitesCourseName.setText(text.get(24));

        corequisitesCourseId.setText(text.get(25));
        corequisitesCourseName.setText(text.get(26));

        addCourseHeaderLabel.setText((text.get(27)));
        addNameLabel.setText((text.get(28)));
        addCapacityLabel.setText((text.get(29)));
        addInstructorIdLabel.setText((text.get(30)));
        addUnitsLabel.setText(text.get(31));
        addScheduleLabel.setText(text.get(32));
        addDayOneLabel.setText((text.get(33)));
        addDayTwoLabel.setText((text.get(34)));
        addTimeIntervalLabel.setText((text.get(35)));
        addFacultyLabel.setText((text.get(36)));
        addStartDateLabel.setText((text.get(37)));
        addFinalExamLabel.setText((text.get(38)));
        addSemesterLabel.setText((text.get(39)));

        save.setText(text.get(40));
        addToPrerequisites.setText(text.get(41));
        addToCorequisites.setText(text.get(42));
        deletePrerequisites.setText(text.get(43));
        deleteCorequisites.setText(text.get(44));

        courseManagementHeader.setText(text.get(45));
        universityManagementHeader.setText(text.get(46));

    }
}