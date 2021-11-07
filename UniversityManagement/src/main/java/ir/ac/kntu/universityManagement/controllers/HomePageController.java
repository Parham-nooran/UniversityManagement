package ir.ac.kntu.universityManagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.net.URL;
import java.util.ResourceBundle;

@Setter
@NoArgsConstructor
public class HomePageController implements Initializable {

    @FXML
    private TreeView treeView;

    Image informationIcon = new Image(getClass().getResourceAsStream("/icon/Information.png"));
    Image courseIcon = new Image(getClass().getResourceAsStream("/icon/Course.png"));
    Image userManagementIcon = new Image(getClass().getResourceAsStream("/icon/UserManagement.png"));
    Image studentsIcon = new Image(getClass().getResourceAsStream("/icon/Students.png"));
    Image facultiesIcon = new Image(getClass().getResourceAsStream("/icon/Faculties.png"));
    Image settingsIcon = new Image(getClass().getResourceAsStream("/icon/Settings.png"));

    private Stage stage;

    public HomePageController(Stage stage) {
        this.stage = stage;
    }

    public void selectItem() {

    }

    public void start() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/HomePage.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TreeItem<String> rootItem1 = new TreeItem<>("Root");

        TreeItem<String> branchItem1 = new TreeItem<>("Personal information", new ImageView(informationIcon));
        TreeItem<String> leafItem1 = new TreeItem<>("View personal information");

        TreeItem<String> branchItem2 = new TreeItem<>("Course", new ImageView(courseIcon));
        TreeItem<String> leafItem2 = new TreeItem<>("View current semester");

        TreeItem<String> branchItem3 = new TreeItem<>("User management", new ImageView(userManagementIcon));
        TreeItem<String> leafItem3 = new TreeItem<>("Manage users");

        TreeItem<String> branchItem4 = new TreeItem<>("Students", new ImageView(studentsIcon));
        TreeItem<String> leafItem6 = new TreeItem<>("View all university students");

        TreeItem<String> branchItem5 = new TreeItem<>("Faculties", new ImageView(facultiesIcon));
        TreeItem<String> leafItem7 = new TreeItem<>("View all university faculties");

        TreeItem<String> branchItem6 = new TreeItem<>("Setting", new ImageView(settingsIcon));
        TreeItem<String> leafItem8 = new TreeItem<>("View application settings");

        rootItem1.getChildren().addAll(branchItem1,branchItem2,branchItem3,branchItem4,branchItem5,branchItem6);
        branchItem1.getChildren().addAll(leafItem1);
        branchItem2.getChildren().addAll(leafItem2);
        branchItem3.getChildren().addAll(leafItem3);
        branchItem4.getChildren().addAll(leafItem6);
        branchItem5.getChildren().addAll(leafItem7);
        branchItem6.getChildren().addAll(leafItem8);

        treeView.setRoot(rootItem1);
        treeView.setShowRoot(false);
    }
}