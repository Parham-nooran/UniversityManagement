package ir.ac.kntu.universityManagement.controllers.managers;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.FacultyController;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.repositories.universityEntites.FacultyRepository;
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
import javafx.stage.Stage;
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
public class FacultyManagementController extends FacultyController implements Initializable {

    //----------------------------------------------------------------Constructor
    public FacultyManagementController(Scene scene) {
        super("FacultyManagement", scene);
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


    //----------------------------------------------------------------AddFaculty
    @FXML private TextField addFacultyName;
    @FXML private TextField addFacultyNumberOfClasses;
    @FXML private TextField addFacultyNumberOfStudents;
    @FXML private TextField addFacultyNumberOfInstructors;
    @FXML private TextField addFacultyPhoneNumber;
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

    @FXML private Label addFacultyNameLabel;
    @FXML private Label addFacultyNumberOfClassesLabel;
    @FXML private Label addFacultyNumberOfStudentsLabel;
    @FXML private Label addFacultyNumberOfInstructorsLabel;
    @FXML private Label addFacultyPhoneNumberLabel;
    @FXML private Label addFacultyHeader;

    @FXML private Button save;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static FacultyRepository facultyRepository;
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


    public static void refillFaculties(){
        Thread thread = new Thread(() -> {
            faculties = FXCollections.observableArrayList(facultyRepository.findAll());
            isUpTODate = true;
        });
        thread.setPriority(7);
        thread.start();
    }

    public void save() throws Exception {
        Image successNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/successNot.png"))));
        List<Faculty> foundFaculties = FacultyManagementController.faculties.stream()
                .filter(faculty1 -> faculty1.getName() != null && faculty1.getName().equals(addFacultyName.getText().trim())).
                collect(Collectors.toList());
        if(addFacultyName.getText().trim().length() < 2){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Name must be at least 2 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام حداقل باید 2 حرف باشد!");
            }
        } else if(isNotNumeric(addFacultyNumberOfInstructors.getText())){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Number of instructors is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("تعداد اساتید قابل قبول نمی باشد!");
            }
        } else if(isNotNumeric(addFacultyNumberOfClasses.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Number of classes is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("تعداد کلاس ها قابل قبول نمی باشد!");
            }
        } else if(isNotNumeric(addFacultyNumberOfStudents.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Number of students is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("تعداد دانشجوها قابل قبول نمی باشد!");
            }
        } else if(addFacultyName.getText().trim().length() < 2) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Name of the faculty is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام دانشکده قابل قبول نمی باشد!");
            }
        } else if(!foundFaculties.isEmpty()) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("There is already a faculty with this name!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("دانشکده ایی با این نام در سیستم وجود دارد!");
            }
        } else {
            Faculty faculty = new Faculty(addFacultyName.getText().trim(), Integer.parseInt(addFacultyNumberOfStudents.getText()),
                    Integer.parseInt(addFacultyNumberOfClasses.getText()), Integer.parseInt(addFacultyNumberOfInstructors.getText()),
                    addFacultyPhoneNumber.getText());
            new Thread(() -> FacultyManagementController.facultyRepository.save(faculty)).start();
            FacultyManagementController.faculties.add(faculty);
            new FacultyManagementController(menuButton.getScene()).start();
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


    public static long getFacultiesSize(){
        return facultyRepository.count();
    }

    public void setRefreshTables(){
        refreshTables(null,null,null,facultyTableView,null);
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){
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
        fxmlID.add("addFacultyHeader");

        fxmlID.add("save");
        fxmlID.add("universityManagementHeader");

        new LanguageConfiguration(fxmlID,text,"FacultyManagement");

        goBackToHomePage.setText(text.get(0));
        selected.setText(text.get(1));
        goToEditFaculty.setText(text.get(2));
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

        addFacultyNameLabel.setText(text.get(14));
        addFacultyNumberOfClassesLabel.setText(text.get(15));
        addFacultyNumberOfStudentsLabel.setText(text.get(16));
        addFacultyNumberOfInstructorsLabel.setText(text.get(17));
        addFacultyPhoneNumberLabel.setText(text.get(18));
        addFacultyHeader.setText(text.get(19));
        save.setText(text.get(20));
        universityManagementHeader.setText(text.get(21));
    }
}