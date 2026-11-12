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
 * Controls the burger ordering page.
 *
 * @author Kayvon Yazdani
 */
public class BurgerController {

    @FXML private ComboBox<String> breadBox;
    @FXML private RadioButton singlePatty;
    @FXML private RadioButton doublePatty;
    @FXML private CheckBox lettuce;
    @FXML private CheckBox tomatoes;
    @FXML private CheckBox onions;
    @FXML private CheckBox avocado;
    @FXML private CheckBox cheese;
    @FXML private CheckBox combo;
    @FXML private ComboBox<String> comboSideBox;
    @FXML private ComboBox<String> comboDrinkBox;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private Label priceLabel;


    /**
     * Initializes the burger customization screen.
     */
    @FXML
    private void initialize() {

        breadBox.getItems().addAll(
                "Brioche", "Wheat Bread", "Pretzel"
        );
        breadBox.getSelectionModel().selectFirst();
        ToggleGroup pattyGroup = new ToggleGroup();
        singlePatty.setToggleGroup(pattyGroup);
        doublePatty.setToggleGroup(pattyGroup);
        singlePatty.setSelected(true);
        comboSideBox.getItems().addAll(
                "Chips", "Apple Slices"
        );
        comboSideBox.getSelectionModel().selectFirst();
        comboDrinkBox.getItems().addAll(
                "Cola", "Tea", "Juice"
        );
        comboDrinkBox.getSelectionModel().selectFirst();
        quantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1, 99, 1
                )
        );

        quantitySpinner.valueProperty().addListener(
                (observable, oldValue, newValue) -> updatePrice()
        );

        comboSideBox.setDisable(true);
        comboDrinkBox.setDisable(true);
        breadBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> updatePrice()
        );

        updatePrice();


    }

    /**
     * Creates a Burger object from the selected controls.
     *
     * @return the customized burger
     */
    private Burger buildBurger() {

        Bread bread = switch (breadBox.getValue()) {
            case "Wheat Bread" -> Bread.WHEAT;
            case "Pretzel" -> Bread.PRETZEL;
            default -> Bread.BRIOCHE;
        };

        Burger burger = new Burger(
                bread, doublePatty.isSelected()
        );

        if (lettuce.isSelected())
            burger.addAddOn(findAddOn("LETTUCE"));

        if (tomatoes.isSelected())
            burger.addAddOn(findAddOn("TOMATO", "TOMATOES"));

        if (onions.isSelected())
            burger.addAddOn(findAddOn("ONION", "ONIONS"));

        if (avocado.isSelected())
            burger.addAddOn(findAddOn("AVOCADO"));

        if (cheese.isSelected())
            burger.addAddOn(findAddOn("CHEESE"));

        burger.quantity = quantitySpinner.getValue();
        return burger;

    }


    /**
     * Builds a combo meal using the selected burger,
     * side, drink, and quantity.
     *
     * @return the customized combo
     */
    private Combo buildCombo() {

        Burger burger = buildBurger();
        burger.setQuantity(1);

        SideType sideType = switch (comboSideBox.getValue()) {
            case "Apple Slices" -> SideType.APPLE_SLICES;
            default -> SideType.CHIPS;
        };

        Flavor drinkFlavor = switch (comboDrinkBox.getValue()) {
            case "Tea" -> Flavor.ICED_TEA;
            case "Juice" -> Flavor.APPLE_JUICE;
            default -> Flavor.COLA;
        };

        Side side = new Side(Size.SMALL, sideType);
        Beverage drink = new Beverage(
                Size.MEDIUM, drinkFlavor
        );
        Combo newCombo = new Combo(burger, drink, side);
        newCombo.setQuantity(quantitySpinner.getValue());
        return newCombo;
    }


    /**
     * Resolves the add-on constant from its possible names.
     *
     * @param names supported enum constant names
     * @return the matching add-on
     */
    private AddOns findAddOn(String... names) {
        for (String name : names) {
            try {
                return AddOns.valueOf(name);
            } catch (IllegalArgumentException ignored) {
            }
        }

        throw new IllegalArgumentException(
                "Unknown add-on: " + String.join("/", names)
        );

    }


    /**
     * Updates the running price using the appropriate
     * menu item's price method.
     */
    @FXML
    private void updatePrice() {
        try {
            boolean isCombo = combo.isSelected();
            comboSideBox.setDisable(!isCombo);
            comboDrinkBox.setDisable(!isCombo);
            MenuItem item;

            if (isCombo) {
                item = buildCombo();
            } else {
                item = buildBurger();
            }

            priceLabel.setText(
                    String.format(
                            Locale.US,
                            "$%.2f",
                            item.price()
                    )
            );
        } catch (RuntimeException exception) {
            priceLabel.setText("Invalid selection");
        }

    }


    /**
     * Returns to the main menu.
     *
     * @param event the button action
     */
    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("main-view.fxml")
            );

            Parent root = loader.load();
            Stage stage = (Stage)
                    ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();
            stage.setScene(new Scene(root, 850, 650));
            stage.setTitle("RU Burger - Main Menu");

        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText("Unable to return to menu");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }



    /**
     * Adds a burger or combo to the current order.
     */
    @FXML
    private void addToOrder() {
        try {
            MenuItem item;

            if (combo.isSelected()) {
                item = buildCombo();
            } else {
                item = buildBurger();
            }

            Order currentOrder = AppSession.getCurrentOrder();
            currentOrder.addItem(item);

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION
            );

            alert.setTitle("RU Burger");
            alert.setHeaderText("Added to Order!");

            String itemName = combo.isSelected()
                    ? "Burger Combo" : "Burger";
            alert.setContentText(
                    String.format(
                            Locale.US,
                            "%d x %s added!\n\n"
                                    + "Subtotal: $%.2f\n"
                                    + "Sales Tax: $%.2f\n"
                                    + "Order Total: $%.2f",
                            item.getQuantity(),
                            itemName,
                            currentOrder.getSubtotal(),
                            currentOrder.getSalesTax(),
                            currentOrder.getTotal()
                    )
            );

            alert.showAndWait();

        } catch (RuntimeException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RU Burger");
            alert.setHeaderText("Unable to Add Item");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Handles combo side and drink selection changes.
     * Image updates will be connected when the image
     * resources and ImageView controls are added.
     */
    @FXML
    private void updateComboSelection() {
        updatePrice();
    }



}

