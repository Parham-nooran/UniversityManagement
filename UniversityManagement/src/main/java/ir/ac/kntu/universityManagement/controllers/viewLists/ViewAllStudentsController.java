package ir.ac.kntu.universityManagement.controllers.viewLists;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.StudentController;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@NoArgsConstructor
public class ViewAllStudentsController extends StudentController implements Initializable {

    //----------------------------------------------------------------Constructor
    public ViewAllStudentsController(Scene scene) {super("ViewAllStudents" ,scene);}
    //----------------------------------------------------------------


    //----------------------------------------------------------------Studetns TableView
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String> studentNumber;
    @FXML private TableColumn<Student, String> studentFirstName;
    @FXML private TableColumn<Student, String> studentLastName;
    @FXML private TableColumn<Student, String> studentNationalId;
    @FXML private TableColumn<Student, String> studentBirthdate;
    @FXML private TableColumn<Student, String> studentEmail;
    @FXML private TableColumn<Student, String> studentPhoneNumber;
    @FXML private TableColumn<Student, String> studentAddress;
    @FXML private TableColumn<Student, String> studentUserInfo;
    @FXML private TableColumn<Student, String> studentFaculty;
    @FXML private TableColumn<Student, String> studentEntranceDate;
    @FXML private ComboBox<String> studentSearchBy;
    @FXML private TextField studentSearchBox;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToStudentManagement;
    @FXML private Button goToEditStudent;
    @FXML private Button goToDeleteStudent;
    @FXML private Button gotoViewAllStudent;
    @FXML private Button selected;
    @FXML private Button refreshTablesButton;
    @FXML private Label studentManagementHeader;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillStudentTable(studentNumber, studentFirstName, studentLastName, studentNationalId, studentBirthdate,
                studentPhoneNumber, studentEmail, studentAddress, studentUserInfo, studentFaculty, studentEntranceDate);
        fillStudentSearch(studentSearchBox,studentSearchBy,studentTableView);
        refreshStudents(StudentManagementController.students,studentTableView);

        fillUserInterface(goBackToHomePage,goToStudentManagement, goToEditStudent, goToDeleteStudent, gotoViewAllStudent,menuButton,logOut);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------

    }

    public void setRefreshTables(){
        refreshTables(null,null,studentTableView,null,null);
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){
        fxmlID.add("goBackToHomePage");
        fxmlID.add("goToStudentManagement");
        fxmlID.add("goToEditStudent");
        fxmlID.add("goToDeleteStudent");
        fxmlID.add("gotoViewAllStudent");
        fxmlID.add("refreshTablesButton");
        fxmlID.add("studentManagementHeader");

        fxmlID.add("studentNumber");
        fxmlID.add("studentFirstName");
        fxmlID.add("studentLastName");
        fxmlID.add("studentNationalId");
        fxmlID.add("studentBirthdate");
        fxmlID.add("studentEmail");
        fxmlID.add("studentPhoneNumber");
        fxmlID.add("studentAddress");
        fxmlID.add("studentUserInfo");
        fxmlID.add("studentEntranceDate");
        fxmlID.add("studentFaculty");
        fxmlID.add("studentSearchBy");
        fxmlID.add("universityManagementHeader");




        new LanguageConfiguration(fxmlID,text,"StudentManagement");

        goBackToHomePage.setText(text.get(0));
        goToStudentManagement.setText(text.get(1));
        goToEditStudent.setText(text.get(2));
        goToDeleteStudent.setText(text.get(3));
        selected.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));
        studentManagementHeader.setText(text.get(6));

        studentNumber.setText(text.get(7));
        studentFirstName.setText(text.get(8));
        studentLastName.setText(text.get(9));
        studentNationalId.setText(text.get(10));
        studentBirthdate.setText(text.get(11));
        studentEmail.setText(text.get(12));
        studentPhoneNumber.setText(text.get(13));
        studentAddress.setText(text.get(14));
        studentUserInfo.setText(text.get(15));
        studentEntranceDate.setText(text.get(16));
        studentFaculty.setText(text.get(17));
        studentSearchBy.setPromptText(text.get(18));
        universityManagementHeader.setText(text.get(19));
    }
}
