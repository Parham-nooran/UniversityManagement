package ir.ac.kntu.universityManagement.controllers.editors;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.FacultyManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.CourseController;
import ir.ac.kntu.universityManagement.controllers.managers.CourseManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Semester;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
@Getter
public class EditCourseController extends CourseController implements Initializable {

    //----------------------------------------------------------------Constructor
    public EditCourseController(Scene scene) {super("EditCourse", scene);}
    //----------------------------------------------------------------


    //----------------------------------------------------------------Table view of Courses
    @FXML private TableView<Course> courseTableView;
    @FXML private TableColumn<Course, String> courseId;
    @FXML private TableColumn<Course, String> courseName;
    @FXML private TableColumn<Course, Integer> courseCapacity;
    @FXML private TableColumn<Course, Instructor> courseInstructor;
    @FXML private TableColumn<Course, Integer> courseUnits;
    @FXML private TableColumn<Course, Set<Schedule>> courseSchedules;
    @FXML private TableColumn<Course, String> courseSemester;
    @FXML private TableColumn<Course, Faculty> courseFaculty;
    @FXML private TableColumn<Course, LocalDate> courseStartDate;
    @FXML private TableColumn<Course, LocalDate> courseFinalExam;
    @FXML private ComboBox<String> courseSearchBy;
    @FXML private TextField courseSearchBox;
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


    //----------------------------------------------------------------Edit instructor field attributes
    @FXML private TextField editCourseId;
    @FXML private TextField editName;
    @FXML private TextField editCapacity;
    @FXML private TextField editInstructorId;
    @FXML private DatePicker editFinalExam;
    @FXML private TextArea editErrorMessage;
    @FXML private AnchorPane editLoginError;
    @FXML private ComboBox<String> editUnits;
    @FXML private ComboBox<String> editDayOne;
    @FXML private ComboBox<String> editDayTwo;
    @FXML private ComboBox<String> editTimeInterval;
    @FXML private ComboBox<String> editSemester;
    @FXML private TextField editFaculty;
    @FXML private DatePicker editStartDate;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToCourseManagement;
    @FXML private Button goToEditCourse;
    @FXML private Button selected;
    @FXML private Button goToDeleteCourse;
    @FXML private Button gotoViewAllCourses;
    @FXML private Button refreshTablesButton;

    @FXML private Label editCourseHeaderLabel;
    @FXML private Label editNameLabel;
    @FXML private Label editCapacityLabel;
    @FXML private Label editInstructorIdLabel;
    @FXML private Label editUnitsLabel;
    @FXML private Label editScheduleLabel;
    @FXML private Label editDayOneLabel;
    @FXML private Label editDayTwoLabel;
    @FXML private Label editTimeIntervalLabel;
    @FXML private Label editFacultyLabel;
    @FXML private Label editStartDateLabel;
    @FXML private Label editFinalExamLabel;
    @FXML private Label editSemesterLabel;

    @FXML private Button save;
    @FXML private Button addToPrerequisites;
    @FXML private Button addToCorequisites;
    @FXML private Button deletePrerequisites;
    @FXML private Button deleteCorequisites;


   @FXML private Label editCourseIdLabel;
    @FXML private CheckBox enableChoose2Edit;

    @FXML private Label courseManagementHeader;
    @FXML private Label universityManagementHeader;

    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static Course course;
    private ObservableList<Course> prerequisites;
    private ObservableList<Course> corequisites;

    public static boolean isUpTODate = true;
    //----------------------------------------------------------------


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enableChoose2Edit.setSelected(true);
        fillRequisitesSearch(prerequisitesSearchBox, prerequisitesSearchBy, prerequisitesTableView,
                new ArrayList<>(prerequisitesTableView.getItems()));
        fillRequisitesSearch(corequisitesSearchBox, corequisitesSearchBy, corequisitesTableView,
                new ArrayList<>(corequisitesTableView.getItems()));
        fillCourseTableView(courseId, courseName, courseCapacity, courseSchedules, courseInstructor, courseUnits,
                courseSemester, courseStartDate, courseFinalExam);
        refreshCourse(CourseManagementController.courses, courseTableView);

        fillInstructorTableView(instructorId, instructorFirstName, instructorLastName);
        refreshInstructors(InstructorManagementController.instructors,instructorTableView);

        fillCourseSearch(courseSearchBox, courseSearchBy, courseTableView);
        fillInstructorSearch(instructorTableSearchBox, instructorTableSearchBy,instructorTableView);

        fillUserInterface(goBackToHomePage, goToCourseManagement, goToEditCourse, goToDeleteCourse, gotoViewAllCourses, menuButton, logOut);

        fillScheduling(editDayOne, editDayTwo, editTimeInterval, editUnits);
        changeEditable(false, editDayOne, editDayTwo, editTimeInterval);

        editSemester.getItems().addAll(Semester.getValues());

        fillRequisitesTableView(prerequisitesCourseId, prerequisitesCourseName);
        fillRequisitesTableView(corequisitesCourseId, corequisitesCourseName);

        prerequisites = FXCollections.observableArrayList();
        corequisites = FXCollections.observableArrayList();

        prerequisitesTableView.setItems(prerequisites);
        corequisitesTableView.setItems(corequisites);

        //------------------------------------------Persian
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianLanguageTranslator(fxmlID,text);
        }
        //-----------------------------------------

        changeEditable(true, editTimeInterval, editDayOne, editDayTwo);
        editUnits.setOnAction((ActionEvent) -> {
            if(editUnits.getValue() != null) {
                changeEditable(false, editTimeInterval, editDayOne);
                changeEditable(Integer.parseInt(editUnits.getValue()) < 3, editDayTwo);
            } else{
                changeEditable(true, editDayOne, editDayTwo, editTimeInterval);
            }
        });

        courseTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && enableChoose2Edit.isSelected()) {
                course = courseTableView.getSelectionModel().getSelectedItem();
                editCourseId.setText(course.getCourseId() != null ? course.getCourseId() : "");
                editCapacity.setText(course.getCapacity() != null ? course.getCapacity().toString() :"");
                editName.setText(course.getName() != null ? course.getName() : "");
                editFinalExam.setValue(course.getFinalExam());
                editInstructorId.setText(course.getInstructor() != null ? course.getInstructor().
                        getInstructorId() : "");
                prerequisites.clear();
                corequisites.clear();
                prerequisites.addAll(course.getPrerequisites());
                corequisites.addAll(course.getCorequisites());
                editUnits.setValue(course.getUnits() != null ? course.getUnits().toString() : null);
                editSemester.setValue(course.getSemester() != null ? course.getSemester().toString() : null);
                editStartDate.setValue(course.getStartDate());
                editFaculty.setText(course.getFaculty() != null ? course.getFaculty().toString() : "");
                fillScheduleFields();
            }
        });
    }

    public Boolean hasSchedule(){
        return course.getSchedules() != null && !course.getSchedules().isEmpty();
    }

    public void fillScheduleFields(){
        ArrayList<ComboBox<String>> days = new ArrayList<>(Arrays.asList(editDayOne, editDayTwo));
        int i = 0;
        if(hasSchedule()){
            for (Schedule schedule : course.getSchedules()) {
                editTimeInterval.setValue(schedule.getTimeInterval().toString());
                days.get(i++).setValue(schedule.getWeekDay().toString());
            }
        } else{
            clearComboBoxes(editDayOne, editDayTwo, editTimeInterval);
        }
    }


    public void edit() throws Exception {
        List<Instructor> instructors = InstructorManagementController.instructors.stream().
                filter(instructor -> instructor.getInstructorId() != null &&
                        instructor.getInstructorId().equals(editInstructorId.getText().trim())).
                collect(Collectors.toList());
        List<Faculty> foundFaculties = FacultyManagementController.faculties.stream().
                filter(faculty -> faculty.getName() != null &&
                        faculty.getName().equals(editFaculty.getText().trim())).collect(Collectors.toList());
        if(checkConstraints()) {
            if (instructors.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no instructor with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("استادی با این آی دی موجود نیست!");
                }
            } else if (instructors.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one instructor with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیشتر از یک استاد با این آی دی وجود دارد!");
                }
            } else if (foundFaculties.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no faculty with this name!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("دانشکده ایی بااین اسم وجود ندراد!");
                }
            } else if (foundFaculties.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one faculty with this name! Please inform the admin!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از دو دانشکده با این اسم وجود دارد! لطفا مسئول آموزش را ئر جریان بگذارید!");
                }
            } else {
                setValues();
                course.setFaculty(foundFaculties.get(0));
                course.setInstructor(instructors.get(0));
                new Thread(() -> CourseManagementController.courseRepository.save(course)).start();
                clear();
                new EditCourseController(courseTableView.getScene()).start();
                Image successNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/successNot.png"))));
                if(BaseController.getLanguage().equals("English")){
                    Notifications notification = Notifications.create()
                            .title("Successfully Edited ! ")
                            .text("You can see it now !!!!")
                            .graphic(new ImageView(successNot))
                            .hideAfter(Duration.seconds(10))
                            .position(Pos.BOTTOM_RIGHT);
                    notification.show();
                } else if (BaseController.getLanguage().equals("Persian")){
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
    }

    private Boolean checkConstraints(){
        if(isNotNumeric(editCapacity.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Capacity value is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("ظرفیت وارد شده صحیح نمی باشد!");
            }
        } else if(editUnits.getValue() == null) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Choose number of units!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("لطفا تعداد واحدها را وارد کنید!");
            }
        } else{
            return true;
        }
            return false;
    }

    private void setValues(){
        course.setCapacity(Integer.parseInt(editCapacity.getText().trim()));
        course.setFinalExam(editFinalExam.getValue());
        course.setSchedules(makeSchedules(editDayOne, editDayTwo, editUnits, editTimeInterval));
        course.setName(editName.getText().trim());
        course.setUnits(Integer.parseInt(editUnits.getValue()));
        course.setPrerequisites(new HashSet<>(prerequisites));
        course.setCorequisites(new HashSet<>(corequisites));
        course.setStartDate(editStartDate.getValue());
        course.setSemester(editSemester.getValue() != null ? Semester.valueOf("_"+editSemester.getValue().
                toUpperCase()) : null);
    }

    public Set<Schedule> makeSchedules(ComboBox<String> dayOne, ComboBox<String> dayTwo, ComboBox<String> units,
                                       ComboBox<String> timeInterval){
        Set<Schedule> schedules = new HashSet<>();
        if(dayOne.getValue() != null && dayTwo.getValue() != null && dayOne.getValue().equals(dayTwo.getValue())){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Schedules could not be on the same day");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("زمانبندی ها نمی توانند در یک روز باشد");
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
        Course chosenCourse = courseTableView.getSelectionModel().getSelectedItem();
        if(course != null && chosenCourse != null && !Objects.equals(course.getId(), chosenCourse.getId()) &&
                !enableChoose2Edit.isSelected()) {
            prerequisites.add(chosenCourse);
            HashSet<Course> temp = new HashSet<>(prerequisites);
            prerequisites.clear();
            prerequisites.addAll(temp);
        }
    }

    public void addToCorequisites() {
        Course chosenCourse = courseTableView.getSelectionModel().getSelectedItem();
        if(course != null && chosenCourse != null && !Objects.equals(course.getId(), chosenCourse.getId()) &&
                !enableChoose2Edit.isSelected()) {
            corequisites.add(chosenCourse);
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
        clearTextFields(editCapacity, editInstructorId, editName);
        clearComboBoxes(editDayTwo, editDayOne, editUnits, editTimeInterval);
        editFinalExam.setValue(null);
    }

    private static Image ErrorNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/ErrorNot.png"))));
    private void makeErrorMassage(String message){
        Notifications notification = Notifications.create()
                .title(" Error ! ")
                .text(message)
                .graphic(new ImageView(ErrorNot))
                .hideAfter(Duration.seconds(10))
                .position(Pos.CENTER);
        notification.show();
    }


    public void setRefreshTables(){
        refreshTables(courseTableView,instructorTableView,null,null,null);
    }


    private void persianLanguageTranslator( ArrayList<String> fxmlID , ArrayList<String> text ){
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

        fxmlID.add("editCourseHeaderLabel");
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

        fxmlID.add("editCourseIdLabel");
        fxmlID.add("enableChoose2Edit");

        fxmlID.add("courseManagementHeader");
        fxmlID.add("universityManagementHeader");


        new LanguageConfiguration(fxmlID,text,"CourseManagement");

        goBackToHomePage.setText(text.get(0));
        goToCourseManagement.setText(text.get(1));
        selected.setText(text.get(2));
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

        editCourseHeaderLabel.setText((text.get(27)));
        editNameLabel.setText((text.get(28)));
        editCapacityLabel.setText((text.get(29)));
        editInstructorIdLabel.setText((text.get(30)));
        editUnitsLabel.setText(text.get(31));
        editScheduleLabel.setText(text.get(32));
        editDayOneLabel.setText((text.get(33)));
        editDayTwoLabel.setText((text.get(34)));
        editTimeIntervalLabel.setText((text.get(35)));
        editFacultyLabel.setText((text.get(36)));
        editStartDateLabel.setText((text.get(37)));
        editFinalExamLabel.setText((text.get(38)));
        editSemesterLabel.setText((text.get(39)));

        save.setText(text.get(40));
        addToPrerequisites.setText(text.get(41));
        addToCorequisites.setText(text.get(42));
        deletePrerequisites.setText(text.get(43));
        deleteCorequisites.setText(text.get(44));

        editCourseIdLabel.setText(text.get(45));
        enableChoose2Edit.setText(text.get(46));

        courseManagementHeader.setText(text.get(47));

        universityManagementHeader.setText(text.get(48));
    }
}



