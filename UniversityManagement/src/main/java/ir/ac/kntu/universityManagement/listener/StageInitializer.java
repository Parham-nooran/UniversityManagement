package ir.ac.kntu.universityManagement.listener;

import ir.ac.kntu.universityManagement.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class StageInitializer implements ApplicationListener<LoginController.StageReadyEvent> {
    @Value("classpath:/fxml/LoginView.fxml")
    private Resource viewResource;
    @SneakyThrows
    @Override
    public void onApplicationEvent(LoginController.StageReadyEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(viewResource.getURL());
        Parent parent = fxmlLoader.load();
        Stage stage = event.getStage();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
