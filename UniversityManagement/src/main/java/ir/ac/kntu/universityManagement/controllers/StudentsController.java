package ir.ac.kntu.universityManagement.controllers;

import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.repositories.StudentRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Setter
@NoArgsConstructor
public class StudentsController implements Initializable {
    @FXML
    public ListView<String> list;

    private Stage stage;

    public static StudentRepository studentRepository;

    public StudentsController(Stage stage) {
        this.stage = stage;
    }

    public void goBack() throws Exception {
        new MainPageController((Stage)list.getScene().getWindow()).start();
    }

    public  void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/StudentsView.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> studentList = studentRepository.findAll().stream().map(Student::toString).collect(Collectors.toList());

        this.list.getItems().addAll(FXCollections.observableArrayList(studentList));
        System.out.println(studentList.get(0)+","+studentList.get(1));
    }
}
