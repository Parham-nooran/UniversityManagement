package ir.ac.kntu.universityManagement.controllers.viewLists;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.managers.FacultyManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.FacultyController;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.ObservableList;
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
public class ViewAllFacultiesController extends FacultyController implements Initializable {

    //----------------------------------------------------------------Constructor
    public ViewAllFacultiesController(Scene scene) {
        super("ViewAllFaculties" ,scene);
    }
    //----------------------------------------------------------------


    //-------------------------------------------------------TableView
    @FXML private TableView<Faculty> facultyTableView;
    @FXML private TableColumn<Faculty, String> facultyId;
    @FXML private TableColumn<Faculty, String> facultyName;
    @FXML private TableColumn<Faculty, Integer> facultyNumberOfClasses;
    @FXML private TableColumn<Faculty, Integer> facultyNumberOfStudents;
    @FXML private TableColumn<Faculty, Integer> facultyNumberOfInstructors;
    @FXML private TableColumn<Faculty, String> facultyPhoneNumber;
    @FXML private TextField facultySearchBox;
    @FXML private ComboBox<String> facultySearchBy;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToFacultyManagement;
    @FXML private Button goToEditFaculty;
    @FXML private Button goToDeleteFaculty;
    @FXML private Button gotoViewAllFaculty;
    @FXML private Button selected;
    @FXML private Button refreshTablesButton;
    @FXML private Label facultyManagementHeader;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static ObservableList<Faculty> faculties;
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillFacultyTable(facultyId,facultyName,facultyNumberOfClasses,facultyNumberOfStudents,facultyNumberOfInstructors,facultyPhoneNumber);
        fillFacultySearch(facultySearchBox,facultySearchBy,facultyTableView);

        refreshFaculties(FacultyManagementController.faculties,facultyTableView);

        fillUserInterface(goBackToHomePage,goToFacultyManagement,goToEditFaculty,goToDeleteFaculty,gotoViewAllFaculty,menuButton,logOut);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------
    }

    public void setRefreshTables(){
        refreshTables(null,null,null,facultyTableView,null);
    }

    private void persianTranslate(ArrayList<String> fxmlID, ArrayList<String> text){
        fxmlID.add("homePageButton");
        fxmlID.add("addFacultyButton");
        fxmlID.add("editFacultyButton");
        fxmlID.add("deleteFacultyButton");
        fxmlID.add("ViewAllFacultiesButton");
        fxmlID.add("refreshTablesButton");
        fxmlID.add("facultyManagementHeader");

        fxmlID.add("facultyId");
        fxmlID.add("facultyName");
        fxmlID.add("facultyNumberOfClasses");
        fxmlID.add("facultyNumberOfStudents");
        fxmlID.add("facultyNumberOfInstructors");
        fxmlID.add("facultyPhoneNumber");
        fxmlID.add("facultySearchBy");
        fxmlID.add("universityManagementHeader");


        new LanguageConfiguration(fxmlID,text,"FacultyManagement");

        goBackToHomePage.setText(text.get(0));
        goToFacultyManagement.setText(text.get(1));
        goToEditFaculty.setText(text.get(2));
        goToDeleteFaculty.setText(text.get(3));
        selected.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));
        facultyManagementHeader.setText(text.get(6));

        facultyId.setText(text.get(7));
        facultyName.setText(text.get(8));
        facultyNumberOfClasses.setText(text.get(9));
        facultyNumberOfStudents.setText(text.get(10));
        facultyNumberOfInstructors.setText(text.get(11));
        facultyPhoneNumber.setText(text.get(12));
        facultySearchBy.setPromptText(text.get(13));
        universityManagementHeader.setText(text.get(14));
    }

}
