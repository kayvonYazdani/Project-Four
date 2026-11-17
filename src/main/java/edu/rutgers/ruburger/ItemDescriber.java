package edu.rutgers.ruburger;

import java.util.Locale;

/**
 * Builds readable text descriptions of menu items, including
 * name, add-ons, size, quantity, and price.
 *
 * @author Timothy Creelman
 */
public final class ItemDescriber {

    /**
     * Prevents creation of ItemDescriber objects.
     */
    private ItemDescriber() {}

    /**
     * Describes a menu item with its quantity and price.
     *
     * @param item the item to describe
     * @return the description text
     */
    public static String describe(MenuItem item) {
        String description;
        if (item instanceof Combo combo) {
            description = "Combo: " + describeSandwich(combo.getSandwich())
                    + "\nSide: " + ViewUtil.pretty(combo.getSide().getType())
                    + " (" + ViewUtil.pretty(combo.getSide().getSize()) + ")"
                    + "\nDrink: "
                    + ViewUtil.pretty(combo.getDrink().getFlavor())
                    + " (" + ViewUtil.pretty(combo.getDrink().getSize()) + ")";
        } else if (item instanceof Sandwich sandwich) {
            description = describeSandwich(sandwich);
        } else if (item instanceof Beverage beverage) {
            description = ViewUtil.pretty(beverage.getFlavor())
                    + " Beverage (" + ViewUtil.pretty(beverage.getSize()) + ")";
        } else if (item instanceof Side side) {
            description = ViewUtil.pretty(side.getType())
                    + " (" + ViewUtil.pretty(side.getSize()) + ")";
        } else {
            description = item.getClass().getSimpleName();
        }
        return String.format(Locale.US,
                "%s\nQuantity: %d     Price: $%.2f",
                description, item.getQuantity(), item.price());
    }

    /**
     * Describes the bread, protein or patty, and add-ons of a
     * sandwich or burger.
     *
     * @param sandwich the sandwich or burger
     * @return the description text
     */
    private static String describeSandwich(Sandwich sandwich) {
        StringBuilder text = new StringBuilder();
        if (sandwich instanceof Burger burger) {
            text.append(burger.isDoublePatty()
                    ? "Double Patty Burger" : "Single Patty Burger");
        } else {
            text.append(ViewUtil.pretty(sandwich.protein))
                    .append(" Sandwich");
        }
        text.append("\nBread: ").append(ViewUtil.pretty(sandwich.bread));
        if (!sandwich.addOns.isEmpty()) {
            text.append("\nAdd-ons: ");
            for (int i = 0; i < sandwich.addOns.size(); i++) {
                if (i > 0) {
                    text.append(", ");
                }
                text.append(ViewUtil.pretty(sandwich.addOns.get(i)));
            }
        }
        return text.toString();
    }
}
