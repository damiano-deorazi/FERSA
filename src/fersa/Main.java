package fersa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login_view.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("FERSA");
        primaryStage.setScene(new Scene(root, 627.0, 433.0));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
