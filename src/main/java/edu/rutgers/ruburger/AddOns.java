package edu.rutgers.ruburger;

/**
 * Represents the available add-ons for burgers and
 * sandwiches at RU Burger. Each add-on has an
 * additional price.
 *
 * @author Kayvon Yazdani
 */
public enum AddOns {

    LETTUCE(0.30),
    TOMATOES(0.30),
    ONIONS(0.30),
    AVOCADO(0.50),
    CHEESE(1.00);

    private final double price;


    /**
     * Constructs an add-on with its corresponding price.
     *
     * @param price the additional cost of the add-on
     */
    AddOns(double price) {
        this.price = price;
    }

    /**
     * Returns the price of the selected add-on.
     *
     * @return the additional price of the add-on
     */
    public double getPrice() {
        return price;
    }

}
