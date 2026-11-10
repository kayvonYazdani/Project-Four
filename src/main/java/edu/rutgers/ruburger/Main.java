package edu.rutgers.ruburger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Launches the RU Burger JavaFX application.
 *
 * @author Kayvon Yazdani
 */
public class Main extends Application {

    /**
     * Initializes and displays the main application window.
     *
     * @param stage the primary JavaFX stage
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        "/edu/rutgers/ruburger/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        stage.setTitle("RU Burger - Ordering System");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(550);
        stage.show();

    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
