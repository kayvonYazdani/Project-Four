package edu.rutgers.ruburger;
import java.util.ArrayList;

/**
 * Represents a customer order at RU Burger.
 * Stores menu items and calculates the subtotal,
 * sales tax, and final order total.
 *
 * @author Kayvon Yazdani
 */
public class Order {

    private int number;
    private ArrayList<MenuItem> items;

    /**
     * Constructs an order with a unique order number.
     *
     * @param number the unique order number
     */
    public Order(int number) {
        this.number = number;
        this.items = new ArrayList<>();
    }


    /**
     * Adds a menu item to the order.
     *
     * @param item the menu item to add
     */
    public void addItem(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Cannot add a null item.");
        }
        items.add(item);

    }

    /**
     * Removes a menu item from the order.
     *
     * @param item the menu item to remove
     * @return true if the item was removed
     */
    public boolean removeItem(MenuItem item) {
        return items.remove(item);
    }

    /**
     * Removes all menu items from the order.
     */
    public void clearItems() {
        items.clear();
    }

    /**
     * Calculates the subtotal before sales tax.
     *
     * @return the order subtotal
     */
    public double getSubtotal() {
        double subtotal = 0.0;

        for (MenuItem item : items) {
            subtotal += item.price();
        }
        return subtotal;

    }

    /**
     * Calculates New Jersey sales tax at 6.625%.
     *
     * @return the sales tax rounded to two decimals
     */
    public double getSalesTax() {
        return Math.round(getSubtotal() * 0.06625 * 100.0)
                / 100.0;
    }

    /**
     * Calculates the total price including sales tax.
     *
     * @return the total order price
     */
    public double getTotal() {
        return Math.round((getSubtotal() + getSalesTax())
                * 100.0) / 100.0;
    }

    /**
     * Returns the unique order number.
     *
     * @return the order number
     */
    public int getNumber() {
        return number;
    }


    /**
     * Returns a copy of the items in the order.
     *
     * @return a list of menu items
     */
    public ArrayList<MenuItem> getItems() {
        return new ArrayList<>(items);
    }
}
