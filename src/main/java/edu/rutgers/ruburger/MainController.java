package edu.rutgers.ruburger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * Controls the main menu of the RU Burger application.
 *
 * @author Kayvon Yazdani
 */
public class MainController {

    /**
     * Handles the burger ordering button.
     */
    @FXML
    private void orderBurger() {
        showComingSoon("Burger Ordering");
    }

    /**
     * Handles the sandwich ordering button.
     */
    @FXML
    private void orderSandwich() {
        showComingSoon("Sandwich Ordering");
    }

    /**
     * Handles the beverage ordering button.
     */
    @FXML
    private void orderBeverage() {
        showComingSoon("Beverage Ordering");
    }

    /**
     * Handles the side ordering button.
     */
    @FXML
    private void orderSide() {
        showComingSoon("Side Ordering");
    }

    /**
     * Handles the current order button.
     */
    @FXML
    private void viewOrder() {
        showComingSoon("Current Order");
    }

    /**
     * Handles the placed orders button.
     */
    @FXML
    private void viewOrders() {
        showComingSoon("Placed Orders");
    }

    /**
     * Displays a temporary message until the
     * corresponding ordering page is implemented.
     *
     * @param feature the selected feature name
     */
    private void showComingSoon(String feature) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RU Burger");
        alert.setHeaderText(feature);
        alert.setContentText(
                "This feature will be available soon.");
        alert.showAndWait();
    }

}
