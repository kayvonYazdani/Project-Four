package edu.rutgers.ruburger;

/**
 * Represents a combo meal at RU Burger.
 * A combo contains a sandwich or burger, a small side,
 * and a medium beverage for an additional two dollars.
 *
 * @author Kayvon Yazdani
 */
public class Combo extends MenuItem {

    private Sandwich sandwich;
    private Beverage drink;
    private Side side;

    /**
     * Constructs a combo meal with the selected items.
     *
     * @param sandwich the sandwich or burger
     * @param drink the medium beverage
     * @param side the small side
     */
    public Combo(Sandwich sandwich, Beverage drink, Side side) {
        if (sandwich == null || drink == null || side == null) {
            throw new IllegalArgumentException(
                    "Combo items cannot be null.");
        }

        if (side.getSize() != Size.SMALL ||
                (side.getType() != SideType.CHIPS &&
                        side.getType() != SideType.APPLE_SLICES)) {
            throw new IllegalArgumentException(
                    "Combo requires small chips or apple slices.");
        }

        if (drink.getSize() != Size.MEDIUM ||
                !isComboDrink(drink.getFlavor())) {
            throw new IllegalArgumentException(
                    "Combo requires a medium cola, tea, or juice.");
        }

        if (sandwich.getQuantity() != 1 ||
                drink.getQuantity() != 1 ||
                side.getQuantity() != 1) {
            throw new IllegalArgumentException(
                    "Combo items must have a quantity of one.");
        }

        this.sandwich = sandwich;
        this.drink = drink;
        this.side = side;
        this.quantity = 1;

    }

    /**
     * Checks whether a beverage flavor is valid for a combo.
     *
     * @param flavor the selected beverage flavor
     * @return true if the flavor is eligible for a combo
     */
    private boolean isComboDrink(Flavor flavor) {
        return switch (flavor) {
            case COLA, ICED_TEA, SWEET_TEA, GREEN_TEA,
                 APPLE_JUICE, ORANGE_JUICE,
                 CRANBERRY_JUICE, GRAPE_JUICE -> true;
            default -> false;
        };
    }

    /**
     * Calculates the combo price with the additional
     * two-dollar charge and selected quantity.
     *
     * @return the total combo price
     */
    @Override
    public double price() {
        return (sandwich.price() + 2.00) * quantity;
    }

    /**
     * Returns the sandwich or burger.
     *
     * @return the selected sandwich
     */
    public Sandwich getSandwich() {
        return sandwich;
    }

    /**
     * Returns the combo beverage.
     *
     * @return the selected beverage
     */
    public Beverage getDrink() {
        return drink;
    }

    /**
     * Returns the combo side.
     *
     * @return the selected side
     */
    public Side getSide() {
        return side;
    }


}
