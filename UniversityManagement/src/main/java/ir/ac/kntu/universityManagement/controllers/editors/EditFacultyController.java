package ir.ac.kntu.universityManagement.controllers.editors;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.FacultyController;
import ir.ac.kntu.universityManagement.controllers.managers.FacultyManagementController;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.settings.Language;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@NoArgsConstructor
public class EditFacultyController extends FacultyController implements Initializable {

    //----------------------------------------------------------------Constructor
    public EditFacultyController(Scene scene) {
        super("EditFaculty", scene);
    }
    //----------------------------------------------------------------


    //-------------------------------------------------Faculties TableView
    @FXML private TableView<Faculty> facultyTableView;
    @FXML private TextField facultySearchBox;
    @FXML private ComboBox<String> facultySearchBy;
    @FXML private TableColumn<Faculty, String> facultyId;
    @FXML private TableColumn<Faculty, String> facultyName;
    @FXML private TableColumn<Faculty, Integer> facultyNumberOfClasses;
    @FXML private TableColumn<Faculty, Integer> facultyNumberOfStudents;
    @FXML private TableColumn<Faculty, Integer> facultyNumberOfInstructors;
    @FXML private TableColumn<Faculty, String> facultyPhoneNumber;
    //----------------------------------------------------------------


    //----------------------------------------------------------------Editing faculty attributes
    @FXML private TextField editFacultyId;
    @FXML private TextField editFacultyName;
    @FXML private TextField editFacultyNumberOfClasses;
    @FXML private TextField editFacultyNumberOfStudents;
    @FXML private TextField editFacultyNumberOfInstructors;
    @FXML private TextField editFacultyPhoneNumber;
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

    @FXML private Label editFacultyNameLabel;
    @FXML private Label editFacultyNumberOfClassesLabel;
    @FXML private Label editFacultyNumberOfStudentsLabel;
    @FXML private Label editFacultyNumberOfInstructorsLabel;
    @FXML private Label editFacultyPhoneNumberLabel;
    @FXML private Label editFacultyHeader;
    @FXML private Button save;
    @FXML private Label editFacultyIDLabel;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static Faculty faculty;
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

        facultyTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                faculty = facultyTableView.getSelectionModel().getSelectedItem();
                if (faculty.getFacultyId() != null) {
                    editFacultyId.setText(faculty.getFacultyId());
                }else{
                    editFacultyId.setText("");
                }
                if (faculty.getName() != null) {
                    editFacultyName.setText(faculty.getName());
                }else{
                    editFacultyName.setText("");
                }
                if (faculty.getNumberOfClasses() != null) {
                    editFacultyNumberOfClasses.setText(faculty.getNumberOfClasses().toString());
                }else{
                    editFacultyNumberOfClasses.setText("");
                }
                if (faculty.getNumberOfStudents() != null) {
                    editFacultyNumberOfStudents.setText(faculty.getNumberOfStudents().toString());
                }else{
                    editFacultyNumberOfStudents.setText("");
                }
                if (faculty.getNumberOfInstructors() != null) {
                    editFacultyNumberOfInstructors.setText(faculty.getNumberOfInstructors().toString());
                }else{
                    editFacultyNumberOfInstructors.setText("");
                }
                if (faculty.getPhoneNumber() != null) {
                    editFacultyPhoneNumber.setText(faculty.getPhoneNumber());
                }else{
                    editFacultyPhoneNumber.setText("");
                }
            }
        });

    }

    public void edit() throws Exception {
        List<Faculty> foundFaculties = FacultyManagementController.faculties.stream()
                .filter(faculty1 -> faculty1.getName() != null && faculty1.getName().equals(editFacultyName.getText().trim())).
                collect(Collectors.toList());
        if (foundFaculties.size() > 1 && !foundFaculties.get(0).getId().equals(faculty.getId())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("There is already a faculty with this name!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("دانشکده ایی با این اسم در سیستم وجود دارد!");
            }
        } else if (editFacultyName.getText().trim().length() < 2) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Name must be at least 2 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام دانشکده حداقل باید 2 حرف باشد!");
            }
        } else if (isNotNumeric(editFacultyNumberOfInstructors.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Number of instructors is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("تعداد اساتید قابل قبول نیست!");
            }
        } else if (isNotNumeric(editFacultyNumberOfClasses.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Number of classes is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("تعداد کلاس ها قابل قبول نیست!!");
            }
        } else if (isNotNumeric(editFacultyNumberOfStudents.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Number of students is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("تعداد دانشجوها قابل قبول نیست!");
            }
        } else {
            faculty.setName(editFacultyName.getText().trim());
            faculty.setNumberOfClasses(Integer.parseInt(editFacultyNumberOfClasses.getText().trim()));
            faculty.setNumberOfStudents(Integer.parseInt(editFacultyNumberOfStudents.getText().trim()));
            faculty.setNumberOfInstructors(Integer.parseInt(editFacultyNumberOfInstructors.getText().trim()));
            faculty.setPhoneNumber(editFacultyPhoneNumber.getText().trim());
            new Thread(() -> FacultyManagementController.facultyRepository.save(faculty)).start();
            editFacultyId.setText("");
            editFacultyNumberOfClasses.setText("");
            editFacultyName.setText("");
            editFacultyNumberOfInstructors.setText("");
            editFacultyNumberOfStudents.setText("");
            editFacultyPhoneNumber.setText("");
            new EditFacultyController(facultyTableView.getScene()).start();
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


        fxmlID.add("addFacultyNameLabel");
        fxmlID.add("addFacultyNumberOfClassesLabel");
        fxmlID.add("addFacultyNumberOfStudentsLabel");
        fxmlID.add("addFacultyNumberOfInstructorsLabel");
        fxmlID.add("addFacultyPhoneNumberLabel");
        fxmlID.add("editFacultyHeader");
        fxmlID.add("save");
        fxmlID.add("editFacultyIDLabel");
        fxmlID.add("universityManagementHeader");


        new LanguageConfiguration(fxmlID,text,"FacultyManagement");

        goBackToHomePage.setText(text.get(0));
        goToFacultyManagement.setText(text.get(1));
        selected.setText(text.get(2));
        goToDeleteFaculty.setText(text.get(3));
        gotoViewAllFaculty.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));
        facultyManagementHeader.setText(text.get(6));

        facultyId.setText(text.get(7));
        facultyName.setText(text.get(8));
        facultyNumberOfClasses.setText(text.get(9));
        facultyNumberOfStudents.setText(text.get(10));
        facultyNumberOfInstructors.setText(text.get(11));
        facultyPhoneNumber.setText(text.get(12));
        facultySearchBy.setPromptText(text.get(13));

        editFacultyNameLabel.setText(text.get(14));
        editFacultyNumberOfClassesLabel.setText(text.get(15));
        editFacultyNumberOfStudentsLabel.setText(text.get(16));
        editFacultyNumberOfInstructorsLabel.setText(text.get(17));
        editFacultyPhoneNumberLabel.setText(text.get(18));
        editFacultyHeader.setText(text.get(19));
        save.setText(text.get(20));
        editFacultyIDLabel.setText(text.get(21));

        universityManagementHeader.setText(text.get(22));
    }

}
