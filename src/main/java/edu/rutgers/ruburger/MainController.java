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
     * Opens the burger ordering screen.
     *
     * @param event the button click
     */
    @FXML
    private void orderBurger(javafx.event.ActionEvent event) {

        try {
            java.net.URL location = Main.class.getResource(
                    "/edu/rutgers/ruburger/burger-view.fxml"
            );

            if (location == null) {
                throw new IllegalStateException(
                        "Cannot find burger-view.fxml. "
                                + "Check the resources folder."
                );
            }

            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(location);

            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage =
                    (javafx.stage.Stage)
                            ((javafx.scene.Node) event.getSource())
                                    .getScene().getWindow();

            stage.setScene(new javafx.scene.Scene(root, 850, 650));
            stage.setTitle("RU Burger - Burger Ordering");

        } catch (Exception exception) {

            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RU Burger");
            alert.setHeaderText("Unable to open Burger Ordering");
            alert.setContentText(exception.toString());
            alert.showAndWait();
        }
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
     * Opens the current order page.
     *
     * @param event the button action
     */
    @FXML
    private void viewOrder(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(
                            Main.class.getResource(
                                    "/edu/rutgers/ruburger/current-order-view.fxml"
                            )
                    );

            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage =
                    (javafx.stage.Stage)
                            ((javafx.scene.Node) event.getSource())
                                    .getScene().getWindow();

            stage.setScene(
                    new javafx.scene.Scene(root, 850, 650)
            );

            stage.setTitle("RU Burger - Current Order");

        } catch (Exception exception) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RU Burger");
            alert.setHeaderText("Unable to Open Current Order");
            alert.setContentText(exception.toString());
            alert.showAndWait();
        }
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
