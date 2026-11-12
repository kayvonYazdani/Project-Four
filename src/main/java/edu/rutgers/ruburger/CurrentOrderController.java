package edu.rutgers.ruburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;

/**
 * Controls the current order page.
 *
 * @author Kayvon Yazdani
 */
public class CurrentOrderController {

    @FXML private ListView<MenuItem> orderListView;
    @FXML private Label orderNumberLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;
    @FXML private Button removeButton;
    @FXML private Button clearButton;
    @FXML private Button placeButton;

    /**
     * Initializes the order display and item list.
     */
    @FXML
    private void initialize() {

        orderListView.setCellFactory(list -> new ListCell<>() {

            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(describeItem(item));
                }
            }
        });

        orderListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldItem, newItem) ->
                        removeButton.setDisable(newItem == null));

        refreshOrder();

    }

    /**
     * Produces a readable description of a menu item.
     *
     * @param item the item to describe
     * @return description with quantity and price
     */
    private String describeItem(MenuItem item) {
        String description;
        if (item instanceof Combo combo) {
            description = "Combo: "
                    + describeSandwich(combo.getSandwich())
                    + "\nSide: " + combo.getSide().getType()
                    + " (" + combo.getSide().getSize() + ")"
                    + "\nDrink: " + combo.getDrink().getFlavor()
                    + " (" + combo.getDrink().getSize() + ")";

        } else if (item instanceof Sandwich sandwich) {
            description = describeSandwich(sandwich);

        } else if (item instanceof Beverage beverage) {
            description = beverage.getFlavor()
                    + " Beverage (" + beverage.getSize() + ")";

        } else if (item instanceof Side side) {
            description = side.getType()
                    + " (" + side.getSize() + ")";


        } else {
            description = item.getClass().getSimpleName();
        }

        return String.format(
                Locale.US,
                "%s\nQuantity: %d     Price: $%.2f",
                description,
                item.getQuantity(),
                item.price()
        );

    }

    /**
     * Describes burger or sandwich selections.
     *
     * @param sandwich the sandwich or burger
     * @return readable description
     */
    private String describeSandwich(Sandwich sandwich) {

        StringBuilder description = new StringBuilder();
        if (sandwich instanceof Burger burger) {
            description.append(
                    burger.isDoublePatty()
                            ? "Double Patty Burger"
                            : "Single Patty Burger"
            );
        } else {
            description.append(sandwich.protein)
                    .append(" Sandwich");
        }

        description.append("\nBread: ")
                .append(sandwich.bread);

        if (!sandwich.addOns.isEmpty()) {
            description.append("\nAdd-ons: ");

            for (int index = 0;
                 index < sandwich.addOns.size();
                 index++) {

                if (index > 0)
                    description.append(", ");

                description.append(
                        sandwich.addOns.get(index)
                );
            }
        }

        return description.toString();
    }

    /**
     * Refreshes the list and all order totals.
     */
    private void refreshOrder() {

        Order order = AppSession.getCurrentOrder();
        orderNumberLabel.setText(
                "Order #" + order.getNumber()
        );

        orderListView.getItems().setAll(
                order.getItems()
        );

        subtotalLabel.setText(
                money(order.getSubtotal())
        );

        taxLabel.setText(
                money(order.getSalesTax())
        );

        totalLabel.setText(
                money(order.getTotal())
        );

        boolean empty = order.getItems().isEmpty();
        removeButton.setDisable(true);
        clearButton.setDisable(empty);
        placeButton.setDisable(empty);
    }

    /**
     * Formats a price with two decimal places.
     *
     * @param amount the amount to format
     * @return formatted price
     */
    private String money(double amount) {
        return String.format(Locale.US, "$%.2f", amount);
    }

    /**
     * Removes the selected item from the order.
     */
    @FXML
    private void removeSelectedItem() {

        MenuItem selected =
                orderListView.getSelectionModel()
                        .getSelectedItem();

        if (selected == null)
            return;

        AppSession.getCurrentOrder()
                .removeItem(selected);

        refreshOrder();

    }

    /**
     * Clears all items after confirmation.
     */
    @FXML
    private void clearOrder() {

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmation.setTitle("RU Burger");
        confirmation.setHeaderText("Clear Current Order?");
        confirmation.setContentText(
                "This will remove every item from the cart."
        );

        if (confirmation.showAndWait().orElse(
                ButtonType.CANCEL) == ButtonType.OK) {

            AppSession.getCurrentOrder().clearItems();
            refreshOrder();
        }
    }

    /**
     * Finalizes the order and creates a new one.
     */
    @FXML
    private void placeOrder() {

        Order order = AppSession.getCurrentOrder();

        if (order.getItems().isEmpty()) {
            showMessage(
                    Alert.AlertType.WARNING,
                    "Empty Order",
                    "Add an item before placing the order."
            );
            return;
        }

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmation.setTitle("RU Burger");
        confirmation.setHeaderText(
                "Place Order #" + order.getNumber() + "?"
        );
        confirmation.setContentText(
                "Order total: " + money(order.getTotal())
        );

        if (confirmation.showAndWait().orElse(
                ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            int placedNumber = order.getNumber();

            AppSession.placeCurrentOrder();
            refreshOrder();
            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Order Placed!",
                    "Order #" + placedNumber
                            + " was placed successfully."
            );

        } catch (RuntimeException exception) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Unable to Place Order",
                    exception.getMessage()
            );
        }

    }

    /**
     * Returns to the main menu.
     *
     * @param event the back button action
     */
    @FXML
    private void goBack(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource(
                            "/edu/rutgers/ruburger/main-view.fxml"
                    )
            );

            Parent root = loader.load();
            Stage stage = (Stage)
                    ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();

            stage.setScene(new Scene(root, 850, 650));
            stage.setTitle("RU Burger - Main Menu");

        } catch (IOException | RuntimeException exception) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Navigation Error",
                    exception.toString()
            );
        }
    }

    /**
     * Displays a message to the user.
     *
     * @param type alert type
     * @param header alert heading
     * @param message alert description
     */
    private void showMessage(
            Alert.AlertType type,
            String header,
            String message) {

        Alert alert = new Alert(type);
        alert.setTitle("RU Burger");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
