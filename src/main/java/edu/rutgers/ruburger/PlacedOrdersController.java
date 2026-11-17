package edu.rutgers.ruburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

/**
 * Controls the placed orders page: browsing, canceling,
 * and exporting the orders that have been placed.
 *
 * @author Timothy Creelman
 */
public class PlacedOrdersController {

    @FXML private ListView<Order> orderListView;
    @FXML private ListView<MenuItem> itemListView;
    @FXML private Label subtotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;
    @FXML private Button cancelButton;
    @FXML private Button exportButton;

    /**
     * Sets up both lists and loads the placed orders.
     */
    @FXML
    private void initialize() {
        orderListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                setText(empty || order == null ? null
                        : "Order #" + order.getNumber() + "   "
                        + money(order.getTotal()));
            }
        });
        itemListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null
                        : ItemDescriber.describe(item));
            }
        });
        orderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldOrder, newOrder) ->
                        showOrder(newOrder));
        refreshOrders();
    }

    /**
     * Reloads the list of placed orders from the order manager.
     */
    private void refreshOrders() {
        List<Order> orders = AppSession.getOrderManager().getOrders();
        orderListView.getItems().setAll(orders);
        exportButton.setDisable(orders.isEmpty());
        showOrder(null);
    }

    /**
     * Shows the items and totals of the selected order.
     *
     * @param order the selected order, or null when none is selected
     */
    private void showOrder(Order order) {
        cancelButton.setDisable(order == null);
        if (order == null) {
            itemListView.getItems().clear();
            subtotalLabel.setText("Subtotal: " + money(0));
            taxLabel.setText("Sales Tax: " + money(0));
            totalLabel.setText("Order Total: " + money(0));
            return;
        }
        itemListView.getItems().setAll(order.getItems());
        subtotalLabel.setText("Subtotal: " + money(order.getSubtotal()));
        taxLabel.setText("Sales Tax: " + money(order.getSalesTax()));
        totalLabel.setText("Order Total: " + money(order.getTotal()));
    }

    /**
     * Cancels the selected order after confirmation.
     */
    @FXML
    private void cancelOrder() {
        Order selected = orderListView.getSelectionModel()
                .getSelectedItem();
        if (selected == null) {
            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("RU Burger");
        confirmation.setHeaderText(
                "Cancel Order #" + selected.getNumber() + "?");
        confirmation.setContentText(
                "This removes the order from the placed orders.");
        if (confirmation.showAndWait().orElse(ButtonType.CANCEL)
                != ButtonType.OK) {
            return;
        }
        AppSession.getOrderManager().cancelOrder(selected.getNumber());
        refreshOrders();
    }

    /**
     * Exports all placed orders to a text file.
     */
    @FXML
    private void exportOrders() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Export Orders");
            chooser.setInitialFileName("orders.txt");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = chooser.showSaveDialog(
                    orderListView.getScene().getWindow());
            if (file == null) {
                return;
            }
            try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
                for (Order order : AppSession.getOrderManager().getOrders()) {
                    writer.println("Order #" + order.getNumber());
                    for (MenuItem item : order.getItems()) {
                        writer.println(ItemDescriber.describe(item));
                        writer.println();
                    }
                    writer.println("Order Total: " + money(order.getTotal()));
                    writer.println("----------------------------------");
                    writer.println();
                }
            }
            ViewUtil.showInfo("Export Complete",
                    "Orders saved to " + file.getName());
        } catch (Exception exception) {
            ViewUtil.showError("Export Failed", exception.toString());
        }
    }

    /**
     * Returns to the main menu.
     *
     * @param event the button action
     */
    @FXML
    private void goBack(ActionEvent event) {
        ViewUtil.goToMenu(event);
    }

    /**
     * Formats an amount with a dollar sign and two decimals.
     *
     * @param amount the amount to format
     * @return the formatted amount
     */
    private String money(double amount) {
        return String.format(Locale.US, "$%.2f", amount);
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
