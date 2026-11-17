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
    @FXML private ImageView itemImage;
    @FXML private Label comboSummaryLabel;
    @FXML private Button comboButton;

    private SideType comboSide = SideType.CHIPS;
    private Flavor comboDrink = Flavor.COLA;
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
        comboButton.setDisable(true);
        updateComboSummary();

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
        Side side = new Side(Size.SMALL, comboSide);
        Beverage drink = new Beverage(Size.MEDIUM, comboDrink);
        Combo newCombo = new Combo(sandwich, drink, side);
        newCombo.setQuantity(quantity);
        return newCombo;
    }

    /**
     * Updates the running price and the combo button.
     */
    @FXML
    private void updatePrice() {
        try {
            boolean isCombo = combo.isSelected();
            comboButton.setDisable(!isCombo);
            priceLabel.setText(String.format(
                    Locale.US, "$%.2f", buildItem().price()));
        } catch (RuntimeException exception) {
            priceLabel.setText("Invalid selection");
        }
    }

    /**
     * Opens the combo window to choose the combo side and drink.
     */
    @FXML
    private void chooseCombo() {
        ComboController chosen = ComboController.showDialog(
                priceLabel.getScene().getWindow(), comboSide, comboDrink);
        if (chosen != null) {
            comboSide = chosen.getSide();
            comboDrink = chosen.getDrink();
            updateComboSummary();
            updatePrice();
        }
    }

    /**
     * Shows the chosen combo side and drink as text.
     */
    private void updateComboSummary() {
        comboSummaryLabel.setText(
                ViewUtil.comboSummary(comboSide, comboDrink));
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
