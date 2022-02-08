package ir.ac.kntu.universityManagement.controllers.deleters;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.FreeTimeDeclarationController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.InstructorController;
import ir.ac.kntu.universityManagement.controllers.managers.CourseManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@NoArgsConstructor
public class DeleteInstructorController extends InstructorController implements Initializable {


    //----------------------------------------------------------------Constructor
    public DeleteInstructorController(Scene scene) {super("DeleteInstructor", scene);}
    //----------------------------------------------------------------


    //----------------------------------------------Instructor TableView
    @FXML
    private TableView<Instructor> instructorTableView;
    @FXML private TextField instructorSearchBox;
    @FXML private TableColumn<Instructor, String> instructorId;
    @FXML private TableColumn<Instructor, String> instructorFirstName;
    @FXML private TableColumn<Instructor, String> instructorLastName;
    @FXML private TableColumn<Instructor, String> instructorPhoneNumber;
    @FXML private TableColumn<Instructor, String> instructorEmail;
    @FXML private TableColumn<Instructor, String> instructorAddress;
    @FXML private TableColumn<Instructor, String> instructorFaculty;
    @FXML private TableColumn<Instructor, String> instructorNationalId;
    @FXML private TableColumn<Instructor, String> instructorUserInfo;
    @FXML private TableColumn<Instructor, String> instructorBirthdate;
    @FXML private TableColumn<Instructor, String> instructorEntranceDate;
    @FXML private ComboBox<String> instructorSearchBy;
    //----------------------------------------------------------------




    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private AnchorPane areYouSure;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToInstructorManagement;
    @FXML private Button goToEditInstructor;
    @FXML private Button goToDeleteInstructor;
    @FXML private Button selected;
    @FXML private Button gotoViewAllInstructors;
    @FXML private Button refreshTablesButton;

    @FXML private Label instructorManagementHeader;

    @FXML private Label youSure;
    @FXML private Button yes;
    @FXML private Button no;
    @FXML private Button showFreeTimes;
    @FXML private Button delete;

    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static Instructor instructor;
    public static ObservableList<Instructor> instructors;
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillInstructorTable(instructorId,instructorFirstName, instructorLastName, instructorNationalId,
                instructorPhoneNumber, instructorEmail, instructorAddress, instructorFaculty, instructorUserInfo,
                instructorBirthdate, instructorEntranceDate);
        fillInstructorSearch(instructorSearchBox,instructorSearchBy,instructorTableView);
        refreshInstructors(InstructorManagementController.instructors,instructorTableView);

        fillUserInterface(goBackToHomePage,goToInstructorManagement,goToEditInstructor,goToDeleteInstructor,gotoViewAllInstructors,menuButton,logOut);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------

        instructorTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                instructor = instructorTableView.getSelectionModel().getSelectedItem();
            }
        });
        if(showFreeTimes != null){
            showFreeTimes.setOnAction((ActionEvent) -> {
                FreeTimeDeclarationController freeTimeDeclarationController =
                        new FreeTimeDeclarationController(menuButton.getScene());
                FreeTimeDeclarationController.calledFromHomePage = false;
                FreeTimeDeclarationController.readOnly = true;
                FreeTimeDeclarationController.instructor = instructorTableView.getSelectionModel().getSelectedItem();
                if(FreeTimeDeclarationController.calendarEntries == null){
                    FreeTimeDeclarationController.calendarEntries = FreeTimeDeclarationController.instructor.getFreeTimes();
                }
                try {
                    freeTimeDeclarationController.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void deleteInstructor() {
        new Thread(() -> InstructorManagementController.instructorRepository.deleteById(instructor.getId())).start();
        InstructorManagementController.instructors.remove(instructor);
        CourseManagementController.isUpTODate = false;
        CourseManagementController.refillCourses();
        areYouSure.setVisible(false);
    }

    public void delete(){
        if(instructor != null) {
            areYouSure.setVisible(true);
        }
    }

    public void no(){
        areYouSure.setVisible(false);
    }

    public void setRefreshTables(){
        refreshTables(null,instructorTableView,null,null,null);
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){

        fxmlID.add("goBackToHomePage");
        fxmlID.add("goToInstructorManagement");
        fxmlID.add("goToEditInstructor");
        fxmlID.add("goToDeleteInstructor");
        fxmlID.add("gotoViewAllInstructors");
        fxmlID.add("refreshTablesButton");

        fxmlID.add("instructorId");
        fxmlID.add("instructorFirstName");
        fxmlID.add("instructorLastName");
        fxmlID.add("instructorPhoneNumber");
        fxmlID.add("instructorEmail");
        fxmlID.add("instructorAddress");
        fxmlID.add("instructorFaculty");
        fxmlID.add("instructorNationalId");
        fxmlID.add("instructorUserInfo");
        fxmlID.add("instructorBirthdate");
        fxmlID.add("instructorEntranceDate");
        fxmlID.add("instructorSearchBy");

        fxmlID.add("instructorManagementHeader");

        fxmlID.add("youSure");
        fxmlID.add("yes");
        fxmlID.add("no");
        fxmlID.add("delete");

        fxmlID.add("universityManagementHeader");


        new LanguageConfiguration(fxmlID,text,"InstructorManagement");

        goBackToHomePage.setText(text.get(0));
        goToInstructorManagement.setText(text.get(1));
        goToEditInstructor.setText(text.get(2));
        selected.setText(text.get(3));
        gotoViewAllInstructors.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));

        instructorId.setText(text.get(6));
        instructorFirstName.setText(text.get(7));
        instructorLastName.setText(text.get(8));
        instructorPhoneNumber.setText(text.get(9));
        instructorEmail.setText(text.get(10));
        instructorAddress.setText(text.get(11));
        instructorFaculty.setText(text.get(12));
        instructorNationalId.setText(text.get(13));
        instructorUserInfo.setText(text.get(14));
        instructorBirthdate.setText(text.get(15));
        instructorEntranceDate.setText(text.get(16));
        instructorSearchBy.setPromptText(text.get(17));

        instructorManagementHeader.setText(text.get(18));

        youSure.setText(text.get(19));
        yes.setText(text.get(20));
        no.setText(text.get(21));
        delete.setText(text.get(22));
        universityManagementHeader.setText(text.get(23));


    }
}
