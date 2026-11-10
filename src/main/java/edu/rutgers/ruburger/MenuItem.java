package edu.rutgers.ruburger;

/**
 * Represents a menu item sold at RU Burger.
 * All menu items must implement the price method.
 *
 * @author Kayvon Yazdani
 */
public abstract class MenuItem {

    protected int quantity;

    /**
     * Calculates the price of the menu item.
     *
     * @return the total price of the menu item
     */
    public abstract double price();

    /**
     * Sets the quantity of this item.
     *
     * @param quantity number of items ordered
     */
    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException(
                    "Quantity must be at least 1.");
        }
        this.quantity = quantity;
    }


    /**
     * Returns the quantity ordered.
     *
     * @return item quantity
     */
    public int getQuantity() {
        return quantity;
    }



}
