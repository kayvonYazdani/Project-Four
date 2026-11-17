package edu.rutgers.ruburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.Locale;

/**
 * Controls the combo window where the side and drink of a
 * combo meal are chosen. The pictures change as the choices change.
 *
 * @author Timothy Creelman
 */
public class ComboController {

    @FXML private ComboBox<SideType> sideBox;
    @FXML private ComboBox<String> drinkBox;
    @FXML private ImageView sideImage;
    @FXML private ImageView drinkImage;

    private boolean confirmed;

    /**
     * Opens the combo window and waits until it is closed.
     *
     * @param owner the window that opens the combo window
     * @param side the side to show as selected
     * @param drink the drink to show as selected
     * @return this window's controller if the choice was confirmed,
     *         or null if it was canceled or could not be opened
     */
    public static ComboController showDialog(
            Window owner, SideType side, Flavor drink) {
        try {
            URL location = ComboController.class.getResource(
                    "/edu/rutgers/ruburger/combo-view.fxml");
            if (location == null) {
                throw new IllegalStateException(
                        "Cannot find combo-view.fxml");
            }
            FXMLLoader loader = new FXMLLoader(location);
            Parent root = loader.load();
            ComboController controller = loader.getController();
            controller.setSelection(side, drink);

            Stage stage = new Stage();
            stage.initOwner(owner);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("RU Burger - Combo Side and Drink");
            stage.setScene(new Scene(root, 560, 470));
            stage.showAndWait();
            return controller.confirmed ? controller : null;
        } catch (Exception exception) {
            ViewUtil.showError("Unable to Open Combo",
                    exception.toString());
            return null;
        }
    }

    /**
     * Fills the choice boxes with the combo options.
     */
    @FXML
    private void initialize() {
        ViewUtil.fillEnumBox(sideBox, new SideType[] {
            SideType.CHIPS, SideType.APPLE_SLICES
        });
        drinkBox.getItems().setAll("Cola", "Tea", "Juice");
        drinkBox.getSelectionModel().selectFirst();
        updateImages();
    }

    /**
     * Shows the given side and drink as the current selection.
     *
     * @param side the side to select
     * @param drink the drink to select
     */
    private void setSelection(SideType side, Flavor drink) {
        sideBox.setValue(side);
        drinkBox.setValue(labelFor(drink));
        updateImages();
    }

    /**
     * Returns the chosen side.
     *
     * @return the selected side type
     */
    public SideType getSide() {
        return sideBox.getValue();
    }

    /**
     * Returns the chosen drink.
     *
     * @return the selected drink flavor
     */
    public Flavor getDrink() {
        return switch (drinkBox.getValue()) {
            case "Tea" -> Flavor.ICED_TEA;
            case "Juice" -> Flavor.APPLE_JUICE;
            default -> Flavor.COLA;
        };
    }

    /**
     * Converts a combo drink flavor to its menu label.
     *
     * @param drink the drink flavor
     * @return Cola, Tea, or Juice
     */
    private String labelFor(Flavor drink) {
        return switch (drink) {
            case ICED_TEA -> "Tea";
            case APPLE_JUICE -> "Juice";
            default -> "Cola";
        };
    }

    /**
     * Changes the side and drink pictures to match the selections.
     */
    @FXML
    private void updateImages() {
        SideType side = sideBox.getValue();
        String drink = drinkBox.getValue();
        if (side != null) {
            sideImage.setImage(ViewUtil.loadImage(
                    side.name().toLowerCase(Locale.US) + ".png"));
        }
        if (drink != null) {
            drinkImage.setImage(ViewUtil.loadImage(
                    drink.toLowerCase(Locale.US) + ".png"));
        }
    }

    /**
     * Confirms the selection and closes the window.
     *
     * @param event the button action
     */
    @FXML
    private void confirm(ActionEvent event) {
        confirmed = true;
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * Closes the window without changing the combo.
     *
     * @param event the button action
     */
    @FXML
    private void cancel(ActionEvent event) {
        confirmed = false;
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * Highlights a button when the mouse enters it.
     *
     * @param event the mouse event
     */
    @FXML
    private void highlightButton(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.getStyleClass().remove("button-hover");
        button.getStyleClass().add("button-hover");
    }

    /**
     * Restores a button's style when the mouse leaves it.
     *
     * @param event the mouse event
     */
    @FXML
    private void resetButton(MouseEvent event) {
        ((Button) event.getSource()).getStyleClass()
                .remove("button-hover");
    }
}
