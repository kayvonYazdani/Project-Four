package edu.rutgers.ruburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;

/**
 * Shared helper methods used by the ordering controllers:
 * scene navigation, alerts, image loading, and enum display names.
 *
 * @author Timothy Creelman
 */
public final class ViewUtil {

    private static final String BASE = "/edu/rutgers/ruburger/";

    /**
     * Prevents creation of ViewUtil objects.
     */
    private ViewUtil() {}

    /**
     * Replaces the scene of the window that fired the event.
     *
     * @param event the button action that triggered the navigation
     * @param fxmlName the FXML file name, such as main-view.fxml
     * @param title the new window title
     */
    public static void switchScene(ActionEvent event,
                                   String fxmlName, String title) {
        try {
            URL location = ViewUtil.class.getResource(BASE + fxmlName);
            if (location == null) {
                throw new IllegalStateException(
                        "Cannot find " + fxmlName);
            }
            Parent root = new FXMLLoader(location).load();
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(new Scene(root, 850, 650));
            stage.setTitle(title);
        } catch (Exception exception) {
            showError("Navigation Error", exception.toString());
        }
    }

    /**
     * Returns to the main menu.
     *
     * @param event the button action that triggered the navigation
     */
    public static void goToMenu(ActionEvent event) {
        switchScene(event, "main-view.fxml", "RU Burger - Main Menu");
    }

    /**
     * Shows an error alert.
     *
     * @param header the alert heading
     * @param message the alert text
     */
    public static void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("RU Burger");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an information alert.
     *
     * @param header the alert heading
     * @param message the alert text
     */
    public static void showInfo(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RU Burger");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Loads an image from the images resource folder.
     *
     * @param fileName the image file name, such as chips.png
     * @return the image, or null if the file does not exist
     */
    public static Image loadImage(String fileName) {
        try {
            URL url = ViewUtil.class.getResource(
                    BASE + "images/" + fileName);
            if (url == null) {
                return null;
            }
            return new Image(url.toExternalForm());
        } catch (RuntimeException exception) {
            return null;
        }
    }

    /**
     * Describes the side and drink of a combo in one line.
     *
     * @param side the combo side
     * @param drink the combo drink
     * @return text such as "Small Chips + Medium Cola"
     */
    public static String comboSummary(SideType side, Flavor drink) {
        return "Small " + pretty(side) + " + Medium " + pretty(drink);
    }

    /**
     * Converts an enum constant to readable text,
     * for example ICED_TEA becomes "Iced Tea".
     *
     * @param value the enum constant
     * @return the readable name
     */
    public static String pretty(Enum<?> value) {
        StringBuilder text = new StringBuilder();
        for (String part : value.name().toLowerCase().split("_")) {
            if (text.length() > 0) {
                text.append(' ');
            }
            text.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1));
        }
        return text.toString();
    }

    /**
     * Fills a combo box with enum constants shown with readable
     * names, and selects the first one.
     *
     * @param box the combo box to fill
     * @param values the enum constants to offer
     * @param <T> the enum type
     */
    public static <T extends Enum<T>> void fillEnumBox(
            ComboBox<T> box, T[] values) {
        box.getItems().setAll(values);
        box.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T value) {
                return value == null ? "" : pretty(value);
            }

            @Override
            public T fromString(String text) {
                return null;
            }
        });
        box.getSelectionModel().selectFirst();
    }
}
