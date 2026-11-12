package edu.rutgers.ruburger;

import java.util.ArrayList;

/**
 * Represents a customizable sandwich at RU Burger.
 * Calculates the price based on the selected protein,
 * add-ons, and quantity.
 *
 * @author Kayvon Yazdani
 */
public class Sandwich extends MenuItem {

    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;

    /**
     * Constructs a sandwich with the selected bread
     * and protein.
     *
     * @param bread the selected bread
     * @param protein the selected protein
     */
    public Sandwich(Bread bread, Protein protein) {
        this.bread = bread;
        this.protein = protein;
        this.addOns = new ArrayList<>();
        this.quantity = 1;

    }

    /**
     * Adds a topping to the sandwich.
     *
     * @param addOn the topping to add
     */
    public void addAddOn(AddOns addOn) {
        if (addOn != null && !addOns.contains(addOn))
            addOns.add(addOn);
    }

    /**
     * Removes a topping from the sandwich.
     *
     * @param addOn the topping to remove
     */
    public void removeAddOn(AddOns addOn) {
        addOns.remove(addOn);
    }

    /**
     * Calculates the price of a sandwich based on
     * its protein, add-ons, and quantity.
     *
     * @return the total sandwich price
     */
    @Override
    public double price() {
        double total = switch (protein) {
            case ROAST_BEEF -> 10.99;
            case SALMON -> 9.99;
            case CHICKEN -> 8.99;
            case BEEF_PATTY -> 6.99;
        };

        for (AddOns addOn : addOns)
            total += addOn.getPrice();

        return total * quantity;

    }


}
