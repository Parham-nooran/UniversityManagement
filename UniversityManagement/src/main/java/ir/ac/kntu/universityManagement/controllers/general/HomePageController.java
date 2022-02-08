package ir.ac.kntu.universityManagement.controllers.general;

import ir.ac.kntu.universityManagement.controllers.editors.EditUserController;
import ir.ac.kntu.universityManagement.controllers.managers.*;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

@Setter
@NoArgsConstructor
public class HomePageController extends BaseController implements Initializable {

    @FXML private TreeView<String> treeView;
    @FXML private MenuButton menuButton;
    @FXML private TextField userName;
    @FXML private Label time;
    @FXML private MenuItem logOut;
    @FXML private TextField numberOfCourses;
    @FXML private TextField numberOfFaculties;
    @FXML private TextField numberOfStudents;
    @FXML private TextField numberOfInstructors;
    @FXML private TextField numberOfUsers;
    @FXML private ImageView loading1;
    @FXML private ImageView loading2;
    @FXML private ImageView loading3;
    @FXML private ImageView loading4;
    @FXML private ImageView loading5;
    @FXML private TextField welcomeText;
    @FXML private ImageView englishHandShake;
    @FXML private ImageView persianHandShake;
    @FXML private Label numberOfInstructorsLabel;
    @FXML private Label numberOfStudentsLabel;
    @FXML private Label numberOfUsersLabel;
    @FXML private Label numberOfCoursesLabel;
    @FXML private Label numberOfFacultiesLabel;
    @FXML private Label numberOfInstructorsPersianLabel;
    @FXML private TextField numberOfInstructorsPersian;
    @FXML private ImageView loadingInstructorPersian;
    @FXML private Label numberOfStudentsPersianLabel;
    @FXML private TextField numberOfStudentsPersian;
    @FXML private ImageView loadingStudentsPersian;
    @FXML private Label numberOfUsersPersianLabel;
    @FXML private TextField numberOfUsersPersian;
    @FXML private ImageView loadingUsersPersian;
    @FXML private Label numberOfCoursesPersianLabel;
    @FXML private TextField numberOfCoursesPersian;
    @FXML private ImageView loadingCoursesPersian;
    @FXML private Label numberOfFacultiesPersianLabel;
    @FXML private TextField numberOfFacultiesPersian;
    @FXML private ImageView loadingFacultiesPersian;
    @FXML private Label universityManagementHeader;


    public static UserInfo user;
    public static ApplicationContext applicationContext;
    private static TreeItem<String> rootItem1 = new TreeItem<>("Root");
    private volatile boolean stop = false;

    private boolean initialized = false;

    public static void fillHomePage(){
        rootItem1.getChildren().clear();
        personalInfo.getChildren().clear();
        if (user.getRole().equals(Role.INSTRUCTOR)) {
            FreeTimeDeclarationController.findInstructor();
            rootItem1.getChildren().addAll(freeTimeDeclaration);
        }  else if(user.getRole().equals(Role.ADMIN)){
            StudentManagementController.refillStudents();
            InstructorManagementController.refillInstructors();
            CourseManagementController.refillCourses();
            FacultyManagementController.refillFaculties();
            UserManagementController.refillUsers();
            UniversityCalendarController.refillEntries();
            rootItem1.getChildren().addAll(courses, students, instructors, faculties, userManagement, calendar);
        } else if(user.getRole().equals(Role.STUDENT)){
            if(UniversityCalendarController.isRegistrationTime()) {
                CoursesRegistrationController.refillCourses();
                rootItem1.getChildren().add(takeCourse);
            }
        }
        Thread thread = new Thread(PersonalInfoController::findUser);
        thread.setPriority(8);
        thread.start();
        EditUserController.userInfo = HomePageController.user;
        rootItem1.getChildren().addAll(personalInfo,setting,about);
    }

    private static Image informationIcon = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/HomePageIcons/TreeView/PersonalInfoTreeIcon.png"))));
    private static Image courseIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/CoursesTreeIcon.png"))));
    private static Image userManagementIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/UserManagementTreeIcon.png"))));
    private static Image studentsIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/StudentsTreeIcon.png"))));
    private static Image instructorIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/InstructorTreeIcon.png"))));
    private static Image facultiesIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/FacultiesTreeIcon.png"))));
    private static Image aboutIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/AboutTreeIcon.png"))));
    private static Image editInfoIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/EditInfoTreeIcon.png"))));
    private static Image settingIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/settingTreeIcon.png"))));
    private static Image calendarIcon = new Image(Objects.requireNonNull(HomePageController.class.getResourceAsStream(("/icons/HomePageIcons/TreeView/calendarTreeIcon.png"))));



    private static TreeItem<String> personalInfo = new TreeItem<>("Personal information", new ImageView(informationIcon));
    private static TreeItem<String> courses = new TreeItem<>("Courses", new ImageView(courseIcon));
    private static TreeItem<String> students = new TreeItem<>("Students", new ImageView(studentsIcon));
    private static TreeItem<String> instructors = new TreeItem<>("Instructors", new ImageView(instructorIcon));
    private static TreeItem<String> faculties = new TreeItem<>("Faculties", new ImageView(facultiesIcon));
    private static TreeItem<String> about = new TreeItem<>("About", new ImageView(aboutIcon));
    private static TreeItem<String> takeCourse = new TreeItem<>("Course Registration", new ImageView(courseIcon));
    private static TreeItem<String> userManagement = new TreeItem<>("User management", new ImageView(userManagementIcon));
    private static TreeItem<String> setting = new TreeItem<>("setting", new ImageView(settingIcon));

    private static TreeItem<String> calendar = new TreeItem<>("University Calendar", new ImageView(calendarIcon));
    private static TreeItem<String> freeTimeDeclaration = new TreeItem<>("Declare Free Times", new ImageView(calendarIcon));

    public HomePageController(Scene scene) {
        super("HomePage", scene);
    }


    public void selectItem() throws Exception {
        if (!treeView.getSelectionModel().isEmpty()) {
            if (treeView.getSelectionModel().getSelectedItem().equals(personalInfo)) {
                new PersonalInfoController(treeView.getScene()).start();
            } else if (treeView.getSelectionModel().getSelectedItem().equals(students)) {
                new StudentManagementController(treeView.getScene()).start();
            } else if (treeView.getSelectionModel().getSelectedItem().equals(userManagement)) {
                new UserManagementController(treeView.getScene()).start();
            } else if(treeView.getSelectionModel().getSelectedItem().equals(courses)) {
                new CourseManagementController(treeView.getScene()).start();
            } else if(treeView.getSelectionModel().getSelectedItem().equals(instructors)) {
                new InstructorManagementController(treeView.getScene()).start();
            } else if(treeView.getSelectionModel().getSelectedItem().equals(faculties)) {
                new FacultyManagementController(treeView.getScene()).start();
            } else if(treeView.getSelectionModel().getSelectedItem().equals(about)) {
                new AboutController((Stage)treeView.getScene().getWindow(),treeView.getScene()).start();
            } else if(treeView.getSelectionModel().getSelectedItem().equals(takeCourse)){
                new CoursesRegistrationController(treeView.getScene()).start();
            } else if(treeView.getSelectionModel().getSelectedItem().equals(setting)){
                new SettingsController(treeView.getScene()).start();
            } else if(treeView.getSelectionModel().getSelectedItem().equals(calendar)){
                new UniversityCalendarController(treeView.getScene()).start();
            } else if(treeView.getSelectionModel().getSelectedItem().equals(freeTimeDeclaration)){
                FreeTimeDeclarationController freeTimeDeclarationController =
                        new FreeTimeDeclarationController(treeView.getScene());
                FreeTimeDeclarationController.calledFromHomePage = true;
                FreeTimeDeclarationController.findInstructor();
                FreeTimeDeclarationController.readOnly = false;
                freeTimeDeclarationController.start();
            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeNow();
        UserInfo userInfo = HomePageController.user;
        treeView.setRoot(rootItem1);
        treeView.setShowRoot(false);
        userName.setText(userInfo.getUsername()+" !");
        menuButton.setText(userInfo.getUsername());
        if(numberOfCourses.getText().equals("")) {
            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                    loading1.setVisible(false);
                    loading2.setVisible(false);
                    loading3.setVisible(false);
                    loading4.setVisible(false);
                    loading5.setVisible(false);
                    loadingInstructorPersian.setVisible(false);
                    loadingStudentsPersian.setVisible(false);
                    loadingUsersPersian.setVisible(false);
                    loadingCoursesPersian.setVisible(false);
                    loadingFacultiesPersian.setVisible(false);

                    initialized = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }



        if(BaseController.getLanguage().equals("English")) {
            new Thread(() -> {
                numberOfCourses.setText(String.valueOf(CourseManagementController.courses != null ?
                        CourseManagementController.courses.size() : CourseManagementController.getCoursesSize()));
                numberOfFaculties.setText(String.valueOf(FacultyManagementController.faculties != null ?
                        FacultyManagementController.faculties.size() : FacultyManagementController.getFacultiesSize()));
                numberOfStudents.setText(String.valueOf(StudentManagementController.students != null ?
                        StudentManagementController.students.size() : StudentManagementController.getStudentsSize()));
                numberOfInstructors.setText(String.valueOf(InstructorManagementController.instructors != null ?
                        InstructorManagementController.instructors.size() : InstructorManagementController.getInstructorsSize()));
                numberOfUsers.setText(String.valueOf(UserManagementController.getUsers() != null ?
                        UserManagementController.getNumberOfUsers() : UserManagementController.getUsersSize()));
            }).start();
        } else if (BaseController.getLanguage().equals("Persian")){
            new Thread(() -> {
                numberOfCoursesPersian.setText(String.valueOf(CourseManagementController.courses != null ?
                        CourseManagementController.courses.size() : CourseManagementController.getCoursesSize()));
                numberOfFacultiesPersian.setText(String.valueOf(FacultyManagementController.faculties != null ?
                        FacultyManagementController.faculties.size() : FacultyManagementController.getFacultiesSize()));
                numberOfStudentsPersian.setText(String.valueOf(StudentManagementController.students != null ?
                        StudentManagementController.students.size() : StudentManagementController.getStudentsSize()));
                numberOfInstructorsPersian.setText(String.valueOf(InstructorManagementController.instructors != null ?
                        InstructorManagementController.instructors.size() : InstructorManagementController.getInstructorsSize()));
                numberOfUsersPersian.setText(String.valueOf(UserManagementController.getUsers() != null ?
                        UserManagementController.getNumberOfUsers() : UserManagementController.getUsersSize()));
            }).start();
        }

        //---------------------------------------------Persian translate part
        homePageTranslator(userInfo);
        //---------------------------------------------


        treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @SneakyThrows
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 1){
                        selectItem();
                    }
                }
            }
        });

        logOut.setOnAction((ActionEvent event) -> {
            abort();
            applicationContext.publishEvent(new LoginController.StageReadyEvent(treeView.getScene().getWindow()));
        });
    }

    private void timeNow(){
        Thread th = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat();
            while (!stop){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                final String timeNow = sdf.format(new Date());
                Platform.runLater(() -> time.setText(timeNow));
            }
        });
        th.setDaemon(true);
        th.start();
    }

    public void turnLoadingOff(){
        loading1.setVisible(false);
    }

    private void homePageTranslator(UserInfo userInfo){

        if(BaseController.getLanguage().equals("English")){
            logOut.setText("LogOut");
            personalInfo.setValue("Personal information");
            courses.setValue("Courses");
            students.setValue("Students");
            instructors.setValue("Instructors");
            faculties.setValue("Faculties");
            about.setValue("About Us");
            takeCourse.setValue("Take Course");
            userManagement.setValue("User management");
            userName.setText(userInfo.getUsername()+" !");
            welcomeText.setText("Hey, Welcome");
            setting.setValue("Setting");
            calendar.setValue("University Calendar");
            freeTimeDeclaration.setValue("Declare Free Times");
            universityManagementHeader.setText("University\nManagement");
            persianHandShake.setVisible(false);
            numberOfInstructorsPersianLabel.setVisible(false);
            numberOfInstructorsPersian.setVisible(false);
            loadingInstructorPersian.setVisible(false);
            numberOfStudentsPersianLabel.setVisible(false);
            numberOfStudentsPersian.setVisible(false);
            loadingStudentsPersian.setVisible(false);
            numberOfUsersPersianLabel.setVisible(false);
            numberOfUsersPersian.setVisible(false);
            loadingUsersPersian.setVisible(false);
            numberOfCoursesPersianLabel.setVisible(false);
            numberOfCoursesPersian.setVisible(false);
            loadingCoursesPersian.setVisible(false);
            numberOfFacultiesPersianLabel.setVisible(false);
            numberOfFacultiesPersian.setVisible(false);
            loadingFacultiesPersian.setVisible(false);

        } else if ((BaseController.getLanguage().equals("Persian"))){
            logOut.setText("خروج از حساب کاربری");
            personalInfo.setValue("اطلاعات کاربری");
            courses.setValue("دروس");
            students.setValue("دانشجوها");
            instructors.setValue("اساتید");
            faculties.setValue("دانشکده ها");
            about.setValue("درباره ما");
            takeCourse.setValue("انتخاب واحد");
            userManagement.setValue("مدیریت کاربران");
            userName.setText("سلام، خوش آمدید");
            setting.setValue("تنظیمات");
            calendar.setValue("تقویم آموزشی");
            freeTimeDeclaration.setValue("تعیین زمان های خالی");
            universityManagementHeader.setText("مدیریت\nدانشگاه");
            welcomeText.setText(userInfo.getUsername());
            welcomeText.setAlignment(Pos.CENTER_RIGHT);
            welcomeText.setMaxWidth(140);
            englishHandShake.setVisible(false);
            numberOfInstructorsLabel.setVisible(false);
            numberOfInstructors.setVisible(false);
            loading1.setVisible(false);
            numberOfStudentsLabel.setVisible(false);
            numberOfStudents.setVisible(false);
            loading2.setVisible(false);
            numberOfUsersLabel.setVisible(false);
            numberOfUsers.setVisible(false);
            loading3.setVisible(false);
            numberOfCoursesLabel.setVisible(false);
            numberOfCourses.setVisible(false);
            loading4.setVisible(false);
            numberOfFacultiesLabel.setVisible(false);
            numberOfFaculties.setVisible(false);
            loading5.setVisible(false);
        }
    }
}