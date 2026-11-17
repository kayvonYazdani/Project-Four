package edu.rutgers.ruburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Locale;

/**
 * Controls the sandwich ordering page.
 *
 * @author Timothy Creelman
 */
public class SandwichController {

    @FXML private ComboBox<Bread> breadBox;
    @FXML private ComboBox<Protein> proteinBox;
    @FXML private CheckBox lettuce;
    @FXML private CheckBox tomatoes;
    @FXML private CheckBox onions;
    @FXML private CheckBox avocado;
    @FXML private CheckBox cheese;
    @FXML private CheckBox combo;
    @FXML private ComboBox<SideType> comboSideBox;
    @FXML private ComboBox<String> comboDrinkBox;
    @FXML private ImageView itemImage;
    @FXML private ImageView comboSideImage;
    @FXML private ImageView comboDrinkImage;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private Label priceLabel;

    /**
     * Initializes the sandwich customization screen.
     */
    @FXML
    private void initialize() {
        ViewUtil.fillEnumBox(breadBox, Bread.values());
        ViewUtil.fillEnumBox(proteinBox, new Protein[] {
            Protein.ROAST_BEEF, Protein.SALMON, Protein.CHICKEN
        });
        ViewUtil.fillEnumBox(comboSideBox, new SideType[] {
            SideType.CHIPS, SideType.APPLE_SLICES
        });
        comboDrinkBox.getItems().setAll("Cola", "Tea", "Juice");
        comboDrinkBox.getSelectionModel().selectFirst();

        quantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1, 99, 1));
        quantitySpinner.valueProperty().addListener(
                (observable, oldValue, newValue) -> updatePrice());

        itemImage.setImage(ViewUtil.loadImage("sandwich.png"));
        updatePrice();
    }

    /**
     * Creates a Sandwich object from the selected controls.
     *
     * @return the customized sandwich with quantity one
     */
    private Sandwich buildSandwich() {
        Sandwich sandwich = new Sandwich(
                breadBox.getValue(), proteinBox.getValue());
        if (lettuce.isSelected()) {
            sandwich.addAddOn(AddOns.LETTUCE);
        }
        if (tomatoes.isSelected()) {
            sandwich.addAddOn(AddOns.TOMATOES);
        }
        if (onions.isSelected()) {
            sandwich.addAddOn(AddOns.ONIONS);
        }
        if (avocado.isSelected()) {
            sandwich.addAddOn(AddOns.AVOCADO);
        }
        if (cheese.isSelected()) {
            sandwich.addAddOn(AddOns.CHEESE);
        }
        return sandwich;
    }

    /**
     * Builds the menu item to order: a sandwich or a combo,
     * using the selected quantity.
     *
     * @return the item to add to the order
     */
    private MenuItem buildItem() {
        int quantity = quantitySpinner.getValue();
        Sandwich sandwich = buildSandwich();
        if (!combo.isSelected()) {
            sandwich.setQuantity(quantity);
            return sandwich;
        }
        Flavor flavor = switch (comboDrinkBox.getValue()) {
            case "Tea" -> Flavor.ICED_TEA;
            case "Juice" -> Flavor.APPLE_JUICE;
            default -> Flavor.COLA;
        };
        Side side = new Side(Size.SMALL, comboSideBox.getValue());
        Beverage drink = new Beverage(Size.MEDIUM, flavor);
        Combo newCombo = new Combo(sandwich, drink, side);
        newCombo.setQuantity(quantity);
        return newCombo;
    }

    /**
     * Updates the running price and the combo images.
     */
    @FXML
    private void updatePrice() {
        try {
            boolean isCombo = combo.isSelected();
            comboSideBox.setDisable(!isCombo);
            comboDrinkBox.setDisable(!isCombo);
            updateComboImages();
            priceLabel.setText(String.format(
                    Locale.US, "$%.2f", buildItem().price()));
        } catch (RuntimeException exception) {
            priceLabel.setText("Invalid selection");
        }
    }

    /**
     * Changes the side and drink pictures to match the selections.
     */
    private void updateComboImages() {
        SideType side = comboSideBox.getValue();
        String drink = comboDrinkBox.getValue();
        if (side != null) {
            comboSideImage.setImage(ViewUtil.loadImage(
                    side == SideType.CHIPS ? "chips.png"
                            : "apple_slices.png"));
        }
        if (drink != null) {
            comboDrinkImage.setImage(ViewUtil.loadImage(
                    drink.toLowerCase(Locale.US) + ".png"));
        }
    }

    /**
     * Adds the sandwich or combo to the current order.
     */
    @FXML
    private void addToOrder() {
        try {
            MenuItem item = buildItem();
            Order order = AppSession.getCurrentOrder();
            order.addItem(item);
            ViewUtil.showInfo("Added to Order!", String.format(
                    Locale.US,
                    "%d x %s added!%n%nSubtotal: $%.2f%n"
                            + "Sales Tax: $%.2f%nOrder Total: $%.2f",
                    item.getQuantity(),
                    combo.isSelected() ? "Sandwich Combo" : "Sandwich",
                    order.getSubtotal(), order.getSalesTax(),
                    order.getTotal()));
        } catch (RuntimeException exception) {
            ViewUtil.showError("Unable to Add Item",
                    exception.getMessage());
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
     * Highlights a button when the mouse enters it.
     *
     * @param event the mouse event
     */
    @FXML
    private void highlightButton(MouseEvent event) {
        ((Button) event.getSource()).setStyle(
                "-fx-background-color: #8B0000; -fx-text-fill: white;");
    }

    /**
     * Restores a button's style when the mouse leaves it.
     *
     * @param event the mouse event
     */
    @FXML
    private void resetButton(MouseEvent event) {
        ((Button) event.getSource()).setStyle("");
    }
}
