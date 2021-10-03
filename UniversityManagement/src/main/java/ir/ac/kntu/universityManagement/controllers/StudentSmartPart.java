package ir.ac.kntu.universityManagement.controllers;

import ir.ac.kntu.universityManagement.models.individuals.Student;
import ir.ac.kntu.universityManagement.repositories.StudentRepository;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class StudentSmartPart extends Application {

    public static StudentRepository studentRepository;

    public static void main(String[] args){
        launch(args);
    }

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField studentID;
    @FXML
    private TextField nationalID;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/StudentSmartPart.fxml"));
        stage.setScene(new Scene(root, 760, 560));
        stage.show();
    }

    public void saveStudent(){
        System.out.println(this.firstName.getText()+", "+ this.lastName.getText()+", "+ this.email.getText());
        studentRepository.save(new Student(this.firstName.getText(), this.lastName.getText(), this.email.getText()));
    }

}
