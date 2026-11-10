
package edu.rutgers.ruburger;

/**
 * Represents a beverage sold at RU Burger.
 * The price depends on the selected size and quantity.
 *
 * @author Kayvon Yazdani
 */
public class Beverage extends MenuItem {

    private Size size;
    private Flavor flavor;

    /**
     * Constructs a beverage with a selected size and flavor.
     *
     * @param size the size of the beverage
     * @param flavor the flavor of the beverage
     */
    public Beverage(Size size, Flavor flavor) {
        if (size == null || flavor == null) {
            throw new IllegalArgumentException(
                    "Size and flavor cannot be null.");
        }
        this.size = size;
        this.flavor = flavor;
        this.quantity = 1;
    }


    /**
     * Calculates the beverage price based on size and quantity.
     *
     * @return the total price of the beverage
     */
    @Override
    public double price() {
        double total = switch (size) {
            case SMALL -> 1.99;
            case MEDIUM -> 2.49;
            case LARGE -> 2.99;
        };
        return total * quantity;

    }

    /**
     * Returns the selected beverage size.
     *
     * @return the beverage size
     */
    public Size getSize() {
        return size;
    }

    /**
     * Returns the selected beverage flavor.
     *
     * @return the beverage flavor
     */
    public Flavor getFlavor() {
        return flavor;
    }

}
