package edu.rutgers.ruburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Locale;

/**
 * Controls the beverage ordering page.
 *
 * @author Timothy Creelman
 */
public class BeverageController {

    @FXML private ComboBox<Flavor> flavorBox;
    @FXML private RadioButton smallSize;
    @FXML private RadioButton mediumSize;
    @FXML private RadioButton largeSize;
    @FXML private ImageView itemImage;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private Label priceLabel;

    /**
     * Initializes the beverage ordering screen.
     */
    @FXML
    private void initialize() {
        ViewUtil.fillEnumBox(flavorBox, Flavor.values());
        ToggleGroup sizeGroup = new ToggleGroup();
        smallSize.setToggleGroup(sizeGroup);
        mediumSize.setToggleGroup(sizeGroup);
        largeSize.setToggleGroup(sizeGroup);
        smallSize.setSelected(true);

        quantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1, 99, 1));
        quantitySpinner.valueProperty().addListener(
                (observable, oldValue, newValue) -> updatePrice());

        itemImage.setImage(ViewUtil.loadImage("beverage.png"));
        updatePrice();
    }

    /**
     * Returns the size chosen by the radio buttons.
     *
     * @return the selected size
     */
    private Size selectedSize() {
        if (largeSize.isSelected()) {
            return Size.LARGE;
        }
        return mediumSize.isSelected() ? Size.MEDIUM : Size.SMALL;
    }

    /**
     * Creates a Beverage from the selected controls.
     *
     * @return the beverage with the selected quantity
     */
    private Beverage buildBeverage() {
        Beverage beverage = new Beverage(
                selectedSize(), flavorBox.getValue());
        beverage.setQuantity(quantitySpinner.getValue());
        return beverage;
    }

    /**
     * Updates the running price.
     */
    @FXML
    private void updatePrice() {
        try {
            priceLabel.setText(String.format(
                    Locale.US, "$%.2f", buildBeverage().price()));
        } catch (RuntimeException exception) {
            priceLabel.setText("Invalid selection");
        }
    }

    /**
     * Adds the beverage to the current order.
     */
    @FXML
    private void addToOrder() {
        try {
            Beverage beverage = buildBeverage();
            Order order = AppSession.getCurrentOrder();
            order.addItem(beverage);
            ViewUtil.showInfo("Added to Order!", String.format(
                    Locale.US,
                    "%d x %s (%s) added!%n%nSubtotal: $%.2f%n"
                            + "Sales Tax: $%.2f%nOrder Total: $%.2f",
                    beverage.getQuantity(),
                    ViewUtil.pretty(beverage.getFlavor()),
                    ViewUtil.pretty(beverage.getSize()),
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
