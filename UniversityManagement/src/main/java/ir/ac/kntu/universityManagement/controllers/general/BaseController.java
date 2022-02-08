package ir.ac.kntu.universityManagement.controllers.general;

import ir.ac.kntu.universityManagement.controllers.managers.*;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.individuals.Person;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.settings.FontSize;
import ir.ac.kntu.universityManagement.models.settings.Language;
import ir.ac.kntu.universityManagement.models.settings.Settings;
import ir.ac.kntu.universityManagement.models.settings.Theme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ir.ac.kntu.universityManagement.controllers.general.HomePageController.applicationContext;

@Getter
@Setter
@NoArgsConstructor
public class BaseController {


    private Stage stage;
    private String viewName;
    private Scene scene;

    private static String previousThemeName = "lightTheme";
    private static String themeName = "darkTheme";
    private static String language = "Persian";
    private static String previousFontSizeName = "largeFont";
    private static String fontSizeName = "mediumFont";

    public BaseController(String viewName, Scene scene) {
        this.viewName = viewName;
        this.scene = scene;
        this.stage = (Stage)scene.getWindow();
    }

    public void start() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + viewName + "View.fxml"));
        Parent parent = fxmlLoader.load();
        scene.setRoot(parent);
        stage.setScene(scene);


        scene.getStylesheets().remove("/css/fonts/"+previousFontSizeName+".css");
        scene.getStylesheets().add("/css/fonts/"+fontSizeName+".css");
        scene.getStylesheets().remove("/css/themes/"+previousThemeName+".css");
        scene.getStylesheets().add("/css/themes/"+themeName+".css");

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        stage.setMaximized(true);
        stage.show();
    }

/*    private void setFont(){
        scene.getStylesheets().remove("/css/fonts/"+previousFontSizeName+".css");
        scene.getStylesheets().add("/css/fonts/"+fontSizeName+".css");
    }

    private void setTheme(){
        //----------------Theme
        scene.getStylesheets().remove("/css/themes/"+previousThemeName+".css");
        scene.getStylesheets().add("/css/themes/"+themeName+".css");
        //----------------
    }*/

    public void clearTextFields(TextField ... textFields){
        for(TextField textField:textFields){
            textField.setText("");
        }
    }

    @SafeVarargs
    public final void clearComboBoxes(ComboBox<String>... comboBoxes){
        for(ComboBox<String> comboBox:comboBoxes){
            comboBox.setValue(null);
        }
    }

    public boolean isNotNumeric(String input){
        try{
            Integer.parseInt(input);
        } catch (NumberFormatException numberFormatException){
            return true;
        }
        return false;
    }

    public void abort(){
        HomePageController.user = null;
        PersonalInfoController.person = null;
        FreeTimeDeclarationController.abortFreeTimes();
    }

    public static String getLanguage(){
        return language;
    }
    public static String getThemeName(){ return themeName;}
    public static String getPreviousThemeName(){ return previousThemeName;}
    public static String getPreviousFontSizeName(){return previousFontSizeName;}
    public static String getFontSizeName(){return fontSizeName;}

    public static void setLanguage(String language){
        BaseController.language = language;
    }
    public static void setTheme(String themeName){
        if (themeName.equals("darkTheme")){
            BaseController.previousThemeName = "lightTheme";
        } else if(themeName.equals("lightTheme")){
            BaseController.previousThemeName = "darkTheme";
        }
        BaseController.themeName = themeName;
    }
    public static void setFontSizeName(String fontSize){
        BaseController.fontSizeName = fontSize;
    }



    public boolean areDatesEqual(LocalDate localDate1, LocalDate localDate2){
        return localDate1.getYear() == localDate2.getYear() && localDate1.getMonth() == localDate2.getMonth() &&
                localDate1.getDayOfMonth() == localDate2.getDayOfMonth();
    }

    public boolean isNotValidEmail(String input){
        String regexPattern = "^(.+)@(\\S+)$";
        return !Pattern.compile(regexPattern)
                .matcher(input)
                .matches();
    }

    //--------------------------Refresh Button
    public void refreshTables(TableView<Course> courseTableView,TableView<Instructor> instructorTableView,TableView<Student> studentTableView
            ,TableView<Faculty> facultyTableView, TableView<UserInfo> userTableView){
        if (courseTableView != null) {
            refreshCourse(CourseManagementController.courses, courseTableView);
        }
        if (instructorTableView != null) {
            refreshInstructors(InstructorManagementController.instructors, instructorTableView);
        }
        if (studentTableView != null) {
            refreshStudents(StudentManagementController.students, studentTableView);
        }
        if (facultyTableView != null) {
            refreshFaculties(FacultyManagementController.faculties, facultyTableView);
        }
        if (userTableView != null) {
            refreshUsers(UserManagementController.users, userTableView);
        }
    }
    //_________________________


    public void refreshInstructors(ObservableList<Instructor> instructorObservableList, TableView<Instructor> instructorTableView){
        if (instructorObservableList != null && StudentManagementController.isUpTODate){
            new Thread(() -> instructorTableView.setItems(instructorObservableList)).start();
        } else {
            new Thread(() -> {
                instructorTableView.setItems(instructorObservableList);
                InstructorManagementController.isUpTODate = true;
            }).start();
        }
    }

    public void refreshStudents(ObservableList<Student> studentObservableList, TableView<Student> studentTableView){
        if (studentObservableList != null && StudentManagementController.isUpTODate){
            new Thread(() -> studentTableView.setItems(studentObservableList)).start();
        } else {
            new Thread(() -> {
                studentTableView.setItems(studentObservableList);
                StudentManagementController.isUpTODate = true;
            }).start();
        }
    }

    public void refreshCourse(ObservableList<Course> courseObservableList, TableView<Course> courseTableView){
        if (courseObservableList != null && CourseManagementController.isUpTODate){
            new Thread(() -> courseTableView.setItems(courseObservableList)).start();
        } else {
            new Thread(() -> {
                courseTableView.setItems(courseObservableList);
                CourseManagementController.isUpTODate = true;
            }).start();
        }
    }

    public void refreshUsers(ObservableList<UserInfo> userObservableList, TableView<UserInfo> userTableView){
        if (userObservableList != null && UserManagementController.isUpTODate){
            new Thread(() -> userTableView.setItems(userObservableList)).start();
        } else {
            new Thread(() -> {
                userTableView.setItems(userObservableList);
                UserManagementController.isUpTODate = true;
            }).start();
        }
    }

    public void refreshFaculties(ObservableList<Faculty> facultyObservableList, TableView<Faculty> facultyTableView){
        if (facultyObservableList!= null && FacultyManagementController.isUpTODate){
            new Thread(() -> facultyTableView.setItems(facultyObservableList)).start();
        } else {
            new Thread(() -> {
                facultyTableView.setItems(facultyObservableList);
                FacultyManagementController.isUpTODate = true;
            }).start();
        }
    }

    public void refreshPersonalInfo(Person person){
        PersonalInfoController.person = person;
    }

    public void goBackToHomePage(Scene scene) throws Exception {
        new HomePageController(scene).start();
    }

    public void logOut(MenuButton menuButton){
        abort();
        applicationContext.publishEvent(new LoginController.StageReadyEvent(menuButton.getScene().getWindow()));
    }

    public void courseSearch(TextField courseSearchBox, ComboBox<String> courseSearchBy, TableView<Course> courseTableView){
        ObservableList<Course> courses = CourseManagementController.courses;
        if (language.equals("English")) {
            if (courseSearchBy.getValue() != null && !courseSearchBox.getText().equals("")) {
                switch (courseSearchBy.getValue()) {
                    case "Course ID":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getCourseId().toLowerCase().
                                        contains(courseSearchBox.getText().toLowerCase()))
                                .collect(Collectors.toList()));
                        break;
                    case "Name":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getName().toLowerCase().
                                        contains(courseSearchBox.getText().toLowerCase())).collect(Collectors.toList()));
                        break;
                    case "Capacity":
                        try {
                            courses = FXCollections.observableArrayList(courses.stream().
                                    filter(course -> course.getCapacity() == Integer.parseInt(courseSearchBox.getText())).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            courses = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                case "Schedule":
                    courses = FXCollections.observableArrayList(courses.stream().
                            filter(course -> course.getSchedules() != null &&
                                    course.getSchedules().toString().contains(courseSearchBox.getText())).
                            collect(Collectors.toList()));
                    break;
                    case "Final Exam":
                        try {
                            courses = FXCollections.observableArrayList(courses.stream().
                                    filter(course -> course.getFinalExam() != null &&
                                            areDatesEqual(course.getFinalExam(),
                                                    LocalDate.parse(courseSearchBox.getText()))).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            courses = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                    case "Instructor":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getInstructor() != null &&
                                        course.getInstructor().getInstructorId().
                                                contains(courseSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                }
            }
        } else if (language.equals("Persian")){
            if (courseSearchBy.getValue() != null && !courseSearchBox.getText().equals("")) {
                switch (courseSearchBy.getValue()) {
                    case "آی دی درس":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getCourseId().toLowerCase().
                                        contains(courseSearchBox.getText().toLowerCase()))
                                .collect(Collectors.toList()));
                        break;
                    case "نام درس":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getName().toLowerCase().
                                        contains(courseSearchBox.getText().toLowerCase())).collect(Collectors.toList()));
                        break;
                    case "ظرفیت درس":
                        try {
                            courses = FXCollections.observableArrayList(courses.stream().
                                    filter(course -> course.getCapacity() == Integer.parseInt(courseSearchBox.getText())).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            courses = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                case "زمانبندی درس":
                    courses = FXCollections.observableArrayList(courses.stream().
                            filter(course -> course.getSchedules() != null &&
                                    course.getSchedules().toString().contains(courseSearchBox.getText())).
                            collect(Collectors.toList()));
                    break;
                    case "پایانترم درس":
                        try {
                            courses = FXCollections.observableArrayList(courses.stream().
                                    filter(course -> course.getFinalExam() != null &&
                                            areDatesEqual(course.getFinalExam(),
                                                    LocalDate.parse(courseSearchBox.getText()))).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            courses = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                    case "استاد درس":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getInstructor() != null &&
                                        course.getInstructor().getInstructorId().
                                                contains(courseSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                }
            }
        }

        courseTableView.setItems(courses);
    }
    public void courseRequisitesSearch(TextField courseSearchBox, ComboBox<String> courseSearchBy,
                                       TableView<Course> courseTableView, List<Course> initialCourses){
        List<Course> courses = initialCourses;
        if (language.equals("English")) {
            if (courseSearchBy.getValue() != null && !courseSearchBox.getText().equals("")) {
                switch (courseSearchBy.getValue()) {
                    case "Course ID":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getCourseId().toLowerCase().
                                        contains(courseSearchBox.getText().toLowerCase()))
                                .collect(Collectors.toList()));
                        break;
                    case "Name":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getName().toLowerCase().
                                        contains(courseSearchBox.getText().toLowerCase())).collect(Collectors.toList()));
                        break;
                }
            }
        } else if (language.equals("Persian")){
            if (courseSearchBy.getValue() != null && !courseSearchBox.getText().equals("")) {
                switch (courseSearchBy.getValue()) {
                    case "آی دی درس":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getCourseId().toLowerCase().
                                        contains(courseSearchBox.getText().toLowerCase()))
                                .collect(Collectors.toList()));
                        break;
                    case "نام درس":
                        courses = FXCollections.observableArrayList(courses.stream().
                                filter(course -> course.getName().toLowerCase().
                                        contains(courseSearchBox.getText().toLowerCase())).collect(Collectors.toList()));
                        break;
                }
            }
        }

        courseTableView.setItems(FXCollections.observableList(courses));
    }

    public void instructorSearch(TextField instructorTableSearchBox, ComboBox<String> instructorTableSearchBy, TableView<Instructor> instructorTableView) {
        ObservableList<Instructor> instructors = InstructorManagementController.instructors;



        if(language.equals("English")) {
            if (instructorTableSearchBy.getValue() != null && !instructorTableSearchBox.getText().equals("")) {
                switch (instructorTableSearchBy.getValue()) {
                    case "Instructor ID":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getInstructorId().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "FirstName":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getFirstName().toLowerCase().
                                        contains(instructorTableSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "LastName":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getLastName().toLowerCase().
                                        contains(instructorTableSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "National ID":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getNationalId().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "PhoneNumber":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getPhoneNumber().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "Email":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getEmail().toLowerCase().
                                        contains(instructorTableSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "Address":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getAddress().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "Username":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getUserInfo() != null &&
                                        instructor.getUserInfo().getUsername().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "Faculty":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getFaculty() != null &&
                                        instructor.getFaculty().getName().toLowerCase().
                                                contains(instructorTableSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "Birthdate":
                        try {
                            instructors = FXCollections.observableArrayList(instructors.stream().
                                    filter(instructor -> instructor.getBirthdate() != null &&
                                            areDatesEqual(instructor.getBirthdate(),
                                                    LocalDate.parse(instructorTableSearchBox.getText()))).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            instructors = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;

                }
            }
        } else if (language.equals("Persian")){
            if (instructorTableSearchBy.getValue() != null && !instructorTableSearchBox.getText().equals("")) {
                switch (instructorTableSearchBy.getValue()) {
                    case "آی دی استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getInstructorId().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "نام کوچک استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getFirstName().toLowerCase().
                                        contains(instructorTableSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "نام حانوادگی استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getLastName().toLowerCase().
                                        contains(instructorTableSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "کدملی استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getNationalId().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "شماره تماس استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getPhoneNumber().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "ایمیل استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getEmail().toLowerCase().
                                        contains(instructorTableSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "آدرس استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getAddress().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "یوسرنیم استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getUserInfo() != null &&
                                        instructor.getUserInfo().getUsername().contains(instructorTableSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "دانشکده استاد":
                        instructors = FXCollections.observableArrayList(instructors.stream().
                                filter(instructor -> instructor.getFaculty() != null &&
                                        instructor.getFaculty().getName().toLowerCase().
                                                contains(instructorTableSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "تاریخ تولد استاد":
                        try {
                            instructors = FXCollections.observableArrayList(instructors.stream().
                                    filter(instructor -> instructor.getBirthdate() != null &&
                                            areDatesEqual(instructor.getBirthdate(),
                                                    LocalDate.parse(instructorTableSearchBox.getText()))).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            instructors = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;

                }
            }
        }
        instructorTableView.setItems(instructors);
    }

    @SafeVarargs
    public final void changeEditable(Boolean disable, ComboBox<String>... comboBoxes){
        for(ComboBox<String> comboBox: comboBoxes){
            comboBox.setDisable(disable);
        }
    }



    public void studentSearch(TextField studentSearchBox, ComboBox<String> studentSearchBy,TableView<Student> studentTableView) {
        ObservableList<Student> students = StudentManagementController.students;

        if(language.equals("English")) {
            if (studentSearchBy.getValue() != null && !studentSearchBox.getText().equals("")) {
                switch (studentSearchBy.getValue()) {
                    case "Student ID":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getStudentNumber().contains(studentSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "FirstName":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getFirstName().toLowerCase().
                                        contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "LastName":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getLastName().toLowerCase().
                                        contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "National ID":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getNationalId().contains(studentSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "Birthdate":
                        try {
                            students = FXCollections.observableArrayList(students.stream().
                                    filter(student -> areDatesEqual(student.getBirthdate(),
                                            LocalDate.parse(studentSearchBox.getText()))).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            students = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                    case "PhoneNumber":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getPhoneNumber() != null &&
                                        student.getPhoneNumber().contains(studentSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "Email":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getEmail() != null &&
                                        student.getEmail().toLowerCase().
                                                contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "Address":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getAddress().toLowerCase().
                                        contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "Faculty":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getFaculty() != null &&
                                        student.getFaculty().getName().toLowerCase().
                                                contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "Username":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getUserInfo() != null &&
                                        student.getUserInfo().getUsername().toLowerCase().
                                                contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                }
            }
        } else if (language.equals("Persian")){
            if (studentSearchBy.getValue() != null && !studentSearchBox.getText().equals("")) {
                switch (studentSearchBy.getValue()) {
                    case "شماره دانشجویی":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getStudentNumber().contains(studentSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "نام کوچک":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getFirstName().toLowerCase().
                                        contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "نام خانوادگی":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getLastName().toLowerCase().
                                        contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "کد ملی":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getNationalId().contains(studentSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "تاریخ تولد":
                        try {
                            students = FXCollections.observableArrayList(students.stream().
                                    filter(student -> areDatesEqual(student.getBirthdate(),
                                            LocalDate.parse(studentSearchBox.getText()))).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            students = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                    case "شماره تماس":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getPhoneNumber() != null &&
                                        student.getPhoneNumber().contains(studentSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "ایمیل":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getEmail() != null &&
                                        student.getEmail().toLowerCase().
                                                contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "آدرس":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getAddress().toLowerCase().
                                        contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "دانشکده":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getFaculty() != null &&
                                        student.getFaculty().getName().toLowerCase().
                                                contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "یوسرنیم":
                        students = FXCollections.observableArrayList(students.stream().
                                filter(student -> student.getUserInfo() != null &&
                                        student.getUserInfo().getUsername().toLowerCase().
                                                contains(studentSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                }
            }
        }
        studentTableView.setItems(students);
    }


    public void userSearch(TextField userSearchBox,ComboBox<String> userSearchBy, TableView<UserInfo> userTableview) {
        ObservableList<UserInfo> users = UserManagementController.users;

        if(language.equals("English")) {
            if (userSearchBy.getValue() != null && !userSearchBox.getText().equals("")) {
                switch (userSearchBy.getValue()) {
                    case "Username":
                        users = FXCollections.observableArrayList(users.stream().
                                filter(userInfo -> userInfo.getUsername().contains(userSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "Role":
                        users = FXCollections.observableArrayList(users.stream().
                                filter(userInfo -> userInfo.getRole().toString().toLowerCase().
                                        contains(userSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                }
            }
        } else if (language.equals("Persian")){
            if (userSearchBy.getValue() != null && !userSearchBox.getText().equals("")) {
                switch (userSearchBy.getValue()) {
                    case "یوسرنیم":
                        users = FXCollections.observableArrayList(users.stream().
                                filter(userInfo -> userInfo.getUsername().contains(userSearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "نقش":
                        users = FXCollections.observableArrayList(users.stream().
                                filter(userInfo -> userInfo.getRole().toString().toLowerCase().
                                        contains(userSearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                }
            }
        }
        userTableview.setItems(users);
    }

    public void facultySearch(TextField facultySearchBox, ComboBox<String> facultySearchBy, TableView<Faculty> facultyTableView) {
        ObservableList<Faculty> faculties = FacultyManagementController.faculties;

        if(language.equals("English")) {

            if (facultySearchBy.getValue() != null && !facultySearchBox.getText().equals("")) {
                switch (facultySearchBy.getValue()) {
                    case "Faculty ID":
                        faculties = FXCollections.observableArrayList(faculties.stream().
                                filter(faculty -> faculty.getFacultyId().contains(facultySearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "Faculty Name":
                        faculties = FXCollections.observableArrayList(faculties.stream().
                                filter(faculty -> faculty.getName().toLowerCase().
                                        contains(facultySearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "Num of Classes":
                        try {
                            faculties = FXCollections.observableArrayList(faculties.stream().
                                    filter(faculty -> faculty.getNumberOfClasses() == Integer.parseInt(facultySearchBox.getText())).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            faculties = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                    case "Num of Students":
                        try {
                            faculties = FXCollections.observableArrayList(faculties.stream().
                                    filter(faculty -> faculty.getNumberOfStudents() == Integer.parseInt(facultySearchBox.getText())).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            faculties = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                    case "Num of Instructors":
                        faculties = FXCollections.observableArrayList(faculties.stream().
                                filter(faculty -> faculty.getNumberOfInstructors() == Integer.parseInt(facultySearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "PhoneNumber":
                        faculties = FXCollections.observableArrayList(faculties.stream().
                                filter(faculty -> faculty.getPhoneNumber().contains(facultySearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                }
            }
        } else if (language.equals("Persian")){

            if (facultySearchBy.getValue() != null && !facultySearchBox.getText().equals("")) {
                switch (facultySearchBy.getValue()) {
                    case "آی دی":
                        faculties = FXCollections.observableArrayList(faculties.stream().
                                filter(faculty -> faculty.getFacultyId().contains(facultySearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "نام دانشکده":
                        faculties = FXCollections.observableArrayList(faculties.stream().
                                filter(faculty -> faculty.getName().toLowerCase().
                                        contains(facultySearchBox.getText().toLowerCase())).
                                collect(Collectors.toList()));
                        break;
                    case "تعداد کلاس":
                        try {
                            faculties = FXCollections.observableArrayList(faculties.stream().
                                    filter(faculty -> faculty.getNumberOfClasses() == Integer.parseInt(facultySearchBox.getText())).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            faculties = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                    case "تعداد دانشجو":
                        try {
                            faculties = FXCollections.observableArrayList(faculties.stream().
                                    filter(faculty -> faculty.getNumberOfStudents() == Integer.parseInt(facultySearchBox.getText())).
                                    collect(Collectors.toList()));
                        } catch (Exception e) {
                            faculties = FXCollections.observableArrayList(new ArrayList<>());
                        }
                        break;
                    case "تعداد استاد":
                        faculties = FXCollections.observableArrayList(faculties.stream().
                                filter(faculty -> faculty.getNumberOfInstructors() == Integer.parseInt(facultySearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                    case "شماره تماس":
                        faculties = FXCollections.observableArrayList(faculties.stream().
                                filter(faculty -> faculty.getPhoneNumber().contains(facultySearchBox.getText())).
                                collect(Collectors.toList()));
                        break;
                }
            }
        }
        facultyTableView.setItems(faculties);
    }

}
