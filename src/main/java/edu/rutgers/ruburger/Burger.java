package edu.rutgers.ruburger;

/**
 * Represents a customizable burger at RU Burger.
 * A burger can have a single or double beef patty
 * with a selection of bread and add-ons.
 *
 * @author Kayvon Yazdani
 */
public class Burger extends Sandwich {

    private boolean doublePatty;

    /**
     * Constructs a burger with the selected bread
     * and patty option.
     *
     * @param bread the selected bread
     * @param doublePatty true for a double patty,
     *                    false for a single patty
     */
    public Burger(Bread bread, boolean doublePatty) {

        super(bread, Protein.BEEF_PATTY);
        if (bread != Bread.BRIOCHE
                && bread != Bread.WHEAT
                && bread != Bread.PRETZEL) {
            throw new IllegalArgumentException(
                    "Invalid bread for a burger.");
        }

        this.doublePatty = doublePatty;

    }

    /**
     * Calculates the price of the burger including
     * its patty selection, add-ons, and quantity.
     *
     * @return the total burger price
     */
    @Override
    public double price() {
        double total = 6.99;

        if (doublePatty)
            total += 2.50;

        for (AddOns addOn : addOns)
            total += addOn.getPrice();

        return total * quantity;
    }

    /**
     * Returns whether the burger has a double patty.
     *
     * @return true if double patty, false otherwise
     */
    public boolean isDoublePatty() {
        return doublePatty;
    }

}
