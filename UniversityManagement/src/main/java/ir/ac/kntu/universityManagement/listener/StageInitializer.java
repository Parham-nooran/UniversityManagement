package ir.ac.kntu.universityManagement.listener;

import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.LoginController;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class StageInitializer implements ApplicationListener<LoginController.StageReadyEvent> {
    @Value("classpath:/fxml/LoginView.fxml") private Resource viewResource;
    @Value("classpath:/fxml/LoginViewPersian.fxml")private Resource viewResourcePersian;
    @SneakyThrows
    @Override
    public void onApplicationEvent(LoginController.StageReadyEvent event) {
        if (BaseController.getLanguage().equals("English")) {
            FXMLLoader fxmlLoader = new FXMLLoader(viewResource.getURL());
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            //-------------
            if(BaseController.getThemeName() != null) {
                scene.getStylesheets().remove("/css/themes/"+BaseController.getPreviousThemeName().toString()+".css");
                scene.getStylesheets().add("/css/themes/"+BaseController.getThemeName()+".css");
            }
            //-------------
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
            stage.setMaximized(true);
            stage.show();
        }
        else if (BaseController.getLanguage().equals("Persian")){
            FXMLLoader fxmlLoader = new FXMLLoader(viewResourcePersian.getURL());
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            //-------------
            if(BaseController.getThemeName() != null) {
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/themes/" +
                        BaseController.getThemeName() + ".css")).toExternalForm());
            }
            //-------------
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
            stage.setMaximized(true);
            stage.show();
        }


    }
}
