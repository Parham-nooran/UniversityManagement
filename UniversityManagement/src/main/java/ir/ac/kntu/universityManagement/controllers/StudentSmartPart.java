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


public class StudentSmartPart {

    public static StudentRepository studentRepository;
    public  static Stage stage;



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


    public static void start() throws Exception {
        Parent root = FXMLLoader.load(StudentSmartPart.class.getResource("/fxml/StudentSmartPart.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void saveStudent(){
        System.out.println(this.firstName.getText()+", "+ this.lastName.getText()+", "+ this.email.getText());
        studentRepository.save(new Student(this.firstName.getText(), this.lastName.getText(), this.email.getText()));
    }

}
